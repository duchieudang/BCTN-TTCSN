<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Admin" %>
<%@ page import="database.AdminDAO" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập Nhật Thông Tin Admin</title>
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
      <jsp:include page="menu.jsp" />
    <div class="content">
     <jsp:include page="header.jsp" />
        <h2 class="text-center mb-4">Cập Nhật Thông Tin Cá Nhân</h2>

        <!-- Hiển thị thông báo thành công hoặc lỗi -->
        
        <%
            String maAdmin = request.getParameter("maadmin");
            AdminDAO adminDAO = new AdminDAO();
            Admin admin = adminDAO.selectByMaAdmin(maAdmin); // Lấy thông tin admin theo maadmin
        %>

        <form action="admin" method="post">
            <input type="hidden" name="action" value="capnhatthongtin">
            <input type="hidden" name="maadmin" value="<%= admin != null ? admin.getMaAdmin() : "" %>">
            <input type="hidden" name="matkhau" value="<%= admin != null ? admin.getMatKhau() : "" %>">
           
            <div class="form-group">
                <label for="hoVaTen">Họ và Tên:</label>
                <input type="text" class="form-control" id="hoVaTen" name="hoVaTen" value="<%= admin != null ? admin.getHoVaTen() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="gioiTinh">Giới Tính:</label>
                <select id="gioiTinh" name="gioiTinh" class="form-control" required>
                    <option value="Nam" <%= admin != null && "Nam".equals(admin.getGioiTinh()) ? "selected" : "" %>>Nam</option>
                    <option value="Nữ" <%= admin != null && "Nữ".equals(admin.getGioiTinh()) ? "selected" : "" %>>Nữ</option>
                    <option value="Khác" <%= admin != null && "Khác".equals(admin.getGioiTinh()) ? "selected" : "" %>>Khác</option>
                </select>
            </div>

            <div class="form-group">
                <label for="diaChi">Địa Chỉ:</label>
                <input type="text" class="form-control" id="diaChi" name="diaChi" value="<%= admin != null ? admin.getDiaChi() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="ngaySinh">Ngày Sinh:</label>
                <input type="date" class="form-control" id="ngaySinh" name="ngaySinh" value="<%= admin != null ? admin.getNgaySinh() != null ? admin.getNgaySinh().toString() : "" : "" %>" required>
            </div>

            <div class="form-group">
                <label for="soDienThoai">Số Điện Thoại:</label>
                <input type="text" class="form-control" id="soDienThoai" name="soDienThoai" value="<%= admin != null ? admin.getSoDienThoai() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= admin != null ? admin.getEmail() : "" %>" required>
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
