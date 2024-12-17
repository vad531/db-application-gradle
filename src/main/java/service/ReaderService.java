package service;

import dao.ReaderDAO;
import entity.Reader;

import java.util.List;

public class ReaderService {
    private ReaderDAO readerDAO;

    public ReaderService(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
    }

    public List<Reader> getAllReaders() {
        return readerDAO.getAllReaders();
    }
}
