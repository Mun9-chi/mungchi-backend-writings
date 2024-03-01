package org.mungchi.writings.ui.controller

import lombok.RequiredArgsConstructor
import org.mungchi.writings.service.WritingsService
import org.mungchi.writings.ui.controller.dto.WritingsDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/writings")
@RestController
@RequiredArgsConstructor
class WritingsController(
    private val writingsService: WritingsService
) {

    @PostMapping("/upload")
    fun uploadFiles(@RequestParam("file") file: List<MultipartFile>): ResponseEntity<List<String>> {
        return try {
            val fileUrls = writingsService.uploadFiles(file)
            ResponseEntity.ok(fileUrls)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

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
