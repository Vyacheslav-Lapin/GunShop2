package dao;

import model.Instance;

import java.util.Collection;
import java.util.Optional;

@FunctionalInterface
public interface InstanceDao {
    Collection<Instance> getAll();
    
    default Optional<Instance> getById(long id) {
        return getAll().stream()
                .filter(instance -> instance.getId() == id)
                .findAny();
    }
}
