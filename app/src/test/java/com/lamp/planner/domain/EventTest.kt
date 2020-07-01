package com.lamp.planner.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class EventTest {

    @Test
    fun toEventEntity() {
        val event = Event(1L, "testName", 12345, 1, 12345, 1L)
        val eventEntity = event.toEventEntity()
        assertEquals(1L, eventEntity.id)
        assertEquals("testName", eventEntity.name)
        assertEquals(12345, eventEntity.time)
        assertEquals(1, eventEntity.allDay)
        assertEquals(12345, eventEntity.zoneOffset)
        assertEquals(1L, eventEntity.groupId)
    }
}
