package com.wewrite.android.ui.write

import BoardRepository
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wewrite.android.api.model.BoardRequest
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.ActivityWriteBinding
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.ui.map.SelectLocationFragment
import com.wewrite.android.util.ImageConvert
import com.wewrite.android.util.PermissionCheck
import com.wewrite.android.util.PermissionCheck.Companion.openGallery
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.Calendar

const val LOCATION_PERMISSION_REQUEST_CODE = 1001

class WriteActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, SelectLocationFragment.OnLocationSelectedListener{

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery(this, setImageResult)
            } else {
                PermissionCheck.showPermissionDeniedDialog(this)
            }
        }

    private lateinit var binding: ActivityWriteBinding
    private lateinit var groupRepository: GroupRepository
    private lateinit var boardRepository: BoardRepository
    private var groupList: List<GroupResponse.GroupData>? = null
    private var boardData: BoardRequest.BoardData? = null
    private var selectedGroupId: Long? = null
    private var boardImage: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLocationButton()
        setupDate()
        setImagePlusButton()
        binding.btnBack.setOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            getGroupList()
            setGroupSpinner()
        }

        // 버튼 클릭 리스너 설정
        binding.btnComplete.setOnClickListener {
            handleSelectedGroup()
        }
    }

    private fun setGroupSpinner() {
        val groupNames = groupList?.map { it.groupName }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, groupNames.orEmpty())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerGroup.adapter = adapter
        binding.spinnerGroup.onItemSelectedListener = this
    }

    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, listener, year, month, day).show()
    }

    private fun setupDate() {
        binding.editDate.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                val monthString = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                val dayString = if (day < 10) "0$day" else "$day"

                binding.editDateText.text = "$year-$monthString-$dayString"
            }
        }
    }

    private val setImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { imageUri ->
                    // 이미지 URI를 ImageView에 설정
                    binding.ivPhoto.setImageURI(imageUri)

                    // 이미지를 MultipartBody.Part로 변환
                    lifecycleScope.launch {
                        try {
                            boardImage = ImageConvert.createImagePartFromUri(this@WriteActivity, imageUri)!!
                            // groupImage를 사용하여 다른 작업 수행
                        } catch (e: Exception) {
                            Log.e("YourActivity", "Error during image conversion", e)
                        }
                    }
                }
            }
        }

    private fun setImagePlusButton() {
        binding.ivPhoto.setOnClickListener {
            PermissionCheck.requestPermissionAndOpenGallery(
                this,
                requestPermissionLauncher,
                setImageResult
            )
        }
    }

    private suspend fun getGroupList() {
        groupRepository = GroupRepository.create()

        try {
            val response = groupRepository.getGroupList()
            groupList = response.data

            setGroupSpinner()
        } catch (e: Exception) {
            Log.e("GroupFragment", e.toString())
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedGroup = groupList?.get(position)
        selectedGroupId = selectedGroup?.groupId
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedGroupId = null
    }

    private fun handleSelectedGroup() {
        lifecycleScope.launch {
            when {
                selectedGroupId == null -> {
                    Toast.makeText(this@WriteActivity, "그룹을 선택해 주세요", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                binding.editTitle.text.toString().isEmpty() -> {
                    Toast.makeText(this@WriteActivity, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                binding.editDateText.text.toString().isEmpty() -> {
                    Toast.makeText(this@WriteActivity, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                binding.editContent.text.toString().isEmpty() -> {
                    Toast.makeText(this@WriteActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                binding.editLocName.text.toString().isEmpty() -> {
                    Toast.makeText(this@WriteActivity, "장소를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                else -> {

                    boardRepository = BoardRepository.create()
                    val title = binding.editTitle.text.toString()
                    val content = binding.editContent.text.toString()
                    val date = binding.editDateText.text.toString()
                    val locName = "서울시"
                    val boardDTO =
                            BoardRequest.BoardData(
                            title,
                            locName,
                            content,
                            selectedGroupId!!.toInt(),
                            date,
                    "37.561878",
                    "127.066622"
                        )

                    try {
                        if (boardImage != null) {
                            var response = boardRepository.createBoard(boardDTO, boardImage)
                        } else {
                            Toast.makeText(this@WriteActivity, "사진을 등록해주세요", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("통신 실패", e.toString())
                    }
                    Toast.makeText(this@WriteActivity, "글 등록이 완료되었습니다", Toast.LENGTH_SHORT).show()
                    goToMainActivity()
                    finish()
                }
            }
        }
    }

    private fun setLocationButton() {
        binding.editLocation.setOnClickListener {
            if (PermissionCheck.checkLocationPermission(this)) {
                // 위치 권한이 있으면 SetLocationFragment를 띄웁니다.
                showSetLocationFragment()
            } else {
                PermissionCheck.requestLocationPermission(this@WriteActivity, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showSetLocationFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // SetLocationFragment를 생성하고 화면에 추가합니다.
        val setLocationFragment = SelectLocationFragment()
        setLocationFragment.show(fragmentManager, "SetLocationFragment")
    }

    override fun onLocationSelected(latitude: Double, longitude: Double) {
        Log.e("위도", latitude.toString())
        Log.e("경도", longitude.toString())
    }

}
