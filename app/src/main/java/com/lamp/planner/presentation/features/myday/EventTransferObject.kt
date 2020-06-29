package com.lamp.planner.presentation.features.myday

import android.os.Parcel
import android.os.Parcelable
import com.lamp.planner.domain.Constants
import com.lamp.planner.domain.Event
import com.lamp.planner.domain.utils.CalendarUtils
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
    var allDay: Int
) : Parcelable {

    val strDate: String
        get() {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return CalendarUtils.getDayInString(calendar.timeInMillis, Constants.FORMAT_TIME)
        }

    fun toEvent(): Event {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hours, minutes)
        return Event(0, name, calendar.time.time, allDay, TimeZone.getDefault().rawOffset, groupId)
    }

    fun getStrTime() = CalendarUtils.formatTime(hours, minutes)

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(groupId)
        parcel.writeString(groupName)
        parcel.writeInt(day)
        parcel.writeInt(month)
        parcel.writeInt(year)
        parcel.writeInt(hours)
        parcel.writeInt(minutes)
        parcel.writeInt(allDay)
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
