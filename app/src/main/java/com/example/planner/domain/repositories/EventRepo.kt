package com.example.planner.domain.repositories

import com.example.planner.data.database.EventEntity

interface EventRepo {
    fun save(event: EventEntity)
}