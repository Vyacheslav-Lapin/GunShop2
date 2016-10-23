package controllers;

import dao.GunDao;
import listeners.Initer;
import lombok.extern.java.Log;
import model.Gun;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Log
@WebServlet("/catalog/")
public class Catalog extends HttpServlet {

    public static final String GUNS = "guns";
    private GunDao gunDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        gunDao = (GunDao) servletContext.getAttribute(Initer.GUN_DAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Gun> guns = gunDao.getAll();
        req.setAttribute(GUNS, guns);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/catalog/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
