package com.kurepin.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

    Logger logger = LoggerFactory.getLogger(RabbitMqConfiguration.class);

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Queue employeeCreateQueue() {
        return new Queue("employee-create-queue");
    }

    @Bean
    public Queue employeeUpdateQueue() {
        return new Queue("employee-update-queue");
    }

    @Bean
    public Queue employeeDeleteQueue() {
        return new Queue("employee-delete-queue");
    }

    @Bean
    public Queue employeeGetQueue() {
        return new Queue("employee-get-queue");
    }

    @Bean
    public Queue employeeGetResultQueue() {
        return new Queue("employee-get-result-queue");
    }

    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange("employee-exchange");
    }

    @Bean
    public Binding employeeCreateBinding(Queue employeeCreateQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeCreateQueue).to(employeeExchange).with("employee.create");
    }

    @Bean
    public Binding employeeUpdateBinding(Queue employeeUpdateQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeUpdateQueue).to(employeeExchange).with("employee.update");
    }

    @Bean
    public Binding employeeDeleteBinding(Queue employeeDeleteQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeDeleteQueue).to(employeeExchange).with("employee.delete.#");
    }

    @Bean
    public Binding employeeGetBinding(Queue employeeGetQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeGetQueue).to(employeeExchange).with("employee.get.#");
    }

    @Bean
    public Binding employeeGetResultBinding(Queue employeeGetResultQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeGetResultQueue).to(employeeExchange).with("employee.get.result");
    }

    @Bean
    public Queue taskCreateQueue() {
        return new Queue("task-create-queue");
    }

    @Bean
    public Queue taskUpdateQueue() {
        return new Queue("task-update-queue");
    }

    @Bean
    public Queue taskDeleteQueue() {
        return new Queue("task-delete-queue");
    }

    @Bean
    public Queue taskGetQueue() {
        return new Queue("task-get-queue");
    }

    @Bean
    public Queue taskGetResultQueue() {
        return new Queue("task-get-result-queue");
    }

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange("task-exchange");
    }

    @Bean
    public Binding taskCreateBinding(Queue taskCreateQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskCreateQueue).to(taskExchange).with("task.create");
    }

    @Bean
    public Binding taskUpdateBinding(Queue taskUpdateQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskUpdateQueue).to(taskExchange).with("task.update");
    }

    @Bean
    public Binding taskDeleteBinding(Queue taskDeleteQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskDeleteQueue).to(taskExchange).with("task.delete.#");
    }

    @Bean
    public Binding taskGetBinding(Queue taskGetQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskGetQueue).to(taskExchange).with("task.get.#");
    }

    @Bean
    public Binding taskGetResultBinding(Queue taskGetResultQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskGetResultQueue).to(taskExchange).with("task.get.result");
    }

    @Bean
    public Queue commentCreateQueue() {
        return new Queue("comment-create-queue");
    }

    @Bean
    public Queue commentUpdateQueue() {
        return new Queue("comment-update-queue");
    }

    @Bean
    public Queue commentDeleteQueue() {
        return new Queue("comment-delete-queue");
    }

    @Bean
    public Queue commentGetQueue() {
        return new Queue("comment-get-queue");
    }

    @Bean
    public Queue commentGetResultQueue() {
        return new Queue("comment-get-result-queue");
    }

    @Bean
    public TopicExchange commentExchange() {
        return new TopicExchange("comment-exchange");
    }

    @Bean
    public Binding commentCreateBinding(Queue commentCreateQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentCreateQueue).to(commentExchange).with("comment.create");
    }

    @Bean
    public Binding commentUpdateBinding(Queue commentUpdateQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentUpdateQueue).to(commentExchange).with("comment.update");
    }

    @Bean
    public Binding commentDeleteBinding(Queue commentDeleteQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentDeleteQueue).to(commentExchange).with("comment.delete.#");
    }

    @Bean
    public Binding commentGetBinding(Queue commentGetQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentGetQueue).to(commentExchange).with("comment.get.#");
    }

    @Bean
    public Binding commentGetResultBinding(Queue commentGetResultQueue, TopicExchange commentExchange) {
        return BindingBuilder.bind(commentGetResultQueue).to(commentExchange).with("comment.get.result");
    }

}
