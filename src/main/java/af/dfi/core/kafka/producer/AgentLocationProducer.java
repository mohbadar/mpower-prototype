package af.dfi.core.kafka.producer;

import af.dfi.data.model.TerminalAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.AGENT_LOCATION_TOPIC;

@Service
public class AgentLocationProducer {

    @Autowired private KafkaTemplate kafkaTemplate;

    public void produce(TerminalAddress obj)
    {
        kafkaTemplate.send(AGENT_LOCATION_TOPIC,obj);
    }
}
