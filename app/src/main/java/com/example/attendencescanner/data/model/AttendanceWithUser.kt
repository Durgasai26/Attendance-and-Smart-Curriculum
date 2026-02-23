package com.example.attendencescanner.data.model

data class AttendanceWithUser(
    val id: Long,
    val studentId: Long,
    val studentName: String,
    val className: String,
    val date: String,
    val time: String
)


