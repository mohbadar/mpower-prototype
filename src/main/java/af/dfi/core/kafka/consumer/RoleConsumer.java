package af.dfi.core.kafka.consumer;

import af.dfi.core.service.RoleService;
import af.dfi.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.ROLE_TOPIC;
import static af.dfi.lang.ParamsConfig.CONSUMER_GROUP_ID;

@Service
@Slf4j
public class RoleConsumer {

    @Autowired
    private RoleService userService;
    @Autowired private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {ROLE_TOPIC}, groupId = CONSUMER_GROUP_ID)
    public void consume(Role obj)
    {
        log.info("CONSUMED DATA"+ obj.toString());
        userService.save(obj);
    }
}
