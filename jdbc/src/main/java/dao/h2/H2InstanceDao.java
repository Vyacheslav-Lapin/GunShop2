package dao.h2;

import dao.InstanceDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import model.Gun;
import model.Instance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@AllArgsConstructor
public class H2InstanceDao implements InstanceDao {

    private Supplier<Connection> connectionSupplier;

    @SneakyThrows
    @Override
    public Collection<Instance> getAll() {
        Collection<Instance> instances = new HashSet<>();
        String sql = "SELECT i.id, i.model_id, g.name, g.caliber FROM Instance i, Gun g WHERE i.model_id = g.id";
        try (Connection connection = connectionSupplier.get();
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
}
