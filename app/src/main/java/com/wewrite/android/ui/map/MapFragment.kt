package com.wewrite.android.ui.map

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.MapResponse
import com.wewrite.android.api.repository.MapRepository
import com.wewrite.android.databinding.FragmentMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                setMarkersFromMapList()
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

    private suspend fun setMarkersFromMapList() {
        mapView.removeAllPOIItems()
        mapList = getShopList()
        Log.e("marker", mapList.toString())

        try {
            mapList?.forEachIndexed { index, mapItem ->
                val bitmap: Bitmap = withContext(Dispatchers.IO) {
                    // Glide를 사용하여 비트맵을 가져옴 (비동기로 동작)
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(mapItem.boardImage)
                        .placeholder(R.drawable.img_user_default)
                        .error(R.drawable.img_user_default)
                        .submit(120, 120) // 원하는 크기로 비트맵을 미리 가져옴
                        .get()
                }

                // 비트맵을 int 배열로 변환
                val pixels = IntArray(bitmap.width * bitmap.height)
                bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

                val marker = MapPOIItem().apply {
                    itemName = mapItem.boardTitle
                    tag = index
                    mapPoint = MapPoint.mapPointWithGeoCoord(mapItem.boardLat, mapItem.boardLng)
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageBitmap = bitmap
                    customSelectedImageBitmap = bitmap
                    isCustomImageAutoscale = true
                    setCustomImageAnchor(0.5f, 1.0f)
                }

                // 맵에 마커 추가
                mapView.addPOIItem(marker)
                Log.e("marker", marker.toString())
            }
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

}
