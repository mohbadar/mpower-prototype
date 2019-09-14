package af.dfi.data.repository;

import af.dfi.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);

}
