package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GiangVien;
import database.GiangVienDAO;

@WebServlet("/giangvien")
public class GiangVienController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        GiangVienDAO giangVienDAO = new GiangVienDAO();
        ArrayList<GiangVien> listGiangVien = giangVienDAO.selectAll();
        request.setAttribute("listGiangVien", listGiangVien);

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }

        request.getRequestDispatcher("GiangVien.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        GiangVienDAO giangVienDAO = new GiangVienDAO();

        switch (action) {
            case "add":
                Random random = new Random();
                int randomNumber = 10000000 + random.nextInt(99999999);
                String maGiangVien = "GV" + String.valueOf(randomNumber);
                String tenGiangVien = request.getParameter("tenGiangVien");
                Date ngaySinh = Date.valueOf(request.getParameter("ngaysinh"));
                String gioiTinh = request.getParameter("gioiTinh");
                String diaChi = request.getParameter("diaChi");
                String soDienThoai = request.getParameter("soDienThoai");
                String email = request.getParameter("email");
                String chuyenNganh = request.getParameter("chuyenNganh");
                String matKhau = maGiangVien + "@"; // New field

                int dem = giangVienDAO.sosanh(maGiangVien);
                if (dem > 0) {
                    request.setAttribute("status", "error");
                    request.setAttribute("message", "Mã giảng viên đã tồn tại. Vui lòng nhập mã giảng viên khác.");
                    request.getRequestDispatcher("GiangVien.jsp").forward(request, response);
                } else {
                    GiangVien giangVien = new GiangVien(maGiangVien, tenGiangVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, chuyenNganh, matKhau);
                    giangVienDAO.insert(giangVien);
                    response.sendRedirect("giangvien?status=success");
                }
                return;

            case "delete":
                String maGiangVienToDelete = request.getParameter("maGiangVien");
                GiangVien giangVienToDelete = new GiangVien();
                giangVienToDelete.setMaGiangVien(maGiangVienToDelete);
                giangVienDAO.delete(giangVienToDelete);
                response.sendRedirect("giangvien?status=success2");
                return;

            case "update":
                String maGiangVienToUpdate = request.getParameter("maGiangVien");
                String newTenGiangVien = request.getParameter("tenGiangVien");
                Date newNgaySinh = Date.valueOf(request.getParameter("ngaysinh"));
                String newGioiTinh = request.getParameter("gioiTinh");
                String newDiaChi = request.getParameter("diaChi");
                String newSoDienThoai = request.getParameter("soDienThoai");
                String newEmail = request.getParameter("email");
                String newChuyenNganh = request.getParameter("chuyenNganh");
                String newMatKhau = request.getParameter("matKhau"); 

                GiangVien giangVienToUpdate = new GiangVien(maGiangVienToUpdate, newTenGiangVien, newNgaySinh, newGioiTinh, newDiaChi, newSoDienThoai, newEmail, newChuyenNganh, newMatKhau);
                giangVienDAO.update(giangVienToUpdate);
                response.sendRedirect("giangvien?status=success3");
                return;

            case "dangnhap":
                String tenDangNhap = request.getParameter("username");
                String matKhau2 = request.getParameter("password");

                // Tạo đối tượng GiangVien để kiểm tra đăng nhập
                GiangVien giangVien = new GiangVien();
                giangVien.setMaGiangVien(tenDangNhap);
                giangVien.setMatKhau(matKhau2);

                // Kiểm tra tài khoản
                GiangVien ketQua = giangVienDAO.selectByUsernameAndPassWord(giangVien);
                if (ketQua != null) {
                    // Đăng nhập thành công
                    request.getSession().setAttribute("giangVien", ketQua);
                    request.getRequestDispatcher("ThoiKhoaBieuGV.jsp").forward(request, response);
                } else {
                    // Đăng nhập thất bại
                    request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

            case "doimatkhau":
                // Lấy thông tin từ form
                String maGiangVien2 = request.getParameter("tendangnhap");
                String matKhauCu = request.getParameter("oldPassword");
                String matKhauMoi = request.getParameter("newPassword");

                GiangVien giangVien2 = giangVienDAO.selectByMaGiangVien(maGiangVien2);

                // Kiểm tra xem mật khẩu cũ có đúng không
                if (!giangVien2.getMatKhau().equals(matKhauCu)) {
                    request.setAttribute("errorMessage", "Mật khẩu cũ không đúng! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau2.jsp").forward(request, response);
                } else if (matKhauMoi.equals(matKhauCu)) {
                    request.setAttribute("errorMessage", "Mật khẩu mới không được giống mật khẩu cũ! Vui lòng nhập lại.");
                    request.getRequestDispatcher("DoiMatKhau2.jsp").forward(request, response);
                } else {
                    GiangVien giangVienToUpdate2 = new GiangVien(maGiangVien2, giangVien2.getTenGiangVien(), giangVien2.getNgaySinh(),
                            giangVien2.getGioiTinh(), giangVien2.getDiaChi(),
                            giangVien2.getSoDienThoai(), giangVien2.getEmail(),
                            giangVien2.getChuyenNganh(), matKhauMoi);
                    int ketQua2 = giangVienDAO.update(giangVienToUpdate2);

                    if (ketQua2 > 0) {
                        request.setAttribute("successMessage", "Đổi mật khẩu thành công!");
                        response.sendRedirect("thanhcong2.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("DoiMatKhau2.jsp").forward(request, response);
                    }
                }
                break;

            case "capnhatthongtin":
                // Lấy thông tin từ form
                String maGiangVien3 = request.getParameter("magiangvien");
                String matkhau2 = request.getParameter("matkhau");
                String tenGiangVien2 = request.getParameter("tenGiangVien");
                String gioiTinh2 = request.getParameter("gioiTinh");
                String diaChi2 = request.getParameter("diaChi");
                Date ngaySinh2 = Date.valueOf(request.getParameter("ngaySinh"));
                String soDienThoai2 = request.getParameter("soDienThoai");
                String email2 = request.getParameter("email");
                String chuyenNganh2 = request.getParameter("chuyenNganh");

                GiangVien giangVienToUpdate3 = new GiangVien(maGiangVien3, tenGiangVien2, ngaySinh2, gioiTinh2, diaChi2, soDienThoai2, email2, chuyenNganh2, matkhau2);

                // Lấy giảng viên hiện tại từ session và cập nhật thông tin mới
                if (maGiangVien3 != null) {
                    int ketQua3 = giangVienDAO.update(giangVienToUpdate3);

                    if (ketQua3 > 0) {
                        request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
                        response.sendRedirect("thanhcong2.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Có lỗi xảy ra. Vui lòng thử lại.");
                        request.getRequestDispatcher("capnhatthongtin.jsp").forward(request, response);
                    }
                }
                break;

            case "quenmatkhau":
                String emailForgotPassword = request.getParameter("email");
                GiangVien giangVienForgotPassword = giangVienDAO.selectByEmail(emailForgotPassword);

                if (giangVienForgotPassword != null) {
                    // Tạo mật khẩu mới
                    String newPassword = "newPassword124"; // Mật khẩu mới có thể được tạo ngẫu nhiên
                    giangVienForgotPassword.setMatKhau(newPassword);

                    // Cập nhật mật khẩu mới vào CSDL
                    giangVienDAO.update(giangVienForgotPassword);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    // Gửi email thông báo đến giảng viên
                    String subject = "Đặt lại mật khẩu";
                    String message = "Mật khẩu mới của bạn là: " + newPassword;
                    sendEmail(emailForgotPassword, subject, message, emailForgotPassword );

                    request.setAttribute("successMessage", "Mật khẩu mới đã được gửi đến email của bạn!");
                   
                } else {
                    request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống!");
                    request.getRequestDispatcher("QuanMatKhauGV.jsp").forward(request, response);
                }
                break;

            default:
                request.setAttribute("errorMessage", "Yêu cầu không hợp lệ!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                break;
        }
    }

    private void sendEmail(String recipient, String subject, String message,String email) {
    	
        String from = email; // Địa chỉ email của bạn
        String host = System.getenv("EMAIL_PASSWORD"); // Địa chỉ SMTP của bạn

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@example.com", "your-email-password"); // Thông tin đăng nhập SMTP
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
