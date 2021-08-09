package com.example.dibujosdeldia.ui.main.api

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.ActivityApiBinding


private const val EARTH = 0
private const val MARS = 1
private const val WEATHER = 2

class ApiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
        setHighlightedTab(EARTH)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })
    }

    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)

        with(binding) {
            tabLayout.getTabAt(EARTH)?.customView = null
            tabLayout.getTabAt(MARS)?.customView = null
            tabLayout.getTabAt(WEATHER)?.customView = null
        }

        when (position) {
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            WEATHER -> {
                setWeatherTabHighlighted(layoutInflater)
            }
            else -> {
                setEarthTabHighlighted(layoutInflater)
            }
        }
    }

    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        with(binding) {
            tabLayout.getTabAt(EARTH)?.customView = earth
            tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(WEATHER)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
        }
    }

    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        with(binding) {
            tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(MARS)?.customView = mars
            tabLayout.getTabAt(WEATHER)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
        }
    }

    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        with(binding) {
            tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
            tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
            tabLayout.getTabAt(WEATHER)?.customView = weather
        }
    }
}
