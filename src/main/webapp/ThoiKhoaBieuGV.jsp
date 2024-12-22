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
<%@ page import="java.sql.Time"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Thời Khóa Biểu Giảng Viên</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
.content {
	margin-left: 270px;
}
</style>
</head>
<body>

	<jsp:include page="menu2.jsp" />
	<div class="content">
		<jsp:include page="header2.jsp" />
		<h1>Thời Khóa Biểu Giảng Viên</h1>

		<!-- Filter Form -->
		<form method="get" class="row g-3">
			<div class="col-md-3">
				<label for="startDate" class="form-label">Ngày bắt đầu</label> <input
					type="date" id="startDate" name="startDate" class="form-control">
			</div>
			<div class="col-md-3">
				<label for="endDate" class="form-label">Ngày kết thúc</label> <input
					type="date" id="endDate" name="endDate" class="form-control">
			</div>
			<div class="col-md-3 d-flex align-items-end">
				<button type="submit" class="btn btn-primary">Lọc dữ liệu</button>
			</div>
		</form>
		<%
		GiangVien giangvien = (GiangVien) session.getAttribute("giangVien");
		%>
		<%
		// Khởi tạo các đối tượng cần thiết

		GiangVienDAO giangVienDAO = new GiangVienDAO();
		MonHocDAO monHocDAO = new MonHocDAO();
		ArrayList<MonHoc> listMonHocByGiangVien = monHocDAO.getMonHocByGiangVien(giangvien.getMaGiangVien());

		GiangVien giangVien = giangVienDAO.getGiangVienByMaGiangVien(giangvien.getMaGiangVien());
		%>

		<h2>
			Giảng Viên:
			<%=giangVien.getTenGiangVien()%></h2>
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
					for (MonHoc monHoc : listMonHocByGiangVien) {
						Date ngayBatDau = monHoc.getNgayBatDau();
						Date ngayKetThuc = monHoc.getNgayKetThuc();
						int hour = monHoc.getTietBatDau();
						int hourend = monHoc.getTietKetThuc();
						// Giả sử phương thức trả về đối tượng Time
						Calendar cal = Calendar.getInstance();
						cal.setTime(ngayBatDau);
						String thu = "Thứ " + ((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 2); // Thứ trong tuần (Thứ 2 - Thứ 7, Chủ Nhật)
						if(thu.equals("Thứ 8")) thu="Chủ Nhật";
						LopDAO lopDAO = new LopDAO();
						ArrayList<Lop> listLop = lopDAO.selectAll();
						ArrayList<GiangVien> listGiangVien = giangVienDAO.selectAll();
						LopHocDAO lopHocDAO = new LopHocDAO();
						ArrayList<LopHoc> listLopHoc = lopHocDAO.selectAll();
						HocPhanDAO hocPhanDAO = new HocPhanDAO();
						ArrayList<HocPhan> listHocPhan = hocPhanDAO.selectAll();
						GiangVien gv0 = giangVienDAO.getGiangVienByMaGiangVien(monHoc.getGiangVien().getMaGiangVien());
						LopHoc lh0 = lopHocDAO.getLopHocByMaLopHoc(monHoc.getLopHoc().getMaLopHoc());
						Lop l0 = lopDAO.getLopByMaLop(monHoc.getLop().getMaLop());

						// Xác định ca học sáng, chiều, tối
						String sang = "", chieu = "", toi = "";
						; // Lấy giờ từ đối tượng Time
						String phong = lh0.getPhong();
						String toaNha = lh0.getToaNha();
						String tenLop = l0.getTenLop();
						String khoa=l0.getKhoa();
						// Kiểm tra giờ để xác định buổi
						if (hour >= 1 && hour < 6) {
							sang = monHoc.getTenMonHoc();

						} else if (hour >= 7 && hour < 12) {
							chieu = monHoc.getTenMonHoc();

						} else {
							toi = monHoc.getTenMonHoc();

						}
					%>
					<tr>
						<td><%=stt++%></td>
						<td><%=thu%></td>
						<td><%=ngayBatDau%></td>
						<td><%=ngayKetThuc%></td>

						<td>
							<%
							if (!sang.isEmpty()) {
							%> <%=sang%> (<%=hour%> <%
 for (int i = hour + 1; i <= hourend; i++) {
 %>, <%=i%> <%
 }
 %>) <br> Phòng: <%=phong%><br> Tòa Nhà: <%=toaNha%><br>
							Tên Lớp: <%=tenLop+"-Khóa "+khoa %> <%
 } else {
 %> Không có lớp <%
 }
 %>
						</td>

						<td>
							<%
							if (!chieu.isEmpty()) {
							%> <%=chieu%> (<%=hour%> <%
 for (int i = hour + 1; i <= hourend; i++) {
 %>, <%=i%> <%
 }
 %>) <br> Phòng: <%=phong%><br> Tòa Nhà: <%=toaNha%><br>
							Tên Lớp: <%=tenLop+"-Khóa "+khoa %> <%
 } else {
 %> Không có lớp <%
 }
 %>
						</td>

						<td>
							<%
							if (!toi.isEmpty()) {
							%> <%=toi%> (<%=hour%> <%
 for (int i = hour + 1; i <= hourend; i++) {
 %>, <%=i%> <%
 }
 %>) <br> Phòng: <%=phong%><br> Tòa Nhà: <%=toaNha%><br>
							Tên Lớp: <%=tenLop+"-Khóa "+khoa %> <%
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
