package af.dfi.api.resource;

import af.dfi.core.kafka.rest.KafkaTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/kafka/rest")
public class KafkaRestApiResource {

    @Autowired private KafkaTopicService kafkaTopicService;

    @GetMapping(value = "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Set<String>> findAllTopics()
    {
        return ResponseEntity.ok(kafkaTopicService.getTopics());
    }
}
