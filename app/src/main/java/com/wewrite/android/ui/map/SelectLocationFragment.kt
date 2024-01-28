package com.wewrite.android.ui.map

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.wewrite.android.databinding.FragmentSelectLocationBinding
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SelectLocationFragment : DialogFragment() {

    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var mapView: MapView
    private lateinit var currentLocationMarker: MapPOIItem

    // 위치를 선택한 결과를 액티비티로 전달하기 위한 리스너
    interface OnLocationSelectedListener {
        fun onLocationSelected(latitude: Double, longitude: Double)
    }

    // 액티비티로 전달할 위치 정보
    private var selectedLatitude: Double = 0.0
    private var selectedLongitude: Double = 0.0

    // 위치 선택 결과를 받을 리스너
    private var locationListener: OnLocationSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            mapView = MapView(requireContext())
            binding.mapContainer.addView(mapView)
            // 현재 위치 가져오기
            getCurrentLocation()

            // 마커 드래그 이벤트 설정
            mapView.setPOIItemEventListener(markerDragListener)
        }

        // "확인" 버튼 클릭 시의 동작 구현
        binding.btnSelect.setOnClickListener {
            locationListener?.onLocationSelected(selectedLatitude, selectedLongitude)
            dismiss()
        }
    }

    // 위치 선택 결과를 받을 리스너를 설정
    fun setOnLocationSelectedListener(listener: OnLocationSelectedListener) {
        this.locationListener = listener
    }

    override fun onResume() {
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onResume()
    }

    private fun getCurrentLocation() {
        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading  //이 부분

        val lm: LocationManager = getSystemService(requireContext(), LocationManager::class.java)!!
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        if (userNowLocation != null) {
            selectedLatitude = userNowLocation.latitude
            selectedLongitude = userNowLocation.longitude
        }
        MapPoint.mapPointWithGeoCoord(selectedLatitude, selectedLongitude)
        addMarker(selectedLatitude, selectedLongitude)
        Log.e("getCurrentLocation", "latitude: $selectedLatitude, longitude: $selectedLongitude")
    }

    private fun addMarker(latitude: Double, longitude: Double) {
        currentLocationMarker = MapPOIItem()
        currentLocationMarker.apply {
            itemName = "현재 위치"
            mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            markerType = MapPOIItem.MarkerType.BluePin
            isDraggable = true
        }

        binding.mapContainer.post {
            mapView.addPOIItem(currentLocationMarker)
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
        }
    }

    private val markerDragListener = object : MapView.POIItemEventListener {
        override fun onPOIItemSelected(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            // Do nothing
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            // Do nothing
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            mapPOIItem: MapPOIItem?,
            calloutBalloonButtonType: MapPOIItem.CalloutBalloonButtonType?
        ) {
            // Do nothing
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, mapPOIItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커가 드래그되었을 때 호출되는 콜백
            mapPoint?.let {
                selectedLatitude = it.mapPointGeoCoord.latitude
                selectedLongitude = it.mapPointGeoCoord.longitude
            }
        }
    }
}
