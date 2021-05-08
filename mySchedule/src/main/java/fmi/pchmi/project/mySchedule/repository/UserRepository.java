package fmi.pchmi.project.mySchedule.repository;

import fmi.pchmi.project.mySchedule.model.database.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
