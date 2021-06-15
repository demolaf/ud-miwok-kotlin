package com.aob.android.miwokvkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.aob.android.miwokvkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val adapter = CategoryAdapter(this, supportFragmentManager, 0)

        val viewPager2 = binding.viewPager

        viewPager2.adapter = adapter

        val tabLayout = binding.tabView

        tabLayout.setupWithViewPager(viewPager2)


    }




}