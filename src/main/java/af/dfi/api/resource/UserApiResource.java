package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.kafka.producer.UserProducer;
import af.dfi.core.service.UserService;
import af.dfi.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserApiResource extends ResponseHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private UserProducer userProducer;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<User>> findAll()
    {
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> findById(@PathVariable(name = "id", required = true) String id)
    {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(value = "/name/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> findByUsername(@PathVariable(name = "username", required = true) String username)
    {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping(value = "/name/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> findByPhone(@PathVariable(name = "phone", required = true) String phone)
    {
        return ResponseEntity.ok(userService.findByPhone(phone));
    }

    @GetMapping(value = "/name/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> findByEmail(@PathVariable(name = "email", required = true) String email)
    {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, Object>> save(@RequestBody(required = true) User user)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        userProducer.produce(user);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String,Object>> update(@PathVariable(required = true, name = "id") String id, @RequestBody(required = true) User user)
    {
        //set id
        user.setId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.ACCEPTED);
        data.put("message", "Data has successfully sent to Kafka");
        userProducer.produce(user);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    HttpStatus delete(@PathVariable(name = "id", required = true) String id)
    {
        userService.delete(id);
        return HttpStatus.ACCEPTED;
    }

}
