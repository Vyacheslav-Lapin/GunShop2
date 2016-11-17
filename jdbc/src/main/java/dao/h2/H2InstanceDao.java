package dao.h2;

import dao.InstanceDao;
import lombok.SneakyThrows;
import model.Gun;
import model.Instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@FunctionalInterface
public interface H2InstanceDao extends InstanceDao {

    @SneakyThrows
    @Override
    default Collection<Instance> getAll() {
        Collection<Instance> instances = new HashSet<>();
        String sql = "SELECT i.id, i.model_id, g.name, g.caliber FROM Instance i, Gun g WHERE i.model_id = g.id";
        try (Connection connection = get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next())
                instances.add(
                        new Instance(
                                resultSet.getLong("id"),
                                new Gun(resultSet.getLong("model_id"),
                                        resultSet.getString("name"),
                                        resultSet.getDouble("caliber"))
                        ));
        }
        return instances;
    }

    @SneakyThrows
    @Override
    default Optional<Instance> getById(long id) {
        String sql = "SELECT i.model_id id, g.name, g.caliber FROM Instance i, Gun g WHERE i.id = ? AND i.model_id = g.id";
        try (Connection connection = get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(resultSet.next() ? Instance.getFrom(resultSet) : null);
            }
        }
    }
}
