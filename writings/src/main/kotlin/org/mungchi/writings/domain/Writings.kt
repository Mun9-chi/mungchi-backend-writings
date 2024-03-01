package org.mungchi.writings.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "writings")
class Writings(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writings_id")
    val id: Long,

    @Embedded
    val owner: Owner,

    val thumbnail: String,

    val content: String,

    var likes: Int,

    val isVideo: Boolean,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,
) {


}
