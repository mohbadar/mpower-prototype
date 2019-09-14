package af.dfi.data.repository;

import af.dfi.data.model.AgentLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentLocationRepository extends MongoRepository<AgentLocation, String> {
    List<AgentLocation> findByCity(String city);
    List<AgentLocation> findByDistrict(String district);
    List<AgentLocation> findByArea(String area);
    List<AgentLocation> findByStreetAddress(String streetAddress);
}
