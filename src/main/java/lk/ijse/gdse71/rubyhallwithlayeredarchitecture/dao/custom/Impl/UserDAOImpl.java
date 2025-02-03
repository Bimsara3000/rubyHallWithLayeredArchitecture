package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.UserDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl {

    public UserDTO checkDetails(String uId) throws SQLException {
        ResultSet details = CrudUtil.execute("select * from user where userId = ?", uId);

        if (details.next()) {
            UserDTO loginDTO = new UserDTO();
            loginDTO.setUserId(details.getString("userId"));
            loginDTO.setJobRoleId(details.getString("jobRoleId"));
            loginDTO.setEmail(details.getString("email"));
            loginDTO.setPassword(details.getString("password"));

            return loginDTO;
        }

        return null;
    }

    public String getUserName(String userId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from user where userID = ?", userId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from user");

        ArrayList<UserDTO> users = new ArrayList<>();

        while (resultSet.next()) {
            UserDTO userDTO = new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            users.add(userDTO);
        }
        return users;
    }

    public String getNextUserId() throws SQLException {
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

    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into user values (?,?,?,?,?)",
                userDTO.getUserId(),
                userDTO.getJobRoleId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        );
    }

    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "update user set jobRoleId=?,name=?,email=?,password=? where userId=?",
                userDTO.getJobRoleId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getUserId()
        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute(
                "delete from user where userId = ?",
                userId
        );
    }
}
