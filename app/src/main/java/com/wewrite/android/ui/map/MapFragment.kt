package com.wewrite.android.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.wewrite.android.R
import com.wewrite.android.api.model.MapResponse
import com.wewrite.android.api.repository.MapRepository
import com.wewrite.android.databinding.FragmentMapBinding
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var mapRepository: MapRepository
    private var mapList: List<MapResponse.MapList>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMapContainer()

        lifecycleScope.launch {
            try {
                // 이미지 리사이징 적용한 함수 호출
                setOneTestMarker()
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupMapContainer() {
        mapView = MapView(requireContext())
        binding.mapContainer.addView(mapView)
    }

    private suspend fun getShopList(): List<MapResponse.MapList> {
        mapRepository = MapRepository.create()
        try {
            val response = mapRepository.getMapList(0)
            Log.e("response", response.toString())
            return response.data ?: emptyList()
        } catch (e: Exception) {
            Log.e("error", e.toString())
            throw e
        }
    }

    private suspend fun setMarker() {
        mapView.removeAllPOIItems()
        mapList = getShopList()
        Log.e("marker", mapList.toString())
        try {
            mapList?.forEachIndexed { index, mapItem ->
                val marker = MapPOIItem().apply {
                    itemName = mapItem.boardTitle
                    tag = index
                    mapPoint = MapPoint.mapPointWithGeoCoord(mapItem.boardLat, mapItem.boardLng)
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageResourceId = R.drawable.img_test1
                    isCustomImageAutoscale = false
                    customImageAnchorPointOffset = MapPOIItem.ImageOffset(80, 80)
                }

                mapView.addPOIItem(marker)
                Log.e("marker", marker.toString())
            }
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

    private suspend fun setOneTestMarker() {
        Log.e("marker", "setOneTestMarker")

        // 이미지 리사이징 적용
        val resizedBitmap = ImageResizer.resizeImage(
            requireContext(),
            R.drawable.img_test2,
            24,  // 원하는 폭
            24   // 원하는 높이
        )

        var marker = MapPOIItem().apply {
            itemName = "테스트"
            tag = 0
            mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
            customImageBitmap = resizedBitmap
            markerType = MapPOIItem.MarkerType.CustomImage
            customSelectedImageBitmap = resizedBitmap
            isCustomImageAutoscale = true
            setCustomImageAnchor(0.5f, 1.0f)
        }

        binding.mapContainer.post {
            mapView.addPOIItem(marker)
        }
    }
}
