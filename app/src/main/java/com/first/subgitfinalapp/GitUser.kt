package com.first.subgitfinalapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GitUser (
    var username: String? = null,
    var avatar: String? = null,
    var name: String? = null,
    var isFav: String? = null
): Parcelable