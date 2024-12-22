<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SinhVien" %>
<%@ page import="database.SinhVienDAO" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập Nhật Thông Tin Sinh Viên</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
        .sinhvien-section {
            margin-top: 60px;
        }
    </style>
</head>
<body>
    <jsp:include page="menu3.jsp" />
    <div class="content">
        <jsp:include page="header3.jsp" />
        <h2 class="text-center mb-4">Cập Nhật Thông Tin Sinh Viên</h2>

        <!-- Hiển thị thông báo thành công hoặc lỗi -->
        
        <%
            String maSinhVien = request.getParameter("masinhvien");
            SinhVienDAO sinhVienDAO = new SinhVienDAO();
            SinhVien sinhVien = sinhVienDAO.selectByMaSinhVien(maSinhVien); // Lấy thông tin sinh viên theo masinhvien
        %>

        <form action="sinhvien" method="post">
            <input type="hidden" name="action" value="capnhatthongtin">
            <input type="hidden" name="masinhvien" value="<%= sinhVien != null ? sinhVien.getMaSinhVien() : "" %>">
             <input type="hidden" name="malop" value="<%= sinhVien != null ? sinhVien.getLop().getMaLop() : "" %>">
            <div class="form-group">
                <label for="tenSinhVien">Tên Sinh Viên:</label>
                <input type="text" class="form-control" id="tenSinhVien" name="tenSinhVien" value="<%= sinhVien != null ? sinhVien.getTenSinhVien() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="gioiTinh">Giới Tính:</label>
                <select id="gioiTinh" name="gioiTinh" class="form-control" required>
                    <option value="Nam" <%= sinhVien != null && "Nam".equals(sinhVien.getGioiTinh()) ? "selected" : "" %>>Nam</option>
                    <option value="Nữ" <%= sinhVien != null && "Nữ".equals(sinhVien.getGioiTinh()) ? "selected" : "" %>>Nữ</option>
                    <option value="Khác" <%= sinhVien != null && "Khác".equals(sinhVien.getGioiTinh()) ? "selected" : "" %>>Khác</option>
                </select>
            </div>

            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <input type="text" class="form-control" id="diaChi" name="diaChi" value="<%= sinhVien != null ? sinhVien.getDiaChi() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="ngaySinh">Ngày Sinh:</label>
                <input type="date" class="form-control" id="ngaySinh" name="ngaySinh" value="<%= sinhVien != null ? sinhVien.getNgaySinh() != null ? sinhVien.getNgaySinh().toString() : "" : "" %>" required>
            </div>

            <div class="form-group">
                <label for="soDienThoai">Số Điện Thoại:</label>
                <input type="text" class="form-control" id="soDienThoai" name="soDienThoai" value="<%= sinhVien != null ? sinhVien.getSoDienThoai() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= sinhVien != null ? sinhVien.getEmail() : "" %>" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Cập Nhật Thông Tin</button>
            <a href="index.jsp" class="btn btn-secondary btn-block">Quay Lại</a>
        </form>
    </div>

    <!-- Bootstrap JS và jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
