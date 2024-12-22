package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SinhVien;
import model.Admin;
import model.Lop; // Nhập mô hình Lop nếu bạn cần sử dụng

import database.SinhVienDAO;

@WebServlet("/sinhvien")
public class SinhVienController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        SinhVienDAO sinhVienDAO = new SinhVienDAO();
        ArrayList<SinhVien> listSinhVien = sinhVienDAO.selectAll();
        request.setAttribute("listSinhVien", listSinhVien);

        // Kiểm tra nếu có yêu cầu hiển thị modal "Thêm sinh viên"
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }
       
       
        request.getRequestDispatcher("ChiTietSinhVien.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        SinhVienDAO sinhVienDAO = new SinhVienDAO();

        switch (action) {
            case "add":
                // Lấy dữ liệu từ form thêm sinh viên
             	Random random = new Random();
            	int randomNumber = 10000000 + random.nextInt(99999999); 
            	String maSinhVien = "SV" + String.valueOf(randomNumber);
                String tenSinhVien = request.getParameter("tensinhvien");
                Date ngaySinh = Date.valueOf(request.getParameter("ngaysinh")); // Chuyển đổi từ chuỗi
                String gioiTinh = request.getParameter("gioitinh");
                String diaChi = request.getParameter("diachi");
                String soDienThoai = request.getParameter("sodienthoai");
                String email = request.getParameter("email");
                String maLop = request.getParameter("malop"); // Mã lớp tương ứng
               String matKhau= maSinhVien+"@";
                // Kiểm tra mã sinh viên trùng
                int dem = sinhVienDAO.sosanh(maSinhVien);

                if (dem > 0) {
                    response.sendRedirect("sinhvien?status=exists&malop=" + maLop); // Chuyển hướng với thông báo sinh viên đã tồn tại
                } else {
                    // Tạo đối tượng sinh viên và thêm vào cơ sở dữ liệu
                    SinhVien sinhVien = new SinhVien(maSinhVien, tenSinhVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, matKhau, new Lop(maLop, null,"","",null)); // Tạo đối tượng Lop nếu cần
                    sinhVienDAO.insert(sinhVien);
                    response.sendRedirect("sinhvien?status=success&malop=" + maLop); 
                  // Chuyển hướng với thông báo thành công
                }
                return;

            case "delete":
                // Xóa sinh viên theo mã sinh viên
                String maSinhVienToDelete = request.getParameter("masinhvien");
                SinhVien sinhVienToDelete = new SinhVien();
                sinhVienToDelete.setMaSinhVien(maSinhVienToDelete);
                sinhVienDAO.delete(sinhVienToDelete);
                String maLop2 = request.getParameter("malop");
                response.sendRedirect("sinhvien?status=unsuccess&malop=" + maLop2); // Chuyển hướng với thông báo thành công
                return;

            case "update":
                // Cập nhật thông tin sinh viên
                String maSinhVienToUpdate = request.getParameter("masinhvien");
                String newTenSinhVien = request.getParameter("tensinhvien");
                Date newNgaySinh = Date.valueOf(request.getParameter("ngaysinh"));
                String newGioiTinh = request.getParameter("gioitinh");
                String newDiaChi = request.getParameter("diachi");
                String newSoDienThoai = request.getParameter("sodienthoai");
                String newEmail = request.getParameter("email");
                String newMaLop = request.getParameter("malop");
                String matKhauToUpdate=maSinhVienToUpdate+"@";
                SinhVien sinhVienToUpdate = new SinhVien(maSinhVienToUpdate, newTenSinhVien, newNgaySinh, newGioiTinh, newDiaChi, newSoDienThoai, newEmail, matKhauToUpdate, new Lop(newMaLop, "","","",null));
                sinhVienDAO.update(sinhVienToUpdate);
                response.sendRedirect("sinhvien?status=updateSuccess&malop=" + newMaLop); // Chuyển hướng với thông báo thành công
                return;
            case "dangnhap":
                String maSinhVienLogin = request.getParameter("username");
                String matKhauLogin = request.getParameter("password");

                SinhVien sinhVienLogin = new SinhVien();
                sinhVienLogin.setMaSinhVien(maSinhVienLogin);
                sinhVienLogin.setMatKhau(matKhauLogin);

                SinhVien ketQua = sinhVienDAO.selectByUsernameAndPassWord(sinhVienLogin);
                if (ketQua != null) {
                    request.getSession().setAttribute("sinhVien", ketQua);
                    response.sendRedirect("ThoiKhoaBieuSV.jsp"); 
                } else {
                    request.setAttribute("errorMessage", "Mã sinh viên hoặc mật khẩu không đúng!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

            case "doimatkhau":
                String maSV = request.getParameter("masinhvien");
                String matKhauCu = request.getParameter("oldPassword");
                String matKhauMoi = request.getParameter("newPassword");

                // Lấy sinh viên từ database theo mã sinh viên
                SinhVien sinhVien2 = sinhVienDAO.selectByMaSinhVien(maSV);

                // Kiểm tra mật khẩu cũ
                if (!sinhVien2.getMatKhau().equals(matKhauCu)) {
                    request.setAttribute("errorMessage", "Mật khẩu cũ không đúng! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau3.jsp").forward(request, response);
                } else if (matKhauMoi.equals(matKhauCu)) {
                    request.setAttribute("errorMessage", "Mật khẩu mới không được giống mật khẩu cũ! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau3.jsp").forward(request, response);
                } else {
                    // Cập nhật thông tin sinh viên với mật khẩu mới
                    SinhVien sinhVienToUpdate2 = new SinhVien(maSV, sinhVien2.getTenSinhVien(), sinhVien2.getNgaySinh(),
                            sinhVien2.getGioiTinh(), sinhVien2.getDiaChi(), sinhVien2.getSoDienThoai(), sinhVien2.getEmail(),
                            matKhauMoi, sinhVien2.getLop());

                    // Cập nhật vào database
                    int ketQua2 = sinhVienDAO.update(sinhVienToUpdate2);

                    // Kiểm tra kết quả cập nhật
                    if (ketQua2 > 0) {
                        request.setAttribute("successMessage", "Đổi mật khẩu thành công!");
                        response.sendRedirect("thanhcong.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("DoiMatKhau.jsp").forward(request, response);
                    }
                }
                break;
            case "capnhatthongtin":
                // Lấy thông tin từ form
            	 String maSinhVienToUpdate2 = request.getParameter("masinhvien");
                 String newTenSinhVien2 = request.getParameter("tenSinhVien");
                 Date newNgaySinh2 = Date.valueOf(request.getParameter("ngaySinh"));
                 String newGioiTinh2 = request.getParameter("gioiTinh");
                 String newDiaChi2 = request.getParameter("diaChi");
                 String newSoDienThoai2 = request.getParameter("soDienThoai");
                 String newEmail2 = request.getParameter("email");
                 String newMaLop2 = request.getParameter("malop");
                 String matKhauToUpdate2=maSinhVienToUpdate2+"@";
                 SinhVien sinhVienToUpdate2 = new SinhVien(maSinhVienToUpdate2, newTenSinhVien2, newNgaySinh2, newGioiTinh2, newDiaChi2, newSoDienThoai2, newEmail2, matKhauToUpdate2, new Lop(newMaLop2, "","","",null));
               
                // Lấy admin hiện tại từ session và cập nhật thông tin mới
                if (maSinhVienToUpdate2 != null) {
                	   int ketQua3 =  sinhVienDAO.update(sinhVienToUpdate2);
                    if (ketQua3 > 0) {
                        // Cập nhật thông tin thành công
                        request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
                        response.sendRedirect("thanhcong3.jsp");
                    } else {
                        // Lỗi khi cập nhật thông tin
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("CapNhapThongTin3.jsp").forward(request, response);
                    }
                } else {
                    // Mã admin không khớp hoặc không tìm thấy admin
                    request.setAttribute("errorMessage", "Không tìm thấy thông tin !");
                    request.getRequestDispatcher("CapNhapThongTin3.jsp").forward(request, response);
                }
                break;

            default:
                response.sendRedirect("sinhvien?status=error&message=Invalid action"); // Xử lý nếu hành động không hợp lệ
                return;
        }
    }
}
