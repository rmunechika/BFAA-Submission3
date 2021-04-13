package com.first.subgitfinalapp.Activity_and_Fragment

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.PersistableBundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.subgitfinalapp.Adapter.ListGitUserFavAdapter
import com.first.subgitfinalapp.R
import com.first.subgitfinalapp.databinding.ActivityFavoriteBinding
import com.first.subgitfinalapp.Database.DbContract.GitUserColumns.Companion.CONTENT_URI
import com.first.subgitfinalapp.GitUser
import com.first.subgitfinalapp.Database.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: ListGitUserFavAdapter
    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListGitUserFavAdapter(this)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val mObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadDataAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)

        if (savedInstanceState == null) {
            loadDataAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<GitUser>(EXTRA_STATE)
            if (list != null) {
                adapter.mData = list
            }
        }
    }

    private fun loadDataAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredData = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursortoArrayList(cursor)
            }
            val data = deferredData.await()
            if (data.size > 0) {
                adapter.mData = data
            } else {
                adapter.mData = ArrayList()
                Toast.makeText(this@FavoriteActivity, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.mData)
    }

    override fun onResume() {
        super.onResume()
        loadDataAsync()
    }
}