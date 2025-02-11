package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.UserDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    public User checkDetails(String uId) throws SQLException, ClassNotFoundException {
        ResultSet details = CrudUtil.execute("select * from user where userId = ?", uId);

        if (details.next()) {
            User login = new User();
            login.setUserId(details.getString("userId"));
            login.setJobRoleId(details.getString("jobRoleId"));
            login.setEmail(details.getString("email"));
            login.setPassword(details.getString("password"));

            return login;
        }

        return null;
    }

    public String getName(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select name from user where userID = ?", userId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from user");

        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            users.add(user);
        }
        return users;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select userId from user order by userId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into user values (?,?,?,?,?)",
                user.getUserId(),
                user.getJobRoleId(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update user set jobRoleId=?,name=?,email=?,password=? where userId=?",
                user.getJobRoleId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId()
        );
    }

    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from user where userId = ?",
                userId
        );
    }
}
