package com.first.subgit2app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.subgit2app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListGitUserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListGitUserAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickCallBack(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitUser) {
                Toast.makeText(this@MainActivity, "${data.username} terpilih", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, GitUserDetailActivity::class.java)
                intent.putExtra(EXTRA_USER, data.username)
                startActivity(intent)
            }
        })

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

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

        mainViewModel.getUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}