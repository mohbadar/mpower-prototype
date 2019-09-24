package af.dfi.core.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.APPLICATION_LOGS;
import static af.dfi.lang.ParamsConfig.CONSUMER_GROUP_ID;

@Service
@Slf4j
public class LogConsumer {

    @Autowired private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {APPLICATION_LOGS}, groupId = CONSUMER_GROUP_ID)
    public void consume(String data)
    {
        log.info("CONSUMED LOGS"+ data);
    }
}
