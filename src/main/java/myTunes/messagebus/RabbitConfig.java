package myTunes.messagebus;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchange.account}")
    private String accountExchangeName;

    @Value("${rabbitmq.queue.delete.user}")
    private String deleteUserQueue;

    @Value("${rabbitmq.binding.routing.key}")
    private String accountRoutingKey;

    @Bean
    public Queue deleteUserQueue(){
        return  new Queue(deleteUserQueue);
    }
    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange(accountExchangeName);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(deleteUserQueue())
                .to(accountExchange())
                .with(accountRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /*@Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }*/
}
