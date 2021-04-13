package com.first.subgitfinalapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.first.subgitfinalapp.Activity_and_Fragment.GitUserDetailActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listGitusers = MutableLiveData<ArrayList<GitUser>>()
    private val gitUserDetail = MutableLiveData<GitUser>()
    private val listGitUsersFollowers = MutableLiveData<ArrayList<GitUser>>()
    private val listGitUsersFollowing = MutableLiveData<ArrayList<GitUser>>()

    fun setGitUser(username: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<GitUser>()
        val url = "https://api.github.com/search/users?q=${username}"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val item = list.getJSONObject(i)
                        val usrname = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val gituser = GitUser()
                        gituser.username = usrname
                        gituser.avatar = avatar
                        listItems.add(gituser)
                    }

                    listGitusers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getGitUser(): LiveData<ArrayList<GitUser>> {
        return listGitusers
    }

    fun setGitUserDetail(username: String) {
        val client = AsyncHttpClient()
        val items = GitUser()
        val url = "https://api.github.com/users/${username}"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val usrname = responseObject.getString("login")
                    val avatar = responseObject.getString("avatar_url")
                    val name = responseObject.getString("name")
                    items.name = name
                    items.username = usrname
                    items.avatar = avatar

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
                gitUserDetail.postValue(items)
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUserDetail(): LiveData<GitUser> {
        return gitUserDetail
    }

    fun setGitUserFollowers(username: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<GitUser>()
        val url = "https://api.github.com/users/${username}/followers"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val usrname = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val gituser = GitUser()
                        gituser.username = usrname
                        gituser.avatar = avatar
                        listItems.add(gituser)
                    }

                    listGitUsersFollowers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getGitUserFollowers(): LiveData<ArrayList<GitUser>> {
        return listGitUsersFollowers
    }

    fun setGitUserFollowing(username: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<GitUser>()
        val url = "https://api.github.com/users/${username}/following"
        client.addHeader("Authorization","token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val usrname = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val gituser = GitUser()
                        gituser.username = usrname
                        gituser.avatar = avatar
                        listItems.add(gituser)
                    }

                    listGitUsersFollowing.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getGitUserFollowing(): LiveData<ArrayList<GitUser>> {
        return listGitUsersFollowing
    }
}