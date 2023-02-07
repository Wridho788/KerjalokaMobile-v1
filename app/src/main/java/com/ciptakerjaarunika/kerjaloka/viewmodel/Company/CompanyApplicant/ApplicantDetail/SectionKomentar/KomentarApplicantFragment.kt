package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CommentAPI
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.CompanyListApplicantAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentKomentarApplicantBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Adapter.KomentarAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.CommentModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionKomentar.Model.send_comment

class KomentarApplicantFragment(
    private var comment: List<CommentModel>,
    private val applicantNo : Long,
    private val jobseekerNo: Long,
    private val jobNo: Long,
) : Fragment() {
    private lateinit var binding: FragmentKomentarApplicantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKomentarApplicantBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.rvCommentApplicant.adapter?.notifyDataSetChanged()

        binding.btnSendComment.setOnClickListener {
            CommentAPI().SendCommentPost(
                context,
                jobseekerNo,
                send_comment(jobseekerNo, binding.etReportJob.text.toString())
            ) {
                if (it != null) {
                    CompanyListApplicantAPI().GetListApplicantPost(context, jobNo.toString()) {
                        res ->
                        if(res?.data != null){
                            val currentData = res.data.find {
                                data-> data.application.applicationNo == applicantNo
                            }
                            binding.etReportJob.setText("")
                            binding.rvCommentApplicant.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = KomentarAdapter(context, currentData!!.comment)
                            }
                            binding.rvCommentApplicant.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        binding.rvCommentApplicant.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = KomentarAdapter(context, comment)
        }

    }
}