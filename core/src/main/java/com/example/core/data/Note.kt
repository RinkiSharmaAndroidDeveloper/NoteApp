package com.example.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//parcelize
@Parcelize
data class Note(
    var title: String,
    var content: String,
    var creationTime: Long,
    var updateTime: Long,
    var id: Long = 0,
    var wordCount: Int = 0
) : Parcelable