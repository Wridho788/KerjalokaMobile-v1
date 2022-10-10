package com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.ManageJobPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Companyjobdetail.iBasicInfoPage
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentDescriptionJobBinding

class DescriptionPage(val value : String?, val updateData : iBasicInfoPage) : Fragment() {
    private lateinit var binding: FragmentDescriptionJobBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionJobBinding.inflate(layoutInflater)
        val view = binding.root

        if(value != null){
            binding.descriptionTxt.setText(value)
        }
        binding.descriptionTxt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    updateData.updateJobDescription(binding.descriptionTxt.text.toString())
                }
        })
        return view
    }
}