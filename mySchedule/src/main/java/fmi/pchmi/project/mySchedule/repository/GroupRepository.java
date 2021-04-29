package fmi.pchmi.project.mySchedule.repository;

import fmi.pchmi.project.mySchedule.model.database.group.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, String> {
    Group findByName(String name);
}
