package com.ciptakerjaarunika.kerjaloka.viewmodel.AkunPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentEditEmailProfileBinding


class fragment_edit_email_profile : Fragment() {
    private lateinit var binding : FragmentEditEmailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditEmailProfileBinding.inflate(layoutInflater)
        return binding.root
    }


}