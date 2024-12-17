package service;

import dao.BookDAO;
import entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void addBook(Book book) {
        logger.info("Добавление книги: {}", book.getTitle());
        bookDAO.addBook(book);
    }

    public Book getBookById(int id) {
        logger.info("Получение книги с ID: {}", id);
        return bookDAO.getBookById(id);
    }

    public List<Book> getAllBooks() {
        logger.info("Получение списка всех книг");
        return bookDAO.getAllBooks();
    }

    public void updateBook(Book book) {
        logger.info("Обновление книги с ID: {}", book.getBookId());
        bookDAO.updateBook(book);
    }

    public void deleteBook(int id) {
        logger.info("Удаление книги с ID: {}", id);
        bookDAO.deleteBook(id);
    }
}
