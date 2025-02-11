package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.SuperBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PriceFlucDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PriceFlucBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public ArrayList<PriceFlucDTO> getAll() throws SQLException, ClassNotFoundException;
    public boolean save(PriceFlucDTO priceFlucDTO) throws SQLException, ClassNotFoundException;
    public boolean update(PriceFlucDTO priceFlucDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String priceFlucId) throws SQLException, ClassNotFoundException;
}
