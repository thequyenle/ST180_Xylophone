package com.xylophone.data.model

data class Recording(
    val id: String,                    // Unique ID
    val name: String,                  // Tên recording
    val duration: Long,                // Tổng thời gian (milliseconds)
    val notes: List<RecordedNote>,     // Danh sách các nốt đã ghi
    val createdAt: Long,               // Thời điểm tạo
    val instrumentName: String = "PIANO"  // Instrument được dùng khi record (default: PIANO cho backward compatibility)
)
