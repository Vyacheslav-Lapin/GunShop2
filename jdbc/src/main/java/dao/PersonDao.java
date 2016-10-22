package dao;

import model.Person;

import java.util.Collection;
import java.util.Optional;

@FunctionalInterface
public interface PersonDao {
    
    Collection<Person> getAll();
    
    default Optional<Person> getById(long id) {
        return getAll().stream()
                .filter(person -> person.getId() == id)
                .findAny();
    }

    default boolean isPersonRegistered(String login, String hash) {
        return getAll().stream()
                .filter(person -> person.getEmail().equals(login))
                .anyMatch(person -> person.getPassword().equals(hash));
    }
}
