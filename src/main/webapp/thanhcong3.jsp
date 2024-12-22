<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông báo thành công</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Chiều cao toàn màn hình */
            margin: 0;
            background-color: #f0f8ff; /* Màu nền nhạt */
            font-family: Arial, sans-serif; /* Phông chữ */
        }
        .container {
            text-align: center;
            background-color: #ffffff; /* Nền trắng */
            padding: 20px 40px;
            border-radius: 8px; /* Bo góc */
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
            transition: transform 0.3s; /* Hiệu ứng chuyển động */
        }
        .container:hover {
            transform: scale(1.02); /* Tăng kích thước khi hover */
        }
        .message {
            font-size: 24px; /* Kích thước chữ */
            color: #28a745; /* Màu chữ xanh lá */
            margin-bottom: 20px; /* Khoảng cách dưới */
        }
        .redirect-message {
            font-size: 16px; /* Kích thước chữ nhỏ hơn */
            color: #555; /* Màu chữ tối hơn */
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="message">Thao tác thành công!</div>
        <div class="redirect-message">Bạn sẽ được chuyển hướng về trang chính trong giây lát...</div>
    </div>
    <script>
        setTimeout(function(){
            window.location.href = 'ThoiKhoaBieuSV.jsp';
        }, 500); // Thời gian chuyển hướng là 3 giây
    </script>
</body>
</html>
