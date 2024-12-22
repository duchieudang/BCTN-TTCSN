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
            <h2>Quản Lý Lớp</h2>

            <div class="table-responsive">
                <table class="table table-bordered text-center" id="khoaTable">
                    <thead class="table-light">
                        <tr>
                            <th>STT</th>
                            <th>Mã Khoa</th>
                            <th>Tên Khoa</th>
                            <th>Thao tác</th>
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
                                <a href="ChiTietNganh2.jsp?makhoa=<%=khoa.getMaKhoa()%>" class="btn btn-info btn-action">Xem Chi Tiết</a>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
