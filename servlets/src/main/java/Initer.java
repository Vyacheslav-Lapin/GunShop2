import common.ConnectionPool;
import dao.GunDao;
import dao.PersonDao;
import dao.h2.H2GunDao;
import dao.h2.H2PersonDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.util.function.Supplier;

@WebListener
public class Initer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        Supplier<Connection> connectionPool = ConnectionPool.create(pathToDbConfig + "db.properties", pathToDbConfig + "h2.sql");
        PersonDao personDao = new H2PersonDao(connectionPool);
        GunDao gunDao = new H2GunDao(connectionPool);

        context.setAttribute("personDao", personDao);
        context.setAttribute("gunDao", gunDao);
    }
}
