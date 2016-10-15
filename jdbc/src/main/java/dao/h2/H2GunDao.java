package dao.h2;

import dao.GunDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Gun;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@AllArgsConstructor
public class H2GunDao implements GunDao {

    private Supplier<Connection> connectionSupplier;

    @SneakyThrows
    @Override
    public Collection<Gun> getAll() {
        Collection<Gun> guns = new HashSet<>();
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, caliber FROM Gun")) {
            while (resultSet.next())
                guns.add(
                        new Gun(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("caliber")
                        ));
        }
        return guns;
    }
}
