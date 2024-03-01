package org.mungchi.writings.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import jakarta.persistence.EntityNotFoundException
import marvin.image.MarvinImage
import org.marvinproject.image.transform.scale.Scale
import org.mungchi.writings.domain.CustomMultipartFile
import org.mungchi.writings.infra.repository.WritingsRepository
import org.mungchi.writings.ui.controller.dto.WritingsDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.imageio.ImageIO


@Service
@Transactional
class WritingsService(
    private val writingsRepository: WritingsRepository,
    private val amazonS3Client: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) {

    fun uploadFiles(files: List<MultipartFile>): List<String> {
        val uploadedUrls = mutableListOf<String>()

        for (file in files) {
            try {
                val fileName = file.originalFilename
                val fileUrl = "https://${bucket}.s3.ap-northeast-2.amazonaws.com/${fileName}"
                val fileFormatName = getFileFormatName(file)

                val resizedImage: MultipartFile = resizer(fileName, fileFormatName, file, 400)

                val metadata = ObjectMetadata()
                metadata.contentType = resizedImage.contentType
                metadata.contentLength = resizedImage.size

                amazonS3Client.putObject(bucket, fileName, resizedImage.inputStream, metadata)
                uploadedUrls.add(fileUrl)
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("File upload failed", e)
            }
        }
        return uploadedUrls
    }

    fun resizer(
        fileName: String?,
        fileFormat: String,
        originalImage: MultipartFile,
        width: Int
    ): MultipartFile {
        return try {
            val image = ImageIO.read(originalImage.inputStream)
            val originWidth = image.width
            val originHeight = image.height

            if (originWidth < width) return originalImage

            val imageMarvin = MarvinImage(image)
            val scale = Scale()
            scale.load()
            scale.setAttribute("newWidth", width)
            scale.setAttribute("newHeight", width * originHeight / originWidth)
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false)

            val imageNoAlpha = imageMarvin.bufferedImageNoAlpha
            val baos = ByteArrayOutputStream()
            ImageIO.write(imageNoAlpha, fileFormat, baos)
            baos.flush()

            return CustomMultipartFile(fileName!!, fileFormat, originalImage.contentType, baos.toByteArray())

        } catch (e: IOException) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 줄이는데 실패했습니다.")
        }
    }

    fun getFileFormatName(file: MultipartFile): String {
        return file.contentType!!.substring(file.contentType!!.lastIndexOf("/") + 1)
    }

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
