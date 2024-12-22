<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>
<%@ page import="model.Nganh"%>
<%@ page import="database.NganhDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Chi Tiết Ngành</title>
<link rel="stylesheet" href="path/to/bootstrap.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
body {
	background-color: #f4f7fa;
}

.content {
	margin-left: 270px;
}

.table th, .table td {
	vertical-align: middle;
}

.table-bordered th, .table-bordered td {
	border: 1px solid #dee2e6;
}

.text-center {
	text-align: center;
}

.table-responsive {
	margin-top: 20px;
}

.table-light {
	background-color: #f8f9fa !important;
}

.table tbody tr:hover {
	background-color: #e9ecef;
}

.btn-action {
	min-width: 80px;
}

.class-section {
	margin-top: 60px;
}
</style>
</head>
<body>

	<jsp:include page="menu.jsp" />

	<div class="content">
		<jsp:include page="header.jsp" />

		<div class="container">
		<h2>Quản Lý Môn Học</h2>
			<%
			String maKhoa = request.getParameter("makhoa");
			KhoaDAO khoaDAO = new KhoaDAO();
			Khoa khoa = khoaDAO.getKhoaByMaKhoa(maKhoa);

			if (khoa != null) {
			%>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th scope="col">Thông Tin</th>
						<th scope="col">Giá Trị</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>Mã Khoa</th>
						<td><%=khoa.getMaKhoa()%></td>
					</tr>
					<tr>
						<th>Tên Khoa</th>
						<td><%=khoa.getTenKhoa()%></td>
					</tr>
				</tbody>
			</table>
			<%
			} else {
			%>
			<div class="alert alert-danger">Không tìm thấy thông tin ngành.</div>
			<%
			}
			%>
			<a href="MonHoc.jsp" class="btn btn-primary mt-3"> <i
				class="bi bi-arrow-left-circle"></i> Trở về danh sách khoa
			</a>
		</div>
		<div class="table-responsive">
			<table class="table table-bordered text-center" id="khoaTable">
				<thead class="table-light">
					<tr>
						<th>STT</th>
						<th>Mã Ngành</th>
						<th>Tên Ngành</th>
						<th>Quản Lý Học Phần</th>
						
					</tr>
				</thead>
				<tbody>
					<%
					NganhDAO nganhDAO = new NganhDAO();
					ArrayList<Nganh> listNganh = nganhDAO.selectByMaKhoa(maKhoa);
					int stt = 1;
					for (Nganh nganh : listNganh) {
					%>

					<tr>
						<td><%=stt++%></td>
						<td><%=nganh.getMaNganh()%></td>
						<td><%=nganh.getTenNganh()%></td>
						
					<td><a href="ChiTietHocPhan2.jsp?manganh=<%=nganh.getMaNganh()%>"
							class="btn btn-info btn-action"> Xem Chi Tiết </a></td>
					
					
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
