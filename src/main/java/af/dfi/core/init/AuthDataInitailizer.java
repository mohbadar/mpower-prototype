package af.dfi.core.init;


import af.dfi.core.service.PrivilegeService;
import af.dfi.core.service.RoleService;
import af.dfi.core.service.UserService;
import af.dfi.data.model.Privilege;
import af.dfi.data.model.Role;
import af.dfi.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthDataInitailizer {

    @Autowired private PrivilegeService privilegeService;
    @Autowired private RoleService roleService;
    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;
//    @Autowired private

    @PostConstruct
    public void init()
    {
        initAuthData();
    }

    private void initAuthData()
    {
        if(userService.findAll().size() <1)
        {
            User admin = new User();
            admin.setEmail("admin@aalpr.af");
            admin.setEnabled(true);
            admin.setName("Admin of System");
            admin.setUsername("admin");
            admin.setPhone("0794035544");
            admin.setRoles(createRolesIfNotExist());
            admin.setPassword("admin");

            userService.save(admin);
        }
    }

    private List<Privilege> getAllPrivileges(){
        List<Privilege> privileges = new ArrayList<>();

        if (privilegeService.findAll().size() < 1)
        {
            Privilege createRolePriv = new Privilege("CREATE_ROLE");
            Privilege readRolePriv = new Privilege("READ_ROLE");
            Privilege updateRolePriv = new Privilege("UPDATE_ROLE");
            Privilege deleteRolePriv = new Privilege("DELETE_ROLE");


            Privilege createUserPriv = new Privilege("CREATE_USER");
            Privilege readUserPriv = new Privilege("READ_USER");
            Privilege updateUserPriv = new Privilege("UPDATE_USER");
            Privilege deleteUserPriv = new Privilege("DELETE_USER");


            privilegeService.save(createRolePriv);
            privilegeService.save(readRolePriv);
            privilegeService.save(updateRolePriv);
            privilegeService.save(deleteRolePriv);

            privilegeService.save(createUserPriv);
            privilegeService.save(readUserPriv);
            privilegeService.save(updateUserPriv);
            privilegeService.save(deleteUserPriv);

            privileges = Arrays.asList(
                createRolePriv, readRolePriv, updateRolePriv, deleteRolePriv,
                    createUserPriv, readUserPriv, updateUserPriv, deleteUserPriv
            );
        }else{
                privileges = privilegeService.findAll();
        }

        return  privileges;
    }

    private List<Role> createRolesIfNotExist()
    {
        List<Role> roles = new ArrayList<>();

        if(roleService.findAll().size() < 1){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("ROLE FOR ADMIN");
            adminRole.setPrivileges(getAllPrivileges());

            roleService.save(adminRole);

            roles = Arrays.asList(
                adminRole
            );
        }else{
            roles = roleService.findAll();
        }
        return roles;
    }

}
