package com.example.planner.presentation.my_day

import android.os.Parcel
import android.os.Parcelable

data class EventTransferObject(
    var name: String,
    var day: Int,
    var month: Int,
    var year: Int,
    var hours: Int,
    var minutes: Int,
    var strDate: String
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(day)
        parcel.writeInt(month)
        parcel.writeInt(year)
        parcel.writeInt(hours)
        parcel.writeInt(minutes)
        parcel.writeString(strDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventTransferObject> {
        override fun createFromParcel(parcel: Parcel): EventTransferObject {
            return EventTransferObject(parcel)
        }

        override fun newArray(size: Int): Array<EventTransferObject?> {
            return arrayOfNulls(size)
        }
    }
}