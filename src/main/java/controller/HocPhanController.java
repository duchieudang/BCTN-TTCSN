package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HocPhan;
import model.Nganh;
import database.HocPhanDAO;
import database.NganhDAO;

@WebServlet("/hocphan")
public class HocPhanController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HocPhanDAO hocPhanDAO = new HocPhanDAO();
        ArrayList<HocPhan> listHocPhan = hocPhanDAO.selectAll();
        request.setAttribute("listHocPhan", listHocPhan);

        NganhDAO nganhDAO = new NganhDAO();
        ArrayList<Nganh> listNganh = nganhDAO.selectAll();
        request.setAttribute("listNganh", listNganh);

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true);
        }

        request.getRequestDispatcher("ChiTietHocPhan.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HocPhanDAO hocPhanDAO = new HocPhanDAO();

        switch (action) {
            case "add":
            	
           
           
                String tenHocPhan = request.getParameter("tenhocphan");
                String[] words = tenHocPhan.split(" ");
                String abbreviation = "";

                for (String word : words) {
                    abbreviation += word.charAt(0);
                }
                
                int tinChi = Integer.parseInt(request.getParameter("tinchi")); // Chuyển sang kiểu int
                String hocKy = request.getParameter("hocky");
                String batbuoc = request.getParameter("batbuoc");
                String maNganh = request.getParameter("manganh");
                String maHocPhan="HP"+ abbreviation+maNganh;
                int dem = hocPhanDAO.sosanh(maHocPhan);

                if (dem > 0) {
                    request.setAttribute("status", "error");
                    request.setAttribute("message", "Mã học phần đã tồn tại. Vui lòng nhập mã học phần khác.");
                    request.getRequestDispatcher("HocPhan.jsp").forward(request, response);
                } else {
                    HocPhan hocPhan = new HocPhan(maHocPhan, tenHocPhan, tinChi, hocKy, new Nganh(maNganh, null, null), batbuoc);
                    hocPhanDAO.insert(hocPhan);
                    response.sendRedirect("hocphan?status=success&manganh=" + maNganh);
                }
                return;

            case "update":
                String maHocPhanToUpdate = request.getParameter("mahocphan");
                String newTenHocPhan = request.getParameter("tenhocphan");
                int newTinChi = Integer.parseInt(request.getParameter("tinchi")); // Chuyển sang kiểu int
                String newHocKy = request.getParameter("hocky");
                String newBatbuoc = request.getParameter("batbuoc");
                String newMaNganh = request.getParameter("manganh");

                HocPhan hocPhanToUpdate = new HocPhan(maHocPhanToUpdate, newTenHocPhan, newTinChi, newHocKy, new Nganh(newMaNganh, null, null), newBatbuoc);
                hocPhanDAO.update(hocPhanToUpdate);
                response.sendRedirect("hocphan?status=success2&manganh=" + newMaNganh);
                return;

            case "delete":
                String maHocPhanToDelete = request.getParameter("maHocPhan");
                String deleteMaNganh = request.getParameter("manganh");
                HocPhan hocPhanToDelete = new HocPhan();
                hocPhanToDelete.setMaHocPhan(maHocPhanToDelete);
                hocPhanDAO.delete(hocPhanToDelete);
                response.sendRedirect("hocphan?status=success3&manganh=" + deleteMaNganh);
                return;

            default:
                response.sendRedirect("hocphan?status=error&message=Invalid action");
                return;
        }
    }
}
