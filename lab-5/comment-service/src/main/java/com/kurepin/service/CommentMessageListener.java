package com.kurepin.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.CommentDto;
import com.kurepin.entities.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
@EnableRabbit
@Service
public class CommentMessageListener {
    Logger logger = LoggerFactory.getLogger(CommentMessageListener.class);
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final CommentService commentService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "comment-create-queue", durable = "true"),
            exchange = @Exchange(value = "comment-exchange", type = ExchangeTypes.TOPIC),
            key = {"comment.create"}
    ))
    public void handleCommentCreate(CommentDto commentDto) throws ParseException {
        CommentDto commentDtoResult = commentService.saveComment(commentDto);
        rabbitTemplate.convertAndSend("comment-get-result-queue", commentDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "comment-update-queue", durable = "true"),
            exchange = @Exchange(value = "comment-exchange", type = ExchangeTypes.TOPIC),
            key = {"comment.update"}
    ))
    public void handleCommentUpdate(CommentDto commentDto) throws ParseException {
        CommentDto commentDtoResult = commentService.updateComment(commentDto);
        rabbitTemplate.convertAndSend("comment-get-result-queue", commentDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "comment-delete-queue", durable = "true"),
            exchange = @Exchange(value = "comment-exchange", type = ExchangeTypes.TOPIC),
            key = "comment.delete.#"
    ))
    public void handleCommentDelete(Message message) throws ParseException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (routingKey.endsWith(".all")) {
            commentService.deleteAllComments();
        } else if (routingKey.endsWith(".byId")) {
            Long taskId = getMessageBody(message, Long.class);
            commentService.deleteByCommentId(taskId);
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "comment-get-queue", durable = "true"),
            exchange = @Exchange(value = "comment-exchange", type = ExchangeTypes.TOPIC),
            key = "comment.get.#"
    ))
    public void handleCommentGet(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        try {
            if (routingKey.endsWith(".all")) {
                List<CommentDto> commentDtoList = commentService.getAllComments();
                rabbitTemplate.convertAndSend("comment-get-result-queue", commentDtoList);
            } else if (routingKey.endsWith(".byId")) {
                Long commentId = getMessageBody(message, Long.class);
                CommentDto commentDto = commentService.getCommentById(commentId);
                rabbitTemplate.convertAndSend("comment-get-result-queue", commentDto);
            } else if (routingKey.endsWith(".allByAuthor")) {
                String author = getMessageBody(message, String.class);
                List<CommentDto> commentDtoList = commentService.getAllCommentsByAuthor(author);
                rabbitTemplate.convertAndSend("comment-get-result-queue", commentDtoList);
            } else if (routingKey.endsWith(".allByTaskId")) {
                TaskDto taskDto = getMessageBody(message, TaskDto.class);
                List<CommentDto> commentDtoList = commentService.getAllCommentsByTaskId(taskDto);
                rabbitTemplate.convertAndSend("comment-get-result-queue", commentDtoList);
            }
        } catch (Exception e) {
            logger.error("Failed to handle message", e);
        }
    }

    private <T> T getMessageBody(Message message, Class<T> clazz) {
        byte[] body = message.getBody();
        try {
            return objectMapper.readValue(body, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize message body", e);
        }
    }
}
