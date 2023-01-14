package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Attachment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditlampiranUploadVaksinBinding
import com.ciptakerjaarunika.kerjaloka.enum.DocumentType
import com.ciptakerjaarunika.kerjaloka.model.Data.CheckDocument
import com.ciptakerjaarunika.kerjaloka.utils.PathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class fragment_editlampiran_upload_vaksin(var dataVaccine : List<CheckDocument>, val iRefreshData: iRefreshData) : Fragment() {
    private lateinit var binding : FragmentEditlampiranUploadVaksinBinding
    private lateinit var uploadVaccine1 : ActivityResultLauncher<Intent>
    private lateinit var uploadVaccine2 : ActivityResultLauncher<Intent>
    private lateinit var uploadVaccine3 : ActivityResultLauncher<Intent>
    private var requestIntent : Intent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditlampiranUploadVaksinBinding.inflate(layoutInflater)
        return binding.root
    }

    fun UploadVaccine(vaccine : Int, documentType: Int, activityResult: ActivityResult) {
        if (activityResult.resultCode == Activity.RESULT_OK && activityResult.data != null) {
            val data = activityResult.data
            val fileUri: Uri? = data?.data
            val pathName = fileUri?.let { it1 -> context?.let { it2 -> PathUtil().GetFilePath(it1, it2) } }

            val file : File? = File(pathName ?: "")
            if(file != null) {
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("file", file.name, requestFile)
                val currentVaccine = dataVaccine.find { data -> data.documentType == documentType }

                if (currentVaccine != null) {
                    AlertDialog.Builder(context)
                        .setMessage("Yakin ingin mengganti Dokumen Vaksin-${vaccine}?")
                        .setTitle("Konfirmasi ganti dokumen vaksin")
                        .setPositiveButton("Ya", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                ManageProfileAPI().JobseekerDeleteVaccine(vaccine, context) { del ->
                                    if (del != null) {
                                        dataVaccine = dataVaccine?.toMutableList()?.apply {
                                            remove(dataVaccine.find { data -> data.documentType == documentType })
                                        }!!
                                        refreshView()
                                        ManageProfileAPI().UploadVaccine(
                                            vaccine,
                                            body,
                                            context
                                        ) { upl ->
                                            if (upl != null && upl.code == 210 && upl.data != null) {
                                                dataVaccine += upl.data
                                                refreshView()
                                                dialog.dismiss()
                                            } else {
                                                if (upl != null) {
                                                    Toast.makeText(
                                                        context,
                                                        upl.message,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Terjadi kesalahan yang tidak diketahui",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Batal", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                dialog.dismiss()
                            }
                        }).create().show()
                } else {
                    ManageProfileAPI().UploadVaccine(vaccine, body, context) { upl ->
                        if (upl != null && upl.code == 210 && upl.data != null) {
                            dataVaccine += upl.data
                            refreshView()
                        } else {
                            if (upl != null) {
                                Toast.makeText(context, upl.message, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Terjadi kesalahan yang tidak diketahui",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
       }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        refreshView()

        uploadVaccine1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            UploadVaccine(1, DocumentType.Vaccine1.value, it)
        }
        uploadVaccine2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            UploadVaccine(2, DocumentType.Vaccine2.value, it)
        }
        uploadVaccine3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            UploadVaccine(3, DocumentType.Vaccine3.value, it)
        }
    }
    fun deleteDoc(vaccine : Int, documentType: Int){
        AlertDialog.Builder(context)
            .setMessage("Yakin ingin menghapus Dokumen Vaksin-${vaccine}?")
            .setTitle("Konfirmasi menghapus")
            .setPositiveButton("Ya", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    ManageProfileAPI().JobseekerDeleteVaccine(vaccine, context){
                        if(it != null){
                            dataVaccine = dataVaccine?.toMutableList()?.apply {
                                remove(dataVaccine.find { data-> data.documentType == documentType })
                            }!!
                            refreshView()
                            dialog.dismiss()
                        }
                    }
                }
            })
            .setNegativeButton("Batal", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                }
            }).create().show()
    }
    fun refreshView(){
        var intent = Intent(Intent.ACTION_GET_CONTENT);
        val mimeTypes = arrayOf(
            "application/pdf",
            "image/*",
        )
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
        requestIntent = Intent.createChooser(intent, "Choose a file");

        val vaccine1 = dataVaccine.find { data-> data.documentType == DocumentType.Vaccine1.value }

        if(vaccine1 != null){
            binding.delete1Btn.visibility = VISIBLE
            binding.vaccine1Txt.text = vaccine1.documentFileName
            binding.delete1Btn.setOnClickListener {
                deleteDoc(1, vaccine1.documentType)
            }
        }
        else{
            binding.delete1Btn.visibility = GONE
            binding.vaccine1Txt.text = "Upload"
        }

        val vaccine2 = dataVaccine.find { data-> data.documentType == DocumentType.Vaccine2.value }
        if(vaccine2 != null){
            binding.delete2Btn.visibility = VISIBLE
            binding.vaccine2Txt.text = vaccine2.documentFileName
            binding.delete2Btn.setOnClickListener {
                deleteDoc(2, vaccine2.documentType)
            }
        }
        else{
            binding.delete2Btn.visibility = GONE
            binding.vaccine2Txt.text = "Upload"
        }

        val vaccine3 = dataVaccine.find { data-> data.documentType == DocumentType.Vaccine3.value }
        if(vaccine3 != null){
            binding.delete3Btn.visibility = VISIBLE
            binding.vaccine3Txt.text = vaccine3.documentFileName
            binding.delete3Btn.setOnClickListener {
                deleteDoc(3, vaccine3.documentType)
            }
        }
        else{
            binding.delete3Btn.visibility = GONE
            binding.vaccine3Txt.text = "Upload"
        }

        binding.btnUnggahVaksinPertama.setOnClickListener{
            checkPermission( context!!.resources.getInteger(R.integer.UploadVaccine) + 1)
        }
        binding.btnUnggahVaksinKedua.setOnClickListener{
            checkPermission( context!!.resources.getInteger(R.integer.UploadVaccine) + 2)
        }
        binding.btnUnggahVaksinKetiga.setOnClickListener{
            checkPermission( context!!.resources.getInteger(R.integer.UploadVaccine) + 3)
        }
    }

    fun checkPermission(requestCode: Int){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ){
            this.activity?.let { it1 ->
                ActivityCompat.requestPermissions(it1,
                    listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    requestCode)
            };
        }
        else{
            when(requestCode){
                context!!.resources.getInteger(R.integer.UploadVaccine) + 1 ->{
                    uploadVaccine1.launch(requestIntent)
                }
                context!!.resources.getInteger(R.integer.UploadVaccine) + 2 ->{
                    uploadVaccine2.launch(requestIntent)
                }
                context!!.resources.getInteger(R.integer.UploadVaccine) + 3 ->{
                    uploadVaccine3.launch(requestIntent)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == id + R.integer.UploadVaccine+1 ||
            requestCode == id + R.integer.UploadVaccine+2 ||
            requestCode == id + R.integer.UploadVaccine+3 ) {
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                var intent = Intent(Intent.ACTION_GET_CONTENT);
                intent.type = "*/*";
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                val requestIntent = Intent.createChooser(intent, "Choose a file");

                when(requestCode){
                    context!!.resources.getInteger(R.integer.UploadVaccine) + 1 ->{
                        uploadVaccine1.launch(requestIntent)
                    }
                    context!!.resources.getInteger(R.integer.UploadVaccine) + 2 ->{
                        uploadVaccine2.launch(requestIntent)
                    }
                    context!!.resources.getInteger(R.integer.UploadVaccine) + 3 ->{
                        uploadVaccine3.launch(requestIntent)
                    }
                }
            }
            else{
                Toast.makeText(activity, "Perlu akses untuk upload file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun back(){
//        val fragmentTransaction = parentFragmentManager.beginTransaction()
//        fragmentTransaction?.replace(id, profilepage(0), "Profile Page")
//        fragmentTransaction?.commit()
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
    }

}