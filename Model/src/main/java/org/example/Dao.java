package org.example;

import org.example.exceptions.FIleDaoException;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> extends AutoCloseable {

     T read(String name) throws FIleDaoException, SQLException;

     void write(String name, T obj) throws FIleDaoException, SQLException;

     List<String> names();

     @Override
     void close() throws Exception;
}
