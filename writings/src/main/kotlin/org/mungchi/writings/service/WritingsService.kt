package org.mungchi.writings.service

import jakarta.persistence.EntityNotFoundException
import org.mungchi.writings.infra.repository.WritingsRepository
import org.mungchi.writings.ui.controller.dto.WritingsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class WritingsService(
    private val writingsRepository: WritingsRepository,
) {

    fun findWritings(): List<WritingsDto> {
        val writingsData = writingsRepository.findAll()
        return writingsData.map { WritingsDto(it) }
    }

    fun findOneWriting(writingsId: Long): WritingsDto {
        val writingsData = writingsRepository.findById(writingsId)
            .orElseThrow { EntityNotFoundException("id를 찾을수 없습니다. $writingsId") }
        return WritingsDto(writingsData)
    }

    fun deleteById(writingsId: Long) {
        return writingsRepository.deleteById(writingsId)
    }
}
