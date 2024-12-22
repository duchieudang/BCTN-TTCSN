<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.MonHoc"%>
<%@ page import="database.MonHocDAO"%>
<%@ page import="model.GiangVien"%>

<%@ page import="database.GiangVienDAO"%>
<%@ page import="database.LopDAO"%>
<%@ page import="model.Lop"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Môn Học Theo Giảng Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f7fa;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .content {
	margin-left: 270px;
}
        .table-bordered th, .table-bordered td {
            border: 1px solid #dee2e6;
        }
        .text-center {
            text-align: center;
        }
    </style>
</head>
<body>

   <jsp:include page="menu2.jsp" />
	<div class="content">
		<jsp:include page="header2.jsp" />
        <h1 class="text-center">Danh Sách Môn Học</h1>

        <%
        // Lấy giảng viên từ session
        GiangVien giangVien = (GiangVien) session.getAttribute("giangVien");
        MonHocDAO monHocDAO = new MonHocDAO();
       
        if (giangVien != null) {
        %>
        
            <h2 class="text-center">Giảng Viên: <%= giangVien.getTenGiangVien() %> (Mã: <%= giangVien.getMaGiangVien() %>)</h2>
            
            
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Mã Môn Học</th>
                        <th scope="col">Tên Môn Học</th>
                        <th scope="col">Tên Lớp</th> <!-- Thêm tên lớp -->
                        <th scope="col">Thao tác </th>
                       
                    </tr>
                </thead>
                <tbody>
                    <%
                    // Lấy danh sách môn học của giảng viên theo mã
                    ArrayList<MonHoc> listMonHoc = monHocDAO.getMonHocByGiangVien(giangVien.getMaGiangVien());
                    if (listMonHoc.isEmpty()) {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Không có môn học nào</td>
                    </tr>
                    <%
                    } else {
                        for (MonHoc monHoc : listMonHoc) {
                            // Tạo đối tượng LopDAO và lấy thông tin lớp học
                            LopDAO lopDAO = new LopDAO();
                            Lop l = lopDAO.getLopByMaLop(monHoc.getLop().getMaLop());
                    %>
                    <tr>
                        <td><%=monHoc.getMaMonHoc()%></td>
                        <td><%=monHoc.getTenMonHoc()%></td>
                        <td><%=l.getTenLop()+"-Khóa "+l.getKhoa()%></td> 
                        <td><a href="DanhSachLop2.jsp?mamonhoc=<%= monHoc.getMaMonHoc() %>"
                            class="btn btn-info btn-action">Xem Chi Tiết</a></td>
                         
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>

        <%
        } else {
        %>
            <div class="alert alert-warning text-center">Không tìm thấy thông tin giảng viên.</div>
        <%
        }
        %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
