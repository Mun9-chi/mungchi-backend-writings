package org.mungchi.writings.ui.controller.dto

import org.mungchi.writings.domain.Writings
import java.time.LocalDateTime

data class WritingsDto(
    val id: Long,
    val nickName: String,
    val imageUrl: String,
    val content: String,
    var likes: Int,
    val isVideo: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

) {
    constructor(writings: Writings) : this(
        writings.id,
        writings.owner.nickName,
        writings.owner.imageUrl,
        writings.content,
        writings.likes,
        writings.isVideo,
        writings.createdAt,
        writings.updatedAt
    )
}

