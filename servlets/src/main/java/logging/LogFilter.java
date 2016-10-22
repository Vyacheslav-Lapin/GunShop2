package logging;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Log
@WebFilter("/catalog/")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Мы пришли в метод doGet!");
        chain.doFilter(request, response);
        log.info("Мы ушли из метода doGet!");
    }
}
