package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.LopHoc;

public class LopHocDAO implements DAOInterface<LopHoc> {

    @Override
    public ArrayList<LopHoc> selectAll() {
        ArrayList<LopHoc> ketQua = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM lophoc";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maLopHoc = rs.getString("malophoc");
                String tenLopHoc = rs.getString("tenlophoc");
                String toaNha = rs.getString("toanha");
                String phong = rs.getString("phong");

                LopHoc lopHoc = new LopHoc(maLopHoc, tenLopHoc, toaNha, phong);
                ketQua.add(lopHoc);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public LopHoc selectById(LopHoc lopHoc) {
        LopHoc ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM lophoc WHERE malophoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, lopHoc.getMaLopHoc());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maLopHoc = rs.getString("malophoc");
                String tenLopHoc = rs.getString("tenlophoc");
                String toaNha = rs.getString("toanha");
                String phong = rs.getString("phong");

                ketQua = new LopHoc(maLopHoc, tenLopHoc, toaNha, phong);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int insert(LopHoc lopHoc) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO lophoc (malophoc, tenlophoc, toanha, phong) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, lopHoc.getMaLopHoc());
            st.setString(2, lopHoc.getTenLopHoc());
            st.setString(3, lopHoc.getToaNha());
            st.setString(4, lopHoc.getPhong());
            ketQua = st.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(LopHoc lopHoc) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE lophoc SET tenlophoc=?, toanha=?, phong=? WHERE malophoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, lopHoc.getTenLopHoc());
            st.setString(2, lopHoc.getToaNha());
            st.setString(3, lopHoc.getPhong());
            st.setString(4, lopHoc.getMaLopHoc());
            ketQua = st.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(LopHoc lopHoc) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM lophoc WHERE malophoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, lopHoc.getMaLopHoc());
            ketQua = st.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int insertAll(ArrayList<LopHoc> arr) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO lophoc (malophoc, tenlophoc, toanha, phong) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for (LopHoc lopHoc : arr) {
                st.setString(1, lopHoc.getMaLopHoc());
                st.setString(2, lopHoc.getTenLopHoc());
                st.setString(3, lopHoc.getToaNha());
                st.setString(4, lopHoc.getPhong());
                ketQua += st.executeUpdate();
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
        
    }
    public LopHoc getLopHocByMaLopHoc(String maLopHoc) {
        LopHoc ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM lophoc WHERE malophoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLopHoc);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String tenLopHoc = rs.getString("tenlophoc");
                String toaNha = rs.getString("toanha");
                String phong = rs.getString("phong");

                ketQua = new LopHoc(maLopHoc, tenLopHoc, toaNha, phong);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int deleteAll(ArrayList<LopHoc> arr) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM lophoc WHERE malophoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            for (LopHoc lopHoc : arr) {
                st.setString(1, lopHoc.getMaLopHoc());
                ketQua += st.executeUpdate();
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public static void main(String[] args) {
        // Tạo danh sách các lớp học
    	  ArrayList<LopHoc> lopHocList = new ArrayList<>();

          // Tạo 30 lớp học cho 5 tòa nhà
          for (int i = 1; i <= 5; i++) {  // 5 nhà
              String toaNha = "Toa nha " + i;
              for (int j = 1; j <= 6; j++) {  // Mỗi nhà có 6 lớp
                  // Mã lớp học theo định dạng LH[tầng][phòng]
                  String maLopHoc = "LH" + i + j;  // Ví dụ: LH101, LH102, ...
                  String tenLopHoc = "Lớp " + maLopHoc;
                  String phong = "Phòng " + j;
                  
                  // Tạo đối tượng LopHoc và thêm vào danh sách
                  LopHoc lopHoc = new LopHoc(maLopHoc, tenLopHoc, toaNha, phong);
                  lopHocList.add(lopHoc);
              }
          }

          // Gọi DAO để thêm tất cả lớp học vào cơ sở dữ liệu
          LopHocDAO lopHocDAO = new LopHocDAO();
          int ketQua = lopHocDAO.insertAll(lopHocList)
 
        // In kết quả thêm lớp học
        System.out.println("Đã thêm " + ketQua + " lớp học vào cơ sở dữ liệu.");
    }
}
