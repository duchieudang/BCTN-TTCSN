<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.MonHoc"%>
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
<%@ page import="java.sql.Date"%>
<%@ page import="java.sql.Time"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Môn Học</title>
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

		<div class="container ">
			<h1>Chi Tiết Môn Học</h1>
			<%
			// Lấy mã ngành từ request
			String maHocPhan = request.getParameter("mahocphan");
			HocPhanDAO hocphanDAO = new HocPhanDAO();
			HocPhan hocPhan = hocphanDAO.getHocPhanByMaHocPhan(maHocPhan);
			GiangVienDAO giangVienDAO = new GiangVienDAO();

			LopDAO lopDAO = new LopDAO();
			ArrayList<Lop> listLop = lopDAO.selectAll();
			ArrayList<GiangVien> listGiangVien = giangVienDAO.selectAll();
			LopHocDAO lopHocDAO = new LopHocDAO();
			ArrayList<LopHoc> listLopHoc = lopHocDAO.selectAll();
			HocPhanDAO hocPhanDAO = new HocPhanDAO();
			ArrayList<HocPhan> listHocPhan = hocPhanDAO.selectAll();
			if (hocPhan != null) {
				NganhDAO nganhDAO = new NganhDAO();
				Nganh nganh = nganhDAO.getNganhByMaNganh(hocPhan.getNganh().getMaNganh());
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
						<th>Tên Khoa</th>
						<td><%=nganh.getKhoa().getTenKhoa()%></td>
					</tr>
					<tr>
						<th>Tên Ngành</th>
						<td><%=nganh.getTenNganh()%></td>
					</tr>
					<tr>
						<th>Mã Học Phần</th>
						<td><%=hocPhan.getMaHocPhan()%></td>
					</tr>
					<tr>
						<th>Tên Học Phần</th>
						<td><%=hocPhan.getTenHocPhan()%></td>
					</tr>
					<tr>
						<th>Tín Chỉ</th>
						<td><%=hocPhan.getTinChi()%></td>
					</tr>
					<tr>
						<th>Bắt Buộc</th>
						<td><%=hocPhan.getBatbuoc()%></td>
					</tr>

				</tbody>
			</table>
			<a
				href="ChiTietHocPhan2.jsp?manganh=<%=hocPhan.getNganh().getMaNganh()%>"
				class="btn btn-primary mt-3"> <i class="bi bi-arrow-left-circle"></i>
				Trở về danh sách ngành
			</a>
			<h2>Quản Lý Môn Học</h2>

			<!-- Hiển thị thông báo trạng thái -->
			<%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = ""; // Lớp thông báo
			String alertMessage = ""; // Nội dung thông báo

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Môn học đã được lưu thành công!";
			} else if ("unsuccess".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã môn học. Yêu cầu nhập lại!";
			} else if ("success3".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Môn học đã được sửa thành công!";
			} else if ("success2".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Môn học đã được xóa thành công!";
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
				data-bs-toggle="modal" data-bs-target="#addClassModal">Thêm
				Môn Học</button>

			<div class="table-responsive">
				<table class="table table-bordered text-center" id="monHocTable">
					<thead class="table-light">
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Mã môn học</th>
							<th scope="col">Tên môn học</th>
							<th scope="col">Ngày bắt đầu</th>
							<th scope="col">Ngày kết thúc</th>
							<th scope="col">Tiết bắt đầu</th>
							<th scope="col">Tiết kết thúc</th>
							<th scope="col">Lịch học</th>
							<th scope="col">Giảng Viên</th>
							<th scope="col">Lớp</th>
							<th scope="col">Lớp học</th>
							<th scope="col">Thao tác</th>

						</tr>
					</thead>
					<tbody>
						<%
						MonHocDAO monHocDAO = new MonHocDAO();
						ArrayList<MonHoc> listMonHoc = monHocDAO.selectByMaHocPhan(maHocPhan);
						int stt = 1;
						for (MonHoc monHoc : listMonHoc) {
						%>
						<tr>
							<td><%=stt++%></td>
							<td><%=monHoc.getMaMonHoc()%></td>
							<td><%=monHoc.getTenMonHoc()%></td>
							<td><%=monHoc.getNgayBatDau()%></td>
							<td><%=monHoc.getNgayKetThuc()%></td>
							<td><%=monHoc.getTietBatDau()%></td>
							<td><%=monHoc.getTietKetThuc()%></td>
							<td><%=monHoc.getLichHoc()%></td>
							<%
							GiangVien gv0 = giangVienDAO.getGiangVienByMaGiangVien(monHoc.getGiangVien().getMaGiangVien());
							LopHoc lh0 = lopHocDAO.getLopHocByMaLopHoc(monHoc.getLopHoc().getMaLopHoc());
							Lop l0 = lopDAO.getLopByMaLop(monHoc.getLop().getMaLop());
							%>
							<td><%=gv0.getTenGiangVien()%></td>
							<td><%=lh0.getTenLopHoc()%></td>
							<td><%=l0.getTenLop()+"-Khóa "+l0.getKhoa()%></td>

							<td>
								<form action="monhoc" method="post" style="display: inline;">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="maHocPhan"
										value="<%=hocPhan.getMaHocPhan()%>"> <input
										type="hidden" name="maMonHoc"
										value="<%=monHoc.getMaMonHoc()%>">
									<button type="submit" class="btn btn-danger btn-action">Xóa</button>
								</form>
								<button type="button" class="btn btn-warning btn-action"
									data-bs-toggle="modal"
									data-bs-target="#editClassModal<%=monHoc.getMaMonHoc().replace(" ", "_")%>">Sửa</button>

								<!-- Modal sửa môn học -->
								<div class="modal fade"
									id="editClassModal<%=monHoc.getMaMonHoc().replace(" ", "_")%>"
									tabindex="-1" aria-labelledby="editClassModalLabel"
									aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="editClassModalLabel">
													Sửa Môn Học:
													<%=monHoc.getMaMonHoc()%></h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<form action="monhoc" method="post">
													<input type="hidden" name="action" value="update">
													<input type="hidden" name="maMonHoc"
														value="<%=monHoc.getMaMonHoc()%>"> <input
														type="hidden" name="maHocPhan"
														value="<%=hocPhan.getMaHocPhan()%>">
													
													<div class="mb-3">
														<label for="tenMonHoc" class="form-label">Tên môn
															học</label> <input type="text" class="form-control"
															id="tenMonHoc" name="tenMonHoc"
															value="<%=monHoc.getTenMonHoc()%>" required>
													</div>
													<div class="mb-3">
														<label for="ngayBatDau" class="form-label">Ngày
															bắt đầu</label> <input type="date" class="form-control"
															id="ngayBatDau" name="ngayBatDau"
															value="<%=monHoc.getNgayBatDau()%>" required>
													</div>
													<div class="mb-3">
														<label for="ngayKetThuc" class="form-label">Ngày
															kết thúc</label> <input type="date" class="form-control"
															id="ngayKetThuc" name="ngayKetThuc"
															value="<%=monHoc.getNgayKetThuc()%>" required>
													</div>
													<div class="mb-3">
														<label for="tietBatDau" class="form-label">Tiết
															bắt đầu</label> <input type="number" class="form-control"
															id="tietBatDau" name="tietBatDau"
															value="<%=monHoc.getTietBatDau()%>" required>
													</div>
													<div class="mb-3">
														<label for="tietKetThuc" class="form-label">Tiết
															kết thúc</label> <input type="number" class="form-control"
															id="tietKetThuc" name="tietKetThuc"
															value="<%=monHoc.getTietKetThuc()%>" required>
													</div>

													<div class="mb-3">
														<label for="lichHoc" class="form-label">Lịch Học</label> <select
															class="form-select" id="lichHoc" name="lichHoc" required>
															<option value="" disabled selected>Lịch Học</option>
															<option value="ThuHai">Thứ 2</option>
															<option value="ThuBa">Thứ 3</option>
															<option value="ThuTu">Thứ 4</option>
															<option value="ThuNam">Thứ 5</option>
															<option value="ThuSau">Thứ 6</option>
															<option value="ThuBay">Thứ 7</option>
															<option value="ChuNhat">Chủ nhật</option>
														</select>
													</div>

													<div class="mb-3">
														<label for="giangVien" class="form-label">Giảng
															Viên</label> <select class="form-select" name="giangVien">
															<%
															for (GiangVien gv : listGiangVien) {
															%>
															<option value="<%=gv.getMaGiangVien()%>"
																<%=gv.getMaGiangVien().equals(monHoc.getGiangVien().getMaGiangVien()) ? "selected" : ""%>>
																<%=gv.getTenGiangVien()%></option>
															<%
															}
															%>
														</select>
													</div>
													<div class="mb-3">
														<label for="lop" class="form-label">Lớp</label> <select
															class="form-select" name="lop">
															<%
															for (Lop lop : listLop) {
															%>
															<option value="<%=lop.getMaLop()%>"
																<%=lop.getMaLop().equals(monHoc.getLop().getMaLop()) ? "selected" : ""%>>
																	<%=lop.getTenLop() +"-Khóa "+lop.getKhoa()%></option>
															<%
															}
															%>
														</select>
													</div>
													<div class="mb-3">
														<label for="lopHoc" class="form-label">Lớp Học</label> <select
															class="form-select" name="lopHoc">
															<%
															for (LopHoc lh : listLopHoc) {
															%>
															<option value="<%=lh.getMaLopHoc()%>"
																<%=lh.getMaLopHoc().equals(monHoc.getLopHoc().getMaLopHoc()) ? "selected" : ""%>>
																<%=lh.getTenLopHoc()%></option>
															<%
															}
															%>
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
			<%
			}
			%>
		</div>
	</div>

	<!-- Modal Thêm Môn Học -->
	<div class="modal fade" id="addClassModal" tabindex="-1"
		aria-labelledby="addClassModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addClassModalLabel">Thêm Môn Học</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="monhoc" method="post">
						<input type="hidden" name="action" value="add">
						
						
						<div class="mb-3">
							<label for="ngayBatDau" class="form-label">Ngày bắt đầu</label> <input
								type="date" class="form-control" id="ngayBatDau"
								name="ngayBatDau" required>
						</div>
						<div class="mb-3">
							<label for="ngayKetThuc" class="form-label">Ngày kết thúc</label>
							<input type="date" class="form-control" id="ngayKetThuc"
								name="ngayKetThuc" required>
						</div>
						<div class="mb-3">
							<label for="tietBatDau" class="form-label">Tiết bắt đầu</label> <input
								type="number" class="form-control" id="tietBatDau"
								name="tietBatDau" min="1" max="16" required>
						</div>
						<div class="mb-3">
							<label for="tietKetThuc" class="form-label">Tiết kết thúc</label>
							<input type="number" class="form-control" id="tietKetThuc"
								name="tietKetThuc" min="1" max="16" required>
						</div>

						<div class="mb-3">
							<label for="lichHoc" class="form-label">Lịch Học</label> <select
								class="form-select" id="lichHoc" name="lichHoc" required>
								<option value="" disabled selected>Lịch Học</option>
								<option value="ThuHai">Thứ 2</option>
								<option value="ThuBa">Thứ 3</option>
								<option value="ThuTu">Thứ 4</option>
								<option value="ThuNam">Thứ 5</option>
								<option value="ThuSau">Thứ 6</option>
								<option value="ThuBay">Thứ 7</option>
								<option value="ChuNhat">Chủ nhật</option>
							</select>
						</div>


						<div class="mb-3">
							<label for="giangVien" class="form-label">Giảng Viên</label> <select
								class="form-select" name="giangVien">
								<%
								for (GiangVien gv : listGiangVien) {
								%>
								<option value="<%=gv.getMaGiangVien()%>">
									<%=gv.getTenGiangVien()%></option>
								<%
								}
								%>
							</select>
						</div>
						<div class="mb-3">
							<label for="lop" class="form-label">Lớp</label> <select
								class="form-select" name="lop">
								<%
								for (Lop lop : listLop) {
								%>
								<option value="<%=lop.getMaLop()%>">
									<%=lop.getTenLop() +"-Khóa "+lop.getKhoa()%></option>
								<%
								}
								%>
							</select>
						</div>
						<div class="mb-3">
							<label for="lopHoc" class="form-label">Lớp Học</label> <select
								class="form-select" name="lopHoc">
								<%
								for (LopHoc lh : listLopHoc) {
								%>
								<option value="<%=lh.getMaLopHoc()%>">
									<%=lh.getTenLopHoc()%></option>
								<%
								}
								%>
							</select>
						</div>
						<input type="hidden" name="maHocPhan"
							value="<%=hocPhan.getMaHocPhan()%>">
							<input type="hidden" name="tenHocPhan"
							value="<%=hocPhan.getTenHocPhan()%>">
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
