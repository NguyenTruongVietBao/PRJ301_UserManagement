package sample.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

public class UserDAO {

    // lop UserDAO de su dung cac cong viec tuong tac voi CO SO DU LIEU (CRUD)
    private static final String LOGIN = "SELECT fullName, roleID FROM tblUsers WHERE userID=? AND password=?";
    private static final String SEARCH = "SELECT userID, fullName, roleID FROM tblUsers WHERE fullName like ?";
    private static final String DELETE = "DELETE tblUsers WHERE userID=?";

    public UserDTO checkLogin(String userID, String password) throws SQLException {

        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            // 1. tao ket noi DB
            conn = DBUtils.getConnection();
            if (conn != null) {
                // 2. tao query statement
                ptm = conn.prepareStatement(LOGIN);
                // gan tham so
                ptm.setString(1, userID);
                ptm.setString(2, password);

                // 3. thuc thi query statement va luu tru ket qua tra ve vao bien rs
                rs = ptm.executeQuery();

                // use if condition because the query returns only a row otherwise using while if return a table
                if (rs.next()) {   // rs.next()  tra ve true neu co bat ky ket qua nao  tra ve tu cau truy van SQL
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    user = new UserDTO(userID, fullName, roleID, "");   // password duoc truyen vao khoang trong "" vi sau khi check authentication chinh xac thi password khong nen duoc luu tru tren source code nham dam bao tinh bao mat; chung chi nen duoc luu tru tren database duoi dang hashing
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                rs.close();
            }

            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public List<UserDTO> getListUser(String search) throws SQLException {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();

                while (rs.next()) {

                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = "****";
                    list.add(new UserDTO(userID, fullName, roleID, password));
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm
                    != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public boolean delete(String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1,userID);
                check= ptm.executeUpdate()>0?true:false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
            return check;

        }
    }
