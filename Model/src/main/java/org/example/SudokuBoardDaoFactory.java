package org.example;

import java.sql.SQLException;

public class SudokuBoardDaoFactory {

    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String dirname) {
        return new FileSudokuBoardDao(dirname);
    }

    public static Dao<SudokuBoard> getDatabaseDao() throws SQLException {
        return new JdbcSudokuBoardDao();
    }
}
