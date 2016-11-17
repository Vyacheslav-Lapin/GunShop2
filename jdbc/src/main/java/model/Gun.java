package model;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.sql.ResultSet;

@Value
@AllArgsConstructor
public class Gun {
    private final long id;
    private final String name;
    private final double caliber;

    @SneakyThrows
    public static Gun getFrom(ResultSet resultSet) {
        return new Gun(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("caliber"));
    }
}
