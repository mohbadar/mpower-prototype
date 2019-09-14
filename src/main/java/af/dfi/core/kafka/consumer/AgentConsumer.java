package af.dfi.core.kafka.consumer;

import af.dfi.core.service.AgentService;
import af.dfi.data.model.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static af.dfi.core.kafka.Topics.AGENT_TOPIC;
import static af.dfi.lang.ParamsConfig.CONSUMER_GROUP_ID;

@Service
@Slf4j
public class AgentConsumer {

    @Autowired
    private AgentService agentService;
    @Autowired private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {AGENT_TOPIC}, groupId = CONSUMER_GROUP_ID)
    public void consume(Agent agent)
    {
        log.info("CONSUMED DATA"+ agent.toString());
        agentService.save(agent);
    }
}
