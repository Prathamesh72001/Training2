package com.example.training2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.training2.Fragments.FavouriteFragment
import com.example.training2.Fragments.InterestingFragment
import com.example.training2.Fragments.RecentFragment
import com.example.training2.R
import com.google.android.material.tabs.TabLayout

class MainActivity6 : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        viewPager=findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter

        tabLayout=findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setText("Recent")
        tabLayout.getTabAt(1)!!.setText("Interesting")
        tabLayout.getTabAt(2)!!.setText("Favourite")

        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem=tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                viewPager.currentItem=position
                tabLayout.setScrollPosition(position,0f,true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        viewPager.setOffscreenPageLimit(3)
    }

    override fun onBackPressed() {
        if(viewPager.currentItem==1) {
            super.onBackPressed()
        }
        else{
            viewPager.setCurrentItem(1)
        }
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            if(position==0){
                return RecentFragment()
            }
            else if(position==1){
                return InterestingFragment()
            }
            else{
                return FavouriteFragment()
            }
        }
    }
}