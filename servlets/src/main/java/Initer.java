import com.hegel.core.functions.ExceptionalConsumer;
import com.hegel.core.functions.ExceptionalSupplier;
import dao.GunDao;
import dao.PersonDao;
import dao.h2.H2GunDao;
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
}
