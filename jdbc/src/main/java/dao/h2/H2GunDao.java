package dao.h2;

import dao.GunDao;
import lombok.SneakyThrows;
import lombok.val;
import model.Gun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@FunctionalInterface
public interface H2GunDao extends GunDao {

    @SneakyThrows
    @Override
    default Collection<Gun> getAll() {
        val guns = new HashSet<Gun>();
        try (Connection connection = get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, caliber FROM Gun")) {
            while (resultSet.next())
                guns.add(Gun.getFrom(resultSet));
        }
        return guns;
    }

    @SneakyThrows
    @Override
    default Optional<Gun> getById(long id) {
        try (Connection connection = get();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name, caliber FROM Gun WHERE id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ? Gun.getFrom(resultSet) : null);
            }
        }
    }
}
