package com.jotangi.strollthroughbeimenisland.ui.goodFood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentNorthGateGoodFoodListBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentStoreIntroductionListBinding
import com.jotangi.strollthroughbeimenisland.ui.storeIntroduction.StoreIntroductionAdapter
import com.jotangi.strollthroughbeimenisland.ui.storeIntroduction.StoreListVO


class NorthGateGoodFoodListFragment : BaseFragment(), GoodFoodClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentNorthGateGoodFoodListBinding

    private var data = mutableListOf<GoodFoodListVO>()
    private lateinit var northGateGoodFoodAdapter: NorthGateGoodFoodAdapter
    private var foodId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNorthGateGoodFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupGoodFoodListTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initData() {
        mainViewModel.getFoodList(
            requireContext(),
            foodId
        )
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.northGateGoodFoodRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            northGateGoodFoodAdapter = NorthGateGoodFoodAdapter(
                data,
                requireContext(),
                this@NorthGateGoodFoodListFragment
            )
            this.adapter = northGateGoodFoodAdapter
        }
    }

    private fun initObserver() {
        mainViewModel.goodFoodListData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                updateListView(result)
            }
        }
    }

    private fun updateListView(result: List<GoodFoodListVO>) {
        northGateGoodFoodAdapter.updateDataSource(result)
    }

    override fun onGoodFoodItemClick(vo: GoodFoodListVO) {
        val bundle = bundleOf("foodId" to vo.foodId)

        findNavController().navigate(
            R.id.action_northGateGoodFoodListFragment_to_northGateGoodFoodListViewFragment,
            bundle
        )
    }
}