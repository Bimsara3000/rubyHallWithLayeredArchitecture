package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.JobRoleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface JobRoleBO extends SuperBO {
    public JobRoleDTO getJobRole(String jobRoleId) throws SQLException, ClassNotFoundException;
    public String getName(String jobRoleId) throws SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<JobRoleDTO> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getJobRoles() throws SQLException, ClassNotFoundException;
    public String getJobRoleId(String name) throws SQLException, ClassNotFoundException;
    public boolean save(JobRoleDTO jobRoleDTO) throws SQLException, ClassNotFoundException;
    public boolean update(JobRoleDTO jobRoleDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String jobRoleId) throws SQLException, ClassNotFoundException;
}
