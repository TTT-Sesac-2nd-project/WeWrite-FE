package com.wewrite.android.ui.map

import BoardRepository
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.api.model.MapResponse
import com.wewrite.android.api.repository.MapRepository
import com.wewrite.android.databinding.FragmentMapBinding
import com.wewrite.android.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment : Fragment(), CalloutBalloonAdapter {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var mapRepository: MapRepository
    private lateinit var boardRepository: BoardRepository
    private var mapList: List<MapResponse.MapList>? = null
    private var selectedLatitude: Double = 0.0
    private var selectedLongitude: Double = 0.0

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
                setMapCenter()
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

    override fun getCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }

    override fun getPressedCalloutBalloon(poiItem: MapPOIItem): View? {
        // Pressed 상태의 말풍선 뷰가 필요하지 않다면 null 반환
        return null
    }

    private suspend fun getShopList(): List<MapResponse.MapList> {
        mapRepository = MapRepository.create()
        try {
            val response = mapRepository.getMapList(0)
            return response.data ?: emptyList()
        } catch (e: Exception) {
            Log.e("error", e.toString())
            throw e
        }
    }

    private fun setMapCenter() {
        val lm: LocationManager = ContextCompat.getSystemService(
            requireContext(),
            LocationManager::class.java
        )!!
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        if (userNowLocation != null) {
            selectedLatitude = userNowLocation.latitude
            selectedLongitude = userNowLocation.longitude
        }

        binding.mapContainer.apply {
            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    selectedLatitude,
                    selectedLongitude
                ), true
            )
            mapView.setZoomLevel(3, true)
        }
    }

    private suspend fun setMarkersFromMapList() {
        mapView.removeAllPOIItems()
        mapList = getShopList()

        try {
            mapList?.forEachIndexed { index, mapItem ->
                val bitmap: Bitmap = withContext(Dispatchers.IO) {
                    // Glide를 사용하여 비트맵을 가져옴 (비동기로 동작)
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(mapItem.boardImage)
                        .placeholder(R.drawable.img_user_default)
                        .error(R.drawable.img_user_default)
                        .submit(150, 150) // 원하는 크기로 비트맵을 미리 가져옴
                        .get()
                }

                val marker = MapPOIItem().apply {
                    itemName = mapItem.boardTitle
                    tag = index
                    mapPoint = MapPoint.mapPointWithGeoCoord(mapItem.boardLat, mapItem.boardLng)
                    markerType = MapPOIItem.MarkerType.CustomImage
                    customImageBitmap = bitmap
                    customSelectedImageBitmap = bitmap
                    isCustomImageAutoscale = false
                    setCustomImageAnchor(0.5f, 1.0f)
                    userObject = mapItem
                }

                mapView.addPOIItem(marker)
                Log.e("marker", marker.toString())
            }
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }

    private fun showDetailView(boardItem: BoardItem) {
        boardRepository = BoardRepository.create()
        try {
            // 코루틴 빌더를 사용하여 getOneBoard 함수를 호출
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    val boardResponse = boardRepository.getOneBoard(boardItem.boardId)
                    val boardDetailData = boardResponse.data
                    val intent = Intent(binding.root.context, DetailActivity::class.java)
                    intent.putExtra("boardDetailData", boardDetailData)
                    intent.putExtra("boardId", boardItem.boardId)
                    intent.putExtra("isBookmarked", boardItem.isBookmarked)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("boardResponse", e.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("CoroutineException", e.toString())
        }
    }
}
