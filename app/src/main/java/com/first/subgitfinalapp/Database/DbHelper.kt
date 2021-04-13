package com.first.subgitfinalapp.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.first.subgitfinalapp.Database.DbContract.GitUserColumns.Companion.TABLE_NAME

class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "dbGituser"
        private val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_GITUSER = "CREATE TABLE $TABLE_NAME" +
                "(${DbContract.GitUserColumns.USERNAME} TEXT PRIMARY KEY NOT NULL," +
                "${DbContract.GitUserColumns.NAME} TEXT NOT NULL," +
                "${DbContract.GitUserColumns.AVATAR_URL} TEXT NOT NULL," +
                "${DbContract.GitUserColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITUSER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}