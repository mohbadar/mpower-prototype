package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.model.Role;
import af.dfi.service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleApiResource extends ResponseHandler {

    @Autowired
    private RoleService roleService;

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
    public @ResponseBody ResponseEntity<Role> save(@RequestBody(required = true) Role role)
    {
        return ResponseEntity.ok(roleService.save(role));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Role> update(@PathVariable(required = true, name = "id") String id, @RequestBody(required = true) Role role)
    {
        role.setId(id);
        return ResponseEntity.ok(roleService.save(role));
    }

    @DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    HttpStatus delete(@PathVariable(name = "id", required = true) String id)
    {
        roleService.delete(id);
        return HttpStatus.ACCEPTED;
    }


}
