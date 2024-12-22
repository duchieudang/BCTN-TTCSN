package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AdminDAO;
import model.Admin;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        AdminDAO adminDAO = new AdminDAO();
        
        switch (action) {
            case "dangnhap":
                String tenDangNhap = request.getParameter("username");
                String matKhau = request.getParameter("password");

                // Tạo đối tượng Admin để kiểm tra đăng nhập
                Admin admin = new Admin();
                admin.setMaAdmin(tenDangNhap);
                admin.setMatKhau(matKhau);

                // Kiểm tra tài khoản
                Admin ketQua = adminDAO.selectByUsernameAndPassWord(admin);
                if (ketQua != null) {
                    // Đăng nhập thành công
                    request.getSession().setAttribute("admin", ketQua);
                    response.sendRedirect("index.jsp"); 
                } else {
                    // Đăng nhập thất bại
                    request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

            case "doimatkhau":
                // Lấy thông tin từ form
                String maAdmin = request.getParameter("maadmin");
                String matKhauCu = request.getParameter("oldPassword");
                String matKhauMoi = request.getParameter("newPassword");

                Admin admin2 = adminDAO.selectByMaAdmin(maAdmin);

                // Kiểm tra xem mật khẩu cũ có đúng không
                if (!admin2.getMatKhau().equals(matKhauCu)) {
                    // Mật khẩu cũ không đúng
                    request.setAttribute("errorMessage", "Mật khẩu cũ không đúng! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau.jsp").forward(request, response);
                } else if (matKhauMoi.equals(matKhauCu)) {
                    // Mật khẩu mới trùng với mật khẩu cũ
                    request.setAttribute("errorMessage", "Mật khẩu mới không được giống mật khẩu cũ! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau.jsp").forward(request, response);
                } else {
                    // Thực hiện cập nhật mật khẩu mới
                    Admin adminToUpdate = new Admin(maAdmin, matKhauMoi, admin2.getHoVaTen(), admin2.getGioiTinh(), admin2.getDiaChi(), admin2.getNgaySinh(), admin2.getSoDienThoai(), admin2.getEmail());
                    int ketQua2 = adminDAO.update(adminToUpdate);

                    if (ketQua2 > 0) {
                        // Đổi mật khẩu thành công
                        request.setAttribute("successMessage", "Đổi mật khẩu thành công!");
                        response.sendRedirect("thanhcong.jsp");
                    } else {
                        // Lỗi khi cập nhật mật khẩu
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("DoiMatKhau.jsp").forward(request, response);
                    }
                }
                break;

            case "capnhatthongtin":
                // Lấy thông tin từ form
                String maAdmin2 = request.getParameter("maadmin");
                String matkhau = request.getParameter("matkhau");
                String hoVaTen = request.getParameter("hoVaTen");
                String gioiTinh = request.getParameter("gioiTinh");
                String diaChi = request.getParameter("diaChi");
                Date ngaySinh = Date.valueOf(request.getParameter("ngaySinh"));
                String soDienThoai = request.getParameter("soDienThoai");
                String email = request.getParameter("email");

                Admin adminToUpdate2 = new Admin(maAdmin2, matkhau, hoVaTen, gioiTinh, diaChi, ngaySinh, soDienThoai, email);
                
                // Lấy admin hiện tại từ session và cập nhật thông tin mới
                if (maAdmin2 != null) {
                    int ketQua3 = adminDAO.update(adminToUpdate2);

                    if (ketQua3 > 0) {
                        // Cập nhật thông tin thành công
                        request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
                        response.sendRedirect("thanhcong.jsp");
                    } else {
                        // Lỗi khi cập nhật thông tin
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("test.jsp").forward(request, response);
                    }
                } else {
                    // Mã admin không khớp hoặc không tìm thấy admin
                    request.setAttribute("errorMessage", "Không tìm thấy thông tin admin!");
                    request.getRequestDispatcher("test.jsp").forward(request, response);
                }
                break;

            default:
                response.sendRedirect("login.jsp"); // Chuyển về trang đăng nhập nếu hành động không hợp lệ
                break;
        }
    }
}
