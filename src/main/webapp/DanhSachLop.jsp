<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.MonHoc"%>
<%@ page import="database.MonHocDAO"%>
<%@ page import="java.util.Random"%>
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
    <jsp:include page="menu2.jsp" />
	<div class="content">
		<jsp:include page="header2.jsp" />
        <div class="container">
            <h1>Danh Sách Sinh Viên</h1>
            <%
                GiangVien giangVien = (GiangVien) session.getAttribute("giangVien");
                String maMonHoc = request.getParameter("mamonhoc");
                MonHocDAO monHocDAO = new MonHocDAO();
                MonHoc monHoc = monHocDAO.getMonHocByMonHoc(maMonHoc);
                String maLop = monHoc != null ? monHoc.getLop().getMaLop() : null;
                Date ngayBatDau = monHoc != null ? monHoc.getNgayBatDau() : null;
                Date ngayKetThuc = monHoc != null ? monHoc.getNgayKetThuc() : null;
                int totalWeeks = ngayBatDau != null && ngayKetThuc != null ? 
                                (int) ((ngayKetThuc.getTime() - ngayBatDau.getTime()) / (1000 * 60 * 60 * 24 * 7)) : 0;
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
                    <tr><th>Khoa</th><td><%= lop.getNganh().getKhoa().getTenKhoa() %></td></tr>
                    <tr><th>Ngành</th><td><%= lop.getNganh().getTenNganh() %></td></tr>
                    <tr><th>Cố vấn học tập</th><td><%= lop.getCoVanHocTap() %></td></tr>
                    <tr><th>Mã lớp</th><td><%= lop.getMaLop() %></td></tr>
                    <tr><th>Tên lớp</th><td><%= lop.getTenLop() %></td></tr>
                    <tr><th>Khóa</th><td><%= lop.getKhoa() %></td></tr>
                </tbody>
            </table>
            <a href="DanhSachMonHoc.jsp?magiangvien=<%= giangVien.getMaGiangVien() %>" class="btn btn-primary mt-3">Trở về danh sách sinh viên</a>
            
            <!-- Attendance Section -->
            <div class="container my-5">
                <h2>Điểm Danh Theo Tuần</h2>
                <p>Đánh dấu để điểm danh sinh viên mỗi tuần.</p>
              
                <form action="diemdanh" method="post">
                    <input type="hidden" name="totalWeeks" value="<%= totalWeeks %>"/> 
                      <input type="hidden" name="mamonhoc" value="<%= monHoc.getMaMonHoc() %>"/> 
                    <!-- Thêm totalWeeks -->
                    <div class="table-responsive">
                        <table class="table table-bordered text-center">
                            <thead class="table-light">
                                <tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Mã Sinh Viên</th>
                                    <th scope="col">Tên Sinh Viên</th>
                                    <% for (int week = 1; week <= totalWeeks; week++) { %>
                                        <th scope="col">Tuần <%= week %></th>
                                    <% } %>
                                </tr>
                            </thead>
                            <tbody>
                                <% int stt = 1;
                                   for (SinhVien sv : listSinhVien) { %>
                                   
                                <tr>  
                                    <td><%= stt++ %></td>
                                    <td>  <input type="hidden" name="maSinhVien" value="<%= sv.getMaSinhVien() %>"/><%= sv.getMaSinhVien() %></td>
                                    <td><%= sv.getTenSinhVien() %></td>
                                    <% for (int week = 1; week <= totalWeeks; week++) {
                                    	DiemDanhDAO diemdanhDAO = new DiemDanhDAO();
                                        boolean tonTai = diemdanhDAO.kiemTraDiemDanhTheoMaSinhVien(sv.getMaSinhVien());
                                        if (!tonTai) {
                                        	 Random random = new Random();
                                         	int randomNumber = 10000000 + random.nextInt(99999999); 
                                         	String maDiemDanh =String.valueOf(randomNumber);
                                            SinhVien sinhvien = new SinhVien(sv.getMaSinhVien(), "", null, "", "", "", "", "", null);
                                            DiemDanh diemdanh2 = new DiemDanh(maDiemDanh, 0, "", sinhvien);
                                            diemdanhDAO.insert(diemdanh2);
                                        } 
 
                                        DiemDanh diemdanh = diemdanhDAO.getDiemDanhByMaSinhVien(sv.getMaSinhVien());
                                        String sotietvang = diemdanh.getSoTietVang();
                                        while (sotietvang.length() < totalWeeks) {
                                            sotietvang += "0";
                                        }
                                    	char kyTu = sotietvang.charAt(week - 1);
                                            int so = kyTu - '0';%>
                                        <td>
                                        <input type="number" name="attendance_<%= sv.getMaSinhVien() %>_week<%= week %>" value="<%= so %>" min="0" max="5" />

                                       
                                        </td>
                                    <% } %>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Lưu Điểm Danh</button>
                </form>
              
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
