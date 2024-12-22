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
import model.MonHoc;
import model.Nganh;
import model.HocPhan;
import model.GiangVien;
import model.Lop;
import model.LopHoc;
import database.MonHocDAO;
import database.NganhDAO;

@WebServlet("/monhoc")
public class MonHocController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        NganhDAO nganhDAO = new NganhDAO();
        ArrayList<Nganh> listNganh = nganhDAO.selectAll();
        request.setAttribute("listNganh", listNganh);

        MonHocDAO monHocDAO = new MonHocDAO();
        ArrayList<MonHoc> listMonHoc = monHocDAO.selectAll();
        request.setAttribute("listMonHoc", listMonHoc);

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true);
        }

        request.getRequestDispatcher("ChiTietMonHoc.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        MonHocDAO monHocDAO = new MonHocDAO();

        switch (action) {
            case "add":
            	  Random random = new Random();
                  int randomNumber = 1 + random.nextInt(1000);
             
          
         
                String tenHocPhan= request.getParameter("tenHocPhan");
                Date ngayBatDau = Date.valueOf(request.getParameter("ngayBatDau"));
                Date ngayKetThuc = Date.valueOf(request.getParameter("ngayKetThuc"));
                
                // Chuyển đổi giờ bắt đầu và giờ kết thúc thành int
                int tietBatDau = Integer.parseInt(request.getParameter("tietBatDau"));
                int tietKetThuc = Integer.parseInt(request.getParameter("tietKetThuc"));
                
                String lichHoc = request.getParameter("lichHoc");

                String hocPhan = request.getParameter("maHocPhan");
                String maMonHoc =hocPhan+randomNumber;
                String giangVien = request.getParameter("giangVien");
                String lop = request.getParameter("lop");
                String lopHoc = request.getParameter("lopHoc");
                
                HocPhan hp = new HocPhan(hocPhan, "", 0, "", null, "");
                GiangVien gv = new GiangVien(giangVien, "", null, "", "", "", "", "", "");
                Lop lop2 = new Lop(lop, "", "", "", null);
                LopHoc lh = new LopHoc(lopHoc, "", "", "");
                 int i=1;
                 String tenMonHoc;
                 do 
                 {
                	 tenMonHoc= tenHocPhan+" " +i;
                	 if(monHocDAO.checkMonHocExistByName(tenMonHoc)) i++;
                 }while (monHocDAO.checkMonHocExistByName(tenMonHoc));
                
                int dem = monHocDAO.sosanh(maMonHoc);
                
                if (dem > 0) {
                    request.setAttribute("status", "error");
                    request.setAttribute("message", "Mã môn học đã tồn tại. Vui lòng nhập mã môn học khác.");
                    response.sendRedirect("monhoc?status=unsuccess&mahocphan=" + hocPhan);
                } else {
                    // Tạo đối tượng MonHoc và thêm vào cơ sở dữ liệu
                    MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tietBatDau, tietKetThuc, lichHoc, hp, gv, lop2, lh);
                    monHocDAO.insert(monHoc);
                    response.sendRedirect("monhoc?status=success&mahocphan=" + hocPhan);
                }
                return;

            case "update":
                String maMonHocToUpdate = request.getParameter("maMonHoc");
                String newTenMonHoc = request.getParameter("tenMonHoc");
                Date newNgayBatDau = Date.valueOf(request.getParameter("ngayBatDau"));
                Date newNgayKetThuc = Date.valueOf(request.getParameter("ngayKetThuc"));
                
                int newTietBatDau = Integer.parseInt(request.getParameter("tietBatDau"));
                int newTietKetThuc = Integer.parseInt(request.getParameter("tietKetThuc"));
                
                String newLichHoc = request.getParameter("lichHoc");

                String newHocPhan = request.getParameter("maHocPhan");
                String newGiangVien = request.getParameter("giangVien");
                String newLop = request.getParameter("lop");
                String newLopHoc = request.getParameter("lopHoc");

                HocPhan newHp = new HocPhan(newHocPhan, "", 0, "", null, "");
                GiangVien newGv = new GiangVien(newGiangVien, "", null, "", "", "", "", "", "");
                Lop newLopObj = new Lop(newLop, "", "", "", null);
                LopHoc newLh = new LopHoc(newLopHoc, "", "", "");

                MonHoc monHocToUpdate = new MonHoc(maMonHocToUpdate, newTenMonHoc, newNgayBatDau, newNgayKetThuc,
                        newTietBatDau, newTietKetThuc, newLichHoc, newHp, newGv, newLopObj, newLh);
                monHocDAO.update(monHocToUpdate);
                response.sendRedirect("monhoc?status=success3&mahocphan=" + newHocPhan);
                return;

            case "delete":
                String maMonHocToDelete = request.getParameter("maMonHoc");
                String hocPhan3 = request.getParameter("maHocPhan");
                MonHoc monHocToDelete = new MonHoc();
                monHocToDelete.setMaMonHoc(maMonHocToDelete);
                monHocDAO.delete(monHocToDelete);
           
                response.sendRedirect("monhoc?status=success2&mahocphan=" + hocPhan3);
                return;

            default:
                response.sendRedirect("monhoc?status=error&message=Invalid action");
                return;
        }
    }
}
