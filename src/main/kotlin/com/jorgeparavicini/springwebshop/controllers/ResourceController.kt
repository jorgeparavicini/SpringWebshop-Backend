package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.config.UPLOAD_DIRECTORY
import com.jorgeparavicini.springwebshop.dto.ResourceCreatedDTO
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import javax.websocket.server.PathParam

@RestController
@RequestMapping(path = ["api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ResourceController {

    @PostMapping("img", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasAuthority('create:img')")
    fun upload(@RequestPart file: MultipartFile, @RequestParam id: String?): ResourceCreatedDTO {
        val fileName = if (id.isNullOrBlank()) {
            UUID.randomUUID().toString() + ".png"
        } else {
            val exists = File(UPLOAD_DIRECTORY).listFiles { _, name -> name == id }?.any() ?: false
            if (exists) throw BadRequestException("Image with name $id already exists")
            id
        }

        val destination = Paths.get(UPLOAD_DIRECTORY, fileName)
        val os = Files.newOutputStream(destination)
        os.write(file.bytes)
        return ResourceCreatedDTO(fileName)
    }

    @PutMapping("img/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasAuthority('update:img')")
    fun update(@RequestPart file: MultipartFile, @PathVariable id: String): ResourceCreatedDTO {
        val path = File(UPLOAD_DIRECTORY).listFiles { _, name -> name == id }?.firstOrNull()
            ?: throw BadRequestException("Image does not exist")
        file.transferTo(path)
        return ResourceCreatedDTO(id)
    }

    @DeleteMapping("img/{id}")
    @PreAuthorize("hasAuthority('delete:img')")
    fun delete(@PathVariable id: String) {
        File(UPLOAD_DIRECTORY).listFiles { _, name -> name == id }?.firstOrNull()?.delete()
    }
}