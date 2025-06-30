package ru.netology.cloudAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.netology.cloudAPI.entity.FileMetadata;

import java.util.List;


public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
    List<FileMetadata> findByUserId(String userId);
}