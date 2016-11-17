package model;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.sql.ResultSet;

@Value
@AllArgsConstructor
public class Instance {
    private final long id;
    private final Gun gun;

    @SneakyThrows
    public static Instance getFrom(ResultSet resultSet) {
        return new Instance(
                resultSet.getLong("id"),
                Gun.getFrom(resultSet));
    }
}
