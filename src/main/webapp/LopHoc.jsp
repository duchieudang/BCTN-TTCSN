<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.LopHoc"%>
<%@ page import="database.LopHocDAO"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản Lý Lớp Học</title>
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
	<jsp:include page="menu.jsp" />
	<div class="content">
		<jsp:include page="header.jsp" />
		<div class="container my-5">
			<h2>Quản Lý Lớp Học</h2>

			<!-- Hiển thị thông báo trạng thái -->
			  <%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Lớp Học đã được lưu thành công!";
			} else if ("success4".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã lớp học. Yêu cầu nhập lại!";
			} else if ("deleteSuccess".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Lớp Học đã được xóa thành công!";
			} else if ("updateSuccess".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Lớp Học đã được sửa thành công!";
			} else if ("error".equals(status)) {
				alertClass = "alert alert-danger";
				alertMessage = message != null ? message : "Đã xảy ra lỗi trong quá trình xử lý!";
			}

			if (!alertMessage.isEmpty()) {
			%>
		<div class="<%=alertClass%> alert-dismissible fade show"
				role="alert">
				<%=alertMessage%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
			}
			%>

			<!-- Nút thêm lớp học -->
			<button type="button" class="btn btn-primary mb-3"
				data-bs-toggle="modal" data-bs-target="#addLopHocModal">Thêm
				Lớp Học</button>

			<!-- Bảng hiển thị danh sách lớp học -->
			<table class="table table-bordered text-center">
				<thead>
					<tr>
						<th scope="col">STT</th>
						<th scope="col">Mã Lớp Học</th>
						<th scope="col">Tên Lớp Học</th>
						<th scope="col">Tòa Nhà</th>
						<th scope="col">Phòng</th>
						<th scope="col">Thao Tác</th>

					</tr>
				</thead>
				<tbody>
					<%
					LopHocDAO lopHocDAO = new LopHocDAO();
					ArrayList<LopHoc> listLopHoc = lopHocDAO.selectAll();
					int stt = 1;
					for (LopHoc lh : listLopHoc) {
					%>
					<tr>
						<td><%=stt++%></td>
						<td><%=lh.getMaLopHoc()%></td>
						<td><%=lh.getTenLopHoc()%></td>
						<td><%=lh.getToaNha()%></td>
						<td><%=lh.getPhong()%></td>
						<td>
							<!-- Nút xóa -->
							<form action="lophoc" method="post" style="display: inline;">
								<input type="hidden" name="action" value="delete"> <input
									type="hidden" name="maLopHoc" value="<%=lh.getMaLopHoc()%>">
								<button type="submit" class="btn btn-danger">Xóa</button>
							</form> <!-- Nút sửa -->
							<button type="button" class="btn btn-warning"
								data-bs-toggle="modal"
								data-bs-target="#editLopHocModal<%=lh.getMaLopHoc().replace(" ", "_")%>">Sửa</button>

							<!-- Modal sửa lớp học -->
							<div class="modal fade"
								id="editLopHocModal<%=lh.getMaLopHoc().replace(" ", "_")%>"
								tabindex="-1" aria-labelledby="editLopHocModalLabel"
								aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">
												Sửa Lớp Học:
												<%=lh.getMaLopHoc()%></h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<form action="lophoc" method="post">
												<input type="hidden" name="action" value="update"> <input
													type="hidden" name="maLopHoc"
													value="<%=lh.getMaLopHoc()%>">
												<div class="mb-3">
													<label for="tenLopHoc" class="form-label">Tên Lớp
														Học</label> <input type="text" class="form-control"
														name="tenLopHoc" value="<%=lh.getTenLopHoc()%>" required>
												</div>
												<div class="mb-3">
													<label for="toaNha" class="form-label">Tòa Nhà</label> <input
														type="text" class="form-control" name="toaNha"
														value="<%=lh.getToaNha()%>" required>
												</div>
												<div class="mb-3">
													<label for="phong" class="form-label">Phòng</label> <input
														type="text" class="form-control" name="phong"
														value="<%=lh.getPhong()%>" required>
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

	<!-- Modal thêm lớp học -->
	<div class="modal fade" id="addLopHocModal" tabindex="-1"
		aria-labelledby="addLopHocModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Thêm Lớp Học Mới</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="lophoc" method="post">
						<input type="hidden" name="action" value="add">
					
						<div class="mb-3">
							<label for="tenLopHoc" class="form-label">Tên Lớp Học</label> <input
								type="text" class="form-control" name="tenLopHoc" required>
						</div>
						<div class="mb-3">
							<label for="toaNha" class="form-label">Tòa Nhà</label> <input
								type="text" class="form-control" name="toaNha" required>
						</div>
						<div class="mb-3">
							<label for="phong" class="form-label">Phòng</label> <input
								type="text" class="form-control" name="phong" required>
						</div>
						<button type="submit" class="btn btn-primary">Thêm Lớp
							Học</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
