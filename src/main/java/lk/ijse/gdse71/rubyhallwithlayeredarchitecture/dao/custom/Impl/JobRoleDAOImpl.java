package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.JobRoleDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobRoleDAOImpl {

    public JobRoleDTO getJobRole(String jobRoleId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from jobRole where jobRoleId = ?", jobRoleId);

        if (resultSet.next()) {
            JobRoleDTO jobRoleDTO = new JobRoleDTO();
            jobRoleDTO.setJobRoleId(resultSet.getString("jobRoleId"));
            jobRoleDTO.setName(resultSet.getString("name"));

            return jobRoleDTO;
        }

        return null;
    }

    public String getJobRoleName(String jobRoleId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from jobRole where jobRoleId = ?", jobRoleId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextJobRoleId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select jobRoleId from jobRole order by jobRoleId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("J%03d", newIdIndex);
        }
        return "J001";
    }

    public ArrayList<JobRoleDTO> getAllJobRoles() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from jobRole");

        ArrayList<JobRoleDTO> jobRoleDTOS = new ArrayList<>();

        while (resultSet.next()) {
            JobRoleDTO jobRoleDTO = new JobRoleDTO(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            jobRoleDTOS.add(jobRoleDTO);
        }
        return jobRoleDTOS;
    }

    public ArrayList<String> getJobRoles() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from jobRole");

        ArrayList<String> jobRoles = new ArrayList<>();

        while (resultSet.next()) {
            jobRoles.add(resultSet.getString(1));
        }
        return jobRoles;
    }

    public String getJobRoleId(String name) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select jobRoleId from jobRole where name = ?", name);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
