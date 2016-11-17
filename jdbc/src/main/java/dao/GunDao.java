package dao;

import com.hegel.core.JdbcDao;
import model.Gun;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface GunDao extends JdbcDao {

    default Collection<Gun> getAll() {
        return Collections.emptySet();
    }
    
    default Optional<Gun> getById(long id) {
        return getAll().stream()
                .filter(gun -> gun.getId() == id)
                .findAny();
    }
}
