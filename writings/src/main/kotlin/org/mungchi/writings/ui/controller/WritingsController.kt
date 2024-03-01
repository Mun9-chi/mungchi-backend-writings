package org.mungchi.writings.ui.controller

import lombok.RequiredArgsConstructor
import org.mungchi.writings.service.WritingsService
import org.mungchi.writings.ui.controller.dto.WritingsDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/writings")
@RestController
@RequiredArgsConstructor
class WritingsController(
    private val writingsService: WritingsService
) {

    @GetMapping("/{writingsId}")
    fun getWritingById(@PathVariable writingsId: Long): ResponseEntity<WritingsDto> {
        val response = writingsService.findOneWriting(writingsId)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun findAllWritings(): ResponseEntity<List<WritingsDto>> {
        val response = writingsService.findWritings()
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{writingsId}")
    fun deleteWritingById(@PathVariable writingsId: Long): ResponseEntity<Unit> {
        writingsService.deleteById(writingsId)
        return ResponseEntity.ok().build()
    }
}
