package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.UserBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.UserDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.UserDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance(DAOFactory.DAOType.USER);

    public UserDTO checkDetails(String uId) throws SQLException, ClassNotFoundException {
        User user = userDAO.checkDetails(uId);

        return new UserDTO(user.getUserId(),user.getJobRoleId(),user.getEmail(),user.getPassword());
    }

    public String getName(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.getName(userId);
    }

    public ArrayList<UserDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = userDAO.getAll();

        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUserId(),user.getJobRoleId(),user.getEmail(),user.getPassword()));
        }
        return userDTOS;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return userDAO.getNextId();
    }

    public boolean save(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(userDTO.getUserId(),userDTO.getJobRoleId(),userDTO.getName(),userDTO.getEmail(),userDTO.getPassword()));
    }

    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDTO.getUserId(),userDTO.getJobRoleId(),userDTO.getName(),userDTO.getEmail(),userDTO.getPassword()));
    }

    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userId);
    }
}
