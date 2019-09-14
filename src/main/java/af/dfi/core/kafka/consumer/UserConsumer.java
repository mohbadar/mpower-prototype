package af.dfi.core.kafka.consumer;

import af.dfi.core.service.UserService;
import af.dfi.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.USER_TOPIC;
import static af.dfi.lang.ParamsConfig.CONSUMER_GROUP_ID;

@Service
@Slf4j
public class UserConsumer {

    @Autowired
    private UserService userService;
    @Autowired private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {USER_TOPIC}, groupId = CONSUMER_GROUP_ID)
    public void consume(User user)
    {
        log.info("CONSUMED DATA"+ user.toString());
        userService.save(user);
    }
}
