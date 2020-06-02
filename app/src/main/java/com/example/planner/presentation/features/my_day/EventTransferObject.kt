package com.example.planner.presentation.features.my_day

import android.os.Parcel
import android.os.Parcelable
import com.example.planner.domain.Event
import com.example.planner.domain.Group
import java.util.*

data class EventTransferObject(
    var name: String,
    var groupId: Long,
    var groupName: String,
    var day: Int,
    var month: Int,
    var year: Int,
    var hours: Int,
    var minutes: Int,
    var strDate: String
):Parcelable
{
    fun toEvent(): Event {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hours, minutes)
        return Event(0, name, calendar.time.time, TimeZone.getDefault().rawOffset, groupId)
    }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong(),
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
        parcel.writeLong(groupId)
        parcel.writeString(groupName)
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