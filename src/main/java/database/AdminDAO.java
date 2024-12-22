package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Admin;

public class AdminDAO implements DAOInterface<Admin> {

    public ArrayList<Admin> data = new ArrayList<>();

    @Override
    public ArrayList<Admin> selectAll() {
        ArrayList<Admin> ketQua = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM admin"; 
            PreparedStatement st = con.prepareStatement(sql);
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String maAdmin = rs.getString("maadmin");
                String matKhau = rs.getString("matkhau");
                String hoVaTen = rs.getString("hoten");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                Date ngaySinh = rs.getDate("ngaysinh");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");

                Admin admin = new Admin(maAdmin, matKhau, hoVaTen, gioiTinh, diaChi, ngaySinh, soDienThoai, email); 
                ketQua.add(admin);
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public Admin selectById(Admin t) {
        Admin ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM admin WHERE maadmin=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaAdmin());

            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String maAdmin = rs.getString("maadmin");
                String matKhau = rs.getString("matkhau");
                String hoVaTen = rs.getString("hoten");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                Date ngaySinh = rs.getDate("ngaysinh");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");

                ketQua = new Admin(maAdmin, matKhau, hoVaTen, gioiTinh, diaChi, ngaySinh, soDienThoai, email); 
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public Admin selectByUsernameAndPassWord(Admin t) {
        Admin ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM admin WHERE maadmin=? AND matkhau=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaAdmin());
            st.setString(2, t.getMatKhau()); // Sửa dòng này để đúng với mật khẩu

            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String maAdmin = rs.getString("maadmin");
                String matKhau = rs.getString("matkhau");
                String hoVaTen = rs.getString("hoten");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                Date ngaySinh = rs.getDate("ngaysinh");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");

                ketQua = new Admin(maAdmin, matKhau, hoVaTen, gioiTinh, diaChi, ngaySinh, soDienThoai, email); 
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int insert(Admin t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO admin (maadmin, matkhau, hoten, gioitinh, diachi, ngaysinh, sodienthoai, email) "
                    + " VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaAdmin());
            st.setString(2, t.getMatKhau());
            st.setString(3, t.getHoVaTen());
            st.setString(4, t.getGioiTinh());
            st.setString(5, t.getDiaChi());
            st.setDate(6, t.getNgaySinh()); 
            st.setString(7, t.getSoDienThoai());
            st.setString(8, t.getEmail());

            ketQua = st.executeUpdate();

            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int insertAll(ArrayList<Admin> arr) {
        int dem = 0;
        for (Admin admin : arr) {
            dem += this.insert(admin);
        }
        return dem;
    }

    @Override
    public int delete(Admin t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE from admin WHERE maadmin=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMaAdmin()); 

            System.out.println(sql);
            ketQua = st.executeUpdate();

            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int deleteAll(ArrayList<Admin> arr) {
        int dem = 0;
        for (Admin admin : arr) {
            dem += this.delete(admin);
        }
        return dem;
    }

    @Override
    public int update(Admin t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE admin SET matkhau=?, hoten=?, gioitinh=?, diachi=?, ngaysinh=?, sodienthoai=?, email=? WHERE maadmin=?"; 

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getMatKhau());
            st.setString(2, t.getHoVaTen());
            st.setString(3, t.getGioiTinh());
            st.setString(4, t.getDiaChi());
            st.setDate(5, t.getNgaySinh()); 
            st.setString(6, t.getSoDienThoai());
            st.setString(7, t.getEmail());
            st.setString(8, t.getMaAdmin());

            ketQua = st.executeUpdate();

            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }
    public Admin selectByMaAdmin(String maAdmin) {
        Admin ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM admin WHERE maadmin=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maAdmin);

            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String matKhau = rs.getString("matkhau");
                String hoVaTen = rs.getString("hoten");
                String gioiTinh = rs.getString("gioitinh");
                String diaChi = rs.getString("diachi");
                Date ngaySinh = rs.getDate("ngaysinh");
                String soDienThoai = rs.getString("sodienthoai");
                String email = rs.getString("email");

                ketQua = new Admin(maAdmin, matKhau, hoVaTen, gioiTinh, diaChi, ngaySinh, soDienThoai, email); 
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAO();

        // Tạo đối tượng Admin để thử nghiệm
        Admin newAdmin = new Admin("admin999", "pass123", "Nguyen Van A", "Nam", "123 ABC Street", 
                                    Date.valueOf("1990-01-01"), "0123456789", "admin999@gmail.com");

        // Thêm mới Admin
        System.out.println("===== Thêm mới Admin =====");
        int resultInsert = adminDAO.insert(newAdmin);
        if (resultInsert > 0) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }

        // Lấy danh sách tất cả Admin
        System.out.println("===== Lấy tất cả Admin =====");
        ArrayList<Admin> admins = adminDAO.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }

        // Tìm kiếm Admin theo mã Admin
        System.out.println("===== Tìm kiếm Admin theo mã =====");
        Admin foundAdmin = adminDAO.selectByMaAdmin("admin999");
        if (foundAdmin != null) {
            System.out.println("Tìm thấy: " + foundAdmin);
        } else {
            System.out.println("Không tìm thấy Admin với mã đã cung cấp.");
        }

        // Cập nhật Admin
        System.out.println("===== Cập nhật Admin =====");
        if (foundAdmin != null) {
            foundAdmin.setDiaChi("456 DEF Street");
            int resultUpdate = adminDAO.update(foundAdmin);
            if (resultUpdate > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại!");
            }
        }

        // Xóa Admin
//        System.out.println("===== Xóa Admin =====");
//        if (foundAdmin != null) {
//            int resultDelete = adminDAO.delete(foundAdmin);
//            if (resultDelete > 0) {
//                System.out.println("Xóa thành công!");
//            } else {
//                System.out.println("Xóa thất bại!");
//            }
//        }

        // Kiểm tra lại danh sách Admin sau khi xóa
        System.out.println("===== Danh sách Admin sau khi xóa =====");
        admins = adminDAO.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
}
