package com.example.phonepeclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.phonepeclone.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        
        setupViewPager()
        setupBottomNavigation()
    }
    
    private fun setupViewPager() {
        viewPager.isUserInputEnabled = false // Disable swiping
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 5
            
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment()
                    1 -> StoresFragment()
                    2 -> InsuranceFragment()
                    3 -> WealthFragment()
                    4 -> HistoryFragment()
                    else -> HomeFragment()
                }
            }
        }
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_stores -> viewPager.currentItem = 1
                R.id.navigation_insurance -> viewPager.currentItem = 2
                R.id.navigation_wealth -> viewPager.currentItem = 3
                R.id.navigation_history -> viewPager.currentItem = 4
            }
            true
        }
    }
} 