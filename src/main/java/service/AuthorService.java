package service;

import dao.AuthorDAO;
import entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private AuthorDAO authorDAO;

    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public void addAuthor(Author author) {
        logger.info("Добавление автора: {}", author.getName());
        authorDAO.addAuthor(author);
    }

    public Author getAuthorById(int id) {
        logger.info("Получение автора с ID: {}", id);
        return authorDAO.getAuthorById(id);
    }

    public List<Author> getAllAuthors() {
        logger.info("Получение списка всех авторов");
        return authorDAO.getAllAuthors();
    }

    public void updateAuthor(Author author) {
        logger.info("Обновление автора с ID: {}", author.getAuthorId());
        authorDAO.updateAuthor(author);
    }

    public void deleteAuthor(int id) {
        logger.info("Удаление автора с ID: {}", id);
        authorDAO.deleteAuthor(id);
    }
    public List<Author> getAuthorsByInitialLetter(String initial) {
        logger.info("Получение авторов, чьи имена начинаются с: {}", initial);
        return authorDAO.getAuthorsByInitialLetter(initial);
    }
}
