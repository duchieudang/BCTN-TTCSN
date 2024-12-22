<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Lịch Thi</title>
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

        /* Hiệu ứng hover */
        .table tbody tr:hover {
            background-color: #e9ecef;
        }

        /* Validation cho input */
        input[type="text"], input[type="date"], input[type="time"] {
            width: 100%;
            box-sizing: border-box;
        }

        /* Kiểu nút */
        .btn-action {
            min-width: 80px;
        }
    </style>
</head>
<body>

    <jsp:include page="menu.jsp" />

    <div class="content">
        <!-- Bao gồm header -->
        <jsp:include page="header.jsp" />

        <div class="container my-5">
            <h2>Quản Lý Lịch Thi</h2>
            <button class="btn btn-success mb-3" onclick="addRow()">Thêm lịch thi</button>
            <div class="table-responsive">
                <table class="table table-bordered text-center" id="examScheduleTable">
                    <thead class="table-light">
                        <tr>
                            <th>STT</th>
                            <th>Mã lịch thi</th>
                            <th>Tên môn học</th>
                            <th>Tên lịch thi</th>
                            <th>Ngày thi</th>
                            <th>Giờ bắt đầu</th>
                            <th>Giờ kết thúc</th>
                            <th>Tòa nhà</th>
                            <th>Phòng</th>
                            <th>Vị trí thi</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        // Thêm một dòng mới vào bảng
        function addRow() {
            let table = document.getElementById("examScheduleTable").getElementsByTagName('tbody')[0];
            let newRow = table.insertRow();
            let cells = [];
            for (let i = 0; i <= 10; i++) {
                cells[i] = newRow.insertCell(i);
                if (i == 0) {
                    cells[i].innerHTML = table.rows.length; // STT
                } else if (i === 10) {
                    // Thêm các nút Lưu và Xóa vào cột Hành động
                    cells[i].innerHTML = `
                        <button class="btn btn-primary btn-action" onclick="saveRow(this)">Lưu</button>
                        <button class="btn btn-danger btn-action" onclick="deleteRow(this)">Xóa</button>`;
                } else if (i === 4) {
                    // Ngày thi
                    cells[i].innerHTML = `<input type="date" class="form-control">`;
                } else if (i === 5 || i === 6) {
                    // Giờ bắt đầu / Giờ kết thúc
                    cells[i].innerHTML = `<input type="time" class="form-control">`;
                } else {
                    // Tạo input cho các trường thông tin
                    cells[i].innerHTML = `<input type="text" class="form-control" placeholder="Nhập thông tin">`;
                }
            }
        }

        // Xóa một dòng khỏi bảng
        function deleteRow(button) {
            let row = button.parentNode.parentNode;
            row.parentNode.removeChild(row);
            updateRowNumbers();
        }

        // Lưu dòng sau khi sửa
        function saveRow(button) {
            let row = button.parentNode.parentNode;
            let cells = row.getElementsByTagName("td");

            // Lưu giá trị mới từ input vào ô
            for (let i = 1; i <= 9; i++) {
                let input = cells[i].getElementsByTagName("input")[0];
                if (input) {
                    cells[i].innerHTML = input.value;  // Lưu lại giá trị từ input vào ô
                }
            }

            // Thay đổi chức năng của nút trở lại thành "Sửa"
            button.innerText = "Sửa";
            button.className = "btn btn-primary btn-action"; // Set button color to blue
            button.onclick = function() { editRow(button); };
        }

        // Sửa một dòng trong bảng
        function editRow(button) {
            let row = button.parentNode.parentNode;
            let cells = row.getElementsByTagName("td");

            // Chuyển các ô trở lại trạng thái nhập liệu
            for (let i = 1; i <= 9; i++) {
                let cellValue = cells[i].innerHTML;
                if (i === 4) {
                    cells[i].innerHTML = `<input type="date" class="form-control" value="${cellValue}">`;
                } else if (i === 5 || i === 6) {
                    cells[i].innerHTML = `<input type="time" class="form-control" value="${cellValue}">`;
                } else {
                    cells[i].innerHTML = `<input type="text" class="form-control" value="${cellValue}">`;
                }
            }

            // Thay đổi nút "Sửa" thành "Lưu"
            button.innerText = "Lưu";
            button.className = "btn btn-warning btn-action"; // Set button color to orange for Save
            button.onclick = function() { saveRow(button); };
        }

        // Cập nhật lại số thứ tự các dòng
        function updateRowNumbers() {
            let table = document.getElementById("examScheduleTable");
            for (let i = 1; i < table.rows.length; i++) {
                table.rows[i].cells[0].innerHTML = i;
            }
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"></script>
</body>
</html>
