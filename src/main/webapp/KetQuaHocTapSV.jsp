<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="model.MonHoc"%>
<%@ page import="database.MonHocDAO"%>
<%@ page import="model.GiangVien"%>
<%@ page import="database.GiangVienDAO"%>
<%@ page import="database.MonHocDAO"%>
<%@ page import="model.Lop"%>
<%@ page import="database.LopDAO"%>
<%@ page import="model.LopHoc"%>
<%@ page import="database.LopHocDAO"%>
<%@ page import="model.HocPhan"%>
<%@ page import="database.HocPhanDAO"%>

<%@ page import="model.GiangVien"%>
<%@ page import="database.GiangVienDAO"%>
<%@ page import="model.HocPhan"%>
<%@ page import="database.HocPhanDAO"%>
<%@ page import="java.util.Date"%>
<%@ page import="database.SinhVienDAO"%>
<%@ page import="model.SinhVien"%>
<%@ page import="model.Diem"%>
<%@ page import="database.DiemDAO"%>
<%@ page import="model.DiemDanh"%>
<%@ page import="database.DiemDanhDAO"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.Time"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Thời Khóa Biểu Sinh Viên</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<style>
.content {
	margin-left: 270px;
}
</style>
</head>
<body>

	<jsp:include page="menu3.jsp" />
	<div class="content">
		<jsp:include page="header3.jsp" />
	 <div class="container">
           <h1>Danh Sách Sinh Viên</h1>
           <%
       	SinhVien sinhVien = (SinhVien) session.getAttribute("sinhVien");
           LopDAO lopDAO= new LopDAO();
           Lop lop=lopDAO.getLopByMaSinhVien(sinhVien.getMaSinhVien());
  		// Khởi tạo DAO để lấy danh sách môn học của sinh viên
  		MonHocDAO monHocDAO = new MonHocDAO();
  		ArrayList<MonHoc> listMonHocBySinhVien = monHocDAO.getMonHocByLop(lop.getMaLop());
              
            
           %>
          

           <!-- Table Information -->
           <table class="table table-bordered mt-3">
               <thead>
                   <tr><th scope="col">Thông Tin</th><th scope="col">Giá Trị</th></tr>
               </thead>
               <tbody>
                   <tr><th>Khoa</th><td><%= lop.getNganh().getKhoa().getTenKhoa() %></td></tr>
                   <tr><th>Ngành</th><td><%= lop.getNganh().getTenNganh() %></td></tr>
                   <tr><th>Cố vấn học tập</th><td><%= lop.getCoVanHocTap() %></td></tr>
                   <tr><th>Mã lớp</th><td><%= lop.getMaLop() %></td></tr>
                   <tr><th>Tên lớp</th><td><%= lop.getTenLop() %></td></tr>
                   <tr><th>Khóa</th><td><%= lop.getKhoa() %></td></tr>
                   <tr><th>Mã Sinh Viên</th><td><%= sinhVien.getMaSinhVien() %></td></tr>
                         <tr><th>Tên Sinh Viên</th><td><%= sinhVien.getTenSinhVien() %></td></tr>
               </tbody>
           </table>
     
           
           <!-- Attendance Section -->
           <div class="container my-5">
               <h2>Quản Lý Điểm</h2>

               <div class="table-responsive">
                   <table class="table table-bordered text-center">
                       <thead class="table-light">
                           <tr>
                               <th scope="col">STT</th>
        
                               <th scope="col">Tên Môc Học</th>
                               <th scope="col">Điểm Thường Xuyên 1</th>
                               <th scope="col">Điểm Thường Xuyên 2</th>
                               <th scope="col">Số buổi vắng</th>
                               <th scope="col">Điều kiện dự thi</th>
                               <th scope="col">Điểm Thi</th>
                               <th scope="col">Xếp loại</th>
                           </tr>
                       </thead>
                       <tbody>
                           <% int stt = 1;
                              for (MonHoc monHoc : listMonHocBySinhVien) { 
                            	   String maLop = monHoc != null ? monHoc.getLop().getMaLop() : null;
                                   Date ngayBatDau = monHoc != null ? monHoc.getNgayBatDau() : null;
                                   Date ngayKetThuc = monHoc != null ? monHoc.getNgayKetThuc() : null;
                                   int totalWeeks = ngayBatDau != null && ngayKetThuc != null ? 
                                                   (int) ((ngayKetThuc.getTime() - ngayBatDau.getTime()) / (1000 * 60 * 60 * 24 * 7)) : 0;
                                   int tietBatDau = monHoc.getTietBatDau();
                                   int tietKetThuc = monHoc.getTietKetThuc();
                                DiemDAO diemDAO = new DiemDAO();
                                Diem diem = diemDAO.getDiemByMaSinhVien(sinhVien.getMaSinhVien(),monHoc.getMaMonHoc());
                                DiemDanhDAO diemdanhDAO = new DiemDanhDAO();
                                DiemDanh diemdanh = diemdanhDAO.getDiemDanhByMaSinhVien(sinhVien.getMaSinhVien());
                                double tx1 = diem.getDiemtx();
                                double tx2 = diem.getDiemtx2();
                                double diemthi = diem.getDiemThi();
                                int soBuoiVang = diemdanh.getTongSoTietVang();
                                totalWeeks = totalWeeks * (tietKetThuc - tietBatDau + 1);
                                String xeploai = "";
                                String Dat = "";
                                if (soBuoiVang * 10 < totalWeeks * 3) Dat = "Đủ điều kiện";
                                else Dat = "Học lại";
                                
                                if (Dat.equals("Học lại")) {
                                    diemthi = 0;
                                    xeploai = "F";
                                } else {
                                    double GPA = tx1 * 0.2 + tx2 * 0.2 + diemthi * 0.6;
                                    if (GPA >= 8.5) xeploai = "A";
                                    else if (GPA >= 7.7) xeploai = "B+";
                                    else if (GPA >= 7) xeploai = "B";
                                    else if (GPA >= 6.5) xeploai = "C+";
                                    else if (GPA >= 5) xeploai = "C";
                                    else if (GPA >= 4) xeploai = "D";
                                    else xeploai = "F";
                                }
                           %>
                           <tr>
                               <td><%= stt++ %></td>
                        <td><a href="ChiTietMonHocSV.jsp?maMonHoc=<%= monHoc.getMaMonHoc() %>" style="text-decoration: none;"><%= monHoc.getTenMonHoc() %></a></td>


                               <td><%= diem.getDiemtx() %></td>
                               <td><%= diem.getDiemtx2() %></td>
                               <td><%= diemdanh.getTongSoTietVang() %></td>
                               <td><%= Dat %></td>
                               <td><%= diemthi %></td>
                               <td><%= xeploai %></td>
                           </tr>
                           <% } %>
                       </tbody>
                   </table>
               </div>
           </div>
           
       </div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
