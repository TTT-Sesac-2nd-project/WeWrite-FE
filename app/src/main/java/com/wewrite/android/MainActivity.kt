package com.wewrite.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wewrite.android.databinding.ActivityMainBinding
import com.wewrite.android.group.GroupFragment
import com.wewrite.android.home.HomeFragment
import com.wewrite.android.map.MapFragment
import com.wewrite.android.myPage.MyPageFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // 뷰 바인딩
        setContentView(binding.root)

        binding.bottomNavi.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navi_home -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit()
                }
                R.id.navi_map -> {
                    val boardFragment = MapFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, boardFragment).commit()
                }
                R.id.navi_group -> {
                    val settingFragment = GroupFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, settingFragment).commit()
                }
                R.id.navi_mypage -> {
                    val settingFragment = MyPageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, settingFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.navi_home
        }
    }

    //네
}