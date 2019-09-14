package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.kafka.producer.RoleProducer;
import af.dfi.core.service.RoleService;
import af.dfi.data.model.Role;
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
@RequestMapping(value = "/api/roles")
public class RoleApiResource extends ResponseHandler {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleProducer roleProducer;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Role>> findAll()
    {
        return ResponseEntity.ok(roleService.findAll());
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Role> findById(@PathVariable(name = "id", required = true) String id)
    {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Role> findByName(@PathVariable(name = "name", required = true) String name)
    {
        return ResponseEntity.ok(roleService.findByName(name));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, Object>> save(@Valid @RequestBody(required = true) Role role)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        roleProducer.produce(role);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, Object>> update(@Valid @PathVariable(required = true, name = "id") String id, @RequestBody(required = true) Role role)
    {
        //set id
        role.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        roleProducer.produce(role);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    HttpStatus delete(@PathVariable(name = "id", required = true) String id)
    {
        roleService.delete(id);
        return HttpStatus.ACCEPTED;
    }


}
