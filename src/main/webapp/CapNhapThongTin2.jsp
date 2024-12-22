<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.GiangVien" %>
<%@ page import="database.GiangVienDAO" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập Nhật Thông Tin Giảng Viên</title>
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
        .giangvien-section {
            margin-top: 60px;
        }
    </style>
</head>
<body>
    <jsp:include page="menu2.jsp" />
    <div class="content">
        <jsp:include page="header2.jsp" />
        <h2 class="text-center mb-4">Cập Nhật Thông Tin Cá Nhân Giảng Viên</h2>

        <% 
            String maGiangVien = request.getParameter("magiangvien");
            GiangVienDAO giangVienDAO = new GiangVienDAO();
            GiangVien giangVien = giangVienDAO.selectByMaGiangVien(maGiangVien); // Lấy thông tin giảng viên theo magiangvien
        %>

        <form action="giangvien" method="post">
            <input type="hidden" name="action" value="capnhatthongtin">
            <input type="hidden" name="magiangvien" value="<%= giangVien != null ? giangVien.getMaGiangVien() : "" %>">
            <input type="hidden" name="matkhau" value="<%= giangVien != null ? giangVien.getMatKhau() : "" %>">
           
            <div class="form-group">
                <label for="tenGiangVien">Họ và Tên:</label>
                <input type="text" class="form-control" id="tenGiangVien" name="tenGiangVien" value="<%= giangVien != null ? giangVien.getTenGiangVien() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="gioiTinh">Giới Tính:</label>
                <select id="gioiTinh" name="gioiTinh" class="form-control" required>
                    <option value="Nam" <%= giangVien != null && "Nam".equals(giangVien.getGioiTinh()) ? "selected" : "" %>>Nam</option>
                    <option value="Nữ" <%= giangVien != null && "Nữ".equals(giangVien.getGioiTinh()) ? "selected" : "" %>>Nữ</option>
                    <option value="Khác" <%= giangVien != null && "Khác".equals(giangVien.getGioiTinh()) ? "selected" : "" %>>Khác</option>
                </select>
            </div>

            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <input type="text" class="form-control" id="diaChi" name="diaChi" value="<%= giangVien != null ? giangVien.getDiaChi() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="ngaySinh">Ngày Sinh:</label>
                <input type="date" class="form-control" id="ngaySinh" name="ngaySinh" value="<%= giangVien != null ? giangVien.getNgaySinh() != null ? giangVien.getNgaySinh().toString() : "" : "" %>" required>
            </div>

            <div class="form-group">
                <label for="soDienThoai">Số Điện Thoại:</label>
                <input type="text" class="form-control" id="soDienThoai" name="soDienThoai" value="<%= giangVien != null ? giangVien.getSoDienThoai() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= giangVien != null ? giangVien.getEmail() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="chuyenNganh">Chuyên Ngành:</label>
                <input type="text" class="form-control" id="chuyenNganh" name="chuyenNganh" value="<%= giangVien != null ? giangVien.getChuyenNganh() : "" %>" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Cập Nhật Thông Tin</button>
            <a href="ThoiKhoaBieuGV.jsp" class="btn btn-secondary btn-block">Quay Lại</a>
        </form>
    </div>

    <!-- Bootstrap JS và jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
