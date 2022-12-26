package com.ciptakerjaarunika.kerjaloka.ui.InterviewPage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import com.ciptakerjaarunika.kerjaloka.model.Interview.Messages
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.incoming_call_model
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.DefaultGalleryMimes
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.DefaultSystemGalleryConfig
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.Company.company_interview_byjob
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import java.io.File
import java.util.*


class ChatPage(
    var sectionName: String,
    var sectionNo: Int?,
    val jobNo: Long?,
    val Receiver: Long,
    val logo: String?,
    val jobPosition: String?
) : Fragment(), PositionOnBottom {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hubConnection: HubConnection
    private var onBottom: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var activityResultCameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var defaultImagePicker: BasicImagePicker

    private var downloadManager: DownloadManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.visibility = View.GONE
        hubConnection = HubConnectionBuilder.create(config().portAddress + "/ws/chat").build()
        if (SessionManager(context).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
            hubConnection.start()
            hubConnection.on(
                "connected",
                { res ->
                    val userNo = SessionManager(context).user!!.userNo.toString()
                    hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                }, String::class.java
            )
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
                        mimesType = DefaultGalleryMimes.customTypes("image/*") // multiType
                    )
                )
                .subscribe { result -> onPickUriSuccess(result.uri) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun pickCamera() {
        try {
            context?.let {
                defaultImagePicker.openCamera(it)
                    .subscribe { result -> onPickUriSuccess(result.uri) }
            }
        } catch (e: Throwable) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: InterruptedException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPickUriSuccess(uri: Uri) {
        val pathName = context?.let { getPathFromUri(it, uri) }
        Log.d("Path", pathName.toString())
        if (pathName != null) {
            uploadImage(pathName)
        }
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
    private fun uploadImage(imagePath: String) {

        val file = File(imagePath)
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", file.name, requestFile)
        InterviewAPI().UploadChatPhoto(context, body) { res ->
            if (res != null) {
                if (res.code == 210) {
                    val sender = SessionManager(context).user!!.userNo.toString()
                    val receiver = listOf<Long>(Receiver)
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
                } else {
                    Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        downloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?

        val titlePage = itemView.findViewById<TextView>(R.id.title)
        titlePage.text = sectionName

        val description = itemView.findViewById<TextView>(R.id.description)
        description.text = jobPosition

        val backButton = itemView.findViewById<ImageButton>(R.id.backButton)
        val cameraButton = itemView.findViewById<ImageButton>(R.id.openCamera)

        val videoCallButton = itemView.findViewById<ImageButton>(R.id.video_call_btn)
        videoCallButton?.setOnClickListener {
            if (context != null) {
                val roomId = SessionManager(context).user?.userNo.toString() + Receiver.toString()
                val userInfo = JitsiMeetUserInfo()

                userInfo.email = SessionManager(context).user?.email
                userInfo.displayName = SessionManager(context).user?.userFullname

                if (SessionManager(context).user?.company != null) {
                    userInfo.displayName = SessionManager(context).user?.company?.companyName
                }


                val options = JitsiMeetConferenceOptions.Builder()
                    .setRoom(roomId)
                    .setUserInfo(userInfo)
                    // Settings for audio and video
                    //.setAudioMuted(true)
                    //.setVideoMuted(true)
                    .build()
                JitsiMeetActivity.launch(context, options)
                hubConnection.send(
                    "SendCall",
                    listOf<Long>(Receiver),
                    roomId,
                    SessionManager(context).user!!.userNo
                )
            }
        }

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri? = data?.data
                val pathName =
                    fileUri?.let { it1 -> context?.let { it2 -> PathUtil().GetFilePath(it1, it2) } }

                val file = File(pathName ?: "")
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("file", file.name, requestFile)
                InterviewAPI().UploadChatFile(context, body) { res ->
                    if (res != null) {
                        if (res.code == 210) {
                            val sender = SessionManager(context).user!!.userNo.toString()
                            val receiver = listOf<Long>(Receiver)
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
                        } else {
                            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        cameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1, listOf(Manifest.permission.CAMERA).toTypedArray(),
                        id + context!!.resources.getInteger(R.integer.ChatPickCamera)
                    )
                }
            } else {
                pickCamera()
            }
        }


        if (activity != null) {
            Glide.with(itemView.context)
                .load(config().portAddress + "/photo/Profile/" + logo).fitCenter()
                .into(itemView.findViewById<ImageView>(R.id.userPhoto))
        }

        backButton.setOnClickListener {
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if (isCompany) {
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
            } else {
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, InterviewPage(), "InterviewPage")
                ft.commit()
            }

//            parentFragmentManager.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            var user = SessionManager(context).user
            val isCompany = user != null && user.roleNo == 2

            if (isCompany) {
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
            } else {
                hubConnection.stop()
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, InterviewPage(), "InterviewPage")
                ft.commit()
            }
        }
        recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerViewChat)
//        view?.setOnClickListener {
//            CLoseKeyboard()
//        }
//        recyclerView.isClickable = true;
//        recyclerView.setOnClickListener{
//            Log.d("CLick", "recyle")
//            CLoseKeyboard()
//        }
        hubConnection.on(
            "connected",
            { res ->
                val userNo = SessionManager(context).user!!.userNo.toString()
                hubConnection.send("Connecting", userNo, SessionManager(context).deviceId)
                hubConnection.send("ReadSectionMessage", sectionNo.toString())

            }, String::class.java
        )

        hubConnection.on(
            "incomingCall",
            { data ->
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(id, IncomingCallPage(data), "IncomingCall")
                ft.addToBackStack("ChatPage")
                ft.commit()
            },
            incoming_call_model::class.java
        )

        hubConnection.on(
            "getmessage",
            { res: chat_data ->
                SessionManager(context).chatData = res
                Log.d("Message", res.toString())
                hubConnection.send("ReadSectionMessage", sectionNo.toString())
                activity?.runOnUiThread(Runnable {
                    recyclerView.adapter?.notifyDataSetChanged()
                    if (onBottom) {
                        recyclerView.adapter?.itemCount?.minus(1)
                            ?.let { recyclerView.scrollToPosition(it) }
                    }
                })
            },
            chat_data::class.java
        )

        var LinearLayoutManager = LinearLayoutManager(activity)
        val thisContext = this

        recyclerView.apply {
            layoutManager = LinearLayoutManager
            adapter = ChatAdapter(context, jobNo, Receiver, thisContext)
        }

        recyclerView.adapter?.itemCount?.minus(1)?.let { recyclerView.scrollToPosition(it) }

        var message = itemView.findViewById<EditText>(R.id.txt_message)
        var img_btnsend = itemView.findViewById<ImageView>(R.id.img_btnsend)
        var btn_send = itemView.findViewById<CardView>(R.id.btn_send)


//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                activity?.runOnUiThread(Runnable {
//                    recyclerView.adapter?.notifyDataSetChanged()
//                })
//            }
//        }, 0, 1000)
        message.setOnClickListener {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread(Runnable {
                        recyclerView.adapter?.itemCount?.minus(1)
                            ?.let { recyclerView.scrollToPosition(it) }
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
                                ?.let { recyclerView.scrollToPosition(it) }
                        })
                    }
                }, 300)
            }
        }
        btn_send.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1, listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                        id + context!!.resources.getInteger(R.integer.ChatUploadFile)
                    )
                }
            } else {
                var intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)

                val requestIntent = Intent.createChooser(intent, "Choose a file")
                activityResultLauncher.launch(requestIntent)
            }
        }
            message.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


                @SuppressLint("NotifyDataSetChanged")
                override fun afterTextChanged(s: Editable) {
                    if (!message.text.toString().isNullOrEmpty() && !message.text.toString()
                            .isNullOrBlank() && message.text.toString() != ""
                    ) {
                        img_btnsend.setImageResource(R.drawable.icon_send)
                        img_btnsend.rotation = -25f
                        btn_send.setOnClickListener {
                            val sender = SessionManager(context).user!!.userNo.toString()
                            val message =
                                itemView.findViewById<EditText>(R.id.txt_message).text.toString()

                            val receiver = listOf<Long>(Receiver)
                            if (!message.isNullOrEmpty() && !message.isNullOrBlank() && message != "") {
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
                                            recyclerView.adapter?.itemCount?.minus(1)
                                                ?.let { recyclerView.scrollToPosition(it) }
                                        })
                                    }
                                }, 500)
                            }
                            CLoseKeyboard()

                            itemView.findViewById<EditText>(R.id.txt_message).text = null
                            recyclerView.adapter?.notifyDataSetChanged()
                        }
                    } else {
                        img_btnsend.setImageResource(R.drawable.ic_attach_file)
                        img_btnsend.rotation = 45f
                        btn_send.setOnClickListener {
                            if (ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                activity?.let { it1 ->
                                    ActivityCompat.requestPermissions(
                                        it1,
                                        listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                                        id + context!!.resources.getInteger(R.integer.ChatUploadFile)
                                    )
                                }
                            } else {
                                var intent = Intent(Intent.ACTION_GET_CONTENT)
                                intent.type = "*/*"
                                intent.addCategory(Intent.CATEGORY_OPENABLE)

                                val requestIntent = Intent.createChooser(intent, "Choose a file")
                                activityResultLauncher.launch(requestIntent)
                            }
                        }
                    }
                }
            })
    }

    fun CLoseKeyboard() {
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

    override fun downloadFile(file: Messages) {
        Toast.makeText(context, "Downloading File...", Toast.LENGTH_SHORT).show()

        Log.d(
            "url",
            "${config().portAddress}/chat/file/download?chatMessageNo=${file.chatMessageNo}&fileName=${file.fileName}"
        )
        val request =
            DownloadManager.Request(
                Uri.parse(
                    "${config().portAddress}/chat/file/download?chatMessageNo=${file.chatMessageNo}&fileName=${file.fileName}"
                )
            )
        request.setTitle(file.fileName)
            .setDescription("File is downloading...")
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS, file.fileName
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val downloadID = downloadManager!!.enqueue(request)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == id + context!!.resources.getInteger(R.integer.ChatUploadFile)) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                var intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)

                val requestIntent = Intent.createChooser(intent, "Choose a file")
                activityResultLauncher.launch(requestIntent)
            } else {
                Toast.makeText(activity, "Perlu akses untuk upload file", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == id + context!!.resources.getInteger(R.integer.ChatPickCamera)) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                pickCamera()
            } else {
                Toast.makeText(activity, "Perlu akses untuk membuka Camera", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

interface PositionOnBottom {
    fun isOnBottom(isOnBottom: Boolean)
    fun downloadFile(fileName: Messages)
}

