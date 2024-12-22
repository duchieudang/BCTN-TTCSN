<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Lop"%>
<%@ page import="database.HocPhanDAO"%>
<%@ page import="model.Nganh"%>
<%@ page import="database.NganhDAO"%>
<%@ page import="model.Khoa"%>
<%@ page import="database.KhoaDAO"%>
<%@ page import="model.HocPhan"%>
<%@ page import="database.HocPhanDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Lớp</title>
    <link rel="stylesheet" href="path/to/bootstrap.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
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
            <h1>Chi Tiết Học Phần</h1>
            <%
                String maNganh = request.getParameter("manganh");
                NganhDAO nganhDAO = new NganhDAO();
                Nganh nganh = nganhDAO.getNganhByMaNganh(maNganh);

                if (nganh != null) {
                    KhoaDAO khoaDAO = new KhoaDAO();
                    Khoa khoa = khoaDAO.getKhoaByMaKhoa(nganh.getKhoa().getMaKhoa());
                    HocPhanDAO hocPhanDAO = new HocPhanDAO();
                    ArrayList<HocPhan> listHocPhan = hocPhanDAO.selectByMaNganh(maNganh);
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
            <a href="ChiTietNganh4.jsp?makhoa=<%=nganh.getKhoa().getMaKhoa()%>" class="btn btn-primary mt-3">
                <i class="bi bi-arrow-left-circle"></i> Trở về danh sách ngành
            </a>
         <%
			String status = request.getParameter("status");
			String message = request.getParameter("message");
			String alertClass = "";
			String alertMessage = "";

			if ("success".equals(status)) {
				alertClass = "alert alert-success";
				alertMessage = "Học phần đã được lưu thành công!";
			} else if ("success4".equals(status)) {
				alertClass = "alert alert-warning";
				alertMessage = "Đã trùng mã Học phần. Yêu cầu nhập lại!";
			} else if ("success2".equals(status)) {
				alertClass = "alert alert-info";
				alertMessage = "Học phần đã được sửa thành công!";
			} else if ("success3".equals(status)) {
				alertClass = "alert alert-secondary";
				alertMessage = "Học phần đã được xóa thành công!";
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
            <h2>Quản Lý Học Phần</h2>
            <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addHocPhanModal">Thêm Học Phần</button>

             <div class="table-responsive my-5">
        <table class="table table-bordered text-center" id="hocPhanTable">
            <thead class="table-light">
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Mã Học Phần</th>
                    <th scope="col">Tên Học Phần</th>
                    <th scope="col">Tín Chỉ</th>
                    <th scope="col">Học Kỳ</th>
                    <th scope="col">Bắt Buộc</th>
                    <th scope="col">Thao Tác</th>
                </tr>
                    </thead>
                    <tbody>
                        <%
                            int stt = 1;
                            for (HocPhan hocPhan : listHocPhan) {
                        %>
                        <tr>
                            <td><%=stt++%></td>
                            <td><%=hocPhan.getMaHocPhan()%></td>
                            <td><%=hocPhan.getTenHocPhan()%></td>
                            <td><%=hocPhan.getTinChi()%></td>
                            <td><%=hocPhan.getHocKy()%></td>
                            <td><%=hocPhan.getBatbuoc()%></td>
                            <td>
                                <form action="hocphan" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="delete"> 
                                    <input type="hidden" name="maHocPhan" value="<%=hocPhan.getMaHocPhan()%>">
                                    <input type="hidden" name="manganh" value="<%=nganh.getMaNganh()%>">
                                    <button type="submit" class="btn btn-danger btn-action">Xóa</button>
                                </form>
                                <button type="button" class="btn btn-warning btn-action" data-bs-toggle="modal" data-bs-target="#editHocPhanModal<%=hocPhan.getMaHocPhan().replace(" ", "_")%>">Sửa</button>

                                <!-- Modal sửa học phần -->
                                <div class="modal fade" id="editHocPhanModal<%=hocPhan.getMaHocPhan().replace(" ", "_")%>" tabindex="-1" aria-labelledby="editHocPhanModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editHocPhanModalLabel">Sửa Học Phần: <%=hocPhan.getMaHocPhan()%></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="hocphan" method="post">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="mahocphan" value="<%=hocPhan.getMaHocPhan()%>">
                                                    <input type="hidden" name="manganh" value="<%=nganh.getMaNganh()%>">
                                                    <div class="mb-3">
                                                        <label for="tenHocPhan" class="form-label">Tên Học Phần</label> 
                                                        <input type="text" class="form-control" id="tenhocphan" name="tenhocphan" value="<%=hocPhan.getTenHocPhan()%>" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="tinChi" class="form-label">Tín Chỉ</label> 
                                                        <input type="number" class="form-control" id="tinchi" name="tinchi" value="<%=hocPhan.getTinChi()%>" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="hocKy" class="form-label">Học Kỳ</label> 
                                                        <input type="text" class="form-control" id="hocKy" name="hocky" value="<%=hocPhan.getHocKy()%>" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="batBuoc" class="form-label">Bắt Buộc</label> 
                                                        <input type="text" class="form-control" id="batBuoc" name="batbuoc" value="<%=hocPhan.getBatbuoc()%>" required>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- Modal thêm học phần -->
            <div class="modal fade" id="addHocPhanModal" tabindex="-1" aria-labelledby="addHocPhanModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addHocPhanModalLabel">Thêm Học Phần</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="hocphan" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="manganh" value="<%=nganh.getMaNganh()%>">
                           
                                <div class="mb-3">
                                    <label for="tenHocPhan" class="form-label">Tên Học Phần</label>
                                    <input type="text" class="form-control" id="tenhocphan" name="tenhocphan" required>
                                </div>
                                <div class="mb-3">
                                    <label for="tinChi" class="form-label">Tín Chỉ</label>
                                    <input type="number" class="form-control" id="tinChi" name="tinchi" required>
                                </div>
                                <div class="mb-3">
                                    <label for="hocKy" class="form-label">Học Kỳ</label>
                                    <input type="text" class="form-control" id="hocKy" name="hocky" required>
                                </div>
                                <div class="mb-3">
                                    <label for="batBuoc" class="form-label">Bắt Buộc</label>
                                    <input type="text" class="form-control" id="batBuoc" name="batbuoc" required>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <%
                } 
            %>
              
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
