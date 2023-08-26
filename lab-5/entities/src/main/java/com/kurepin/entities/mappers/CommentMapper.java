package com.kurepin.entities.mappers;

import com.kurepin.entities.dto.CommentDto;
import com.kurepin.entities.Comment;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@RequiredArgsConstructor
@Data
@Component
public class CommentMapper {

    private final ModelMapper modelMapper;

    public Comment toEntity(CommentDto commentDto) throws ParseException {
        return modelMapper.map(commentDto, Comment.class);
    }

    public CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}
