package af.dfi.api.resource;

import af.dfi.core.kafka.producer.AgentLocationProducer;
import af.dfi.core.service.AgentLocationService;
import af.dfi.data.model.AgentLocation;
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
@RequestMapping(value = "/api/agent-locations")
@Slf4j
public class AgentLocationApiResource {

    @Autowired private AgentLocationProducer agentLocationProducer;
    @Autowired private AgentLocationService agentLocationService;

    @Loggable
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> save(@Valid @RequestBody(required = true) AgentLocation agentLocation)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        agentLocationProducer.produce(agentLocation);
        return ResponseEntity.ok(data);
    }


    @Loggable
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Map<String,Object>> update(@Valid @RequestBody(required = true) AgentLocation agentLocation, @PathVariable(value = "id", required = true) String id)
    {
        //set id
        agentLocation.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        agentLocationProducer.produce(agentLocation);
        return ResponseEntity.ok(data);
    }


    @Loggable
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<AgentLocation>> findAll()
    {
        return ResponseEntity.ok(agentLocationService.findAll());
    }


    @Loggable
    @GetMapping(value = "/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<AgentLocation>> findByCity(@PathVariable(value = "city", required = true) String city)
    {
        return ResponseEntity.ok(agentLocationService.findByCity(city));
    }


    @Loggable
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AgentLocation> findById(@PathVariable(value = "id", required = true) String id)
    {
        return ResponseEntity.ok(agentLocationService.findById(id));
    }

    @Loggable
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, Object>> delete(@PathVariable(value = "id", required = true) String id)
    {
        Map<String, Object> data = new HashMap<>();

        if(agentLocationService.delete(id))
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
