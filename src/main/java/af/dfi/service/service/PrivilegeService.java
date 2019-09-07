package af.dfi.service.service;

import af.dfi.core.model.Privilege;
import af.dfi.service.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository repository;

    @Retryable
    public Privilege save(Privilege privilege)
    {
        return repository.save(privilege);
    }
    public List<Privilege> findAll()
    {
        return repository.findAll();
    }
}