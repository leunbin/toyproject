package com.example.HitLoggerTracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Hit {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String path;
    @Column
    private String method;
    @Column
    private String ip;
    @Column
    private String userAgent;
    @Column
    @CreationTimestamp
    private LocalDateTime at;
}
