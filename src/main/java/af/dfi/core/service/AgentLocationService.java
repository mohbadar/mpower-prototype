package af.dfi.core.service;

import af.dfi.data.model.AgentLocation;
import af.dfi.data.repository.AgentLocationRepository;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentLocationService {

    @Autowired private AgentLocationRepository agentLocationRepository;

    @Loggable
    @Retryable
    public AgentLocation save(AgentLocation agentLocation)
    {
        return agentLocationRepository.save(agentLocation);
    }

    @Loggable
    public List<AgentLocation> findAll()
    {
        return agentLocationRepository.findAll();
    }

    @Loggable
    public AgentLocation findById(String id)
    {
        return agentLocationRepository.findById(id).get();
    }

    @Loggable
    public boolean delete(String id)
    {
        if (id ==null) return false;
        agentLocationRepository.deleteById(id);
        return true;
    }

    @Loggable
    public List<AgentLocation> findByCity(String cityName)
    {
        return agentLocationRepository.findByCity(cityName);
    }
}
