package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.bo.custom.PriceFlucBO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.PriceFlucDAO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dto.PriceFlucDTO;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.entity.PriceFluc;

import java.sql.SQLException;
import java.util.ArrayList;

public class PriceFlucBOImpl implements PriceFlucBO {
    PriceFlucDAO priceFlucDAO = (PriceFlucDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRICE_FLUC);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return priceFlucDAO.getNextId();
    }

    public ArrayList<PriceFlucDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<PriceFluc> priceFlucs = priceFlucDAO.getAll();
        ArrayList<PriceFlucDTO> priceFlucDTOs = new ArrayList<>();

        for (PriceFluc priceFluc : priceFlucs) {
            PriceFlucDTO priceFlucDTO = new PriceFlucDTO(
                    priceFluc.getPriceFlucId(),
                    priceFluc.getDescription(),
                    priceFluc.getSDate(),
                    priceFluc.getEDate(),
                    priceFluc.getPercentage()
            );
            priceFlucDTOs.add(priceFlucDTO);
        }
        return priceFlucDTOs;
    }

    public boolean save(PriceFlucDTO priceFlucDTO) throws SQLException, ClassNotFoundException {
        return priceFlucDAO.save(new PriceFluc(priceFlucDTO.getPriceFlucId(),priceFlucDTO.getDescription(),priceFlucDTO.getSDate(),priceFlucDTO.getEDate(),priceFlucDTO.getPercentage()));
    }

    public boolean update(PriceFlucDTO priceFlucDTO) throws SQLException, ClassNotFoundException {
        return priceFlucDAO.update(new PriceFluc(priceFlucDTO.getPriceFlucId(),priceFlucDTO.getDescription(),priceFlucDTO.getSDate(),priceFlucDTO.getEDate(),priceFlucDTO.getPercentage()));
    }

    public boolean delete(String priceFlucId) throws SQLException, ClassNotFoundException {
        return priceFlucDAO.delete(priceFlucId);
    }
}
