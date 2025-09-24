package org.example;

import org.example.exceptions.FIleDaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {

    private Dao<SudokuBoard> dao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
        connection.setAutoCommit(false);
        dao = SudokuBoardDaoFactory.getDatabaseDao();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM SudokuField");
            stmt.executeUpdate("DELETE FROM SudokuBoard");
        }
        connection.commit();
        connection.close();
    }

    @Test
    public void testWriteValidSudokuBoard() throws SQLException, FIleDaoException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sudoku.db")) {
            conn.setAutoCommit(false);
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            dao.write("testBoard", board);
            conn.commit();

            SudokuBoard retrievedBoard = dao.read("testBoard");

            assertEquals(board,retrievedBoard);

            assertNotNull(retrievedBoard);
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    assertEquals(board.get(row, col), retrievedBoard.get(row, col));
                }
            }
        }
    }

    @Test
    public void testWriteSQLException() {
        assertThrows(SQLException.class, () -> {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sudoku.db")) {
                conn.setAutoCommit(false);
                dao.write(null, new SudokuBoard(new BacktrackingSudokuSolver()));
            }
        });
    }
}