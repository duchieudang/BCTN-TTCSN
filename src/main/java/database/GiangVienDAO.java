package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.GiangVien;

public class GiangVienDAO implements DAOInterface<GiangVien> {

    @Override
    public ArrayList<GiangVien> selectAll() {
        ArrayList<GiangVien> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giangvien";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maGiangVien = rs.getString("magiangvien");
                String tenGiangVien = rs.getString("tengiangvien");
                Date ngaySinh = rs.getDate("ngaysinh");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");
                String chuyenNganh = rs.getString("chuyennganh");
                String matKhau = rs.getString("matkhau");

                GiangVien giangVien = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai,
                        email, chuyenNganh, matKhau);
                ketQua.add(giangVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public GiangVien selectByEmail(String email) {
        GiangVien giangVien = null;
        String query = "SELECT * FROM giangvien WHERE email = ?"; // Thay giangvien bằng tên bảng giảng viên trong CSDL của bạn
        Connection connection = null;

        try {
            connection = JDBCUtil.getConnection(); // Mở kết nối từ JDBCUtil
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Kiểm tra xem có giảng viên nào có email trùng khớp không
            if (resultSet.next()) {
                String maGiangVien = resultSet.getString("maGiangVien");
                String tenGiangVien = resultSet.getString("tenGiangVien");
                Date ngaySinh = resultSet.getDate("ngaySinh");
                String gioiTinh = resultSet.getString("gioiTinh");
                String diaChi = resultSet.getString("diaChi");
                String soDienThoai = resultSet.getString("soDienThoai");
                String chuyenNganh = resultSet.getString("chuyenNganh");
                String matKhau = resultSet.getString("matKhau");

                giangVien = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, chuyenNganh, matKhau);
            }

            // Đóng các đối tượng PreparedStatement và ResultSet
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection); // Đóng kết nối từ JDBCUtil
        }

        return giangVien;
    }
    @Override
    public GiangVien selectById(GiangVien t) {
        GiangVien ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giangvien WHERE magiangvien=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaGiangVien());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maGiangVien = rs.getString("magiangvien");
                String tenGiangVien = rs.getString("tengiangvien");
                Date ngaySinh = rs.getDate("ngaysinh");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");
                String chuyenNganh = rs.getString("chuyennganh");
                String matKhau = rs.getString("matkhau");

                ketQua = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email,
                        chuyenNganh, matKhau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }
    public GiangVien selectByMaGiangVien(String maGiangVien) {
        GiangVien ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giangvien WHERE magiangvien=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maGiangVien);

            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String matKhau = rs.getString("matkhau");
                String tenGiangVien = rs.getString("tengiangvien");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                Date ngaySinh = rs.getDate("ngaysinh");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");
                String chuyenNganh = rs.getString("chuyennganh");

                ketQua = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, chuyenNganh, matKhau);
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public GiangVien selectByUsernameAndPassWord(GiangVien t) {
        GiangVien ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giangvien WHERE magiangvien=? AND matkhau=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaGiangVien());
            st.setString(2, t.getMatKhau());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String maGiangVien = rs.getString("magiangvien");
                String tenGiangVien = rs.getString("tengiangvien");
                Date ngaySinh = rs.getDate("ngaysinh");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");
                String chuyenNganh = rs.getString("chuyennganh");
                String matKhau = rs.getString("matkhau");

                ketQua = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, chuyenNganh, matKhau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public int sosanh(String maGiangVienMoi) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM giangvien WHERE magiangvien = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maGiangVienMoi);
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
    public int insert(GiangVien t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO giangvien (magiangvien, tengiangvien, ngaysinh, gioitinh, diachi, sodienthoai, email, chuyennganh, matkhau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaGiangVien());
            st.setString(2, t.getTenGiangVien());
            st.setDate(3, new java.sql.Date(t.getNgaySinh().getTime()));
            st.setString(4, t.getGioiTinh());
            st.setString(5, t.getDiaChi());
            st.setString(6, t.getSoDienThoai());
            st.setString(7, t.getEmail());
            st.setString(8, t.getChuyenNganh());
            st.setString(9, t.getMatKhau());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int update(GiangVien t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE giangvien SET tengiangvien=?, ngaysinh=?, gioitinh=?, diachi=?, sodienthoai=?, email=?, chuyennganh=?, matkhau=? WHERE magiangvien=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenGiangVien());
            st.setDate(2, new java.sql.Date(t.getNgaySinh().getTime()));
            st.setString(3, t.getGioiTinh());
            st.setString(4, t.getDiaChi());
            st.setString(5, t.getSoDienThoai());
            st.setString(6, t.getEmail());
            st.setString(7, t.getChuyenNganh());
            st.setString(8, t.getMatKhau());
            st.setString(9, t.getMaGiangVien());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(GiangVien t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM giangvien WHERE magiangvien=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaGiangVien());
            ketQua = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int insertAll(ArrayList<GiangVien> arr) {
        int dem = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO giangvien (magiangvien, tengiangvien, ngaysinh, gioitinh, diachi, sodienthoai, email, chuyennganh, matkhau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            for (GiangVien giangVien : arr) {
                st.setString(1, giangVien.getMaGiangVien());
                st.setString(2, giangVien.getTenGiangVien());
                st.setDate(3, new java.sql.Date(giangVien.getNgaySinh().getTime()));
                st.setString(4, giangVien.getGioiTinh());
                st.setString(5, giangVien.getDiaChi());
                st.setString(6, giangVien.getSoDienThoai());
                st.setString(7, giangVien.getEmail());
                st.setString(8, giangVien.getChuyenNganh());
                st.setString(9, giangVien.getMatKhau());
                dem += st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return dem;
    }

    public GiangVien getGiangVienByMaGiangVien(String maGiangVien) {
        GiangVien giangVien = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giangvien WHERE magiangvien = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maGiangVien);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String maGiangVienResult = rs.getString("magiangvien");
                String tenGiangVien = rs.getString("tengiangvien");
                Date ngaySinh = rs.getDate("ngaysinh");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");
                String chuyenNganh = rs.getString("chuyennganh");
                String matKhau = rs.getString("matkhau");

                giangVien = new GiangVien(maGiangVienResult, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai,
                        email, chuyenNganh, matKhau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return giangVien;
    }

	@Override
	public int deleteAll(ArrayList<GiangVien> arr) {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void main(String[] args) {
        // Danh sách họ tên nam
        String[] FIRST_NAMES_MALE = {"Anh", "Bình", "Cường", "Duy", "Hùng", "Khoa", "Linh", "Mạnh", "Nam", "Quân", "Sơn", "Tài", "Toàn", "Tùng", "Vinh"};
        // Danh sách họ tên nữ
        String[] FIRST_NAMES_FEMALE = {"Bích", "Cẩm", "Duyên", "Hương", "Lan", "Mai", "Nhung", "Phương", "Quỳnh", "Thảo", "Tuyết", "Vân", "Xuyến"};
        // Danh sách họ
        String[] LAST_NAMES = {"Nguyễn", "Trần", "Lê", "Phan", "Vũ", "Đặng", "Bùi", "Hoàng", "Hà", "Lý", "Phạm"};
        // Danh sách đệm
        String[] MIDDLE_NAMES = {"Văn", "Thị", "Quốc", "Minh", "Ngọc", "Trọng", "Đức", "Quang", "Hữu", "Nguyên", "Hoàng"};
        // Danh sách tỉnh miền Bắc và miền Trung
        String[] TOWNS = {"Hà Nội", "Hải Phòng", "Bắc Giang", "Bắc Ninh", "Quảng Ninh", "Thái Nguyên", "Vĩnh Phúc", "Hà Nam", "Hòa Bình", "Lạng Sơn", "Điện Biên", "Sơn La", "Lào Cai", "Yên Bái", "Tuyên Quang", "Hưng Yên", "Nam Định", "Thái Bình", "Ninh Bình", "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị", "Thừa Thiên-Huế", "Quảng Nam", "Quảng Ngãi", "Bình Định", "Phú Yên", "Khánh Hòa", "Ninh Thuận", "Bình Thuận"};
        // Danh sách chuyên ngành
        String[] MAJOR_LIST = {"Công nghệ thông tin", "Điện tử viễn thông", "Mạng",  "Ngoại ngữ"};

        GiangVienDAO giangVienDAO = new GiangVienDAO();
        ArrayList<GiangVien> giangViens = new ArrayList<>();
        Random rand = new Random();

        // Tạo 20 giảng viên ngẫu nhiên
        for (int i = 1; i <= 20; i++) {
            // Tạo mã giảng viên ngẫu nhiên
            String maGiangVien = "GV" + String.format("%06d", rand.nextInt(1000000));
            
            // Giới tính ngẫu nhiên
            boolean isMale = rand.nextBoolean();
            
            // Tạo họ tên ngẫu nhiên
            String firstName = isMale ? FIRST_NAMES_MALE[rand.nextInt(FIRST_NAMES_MALE.length)] : FIRST_NAMES_FEMALE[rand.nextInt(FIRST_NAMES_FEMALE.length)];
            String middleName = MIDDLE_NAMES[rand.nextInt(MIDDLE_NAMES.length)];
            String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
            
            // Tạo tên đầy đủ với họ, đệm và tên
            String fullName = lastName + " " + middleName + " " + firstName;
            
            // Tạo tuổi ngẫu nhiên (từ 40 tuổi trở lên)
            int age = rand.nextInt(40) + 40;
            Date ngaySinh = Date.valueOf("1980-01-01"); // Giả định tất cả sinh năm 1980 trở lên
            ngaySinh = Date.valueOf("19" + (rand.nextInt(20) + 60) + "-" + (rand.nextInt(12) + 1) + "-" + (rand.nextInt(28) + 1));
            
            // Địa chỉ ngẫu nhiên từ danh sách tỉnh
            String diaChi = TOWNS[rand.nextInt(TOWNS.length)];
            
            // Số điện thoại ngẫu nhiên
            String soDienThoai = "012345678" + rand.nextInt(10);
            
            // Email ngẫu nhiên
            String email = maGiangVien + "@example.com";
            
            // Chuyên ngành ngẫu nhiên từ danh sách chuyên ngành
            String chuyenNganh = MAJOR_LIST[rand.nextInt(MAJOR_LIST.length)];
            
            // Mật khẩu ngẫu nhiên là mã giảng viên + "@"
            String matKhau = maGiangVien + "@";
            
            // Tạo đối tượng GiangVien
            GiangVien giangVien = new GiangVien(maGiangVien, fullName, ngaySinh, isMale ? "Nam" : "Nữ", diaChi, soDienThoai, email, chuyenNganh, matKhau);
            
            // Thêm giảng viên vào danh sách
            giangViens.add(giangVien);
        }
        
        // Chèn danh sách giảng viên vào cơ sở dữ liệu
        int rowsInserted = giangVienDAO.insertAll(giangViens);
        
        // In ra số lượng giảng viên đã được thêm
        System.out.println("Đã thêm " + rowsInserted + " giảng viên vào cơ sở dữ liệu.");
    }
}
