package database;

import model.DiemDanh;
import model.SinhVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiemDanhDAO {

    public int insert(DiemDanh diemDanh) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();

            // Kiểm tra nếu đã có bản ghi với masinhvien
            String checkSql = "SELECT madiemdanh FROM diemdanh WHERE masinhvien = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setString(1, diemDanh.getSinhVien().getMaSinhVien());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Nếu đã có bản ghi, cập nhật sobuoivang và sotietvang
                String updateSql = "UPDATE diemdanh SET tongsotietvang = ?, sotietvang = ? WHERE masinhvien = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateSql);
                updateStmt.setInt(1, diemDanh.getTongSoTietVang());
                updateStmt.setString(2, diemDanh.getSoTietVang());
                updateStmt.setString(3, diemDanh.getSinhVien().getMaSinhVien());
                ketQua = updateStmt.executeUpdate();
            } else {
                // Nếu chưa có bản ghi, chèn bản ghi mới
                String insertSql = "INSERT INTO diemdanh (madiemdanh,tongsotietvang, sotietvang, masinhvien) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertSql);
                insertStmt.setString(1, diemDanh.getMaDiemDanh());
                insertStmt.setInt(2, diemDanh.getTongSoTietVang());
                insertStmt.setString(3, diemDanh.getSoTietVang());
                insertStmt.setString(4, diemDanh.getSinhVien().getMaSinhVien());
                ketQua = insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public DiemDanh getDiemDanhByMaSinhVien(String maSinhVien) {
        DiemDanh diemDanh = new DiemDanh();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();

            // Chuẩn bị câu lệnh SQL để lấy thông tin điểm danh
            String sql = "SELECT madiemdanh, tongsotietvang, sotietvang, masinhvien FROM diemdanh WHERE masinhvien = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSinhVien);

            ResultSet rs = stmt.executeQuery();

            // Kiểm tra kết quả truy vấn
            if (rs.next()) {
                // Lấy dữ liệu từ ResultSet và khởi tạo đối tượng DiemDanh
                String maDiemDanh = rs.getString("madiemdanh");
                int soBuoiVang = rs.getInt("tongsotietvang");
                String soTietVang = rs.getString("sotietvang"); // Lấy giá trị của soTietVang

                SinhVien sinhVien = new SinhVien();
                sinhVien.setMaSinhVien(maSinhVien);

                diemDanh = new DiemDanh();
                diemDanh.setMaDiemDanh(maDiemDanh);
                diemDanh.setTongSoTietVang(soBuoiVang);
                diemDanh.setSoTietVang(soTietVang); // Gán giá trị soTietVang
                diemDanh.setSinhVien(sinhVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return diemDanh;
        
    }
    public boolean kiemTraDiemDanhTheoMaSinhVien(String maSinhVien) {
        boolean tonTai = false;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();

            // Câu lệnh SQL kiểm tra sự tồn tại của mã sinh viên trong bảng diemdanh
            String sql = "SELECT madiemdanh FROM diemdanh WHERE masinhvien = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSinhVien);

            ResultSet rs = stmt.executeQuery();
            // Nếu có kết quả, điều đó có nghĩa là điểm danh tồn tại
            if (rs.next()) {
                tonTai = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return tonTai;
    }
    public int delete(DiemDanh diemDanh) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();

            // Prepare SQL statement to delete the attendance record based on maDiemDanh
            String sql = "DELETE FROM diemdanh WHERE madiemdanh = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, diemDanh.getMaDiemDanh());

            // Execute the delete query
            ketQua = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }


    public ArrayList<DiemDanh> selectByMaSinhVien(String maSinhVien) {
        ArrayList<DiemDanh> listDiemDanh = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();

            // SQL query to retrieve all attendance records for the specified student ID
            String sql = "SELECT madiemdanh, tongsotietvang, sotietvang, masinhvien FROM diemdanh WHERE masinhvien = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSinhVien);

            ResultSet rs = stmt.executeQuery();

            // Process each result
            while (rs.next()) {
                // Create a new DiemDanh object and populate it with data from the current row
                String maDiemDanh = rs.getString("madiemdanh");
                int tongSoTietVang = rs.getInt("tongsotietvang");
                String soTietVang = rs.getString("sotietvang");

                SinhVien sinhVien = new SinhVien();
                sinhVien.setMaSinhVien(maSinhVien);

                DiemDanh diemDanh = new DiemDanh();
                diemDanh.setMaDiemDanh(maDiemDanh);
                diemDanh.setTongSoTietVang(tongSoTietVang);
                diemDanh.setSoTietVang(soTietVang);
                diemDanh.setSinhVien(sinhVien);

                // Add the populated DiemDanh object to the list
                listDiemDanh.add(diemDanh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return listDiemDanh;
    }

   
    public static void main(String[] args) {
        DiemDanhDAO diemDanhDAO = new DiemDanhDAO();

        // Tạo đối tượng SinhVien
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien("SV71141616"); // Thay mã sinh viên phù hợp
        String maSinhVien = "SV71141616"; // Mã sinh viên cần kiểm tra
        boolean tonTai = diemDanhDAO.kiemTraDiemDanhTheoMaSinhVien(maSinhVien);
        if (tonTai) {
            System.out.println("Điểm danh tồn tại cho mã sinh viên: " + maSinhVien);
        } else {
            System.out.println("Không tìm thấy điểm danh cho mã sinh viên: " + maSinhVien);
        }
        // Tạo đối tượng DiemDanh
//        DiemDanh diemDanh = new DiemDanh();
//        diemDanh.setMaDiemDanh("DD001"); // Mã điểm danh
//        diemDanh.setTongSoTietVang(5);   // Tổng số tiết vắng
//        diemDanh.setSoTietVang("3");     // Số tiết vắng
//        diemDanh.setSinhVien(sinhVien);  // Gán đối tượng sinh viên vào điểm danh
//
//        // Thêm hoặc cập nhật thông tin điểm danh
//        int result = diemDanhDAO.insert(diemDanh);
//        if (result > 0) {
//            System.out.println("Thông tin điểm danh đã được thêm hoặc cập nhật thành công.");
//        } else {
//            System.out.println("Không thể thêm hoặc cập nhật thông tin điểm danh.");
//        }
//
//        // Lấy thông tin điểm danh theo mã sinh viên
//        DiemDanh retrievedDiemDanh = diemDanhDAO.getDiemDanhByMaSinhVien("SV71141616");
//        if (retrievedDiemDanh != null && retrievedDiemDanh.getMaDiemDanh() != null) {
//            System.out.println("Mã điểm danh: " + retrievedDiemDanh.getMaDiemDanh());
//            System.out.println("Tổng số tiết vắng: " + retrievedDiemDanh.getTongSoTietVang());
//            System.out.println("Số tiết vắng: " + retrievedDiemDanh.getSoTietVang());
//            System.out.println("Mã sinh viên: " + retrievedDiemDanh.getSinhVien().getMaSinhVien());
//        } else {
//            System.out.println("Không tìm thấy thông tin điểm danh cho mã sinh viên đã cho.");
//        }
    }
}
