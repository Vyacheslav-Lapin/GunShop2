package dao;

import com.hegel.core.JdbcDao;
import model.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@FunctionalInterface
public interface PersonDao extends JdbcDao {
    
    default Collection<Person> getAll() {
        return Collections.emptySet();
    }
    
    default Optional<Person> getById(long id) {
        return getAll().stream()
                .filter(person -> person.getId() == id)
                .findAny();
    }

    default Optional<Person> isPersonRegistered(String login, String hash) {
        return getAll().stream()
                .filter(person -> person.getEmail().equals(login))
                .filter(person -> person.getPassword().equals(hash))
                .findAny();
    }
}
