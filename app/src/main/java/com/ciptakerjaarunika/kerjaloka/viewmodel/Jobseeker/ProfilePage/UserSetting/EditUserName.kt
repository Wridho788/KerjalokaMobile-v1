package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.UserSetting

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.api.ManageProfileAPI
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditUsernameProfileBinding
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.ProfilePage.profilepage

class EditUserName() : Fragment() {
    private lateinit var binding : FragmentEditUsernameProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUsernameProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputTxt.setText(SessionManager(context).user?.username)
        binding.backBtn.setOnClickListener{
            back()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }
        binding.errorMessage.visibility = View.GONE

        binding.inputTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable) {
                if (!binding.inputTxt.text.toString()
                        .isNullOrEmpty() && !binding.inputTxt.text.toString()
                        .isNullOrBlank() && binding.inputTxt.text.toString() != ""
                ) {
                    binding.errorMessage.visibility = View.GONE
                }
            }
        })

        binding.saveBtn.setOnClickListener {
            if (binding.inputTxt.text.toString().isNullOrEmpty()) {
                binding.errorMessage.visibility = View.VISIBLE
            } else{
                ManageProfileAPI().JobseekerChangeUsername(binding.inputTxt.text.toString(), context){
                    if (it!= null){
                        fragmentManager?.popBackStack()
                        Toast.makeText(activity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun back(){
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction?.replace(id, profilepage(6), "Profile Page")
        fragmentTransaction?.commit()
    }
}