<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.MonHoc"%>
<%@ page import="database.MonHocDAO"%>
<%@ page import="model.Lop"%>
<%@ page import="database.SinhVienDAO"%>
<%@ page import="model.SinhVien"%>
<%@ page import="database.LopDAO"%>
<%@ page import="java.util.Date"%>
<%@ page import="database.LopHocDAO"%>
<%@ page import="model.HocPhan"%>
<%@ page import="database.HocPhanDAO"%>
<%@ page import="model.Nganh"%>
<%@ page import="database.NganhDAO"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>
<%@ page import="model.GiangVien"%>
<%@ page import="database.GiangVienDAO"%>
<%@ page import="model.Diem"%>
<%@ page import="database.DiemDAO"%>
<%@ page import="model.DiemDanh"%>
<%@ page import="database.DiemDanhDAO"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7fa; }
        .content { margin-left: 270px; }
        .table th, .table td { vertical-align: middle; }
        .table-bordered th, .table-bordered td { border: 1px solid #dee2e6; }
        .table-responsive { margin-top: 20px; }
        .table-light { background-color: #f8f9fa !important; }
        .table tbody tr:hover { background-color: #e9ecef; }
    </style>
</head>
<body>
   <jsp:include page="menu3.jsp" />
   <div class="content">
       <jsp:include page="header3.jsp" />
       <div class="container">
           <h1>Danh Sách Sinh Viên</h1>
           <%
               String maMonHoc = request.getParameter("maMonHoc");
               MonHocDAO monHocDAO = new MonHocDAO();
               MonHoc monHoc = monHocDAO.getMonHocByMonHoc(maMonHoc);
               HocPhanDAO hocPhanDAO = new HocPhanDAO();
               HocPhan hocPhan = hocPhanDAO.getHocPhanByMaHocPhan(monHoc.getHocPhan().getMaHocPhan());
               String maLop = monHoc != null ? monHoc.getLop().getMaLop() : null;
               Date ngayBatDau = monHoc != null ? monHoc.getNgayBatDau() : null;
               Date ngayKetThuc = monHoc != null ? monHoc.getNgayKetThuc() : null;
               int totalWeeks = ngayBatDau != null && ngayKetThuc != null ? 
                               (int) ((ngayKetThuc.getTime() - ngayBatDau.getTime()) / (1000 * 60 * 60 * 24 * 7)) : 0;
               int tietBatDau = monHoc.getTietBatDau();
               int tietKetThuc = monHoc.getTietKetThuc();
           %>
           <% if (maMonHoc != null && maLop != null) { 
               LopDAO lopDAO = new LopDAO();
               Lop lop = lopDAO.getLopByMaLop(maLop);
               ArrayList<SinhVien> listSinhVien = new SinhVienDAO().selectByMaLop(maLop);
           %>

           <!-- Table Information -->
           <table class="table table-bordered mt-3">
               <thead>
                   <tr><th scope="col">Thông Tin</th><th scope="col">Giá Trị</th></tr>
               </thead>
               <tbody>
                   <tr><th>Môn</th><td><%= monHoc.getTenMonHoc() %></td></tr>
                   <tr><th>Mã Lớp</th><td><%= monHoc.getLop().getMaLop() %></td></tr>
                   <tr><th>Số tín chỉ</th><td><%= hocPhan.getTinChi() %></td></tr>
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
                            
                               <th scope="col">Tên Sinh Viên</th>
                               <th scope="col">Điểm Thường Xuyên 1</th>
                               <th scope="col">Điểm Thường Xuyên 2</th>
                               <th scope="col">Số buổi vắng</th>
                               <th scope="col">Điều kiên dự thi</th>
                               <th scope="col">Điểm Thi</th>
                               <th scope="col">Xếp loại</th>
                           </tr>
                       </thead>
                       <tbody>
                           <% int stt = 1;
                              for (SinhVien sv : listSinhVien) { 
                                DiemDAO diemDAO = new DiemDAO();
                                Diem diem = diemDAO.getDiemByMaSinhVien(sv.getMaSinhVien());
                                DiemDanhDAO diemdanhDAO = new DiemDanhDAO();
                                DiemDanh diemdanh = diemdanhDAO.getDiemDanhByMaSinhVien(sv.getMaSinhVien());
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
                                    if (GPA < 8.5 && GPA >= 7.7) xeploai = "B+";
                                    if (GPA < 7.7 && GPA >= 7) xeploai = "B";
                                    if (GPA < 7 && GPA >= 6.5) xeploai = "C+";
                                    if (GPA < 6.5 && GPA >= 5) xeploai = "C";
                                    if (GPA < 5 && GPA >= 4) xeploai = "D";
                                    if (GPA < 4) xeploai = "F";
                                }
                           %>
                           <tr>
                               <td><%= stt++ %></td>
                            
                              <td><a href="KetQuaHocTap2.jsp?maSinhVien=<%= sv.getMaSinhVien() %>" style="text-decoration: none;"><%= sv.getTenSinhVien() %></a></td>

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
           <% } else { %>
               <div class="alert alert-danger">Không tìm thấy thông tin sinh viên hoặc lớp học.</div>
           <% } %>
       </div>
   </div>

   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
