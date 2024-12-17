import dao.AuthorDAO;
import dao.AuthorDAOImpl;
import entity.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AuthorService;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateRunnerTest {

    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        // Инициализация зависимостей
        AuthorDAO authorDAO = new AuthorDAOImpl(HibernateUtil.getSessionFactory());
        authorService = new AuthorService(authorDAO);

        // Заполнение базы данных тестовыми данными
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Добавление тестовых данных
            Author author1 = new Author("Alice", LocalDate.of(1985, 5, 20));
            Author author2 = new Author("Albert", LocalDate.of(1990, 6, 15));
            Author author3 = new Author("Bob", LocalDate.of(1980, 8, 30));

            session.save(author1);
            session.save(author2);
            session.save(author3);

            transaction.commit();
        }
    }

    @Test
    public void testGetAuthorsByInitialLetter() {
        // Тестирование метода поиска авторов по первой букве
        List<Author> authors = authorService.getAuthorsByInitialLetter("A");

        assertNotNull(authors);
        assertTrue(authors.size() > 0);

        // Проверяем, что авторы начинаются с "A"
        assertTrue(authors.stream().allMatch(author -> author.getName().startsWith("A")));
    }
}