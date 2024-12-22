<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Lop"%>
<%@ page import="database.LopDAO"%>
<%@ page import="model.Nganh"%>
<%@ page import="database.NganhDAO"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>
<%@ page import="model.Lop"%>
<%@ page import="database.LopDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Chi Tiết Lớp</title>
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
			<h1>Chi Tiết Sinh Viên</h1>
			<%
			  String maNganh = request.getParameter("manganh");
            NganhDAO nganhDAO = new NganhDAO();
            Nganh nganh = nganhDAO.getNganhByMaNganh(maNganh);

            if (nganh != null) {
                KhoaDAO khoaDAO = new KhoaDAO();
                Khoa khoa = khoaDAO.getKhoaByMaKhoa(nganh.getKhoa().getMaKhoa());
                LopDAO lopDAO = new LopDAO();
                ArrayList<Lop> listLop = lopDAO.selectByMaNganh(maNganh);
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
                        <th>Mã Ngành</th>
                        <td><%=nganh.getMaNganh()%></td>
                    </tr>
                    <tr>
                        <th>Tên Ngành</th>
                        <td><%=nganh.getTenNganh()%></td>
                    </tr>
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
			<a href="ChiTietNganh2.jsp?makhoa=<%=nganh.getKhoa().getMaKhoa()%>"
				class="btn btn-primary mt-3"> <i class="bi bi-arrow-left-circle"></i>
				Trở về danh sách ngành
			</a>



			<%
			} else {
			%>
			<div class="alert alert-danger">Không tìm thấy thông tin ngành.</div>
			<%
			}
			%>
		</div>
		<div class="table-responsive">
			<table class="table table-bordered text-center" id="khoaTable">
				<thead class="table-light">
					<tr>
						<th>STT</th>
						<th>Mã Lớp</th>
						<th>Tên Lớp</th>
						<th>Cố vấn học tập</th>
						<th>Khóa</th>
						<th>Thao Tác</th>
					</tr>
				</thead>
				<tbody>
					<%
					LopDAO lopDAO = new LopDAO();
					ArrayList<Lop> listLop = lopDAO.selectByMaNganh(maNganh);
					int stt = 1;
					for (Lop lop : listLop) {
					%>

					<tr>
						<td><%=stt++%></td>
						<td><%=lop.getMaLop()%></td>
						<td><%=lop.getTenLop()%></td>
						<td><%=lop.getCoVanHocTap()%></td>
						<td><%=lop.getKhoa()%></td>
						<td><a href="ChiTietSinhVien.jsp?malop=<%=lop.getMaLop()%>"
							class="btn btn-info btn-action"> Xem Chi Tiết </a></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>



	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
