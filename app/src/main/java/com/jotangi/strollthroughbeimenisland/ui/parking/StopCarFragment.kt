package com.jotangi.strollthroughbeimenisland.ui.parking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jotangi.strollthroughbeimenisland.BaseFragment
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.AppBarMainBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentHomeBinding
import com.jotangi.strollthroughbeimenisland.databinding.FragmentStopCarBinding

class StopCarFragment : BaseFragment(), ParkingClickListener, ParkingOnClickListener {

    override fun getToolBar(): AppBarMainBinding = binding.toolbarInclude
    private lateinit var binding: FragmentStopCarBinding
    private var data = mutableListOf<ParkingVO>()
    private lateinit var parkingAdapter: ParkingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStopCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupStopCarTitle()
        initObserver()
        initView()
        initData()
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initData() {
        mainViewModel.getParking(requireContext())
    }

    private fun initObserver() {
        mainViewModel.parkingData.observe(viewLifecycleOwner) { result ->
            if (result != null) {

                updateListView(result)
            }
        }
    }

    private fun initRecyclerView() {
        binding.parkingRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            parkingAdapter = ParkingAdapter(
                data,
                requireContext(),
                this@StopCarFragment,
                this@StopCarFragment
            )
            this.adapter = parkingAdapter
        }
    }

    private fun updateListView(result: List<ParkingVO>) {
        parkingAdapter.updateDataSource(result)
    }

    override fun onParkingPhoneClick(vo: ParkingVO) {

        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + vo.parkingPhone))
        startActivity(intent)

    }

    override fun onParkingMapClick(vo: ParkingVO) {

        val map =
            "http://maps.google.com/maps?" + "daddr=" + vo.parkingLatitude + "," + vo.parkingLongitude
        val uri = Uri.parse(map)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}