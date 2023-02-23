package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Adapter.CompReviewHistoryAdapter
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Listener.ShowModal
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.`interface`.iRefreshData
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentReviewHistoryBinding
import com.google.gson.Gson

class ReviewHistory : Fragment(), iRefreshData {
    private lateinit var binding: FragmentReviewHistoryBinding
    private var dataList: List<DataX> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        val btn_back = view.findViewById<ImageButton>(R.id.btn_back)

        UsersAPI().CompSendedReview(SortByNewest = false, context) {
            binding.spinner.visibility = GONE
            binding.emptyTxt.visibility = GONE
            if (it != null) {
                if (it.code == 210) {
                    dataList = it.data
                    refreshData()

                }

            }
        }

        btn_back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        return view
    }

    private fun refreshData() {
        UsersAPI().CompSendedReview(SortByNewest = false, context) {
            binding.spinner.visibility = GONE
            if (it != null) {
                if (it.code == 210) {
                    dataList = it.data
                    if (dataList.isNotEmpty()) {
                        binding.contentContainer.visibility = VISIBLE
                        binding.emptyTxt.visibility = GONE
                        binding.recycleRevHistory.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = assignAdapter(dataList)
                        }
                        binding.recycleRevHistory.adapter?.notifyDataSetChanged()
                    } else {
                        binding.emptyTxt.visibility = VISIBLE
                    }
                }
            }
        }

    }

    private fun assignAdapter(list: List<DataX>): CompReviewHistoryAdapter {
        return CompReviewHistoryAdapter(requireContext(), list, object : ShowModal {

            override fun showDetail(review: DataX) {
                val sheet = EditMyReview(this@ReviewHistory)
                val mBundle = Bundle()
                val reviewData = Gson().toJson(review)
                mBundle.putString(EditMyReview.EXTRA_EDIT_REVIEW, reviewData)
                sheet.arguments = mBundle
                activity?.let { it1 ->
                    sheet.show(
                        it1.supportFragmentManager,
                        "DemoBottomSheetFragment"
                    )
                }
            }

            override fun showDelete(review: DataX) {
//                val sheet = DeleteReviewModal()
//                val mBundle = Bundle()
//                val reviewData = Gson().toJson(review)
//
//                mBundle.putString(DeleteReviewModal.EXTRA_DELETE_REVIEW, reviewData)
//                sheet.arguments = mBundle
//                activity?.let { it1 ->
//                    sheet.show(
//                        it1.supportFragmentManager,
//                        "DemoBottomSheetFragment"
//                    )
//                }
                AlertDialog.Builder(context)
                    .setMessage("Yakin ingin menghapus review pada '${review.userFullName}'?")
                    .setTitle("Konfirmasi menghapus")
                    .setPositiveButton("Ya", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            UsersAPI().DeleteSendedReview(review.userRatingNo, context) {
                                dialog.dismiss()
                                dataList = dataList.toMutableList().apply {
                                    remove(review)
                                }
                                if (dataList.isNotEmpty()) {
                                    binding.contentContainer.visibility = VISIBLE

                                    binding.recycleRevHistory.apply {
                                        layoutManager = LinearLayoutManager(context)
                                        adapter = assignAdapter(dataList)
                                    }
                                    binding.recycleRevHistory.adapter?.notifyDataSetChanged()
                                } else {
                                    binding.emptyTxt.visibility = VISIBLE
                                }
                                refreshData()
                            }
                        }
                    })
                    .setNegativeButton("Batal", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            dialog.dismiss()
                        }
                    }).create().show()
            }
        })
    }

    override fun refresh() {

    }
}