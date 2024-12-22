package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.GiangVien;
import model.HocPhan;
import model.Lop;
import model.LopHoc;
import model.MonHoc;

public class MonHocDAO implements DAOInterface<MonHoc> {

	@Override
	public ArrayList<MonHoc> selectAll() {
	    ArrayList<MonHoc> ketQua = new ArrayList<>();
	    String sql = "SELECT * FROM monhoc";
	    
	    try (Connection con = JDBCUtil.getConnection();
	         PreparedStatement st = con.prepareStatement(sql);
	         ResultSet rs = st.executeQuery()) {
	        
	        while (rs.next()) {
	            String maMonHoc = rs.getString("mamonhoc");
	            String tenMonHoc = rs.getString("tenmonhoc");
	            Date ngayBatDau = rs.getDate("ngaybatdau");
	            Date ngayKetThuc = rs.getDate("ngayketthuc");
	            int tietBatDau = rs.getInt("tietbatdau");
	            int tietKetThuc = rs.getInt("tietketthuc");
	            String lichHoc = rs.getString("lichhoc");
	            String maHocPhan = rs.getString("mahocphan");
	            String maGiangVien = rs.getString("magiangvien");
	            String maLop = rs.getString("malop");
	            String maLopHoc = rs.getString("malophoc");
	            
	            HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
	            GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
	            Lop lop = new Lop(maLop, "", "", "", null);
	            LopHoc lh = new LopHoc(maLopHoc, "", "", "");
	            
	            MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop, lh);
	            ketQua.add(monHoc);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return ketQua;
	}

    public boolean checkMonHocExistByName(String tenMonHoc) {
        boolean isExist = false;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM monhoc WHERE tenmonhoc = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, tenMonHoc);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                // Nếu số lượng môn học trùng tên lớn hơn 0 thì trả về true
                isExist = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return isExist;
    }

    public MonHoc getMonHocByMonHoc(String maMonHoc) {
        MonHoc ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM monhoc WHERE mamonhoc = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maMonHoc);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String tenMonHoc = rs.getString("tenmonhoc");
                Date ngayBatDau = rs.getDate("ngaybatdau");
                Date ngayKetThuc = rs.getDate("ngayketthuc");
                int tietBatDau = rs.getInt("tietbatdau");
                int tietKetThuc = rs.getInt("tietketthuc");
                String lichHoc = rs.getString("lichhoc");
                String maHocPhan = rs.getString("mahocphan");
                String maGiangVien = rs.getString("magiangvien");
                String maLop = rs.getString("malop");
                String maLopHoc = rs.getString("malophoc");

                HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(maLop, "", "", "", null);
                LopHoc lh = new LopHoc(maLopHoc, "", "", "");

                ketQua = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public ArrayList<MonHoc> getMonHocByGiangVien(String maGiangVien) {
        ArrayList<MonHoc> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM monhoc WHERE magiangvien = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maGiangVien);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maMonHoc = rs.getString("mamonhoc");
                String tenMonHoc = rs.getString("tenmonhoc");
                Date ngayBatDau = rs.getDate("ngaybatdau");
                Date ngayKetThuc = rs.getDate("ngayketthuc");
                int tietBatDau = rs.getInt("tietbatdau");
                int tietKetThuc = rs.getInt("tietketthuc");
                String lichHoc = rs.getString("lichhoc");
                String maHocPhan = rs.getString("mahocphan");
                String maLop = rs.getString("malop");
                String maLopHoc = rs.getString("malophoc");

                HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(maLop, "", "", "", null);
                LopHoc lh = new LopHoc(maLopHoc, "", "", "");

                MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
                ketQua.add(monHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public ArrayList<MonHoc> getMonHocByLop(String maLop) {
        ArrayList<MonHoc> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM monhoc WHERE malop = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLop);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maMonHoc = rs.getString("mamonhoc");
                String tenMonHoc = rs.getString("tenmonhoc");
                Date ngayBatDau = rs.getDate("ngaybatdau");
                Date ngayKetThuc = rs.getDate("ngayketthuc");
                int tietBatDau = rs.getInt("tietbatdau");
                int tietKetThuc = rs.getInt("tietketthuc");
                String lichHoc = rs.getString("lichhoc");
                String maHocPhan = rs.getString("mahocphan");
                String maGiangVien = rs.getString("magiangvien");
                String maLopHoc = rs.getString("malophoc");

                HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(maLop, "", "", "", null);
                LopHoc lh = new LopHoc(maLopHoc, "", "", "");

                MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
                ketQua.add(monHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public int sosanh(String maLopMoi) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM lop WHERE malop = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLopMoi);
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
    public MonHoc selectById(MonHoc t) {
        MonHoc ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM monhoc WHERE mamonhoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaMonHoc());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maMonHoc = rs.getString("mamonhoc");
                String tenMonHoc = rs.getString("tenmonhoc");
                Date ngayBatDau = rs.getDate("ngaybatdau");
                Date ngayKetThuc = rs.getDate("ngayketthuc");
                int tietBatDau = rs.getInt("tietbatdau");
                int tietKetThuc = rs.getInt("tietketthuc");
                String lichHoc = rs.getString("lichhoc");
                String maHocPhan = rs.getString("mahocphan");
                String maGiangVien = rs.getString("magiangvien");
                String maLop = rs.getString("malop");
                String maLopHoc = rs.getString("malophoc");

                HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(maLop, "", "", "", null);
                LopHoc lh = new LopHoc(maLopHoc, "", "", "");

                ketQua = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int insert(MonHoc t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO monhoc (mamonhoc, tenmonhoc, ngaybatdau, ngayketthuc, tietbatdau, tietketthuc, lichhoc, mahocphan, magiangvien, malop, malophoc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaMonHoc());
            st.setString(2, t.getTenMonHoc());
            st.setDate(3, new java.sql.Date(t.getNgayBatDau().getTime()));
            st.setDate(4, new java.sql.Date(t.getNgayKetThuc().getTime()));
            st.setInt(5, t.getTietBatDau());
            st.setInt(6, t.getTietKetThuc());
            st.setString(7, t.getLichHoc());
            st.setString(8, t.getHocPhan().getMaHocPhan());
            st.setString(9, t.getGiangVien().getMaGiangVien());
            st.setString(10, t.getLop().getMaLop());
            st.setString(11, t.getLopHoc().getMaLopHoc());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public ArrayList<MonHoc> selectByMaHocPhan(String maHocPhan) {
        ArrayList<MonHoc> ketQua = new ArrayList<>();
        Connection con = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM monhoc WHERE mahocphan = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maHocPhan);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String maMonHoc = rs.getString("mamonhoc");
                String tenMonHoc = rs.getString("tenmonhoc");
                Date ngayBatDau = rs.getDate("ngaybatdau");
                Date ngayKetThuc = rs.getDate("ngayketthuc");
                int tietBatDau = rs.getInt("tietbatdau");
                int tietKetThuc = rs.getInt("tietketthuc");
                String lichHoc = rs.getString("lichhoc");
                String maGiangVien = rs.getString("magiangvien");
                String maLop = rs.getString("malop");
                String maLopHoc = rs.getString("malophoc");

                // Tạo các đối tượng phụ thuộc
                HocPhan hp = new HocPhan(maHocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(maGiangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(maLop, "", "", "", null);
                LopHoc lh = new LopHoc(maLopHoc, "", "", "");

                // Tạo đối tượng MonHoc và thêm vào kết quả
                MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
                ketQua.add(monHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }

        return ketQua;
    }

    @Override
    public int update(MonHoc t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE monhoc SET tenmonhoc=?, ngaybatdau=?, ngayketthuc=?, tietbatdau=?, tietketthuc=?, lichhoc=?, mahocphan=?, magiangvien=?, malop=?, malophoc=? WHERE mamonhoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenMonHoc());
            st.setDate(2, new java.sql.Date(t.getNgayBatDau().getTime()));
            st.setDate(3, new java.sql.Date(t.getNgayKetThuc().getTime()));
            st.setInt(4, t.getTietBatDau());
            st.setInt(5, t.getTietKetThuc());
            st.setString(6, t.getLichHoc());
            st.setString(7, t.getHocPhan().getMaHocPhan());
            st.setString(8, t.getGiangVien().getMaGiangVien());
            st.setString(9, t.getLop().getMaLop());
            st.setString(10, t.getLopHoc().getMaLopHoc());
            st.setString(11, t.getMaMonHoc());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(MonHoc t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM monhoc WHERE mamonhoc=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaMonHoc());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int insertAll(ArrayList<MonHoc> arr) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO monhoc (mamonhoc, tenmonhoc, ngaybatdau, ngayketthuc, tietbatdau, tietketthuc, lichhoc, mahocphan, magiangvien, malop, malophoc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            for (MonHoc t : arr) {
                st.setString(1, t.getMaMonHoc());
                st.setString(2, t.getTenMonHoc());
                st.setDate(3, new java.sql.Date(t.getNgayBatDau().getTime()));
                st.setDate(4, new java.sql.Date(t.getNgayKetThuc().getTime()));
                st.setInt(5, t.getTietBatDau());
                st.setInt(6, t.getTietKetThuc());
                st.setString(7, t.getLichHoc());
                st.setString(8, t.getHocPhan().getMaHocPhan());
                st.setString(9, t.getGiangVien().getMaGiangVien());
                st.setString(10, t.getLop().getMaLop());
                st.setString(11, t.getLopHoc().getMaLopHoc());
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
	public int deleteAll(ArrayList<MonHoc> arr) {
		// TODO Auto-generated method stub
		return 0;
	}
	 public static void main(String[] args) {
	        MonHocDAO monHocDAO = new MonHocDAO();

	        // Tạo một đối tượng MonHoc mới
	      

	        ArrayList<MonHoc> monHocs = monHocDAO.selectAll();
	        for (MonHoc mh : monHocs) {
	            System.out.println("MonHoc: " + mh.getTenMonHoc());
	        }
	 }

	    }
	  
