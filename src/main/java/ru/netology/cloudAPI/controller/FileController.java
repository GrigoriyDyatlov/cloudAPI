package ru.netology.cloudAPI.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.cloudAPI.entity.FileMetadata;
import ru.netology.cloudAPI.service.FileStorageService;
import ru.netology.cloudAPI.util.JwtUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cloud/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        String userId = jwtUtil.extractUsername(token);

        FileMetadata metadata = fileStorageService.storeFile(file, userId);
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) {
        Optional<FileMetadata> fileMetadataOptional = fileStorageService.getFileMetadata(id);

        if (fileMetadataOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        FileMetadata fileMetadata = fileMetadataOptional.get();

        // TODO: Retrieve the file data from MongoDB GridFS (implementation not included)
        byte[] fileData = new byte[0]; // Replace with actual file data retrieval

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileMetadata.getFilename())
                .contentType(MediaType.parseMediaType(fileMetadata.getContentType()))
                .contentLength(fileData.length)
                .body(resource);
    }

    @GetMapping("/list")
    public List<FileMetadata> listFiles(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        String userId = jwtUtil.extractUsername(token);
        return fileStorageService.getFilesByUserId(userId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) {
        fileStorageService.deleteFile(id);
        return ResponseEntity.ok().build();
    }
}
