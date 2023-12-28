package com.jotangi.strollthroughbeimenisland.ui.navigationShop

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


class NavigationShopFragment : BaseFragment(), NavigationClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentNavigationShopBinding
    private var data = mutableListOf<NavigationVO>()
    private lateinit var navigationAdapter: NavigationAdapter

    private var tourismId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigationShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupNavigationShopTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initObserver() {
        mainViewModel.navigationData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            }
        }
    }

    private fun initData() {
        mainViewModel.getTourism(
            requireContext(),
            tourismId
        )
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.navRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            navigationAdapter = NavigationAdapter(
                data,
                requireContext(),
                this@NavigationShopFragment
            )
            this.adapter = navigationAdapter
        }
    }


    private fun updateListView(result: List<NavigationVO>) {

        navigationAdapter.updateDataSource(result)
    }

    override fun onNavigationItemClick(vo: NavigationVO) {

        val bundle = bundleOf(
            "tourismId" to vo.tourismId,
        )

        findNavController().navigate(
            R.id.action_navigationShopFragment_to_navigationViewFragment,
            bundle
        )
    }
}