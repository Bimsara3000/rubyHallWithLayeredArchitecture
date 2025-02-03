package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.projectrubyhall.dto.PaymentTypeDTO;
import lk.ijse.gdse71.projectrubyhall.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentTypeDAOImpl {
    public String getPaymentDescription(String paymentTypeId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select description from paymentType where paymentTypeId = ?", paymentTypeId);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public String getNextPaymentTypeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select paymentTypeId from paymentType order by paymentTypeId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PT%03d", newIdIndex);
        }
        return "PT001";
    }

    public ArrayList<PaymentTypeDTO> getAllPaymentTypes() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from paymentType");

        ArrayList<PaymentTypeDTO> paymentTypeDTOS = new ArrayList<>();

        while (resultSet.next()) {
            PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            paymentTypeDTOS.add(paymentTypeDTO);
        }
        return paymentTypeDTOS;
    }

    public boolean savePaymentType(PaymentTypeDTO paymentTypeDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into paymentType values (?,?)",
                paymentTypeDTO.getPaymentTypeId(),
                paymentTypeDTO.getDescription()
        );
    }

    public boolean updatePaymentType(PaymentTypeDTO paymentTypeDTO) throws SQLException {
        return CrudUtil.execute(
                "update paymentType set description=? where paymentTypeDTO=?",
                paymentTypeDTO.getDescription(),
                paymentTypeDTO.getPaymentTypeId()
        );
    }

    public boolean deletePaymentType(String paymentTypeId) throws SQLException {
        return CrudUtil.execute(
                "delete from paymentType where paymentTypeId = ?",
                paymentTypeId
        );
    }
}
