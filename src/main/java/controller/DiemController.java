package controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SinhVien;

import model.Diem;
import model.MonHoc;
import database.DiemDAO;
/**
 * Servlet implementation class DiemDanhController
 */
@WebServlet("/diem")
public class DiemController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the list of student IDs from the request
        String[] sinhVienIds = request.getParameterValues("maSinhVien");
        String maMonHoc = request.getParameter("mamonhoc");
        DiemDAO diemDAO = new DiemDAO();

        // Loop through each student's ID and handle their grades
        for (String maSinhVien : sinhVienIds) {
            // Get the grades for each student
            Double diemTX1 = Double.parseDouble(request.getParameter("diemTX1_" + maSinhVien));
            Double diemTX2 = Double.parseDouble(request.getParameter("diemTX2_" + maSinhVien));
            Double diemThi = Double.parseDouble(request.getParameter("diemThi_" + maSinhVien));

            // Generate a random ID for the Diem record
            Random random = new Random();
            int randomNumber = 10000000 + random.nextInt(99999999); 
            String maDiemDanh = String.valueOf(randomNumber);

            // Create a SinhVien object for the student
            SinhVien sinhvien = new SinhVien(maSinhVien, "", null, "", "", "", "", "", null);
         MonHoc monHoc=new MonHoc();
         monHoc.setMaMonHoc(maMonHoc);
            // Create a Diem object for the student's grades
            Diem diem = new Diem(maDiemDanh, diemTX1, diemTX2, diemThi, sinhvien,monHoc);

            // Insert the Diem record into the database
            diemDAO.insert(diem);
        }

        // Forward the request to the grade management page
        request.getRequestDispatcher("QuanLyDiem.jsp").forward(request, response);
    }
}