package af.dfi.core.kafka.producer;

import af.dfi.data.model.Role;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.ROLE_TOPIC;

@Service
public class RoleProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Loggable
    public void produce(Role obj)
    {
        kafkaTemplate.send(ROLE_TOPIC,obj);
    }
}
