package com.example.planner.domain

import android.os.Parcel
import android.os.Parcelable
import com.example.planner.data.database.GroupEntity

data class Group(
    val id: Long,
    var name: String,
    var picture: Int,
    var color: String,
    var sort: Int,
    var default: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    fun toGroupEntity() = GroupEntity(id, name, picture, color, sort, default)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(picture)
        parcel.writeString(color)
        parcel.writeInt(sort)
        parcel.writeByte(if (default) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel): Group {
            return Group(parcel)
        }

        override fun newArray(size: Int): Array<Group?> {
            return arrayOfNulls(size)
        }
    }

}