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
<%@ page import="model.Nganh"%>
<%@ page import="database.NganhDAO"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>
<%@ page import="model.GiangVien"%>
<%@ page import="database.GiangVienDAO"%>
<%@ page import="model.HocPhan"%>
<%@ page import="database.HocPhanDAO"%>
<%@ page import="java.util.Date"%>
<%@ page import="database.SinhVienDAO"%>
<%@ page import="model.SinhVien"%>
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
		<h1>Thời Khóa Biểu Sinh Viên</h1>

		<%
		// Lấy thông tin sinh viên từ session
		SinhVien sinhVien = (SinhVien) session.getAttribute("sinhVien");
         LopDAO lopDAO= new LopDAO();
         Lop lop=lopDAO.getLopByMaSinhVien(sinhVien.getMaSinhVien());
		// Khởi tạo DAO để lấy danh sách môn học của sinh viên
		MonHocDAO monHocDAO = new MonHocDAO();
		ArrayList<MonHoc> listMonHocBySinhVien = monHocDAO.getMonHocByLop(lop.getMaLop());
		%>

		<h2>Sinh Viên: <%= sinhVien.getTenSinhVien() %></h2>
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th scope="col">STT</th>
						<th scope="col">Thứ</th>
						<th scope="col">Ngày bắt đầu</th>
						<th scope="col">Ngày kết thúc</th>
						<th scope="col">Sáng</th>
						<th scope="col">Chiều</th>
						<th scope="col">Tối</th>
					</tr>
				</thead>
				<tbody>
					<%
					int stt = 1;
					for (MonHoc monHoc : listMonHocBySinhVien) {
						Date ngayBatDau = monHoc.getNgayBatDau();
						Date ngayKetThuc = monHoc.getNgayKetThuc();
						int hour = monHoc.getTietBatDau();
						int hourend = monHoc.getTietKetThuc();
						Calendar cal = Calendar.getInstance();
						cal.setTime(ngayBatDau);
						String thu = "Thứ " + ((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 2);
						if (thu.equals("Thứ 8")) thu = "Chủ Nhật";

						// Lấy thông tin lớp học
						LopHocDAO lopHocDAO = new LopHocDAO();
						LopHoc lh = lopHocDAO.getLopHocByMaLopHoc(monHoc.getLopHoc().getMaLopHoc());

						// Xác định ca học
						String sang = "", chieu = "", toi = "";
						String phong = lh.getPhong();
						String toaNha = lh.getToaNha();

						if (hour >= 1 && hour < 6) {
							sang = monHoc.getTenMonHoc();
						} else if (hour >= 7 && hour < 12) {
							chieu = monHoc.getTenMonHoc();
						} else {
							toi = monHoc.getTenMonHoc();
						}
					%>
					<tr>
						<td><%= stt++ %></td>
						<td><%= thu %></td>
						<td><%= ngayBatDau %></td>
						<td><%= ngayKetThuc %></td>

						<td>
							<%
							if (!sang.isEmpty()) {
							%> <%= sang %> (<%= hour %> <%
								for (int i = hour + 1; i <= hourend; i++) {
							%>, <%= i %> <%
								}
							%>) <br> Phòng: <%= phong %><br> Tòa Nhà: <%= toaNha %> <%
							} else {
							%> Không có lớp <%
							}
							%>
						</td>

						<td>
							<%
							if (!chieu.isEmpty()) {
							%> <%= chieu %> (<%= hour %> <%
								for (int i = hour + 1; i <= hourend; i++) {
							%>, <%= i %> <%
								}
							%>) <br> Phòng: <%= phong %><br> Tòa Nhà: <%= toaNha %> <%
							} else {
							%> Không có lớp <%
							}
							%>
						</td>

						<td>
							<%
							if (!toi.isEmpty()) {
							%> <%= toi %> (<%= hour %> <%
								for (int i = hour + 1; i <= hourend; i++) {
							%>, <%= i %> <%
								}
							%>) <br> Phòng: <%= phong %><br> Tòa Nhà: <%= toaNha %> <%
							} else {
							%> Không có lớp <%
							}
							%>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
