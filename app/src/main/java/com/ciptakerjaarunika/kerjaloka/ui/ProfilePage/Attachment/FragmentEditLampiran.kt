package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Attachment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentLampiranBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.model.Data.Documents
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.AttachmentAdapter
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class FragmentEditLampiran(var dataList : List<Documents>?,val iRefreshData: iRefreshData?) : Fragment(), iEditLampiran {
    private lateinit var binding: FragmentLampiranBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private var document : MultipartBody.Part? = null
    private var file : File? = null
    private var documentNameError : Boolean = false
    private var processUpload = 0;

    @RequiresApi(Build.VERSION_CODES.O)

    private fun getPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val project = arrayOf(MediaStore.Files.FileColumns.DATA)
            cursor = context.contentResolver.query(contentUri, project, null, null, null)
            val columnIndex: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLampiranBinding.inflate(layoutInflater)
        val view = binding.root
        return view;
    }

    fun getPDFPath(uri: Uri?): String? {
        val cursor: Cursor? = context?.contentResolver?.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf("_data"), "_id=?",
            arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1]), null)
        val column_index = cursor?.getColumnIndexOrThrow("_data")
        cursor?.moveToFirst()
        return column_index?.let { cursor?.getString(it) }
    }

    fun SelectFile(){
        var intent = Intent(Intent.ACTION_GET_CONTENT);
        val mimeTypes = arrayOf(
            "image/*",
            "application/pdf",
            "application/msword",
            "application/vnd.ms-powerpoint",
            "application/vnd.ms-excel",
            "text/plain"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size === 1) mimeTypes[0] else "*/*"
            if (mimeTypes.isNotEmpty()) {
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
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val requestIntent = Intent.createChooser(intent, "Choose a File");
        activityResultLauncher.launch(requestIntent)
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
                val pathName = context?.let { it1 -> PathUtil().GetFilePath(fileUri, it1) }
                if (pathName != null) {
                    val file = File(pathName ?: "")
                    this.file = file
                    val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                    binding.uploadDocumentBtn.text = file.name
                    document = MultipartBody.Part.createFormData("document", file.name, requestFile)
                } else {
                    binding.uploadDocumentBtn.text = "Upload Lampiran"
                    document = null
                }
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
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ){
                this.activity?.let { it1 ->
                    ActivityCompat.requestPermissions(it1,
                        listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(), id + context!!.resources.getInteger(R.integer.LampiranUploadFile))
                };
            }
            else{
                SelectFile()
            }
        }

        binding.saveBtn.setOnClickListener {
            var processUpload = dataList?.filter { data-> data.documentFile != null }!!.size

            if(processUpload != 0) {
                dataList?.filter { data-> data.documentFile != null }!!.forEach { data ->
                        ManageProfileAPI().JobseekerUploadDocument(context, data.documentFile!!) {
                            if (it != null) {
                                if (it.documentName != null) {
                                    processUpload -= 1;
                                    data.documentFileName = it.documentName.toString()
                                    EditLampiran(processUpload)
                                } else {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
            else{
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
            else if(dataList!!.any { data-> data.documentFileName == file!!.name }){
                binding.errorTxt.text = "Tidak dapat menambahkan file yang sama"
                document = null
                file = null
                binding.documentName.text = null;
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
                file = null
                binding.uploadDocumentBtn.text = "Upload Lampiran"
                binding.documentName.text = null;
            }
        }
    }
    fun EditLampiran(totalProcess : Int){
        if(totalProcess == 0){
            ManageProfileAPI().JobseekerEditLampiran(dataList, context){
                if(it != null && context != null) {
                    Toast.makeText(context, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    back()
                }
                else{
                    Toast.makeText(context, "Terjadi kesalahan yang tidak diketahui", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun back(){
        fragmentManager?.popBackStack()
        iRefreshData!!.refresh()
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == id + context!!.resources.getInteger(R.integer.LampiranUploadFile)) {
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                SelectFile()
            }
            else{
                Toast.makeText(activity, "Perlu akses untuk upload file", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

interface iEditLampiran{
    fun delete(value : Documents)
}