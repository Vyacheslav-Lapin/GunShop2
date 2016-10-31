package dao.h2;

import dao.GunDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Gun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
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

    @SneakyThrows
    @Override
    public Optional<Gun> getById(long id) {
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement("SELECT name, caliber FROM Gun WHERE id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ?
                        new Gun(id,
                                resultSet.getString("name"),
                                resultSet.getDouble("caliber")) :
                        null);
            }
        }
    }
}
