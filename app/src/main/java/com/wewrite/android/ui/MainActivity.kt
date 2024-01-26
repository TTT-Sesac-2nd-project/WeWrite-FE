package com.wewrite.android.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wewrite.android.R
import com.wewrite.android.databinding.ActivityMainBinding
import com.wewrite.android.ui.group.GroupFragment
import com.wewrite.android.ui.home.HomeFragment
import com.wewrite.android.ui.map.MapFragment
import com.wewrite.android.ui.myPage.MyPageFragment
import com.wewrite.android.ui.write.WriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fbWritePost.setOnClickListener { goToWrite(it) }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() = binding.bottomNavi.apply {
        setOnNavigationItemSelectedListener { navigateToFragment(it.itemId) }
        selectedItemId = R.id.navi_home
    }


    private fun navigateToFragment(itemId: Int): Boolean {
        when (itemId) {
            R.id.navi_home -> navigateTo(HomeFragment())
            R.id.navi_map -> navigateTo(MapFragment())
            R.id.navi_group -> navigateTo(GroupFragment())
            R.id.navi_mypage -> navigateTo(MyPageFragment())
        }
        return true
    }

    private fun navigateTo(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
    fun goToWrite(view: android.view.View) {
        val intent = Intent(this, WriteActivity::class.java)
        startActivity(intent)
    }
}
