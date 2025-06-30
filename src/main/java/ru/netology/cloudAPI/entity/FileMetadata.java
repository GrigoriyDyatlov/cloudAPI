package ru.netology.cloudAPI.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
public class FileMetadata {

    @Id
    private String id;

    private String filename;
    private String contentType;
    private long size;
    private String userId;
    private String uploadDate;

}
