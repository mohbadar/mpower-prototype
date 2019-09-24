package af.dfi.data.repository;

import af.dfi.data.model.TerminalAddress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentLocationRepository extends MongoRepository<TerminalAddress, String> {
    List<TerminalAddress> findByCity(String city);
    List<TerminalAddress> findByDistrict(String district);
    List<TerminalAddress> findByArea(String area);
    List<TerminalAddress> findByStreetAddress(String streetAddress);
}
