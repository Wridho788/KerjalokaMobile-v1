package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.R.attr.data
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.BasicImagePicker
import com.ciptakerjaarunika.kerjaloka.`interface`.RxImagePicker
import com.ciptakerjaarunika.kerjaloka.api.InterviewAPI
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.model.Interview.MessageType
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.DefaultGalleryMimes
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.DefaultSystemGalleryConfig
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jitsi.meet.sdk.*
import timber.log.Timber
import java.io.File
import java.net.URI
import java.util.*


class ChatPage(var sectionName: String,
               var sectionNo : Int?,
               val jobNo : Long?,
               val Receiver : Long,
               val logo: String?,
               val jobPosition : String?
)
    :  Fragment(), PositionOnBottom {

    private var chatModel : chat_model? = null
    private lateinit var recyclerView : RecyclerView;
    private lateinit var hubConnection: HubConnection
    private var onBottom : Boolean = false;
    private lateinit var binding : ActivityMainBinding
    private var MY_CAMERA_REQUEST_CODE :Int = 100
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var defaultImagePicker: BasicImagePicker

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.visibility = View.GONE
        hubConnection = HubConnectionBuilder.create(config().portAddress+"/ws/chat").build()
        if(SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED){
            hubConnection.start()
        }

        initRxImagePicker()
    }

    private fun initRxImagePicker() {
        defaultImagePicker = RxImagePicker.create(BasicImagePicker::class.java)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun pickGallery() {
        context?.let {
            defaultImagePicker
                .openGallery(
                    it,
                    DefaultSystemGalleryConfig.instance(
                        // mimesType = DefaultGalleryMimes.videoOnly()     // only video files
                        // mimesType = DefaultGalleryMimes.imageOnly()     // only image files, default options.
                        // mimesType = DefaultGalleryMimes.audioOnly()     // only audio files
                        mimesType = DefaultGalleryMimes.customTypes("video/*;image/*") // multiType
                    )
                )
                .subscribe { result -> onPickUriSuccess(result.uri) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun pickCamera() {
        context?.let {
            defaultImagePicker.openCamera(it)
                .subscribe { result -> onPickUriSuccess(result.uri) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPickUriSuccess(uri: Uri) {
        val pathName = context?.let { getPathFromUri(it, uri) }
        Log.d("Path", pathName.toString())
        if(pathName != null) {
            uploadImage(pathName)
        }
//        GlideApp.with(this)
//            .load(uri)
//            .into(imageView)
    }
    private fun getPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val project = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, project, null, null, null)
            val columnIndex: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage(imagePath : String){

        val file = File(imagePath?:"")
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        InterviewAPI().UploadChatPhoto(context, body) { res ->
            if (res != null) {
                if(res.code == 210){
                    val sender = SessionManager(context).user!!.userNo.toString()
                    val receiver = listOf<Long>(Receiver);
                    hubConnection.send(
                        "SendMessage",
                        sectionNo,
                        sender,
                        res.data.fileName,
                        receiver,
                        jobNo,
                        MessageType.ImageMessage.type.toString().toInt(),
                        res.data.resultFileName
                    )
                } else{
                    Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
                ... other events
         */
        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        context?.let { LocalBroadcastManager.getInstance(it).registerReceiver(broadcastReceiver, intentFilter) }
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i("Conference Joined with url%s", event.getData().get("url"))
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i("Participant joined%s", event.getData().get("name"))
                else -> Timber.i("Received event: %s", event.type)
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        context?.applicationContext?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(hangupBroadcastIntent) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)



        val titlePage = itemView.findViewById<TextView>(R.id.title)
        titlePage.text = sectionName

            val description = itemView.findViewById<TextView>(R.id.description)
            description.text = jobPosition

        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)
        val cameraButton = itemView.findViewById<ImageButton>(R.id.openCamera)

        val videoCallButton = itemView.findViewById<ImageButton>(R.id.video_call_btn)
        videoCallButton?.setOnClickListener{
            val roomId = SessionManager(context).user?.userNo.toString()+ Receiver.toString()
            val userInfo = JitsiMeetUserInfo();
            userInfo.email = SessionManager(context).user?.email
            userInfo.displayName = SessionManager(context).user?.userFullname

            if(SessionManager(context).user?.company != null){
                userInfo.displayName = SessionManager(context).user?.company?.companyName
            }


            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomId)
                .setUserInfo(userInfo)
                // Settings for audio and video
                //.setAudioMuted(true)
                //.setVideoMuted(true)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(context, options)
            hubConnection.send(
                "SendCall",
                listOf<Long>(Receiver),
                roomId
            )
        }

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri? = data?.data
                val pathName = fileUri?.let { it1 -> context?.let { it2 -> PathUtils.getPath(it2, it1) } }

                val file = File(pathName?:"")
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)
                InterviewAPI().UploadChatFile(context, body) { res ->
                    if (res != null) {
                        if(res.code == 210){
                            val sender = SessionManager(context).user!!.userNo.toString()
                            val receiver = listOf<Long>(Receiver);
                            hubConnection.send(
                                "SendMessage",
                                sectionNo,
                                sender,
                                res.data.fileName,
                                receiver,
                                jobNo,
                                MessageType.FileMessage.type.toString().toInt(),
                                res.data.resultFileName
                            )
                        } else{
                            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

//                val photo = it.data?.extras!!["data"] as Bitmap?
//                if (photo != null) {
//
////                    InterviewAPI().UploadChatPhoto(context, photo) { res ->
////                        Log.d("Response Upload", res.toString())
////                    }
//                }
            }
        }

        cameraButton.setOnClickListener{
            pickCamera()
            /*val intent = Intent("android.media.action.IMAGE_CAPTURE")

            Log.d("Camera Permission", ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA).toString())
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
                ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ){
                this.activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        listOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(), MY_CAMERA_REQUEST_CODE)
                };

                Toast.makeText(context, "Tidak memiliki izin akses kamera", Toast.LENGTH_SHORT).show()
            }
            else{
                activityResultLauncher.launch(intent)
//                startActivity(intent)
            }*/
        }


        Glide.with(itemView.context)
            .load(config().portAddress + "/photo/Profile/" + logo).fitCenter()
            .into(itemView.findViewById<ImageView>(R.id.userPhoto))

        backButton.setOnClickListener{
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if(isCompany) {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                InterviewAPI().CompanyGetInterviewList(context) {
                    if (it != null) {
                        val current_data = it.data.find { data -> data.jobNo == jobNo }
                        if (current_data != null) {
                            ft.replace(
                                id,
                                company_interview_byjob(current_data, jobNo),
                                "ChatFragment"
                            )
                            ft.commit()
                        }
                    }
                }
            }
            else{
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,  InterviewPage(), "InterviewPage")
                ft.commit()
            }

//            parentFragmentManager.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if(isCompany) {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                InterviewAPI().CompanyGetInterviewList(context) {
                    if (it != null) {
                        hubConnection.stop()
                        val current_data = it.data.find { data -> data.jobNo == jobNo }
                        if (current_data != null) {
                            ft.replace(
                                id,
                                company_interview_byjob(current_data, jobNo),
                                "ChatFragment"
                            )
                            ft.commit()
                        }
                    }
                }
            }
            else{
                hubConnection.stop();
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,  InterviewPage(), "InterviewPage")
                ft.commit()
            }
        }


        //recyclerView.scrollToPosition(section.Messages.size-1)
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewChat) as RecyclerView
//        view?.setOnClickListener {
//            CLoseKeyboard()
//        }
//        recyclerView.isClickable = true;
//        recyclerView.setOnClickListener{
//            Log.d("CLick", "recyle")
//            CLoseKeyboard()
//        }
        hubConnection.on("connected",
            { res ->
                Log.d("Websocket Response : ", res.toString())
                val userNo = SessionManager(context).user!!.userNo.toString()
                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                hubConnection.send("ReadSectionMessage", sectionNo.toString())

            }, String::class.java)

        hubConnection.on(
            "incomingCall",
            { roomId ->
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id,  IncomingCallPage(roomId), "IncomingCall")
                ft.commit()
            },
            String::class.java
        )

        hubConnection.on(
            "getmessage",
            { res: chat_data ->
                SessionManager(context).chatData = res
                Log.d("Message", res.toString())
                hubConnection.send("ReadSectionMessage", sectionNo.toString())
                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter?.notifyDataSetChanged()
                    if(onBottom) {
                         recyclerView.adapter?.itemCount?.minus(1)?.let { recyclerView.scrollToPosition(it)};
                    }
                })
            },
            chat_data::class.java
        )

        var LinearLayoutManager = LinearLayoutManager(activity)
        val thisContext = this
        recyclerView?.apply {
            layoutManager = LinearLayoutManager
            adapter = ChatAdapter(context, jobNo, Receiver, thisContext)
        }

        recyclerView.adapter?.itemCount?.minus(1)?.let { recyclerView.scrollToPosition(it)};

        var message = itemView.findViewById<EditText>(R.id.txt_message);
        var img_btnsend = itemView.findViewById<ImageView>(R.id.img_btnsend);
        var btn_send = itemView.findViewById<CardView>(R.id.btn_send);


//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                activity?.runOnUiThread(Runnable {
//                    recyclerView.adapter?.notifyDataSetChanged()
//                })
//            }
//        }, 0, 1000)
                message.setOnClickListener(){
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            activity?.runOnUiThread(Runnable {
                                recyclerView.adapter?.itemCount?.minus(1)
                                    ?.let { recyclerView.scrollToPosition(it) };
                            })
                        }
                    }, 300)
                }
                message.setOnFocusChangeListener { view, hasFocus ->
                    if (hasFocus) {
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread(Runnable {
                                    recyclerView.adapter?.itemCount?.minus(1)
                                        ?.let { recyclerView.scrollToPosition(it) };
                                })
                            }
                        }, 300)
                    }
                }
                btn_send.setOnClickListener{
                    var intent = Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    val requestIntent = Intent.createChooser(intent, "Choose a file");
                    activityResultLauncher.launch(requestIntent)
                }
                message.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


                    @SuppressLint("NotifyDataSetChanged")
                    override fun afterTextChanged(s: Editable) {
                        if(!message.text.toString().isNullOrEmpty() && !message.text.toString().isNullOrBlank() && message.text.toString() != ""){
                            img_btnsend.setImageResource(R.drawable.icon_send);
                            img_btnsend.rotation=-25f
                            btn_send.setOnClickListener{
                                val sender = SessionManager(context).user!!.userNo.toString()
                                val message = itemView.findViewById<EditText>(com.ciptakerjaarunika.kerjaloka.R.id.txt_message).text.toString()

                                val receiver = listOf<Long>(Receiver);
                                if(!message.isNullOrEmpty() && !message.isNullOrBlank() && message != "") {
                                    hubConnection.send(
                                        "SendMessage",
                                        sectionNo,
                                        sender,
                                        message,
                                        receiver,
                                        jobNo,
                                        MessageType.NormalMessage.type.toString().toInt(),
                                        null
                                    )
                                    Timer().schedule(object : TimerTask() {
                                        override fun run() {
                                            activity?.runOnUiThread(Runnable {
                                                recyclerView.adapter?.itemCount?.minus(1)?.let { recyclerView.scrollToPosition(it)};
                                            })
                                        }
                                    }, 500)
                                }
                                CLoseKeyboard()

                                itemView.findViewById<EditText>(R.id.txt_message).text = null
                                recyclerView?.adapter?.notifyDataSetChanged()
                            }
                        }
                        else{
                            img_btnsend.setImageResource(R.drawable.ic_attach_file);
                            img_btnsend.rotation=45f
                        }
                    }
                })
    }
    fun CLoseKeyboard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_page, container, false)
    }

    override fun isOnBottom(isOnBottom: Boolean) {
        onBottom = isOnBottom
    }
}
interface PositionOnBottom{
    fun isOnBottom(isOnBottom : Boolean)
}

