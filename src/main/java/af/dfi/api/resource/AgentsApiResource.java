package af.dfi.api.resource;

import af.dfi.core.kafka.producer.AgentProducer;
import af.dfi.core.service.AgentService;
import af.dfi.data.model.Agent;
import af.dfi.lang.aspect.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/agents")
@Slf4j
public class AgentsApiResource {

    @Autowired private AgentService agentService;
    @Autowired private AgentProducer agentProducer;

    @Loggable
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> save(@Valid @RequestBody(required = true) Agent agent)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        agentProducer.produce(agent);
        return ResponseEntity.ok(data);
    }


    @Loggable
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> update(@Valid @RequestBody(required = true) Agent agent, @PathVariable(value = "id", required = true) String id)
    {
        //set id
        agent.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        agentProducer.produce(agent);
        return ResponseEntity.ok(data);
    }


    @Loggable
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Agent>> findAll()
    {
        return ResponseEntity.ok(agentService.findAll());
    }


    @Loggable
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Agent> findById(@PathVariable(value = "id", required = true) String id)
    {
        return ResponseEntity.ok(agentService.findById(id));
    }

    @Loggable
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, Object>> delete(@PathVariable(value = "id", required = true) String id)
    {
        Map<String, Object> data = new HashMap<>();

        if(agentService.delete(id))
        {
            data.put("code", HttpStatus.OK);
            data.put("message", "Data has successfully deleted");

        }else{
            data.put("code", HttpStatus.NOT_FOUND);
            data.put("message", "Data is not found. Please Try again later!");
        }
        return  ResponseEntity.ok(data);
    }
}
