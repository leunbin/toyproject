package com.example.FileSharing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class StoredFile {
    @GeneratedValue
    @Id
    private Long id;

    private String originalName;
    private String savedName;
    private Long size;
    private String contentType;
    private LocalDateTime uploadedAt;

    protected StoredFile(){
    }

    public StoredFile(
            String originalName,
            String savedName,
            Long size,
            String contentType,
            LocalDateTime uploadedAt
    ){
      this.originalName = originalName;
      this.savedName = savedName;
      this.size = size;
      this.contentType = contentType;
      this.uploadedAt = uploadedAt;
    };
}
