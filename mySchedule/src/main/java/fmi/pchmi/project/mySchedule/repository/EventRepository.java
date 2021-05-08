package fmi.pchmi.project.mySchedule.repository;

import fmi.pchmi.project.mySchedule.model.database.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends CrudRepository<Event, String> {
    @Query(value = "SELECT * FROM events WHERE id IN :collection", nativeQuery = true)
    Collection<Event> getAllEventsInList(Collection<String> collection);
}
