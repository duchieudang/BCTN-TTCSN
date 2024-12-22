<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
    + request.getContextPath();
%>

<style>
/* Sidebar styles */
.sidebar {
  height: 100vh;
  background-color: #343a40; /* Dark background */
  padding: 20px;
  position: fixed;
  width: 250px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1); /* Added shadow for depth */
}

.sidebar h2 {
  color: #fff;
  font-size: 24px;
  margin-bottom: 30px;
  text-align: center; /* Center align the title */
}

.sidebar a {
  color: #fff;
  text-decoration: none;
  padding: 15px 20px; /* Increased padding for better touch area */
  display: block;
  border-radius: 5px;
  margin-bottom: 10px;
  transition: background-color 0.3s, transform 0.3s; /* Transition effects */
}

.sidebar a:hover {
  background-color: #007bff; /* Highlight on hover */
  transform: translateX(5px); /* Subtle movement effect on hover */
}

/* Active link styles */
.sidebar a.active {
  color: red; /* Change color to red */
}

/* Submenu styles */
.sidebar .submenu {
  display: none; /* Hidden by default */
  margin-left: 15px; /* Indentation for submenu */
}

.sidebar a.dropdown-toggle {
  position: relative; /* Position relative for the arrow */
}

.sidebar a.dropdown-toggle::after {
  content: '▼'; /* Downward arrow */
  position: absolute;
  right: 20px;
  font-size: 0.8em;
  color: #fff; /* Arrow color */
}

.sidebar a.dropdown-toggle.active + .submenu {
  display: block; /* Show submenu when active */
}
</style>

<script>
  // Function to set active link based on current URL
  document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.sidebar a');
    const currentUrl = window.location.href;

    links.forEach(link => {
      // Check if the link's href matches the current URL
      if (link.href === currentUrl) {
        link.classList.add('active'); // Add 'active' class to the current link
      }
      
      // Add click event listener to remove 'active' class from all links
      link.addEventListener('click', function () {
        links.forEach(item => item.classList.remove('active'));
        this.classList.add('active'); // Add 'active' class to the clicked link
      });
    });
  });
</script>

<div class="sidebar">
  <h2>Quản lý Đại học</h2>
  <a href="index.jsp">Trang Chủ</a>
  <a href="Khoa.jsp">Quản Lý Khoa</a>
  <a href="Nganh.jsp">Quản Lý Ngành</a>
  <a href="Lop.jsp">Quản Lý Lớp</a>
  <a href="SinhVien.jsp">Quản Lý Sinh Viên</a>
  <a href="HocPhan.jsp">Quản Lý Hoc Phần</a>
  <a href="MonHoc.jsp">Quản Lý Môn Học</a>
  <a href="LopHoc.jsp">Quản Lý Lớp Học</a>
  <a href="GiangVien.jsp">Quản Lý Giảng Viên</a>
</div>
