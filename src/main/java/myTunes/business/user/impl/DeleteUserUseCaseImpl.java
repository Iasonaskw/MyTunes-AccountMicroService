package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import myTunes.business.user.DeleteUserUseCase;
import myTunes.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    @Value("${rabbitmq.exchange.account}")
    private String exchange;
    @Value("${rabbitmq.binding.routing.key}")
    private String accountRoutingKey;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }
    @Override
    public void sendMessage(long id){
        rabbitTemplate.convertAndSend(exchange, accountRoutingKey, id);
    }
}
