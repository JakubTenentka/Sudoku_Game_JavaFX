package org.example;

import java.sql.*;
import java.util.List;

public class JdbcSudokuBoardDao implements AutoCloseable, Dao<SudokuBoard> {
public static final String DRIVER = "org.sqlite.JDBC";
public static final String DB_URL = "jdbc:sqlite:sudoku.db";

    private Connection connection;


    public JdbcSudokuBoardDao() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Failed to load JDBC driver.", e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.createStatement();
        } catch (SQLException  e) {
            throw new SQLException("Problem with opening connection");
        }

        createTables();
    }

    private void createTables() throws SQLException {
        String createSudokuBoardTable = """
            CREATE TABLE IF NOT EXISTS SudokuBoard (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE
            );
        """;

        String createSudokuFieldTable = """
            CREATE TABLE IF NOT EXISTS SudokuField (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                row INTEGER NOT NULL,
                column INTEGER NOT NULL,
                value INTEGER NOT NULL,
                board_id INTEGER NOT NULL,
                FOREIGN KEY (board_id) REFERENCES SudokuBoard (id)
                    ON DELETE CASCADE
                    ON UPDATE NO ACTION
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSudokuBoardTable);
            stmt.execute(createSudokuFieldTable);
        }
    }

    @Override
    public void write(String name, SudokuBoard obj) throws SQLException {
        if (!(obj instanceof SudokuBoard board)) {
            throw new IllegalArgumentException("Only SudokuBoard objects can be saved.");
        }

        String insertBoardQuery = "INSERT INTO SudokuBoard (name) VALUES (?)";
        String insertFieldQuery = """
            INSERT INTO SudokuField (row, column, value, board_id) 
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement boardStmt = connection.prepareStatement(insertBoardQuery,
                Statement.RETURN_GENERATED_KEYS);
             PreparedStatement fieldStmt = connection.prepareStatement(insertFieldQuery)) {

            boardStmt.setString(1, name);
            boardStmt.executeUpdate();

            ResultSet generatedKeys = boardStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Failed to retrieve board ID.");
            }
            int boardId = generatedKeys.getInt(1);

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    fieldStmt.setInt(1, row);
                    fieldStmt.setInt(2, col);
                    fieldStmt.setInt(3, board.get(row, col));
                    fieldStmt.setInt(4, boardId);
                    fieldStmt.addBatch();
                }
            }
            fieldStmt.executeBatch();
        }
    }

    @Override
    public List<String> names() {
        return List.of();
    }


    public SudokuBoard read(String name) throws SQLException {
        String getBoardIdQuery = "SELECT id FROM SudokuBoard WHERE name = ?";
        String getFieldsQuery = "SELECT row, column, value FROM SudokuField WHERE board_id = ?";

        try (PreparedStatement boardStmt = connection.prepareStatement(getBoardIdQuery);
             PreparedStatement fieldStmt = connection.prepareStatement(getFieldsQuery)) {

            boardStmt.setString(1, name);
            ResultSet boardResultSet = boardStmt.executeQuery();

            if (!boardResultSet.next()) {
                throw new SQLException("Board with name '" + name + "' not found.");
            }
            int boardId = boardResultSet.getInt("id");

            fieldStmt.setInt(1, boardId);
            ResultSet fieldResultSet = fieldStmt.executeQuery();

            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            while (fieldResultSet.next()) {
                int row = fieldResultSet.getInt("row");
                int col = fieldResultSet.getInt("column");
                int value = fieldResultSet.getInt("value");
                board.set(row, col, value);
            }
            return board;
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}



