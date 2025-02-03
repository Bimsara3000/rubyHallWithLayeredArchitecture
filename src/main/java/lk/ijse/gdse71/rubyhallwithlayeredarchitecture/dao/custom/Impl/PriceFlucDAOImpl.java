package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.PriceFlucDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PriceFlucDAOImpl {
    public String getNextPriceFlucId() throws SQLException {
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

    public ArrayList<PriceFlucDTO> getAllPriceFluctuations() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from priceFluctuation");

        ArrayList<PriceFlucDTO> priceFlucDTOS = new ArrayList<>();

        while (resultSet.next()) {
            PriceFlucDTO priceFlucDTO = new PriceFlucDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            );
            priceFlucDTOS.add(priceFlucDTO);
        }
        return priceFlucDTOS;
    }

    public boolean savePriceFluctuation(PriceFlucDTO priceFlucDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into facility values (?,?,?,?,?)",
                priceFlucDTO.getPriceFlucId(),
                priceFlucDTO.getDescription(),
                priceFlucDTO.getSDate(),
                priceFlucDTO.getEDate(),
                priceFlucDTO.getPercentage()
        );
    }

    public boolean updatePriceFluctuation(PriceFlucDTO priceFlucDTO) throws SQLException {
        return CrudUtil.execute(
                "update priceFluctuation set description=?,sDate=?,eDate=?,percentage=? where priceFluctuationId=?",
                priceFlucDTO.getDescription(),
                priceFlucDTO.getSDate(),
                priceFlucDTO.getEDate(),
                priceFlucDTO.getPercentage(),
                priceFlucDTO.getPriceFlucId()
        );
    }

    public boolean deletePriceFluc(String priceFlucId) throws SQLException {
        return CrudUtil.execute(
                "delete from priceFluctuation where priceFlucId = ?",
                priceFlucId
        );
    }
}
