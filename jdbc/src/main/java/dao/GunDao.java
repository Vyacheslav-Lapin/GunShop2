package dao;

import model.Gun;

import java.util.Collection;
import java.util.Optional;

@FunctionalInterface
public interface GunDao {
    Collection<Gun> getAll();
    
    default Optional<Gun> getById(long id) {
        return getAll().stream()
                .filter(gun -> gun.getId() == id)
                .findAny();
    }
}
