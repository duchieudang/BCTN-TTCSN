package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Nganh;
import model.Khoa; // Nhập mô hình Khoa nếu bạn cần sử dụng
import database.NganhDAO;

@WebServlet("/nganh")
public class NganhController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        NganhDAO nganhDAO = new NganhDAO();
        ArrayList<Nganh> listNganh = nganhDAO.selectAll();
        request.setAttribute("listNganh", listNganh);

        // Kiểm tra nếu có yêu cầu hiển thị modal "Thêm ngành"
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }

        request.getRequestDispatcher("ChiTietNganh.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        NganhDAO nganhDAO = new NganhDAO();

        switch (action) {
            case "add":
                // Lấy dữ liệu từ form thêm ngành
               
                String tenNganh = request.getParameter("tennganh");
                String[] words = tenNganh.split(" "); // Tách chuỗi thành các từ
                String result = "";

                for (String word : words) {
                    if (!word.isEmpty()) {
                        result += word.charAt(0); // Thêm chữ cái đầu tiên của từ vào kết quả
                    }
                }
                String maNganh ="N"+ result;
                String maKhoa = request.getParameter("makhoa"); // Mã khoa tương ứng

                // Kiểm tra mã ngành trùng
                int dem = nganhDAO.sosanh(maNganh);

                if (dem > 0) {
                    response.sendRedirect("nganh?status=exists&makhoa=" + maKhoa); // Chuyển hướng với thông báo ngành đã tồn tại
                } else {
                    // Tạo đối tượng ngành và thêm vào cơ sở dữ liệu
                    Nganh nganh = new Nganh(maNganh, tenNganh, new Khoa(maKhoa, null)); // Tạo đối tượng Khoa nếu cần
                    nganhDAO.insert(nganh);
                    response.sendRedirect("nganh?status=success&makhoa=" + maKhoa); // Chuyển hướng với thông báo thành công
                }
                return;

            case "delete":
                // Xóa ngành theo mã ngành
                String maNganhToDelete = request.getParameter("manganh");
                Nganh nganhToDelete = new Nganh();
                nganhToDelete.setMaNganh(maNganhToDelete);
                nganhDAO.delete(nganhToDelete);
                String maKhoaq = request.getParameter("makhoa"); 
                response.sendRedirect("nganh?status=success&makhoa=" + maKhoaq); // Chuyển hướng với thông báo thành công
                return;

            case "update":
                // Cập nhật thông tin ngành
                String maNganhToUpdate = request.getParameter("manganh");
                String newTenNganh = request.getParameter("tennganh");
                String newMaKhoa = request.getParameter("makhoa");
                Nganh nganhToUpdate = new Nganh(maNganhToUpdate, newTenNganh, new Khoa(newMaKhoa, null));
                nganhDAO.update(nganhToUpdate);
                response.sendRedirect("nganh?status=success&makhoa=" + newMaKhoa); // Chuyển hướng với thông báo thành công
                return;

            default:
                response.sendRedirect("nganh?status=error&message=Invalid action"); // Xử lý nếu hành động không hợp lệ
                return;
        }
    }
}
