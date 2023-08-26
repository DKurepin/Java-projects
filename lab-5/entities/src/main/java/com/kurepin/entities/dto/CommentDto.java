package com.kurepin.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@RequiredArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private Date creationDate;
    private Long taskId;

    @JsonCreator
    public CommentDto(@JsonProperty("id") Long id,
                      @JsonProperty("content") String content,
                      @JsonProperty("author") String author,
                      @JsonProperty("creationDate") Date creationDate,
                      @JsonProperty("taskId") Long taskId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.taskId = taskId;
    }
}
