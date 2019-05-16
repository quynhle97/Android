package com.example.week6.ROOM

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String
) {
    constructor() : this(null, "")
}