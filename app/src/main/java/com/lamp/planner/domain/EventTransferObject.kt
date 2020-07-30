package com.lamp.planner.domain

import android.os.Parcel
import android.os.Parcelable
import com.lamp.planner.domain.utils.CalendarUtils
import com.lamp.planner.presentation.features.NotifyTimeInterval
import java.util.Calendar

data class EventTransferObject(
    var name: String,
    var image: Int = -1,
    var groupId: Long,
    var groupName: String,
    var day: Int,
    var month: Int,
    var year: Int,
    var hours: Int,
    var minutes: Int,
    var allDay: Int,
    var reminderInterval: NotifyTimeInterval
) : Parcelable {

    val time: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hours, minutes, 0)
            return calendar.timeInMillis
        }
    val notifyTime: Long
        get() {
            val time =
                (reminderInterval.days * 86400 + reminderInterval.hours * 3600 + reminderInterval.minutes * 60) * 1000
            return this.time - time.toLong()
        }
    val strDate: String
        get() {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return CalendarUtils.getDayInString(calendar.timeInMillis, Constants.FORMAT_TIME)
        }

    fun getStrTime() = CalendarUtils.formatTime(hours, minutes)

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readSerializable() as NotifyTimeInterval
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeLong(groupId)
        parcel.writeString(groupName)
        parcel.writeInt(day)
        parcel.writeInt(month)
        parcel.writeInt(year)
        parcel.writeInt(hours)
        parcel.writeInt(minutes)
        parcel.writeInt(allDay)
        parcel.writeSerializable(reminderInterval)
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
