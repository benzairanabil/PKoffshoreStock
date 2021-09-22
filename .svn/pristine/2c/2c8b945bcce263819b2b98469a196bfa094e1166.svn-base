<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Controle Producion</title>
<link rel="stylesheet" href="./assets/style/bootstrap.min.css">
<link rel="stylesheet" href="./assets/style/style.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container text-center">
		<div class="row mt-sm-5">
			<div class="col-3"></div>
			<div class="col-6">
				<div class="card border-success mb-3" style="">
					<div class="card-header">Controle Production</div>
					<div class="card-body text-success">
						<button type="button" class="btn btn-outline-success"
							onclick="show_modal();">Reception</button>
					</div>
				</div>

			</div>
			<div class="col-3"></div>
		</div>
			<div class="row mt-sm-5">
			<div class="col-3"></div>
			<div class="col-6">
				<div class="card border-success mb-3" style="">
					<div class="card-header">Controle Production</div>
					<div class="card-body text-success">
						<button type="button" class="btn btn-outline-success"
							onclick="show_modal_renvoi_production();">Envoi</button>
					</div>
				</div>

			</div>
			<div class="col-3"></div>
		</div>

	</div>

	<!-- debut modal -->
	<div class="modal fade" id="confirmation_reception" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header alert alert-success">
					<h4 class="modal-title text-center" id="exampleModalLabel">Confirmation
						reception des cartons !!!</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-form-label">Code barre : </label> <input
							type="text" class="form-control code_barre" id="code_barre"
							autocomplete="off" required> <input type="hidden"
							id="rec_depot" value="5"> <br>
						<div id="msg">
							<strong id='bad' style='color: red'></strong> <strong id='good'
								style='color: green'> </strong>
						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary "
						onclick="active_application();" id="valider">Valider</button>
				</div>
			</div>
		</div>
	</div>
	<!-- debut modal envoi -->
	<div class="modal fade" id="renvoi_production" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header alert alert-danger">
					<h4 class="modal-title text-center" id="exampleModalLabel">Confirmation d'envoi des cartons !!!</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-form-label">Code barre : </label> <input
							type="text" class="form-control code_barre" id="code_barre_RP"
							autocomplete="off" required> <input type="hidden"
							id="rec_prod" value="6"> <br>
						<div id="msg_RP">
							<strong id='bad_RP' style='color: red'></strong> <strong
								id='good_RP' style='color: green'> </strong>
						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary "
						onclick="active_application_RP();" id="valider_RP">Valider</button>

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
	<script type="text/javascript" src="assets/scripts/script.js"></script>
	<!-- fin modal -->
</body>
</html>