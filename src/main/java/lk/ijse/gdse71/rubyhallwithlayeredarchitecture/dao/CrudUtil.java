package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql, Object...args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }

        if(sql.startsWith("SELECT")){
            return (T)pstm.executeQuery();
        } else {
            return (T) (Boolean) (pstm.executeUpdate() > 0);
        }
    }

    public static <T>T executeTransaction(String sql, Connection connection, Object...args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }

        if(sql.startsWith("SELECT")){
            return (T)pstm.executeQuery();
        } else {
            return (T) (Boolean) (pstm.executeUpdate() > 0);
        }
    }
}
