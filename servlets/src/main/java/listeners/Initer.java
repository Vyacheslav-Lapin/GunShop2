package listeners;

import com.hegel.core.StringEncryptUtil;
import common.ConnectionPool;
import dao.h2.H2GunDao;
import dao.h2.H2InstanceDao;
import dao.h2.H2PersonDao;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Supplier;

import static com.hegel.core.functions.ExceptionalSupplier.toUncheckedSupplier;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@WebListener
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Initer implements ServletContextListener {

    public static String PERSON_DAO = "personDao";
    public static String GUN_DAO = "gunDao";
    public static String INSTANCE_DAO = "instanceDao";

    @NonFinal
    @Resource(name = "jdbc/TestDB")
    DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ConnectionPool connectionPool = ConnectionPool.create(
                toUncheckedSupplier(dataSource::getConnection),
                context.getRealPath("/WEB-INF/classes/h2.sql")
        );

//        encryptPasswords(connectionPool);

        context.setAttribute(PERSON_DAO, (H2PersonDao) connectionPool::get);
        context.setAttribute(GUN_DAO, (H2GunDao) connectionPool::get);
        context.setAttribute(INSTANCE_DAO, (H2InstanceDao) connectionPool::get);
    }

    @SneakyThrows
    private void encryptPasswords(Supplier<Connection> connectionSupplier) {

        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, password FROM Person");
             Statement statement1 = connection.createStatement()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String password = resultSet.getString("password");
                statement1.addBatch(
                        "UPDATE Person SET password = '" +
                                StringEncryptUtil.encrypt(password) +
                                "' WHERE id = " + id);
            }

            statement1.executeBatch();
        }
    }
}
