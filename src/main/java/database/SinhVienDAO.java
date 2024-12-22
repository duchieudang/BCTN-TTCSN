package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import model.SinhVien;
import model.Diem;
import model.DiemDanh;
import model.Lop;


public class SinhVienDAO implements DAOInterface<SinhVien> {

	  @Override
	    public ArrayList<SinhVien> selectAll() {
	        ArrayList<SinhVien> ketQua = new ArrayList<>();
	        try {
	            Connection con = JDBCUtil.getConnection();
	            String sql = "SELECT * FROM sinhvien";
	            PreparedStatement st = con.prepareStatement(sql);
	            ResultSet rs = st.executeQuery();
	            while (rs.next()) {
	                String maSinhVien = rs.getString("masinhvien");
	                String tenSinhVien = rs.getString("tensinhvien");
	                Date ngaySinh = rs.getDate("ngaysinh");
	                String gioiTinh = rs.getString("gioitinh");
	                String diaChi = rs.getString("diachi");
	                String soDienThoai = rs.getString("sodienthoai");
	                String email = rs.getString("email");
	                String matKhau = rs.getString("matkhau");  // Lấy mật khẩu
	                String maLop = rs.getString("malop");
	                Lop lop = new Lop(maLop, "", "", "", null);
	                SinhVien sinhVien = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
	                ketQua.add(sinhVien);
	            }
	            JDBCUtil.closeConnection(con);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ketQua;
	    }

	  @Override
	    public SinhVien selectById(SinhVien t) {
	        SinhVien ketQua = null;
	        try {
	            Connection con = JDBCUtil.getConnection();
	            String sql = "SELECT * FROM sinhvien WHERE masinhvien=?";
	            PreparedStatement st = con.prepareStatement(sql);
	            st.setString(1, t.getMaSinhVien());
	            ResultSet rs = st.executeQuery();
	            if (rs.next()) {
	                String maSinhVien = rs.getString("masinhvien");
	                String tenSinhVien = rs.getString("tensinhvien");
	                Date ngaySinh = rs.getDate("ngaysinh");
	                String gioiTinh = rs.getString("gioitinh");
	                String diaChi = rs.getString("diachi");
	                String soDienThoai = rs.getString("sodienthoai");
	                String email = rs.getString("email");
	                String matKhau = rs.getString("matkhau");  // Lấy mật khẩu
	                String maLop = rs.getString("malop");

	                Lop lop = new Lop(maLop, "", "", "", null);
	                ketQua = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
	            }
	            JDBCUtil.closeConnection(con);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ketQua;
	    }
	  @Override
	    public int insert(SinhVien t) {
	        int ketQua = 0;
	        try {
	            Connection con = JDBCUtil.getConnection();
	            String sql = "INSERT INTO sinhvien (masinhvien, tensinhvien, ngaysinh, gioitinh, diachi, sodienthoai, email, matkhau, malop) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement st = con.prepareStatement(sql);
	            st.setString(1, t.getMaSinhVien());
	            st.setString(2, t.getTenSinhVien());
	            st.setDate(3, new java.sql.Date(t.getNgaySinh().getTime()));
	            st.setString(4, t.getGioiTinh());
	            st.setString(5, t.getDiaChi());
	            st.setString(6, t.getSoDienThoai());
	            st.setString(7, t.getEmail());
	            st.setString(8, t.getMatKhau());  // Thêm mật khẩu
	            st.setString(9, t.getLop().getMaLop());
	            ketQua = st.executeUpdate();
	            JDBCUtil.closeConnection(con);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ketQua;
	    }

	  @Override
	    public int update(SinhVien t) {
	        int ketQua = 0;
	        try {
	            Connection con = JDBCUtil.getConnection();
	            String sql = "UPDATE sinhvien SET tensinhvien=?, ngaysinh=?, gioitinh=?, diachi=?, sodienthoai=?, email=?, matkhau=?, malop=? WHERE masinhvien=?";
	            PreparedStatement st = con.prepareStatement(sql);
	            st.setString(1, t.getTenSinhVien());
	            st.setDate(2, new java.sql.Date(t.getNgaySinh().getTime()));
	            st.setString(3, t.getGioiTinh());
	            st.setString(4, t.getDiaChi());
	            st.setString(5, t.getSoDienThoai());
	            st.setString(6, t.getEmail());
	            st.setString(7, t.getMatKhau());  // Cập nhật mật khẩu
	            st.setString(8, t.getLop().getMaLop());
	            st.setString(9, t.getMaSinhVien());
	            ketQua = st.executeUpdate();
	            JDBCUtil.closeConnection(con);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ketQua;
	    }

	public ArrayList<SinhVien> selectByMaLop(String maLop) {
	    ArrayList<SinhVien> listSinhVien = new ArrayList<>();
	    Connection con = null;

	    try {
	        con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM SinhVien WHERE malop = ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, maLop);
	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            // Lấy thông tin từ ResultSet
	            String maSinhVien = rs.getString("masinhvien");
	            String tenSinhVien = rs.getString("tensinhvien");
	            Date ngaySinh = rs.getDate("ngaysinh");
	            String gioiTinh = rs.getString("gioitinh");
	            String diaChi = rs.getString("diachi");
	            String soDienThoai = rs.getString("sodienthoai");
	            String email = rs.getString("email");
	            String matKhau = rs.getString("matkhau"); 
	        
	           
	            Lop lop = new Lop(maLop, "", "", "", null); // Nếu cần thêm thông tin về lớp
	            
	            // Khởi tạo đối tượng SinhVien và thiết lập các thuộc tính
	            SinhVien sinhVien = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
	            
	            // Thêm sinh viên vào danh sách
	            listSinhVien.add(sinhVien);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(con);
	    }
	    
	    return listSinhVien;
	}

	public SinhVien selectByUsernameAndPassWord(SinhVien t) {
	    SinhVien ketQua = null;
	    Connection con = null;
	    try {
	        con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM sinhvien WHERE masinhvien=? AND matkhau=?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, t.getMaSinhVien());
	        st.setString(2, t.getMatKhau());

	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            String maSinhVien = rs.getString("masinhvien");
	            String tenSinhVien = rs.getString("tensinhvien");
	            Date ngaySinh = rs.getDate("ngaysinh");
	            String gioiTinh = rs.getString("gioitinh");
	            String diaChi = rs.getString("diachi");
	            String soDienThoai = rs.getString("sodienthoai");
	            String email = rs.getString("email");
	          
	            String matKhau = rs.getString("matkhau");
	            String maLop = rs.getString("malop");
	            Lop lop = new Lop(maLop, "", "", "", null);
	            ketQua = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(con);
	    }
	    return ketQua;
	}
	public SinhVien selectByMaSinhVien(String maSinhVien) {
	    SinhVien ketQua = null;
	    try {
	        Connection con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM sinhvien WHERE masinhvien=?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, maSinhVien);

	        System.out.println(sql);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            String matKhau = rs.getString("matkhau");
	            String tenSinhVien = rs.getString("tensinhvien");
	            String gioiTinh = rs.getString("gioitinh");
	            String diaChi = rs.getString("diachi");
	            Date ngaySinh = rs.getDate("ngaysinh");
	            String soDienThoai = rs.getString("sodienthoai");
	            String email = rs.getString("email");
	       
	            String maLop = rs.getString("malop");
	            Lop lop = new Lop(maLop, "", "", "", null);
	            ketQua = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
	        }

	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ketQua;
	}

	@Override
	public int delete(SinhVien t) {
	    int ketQua = 0;
	    try {
	        Connection con = JDBCUtil.getConnection();

	        // Step 1: Delete associated `diem` records
	        DiemDAO diemDAO = new DiemDAO();
	        ArrayList<Diem> listDiem = diemDAO.selectByMaSinhVien(t.getMaSinhVien());
	        for (Diem diem : listDiem) {
	            ketQua += diemDAO.delete(diem);
	        }

	        // Step 2: Delete associated `diemdanh` records
	        DiemDanhDAO diemDanhDAO = new DiemDanhDAO();
	        ArrayList<DiemDanh> listDiemDanh = diemDanhDAO.selectByMaSinhVien(t.getMaSinhVien());
	        for (DiemDanh diemDanh : listDiemDanh) {
	            ketQua += diemDanhDAO.delete(diemDanh);
	        }

	        // Step 3: Delete the `sinhvien` record
	        String sql = "DELETE FROM sinhvien WHERE masinhvien=?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, t.getMaSinhVien());
	        ketQua += st.executeUpdate();

	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ketQua;
	}


	// Hàm kiểm tra mã sinh viên đã tồn tại trong cơ sở dữ liệu chưa
	public int sosanh(String maSinhVienMoi) {
		int dem = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT COUNT(*) FROM sinhvien WHERE masinhvien = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, maSinhVienMoi);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				dem = rs.getInt(1); // Lấy số lượng bản ghi trùng mã sinh viên
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dem;
	}

	

	@Override
	public int insertAll(ArrayList<SinhVien> arr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll(ArrayList<SinhVien> arr) {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void main(String[] args) {
        SinhVienDAO sinhVienDAO = new SinhVienDAO();
        ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
        Random random = new Random();

        // Tạo lớp LCNTT217
        Lop lop = new Lop("LCNTT217", "", "", "", null);

        // Danh sách tỉnh, họ, tên đệm, và tên
        String[] tinhList = {"Nam Định", "Hà Nội", "Bắc Ninh", "Thái Nguyên", "Ninh Bình", 
                             "Hà Nam", "Phú Thọ", "Hải Dương", "Lạng Sơn", "Điện Biên", 
                             "Hòa Bình", "Vĩnh Phúc", "Thanh Hóa", "Quảng Ninh", "Bình Dương", 
                             "Hải Phòng", "Đà Nẵng", "Bắc Giang", "Lâm Đồng", "Cà Mau", 
                             "Sóc Trăng", "An Giang", "Bến Tre", "Cần Thơ", "Tây Ninh", 
                             "Lào Cai", "Khánh Hòa", "Quảng Nam", "Gia Lai", "Hậu Giang"};
                             
        String[] hoList = {"Đặng", "Nguyễn", "Lương", "Đới", "Vũ", "Phạm", "Trần", "Lê", "Mai", "Dương", 
                            "Bùi", "Võ", "Hoàng", "Phan", "Đoàn", "Tạ", "Ngô", "Hồ", "Lý", "Trịnh", 
                            "Đỗ", "Ngọc", "Hà", "Hồng", "Lương", "Hoàng", "Mạc", "Lê", "Vương", "Bạch", 
                            "Trịnh", "Ngô", "Dương", "Bùi", "Chu", "Vũ", "Phan", "Huỳnh", "Nguyễn", "Mai"};
                             
        String[] tenDemNamList = {"Văn", "Đức", "Nghĩa", "Quang", "Tuấn", "Tùng", "Hữu", "Xuân"};
        String[] tenDemNuList = {"Thị", "Như", "Hồng", "Lan", "Trâm", "Bảo"};
        String[] tenNamList = {"Anh", "Bình", "Châu", "Dương", "Khoa", "Phong", "Quân", "Tài", "Hùng", "Sơn", 
                               "Vinh", "Vũ", "Duy", "Tuấn", "Mạnh"};
        String[] tenNuList = {"Huyền", "Linh", "Minh", "Trang", "Thảo", "Lan", "Mai", "Vân", "Nhung", "Kiều"};

        for (int i = 1; i <= 30; i++) {
            // Tạo mã sinh viên: SV + 6 số ngẫu nhiên
            String maSinhVien = "SV" + String.format("%06d", random.nextInt(1000000));

            // Chọn giới tính và tên phù hợp
            String gioiTinh;
            String tenDem, ten;

            if (random.nextBoolean()) {
                // Giới tính Nam
                gioiTinh = "Nam";
                tenDem = tenDemNamList[random.nextInt(tenDemNamList.length)];
                ten = tenNamList[random.nextInt(tenNamList.length)];
            } else {
                // Giới tính Nữ
                gioiTinh = "Nữ";
                tenDem = tenDemNuList[random.nextInt(tenDemNuList.length)];
                ten = tenNuList[random.nextInt(tenNuList.length)];
            }

            // Tạo họ tên ngẫu nhiên
            String ho = hoList[random.nextInt(hoList.length)];
            String tenSinhVien = ho + " " + tenDem + " " + ten;

            // Ngày sinh: Năm 2004, tháng và ngày ngẫu nhiên
            int month = 1 + random.nextInt(12); // 1 -> 12
            int day = 1 + random.nextInt(28);   // 1 -> 28
            Date ngaySinh = Date.valueOf("2003-" + month + "-" + day);

            // Tỉnh ngẫu nhiên
            String diaChi = tinhList[random.nextInt(tinhList.length)];

            // Số điện thoại ngẫu nhiên
            String soDienThoai = "09" + String.format("%08d", random.nextInt(100000000));

            // Email ngẫu nhiên
            String email = maSinhVien.toLowerCase() + "@gmail.com";

            // Mật khẩu là maSV + "@"
            String matKhau = maSinhVien + "@";

            // Tạo đối tượng SinhVien
            SinhVien sinhVien = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, lop);
            danhSachSinhVien.add(sinhVien);
        }

        // Thêm vào database
        for (SinhVien sv : danhSachSinhVien) {
            int ketQua = sinhVienDAO.insert(sv);
            if (ketQua > 0) {
                System.out.println("Thêm thành công: " + sv.getMaSinhVien() + " - " + sv.getTenSinhVien() + " - " + sv.getGioiTinh());
            } else {
                System.out.println("Thêm thất bại: " + sv.getMaSinhVien());
            }
        }
    }
}

