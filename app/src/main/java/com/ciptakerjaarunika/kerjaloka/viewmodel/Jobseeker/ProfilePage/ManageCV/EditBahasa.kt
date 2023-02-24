package com.ciptakerjaarunika.kerjaloka.viewmodel.ProfilePage

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditBahasaBinding
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.model.Data.Language
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerLanguages
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.Adapter.LanguageAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.ChooseLanguage
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.ModalEdit.ChooseScore


class EditBahasa(
    var jobseekerNo: Long,
    var data: List<JobseekerLanguages>?,
    val iRefreshData: iRefreshData
) : Fragment(), iEditBahasa {
    private lateinit var binding: FragmentEditBahasaBinding
    private var languages: List<Language> = listOf()
    private var language: Language? = null
    private var spokenScore: Int? = null
    private var writtenScore: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBahasaBinding.inflate(layoutInflater)
        val view = binding.root

        val lang = view.findViewById<TextView>(R.id.js_EditLang)

        val sLisan = view.findViewById<TextView>(R.id.edit_scorelisan)
        val sTulisan = view.findViewById<TextView>(R.id.edit_scoretulisan)

        DataAPI().GetLanguages(context) {
            if (it != null) {
                languages = it
                lang.setOnClickListener {
                    val sheet = ChooseLanguage(language?.languageNo, languages, this)
                    activity?.let { it1 ->
                        sheet.show(
                            it1.supportFragmentManager,
                            "DemoBottomSheetFragment"
                        )
                    }
                }
            }
        }


        sLisan.setOnClickListener {
            val sheet = ChooseScore("spokken", spokenScore, this@EditBahasa)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }
        sTulisan.setOnClickListener {
            val sheet = ChooseScore("written", writtenScore, this@EditBahasa)
            activity?.let { it1 ->
                sheet.show(
                    it1.supportFragmentManager,
                    "DemoBottomSheetFragment"
                )
            }
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener {
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        binding.recycleLanguage.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = data?.let { LanguageAdapter(true, it, this@EditBahasa) }
        }
        binding.addSkillBtn.setOnClickListener {
            binding.errorTxt.visibility = VISIBLE
            if (language == null) {
                binding.errorTxt.text = "Silahkan Pilih Bahasa"
            } else if (spokenScore == null) {
                binding.errorTxt.text = "Skor Lisan tidak boleh kosong"
            } else if (writtenScore == null) {
                binding.errorTxt.text = "Skor Tertulis tidak boleh kosong"
            } else {
                binding.errorTxt.visibility = GONE
                data = data?.plus(
                    JobseekerLanguages(
                        jobseekerNo,
                        language!!.languageName,
                        language!!.languageNo,
                        spokenScore!!,
                        writtenScore!!
                    )
                )
                binding.recycleLanguage.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = data?.let { LanguageAdapter(true, it, this@EditBahasa) }
                }
                binding.recycleLanguage.adapter?.notifyDataSetChanged()
                language = null
                spokenScore = null
                writtenScore = null
                binding.editScorelisan.text = null
                binding.editScoretulisan.text = null
                binding.jsEditLang.text = null
            }
        }
        binding.saveBtn.setOnClickListener {
            Log.d("bahasa", data.toString())
            if (data?.size == 0) {
                Toast.makeText(context, "Silahkan Pilih Bahasa", Toast.LENGTH_SHORT).show()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ManageProfileAPI().JobseekerEditLanguages(data, context) {
                        if (it != null) {
                            Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT)
                                .show()
                            back()
                        } else {
                            Toast.makeText(
                                activity,
                                "Terjadi kesalahan yang tidak diketahui",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun updateScoreLisan(value: Int) {
        if (binding.errorTxt.visibility == VISIBLE && spokenScore == null) {
            binding.errorTxt.visibility = GONE
        }
        spokenScore = value
        binding.editScorelisan.text = value.toString()
    }

    override fun updateScoreTulisan(value: Int) {
        if (binding.errorTxt.visibility == VISIBLE && writtenScore == null) {
            binding.errorTxt.visibility = GONE
        }
        writtenScore = value
        binding.editScoretulisan.text = value.toString()
    }

    override fun updateLanguage(value: Language) {
        if (binding.errorTxt.visibility == VISIBLE && language == null) {
            binding.errorTxt.visibility = GONE
        }
        language = value
        binding.jsEditLang.text = language?.languageName
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun removeLanguage(value: JobseekerLanguages) {
        data = data?.toMutableList()?.apply {
            remove(value)
        }

        binding.recycleLanguage.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = data?.let { LanguageAdapter(true, it, this@EditBahasa) }
        }
        binding.recycleLanguage.adapter?.notifyDataSetChanged()
    }

    private fun back() {
        fragmentManager?.popBackStack()
        iRefreshData.refresh()
    }
}

interface iEditBahasa {
    fun updateScoreLisan(value: Int)
    fun updateScoreTulisan(value: Int)
    fun updateLanguage(language: Language)
    fun removeLanguage(value: JobseekerLanguages)
}