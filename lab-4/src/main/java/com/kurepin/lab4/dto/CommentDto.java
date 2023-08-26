package com.kurepin.lab4.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private OffsetDateTime creationDate;
    private Long taskId;
}
