<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>One HaUI</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: url('img/dangnhap/background.jpg');
            background-size: cover;
            background-attachment: fixed;
            background-position: center;
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            height: 100vh;
        }

        .login-box {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 10px;
            padding: 40px 30px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .login-box img {
            width: 90px;
            height: 90px;
            margin-bottom: 20px;
        }

        .footer {
            margin-top: 20px;
            color: #333;
            font-size: 14px;
        }

        .header {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 10px;
        }

        .header h1 {
            margin: 0;
            font-size: 24px;
            text-transform: uppercase;
            font-weight: bold;
        }

        .header .text-gray-900 {
            color: #555;
            font-size: 18px;
            margin: 0;
            text-transform: uppercase;
            font-weight: bold;
        }

        /* Cải thiện giao diện nút đăng nhập */
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            padding: 12px 20px;
            font-size: 16px;
            font-weight: bold;
            text-transform: uppercase;
            border-radius: 25px;
            transition: background-color 0.3s ease-in-out, transform 0.2s ease-in-out;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }

        /* Thêm viền bo tròn cho trường nhập liệu */
        .form-control {
            border-radius: 25px;
            padding: 10px 15px;
            font-size: 15px;
        }

        .input-group-text {
            border-radius: 25px 0 0 25px;
            background-color: #e9ecef;
            color: #495057;
        }

        /* Giữ nguyên kích thước icon */
        .input-group-text i {
            font-size: 14px;
        }

        input[type="password"] {
            border-radius: 0 25px 25px 0;
        }

        label {
            display: none;
        }
    </style>
    <script>
        function setAction() {
            const username = document.getElementById('maadmin').value;
            const form = document.querySelector('form');

            if (username.includes('Admin')) {
                form.action = 'admin';
            } else if (username.includes('GV')) {
                form.action = 'giangvien';
            } else if (username.includes('SV')) {
                form.action = 'sinhvien';
            } else {
                form.action = 'admin'; 
            }
        }
    </script>
</head>
<body>

    <main class="container-fluid d-flex justify-content-center align-items-center h-100">
        <div class="login-box col-12 col-xl-4 mb-2">
            <img src="https://cdn-001.haui.edu.vn//img/logo-haui-size.png" alt="logo">
            <div class="header">
                <h1>ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI</h1>
                <div class="text-gray-900">ONE HAUI</div>
            </div>

            <form onsubmit="setAction()" method="POST" style="display: inline;">
                <input type="hidden" name="action" value="dangnhap" />
                <div class="form-group">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fas fa-user"></i> </span>
                        </div>
                        <input type="text" class="form-control" id="maadmin" placeholder="Đăng Nhập" name="username" autocomplete="off" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fas fa-lock"></i> </span>
                        </div>
                        <input type="password" class="form-control" id="matkhau" placeholder="Mật Khẩu" name="password" required>
                    </div>
                </div>
                <button class="btn btn-primary btn-block" type="submit">Đăng nhập</button>
                <div class="d-flex justify-content-between mb-3">
                    <a href="QuenMatKhauGV.jsp" id="forgot" class="link-primary">Quên mật khẩu?</a> 
                    <a href="#">Sử dụng đại học điện tử </a>
                </div>
            </form>

            <div class="footer">
                Copyright 2024 © <strong>HaUI</strong>
            </div>
        </div>
    </main>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
