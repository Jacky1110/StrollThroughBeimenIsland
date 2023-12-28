package com.jotangi.strollthroughbeimenisland.ui.storeIntroduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentNavigationShopBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentStoreIntroductionListBinding
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationAdapter
import com.jotangi.strollthroughbeimenisland.ui.navigationShop.NavigationVO


class StoreIntroductionListFragment : BaseFragment(), StoreIntroductionClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentStoreIntroductionListBinding

    private var data = mutableListOf<StoreListVO>()
    private lateinit var storeIntroductionAdapter: StoreIntroductionAdapter

    private var storeId: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreIntroductionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupStoreIntroductionListTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initData() {
        mainViewModel.getStoreList(
            requireContext(),
            storeId
        )
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.storeIntroductionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            storeIntroductionAdapter = StoreIntroductionAdapter(
                data,
                requireContext(),
                this@StoreIntroductionListFragment
            )
            this.adapter = storeIntroductionAdapter
        }
    }

    private fun initObserver() {
        mainViewModel.storeListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            }
        }
    }

    private fun updateListView(result: List<StoreListVO>) {
        storeIntroductionAdapter.updateDataSource(result)
    }

    override fun onStoreIntroductionItemClick(vo: StoreListVO) {
        val bundle = bundleOf("storeId" to vo.storeId)

        findNavController().navigate(
            R.id.action_storeIntroductionListFragment_to_storeIntroductionListViewFragment,
            bundle
        )
    }

}