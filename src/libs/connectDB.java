package libs;

import javax.swing.*;
import java.sql.*;

public class connectDB {
    private String dbName;
    private String userName;
    private String password;
    private String customer_ID;

    public connectDB(String dbName, String userName, String password) {
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public Connection connection(Connection conn){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Download JDBC
            String url = "jdbc:mysql://localhost:3306/car";// your db name
            String user = this.userName; // your db username
            String password = this.password; // your db password
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connect success!");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public String select_ID_byName(String tmp_name, Connection conn)  throws SQLException {
        String id = "";
        String query_1 = "SELECT * FROM customer WHERE customer.name = ?";
        PreparedStatement stmt_1 = conn.prepareStatement(query_1);
        stmt_1.setString(1, tmp_name);
        ResultSet rs = stmt_1.executeQuery();
        while (rs.next()) {
            id = rs.getString("card_id");
        }
        System.out.println(id);
        return id;
    }

    public void select_Name(String tmp_name, Connection conn) throws SQLException {
        String id = "";
        String query_1 = "SELECT * FROM customer WHERE customer.name = ?";
        PreparedStatement stmt_1 = conn.prepareStatement(query_1);
        stmt_1.setString(1, tmp_name);
        ResultSet rs = stmt_1.executeQuery();
        while(rs.next()) {
            id = rs.getString("card_id");
        }
        String query = "DELETE FROM card WHERE card.id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, id);

        int rs_2 = stmt.executeUpdate();
        if(rs_2 != -1) {
            JOptionPane.showMessageDialog(null, "XÓA THẺ XE THÀNH CÔNG", "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "XÓA THẺ XE KHÔNG THÀNH CÔNG", "Thông báo", JOptionPane.ERROR_MESSAGE);

        }
    }

    public boolean select_ID(String item, Connection conn) throws SQLException {
        boolean Flag = false;
        String query = "SELECT * FROM card WHERE card.id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, item);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Flag = true;
            this.customer_ID = rs.getString("id");
            System.out.println(rs.getInt("status"));
            if(rs.getInt("status") == '0') {
                Flag = false;
            }
        }
        System.out.println(Flag);
        return Flag;
    }

    public boolean select_date(String tmp_date, String ID, Connection conn) throws SQLException {
        boolean Flag = false;
        String query = "SELECT * FROM date_time WHERE date_time.date_time = ? AND date_time.card_id = ? ";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_date);
        stmt.setString(2, ID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Flag = true;
        }
        return Flag;
    }

    public ResultSet select_list_customer(Connection conn) throws SQLException {
        String query = "SELECT * FROM customer";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public String select_newest_date(String tmp_id, Connection conn) throws SQLException {
        String tmp_date = "";
        String query = "SELECT * FROM customer where card_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tmp_date = rs.getString("newest_date");
        }
        return tmp_date;
    }

    public ResultSet select_ParkingTime(Connection conn, String tmp_ID) throws SQLException {
        String query = "SELECT * FROM parking_datetime WHERE parking_datetime.id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_ID);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }


    public ResultSet selection(String item, Connection conn) throws SQLException {
        String query = "SELECT * FROM customer WHERE card_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, item);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public void insert_card(String tmp_id, int tmp_status, Connection conn) throws SQLException {
        String query = "INSERT INTO card VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_id);
        stmt.setInt(2, tmp_status);
        stmt.execute();
    }

    public void insert_datetime(String tmp_datetime, String ID, Connection conn) throws SQLException {
        String query = "INSERT INTO date_time VALUES(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_datetime);
        stmt.setString(2, ID);
        stmt.execute();
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void insertParkingDate(Connection conn, String tmp_dt, String tmp_ID, int tmp_status) throws SQLException {
        String query = "INSERT INTO parking_datetime VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_dt);
        stmt.setString(2, tmp_ID);
        stmt.setInt(3, tmp_status);
        stmt.execute();
    }



    public void insert_customer(String tmp_id, String tmp_name, int tmp_age, String tmp_moto_code, String tmp_dob, Connection conn) throws SQLException {
        String query = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_id);
        stmt.setString(2, tmp_name);
        stmt.setInt(3, tmp_age);
        stmt.setString(4, tmp_moto_code);
        stmt.setString(5, tmp_dob);
        stmt.setString(6, "");
        stmt.execute();
    }

    public void update_customer_date_in(String tmp_date, String tmp_card_id , Connection conn) throws SQLException {
        String query = "UPDATE customer set newest_date = ? WHERE customer.card_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, tmp_date);
        stmt.setString(2, tmp_card_id);
        stmt.execute();
    }

    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
