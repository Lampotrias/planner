package com.lamp.planner.domain

import android.os.Parcel
import android.os.Parcelable

class SimpleGroupFields(val id: Long, var name: String, val image: Int, val color: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleGroupFields> {
        override fun createFromParcel(parcel: Parcel): SimpleGroupFields {
            return SimpleGroupFields(parcel)
        }

        override fun newArray(size: Int): Array<SimpleGroupFields?> {
            return arrayOfNulls(size)
        }
    }
}
