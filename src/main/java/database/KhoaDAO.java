package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Khoa;
import model.Nganh;



public class KhoaDAO implements DAOInterface<Khoa> {

    @Override
    public ArrayList<Khoa> selectAll() {
        ArrayList<Khoa> ketQua = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM khoa";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maKhoa = rs.getString("makhoa");
                String tenKhoa = rs.getString("tenkhoa");
                Khoa khoa = new Khoa(maKhoa, tenKhoa);
                ketQua.add(khoa);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public Khoa selectById(Khoa t) {
        Khoa ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM khoa WHERE makhoa=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaKhoa());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maKhoa = rs.getString("makhoa");
                String tenKhoa = rs.getString("tenkhoa");
                ketQua = new Khoa(maKhoa, tenKhoa);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int insert(Khoa t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO khoa (makhoa, tenkhoa) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaKhoa());
            st.setString(2, t.getTenKhoa());
            ketQua = st.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Khoa t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE khoa SET tenkhoa=? WHERE makhoa=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenKhoa());
            st.setString(2, t.getMaKhoa());
            ketQua = st.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(Khoa t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();

            // Xóa tất cả các ngành thuộc makhoa trước
        	NganhDAO nganhDAO = new NganhDAO();
			ArrayList<Nganh> listNganh = nganhDAO.selectByMaKhoa(t.getMaKhoa());
			for (Nganh nganh : listNganh) {
				ketQua+=nganhDAO.delete(nganh);
			}
            // Sau khi xóa ngành, tiếp tục xóa khoa
            String sqlDeleteKhoa = "DELETE FROM khoa WHERE makhoa=?";
            PreparedStatement stDeleteKhoa = con.prepareStatement(sqlDeleteKhoa);
            stDeleteKhoa.setString(1, t.getMaKhoa());
            ketQua = stDeleteKhoa.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public Khoa getKhoaByMaKhoa(String maKhoa) {
        Khoa khoa = null;
        Connection conn = null; // Khai báo Connection
        String sql = "SELECT * FROM khoa WHERE makhoa = ?"; // Sử dụng cú pháp SQL đúng
        try {
            conn = JDBCUtil.getConnection(); // Sử dụng JDBCUtil để kết nối
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKhoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khoa = new Khoa(); // Khởi tạo đối tượng Khoa
                khoa.setMaKhoa(rs.getString("makhoa")); // Lấy mã khoa
                khoa.setTenKhoa(rs.getString("tenkhoa")); // Lấy tên khoa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn); // Đảm bảo đóng kết nối trong finally
        }
        return khoa; // Trả về đối tượng Khoa
    }



    @Override
    public int insertAll(ArrayList<Khoa> arr) {
        int dem = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO khoa (makhoa, tenkhoa) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            for (Khoa khoa : arr) {
                st.setString(1, khoa.getMaKhoa());
                st.setString(2, khoa.getTenKhoa());
                dem += st.executeUpdate();
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dem;
    }

    @Override
    public int deleteAll(ArrayList<Khoa> arr) {
        int dem = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM khoa WHERE makhoa=?";
            PreparedStatement st = con.prepareStatement(sql);

            for (Khoa khoa : arr) {
                st.setString(1, khoa.getMaKhoa());
                dem += st.executeUpdate();
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dem;
    }

    // Hàm sosanh kiểm tra mã khoa đã tồn tại trong cơ sở dữ liệu chưa
    public int sosanh(String maKhoaMoi) {
        int dem = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM khoa WHERE makhoa = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maKhoaMoi);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                dem = rs.getInt(1); // Lấy số lượng bản ghi trùng mã khoa
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dem;
    }
    public int sosanh2(String tenKhoaMoi) {
        int dem = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM khoa WHERE tenkhoa = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, tenKhoaMoi);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                dem = rs.getInt(1); // Lấy số lượng bản ghi trùng mã khoa
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dem;
    }
    public int insertMultiple(ArrayList<Khoa> khoaList) {
        int dem = 0;
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO khoa (makhoa, tenkhoa) VALUES (?, ?)";
            st = con.prepareStatement(sql);

            // Sử dụng batch processing để chèn nhiều bản ghi một lần
            for (Khoa khoa : khoaList) {
                st.setString(1, khoa.getMaKhoa());
                st.setString(2, khoa.getTenKhoa());
                st.addBatch();
            }

            int[] results = st.executeBatch(); // Thực thi batch
            for (int result : results) {
                if (result > 0) {
                    dem++; // Đếm số bản ghi được chèn thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) JDBCUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dem; // Trả về số bản ghi được chèn thành công
    }
        
    public static void main(String[] args) {
        KhoaDAO khoaDAO = new KhoaDAO();
        System.out.println("Test KhoaDAO");
        
        // Tạo một danh sách các khoa cần thêm
        Khoa khoa1 = new Khoa("K001", "Khoa Công Nghệ Thông Tin");
        Khoa khoa2 = new Khoa("K002", "Khoa Quản Trị Kinh Doanh");
        Khoa khoa3 = new Khoa("K003", "Khoa Ngoại Ngữ");

        ArrayList<Khoa> khoaList = new ArrayList<>();
        khoaList.add(khoa1);
        khoaList.add(khoa2);
        khoaList.add(khoa3);

        // Kiểm tra và thêm các khoa vào cơ sở dữ liệu
        for (Khoa khoa : khoaList) {
            int occurrences = khoaDAO.sosanh(khoa.getMaKhoa());

            if (occurrences == 0) {
                int result = khoaDAO.insert(khoa);
                if (result > 0) {
                    System.out.println("Chèn thành công khoa: " + khoa.getMaKhoa());
                } else {
                    System.out.println("Không thể chèn khoa: " + khoa.getMaKhoa());
                }
            } else {
                System.out.println("Mã khoa " + khoa.getMaKhoa() + " đã tồn tại " + occurrences + " lần trong cơ sở dữ liệu.");
            }
        }

        // In danh sách các khoa sau khi xử lý
        System.out.println("\nDanh sách các khoa sau khi xử lý:");
        ArrayList<Khoa> allKhoa = khoaDAO.selectAll();
        for (Khoa khoa : allKhoa) {
            System.out.println(khoa);
        }
    }
    
}
