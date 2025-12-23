// 1. 파일 시스템에 저장
// 2. 메타데이터를 DB에 저장

package com.example.FileSharing.service;

import com.example.FileSharing.domain.StoredFile;
import com.example.FileSharing.repository.StoredFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class FileService {
    private final StoredFileRepository repository;

    private final Path uploadPath = Paths.get("upload");

    private void validate(MultipartFile file){
        if(file.isEmpty()){
            throw new IllegalArgumentException("빈 파일");
        }

        if(file.getSize()>5*1024*1024){
            throw new IllegalArgumentException("5MB 초과");
        }

        List<String> allowed = List.of(
                "image/png",
                "image/jpeg",
                "application/pdf"
        );

        if(!allowed.contains(file.getContentType())){
            throw new IllegalArgumentException("허용되지 않은 타입");
        }
    }

    public void upload(MultipartFile file){
        validate(file);

        String savedName = UUID.randomUUID().toString();
        Path target = uploadPath.resolve(savedName);

        try(InputStream in = file.getInputStream()){
            Files.copy(in, target);
        } catch(IOException e){
            throw new RuntimeException("파일 저장 실패",e);
        }

        StoredFile storedFile = new StoredFile(
                file.getOriginalFilename(),
                savedName,
                file.getSize(),
                file.getContentType(),
                LocalDateTime.now()
        );

        repository.save(storedFile);
    }

    public ResponseEntity<Resource> download(Long id){
        StoredFile file = repository.findById(id).orElseThrow();

        Path path = uploadPath.resolve(file.getSavedName());
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+file.getOriginalName()+"\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }

    public List<StoredFile> findAllFiles(){
        return repository.findAll();
    }
}
