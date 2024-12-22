package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LopHoc;
import database.LopHocDAO;

@WebServlet("/lophoc")
public class LopHocController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        LopHocDAO lopHocDAO = new LopHocDAO();
        ArrayList<LopHoc> listLopHoc = lopHocDAO.selectAll();
        request.setAttribute("listLopHoc", listLopHoc);

        // Kiểm tra nếu có yêu cầu hiển thị modal "Thêm lớp học"
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true); // Truyền thông tin để mở modal
        }

        request.getRequestDispatcher("LopHoc.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        LopHocDAO lopHocDAO = new LopHocDAO();

        switch (action) {
            case "add":
                // Lấy dữ liệu từ form thêm lớp học
           
             
          
            
                String tenLopHoc = request.getParameter("tenLopHoc");
                String maLopHoc = "LH" +tenLopHoc ;
                String toaNha = request.getParameter("toaNha");
                String phong = request.getParameter("phong");

                // Tạo đối tượng LopHoc và thêm vào cơ sở dữ liệu
                LopHoc lopHoc = new LopHoc(maLopHoc, tenLopHoc, toaNha, phong);
                lopHocDAO.insert(lopHoc);
                response.sendRedirect("lophoc?status=success"); // Chuyển hướng với thông báo thành công
                return;

            case "delete":
                // Xóa lớp học theo mã lớp học
                String maLopHocToDelete = request.getParameter("maLopHoc");
                LopHoc lopHocToDelete = new LopHoc();
                lopHocToDelete.setMaLopHoc(maLopHocToDelete);
                lopHocDAO.delete(lopHocToDelete);
                response.sendRedirect("lophoc?status=deleteSuccess"); // Chuyển hướng với thông báo thành công
                return;

            case "update":
                // Cập nhật thông tin lớp học
                String maLopHocToUpdate = request.getParameter("maLopHoc");
                String newTenLopHoc = request.getParameter("tenLopHoc");
                String newToaNha = request.getParameter("toaNha");
                String newPhong = request.getParameter("phong");

                LopHoc lopHocToUpdate = new LopHoc(maLopHocToUpdate, newTenLopHoc, newToaNha, newPhong);
                lopHocDAO.update(lopHocToUpdate);
                response.sendRedirect("lophoc?status=updateSuccess"); // Chuyển hướng với thông báo thành công
                return;

            default:
                response.sendRedirect("lophoc?status=error&message=Invalid action"); // Xử lý nếu hành động không hợp lệ
                return;
        }
    }
}
