package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Lop;
import model.Khoa;  // Thêm import cho Khoa
import model.Nganh; // Thêm import cho Nganh
import database.LopDAO;

@WebServlet("/lop")
public class LopController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        LopDAO lopDAO = new LopDAO();
        ArrayList<Lop> listLop = lopDAO.selectAll();
        request.setAttribute("listLop", listLop);

        // Kiểm tra nếu có yêu cầu hiển thị modal "Thêm lớp"
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }

        request.getRequestDispatcher("ChiTietLop.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        LopDAO lopDAO = new LopDAO();

        switch (action) {
            case "add":
                // Lấy dữ liệu từ form thêm lớp
              
                String tenLop = request.getParameter("tenLop");
                String coVanHocTap = request.getParameter("coVanHocTap");
                String tenKhoa = request.getParameter("khoa"); // Tên khoa
                String maNganh = request.getParameter("maNganh"); // Mã ngành
                String[] words = tenLop.split(" "); // Tách chuỗi thành các từ
                String result = "";

                for (String word : words) {
                    if (!word.isEmpty()) {
                        result += word.charAt(0); // Thêm chữ cái đầu tiên của từ vào kết quả
                    }
                }
                String maLop ="L"+ result+tenKhoa;
                // Kiểm tra mã lớp trùng
                int dem = lopDAO.sosanh(maLop);

                if (dem > 0) {
                	 response.sendRedirect("lop?status=exists&manganh=" + maNganh); 
                } else {
                    // Tạo đối tượng Khoa và Nganh
                    Khoa khoa = new Khoa("", ""); // Tên khoa có thể được truy vấn thêm nếu cần
                    Nganh nganh = new Nganh(maNganh, "", khoa);
                    // Tạo đối tượng lớp và thêm vào cơ sở dữ liệu
                    Lop lop = new Lop(maLop, tenLop, coVanHocTap, tenKhoa, nganh);
                    lopDAO.insert(lop);
                    response.sendRedirect("lop?status=successs&manganh=" + maNganh); // Chuyển hướng với thông báo thành công
                }
                return;

            case "delete":
                // Xóa lớp theo mã lớp
                String maLopToDelete = request.getParameter("malop");
                String maNganh2 = request.getParameter("maNganh");
                Lop lopToDelete = new Lop();
                lopToDelete.setMaLop(maLopToDelete);
                lopDAO.delete(lopToDelete);
                response.sendRedirect("lop?status=successs&manganh=" + maNganh2); // Chuyển hướng với thông báo thành công
                return;

            case "update":
                // Cập nhật thông tin lớp
                String maLopToUpdate = request.getParameter("malop");
                String newTenLop = request.getParameter("tenlop");
                String newCoVanHocTap = request.getParameter("covanhoctap");
                String newTenKhoa = request.getParameter("khoa"); // Tên khoa mới
                String newMaNganh = request.getParameter("maNganh"); // Mã ngành mới
                // Tạo đối tượng Khoa và Nganh cho cập nhật
                Khoa newKhoa = new Khoa("", ""); // Tên khoa có thể được truy vấn thêm nếu cần
                Nganh newNganh = new Nganh(newMaNganh, "", newKhoa);
                Lop lopToUpdate = new Lop(maLopToUpdate, newTenLop, newCoVanHocTap, newTenKhoa, newNganh);
                lopDAO.update(lopToUpdate);
                response.sendRedirect("lop?status=success&manganh=" + newMaNganh); // Chuyển hướng với thông báo thành công
                return;

            default:
                response.sendRedirect("lop?status=error&message=Invalid action"); // Xử lý nếu hành động không hợp lệ
                return;
        }
    }
}
