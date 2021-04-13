package com.first.subgitfinalapp.Activity_and_Fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.subgitfinalapp.*
import com.first.subgitfinalapp.Adapter.ListGitUserAdapter
import com.first.subgitfinalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListGitUserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListGitUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        adapter.setOnItemClickCallBack(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitUser) {
                Toast.makeText(this@MainActivity, "${data.username} terpilih", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, GitUserDetailActivity::class.java)
                intent.removeExtra(EXTRA_USER)
                intent.putExtra(EXTRA_USER, data.username)
                intent.removeExtra(EXTRA_AVATAR)
                intent.putExtra(EXTRA_AVATAR, data.avatar)
                startActivity(intent)
            }
        })

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = findViewById<SearchView>(R.id.inputData)
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.queryHint = resources.getString(R.string.search_hint)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setGitUser(query)
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mainViewModel.getGitUser().observe(this, { userItems ->
            if (userItems != null && userItems.isNotEmpty()) {
                adapter.setData(userItems)
                showLoading(false)
                binding.searchNotFound.visibility = View.GONE
                binding.rvMain.visibility = View.VISIBLE
            } else {
                showLoading(false)
                binding.searchNotFound.visibility = View.VISIBLE
                binding.rvMain.visibility = View.INVISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.action_notification -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}