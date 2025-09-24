package org.example;

import org.apache.commons.io.FileUtils;
import org.example.exceptions.FIleDaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.apache.http.client.methods.RequestBuilder.delete;
import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {

    private Dao<SudokuBoard> fileSudokuBoardDao;
    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board1 = new SudokuBoard(solver);
    SudokuBoard board2;

    @Test
    public void testWriteAndReadSudokuBoard() throws FIleDaoException {
        try {

            try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("daoDirectory")) {
                fileSudokuBoardDao.write("board1", board1);
                board2 = fileSudokuBoardDao.read("board1");
                Assertions.assertEquals(board1, board2);
            }
            FileUtils.deleteDirectory(new File("daoDirectory"));
        } catch (Exception e) {
            throw new FIleDaoException();
        }
    }

    @Test
    public void namesTest() throws FIleDaoException {
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("daoDirectory")) {
            fileSudokuBoardDao.write("board1", board1);

            Assertions.assertEquals("board1", fileSudokuBoardDao.names().get(0));
        } catch (Exception e) {
            throw new FIleDaoException();
        }
    }



}
