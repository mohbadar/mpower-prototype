package af.dfi.core.kafka.producer;

import af.dfi.data.model.User;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.USER_TOPIC;

@Service
public class UserProducer {

    @Autowired private KafkaTemplate kafkaTemplate;

    @Loggable
    public void produce(User obj)
    {
        kafkaTemplate.send(USER_TOPIC,obj);
    }
}
