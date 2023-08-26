package com.kurepin.lab3.mapper;

import com.kurepin.lab3.dto.CommentDto;
import com.kurepin.lab3.entities.Comment;
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

}
