package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.HocPhan;

import model.Nganh;

public class HocPhanDAO implements DAOInterface<HocPhan> {

    @Override
    public ArrayList<HocPhan> selectAll() {
        ArrayList<HocPhan> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM hocphan";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maHocPhan = rs.getString("mahocphan");
                String tenHocPhan = rs.getString("tenhocphan");
                int tinChi = rs.getInt("tinchi"); // Lấy tinChi dưới dạng int
                String hocKy = rs.getString("hocky");
                String maNganh = rs.getString("manganh");
                String batbuoc = rs.getString("batbuoc");
                Nganh nganh = new Nganh(maNganh, "", null);
                HocPhan hocPhan = new HocPhan(maHocPhan, tenHocPhan, tinChi, hocKy, nganh, batbuoc);
                ketQua.add(hocPhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public int sosanh(String maHocPhanMoi) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM hocphan WHERE mahocphan = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maHocPhanMoi);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                dem = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return dem;
    }

    @Override
    public HocPhan selectById(HocPhan t) {
        HocPhan ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM hocphan WHERE mahocphan=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaHocPhan());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maHocPhan = rs.getString("mahocphan");
                String tenHocPhan = rs.getString("tenhocphan");
                int tinChi = rs.getInt("tinchi");
                String hocKy = rs.getString("hocky");
                String maNganh = rs.getString("manganh");
                String batbuoc = rs.getString("batbuoc");
                Nganh nganh = new Nganh(maNganh, "", null);
                ketQua = new HocPhan(maHocPhan, tenHocPhan, tinChi, hocKy, nganh, batbuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int insert(HocPhan t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO hocphan (mahocphan, tenhocphan, tinchi, hocky, manganh, batbuoc) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaHocPhan());
            st.setString(2, t.getTenHocPhan());
            st.setInt(3, t.getTinChi());
            st.setString(4, t.getHocKy());
            st.setString(5, t.getNganh().getMaNganh());
            st.setString(6, t.getBatbuoc());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int update(HocPhan t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE hocphan SET tenhocphan=?, tinchi=?, hocky=?, manganh=?, batbuoc=? WHERE mahocphan=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenHocPhan());
            st.setInt(2, t.getTinChi());
            st.setString(3, t.getHocKy());
            st.setString(4, t.getNganh().getMaNganh());
            st.setString(5, t.getBatbuoc());
            st.setString(6, t.getMaHocPhan());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(HocPhan t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM hocphan WHERE mahocphan=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaHocPhan());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int insertAll(ArrayList<HocPhan> arr) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO hocphan (mahocphan, tenhocphan, tinchi, hocky, manganh, batbuoc) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            for (HocPhan hocPhan : arr) {
                st.setString(1, hocPhan.getMaHocPhan());
                st.setString(2, hocPhan.getTenHocPhan());
                st.setInt(3, hocPhan.getTinChi());
                st.setString(4, hocPhan.getHocKy());
                st.setString(5, hocPhan.getNganh().getMaNganh());
                st.setString(6, hocPhan.getBatbuoc());
                dem += st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return dem;
    }

    @Override
    public int deleteAll(ArrayList<HocPhan> arr) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM hocphan WHERE mahocphan=?";
            PreparedStatement st = con.prepareStatement(sql);

            for (HocPhan hocPhan : arr) {
                st.setString(1, hocPhan.getMaHocPhan());
                dem += st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return dem;
    }


    public ArrayList<HocPhan> selectByMaNganh(String maNganh) {
        ArrayList<HocPhan> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM hocphan WHERE manganh = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maNganh);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maHocPhan = rs.getString("mahocphan");
                String tenHocPhan = rs.getString("tenhocphan");
                int tinChi = rs.getInt("tinchi");
                String hocKy = rs.getString("hocky");
                String batbuoc = rs.getString("batbuoc");
                Nganh nganh = new Nganh(maNganh, "", null);
                HocPhan hocPhan = new HocPhan(maHocPhan, tenHocPhan, tinChi, hocKy, nganh, batbuoc);
                ketQua.add(hocPhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public HocPhan getHocPhanByMaHocPhan(String maHocPhan) {
        HocPhan hocPhan = null;
        Connection conn = null; // Khai báo Connection
        String sql = "SELECT * FROM hocphan WHERE mahocphan = ?"; // Sử dụng cú pháp SQL đúng
        try {
            conn = JDBCUtil.getConnection(); // Sử dụng JDBCUtil để kết nối
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maHocPhan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maHocPhanResult = rs.getString("mahocphan"); // Lấy mã học phần
                String tenHocPhan = rs.getString("tenhocphan"); // Lấy tên học phần
                int tinChi = rs.getInt("tinchi"); // Lấy tín chỉ
                String hocKy = rs.getString("hocky"); // Lấy học kỳ
                String maNganh = rs.getString("manganh"); // Lấy mã ngành
                String batbuoc = rs.getString("batbuoc"); // Lấy bắt buộc
                
                Nganh nganh = new Nganh(maNganh, "", null); // Tạo đối tượng Nganh
                hocPhan = new HocPhan(maHocPhanResult, tenHocPhan, tinChi, hocKy, nganh, batbuoc); // Khởi tạo đối tượng HocPhan
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn); // Đảm bảo đóng kết nối trong finally
        }
        return hocPhan; // Trả về đối tượng HocPhan
    }
    public static void main(String[] args) {
        // Tạo danh sách các học phần
        ArrayList<HocPhan> hocPhanList = new ArrayList<>();
        HocPhanDAO hocPhanDAO = new HocPhanDAO();
        // Danh sách tên học phần
        String[] tenHocPhanList = {
            "Lập trình Java", "Cơ sở dữ liệu", "Mạng máy tính", "Cấu trúc dữ liệu và giải thuật", 
            "Hệ điều hành", "Phân tích và thiết kế hệ thống", "Kiến trúc máy tính", "An ninh mạng", 
            "Lập trình Web", "Lập trình di động", "Trí tuệ nhân tạo", "Khoa học dữ liệu", 
            "Học máy", "Hệ thống thông tin quản lý", "Tính toán song song"
        };

        // Tạo và thêm các học phần vào danh sách
        for (int i = 0; i < 15; i++) {
            String maHocPhan = "NCNTT" + (i + 1);  // Mã học phần: NCNTT1, NCNTT2, ..., NCNTT15
            String tenHocPhan = tenHocPhanList[i]; // Tên học phần từ danh sách
            int tinChi = (i % 2 == 0) ? 3 : 4; // Chọn tín chỉ (3 hoặc 4 tín chỉ cho mỗi học phần)
            String hocKy = (i % 2 == 0) ? "Học kỳ 1" : "Học kỳ 2"; // Học kỳ cho mỗi học phần
            String batbuoc = "Bắt buộc"; // Mọi học phần đều bắt buộc

            // Giả sử ngành học có mã "CNTT"
            Nganh nganh = new Nganh("NCNTT", "", null);

            // Tạo đối tượng HocPhan và thêm vào danh sách
            HocPhan hocPhan = new HocPhan(maHocPhan, tenHocPhan, tinChi, hocKy, nganh, batbuoc);
            hocPhanDAO.insert(hocPhan); 
            hocPhanList.add(hocPhan);
        }

        // In ra danh sách học phần đã tạo
        for (HocPhan hocPhan : hocPhanList) {
            System.out.println("Mã học phần: " + hocPhan.getMaHocPhan());
            System.out.println("Tên học phần: " + hocPhan.getTenHocPhan());
            System.out.println("Tín chỉ: " + hocPhan.getTinChi());
            System.out.println("Học kỳ: " + hocPhan.getHocKy());
            System.out.println("Ngành: " + hocPhan.getNganh().getMaNganh());
            System.out.println("Bắt buộc: " + hocPhan.getBatbuoc());
            System.out.println("------------------------------");
        }
    }
}
