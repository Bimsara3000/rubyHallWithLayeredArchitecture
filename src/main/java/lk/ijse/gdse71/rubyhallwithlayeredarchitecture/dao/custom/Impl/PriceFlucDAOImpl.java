package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PriceFlucDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.PriceFluc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PriceFlucDAOImpl implements PriceFlucDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select priceFluctuationId from priceFluctuation order by priceFluctuationId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PF%03d", newIdIndex);
        }
        return "PF001";
    }

    public ArrayList<PriceFluc> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from priceFluctuation");

        ArrayList<PriceFluc> priceFlucs = new ArrayList<>();

        while (resultSet.next()) {
            PriceFluc priceFluc = new PriceFluc(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            );
            priceFlucs.add(priceFluc);
        }
        return priceFlucs;
    }

    public boolean save(PriceFluc priceFluc) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into facility values (?,?,?,?,?)",
                priceFluc.getPriceFlucId(),
                priceFluc.getDescription(),
                priceFluc.getSDate(),
                priceFluc.getEDate(),
                priceFluc.getPercentage()
        );
    }

    public boolean update(PriceFluc priceFluc) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update priceFluctuation set description=?,sDate=?,eDate=?,percentage=? where priceFluctuationId=?",
                priceFluc.getDescription(),
                priceFluc.getSDate(),
                priceFluc.getEDate(),
                priceFluc.getPercentage(),
                priceFluc.getPriceFlucId()
        );
    }

    public boolean delete(String priceFlucId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from priceFluctuation where priceFlucId = ?",
                priceFlucId
        );
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        return "";
    }
}
