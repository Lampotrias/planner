package com.example.planner.domain

interface BottomSheetControl {
    fun state(): Int
    fun offset(): Float
}