package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.JobRoleDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.JobRole;

import java.sql.SQLException;
import java.util.ArrayList;

public interface JobRoleDAO extends CrudDAO<JobRole> {
    public JobRole getJobRole(String jobRoleId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getJobRoles() throws SQLException, ClassNotFoundException;
    public String getJobRoleId(String name) throws SQLException, ClassNotFoundException;
}
