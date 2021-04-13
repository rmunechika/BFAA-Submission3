package com.first.subgitfinalapp.Database

import android.database.Cursor
import com.first.subgitfinalapp.GitUser

object MappingHelper {
    fun mapCursortoArrayList(cursor: Cursor?): ArrayList<GitUser> {
        val list = ArrayList<GitUser>()

        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.AVATAR_URL))
                val favorite = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.FAVORITE))

                list.add(
                    GitUser(
                        username, avatar, name, favorite
                )
                )
            }
        }
        return list
    }
}