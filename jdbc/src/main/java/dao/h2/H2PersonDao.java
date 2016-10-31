package dao.h2;

import dao.PersonDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;

@Log
@AllArgsConstructor
public class H2PersonDao implements PersonDao {

    private Supplier<Connection> connectionSupplier;

    @SneakyThrows
    @Override
    public Collection<Person> getAll() {
        Collection<Person> persons = new HashSet<>();
        String sql = "SELECT id, first_name, last_name, permission, dob, email, password, address, telephone " +
                "FROM Person";
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next())
                persons.add(
                        new Person(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getBoolean("permission"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("address"),
                                resultSet.getString("telephone")
                        ));
        }
        return persons;
    }

    @SneakyThrows
    @Override
    public Optional<Person> getById(long id) {
        String sql = "SELECT first_name, last_name, permission, dob, email, password, address, telephone " +
                "FROM Person WHERE id = ?";
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ?
                        new Person(
                                id,
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getBoolean("permission"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("address"),
                                resultSet.getString("telephone")) :
                        null);
            }
        }
    }

    @SneakyThrows
    @Override
    public Optional<Person> isPersonRegistered(String login, String hash) {
        String sql = "SELECT id, first_name, last_name, permission, dob, address, telephone " +
                "FROM Person WHERE email = ? AND password = ?";
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, hash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ?
                        new Person(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getBoolean("permission"),
                                resultSet.getDate("dob").toLocalDate(),
                                login,
                                hash,
                                resultSet.getString("address"),
                                resultSet.getString("telephone")) :
                        null);
            }
        }
    }
}
