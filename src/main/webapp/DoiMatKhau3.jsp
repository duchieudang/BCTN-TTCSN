<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SinhVien" %>
<%@ page import="database.SinhVienDAO" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi Mật Khẩu Sinh Viên</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- Liên kết tới Bootstrap CSS -->
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
    <jsp:include page="menu2.jsp" />
    <div class="content">
        <jsp:include page="header2.jsp" />
        
        <h2 class="text-center">Đổi Mật Khẩu Sinh Viên</h2>
        
        <%
        String maSinhVien = request.getParameter("masinhvien");
        SinhVienDAO sinhVienDAO = new SinhVienDAO();
        SinhVien sinhVien = sinhVienDAO.selectByMaSinhVien(maSinhVien);
        %>

        <form action="sinhvien" method="post" class="mt-4">
            <input type="hidden" name="action" value="doimatkhau">
            
            <div class="form-group">
                <label for="masinhvien">Mã Sinh Viên:</label>
                <input type="text" id="masinhvien" name="tendangnhap" class="form-control" value="<%= sinhVien.getMaSinhVien() %>" readonly>
            </div>

            <div class="form-group">
                <label for="oldPassword">Mật Khẩu Cũ:</label>
                <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="newPassword">Mật Khẩu Mới:</label>
                <input type="password" id="newPassword" name="newPassword" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Đổi Mật Khẩu</button>
        </form>

        <p class="mt-3 text-center"><a href="ThoiKhoaBieuSV.jsp">Trở về trang chủ</a></p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
