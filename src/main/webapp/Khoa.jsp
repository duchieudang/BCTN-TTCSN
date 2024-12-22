<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Khoa</title>
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

.khoa-section {
	margin-top: 60px;
}
</style>
</head>
<body>

	<jsp:include page="menu.jsp" />

	<div class="content">
		<jsp:include page="header.jsp" />

		<div class="container my-5">
			<h2>Quản Lý Khoa</h2>

			<!-- Hiển thị thông báo trạng thái -->
			<%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Khoa đã được lưu thành công!";
			} else if ("success4".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã khoa. Yêu cầu nhập lại!";
			} else if ("success3".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Khoa đã được sửa thành công!";
			} else if ("success2".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Khoa đã được xóa thành công!";
			} else if ("success5".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Tải lên file thành công và đã thêm các khoa!";
			} else if ("uploadError".equals(status)) {
				alertClass = "alert alert-danger";
				alertMessage = "Đã xảy ra lỗi trong quá trình tải file. Vui lòng thử lại!";
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
				data-bs-toggle="modal" data-bs-target="#addKhoaModal">Thêm
				Khoa</button>


			<div class="table-responsive">
				<table class="table table-bordered text-center" id="khoaTable">
					<thead class="table-light">
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã Khoa</th>
							<th scope="col">Tên Khoa</th>
							<th scope="col">Thao tác</th>

						</tr>
					</thead>
					<tbody>
						<%
						KhoaDAO khoaDAO = new KhoaDAO();
						ArrayList<Khoa> listKhoa = khoaDAO.selectAll();
						int stt = 1;
						for (Khoa khoa : listKhoa) {
						%>
						<tr>
							<td><%=stt++%></td>
							<td><%=khoa.getMaKhoa()%></td>
							<td><%=khoa.getTenKhoa()%></td>
							<td>
								<form action="khoa" method="post" style="display: inline;">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="maKhoa" value="<%=khoa.getMaKhoa()%>">
									<button type="submit" class="btn btn-danger btn-action">Xóa</button>
								</form>
								<button type="button" class="btn btn-warning btn-action"
									data-bs-toggle="modal"
									data-bs-target="#editKhoaModal<%=khoa.getMaKhoa().replace(" ", "_")%>">Sửa</button>

								<!-- Modal sửa khoa -->
								<div class="modal fade"
									id="editKhoaModal<%=khoa.getMaKhoa().replace(" ", "_")%>"
									tabindex="-1" aria-labelledby="editKhoaModalLabel"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="editKhoaModalLabel">
													Sửa Khoa:
													<%=khoa.getMaKhoa()%>
												</h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<form action="khoa" method="post">
													<input type="hidden" name="action" value="update">
													<input type="hidden" name="maKhoa"
														value="<%=khoa.getMaKhoa()%>">
													<div class="mb-3">
														<label for="tenKhoa" class="form-label">Tên Khoa</label> <select
															class="form-select" id="tenKhoa" name="tenKhoa" required>
															<option value="" disabled selected>Chọn Tên Khoa</option>
															<option value="Khoa Công Nghệ Thông Tin">Khoa
																Công Nghệ Thông Tin</option>
															<option value="Khoa Điện - Điện Tử">Khoa Điện -
																Điện Tử</option>
															<option value="Khoa Cơ Khí">Khoa Cơ Khí</option>
															<option value="Khoa Hệ Thống Thông Tin Quản Lý">Khoa
																Hệ Thống Thông Tin Quản Lý</option>
															<option value="Khoa Khoa Học Và Kỹ Thuật Vật Liệu">Khoa
																Khoa Học Và Kỹ Thuật Vật Liệu</option>
															<option value="Khoa Kinh Tế Và Quản Trị Kinh Doanh">Khoa
																Kinh Tế Và Quản Trị Kinh Doanh</option>
															<option value="Khoa Công Nghệ May Và Thời Trang">Khoa
																Công Nghệ May Và Thời Trang</option>
															<option value="Khoa Xây Dựng">Khoa Xây Dựng</option>
															<option value="Khoa Môi Trường">Khoa Môi Trường</option>
															<option value="Khoa Ngoại Ngữ">Khoa Ngoại Ngữ</option>
														</select>
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

	<!-- Modal Thêm Khoa -->
	<div class="modal fade" id="addKhoaModal" tabindex="-1"
		aria-labelledby="addKhoaModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addKhoaModalLabel">Thêm Khoa Mới</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="khoa" method="post">
						<input type="hidden" name="action" value="add">

						<div class="mb-3">
							<label for="tenKhoa" class="form-label">Tên Khoa</label> <select
								class="form-select" id="tenKhoa" name="tenKhoa" required>
								<option value="" disabled selected>Chọn Tên Khoa</option>
								<option value="Khoa Công Nghệ Thông Tin">Khoa Công Nghệ
									Thông Tin</option>
								<option value="Khoa Điện - Điện Tử">Khoa Điện - Điện Tử</option>
								<option value="Khoa Cơ Khí">Khoa Cơ Khí</option>
								<option value="Khoa Hệ Thống Thông Tin Quản Lý">Khoa Hệ
									Thống Thông Tin Quản Lý</option>
								<option value="Khoa Khoa Học Và Kỹ Thuật Vật Liệu">Khoa
									Khoa Học Và Kỹ Thuật Vật Liệu</option>
								<option value="Khoa Kinh Tế Và Quản Trị Kinh Doanh">Khoa
									Kinh Tế Và Quản Trị Kinh Doanh</option>
								<option value="Khoa Công Nghệ May Và Thời Trang">Khoa
									Công Nghệ May Và Thời Trang</option>
								<option value="Khoa Xây Dựng">Khoa Xây Dựng</option>
								<option value="Khoa Môi Trường">Khoa Môi Trường</option>
								<option value="Khoa Ngoại Ngữ">Khoa Ngoại Ngữ</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- Button to trigger file upload modal -->



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
