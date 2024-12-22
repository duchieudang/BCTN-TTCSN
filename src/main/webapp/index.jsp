
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Electronic University Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
/* Custom CSS */
body {
	font-family: 'Open Sans', sans-serif;
}

.content {
	margin-left: 270px;
}

.info-card {
	background: #f8f9fa;
	border-radius: 12px;
	padding: 20px;
	text-align: center;
	margin-bottom: 20px;
}

.info-card h5 {
	font-size: 18px;
	margin-bottom: 10px;
}

.info-card h6 {
	font-size: 22px;
	font-weight: bold;
}

.card-icon {
	background-color: #007bff;
	width: 50px;
	height: 50px;
	color: #fff;
	border-radius: 50%;
	font-size: 24px;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<!-- Menu -->
	<jsp:include page="menu.jsp" />

	<!-- Main Content -->
	<div class="content">
		<jsp:include page="header.jsp" />
		<section class="section dashboard">
			<div class="container">
				<div class="row">
					<!-- Card số lượng khoa -->
					<div class="col-lg-3 col-md-6">
						<div class="info-card">
							<div
								class="card-icon d-flex align-items-center justify-content-center">
								<i class="bi bi-building"></i>
							</div>
							<h5 class="card-title">Khoa</h5>
							<h6>15</h6>
							<span class="text-muted small">Tổng số khoa</span>
						</div>
					</div>

					<!-- Card số lượng sinh viên -->
					<div class="col-lg-3 col-md-6">
						<div class="info-card">
							<div
								class="card-icon d-flex align-items-center justify-content-center">
								<i class="bi bi-person"></i>
							</div>
							<h5 class="card-title">Sinh viên</h5>
							<h6>1500</h6>
							<span class="text-muted small">Sinh viên đang theo học</span>
						</div>
					</div>

					<!-- Card số lượng khóa học -->
					<div class="col-lg-3 col-md-6">
						<div class="info-card">
							<div
								class="card-icon d-flex align-items-center justify-content-center">
								<i class="bi bi-book"></i>
							</div>
							<h5 class="card-title">Khóa học</h5>
							<h6>12</h6>
							<span class="text-muted small">Khóa học hiện tại</span>
						</div>
					</div>

					<!-- Card số lượng giảng viên -->
					<div class="col-lg-3 col-md-6">
						<div class="info-card">
							<div
								class="card-icon d-flex align-items-center justify-content-center">
								<i class="bi bi-person-badge"></i>
							</div>
							<h5 class="card-title">Giảng viên</h5>
							<h6>30</h6>
							<!-- Added a placeholder value for the number of teachers -->
							<span class="text-muted small">Giảng viên hiện có</span>
						</div>
					</div>
				</div>

				<!-- Biểu đồ báo cáo theo tháng -->
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">Báo cáo tuyển sinh theo tháng</h5>
								<canvas id="monthlyEnrollmentChart"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<!-- Bootstrap JS and Chart.js -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<!-- Biểu đồ Chart.js -->
	<script>
		var ctx = document.getElementById('monthlyEnrollmentChart').getContext(
				'2d');
		var monthlyEnrollmentChart = new Chart(ctx, {
			type : 'bar',
			data : {
				labels : [ 'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4',
						'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9',
						'Tháng 10', 'Tháng 11', 'Tháng 12' ],
				datasets : [ {
					label : 'Số lượng tuyển sinh',
					data : [ 100, 150, 120, 180, 220, 240, 300, 350, 400, 370,
							420, 450 ],
					backgroundColor : 'rgba(0, 123, 255, 0.5)',
					borderColor : 'rgba(0, 123, 255, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				scales : {
					y : {
						beginAtZero : true,
						title : {
							display : true,
							text : 'Số lượng tuyển sinh'
						}
					},
					x : {
						title : {
							display : true,
							text : 'Tháng'
						}
					}
				}
			}
		});
	</script>
</body>
</html>
