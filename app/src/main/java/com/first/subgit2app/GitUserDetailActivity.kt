package com.first.subgit2app

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.first.subgit2app.databinding.ActivityGitUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class GitUserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGitUserDetailBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usrname = intent.getStringExtra(MainActivity.EXTRA_USER) as String
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.setGitUserDetail(usrname)
        mainViewModel.getUserDetail().observe(this, { userItems ->
            if (userItems != null) {
                binding.tvDetname.text = userItems.name
                binding.tvDetusername.text = userItems.username
                Picasso.get().load(userItems.avatar).into(binding.imgItemDetailAvatar)
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = usrname
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }
}