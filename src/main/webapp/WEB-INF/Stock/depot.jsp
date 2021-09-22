<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Scan Depot</title>
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
					<div class="card-header">Reception Depot</div>
					<div class="card-body text-success">
						<button type="button" class="btn btn-outline-success"
							onclick="show_modal();">Confirmer la reception des
							cartons</button>
					</div>
				</div>

			</div>
			<div class="col-3"></div>

		</div>
		<div class="row mt-sm-5">
			<div class="col-3"></div>
			<div class="col-6">

				<div class="card border-danger mb-3" style="">
					<div class="card-header">Envoi vers production</div>
					<div class="card-body text-danger">
						<button type="button" class="btn btn-outline-danger"
							onclick="show_modal_renvoi_production();"
							data-mdb-ripple-color="dark">Confirmer le renvoi des
							cartons vers la production</button>
					</div>
				</div>

			</div>
			<div class="col-3"></div>
		</div>
		<!-- 2 -->
		<div class="row mt-sm-5">
			<div class="col-3"></div>
			<div class="col-6">
				<div class="card border-primary mb-3" style="">
					<div class="card-header">En destruction</div>
					<div class="card-body text-primary">
						<button type="button" class="btn btn-outline-info"
							onclick="show_modal_renvoi_distruction();"
							data-mdb-ripple-color="dark">confirmer la reception
							depuis la production vers la distruction</button>
					</div>
				</div>
			</div>
			<div class="col-3"></div>
		</div>
		<!-- fin row -->
	</div>
	<!-- fin container -->
	<!-- debut modal Confirmation	reception des cartons -->
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
							id="rec_depot" value="1"> <br>
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
	<!-- fin modal Confirmation	reception des cartons -->
	<!-- debut modal Confirmation	le renvoi vers production -->
	<div class="modal fade" id="renvoi_production" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header alert alert-danger">
					<h4 class="modal-title text-center" id="exampleModalLabel">Scan
						Cartons Envoyer Vers La Production !!!</h4>
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
							id="rec_prod" value="2"> <br>
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

	<!-- fin modal Confirmation	le renvoi vers production -->
	<!-- debut modal reception depot pour la distruction -->

	<div class="modal fade" id="renvoi_distruction" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header alert alert-info">
					<h4 class="modal-title text-center" id="exampleModalLabel">Scan
						Cartons Envoyer Vers La Distruction !!!</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-form-label">Code barre initial </label> <input
							type="text" class="form-control code_barre" id="code_barre_RD"
							autocomplete="off" required> 
							<label class="col-form-label">Code barre Archivage </label> <input
							type="text" class="form-control code_barre" id="code_barre_AR"
							autocomplete="off" required>
							<input type="hidden"
							id="rec_dist" value="99"> <br>
						<div id="msg_RD">
							<strong id='bad_RD' style='color: red'></strong> <strong
								id='good_RD' style='color: green'> </strong>
						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary "
						onclick="active_application_RD();" id="valider_RD">Valider</button>

				</div>
			</div>
		</div>
	</div>

	<div class="fixed-bottom">
		<jsp:include page="footer.jsp" />
	</div>
	<script type="text/javascript" src="assets/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="assets/scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/scripts/script.js"></script>
</body>
</html>