package service;

import dao.GenreDAO;
import entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenreService {
    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private GenreDAO genreDAO;

    public GenreService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public void addGenre(Genre genre) {
        logger.info("Добавление жанра: {}", genre.getName());
        genreDAO.addGenre(genre);
    }

    public Genre getGenreById(int id) {
        logger.info("Получение жанра с ID: {}", id);
        return genreDAO.getGenreById(id);
    }

    public List<Genre> getAllGenres() {
        logger.info("Получение списка всех жанров");
        return genreDAO.getAllGenres();
    }

    public void updateGenre(Genre genre) {
        logger.info("Обновление жанра с ID: {}", genre.getGenreId());
        genreDAO.updateGenre(genre);
    }

    public void deleteGenre(int id) {
        logger.info("Удаление жанра с ID: {}", id);
        genreDAO.deleteGenre(id);
    }
}
