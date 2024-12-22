package controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SinhVien;

import model.DiemDanh;
import database.DiemDanhDAO;
/**
 * Servlet implementation class DiemDanhController
 */
@WebServlet("/diemdanh")
public class DiemDanhController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] sinhVienIds = request.getParameterValues("maSinhVien"); 
        int totalWeeks = Integer.parseInt(request.getParameter("totalWeeks"));
        
        DiemDanhDAO diemdanhDAO = new DiemDanhDAO();
        
        // Loop through each student's ID
        for (String maSinhVien : sinhVienIds) {
            String soTietVang = "";
            int soBuoiVang = 0;
            
            // Loop through each week and retrieve attendance data for the student
            for (int week = 1; week <= totalWeeks; week++) {
                String attendanceParam = request.getParameter("attendance_" + maSinhVien + "_week" + week);
                if (attendanceParam != null && !attendanceParam.isEmpty()) {
                    int number = Integer.parseInt(attendanceParam);
                    if (number > 0) {
                        soBuoiVang += number;
                    }
                    soTietVang += String.valueOf(number); // Build the attendance string
                } else {
                    // If no value, assume 0 attendance for the week
                    soTietVang += "0";
                }
            }

            // Generate a random attendance ID
            Random random = new Random();
            int randomNumber = 10000000 + random.nextInt(99999999); 
            String maDiemDanh = String.valueOf(randomNumber);
            
            // Create DiemDanh object for the student
            SinhVien sinhvien = new SinhVien(maSinhVien, "", null, "", "", "", "", "", null);
            DiemDanh diemdanh = new DiemDanh(maDiemDanh, soBuoiVang, soTietVang, sinhvien);
            
            // Insert the attendance record for the student
            diemdanhDAO.insert(diemdanh);
        }

        // Redirect to the class list page or any other page
        request.getRequestDispatcher("DanhSachLop.jsp").forward(request, response);
    }
}