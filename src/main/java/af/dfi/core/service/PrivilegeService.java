package af.dfi.core.service;

import af.dfi.data.model.Privilege;
import af.dfi.data.repository.PrivilegeRepository;
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