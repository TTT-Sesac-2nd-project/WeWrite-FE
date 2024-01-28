//package com.wewrite.android.ui.map
//
//import android.view.LayoutInflater
//import android.view.View
//import com.bumptech.glide.Glide
//import com.wewrite.android.api.model.MapResponse
//import com.wewrite.android.databinding.MapBallonLayoutBinding
//import net.daum.mf.map.api.CalloutBalloonAdapter
//import net.daum.mf.map.api.MapPOIItem
//
//class CustomBalloonAdapter(private val inflater: LayoutInflater) : CalloutBalloonAdapter {
//    val binding = MapBallonLayoutBinding.inflate(inflater)
//
//    override fun getCalloutBalloon(itemData: MapResponse.MapList): View {
//        binding.tvTitle.text = itemData.boardTitle
//        binding.tvContent.text = itemData.boardContent
//        binding.tvDate.text = itemData.boardCreatedDate
//        Glide.with(binding.root).load(itemData.boardImage).into(binding.ivImage)
//
//        return binding.root
//    }
//
//    override fun getPressedCalloutBalloon(poiItem: MapPOIItem): View? {
//        // Return null to use the default pressed balloon
//        return null
//    }
//}
