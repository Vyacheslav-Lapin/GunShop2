package dao.h2;

import dao.PersonDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@Log
@AllArgsConstructor
public class H2PersonDao implements PersonDao {

    private Supplier<Connection> connectionSupplier;

    @SneakyThrows
    @Override
    public Collection<Person> getAll() {
        Collection<Person> persons = new HashSet<>();
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, first_name, last_name, permission, dob, email, password, address, telephone FROM Person")) {
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
                        ));}
        return persons;
    }
}
