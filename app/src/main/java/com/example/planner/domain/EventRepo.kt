package com.example.planner.domain

import com.example.planner.data.database.EventEntity

interface EventRepo {
    fun save(event: EventEntity)
}