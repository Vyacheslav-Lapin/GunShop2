package dao.h2;

import dao.PersonDao;
import lombok.SneakyThrows;
import lombok.val;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public interface H2PersonDao extends PersonDao {

    @SneakyThrows
    @Override
    default Collection<Person> getAll() {
        val persons = new HashSet<Person>();
        val sql = "SELECT id, first_name, last_name, permission, dob, email, password, address, telephone " +
                "FROM Person";
        try (Connection connection = get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next())
                persons.add(Person.getFrom(resultSet));
        }
        return persons;
    }

    @SneakyThrows
    @Override
    default Optional<Person> getById(long id) {
        String sql = "SELECT id, first_name, last_name, permission, dob, email, password, address, telephone " +
                "FROM Person WHERE id = ?";
        return mapPreparedRow(sql, Person::getFrom).getOrThrowUnchecked(id);
    }

    @SneakyThrows
    @Override
    default Optional<Person> isPersonRegistered(String login, String hash) {
        String sql = "SELECT id, first_name, last_name, permission, dob, email, password, address, telephone " +
                "FROM Person WHERE email = ? AND password = ?";
        try (Connection connection = get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, hash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ? Person.getFrom(resultSet) : null);
            }
        }
    }
}
