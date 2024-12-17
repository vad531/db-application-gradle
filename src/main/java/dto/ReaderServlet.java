package dto;

import dao.ReaderDAO;
import dao.ReaderDAOImpl;
import entity.Reader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.ReaderService;

import java.io.IOException;
import java.util.List;

@WebServlet("/readers")
public class ReaderServlet extends HttpServlet {

    private ReaderService readerService;

    @Override
    public void init() throws ServletException {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            ReaderDAO readerDAO = new ReaderDAOImpl(sessionFactory);
            readerService = new ReaderService(readerDAO);
        } catch (Exception e) {
            throw new ServletException("Не удалось инициализировать Hibernate", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reader> readers = readerService.getAllReaders();

        req.setAttribute("readers", readers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/readers.jsp");
        dispatcher.forward(req, resp);
    }
}