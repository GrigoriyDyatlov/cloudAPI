package ru.netology.cloudAPI.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data // Lombok annotation
public class FileMetadata {

    @Id
    private String id;  // MongoDB generates a unique ID

    private String filename;
    private String contentType;
    private long size;
    private String userId; // Reference to the user who uploaded the file
    private String uploadDate; // store uploaddate as string
    // You might add other metadata like upload date, description, etc.
}
