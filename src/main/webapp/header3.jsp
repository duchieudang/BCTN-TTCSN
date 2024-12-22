<%@page import="model.SinhVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
    /* Header styles */
    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 20px 20px;
        background-color: #ffffff;
        border-bottom: 2px solid #e3e3e3;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.0);
    }

    .header .logo {
        display: flex;
        align-items: center;
    }

    .header .logo img {
        height: 40px;
        margin-right: 10px;
    }

    .header .logo span {
        font-size: 24px; /* Increased font size */
        font-weight: bold;
        color: #007bff; /* Bootstrap primary color */
        text-shadow: 1px 1px 2px rgba(0, 123, 255, 0.2); /* Subtle text shadow */
    }

    .header .icons {
        display: flex;
        align-items: center;
    }

    .header .icons i {
        font-size: 26px; /* Increased icon size */
        margin: 0 10px;
        position: relative;
        color: #495057; /* Darker color for icons */
        transition: color 0.3s; /* Transition for hover effect */
    }

    .header .icons i:hover {
        color: #007bff; /* Change color on hover */
    }

    .header .icons .badge {
        position: absolute;
        top: -5px;
        right: -10px;
        background-color: #dc3545; /* Bootstrap danger color for badge */
        color: white;
        border-radius: 50%;
        font-size: 12px;
        padding: 2px 6px;
    }

    .header .user-profile {
        display: flex;
        align-items: center;
        margin-left: 20px;
        cursor: pointer;
        position: relative;
    }

    .header .user-profile img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        margin-right: 10px;
        border: 2px solid #007bff; /* Blue border around profile picture */
        transition: border-color 0.3s; /* Transition for hover effect */
    }

    .header .user-profile img:hover {
        border-color: #0056b3; /* Darker blue on hover */
    }

    .header .user-profile span {
        font-size: 18px; /* Increased font size */
        font-weight: 600;
        color: #333;
    }
</style>

<header class="header">
    <div class="logo">
        <img src="img/dangnhap/logo.png" alt="University Logo">
        <span>Student</span>
    </div>

    <div class="user-profile dropdown">
        <div class="icons">
            <i class="bi bi-bell" aria-label="Notifications">
                <span class="badge">4</span>
            </i>
            <i class="bi bi-chat-left-text" aria-label="Messages">
                <span class="badge">3</span>
            </i>
        </div>
<%
    SinhVien sinhvien = (SinhVien) session.getAttribute("sinhVien"); 
if (sinhvien == null) {
    request.getRequestDispatcher("login.jsp").forward(request, response); // Redirect to login.jsp
    return; // Stop execution of the current page
} else {
%>
        <img src="img/dangnhap/logo.png" alt="User Profile" data-bs-toggle="dropdown" aria-expanded="false">
        <span><%= sinhvien.getTenSinhVien() %></span> 

        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li><a class="dropdown-item" href="CapNhapThongTin3.jsp?masinhvien=<%=sinhvien.getMaSinhVien()%>">Thay đổi thông tin</a></li>
            <li><a class="dropdown-item" href="DoiMatKhau3.jsp?masinhvien=<%=sinhvien.getMaSinhVien()%>">Đổi mật khẩu</a></li>
            <li><a class="dropdown-item" href="login.jsp">Đăng xuất</a></li>
        </ul>

<%}
%>
    </div>
</header>
