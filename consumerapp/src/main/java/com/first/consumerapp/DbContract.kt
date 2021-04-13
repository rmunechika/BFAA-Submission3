package com.first.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DbContract {

    const val AUTHORITY = "com.first.subgitfinalapp"
    const val SCHEME = "content"

    internal class GitUserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "gitusers"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR_URL = "avatar_url"
            const val FAVORITE = "isFav"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}