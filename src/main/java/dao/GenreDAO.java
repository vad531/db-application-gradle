package dao;

import entity.Genre;

import java.util.List;

public interface GenreDAO {
    void addGenre(Genre genre);
    Genre getGenreById(int id);
    List<Genre> getAllGenres();
    void updateGenre(Genre genre);
    void deleteGenre(int id);
}