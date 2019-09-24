package af.dfi.core.kafka.consumer;

import af.dfi.core.service.AgentLocationService;
import af.dfi.data.model.TerminalAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static af.dfi.core.kafka.Topics.AGENT_LOCATION_TOPIC;
import static af.dfi.lang.ParamsConfig.CONSUMER_GROUP_ID;

@Component
@Slf4j
public class AgentLocationConsumer {


    @Autowired private AgentLocationService agentLocationService;
    @Autowired private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {AGENT_LOCATION_TOPIC}, groupId = CONSUMER_GROUP_ID)
    public void consume(TerminalAddress terminalAddress)
    {
        log.info("CONSUMED DATA"+ terminalAddress.toString());
        agentLocationService.save(terminalAddress);
    }
}
