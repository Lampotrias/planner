package com.example.planner.presentation.my_day

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planner.data.database.EventEntity
import com.example.planner.domain.EventRepo
import com.example.planner.presentation.main_popup.PopUpClass
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MayDayViewModel @Inject constructor(private val repo: EventRepo) : ViewModel() {

    //    private val _dateField: MutableLiveData<String> = MutableLiveData("")
//    private val _timeField: MutableLiveData<String> = MutableLiveData("")
    private val _error: MutableLiveData<String> = MutableLiveData("")
    val error: LiveData<String> = _error
//    val dateField: LiveData<String> = _dateField
//    val timeField: LiveData<String> = _timeField

//    fun setTime(hourOfDay: Int, minute: Int) {
//        _timeField.postValue("$hourOfDay:$minute")
//    }
//    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
//        val m = month + 1
//        _dateField.postValue("$dayOfMonth-$m-$year")
//    }
//    fun saveEventButton() {
//        val date = getValidDate(_dateField.value, _timeField.value)
//
//        if (date > 0) {
//            val event = EventEntity(1, "11", 1, 1, 1, 1, 1)
//            Timber.i(date.toString())
//            repo.save(event)
//        } else {
//            _error.postValue("Некорреткная дата")
//        }
//    }

    fun fabClick(v: View) {
        val popup = PopUpClass()
        popup.showPopupWindow(v)
    }

//    private fun getValidDate(date: String?, time: String?): Long {
//        if (date == null || time == null)
//            return 0
//
//        val format = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
//        val inputDate: Date?
//        val curDate = Date()
//
//        inputDate = try {
//            format.parse("$date $time")
//        } catch (e: Exception) {
//            null
//        }
//
//        return if (inputDate == null || inputDate.before(curDate))
//            0
//        else
//            inputDate.time
//    }
}