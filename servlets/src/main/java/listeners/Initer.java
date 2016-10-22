package listeners;

import com.hegel.core.functions.ExceptionalConsumer;
import com.hegel.core.functions.ExceptionalSupplier;
import dao.GunDao;
import dao.InstanceDao;
import dao.PersonDao;
import dao.h2.H2GunDao;
import dao.h2.H2InstanceDao;
import dao.h2.H2PersonDao;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@WebListener
public class Initer implements ServletContextListener {

    public static final String PERSON_DAO = "personDao";
    public static final String GUN_DAO = "gunDao";
    public static final String INSTANCE_DAO = "instanceDao";

    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        Supplier<Connection> connectionPool = ExceptionalSupplier.toUncheckedSupplier(dataSource::getConnection);
        ServletContext context = sce.getServletContext();
        initDb(connectionPool, context.getRealPath("/") + "WEB-INF/classes/h2.sql");

        PersonDao personDao = new H2PersonDao(connectionPool);
        GunDao gunDao = new H2GunDao(connectionPool);
        InstanceDao instanceDao = new H2InstanceDao(connectionPool);

        context.setAttribute(PERSON_DAO, personDao);
        context.setAttribute(GUN_DAO, gunDao);
        context.setAttribute(INSTANCE_DAO, instanceDao);
    }

    @SneakyThrows
    private void initDb(Supplier<Connection> connectionPool, String pathToInitScript) {

        try (Connection connection = connectionPool.get();
             Statement statement = connection.createStatement()) {

            Arrays.stream(
                    Files.lines(Paths.get(pathToInitScript))
                            .collect(Collectors.joining())
                            .split(";"))
                    .forEachOrdered(ExceptionalConsumer.toUncheckedConsumer(statement::addBatch));

            statement.executeBatch();

//            try (ResultSet resultSet = statement.executeQuery("SELECT id, password FROM Person");
//                Statement statement1 = connection.createStatement()) {
//                while (resultSet.next()) {
//                    String id = resultSet.getString("id");
//                    String password = resultSet.getString("password");
//                    statement1.addBatch(
//                            "UPDATE Person SET password = '" +
//                            StringEncryptUtil.encrypt(password) +
//                            "' WHERE id = " + id);
//                }
//                statement1.executeBatch();
//            }
        }
    }
}
