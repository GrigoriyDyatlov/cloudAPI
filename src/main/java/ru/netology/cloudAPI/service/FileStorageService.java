package ru.netology.cloudAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudAPI.entity.FileMetadata;
import ru.netology.cloudAPI.repository.FileMetadataRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageService {

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public FileMetadata storeFile(MultipartFile file, String userId) throws IOException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String uploadDate = dtf.format(now);

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFilename(filename);
        fileMetadata.setContentType(contentType);
        fileMetadata.setSize(size);
        fileMetadata.setUserId(userId);
        fileMetadata.setUploadDate(uploadDate);

        return fileMetadataRepository.save(fileMetadata);
    }

    public Optional<FileMetadata> getFileMetadata(String id) {
        return fileMetadataRepository.findById(id);
    }

    public List<FileMetadata> getFilesByUserId(String userId) {
        return fileMetadataRepository.findByUserId(userId);
    }

    public void deleteFile(String id) {
        fileMetadataRepository.deleteById(id);
    }
}
