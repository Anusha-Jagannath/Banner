package com.example.bannerviewpager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.models.SlideModel
import com.example.bannerviewpager.adapter.BannerViewPagerAdapter
import com.example.bannerviewpager.databinding.ActivityMainBinding
import com.example.bannerviewpager.model.ImageItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imagesList: ArrayList<ImageItem>
    private lateinit var adapter: BannerViewPagerAdapter
    private lateinit var dots: Array<ImageView>
    private lateinit var params: LinearLayout.LayoutParams
    private lateinit var handler: Handler
    val imageList = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imagesList = arrayListOf()
        handler = Handler(Looper.getMainLooper())
        getData()
        getSliderData()
        setUpAdapter()
        createDots()
        initClickListeners()
        setUpMarginTranformation()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getData() {
        imagesList.add(ImageItem(R.drawable.n1))
        imagesList.add(ImageItem(R.drawable.nature))
        imagesList.add(ImageItem(R.drawable.nature1))
        imagesList.add(ImageItem(R.drawable.nature2))
    }

    private fun setUpAdapter() {
        adapter = BannerViewPagerAdapter(imagesList)
        binding.viewPager.adapter = adapter
    }

    private fun createDots() {
        dots = Array<ImageView>(imagesList.size) { ImageView(this) }
        params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }
        for (dot in dots) {
            dot.setImageResource(R.drawable.inactive_dot)
            binding.linearLayout.addView(dot, params)
        }

        dots[0].setImageResource(R.drawable.active_dot)
    }

    private fun initClickListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeColor(position % 4)
                if (position == imagesList.size - 1) {
                    imagesList.addAll(imagesList)
                    adapter.notifyDataSetChanged()
                }
                handler.postDelayed(runnable, 5000)

            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun changeColor(position: Int) {
        dots.forEach {
            it.setImageResource(R.drawable.inactive_dot)
        }

        dots[position].setImageResource(R.drawable.active_dot)
    }


    private val runnable: Runnable = Runnable {
        binding.viewPager.currentItem += 1
    }

    private fun setUpMarginTranformation() {

        val transformer = MarginPageTransformer(10)
        binding.viewPager.setPageTransformer(transformer)
        binding.viewPager.offscreenPageLimit = 4
        val recyclerview = binding.viewPager.getChildAt(0) as RecyclerView
        recyclerview.clipToPadding = false
        recyclerview.setPadding(60, 0, 60, 0)
    }

    private fun getSliderData() {
        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.startSliding(3000)
    }
}