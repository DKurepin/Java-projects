package com.kurepin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.EmployeeDto;
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
import java.util.List;


@Component
@RequiredArgsConstructor
@EnableRabbit
@Service
public class EmployeeMessageListener {

    Logger logger = LoggerFactory.getLogger(EmployeeMessageListener.class);
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final EmployeeService employeeService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "employee-create-queue", durable = "true"),
            exchange = @Exchange(value = "employee-exchange", type = ExchangeTypes.TOPIC),
            key = {"employee.create"}
    ))
    public void handleEmployeeCreate(EmployeeDto employeeDto) {
        EmployeeDto employeeDtoResult = employeeService.saveEmployee(employeeDto);
        rabbitTemplate.convertAndSend( "employee-get-result-queue", employeeDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "employee-update-queue", durable = "true"),
            exchange = @Exchange(value = "employee-exchange", type = ExchangeTypes.TOPIC),
            key = {"employee.update"}
    ))
    public void handleEmployeeUpdate(EmployeeDto employeeDto) {
        EmployeeDto employeeDtoResult = employeeService.updateEmployee(employeeDto);
        rabbitTemplate.convertAndSend( "employee-get-result-queue", employeeDtoResult);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "employee-delete-queue", durable = "true"),
            exchange = @Exchange(value = "employee-exchange", type = ExchangeTypes.TOPIC),
            key = "employee.delete.#"
    ))
    public void handleEmployeeDelete(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (routingKey.endsWith(".all")) {
            employeeService.deleteAllEmployees();
        } else if (routingKey.endsWith(".byId")) {
            Long employeeId = getMessageBody(message, Long.class);
            employeeService.deleteByEmployeeId(employeeId);
        } else if (routingKey.endsWith(".byEmployee")) {
            EmployeeDto employeeDto = getMessageBody(message, EmployeeDto.class);
            employeeService.deleteByEmployee(employeeDto);
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "employee-get-queue", durable = "true"),
            exchange = @Exchange(value = "employee-exchange", type = ExchangeTypes.TOPIC),
            key = "employee.get.#"
    ))
    public void handleEmployeeGet(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        try {
            if (routingKey.endsWith(".all")) {
                List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees();
                rabbitTemplate.convertAndSend( "employee-get-result-queue", employeeDtoList);
            } else if (routingKey.endsWith(".byId")) {
                Long employeeId = getMessageBody(message, Long.class);
                EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
                rabbitTemplate.convertAndSend( "employee-get-result-queue", employeeDto);
            } else if (routingKey.endsWith(".allByName")) {
                String name = getMessageBody(message, String.class);
                List<EmployeeDto> employeeDtoList = employeeService.getAllEmployeesByName(name);
                rabbitTemplate.convertAndSend( "employee-get-result-queue", employeeDtoList);
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
