package bohdan.abramovych.expandapis.core.persistence;

import bohdan.abramovych.expandapis.core.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
}
