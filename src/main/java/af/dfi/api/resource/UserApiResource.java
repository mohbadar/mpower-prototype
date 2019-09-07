package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.model.User;
import af.dfi.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserApiResource extends ResponseHandler {

    @Autowired
    private UserService userService;

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
    public @ResponseBody ResponseEntity<User> save(@RequestBody(required = true) User user)
    {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> update(@PathVariable(required = true, name = "id") String id, @RequestBody(required = true) User user)
    {
        user.setId(id);
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    HttpStatus delete(@PathVariable(name = "id", required = true) String id)
    {
        userService.delete(id);
        return HttpStatus.ACCEPTED;
    }

}
