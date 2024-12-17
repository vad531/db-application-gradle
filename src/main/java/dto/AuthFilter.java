package dto;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.UserService;

import java.io.IOException;

@WebFilter("/admin/*")
public class AuthFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            UserDAO userDAO = new UserDAOImpl(sessionFactory);
            this.userService = new UserService(userDAO);
        } catch (Exception e) {
            throw new ServletException("Не удалось инициализировать Hibernate", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            // Проверка, имеет ли пользователь доступ к защищенной странице
            if (userService.hasRole(user.getUserId(), "ADMIN")) {
                chain.doFilter(request, response); // Доступ разрешен
            } else {
                ((HttpServletResponse) response).sendRedirect("/WEB-INF/access-denied.jsp");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("/WEB-INF/login.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}