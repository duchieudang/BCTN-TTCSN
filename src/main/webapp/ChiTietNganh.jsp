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
			<h1>Chi Tiết Ngành</h1>
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
			<a href="Nganh.jsp" class="btn btn-primary mt-3"> <i
				class="bi bi-arrow-left-circle"></i> Trở về danh sách ngành
			</a>
		</div>

		<div class="container my-5">
			<h1>Quản Lý Ngành</h1>

			<!-- Hiển thị thông báo trạng thái -->
			<%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Ngành đã được lưu thành công!";
			} else if ("exists".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã ngành. Yêu cầu nhập lại!";
			} else if ("updateSuccess".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Ngành đã được sửa thành công!";
			} else if ("deleteSuccess".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Ngành đã được xóa thành công!";
			} else if ("error".equals(status)) {
				alertClass = "alert alert-danger";
				alertMessage = message != null ? message : "Đã xảy ra lỗi trong quá trình xử lý!";
			}

			if (!alertMessage.isEmpty()) {
			%>
			<div class="<%=alertClass%>">
				<%=alertMessage%>
			</div>
			<%
			}
			%>

			<button type="button" class="btn btn-success mb-3"
				data-bs-toggle="modal" data-bs-target="#addNganhModal">Thêm
				Ngành</button>

			<%
			NganhDAO nganhDAO = new NganhDAO();
			ArrayList<Nganh> listNganh = nganhDAO.selectByMaKhoa(maKhoa); // Lấy tất cả các ngành
			%>

			<div class="table-responsive">
				<table class="table table-bordered text-center" id="nganhTable">
					<thead class="table-light">
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã Ngành</th>
							<th scope="col">Tên Ngành</th>
							<th scope="col">Thao tác</th>

						</tr>
					</thead>
					<tbody>
						<%
						int stt = 1;
						for (Nganh nganh : listNganh) {
						%>
						<tr>
							<td><%=stt++%></td>
							<td><%=nganh.getMaNganh()%></td>
							<td><%=nganh.getTenNganh()%></td>
							<td>
								<form action="nganh" method="post" style="display: inline;">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="manganh" value="<%=nganh.getMaNganh()%>">
									<input type="hidden" name="makhoa"
										value="<%=khoa.getMaKhoa()%>">
									<button type="submit" class="btn btn-danger btn-action">Xóa</button>
								</form>
								<button type="button" class="btn btn-warning btn-action"
									data-bs-toggle="modal"
									data-bs-target="#editNganhModal<%=nganh.getMaNganh().replace(" ", "_")%>">Sửa</button>

								<!-- Modal sửa ngành -->
								<div class="modal fade"
									id="editNganhModal<%=nganh.getMaNganh().replace(" ", "_")%>"
									tabindex="-1" aria-labelledby="editNganhModalLabel"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="editNganhModalLabel">
													Sửa Ngành:
													<%=nganh.getMaNganh()%></h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<form action="nganh" method="post">
													<input type="hidden" name="action" value="update">
													<input type="hidden" name="manganh"
														value="<%=nganh.getMaNganh()%>"> <input
														type="hidden" name="makhoa" value="<%=khoa.getMaKhoa()%>">

													<div class="mb-3">
														<label for="tenNganh" class="form-label">Tên Ngành</label>
														<input type="text" class="form-control" id="tenNganh"
															name="tennganh" value="<%=nganh.getTenNganh()%>" required>
													</div>
													<button type="submit" class="btn btn-primary">Cập
														nhật</button>
												</form>
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Add Ngành Modal -->
	<div class="modal fade" id="addNganhModal" tabindex="-1"
		aria-labelledby="addNganhModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addNganhModalLabel">Thêm Ngành Mới</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="nganh" method="post">
						<input type="hidden" name="action" value="add"> <input
							type="hidden" name="makhoa" value="<%=khoa.getMaKhoa()%>">
						
						<div class="mb-3">
							<label for="tenNganh" class="form-label">Tên Ngành</label> <input
								type="text" class="form-control" id="tenNganh" name="tennganh"
								required placeholder="Nhập tên ngành">
						</div>
						<button type="submit" class="btn btn-primary">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
