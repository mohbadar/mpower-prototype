package af.dfi.core.service;


import af.dfi.data.model.Role;
import af.dfi.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll()
    {
        return roleRepository.findAll();
    }

    public Role findByName(String name)
    {
        return  roleRepository.findByName(name);
    }

    public Role findById(String id)
    {
        return roleRepository.findById(id).get();
    }

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public void delete(String id)
    {
         roleRepository.deleteById(id);
    }

}
