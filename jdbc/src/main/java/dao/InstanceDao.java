package dao;

import com.hegel.core.JdbcDao;
import model.Instance;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface InstanceDao extends JdbcDao {

    default Collection<Instance> getAll() {
        return Collections.emptySet();
    }
    
    default Optional<Instance> getById(long id) {
        return getAll().stream()
                .filter(instance -> instance.getId() == id)
                .findAny();
    }
}
