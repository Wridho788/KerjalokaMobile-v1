package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.DataAPI
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditInterestLayoutBinding
import com.ciptakerjaarunika.kerjaloka.model.Data.Field
import com.ciptakerjaarunika.kerjaloka.model.Data.FieldFilter
import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerFields
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Adapter.MinatAdapter
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.profilepage

class fragment_edit_interest_layout(val jobseekerFields: List<Field>?) : Fragment() {
    private lateinit var binding: FragmentEditInterestLayoutBinding
    private var fields : List<FieldFilter> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditInterestLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ActivityMainBinding.inflate(layoutInflater).bottomNavigationView.visibility = GONE

        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        DataAPI().GetFields(context) { data ->
            if (data != null) {
                fields = data.map { item ->
                    FieldFilter(item.fieldName, item.fieldNo,
                        jobseekerFields?.any { field -> field.fieldNo == item.fieldNo } == true)
                }

                binding.recycleview.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = MinatAdapter(fields)
                }

                binding.searchFilter.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val keyword = newText.toString().toLowerCase()
                        if (keyword.isNullOrEmpty()) {
                            binding.recycleview.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = MinatAdapter(fields)
                            }
                            binding.recycleview.adapter?.notifyDataSetChanged()
                        } else {
                            var temp = fields?.filter { f ->
                                f.fieldName.toLowerCase().contains(keyword)
                            }
                            binding.recycleview.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = MinatAdapter(temp!!)
                            }
                            binding.recycleview.adapter?.notifyDataSetChanged()
                        }
                        return true;
                    }
                })
            }
        }
        binding.saveBtn.setOnClickListener {
           val selected = fields.filter { data-> data.checked }.map {
               it -> JobseekerFields(SessionManager(context).user!!.userNo, it.fieldNo)
           }
            ManageProfileAPI().JobseekerEditFields(selected, context){
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
        fragmentTransaction?.replace(id, profilepage(2), "Profile Page")
        fragmentTransaction?.commit()
    }
}

