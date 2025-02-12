package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.JobRoleBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.JobRoleDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.JobRoleDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.JobRole;

import java.sql.SQLException;
import java.util.ArrayList;

public class JobRoleBOImpl implements JobRoleBO {
    JobRoleDAO jobRoleDAO = (JobRoleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.JOB_ROLE);

    public JobRoleDTO getJobRole(String jobRoleId) throws SQLException, ClassNotFoundException {
        JobRole jobRole = jobRoleDAO.getJobRole(jobRoleId);
        return new JobRoleDTO(jobRole.getJobRoleId(),jobRole.getName());
    }

    public String getName(String jobRoleId) throws SQLException, ClassNotFoundException {
        return jobRoleDAO.getName(jobRoleId);
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        return jobRoleDAO.getNextId();
    }

    public ArrayList<JobRoleDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<JobRole> jobRoles = jobRoleDAO.getAll();
        ArrayList<JobRoleDTO> jobRoleDTOs = new ArrayList<>();

        for (JobRole jobRole : jobRoles) {
            jobRoleDTOs.add(new JobRoleDTO(jobRole.getJobRoleId(),jobRole.getName()));
        }
        return jobRoleDTOs;
    }

    public ArrayList<String> getJobRoles() throws SQLException, ClassNotFoundException {
        return jobRoleDAO.getJobRoles();
    }

    public String getJobRoleId(String name) throws SQLException, ClassNotFoundException {
        return jobRoleDAO.getJobRoleId(name);
    }
}
