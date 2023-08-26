package com.kurepin.lab3.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.OffsetDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private OffsetDateTime creationDate;
    private String taskName;
}
