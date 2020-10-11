package controller;


import entity.AuditEntity;
import exception.ResourceNotFoundException;
import filter.AdminLevelRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BaseService;

import java.util.List;

@RequestMapping("/default")
public interface BaseController<M extends AuditEntity> {

    BaseService<M> getService();

    @AdminLevelRequest
    @GetMapping("/")
    default List<M> getList() {
        return getService().getList();
    }

    @AdminLevelRequest
    @PostMapping("/")
    default M create(@RequestBody M model) {
        return getService().create(model);
    }

    @AdminLevelRequest
    @PutMapping("/")
    default M edit(@RequestBody M model) throws ResourceNotFoundException {
        return getService().edit(model);
    }

    @AdminLevelRequest
    @GetMapping("/{id}")
    default M getById(@PathVariable Long id) throws ResourceNotFoundException {
        return getService().getById(id);
    }

    @AdminLevelRequest
    @DeleteMapping("/{id}")
    default ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        return getService().delete(id)
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
