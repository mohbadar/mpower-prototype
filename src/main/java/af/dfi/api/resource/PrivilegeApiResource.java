package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.model.Privilege;
import af.dfi.service.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(value = "/api/privileges")
public class PrivilegeApiResource extends ResponseHandler {

    @Autowired private PrivilegeService privilegeService;

    @PostConstruct
    public void init()
    {
        Privilege createRolePriv = new Privilege("CREATE_ROLE");
        Privilege readRolePriv = new Privilege("READ_ROLE");
        Privilege updateRolePriv = new Privilege("UPDATE_ROLE");
        Privilege deleteRolePriv = new Privilege("DELETE_ROLE");

        if (privilegeService.findAll().size() < 1)
        {
            privilegeService.save(createRolePriv);
            privilegeService.save(readRolePriv);
            privilegeService.save(updateRolePriv);
            privilegeService.save(deleteRolePriv);
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Privilege>> findAll() {
        return ResponseEntity.ok(privilegeService.findAll());
    }
}
