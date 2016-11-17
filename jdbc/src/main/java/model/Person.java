package model;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.sql.ResultSet;
import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Person {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final boolean permission;
    private final LocalDate dob;
    private final String email;
    private final String password;
    private final String address;
    private final String telephone;

    @SneakyThrows
    public static Person getFrom(ResultSet resultSet) {
        return new Person(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getBoolean("permission"),
                resultSet.getDate("dob").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("address"),
                resultSet.getString("telephone")
        );
    }
}