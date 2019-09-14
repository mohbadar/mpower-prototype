package af.dfi.core.service;

import af.dfi.data.model.Agent;
import af.dfi.data.repository.AgentRepository;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    @Autowired private AgentRepository agentRepository;

    @Loggable
    @Retryable
    public Agent save(Agent agent)
    {
        return agentRepository.save(agent);
    }

    @Loggable
    public List<Agent> findAll()
    {
        return agentRepository.findAll();
    }

    @Loggable
    public boolean delete(String id)
    {
        if (id == null) return false;
        agentRepository.deleteById(id);
        return true;
    }


    @Loggable
    public Agent findById(String id)
    {
        return agentRepository.findById(id).get();
    }

}
