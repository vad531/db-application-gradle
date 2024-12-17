import dao.*;
import entity.Author;
import entity.Book;
import entity.Genre;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.time.LocalDate;
import java.util.List;

public class HibernateRunner {
    public static void main(String[] args) {
        // Инициализация SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Создаем DAO и сервисы
        GenreDAO genreDAO = new GenreDAOImpl(sessionFactory);
        BookDAO bookDAO = new BookDAOImpl(sessionFactory);
        AuthorDAO authorDAO = new AuthorDAOImpl(sessionFactory);

        GenreService genreService = new GenreService(genreDAO);
        BookService bookService = new BookService(bookDAO);
        AuthorService authorService = new AuthorService(authorDAO);

        // Добавляем жанр
        Genre genre = new Genre();
        genre.setName("Фэнтази");
        genreService.addGenre(genre);

        // Получаем и обновляем жанр
        List<Genre> genres = genreService.getAllGenres();
        genres.forEach(System.out::println);

        Genre fetchedGenre = genreService.getGenreById(genre.getGenreId());
        if (fetchedGenre != null) {
            fetchedGenre.setName("Фантастика");
            genreService.updateGenre(fetchedGenre);
        } else {
            System.out.println("Жанр не найден.");
        }

        // Удаляем жанр
        if (fetchedGenre != null) {
            genreService.deleteGenre(fetchedGenre.getGenreId());
        }

        // Создаем и добавляем автора
        Author author = new Author("J.R.R. Tolkien", LocalDate.of(1892, 1, 3));
        authorService.addAuthor(author);

        // Получаем автора
        Author fetchedAuthor = authorService.getAuthorById(author.getAuthorId());
        if (fetchedAuthor != null) {
            // Создаем книгу с установленными автором и жанром
            Book book = new Book();
            book.setTitle("Хоббит");
            book.setAuthor(fetchedAuthor); // Устанавливаем автора
            book.setGenre(fetchedGenre);   // Устанавливаем жанр
            book.setPublishedYear(1937);
            book.setQuantity(10);

            // Добавляем книгу в базу данных
            bookService.addBook(book);

            // Получаем и обновляем книгу
            List<Book> books = bookService.getAllBooks();
            books.forEach(System.out::println);

            Book fetchedBook = bookService.getBookById(book.getBookId());
            if (fetchedBook != null) {
                fetchedBook.setTitle("Хоббит: Начало");
                bookService.updateBook(fetchedBook);
            } else {
                System.out.println("Книга не найдена.");
            }
        } else {
            System.out.println("Автор не найден.");
        }

        // Закрываем сессию
        sessionFactory.close();
    }
}
