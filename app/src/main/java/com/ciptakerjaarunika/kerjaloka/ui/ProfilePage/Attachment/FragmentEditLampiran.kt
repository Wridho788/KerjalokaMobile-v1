package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentLampiranBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.AttachmentAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import com.qingmei2.rximagepicker_extension.utils.PathUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class FragmentEditLampiran(var dataList : List<Documents>?) : Fragment(), iEditLampiran {
    private lateinit var binding: FragmentLampiranBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var document : MultipartBody.Part? = null
    private var file : File? = null
    private var documentNameError : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLampiranBinding.inflate(layoutInflater)
        val view = binding.root
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleview.apply {
            layoutManager   = LinearLayoutManager(activity)
            adapter = dataList?.let { AttachmentAdapter(it, this@FragmentEditLampiran) }
        }
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val data = it.data
                val fileUri: Uri = data!!.data!!
                val pathName = context?.let { it2 -> PathUtil().getRealPath(it2, fileUri) }

                val file = File(pathName?:"")
                this.file = file
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                binding.uploadDocumentBtn.text = file.name
                document = MultipartBody.Part.createFormData("document", file.name, requestFile)
            }
        }
        binding.documentName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable) {
                if(!binding.documentName.text.toString().isNullOrEmpty()){
                    if(documentNameError){
                    binding.errorTxt.visibility = GONE}
                }
            }
        })
        binding.uploadDocumentBtn.setOnClickListener {

            var intent = Intent(Intent.ACTION_GET_CONTENT);
            val mimeTypes = arrayOf(
                "image/*",
                "application/pdf",
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/vnd.ms-excel",
                "text/plain"
            )
//            intent.type = "image/*|application/pdf|application/msword|application/vnd.ms-powerpoint|application/vnd.ms-excel|text/plain"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.type = if (mimeTypes.size === 1) mimeTypes[0] else "*/*"
                if (mimeTypes.size > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                }
            } else {
                var mimeTypesStr = ""
                for (mimeType in mimeTypes) {
                    mimeTypesStr += "$mimeType|"
                }
                intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
            }

            intent.addCategory(Intent.CATEGORY_OPENABLE);

            val requestIntent = Intent.createChooser(intent, "Choose a File");
            activityResultLauncher.launch(requestIntent)
        }

        binding.saveBtn.setOnClickListener {
            var processUpload = dataList?.filter { data-> data.documentFile != null }!!.size
            dataList?.forEach {data ->
                if(data.documentFile != null){
                    ManageProfileAPI().JobseekerUploadDocument(context, data.documentFile!!){
                        if(it != null){
                            processUpload -= 1;
                            data.documentFileName = it.documentName.toString()
                            EditLampiran(processUpload)
                        }
                    }
                }
                EditLampiran(processUpload)
            }
        }
        binding.addDocumentBtn.setOnClickListener {
            binding.errorTxt.visibility = VISIBLE
            documentNameError = false

            if(binding.documentName.text.isNullOrEmpty()){
                documentNameError = true
                binding.errorTxt.text = "Judul Dokumen tidak boleh kosong"
            }
            else if(document == null){
                binding.errorTxt.text = "Upload lampiran terlebih dahulu"
            }
            else {
                binding.errorTxt.visibility = GONE
                dataList = dataList?.plus(
                    Documents(
                        file!!.name,
                        binding.documentName.text.toString(),
                        null,
                        DocumentType.CV.value,
                        SessionManager(context).user!!.userNo,
                        document
                    )
                )
                binding.recycleview.apply {
                    layoutManager   = LinearLayoutManager(activity)
                    adapter = dataList?.let { AttachmentAdapter(it, this@FragmentEditLampiran) }
                }
                binding.recycleview.adapter?.notifyDataSetChanged()

                document = null
                binding.uploadDocumentBtn.text = "Upload Lampiran"
                binding.documentName.text = null;
            }
        }

    }
    fun EditLampiran(totalProcess : Int){
        if(totalProcess == 0){
            ManageProfileAPI().JobseekerEditLampiran(dataList, context){
                if(it != null) {
                    Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    back()
                }
                else{
                    Toast.makeText(activity, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(3), "Profile Page")
        fragmentTransaction?.commit()
    }

    override fun delete(value: Documents) {
        dataList = dataList?.toMutableList()?.apply {
            remove(value)
        }
        binding.recycleview.apply {
            layoutManager   = LinearLayoutManager(activity)
            adapter = dataList?.let { AttachmentAdapter(it, this@FragmentEditLampiran) }
        }
        binding.recycleview.adapter?.notifyDataSetChanged()
    }
}

interface iEditLampiran{
    fun delete(value : Documents)
}