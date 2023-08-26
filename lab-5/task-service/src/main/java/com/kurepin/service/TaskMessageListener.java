package com.kurepin.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.EmployeeDto;
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
public class TaskMessageListener {

    Logger logger = LoggerFactory.getLogger(TaskMessageListener.class);
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final TaskService taskService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "task-create-queue", durable = "true"),
            exchange = @Exchange(value = "task-exchange", type = ExchangeTypes.TOPIC),
            key = {"task.create"}
    ))
    public void handleTaskCreate(TaskDto taskDto) throws ParseException {
        TaskDto taskDtoResult = taskService.saveTask(taskDto);
        rabbitTemplate.convertAndSend("task-get-result-queue", taskDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "task-update-queue", durable = "true"),
            exchange = @Exchange(value = "task-exchange", type = ExchangeTypes.TOPIC),
            key = {"task.update"}
    ))
    public void handleTaskUpdate(TaskDto taskDto) throws ParseException {
        TaskDto taskDtoResult = taskService.updateTask(taskDto);
        rabbitTemplate.convertAndSend("task-get-result-queue", taskDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "task-delete-queue", durable = "true"),
            exchange = @Exchange(value = "task-exchange", type = ExchangeTypes.TOPIC),
            key = "task.delete.#"
    ))
    public void handleTaskDelete(Message message) throws ParseException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (routingKey.endsWith(".all")) {
            taskService.deleteAllTasks();
        } else if (routingKey.endsWith(".byId")) {
            Long taskId = getMessageBody(message, Long.class);
            taskService.deleteByTaskId(taskId);
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "task-get-queue", durable = "true"),
            exchange = @Exchange(value = "task-exchange", type = ExchangeTypes.TOPIC),
            key = "task.get.#"
    ))
    public void handleTaskGet(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        try {
            if (routingKey.endsWith(".all")) {
                List<TaskDto> taskDtoList = taskService.getAllTasks();
                rabbitTemplate.convertAndSend("task-get-result-queue", taskDtoList);
            } else if (routingKey.endsWith(".byId")) {
                Long taskId = getMessageBody(message, Long.class);
                TaskDto taskDto = taskService.getTaskById(taskId);
                rabbitTemplate.convertAndSend("task-get-result-queue", taskDto);
            } else if (routingKey.endsWith(".allByName")) {
                String name = getMessageBody(message, String.class);
                List<TaskDto> taskDtoList = taskService.getAllTasksByName(name);
                rabbitTemplate.convertAndSend("task-get-result-queue", taskDtoList);
            } else if (routingKey.endsWith(".allByEmployeeId")) {
                EmployeeDto employeeDto = getMessageBody(message, EmployeeDto.class);
                List<TaskDto> taskDtoList = taskService.getAllTasksByEmployeeId(employeeDto);
                rabbitTemplate.convertAndSend("task-get-result-queue", taskDtoList);
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
