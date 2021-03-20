package fmi.pchmi.project.mySchedule.repository;

import fmi.pchmi.project.mySchedule.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
