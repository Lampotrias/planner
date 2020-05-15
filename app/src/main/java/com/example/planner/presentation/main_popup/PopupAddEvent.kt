package com.example.planner.presentation.main_popup

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.example.planner.R

//
//class PopupAddEvent(
//    private val anchorView: View,
//    private val responseEventPopupCallback: ResponseEventPopupCallback) {
//
//    private val popupWindow: PopupWindow
//    private val binding: PopupAddEventBinding
//    private val eventAddPopupResult = EventAddPopupResult("")
//
//    init {
//        val inflater =
//            anchorView.context.getSystemService(LAYOUT_INFLATER_SERVICE) as? LayoutInflater
//        binding = PopupAddEventBinding.inflate(inflater!!)
//
//        popupWindow = PopupWindow(
//            binding.root,
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            true
//        )
//    }
//
//    fun show() {
//
//        prepareChildViews()
//        //popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
//        popupWindow.animationStyle = R.style.popup_window_animation_slide
//        popupWindow.showAsDropDown(anchorView, 1, 1)
//        binding.root.setOnTouchListener { _, _ ->
//            responseEventPopupCallback.close()
//            popupWindow.dismiss()
//            return@setOnTouchListener true
//        }
//        binding.card.setOnTouchListener{ _, _ -> return@setOnTouchListener true }
//    }
//
//    private fun prepareChildViews() {
//        binding.timeEvent.setOnClickListener {showCalendar()}
//        binding.submitButton.alpha = 0.3f
//        binding.nameEvent.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {}
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                binding.nameEvent.text?.isNotEmpty()?.let { enableSubmit(it) }
//            }
//        })
//    }
//
//    private fun showCalendar() {
////        val calendarPopup =
////            PopupCalendar(
////                view = anchorView,
////                responseCalendarCallback = object :
////                    PopupCalendar.ResponseCalendarCallback {
////                    override fun calendarResult(result: PopupCalendar.CalendarResult) {
////                        //apply calendar popup to main window
////                        eventAddPopupResult.calendarResult = result
////                    }
////                })
//        //calendarPopup.show()
//    }
//
//    fun enableSubmit(enable: Boolean) {
//        if (enable) {
//            binding.submitButton.apply() {
//                setOnClickListener {
//                    saveAction()
//                    popupWindow.dismiss()
//                }
//                alpha = 1f
//            }
//        } else {
//            binding.submitButton.apply() {
//                setOnClickListener {}
//                alpha = 0.3f
//            }
//        }
//    }
//
//    private fun saveAction() {
//        eventAddPopupResult.name = binding.nameEvent.toString()
//        responseEventPopupCallback.result(eventAddPopupResult)
//    }
//
//    interface ResponseEventPopupCallback{
//        fun result(result: EventAddPopupResult)
//        fun close()
//    }
//
//    data class EventAddPopupResult(var name: String){
//        //var calendarResult: PopupCalendar.CalendarResult? = null
//    }
//}