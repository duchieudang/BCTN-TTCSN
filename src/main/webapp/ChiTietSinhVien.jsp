<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.SinhVien"%>
<%@ page import="database.SinhVienDAO"%>
<%@ page import="model.Lop"%>
<%@ page import="database.LopDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Chi Tiết Sinh Viên</title>
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
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Sinh Viên đã được lưu thành công!";
			} else if ("updateSuccess".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Sinh Viên đã được sửa thành công!";
			} else if ("unsuccess".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Lớp đã được xóa thành công!";
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
			<%
			String maLop = request.getParameter("malop");
			if (maLop != null) {
				LopDAO lopDAO = new LopDAO();
				Lop lop = lopDAO.getLopByMaLop(maLop);

				if (lop != null) {
			%>

			<table class="table table-bordered mt-3">
				<thead>
					<tr>
						<th scope="col">Thông Tin</th>
						<th scope="col">Giá Trị</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>Khoa</th>
						<td><%=lop.getNganh().getKhoa().getTenKhoa()%></td>
					</tr>
					<tr>
						<th>Ngành</th>
						<td><%=lop.getNganh().getTenNganh()%></td>
					</tr>
					<tr>
						<th>Cố vấn học tập</th>
						<td><%=lop.getCoVanHocTap()%></td>
					</tr>
					<tr>
						<th>Mã lớp</th>
						<td><%=lop.getMaLop()%></td>
					</tr>
					<tr>
						<th>Tên lớp</th>
						<td><%=lop.getTenLop()%></td>
					</tr>
					<tr>
						<th>Khóa</th>
						<td><%=lop.getKhoa()%></td>
					</tr>
				</tbody>
			</table>
			<a href="ChiTietLop2.jsp?manganh=<%=lop.getNganh().getMaNganh()%>"
				class="btn btn-primary mt-3">Trở về danh sách sinh viên</a>
			<%
			} else {
			%>
			<div class="alert alert-warning">Không tìm thấy thông tin lớp.</div>
			<%
			}
			} else {
			%>
			<div class="alert alert-danger">Không tìm thấy thông tin sinh
				viên.</div>
			<%
			}
			%>

		</div>
		<div class="container my-5">
			<h1>Danh Sách Sinh Viên</h1>
			<button type="button" class="btn btn-success mb-3"
				data-bs-toggle="modal" data-bs-target="#addSinhVienModal">Thêm
				Sinh Viên</button>

			<%
			SinhVienDAO sinhVienDAO = new SinhVienDAO();
			ArrayList<SinhVien> listSinhVien = sinhVienDAO.selectByMaLop(maLop);
			%>

			<div class="table-responsive">
				<table class="table table-bordered text-center" id="sinhVienTable">
					<thead class="table-light">
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã Sinh Viên</th>
							<th scope="col">Tên Sinh Viên</th>
							<th scope="col">Ngày Sinh</th>
							<th scope="col">Giới Tính</th>
							<th scope="col">Địa Chỉ</th>
							<th scope="col">Số Điện Thoại</th>
							<th scope="col">Email</th>
							<th scope="col">Thao tác</th>

						</tr>
					</thead>
					<tbody>
						<%
						int stt = 1;
						for (SinhVien sv : listSinhVien) {
						%>
						<tr>
							<td><%=stt++%></td>
							<td><%=sv.getMaSinhVien()%></td>
							<td><%=sv.getTenSinhVien()%></td>
							<td><%=sv.getNgaySinh()%></td>
							<td><%=sv.getGioiTinh()%></td>
							<td><%=sv.getDiaChi()%></td>
							<td><%=sv.getSoDienThoai()%></td>
							<td><%=sv.getEmail()%></td>
							<td>
								<form action="sinhvien" method="post" style="display: inline;">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="masinhvien"
										value="<%=sv.getMaSinhVien()%>"> <input type="hidden"
										name="malop" value="<%=sv.getLop().getMaLop()%>">
									<button type="submit" class="btn btn-danger btn-action">Xóa</button>
								</form>

								<button type="button" class="btn btn-warning btn-action"
									data-bs-toggle="modal"
									data-bs-target="#editSinhVienModal<%=sv.getMaSinhVien().replace(" ", "_")%>">Sửa</button>

								<div class="modal fade"
									id="editSinhVienModal<%=sv.getMaSinhVien().replace(" ", "_")%>"
									tabindex="-1" aria-labelledby="editSinhVienModalLabel"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="editSinhVienModalLabel">
													Sửa Thông Tin Sinh Viên:
													<%=sv.getMaSinhVien()%></h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<form action="sinhvien" method="post">
													<input type="hidden" name="action" value="update">
													<input type="hidden" name="masinhvien"
														value="<%=sv.getMaSinhVien()%>"> <input
														type="hidden" name="malop"
														value="<%=sv.getLop().getMaLop()%>">
													<div class="mb-3">
														<label for="tensinhvien" class="form-label">Tên
															Sinh Viên</label> <input type="text" class="form-control"
															id="tensinhvien" name="tensinhvien"
															value="<%=sv.getTenSinhVien()%>" required>
													</div>

													<div class="mb-3">
														<label for="ngaysinh" class="form-label">Ngày Sinh</label>
														<input type="date" class="form-control" id="ngaysinh"
															name="ngaysinh" value="<%=sv.getNgaySinh()%>" required>
													</div>

													<div class="mb-3">
														<label for="gioitinh" class="form-label">Giới Tính</label>
														<select class="form-control" id="gioitinh" name="gioitinh">
															<option value="Nam"
																<%="Nam".equals(sv.getGioiTinh()) ? "selected" : ""%>>Nam</option>
															<option value="Nữ"
																<%="Nữ".equals(sv.getGioiTinh()) ? "selected" : ""%>>Nữ</option>
														</select>
													</div>

													<div class="mb-3">
														<label for="diachi" class="form-label">Địa Chỉ</label> <input
															type="text" class="form-control" id="diachi"
															name="diachi" value="<%=sv.getDiaChi()%>" required>
													</div>

													<div class="mb-3">
														<label for="sodienthoai" class="form-label">Số
															Điện Thoại</label> <input type="text" class="form-control"
															id="sodienthoai" name="sodienthoai"
															value="<%=sv.getSoDienThoai()%>" required>
													</div>

													<div class="mb-3">
														<label for="email" class="form-label">Email</label> <input
															type="email" class="form-control" id="email" name="email"
															value="<%=sv.getEmail()%>" required>
													</div>

													<button type="submit" class="btn btn-primary">Cập
														Nhật</button>
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
	<div class="modal fade" id="addSinhVienModal" tabindex="-1"
		aria-labelledby="addSinhVienModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addSinhVienModalLabel">Thêm Sinh
						Viên Mới</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="sinhvien" method="post">
						<input type="hidden" name="action" value="add"> <input
							type="hidden" name="malop" value="<%=maLop%>">
						<!-- Thêm mã lớp để liên kết sinh viên với lớp -->


						<div class="mb-3">
							<label for="tenSinhVien" class="form-label">Tên Sinh Viên</label>
							<input type="text" class="form-control" id="tenSinhVien"
								name="tensinhvien" required>
						</div>
						<div class="mb-3">
							<label for="ngaySinh" class="form-label">Ngày Sinh</label> <input
								type="date" class="form-control" id="ngaySinh" name="ngaysinh"
								required>
						</div>
						<div class="mb-3">
							<label for="gioiTinh" class="form-label">Giới Tính</label> <select
								class="form-control" id="gioiTinh" name="gioitinh">
								<option value="Nam">Nam</option>
								<option value="Nữ">Nữ</option>
							</select>
						</div>
						<div class="mb-3">
							<label for="diaChi" class="form-label">Địa Chỉ</label> <select
								class="form-select" id="diaChi" name="diachi" required>
								<option value="" selected>Chọn Tỉnh/Thành phố</option>
								<option value="An Giang">An Giang</option>
								<option value="Bà Rịa - Vũng Tàu">Bà Rịa - Vũng Tàu</option>
								<option value="Bắc Giang">Bắc Giang</option>
								<option value="Bắc Kạn">Bắc Kạn</option>
								<option value="Bạc Liêu">Bạc Liêu</option>
								<option value="Bắc Ninh">Bắc Ninh</option>
								<option value="Bến Tre">Bến Tre</option>
								<option value="Bình Định">Bình Định</option>
								<option value="Bình Dương">Bình Dương</option>
								<option value="Bình Phước">Bình Phước</option>
								<option value="Bình Thuận">Bình Thuận</option>
								<option value="Cà Mau">Cà Mau</option>
								<option value="Cần Thơ">Cần Thơ</option>
								<option value="Cao Bằng">Cao Bằng</option>
								<option value="Đà Nẵng">Đà Nẵng</option>
								<option value="Đắk Lắk">Đắk Lắk</option>
								<option value="Đắk Nông">Đắk Nông</option>
								<option value="Điện Biên">Điện Biên</option>
								<option value="Đồng Nai">Đồng Nai</option>
								<option value="Đồng Tháp">Đồng Tháp</option>
								<option value="Gia Lai">Gia Lai</option>
								<option value="Hà Giang">Hà Giang</option>
								<option value="Hà Nam">Hà Nam</option>
								<option value="Hà Nội">Hà Nội</option>
								<option value="Hà Tĩnh">Hà Tĩnh</option>
								<option value="Hải Dương">Hải Dương</option>
								<option value="Hải Phòng">Hải Phòng</option>
								<option value="Hậu Giang">Hậu Giang</option>
								<option value="Hòa Bình">Hòa Bình</option>
								<option value="Hồ Chí Minh">Hồ Chí Minh</option>
								<option value="Hưng Yên">Hưng Yên</option>
								<option value="Khánh Hòa">Khánh Hòa</option>
								<option value="Kiên Giang">Kiên Giang</option>
								<option value="Kon Tum">Kon Tum</option>
								<option value="Lai Châu">Lai Châu</option>
								<option value="Lâm Đồng">Lâm Đồng</option>
								<option value="Lạng Sơn">Lạng Sơn</option>
								<option value="Lào Cai">Lào Cai</option>
								<option value="Long An">Long An</option>
								<option value="Nam Định">Nam Định</option>
								<option value="Nghệ An">Nghệ An</option>
								<option value="Ninh Bình">Ninh Bình</option>
								<option value="Ninh Thuận">Ninh Thuận</option>
								<option value="Phú Thọ">Phú Thọ</option>
								<option value="Phú Yên">Phú Yên</option>
								<option value="Quảng Bình">Quảng Bình</option>
								<option value="Quảng Nam">Quảng Nam</option>
								<option value="Quảng Ngãi">Quảng Ngãi</option>
								<option value="Quảng Ninh">Quảng Ninh</option>
								<option value="Quảng Trị">Quảng Trị</option>
								<option value="Sóc Trăng">Sóc Trăng</option>
								<option value="Sơn La">Sơn La</option>
								<option value="Tây Ninh">Tây Ninh</option>
								<option value="Thái Bình">Thái Bình</option>
								<option value="Thái Nguyên">Thái Nguyên</option>
								<option value="Thanh Hóa">Thanh Hóa</option>
								<option value="Thừa Thiên Huế">Thừa Thiên Huế</option>
								<option value="Tiền Giang">Tiền Giang</option>
								<option value="Trà Vinh">Trà Vinh</option>
								<option value="Tuyên Quang">Tuyên Quang</option>
								<option value="Vĩnh Long">Vĩnh Long</option>
								<option value="Vĩnh Phúc">Vĩnh Phúc</option>
								<option value="Yên Bái">Yên Bái</option>
							</select>
						</div>

						<div class="mb-3">
							<label for="soDienThoai" class="form-label">Số Điện Thoại</label>
							<input type="text" class="form-control" id="soDienThoai"
								name="sodienthoai" required>
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">Email</label> <input
								type="email" class="form-control" id="email" name="email"
								required>
						</div>

						<button type="submit" class="btn btn-primary">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
