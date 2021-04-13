package com.first.subgitfinalapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.first.subgitfinalapp.Database.DbContract.GitUserColumns.Companion.TABLE_NAME
import com.first.subgitfinalapp.Database.DbContract.GitUserColumns.Companion.USERNAME
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.sql.SQLException
import kotlin.jvm.Throws

class GitUserHelper(context: Context) {

    private var  dbHelper: DbHelper = DbHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: GitUserHelper? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): GitUserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: GitUserHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$USERNAME ASC"
        )
    }
    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null
        )
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}