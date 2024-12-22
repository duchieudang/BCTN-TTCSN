<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quên Mật Khẩu</title>
</head>
<body>
    <h2>Quên Mật Khẩu</h2>
    <form action="giangvien" method="post">
        <input type="hidden" name="action" value="quenmatkhau">
        <label>Email:</label>
        <input type="email" name="email" required>
        <button type="submit">Gửi Mật Khẩu Mới</button>
    </form>
  
</body>
</html>
