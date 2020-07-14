package com.lamp.planner.presentation.features.groupproperty

interface GroupPropertyBottom {
    fun setCallback(callback: OnClickBottomButton)
    fun setSelectValue(value: Int)
    fun slideUp()
    fun slideDown()

    interface OnClickBottomButton {
        fun clickPalette()
        fun clickImage()
        fun clickEdit()
        fun clickDelete()
        fun clickBookmark()
    }
}
