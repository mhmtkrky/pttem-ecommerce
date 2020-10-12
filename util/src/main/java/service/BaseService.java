package service;

import entity.AuditEntity;
import exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<M extends AuditEntity> {

    JpaRepository<M, Long> getRepository();

    default List<M> getList() {
        return getRepository().findAll();
    }

    ;

    default M create(M model) {
        return getRepository().save(model);
    }

    default M edit(M model) throws ResourceNotFoundException {

        if (!getRepository().existsById(model.getId()))
            throw new ResourceNotFoundException(model.getClass().getSimpleName()
                    + " not found with id " + model.getId());
        return getRepository().findById(model.getId())
                .map(x -> getRepository().save(x)).orElseThrow(()
                        -> new ResourceNotFoundException(model.getClass().getSimpleName()
                        + " not found with id " + model.getId()));

    }

    default M getById(Long id) throws ResourceNotFoundException {
        return getRepository().findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found with id " + id));
    }

    default boolean delete(Long id) throws ResourceNotFoundException {
        return getRepository().findById(id)
                .map(x -> {
                    getRepository().delete(x);
                    return true;
                }).orElseThrow(() -> new ResourceNotFoundException("Resource not found with id " + id));
    }

    ;
}
