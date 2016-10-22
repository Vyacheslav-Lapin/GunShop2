import com.hegel.core.functions.ExceptionalConsumer;
import dao.GunDao;
import dao.PersonDao;
import dao.h2.H2GunDao;
import dao.h2.H2PersonDao;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@WebListener
public class Initer implements ServletContextListener {

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        Supplier<Connection> connectionPool;
//        connectionPool = ConnectionPool.create(pathToDbConfig + "db.properties", pathToDbConfig + "h2.sql");

        connectionPool = getConnectionSupplier();

        initDb(connectionPool, pathToDbConfig + "h2.sql");

        PersonDao personDao = new H2PersonDao(connectionPool);
        GunDao gunDao = new H2GunDao(connectionPool);

        context.setAttribute("personDao", personDao);
        context.setAttribute("gunDao", gunDao);
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
        }
    }

    private Supplier<Connection> getConnectionSupplier() throws NamingException {
        Context initContext = new InitialContext();
        Context envContext  = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
        return () -> {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
