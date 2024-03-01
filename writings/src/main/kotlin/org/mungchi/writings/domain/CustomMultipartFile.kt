package org.mungchi.writings.domain

import org.springframework.util.Assert
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class CustomMultipartFile(
    private val name: String,
    private var originalFilename: String?,
    private var contentType: String?,
    private val content: ByteArray
) : MultipartFile {

    init {
        Assert.hasLength(name, "Name must not be null")
        this.originalFilename = originalFilename ?: ""
    }

    override fun getName(): String {
        return this.name
    }

    override fun getOriginalFilename(): String? {
        return this.originalFilename
    }

    override fun getContentType(): String? {
        return this.contentType
    }

    override fun isEmpty(): Boolean {
        return this.content.isEmpty()
    }

    override fun getSize(): Long {
        return this.content.size.toLong()
    }

    @Throws(IOException::class)
    override fun getBytes(): ByteArray {
        return this.content
    }

    @Throws(IOException::class)
    override fun getInputStream(): InputStream {
        return ByteArrayInputStream(this.content)
    }

    @Throws(IOException::class, IllegalStateException::class)
    override fun transferTo(dest: File) {
        FileCopyUtils.copy(this.content, dest)
    }
}
