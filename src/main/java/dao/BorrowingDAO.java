package dao;

import entity.Borrowing;

import java.util.List;

public interface BorrowingDAO {
    void addBorrowing(Borrowing borrowing);
    Borrowing getBorrowingById(int id);
    List<Borrowing> getAllBorrowings();
    void updateBorrowing(Borrowing borrowing);
    void deleteBorrowing(int id);
}
