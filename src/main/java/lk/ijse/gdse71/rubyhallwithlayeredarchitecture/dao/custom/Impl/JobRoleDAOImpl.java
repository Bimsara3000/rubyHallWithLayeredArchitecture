package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.JobRoleDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.JobRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobRoleDAOImpl implements JobRoleDAO {

    public JobRole getJobRole(String jobRoleId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from jobRole where jobRoleId = ?", jobRoleId);

        if (resultSet.next()) {
            JobRole jobRole = new JobRole();
            jobRole.setJobRoleId(resultSet.getString("jobRoleId"));
            jobRole.setName(resultSet.getString("name"));

            return jobRole;
        }

        return null;
    }

    public String getName(String jobRoleId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select name from jobRole where jobRoleId = ?", jobRoleId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean save(JobRole dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(JobRole dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public ArrayList<JobRole> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from jobRole");

        ArrayList<JobRole> jobRoles = new ArrayList<>();

        while (resultSet.next()) {
            JobRole jobRole = new JobRole(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            jobRoles.add(jobRole);
        }
        return jobRoles;
    }

    public ArrayList<String> getJobRoles() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select name from jobRole");

        ArrayList<String> jobRoles = new ArrayList<>();

        while (resultSet.next()) {
            jobRoles.add(resultSet.getString(1));
        }
        return jobRoles;
    }

    public String getJobRoleId(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select jobRoleId from jobRole where name = ?", name);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
