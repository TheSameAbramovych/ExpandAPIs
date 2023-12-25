package bohdan.abramovych.expandapis.repo;

import bohdan.abramovych.expandapis.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
