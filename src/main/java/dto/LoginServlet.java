package dto;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            // Инициализация Hibernate SessionFactory
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            // Создаем DAO и сервисный слой
            UserDAO userDAO = new UserDAOImpl(sessionFactory);
            this.userService = new UserService(userDAO);
        } catch (Exception e) {
            logger.error("Ошибка инициализации Hibernate", e);
            throw new ServletException("Не удалось инициализировать Hibernate", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {

            User user = userService.login(username, password);

            if (user != null) {
                logger.info("Пользователь {} вошел в систему", username);
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                resp.sendRedirect("/WEB-INF/index.jsp");
            } else {
                logger.warn("Неудачная попытка входа для {}", username);
                resp.sendRedirect("/WEB-INF/login.jsp?error=true");
            }
        } catch (Exception e) {
            logger.error("Ошибка во время авторизации пользователя {}", username, e);
            resp.sendRedirect("/WEB-INF/login.jsp?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
    }
}