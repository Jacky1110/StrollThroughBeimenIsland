package com.jotangi.strollthroughbeimenisland.ui.parking

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.jotangi.strollthroughbeimenisland.R
import com.jotangi.strollthroughbeimenisland.databinding.ItemParkingBinding
import java.util.*

class ParkingItemViewHolder(val binding: ItemParkingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ParkingVO,
        listener: ParkingClickListener?,
        listen: ParkingOnClickListener?
    ) {

        binding.apply {


            if (data.parkingPhone.isNotBlank()) {
                ivParkPhone.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivParkPhone.context,
                        R.drawable.ic_base_phone
                    )
                )

                ivParkPhone.setOnClickListener {
                    listener?.onParkingPhoneClick(data)
                }
            } else {
                ivParkPhone.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivParkPhone.context,
                        R.drawable.ic_phone_grey
                    )
                )
            }

            if (data.parkingLatitude.isNotBlank()) {

                ivParkMap.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivParkMap.context,
                        R.drawable.ic_navigation
                    )
                )

                ivParkMap.setOnClickListener {

                    listen?.onParkingMapClick(data)
                }
            } else {

                ivParkMap.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        ivParkMap.context,
                        R.drawable.ic_marker_grey
                    )
                )

            }

            tvTitleNumber.text = data.parkingId
            tvParkingTitle.text = data.parkingName
            tvStopTime.text = data.parkingFee
        }
    }
}