package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Diem;
import model.MonHoc;
import model.SinhVien;

public class DiemDAO {

	public int insert(Diem diem) {
	    int ketQua = 0;
	    Connection con = null;
	    try {
	        con = JDBCUtil.getConnection();

	        // Kiểm tra xem mã sinh viên đã tồn tại trong bảng diem chưa
	        String checkSql = "SELECT madiem FROM diem WHERE masinhvien = ?";
	        PreparedStatement checkStmt = con.prepareStatement(checkSql);
	        checkStmt.setString(1, diem.getSinhvien().getMaSinhVien());
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            // Nếu mã sinh viên đã tồn tại, cập nhật các điểm
	            String updateSql = "UPDATE diem SET diemtx1 = ?, diemtx2 = ?, diemthi = ?, mamonhoc = ? WHERE masinhvien = ?";
	            PreparedStatement updateStmt = con.prepareStatement(updateSql);
	            updateStmt.setDouble(1, diem.getDiemtx());
	            updateStmt.setDouble(2, diem.getDiemtx2());
	            updateStmt.setDouble(3, diem.getDiemThi());
	            updateStmt.setString(4, diem.getMonHoc().getMaMonHoc());  // Set MonHoc ID
	            updateStmt.setString(5, diem.getSinhvien().getMaSinhVien());
	            ketQua = updateStmt.executeUpdate();
	        } else {
	            // Nếu mã sinh viên chưa tồn tại, chèn dữ liệu mới
	            String insertSql = "INSERT INTO diem (madiem, diemtx1, diemtx2, diemthi, masinhvien, mamonhoc) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement insertStmt = con.prepareStatement(insertSql);
	            insertStmt.setString(1, diem.getMaDiem());
	            insertStmt.setDouble(2, diem.getDiemtx());
	            insertStmt.setDouble(3, diem.getDiemtx2());
	            insertStmt.setDouble(4, diem.getDiemThi());
	            insertStmt.setString(5, diem.getSinhvien().getMaSinhVien());
	            insertStmt.setString(6, diem.getMonHoc().getMaMonHoc());  // Set MonHoc ID
	            ketQua = insertStmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(con);
	    }
	    return ketQua;
	}
	public boolean kiemTraDiemTheoMaSinhVien(String maSinhVien, String maMonHoc) {
	    boolean exists = false;
	    Connection con = null;
	    try {
	        con = JDBCUtil.getConnection();
	        
	        // SQL query to check if a record exists for the given student and subject
	        String sql = "SELECT COUNT(*) FROM diem WHERE masinhvien = ? AND mamonhoc = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maSinhVien);
	        stmt.setString(2, maMonHoc);  // Set the maMonHoc parameter
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1);  // Get the count of records
	            if (count > 0) {
	                exists = true;  // Record exists
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(con);
	    }
	    return exists;  // Return true if record exists, false otherwise
	}

	public Diem getDiemByMaSinhVien(String maSinhVien, String maMonHoc) {
	    Diem diem = new Diem();
	    Connection con = null;
	    try {
	        con = JDBCUtil.getConnection();
	        
	        // Modify SQL query to include both masinhvien and mamonhoc
	        String sql = "SELECT madiem, diemtx1, diemtx2, diemthi, masinhvien, mamonhoc FROM diem WHERE masinhvien = ? AND mamonhoc = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maSinhVien);
	        stmt.setString(2, maMonHoc);  // Set the maMonHoc parameter
	        
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            diem.setMaDiem(rs.getString("madiem"));
	            diem.setDiemtx(rs.getDouble("diemtx1"));
	            diem.setDiemtx2(rs.getDouble("diemtx2"));
	            diem.setDiemThi(rs.getDouble("diemthi"));

	            // Create and set SinhVien object
	            SinhVien sinhVien = new SinhVien();
	            sinhVien.setMaSinhVien(rs.getString("masinhvien"));
	            diem.setSinhvien(sinhVien);

	            // Create and set MonHoc object
	            String maMonHocFromDb = rs.getString("mamonhoc");
	            MonHoc monHoc = new MonHoc();
	            monHoc.setMaMonHoc(maMonHocFromDb);
	            diem.setMonHoc(monHoc);  // Assign the MonHoc to Diem
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(con);
	    }
	    return diem;
	}


    public ArrayList<Diem> selectByMaSinhVien(String maSinhVien) {
        ArrayList<Diem> listDiem = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT madiem, diemtx1, diemtx2, diemthi, masinhvien FROM diem WHERE masinhvien = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSinhVien);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Diem diem = new Diem();
                diem.setMaDiem(rs.getString("madiem"));
                diem.setDiemtx(rs.getDouble("diemtx1"));
                diem.setDiemtx2(rs.getDouble("diemtx2"));
                diem.setDiemThi(rs.getDouble("diemthi"));

                // Set SinhVien object for the Diem
                SinhVien sinhVien = new SinhVien();
                sinhVien.setMaSinhVien(rs.getString("masinhvien"));
                diem.setSinhvien(sinhVien);

                listDiem.add(diem);  // Add Diem object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return listDiem;
    }
    public int delete(Diem diem) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            
            // Delete the diem record for the given student (masinhvien)
            String sql = "DELETE FROM diem WHERE masinhvien = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, diem.getSinhvien().getMaSinhVien());
            
            // Execute the delete statement
            ketQua = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public static void main(String[] args) {
        // Khởi tạo đối tượng DiemDAO
        DiemDAO diemDAO = new DiemDAO();

        // Tạo đối tượng SinhVien
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien("SV378976");  // Mã sinh viên (ví dụ)

        // Tạo đối tượng MonHoc
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMonHoc("NCNTT10178");  // Mã môn học (ví dụ)

        // Tạo đối tượng Diem
        Diem diem = new Diem();
        diem.setMaDiem("D001");
        diem.setDiemtx(8.5);
        diem.setDiemtx2(7.0);
        diem.setDiemThi(6.5);
        diem.setSinhvien(sinhVien);  // Gán sinh viên cho đối tượng điểm
        diem.setMonHoc(monHoc);  // Gán môn học cho đối tượng điểm

        // Kiểm tra phương thức insert (thêm hoặc cập nhật điểm)
        int insertResult = diemDAO.insert(diem);
        System.out.println("Insert result: " + insertResult);
    }

}

    
