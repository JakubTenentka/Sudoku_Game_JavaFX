package org.example;

import org.example.exceptions.FIleDaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String dirname;
    private boolean isClosed = false;

    public FileSudokuBoardDao(String dirname) {
        this.dirname = dirname;
        new File(dirname).mkdirs();
    }

    @Override
    public SudokuBoard read(String name) throws FIleDaoException {
        if (isClosed) {
            throw new IllegalStateException("Resource is already closed.");
        }

        SudokuBoard obj = null;
        File file = new File(dirname, name);
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            obj = (SudokuBoard) objectIn.readObject();
        } catch (IOException io) {
            throw new FIleDaoException(FIleDaoException.ExceptionType.INPUT_PROBLEM, io);
        } catch (ClassNotFoundException e) {
            throw new FIleDaoException(FIleDaoException.ExceptionType.CLASS_PROBLEM, e);
        }
        return obj;
    }

    @Override
    public void write(String name, SudokuBoard obj) throws FIleDaoException {
        if (isClosed) {
            throw new IllegalStateException("Resource is already closed.");
        }

        File file = new File(dirname, name);
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(obj);
        } catch (FileNotFoundException e) {
            throw new FIleDaoException(FIleDaoException.ExceptionType.FILE_PROBLEM,e);
        } catch (IOException e) {
            throw new FIleDaoException(FIleDaoException.ExceptionType.INPUT_PROBLEM,e);
        }
    }

    @Override
    public List<String> names() {
        if (isClosed) {
            throw new IllegalStateException("Resource is already closed.");
        }

        File directory = new File(dirname);
        String[] files = directory.list();
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (String file : files) {
                if (new File(directory, file).isFile()) {
                    fileNames.add(file);
                }
            }
        }
        return fileNames;
    }

    @Override
    public void close() {
        if (!isClosed) {
            isClosed = true;
        }
    }
}
