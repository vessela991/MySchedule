package fmi.pchmi.project.mySchedule.repository.helper;

import fmi.pchmi.project.mySchedule.model.exception.InternalException;
import fmi.pchmi.project.mySchedule.model.exception.ValidationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryHelper {
    public <T> Iterable<T> findAll(CrudRepository<T, String> repository) {
        try {
            return repository.findAll();
        } catch (Exception ex) {
            throw InternalException.create();
        }
    }

    public <T> T findById(CrudRepository<T, String> repository, String failureMessage, String id) {
        return repository.findById(id).orElseThrow(() -> ValidationException.create(failureMessage));
    }

    public <T> T save(CrudRepository<T, String> repository, T t) {
        try {
            return repository.save(t);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw InternalException.create();
        }
    }

    public <T> void deleteById(CrudRepository<T, String> repository, String failureMessage, String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw ValidationException.create(failureMessage);
        } catch (Exception ex) {
            throw InternalException.create();
        }
    }
}
