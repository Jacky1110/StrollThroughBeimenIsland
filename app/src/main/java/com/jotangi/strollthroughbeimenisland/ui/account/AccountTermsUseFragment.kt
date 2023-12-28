package com.jotangi.strollthroughbeimenisland.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountRegisterBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentAccountTermsUseBinding

class AccountTermsUseFragment : BaseFragment() {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentAccountTermsUseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountTermsUseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupTermsUseTitle()
    }
}