<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Authentification</title>
<link rel="stylesheet" href="./assets/style/bootstrap.min.css">
</head>
<body>

	<div class="container " style="margin-top:100px">
		<div class="row justify-content-md-center">
			<div class="col-5 text-center">
			<div class="card border-warning mb-3" style="">
					<div class="card-header border-warning"><img style="height: 100%; width: 70%;" src="./assets/images/logo.svg"	alt="logo"></div>
					<div class="card-body ">
					<div class="text-center"><h2 >Authentification</h2></div>   
						<img style="height: 45%; width: 45%; margin-bottom:10px;" src="./assets/images/qr.png">
						<form method="post" id="formauth">
						<input style="margin-bottom:10px;"	type="password" class="form-control border-warning qr" id="qr"	name="qrcodepass" autofocus required>
						<div ><strong style="color: red;" id="erreur"> ${error} </strong> </div>
						
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="fixed-bottom">
		<jsp:include page="footer.jsp" />
	</div>
	<!-- jQuery library -->
	<script type="text/javascript" src="assets/scripts/jquery.min.js"></script>
	<!-- Popper JS -->
	<script type="text/javascript" src="assets/scripts/popper.min.js"></script>
	<!-- Latest compiled JavaScript -->
	<script type="text/javascript" src="assets/scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/scripts/script_consult.js"></script>
	<script type="text/javascript" src="assets/scripts/script.js"></script>
</body>
</html>