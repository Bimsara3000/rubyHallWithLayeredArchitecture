package lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.Impl;

import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.CrudUtil;
import lk.ijse.gdse71.rubyhallwithlayeredarchitecture.dao.custom.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public String getPaymentAmount(String reservationId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT\n" +
                        "    (p.price +\n" +
                        "     COALESCE(service_total, 0) +\n" +
                        "     COALESCE(facility_total, 0)) AS total_price\n" +
                        "FROM reservation r\n" +
                        "         JOIN package p ON r.packageId = p.packageId\n" +
                        "\n" +
                        "         LEFT JOIN (\n" +
                        "    SELECT rs.reservationId, SUM(s.price * rs.duration) AS service_total\n" +
                        "    FROM reservationService rs\n" +
                        "             JOIN service s ON rs.serviceId = s.serviceId\n" +
                        "    GROUP BY rs.reservationId\n" +
                        ") service_calc ON r.reservationId = service_calc.reservationId\n" +
                        "\n" +
                        "         LEFT JOIN (\n" +
                        "    SELECT rr.reservationId, SUM(f.price) AS facility_total\n" +
                        "    FROM reservationRoom rr\n" +
                        "             JOIN roomFacility rf ON rr.roomId = rf.roomId\n" +
                        "             JOIN facility f ON rf.facilityId = f.facilityId\n" +
                        "    GROUP BY rr.reservationId\n" +
                        ") facility_calc ON r.reservationId = facility_calc.reservationId\n" +
                        "\n" +
                        "WHERE r.reservationId = ?",
                reservationId
        );

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllAvailableRooms(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT r.roomId, r.roomTypeId, r.floorId\n" +
                        "FROM room r\n" +
                        "         LEFT JOIN reservationRoom rr\n" +
                        "                   ON r.roomId = rr.roomId\n" +
                        "                       AND (\n" +
                        "                          rr.startDate <= ?\n" +
                        "                              AND rr.endDate >= ?\n" +
                        "                          )\n" +
                        "WHERE rr.roomId IS NULL",
                endDate,
                startDate
        );

        ArrayList<String> availableRooms = new ArrayList<>();

        while (resultSet.next()){
            availableRooms.add(resultSet.getString(1));
        }

        return availableRooms;
    }

    @Override
    public ArrayList<String> searchAllAvailableRooms(String startDate, String endDate, String roomTypeId, String floorId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT r.roomId FROM room r " +
                        "LEFT JOIN reservationRoom rr " +
                        "ON r.roomId = rr.roomId " +
                        "AND (rr.startDate <= ? AND rr.endDate >= ?) " +
                        "WHERE rr.roomId IS NULL " +
                        "AND r.roomTypeId = ? " +
                        "AND r.floorId = ?",
                endDate,
                startDate,
                roomTypeId,
                floorId
        );

        ArrayList<String> availableRooms = new ArrayList<>();

        while (resultSet.next()){
            availableRooms.add(resultSet.getString(1));
        }

        return availableRooms;
    }
}
