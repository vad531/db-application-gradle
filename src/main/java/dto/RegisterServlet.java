package dto;

import dao.UserDAO;
import dao.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            UserDAO userDAO = new UserDAOImpl(sessionFactory);
            userService = new UserService(userDAO);
        } catch (Exception e) {
            throw new ServletException("Не удалось инициализировать Hibernate", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            userService.registerUser(username, password);
            logger.info("Пользователь {} успешно зарегистрировался", username);
            resp.sendRedirect("/WEB-INF/login.jsp");
        } catch (Exception e) {
            logger.error("Ошибка регистрации пользователя {}: {}", username, e.getMessage());
            resp.sendRedirect("/WEB-INF/register.jsp?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }
}