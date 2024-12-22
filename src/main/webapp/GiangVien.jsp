<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.GiangVien"%>
<%@ page import="database.GiangVienDAO"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Giảng Viên</title>
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

.giangvien-section {
	margin-top: 60px;
}
</style>
</head>
<body>

	<jsp:include page="menu.jsp" />

	<div class="content">
		<jsp:include page="header.jsp" />

		<div class="container my-5">
			<h2>Quản Lý Giảng Viên</h2>

			<!-- Hiển thị thông báo trạng thái -->
			<%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Giảng viên đã được lưu thành công!";
			} else if ("success4".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã giảng viên. Yêu cầu nhập lại!";
			} else if ("success3".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Giảng viên đã được sửa thành công!";
			} else if ("success2".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Giảng viên đã được xóa thành công!";
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
				data-bs-toggle="modal" data-bs-target="#addGiangVienModal">Thêm
				Giảng Viên</button>

			<div class="table-responsive">
				<table class="table table-bordered text-center" id="giangVienTable">
					<thead class="table-light">
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã Giảng Viên</th>
							<th scope="col">Tên Giảng Viên</th>
							<th scope="col">Ngày Sinh</th>
							<th scope="col">Giới Tính</th>
							<th scope="col">Địa Chỉ</th>
							<th scope="col">Số Điện Thoại</th>
							<th scope="col">Email</th>
							<th scope="col">Chuyên Ngành</th>
							<th scope="col">Thao Tác</th>

						</tr>
					</thead>
					<tbody>
						<%
						GiangVienDAO giangVienDAO = new GiangVienDAO();
						ArrayList<GiangVien> listGiangVien = giangVienDAO.selectAll();
						int stt = 1;
						for (GiangVien giangVien : listGiangVien) {
						%>
						<tr>
							<td><%=stt++%></td>
							<td><%=giangVien.getMaGiangVien()%></td>
							<td><%=giangVien.getTenGiangVien()%></td>
							<td><%=giangVien.getNgaySinh()%></td>
							<td><%=giangVien.getGioiTinh()%></td>
							<td><%=giangVien.getDiaChi()%></td>
							<td><%=giangVien.getSoDienThoai()%></td>
							<td><%=giangVien.getEmail()%></td>
							<td><%=giangVien.getChuyenNganh()%></td>
							<td>
								<form action="giangvien" method="post" style="display: inline;">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="maGiangVien"
										value="<%=giangVien.getMaGiangVien()%>">
									<button type="submit" class="btn btn-danger btn-action">Xóa</button>
								</form>
								<button type="button" class="btn btn-warning btn-action"
									data-bs-toggle="modal"
									data-bs-target="#editGiangVienModal<%=giangVien.getMaGiangVien().replace(" ", "_")%>">Sửa</button>

								<!-- Modal sửa giảng viên -->
								<div class="modal fade"
									id="editGiangVienModal<%=giangVien.getMaGiangVien().replace(" ", "_")%>"
									tabindex="-1" aria-labelledby="editGiangVienModalLabel"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="editGiangVienModalLabel">
													Sửa Giảng Viên:
													<%=giangVien.getMaGiangVien()%></h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<form action="giangvien" method="post">
													<input type="hidden" name="action" value="update">
													<input type="hidden" name="maGiangVien"
														value="<%=giangVien.getMaGiangVien()%>">
														<input type="hidden" name="matKhau"
														value="<%=giangVien.getMatKhau()%>">
													<div class="mb-3">
														<label for="tenGiangVien" class="form-label">Tên
															Giảng Viên</label> <input type="text" class="form-control"
															id="tenGiangVien" name="tenGiangVien"
															value="<%=giangVien.getTenGiangVien()%>" required>
													</div>
													<div class="mb-3">
														<label for="ngaysinh" class="form-label">Ngày Sinh</label>
														<input type="date" class="form-control" id="ngaysinh"
															name="ngaysinh" value="<%=giangVien.getNgaySinh()%>"
															required>
													</div>
													<div class="mb-3">
														<label for="gioiTinh" class="form-label">Giới Tính</label>
														<select class="form-select" id="gioiTinh" name="gioiTinh"
															required>
															<option value="" disabled>Chọn giới tính</option>
															<option value="Nam"
																<%="Nam".equals(giangVien.getGioiTinh()) ? "selected" : ""%>>Nam</option>
															<option value="Nữ"
																<%="Nữ".equals(giangVien.getGioiTinh()) ? "selected" : ""%>>Nữ</option>
														</select>
													</div>
													<div class="mb-3">
														<label for="diaChi" class="form-label">Địa Chỉ</label> <input
															type="text" class="form-control" id="diaChi"
															name="diaChi" value="<%=giangVien.getDiaChi()%>" required>
													</div>
													<div class="mb-3">
														<label for="soDienThoai" class="form-label">Số
															Điện Thoại</label> <input type="text" class="form-control"
															id="soDienThoai" name="soDienThoai"
															value="<%=giangVien.getSoDienThoai()%>" required>
													</div>
													<div class="mb-3">
														<label for="email" class="form-label">Email</label> <input
															type="email" class="form-control" id="email" name="email"
															value="<%=giangVien.getEmail()%>" required>
													</div>
													<div class="mb-3">
														<label for="chuyenNganh" class="form-label">Chuyên
															Ngành</label> <input type="text" class="form-control"
															id="chuyenNganh" name="chuyenNganh"
															value="<%=giangVien.getChuyenNganh()%>" required>
													</div>

													<div class="modal-footer">
														<button type="button" class="btn btn-secondary"
															data-bs-dismiss="modal">Đóng</button>
														<button type="submit" class="btn btn-primary">Lưu
															thay đổi</button>
													</div>
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

	<!-- Modal thêm giảng viên -->
	<div class="modal fade" id="addGiangVienModal" tabindex="-1"
		aria-labelledby="addGiangVienModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addGiangVienModalLabel">Thêm Giảng
						Viên</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="giangvien" method="post">
						<input type="hidden" name="action" value="add">

						<div class="mb-3">
							<label for="tenGiangVien" class="form-label">Tên Giảng
								Viên</label> <input type="text" class="form-control" id="tenGiangVien"
								name="tenGiangVien" required>
						</div>
						<div class="mb-3">
							<label for="ngaysinh" class="form-label">Ngày Sinh</label> <input
								type="date" class="form-control" id="ngaysinh" name="ngaysinh"
								required>
						</div>
						<div class="mb-3">
							<label for="gioiTinh" class="form-label">Giới Tính</label> <select
								class="form-select" id="gioiTinh" name="gioiTinh" required>
								<option value="" disabled>Chọn giới tính</option>
								<option value="Nam">Nam</option>
								<option value="Nữ">Nữ</option>
							</select>
						</div>
						<div class="mb-3">
							<label for="diaChi" class="form-label">Địa Chỉ</label> <input
								type="text" class="form-control" id="diaChi" name="diaChi"
								required>
						</div>
						<div class="mb-3">
							<label for="soDienThoai" class="form-label">Số Điện Thoại</label>
							<input type="text" class="form-control" id="soDienThoai"
								name="soDienThoai" required>
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">Email</label> <input
								type="email" class="form-control" id="email" name="email"
								required>
						</div>
						<div class="mb-3">
							<label for="chuyenNganh" class="form-label">Chuyên Ngành</label>
							<input type="text" class="form-control" id="chuyenNganh"
								name="chuyenNganh" required>
						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Đóng</button>
							<button type="submit" class="btn btn-primary">Lưu</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
