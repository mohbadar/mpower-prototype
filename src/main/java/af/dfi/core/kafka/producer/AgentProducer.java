package af.dfi.core.kafka.producer;

import af.dfi.data.model.Agent;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.AGENT_TOPIC;

@Service
public class AgentProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Loggable
    public void produce(Agent obj)
    {
        kafkaTemplate.send(AGENT_TOPIC,obj);
    }
}
