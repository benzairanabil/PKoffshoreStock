// reception depot
$('#code_barre').keydown(function(event) {
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	if (keyCode == 13) {
		$('#valider').trigger('click');
	}
});
$('#qr').keydown(function(event) {
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	if (keyCode == 13) {
		$("#formauth").submit();
	}
});


$('.modal').on('shown.bs.modal', function() {
	$('input:text:visible:first', this).focus();
	$(".form-control").prop('required', true);
});
function show_modal() {
	$("#code_barre").val("");
	$("#code_barre").focus();
	$('#confirmation_reception').modal('show');
	$("#good").text("");
	$("#bad").text("");
}
function show_modal_steps(numero) {
	$('#confirmation_reception').modal('show');
	$.get('ConsultationCarton?action=steps' + '&numero=' + numero, function(data) {
	}).done(function(data) {
		$("#exampleModalLabel").text("Carton Tracker : " + numero);
		$("ol").empty();
		$.each(data, function(i, items) {
		var date = new Date(items.dateHisto);
		var res = (date.getDate() +'/'+(date.getMonth() + 1) +'/'+date.getFullYear()+'  ' +date.getHours()+':'+date.getMinutes());
			$("ol").append('<li  class="list-group-item completed">'+ 'Le : '+res +' '+items.locale+' scanner par '+items.userLogin+ '<span></span> </li>');
			
		});


	})
}


function active_application() {

	$.get('ChangePositionCarton?code=' + $("#code_barre").val() + '&status=' + $("#rec_depot").val(), function(data) {
	}).done(function(data) {
		console.log(data);
		$("#code_barre").val("");
		$("#code_barre").focus();
		if (data.status == false) {
			$("#bad").css("display", "block")
			$("#good").css("display", "none")
			$("#good").text("");
			$("#bad").text(data.msg);
		}
		else {
			$("#good").css("display", "block")
			$("#bad").css("display", "none")
			$("#bad").text("");
			$("#good").text(data.msg);
		}
	})
}

//Renvoi Production

$('#code_barre_RP').keydown(function(event) {
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	if (keyCode == 13) {
		$('#valider_RP').trigger('click');
	}
});

function show_modal_renvoi_production() {
	$("#code_barre").val("");
	$('#renvoi_production').modal('show');
	$("#good_RP").text("");
	$("#bad_RP").text("");
}

function active_application_RP() {
	$.get('ChangePositionCarton?code=' + $("#code_barre_RP").val() + '&status=' + $("#rec_prod").val(), function(data) {
	}).done(function(data) {
		console.log(data);
		$("#code_barre_RP").val("");
		$("#code_barre_RP").focus();
		if (data.status == false) {
			$("#bad_RP").css("display", "block")
			$("#good_RP").css("display", "none")
			$("#good_RP").text("");
			$("#bad_RP").text(data.msg);
		}
		else {
			$("#good_RP").css("display", "block")
			$("#bad_RP").css("display", "none")
			$("#bad_RP").text("");
			$("#good_RP").text(data.msg);
		}
	})
}

// reception pour la distruction
$('#code_barre_RD').keydown(function(event) {
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	if (keyCode == 13) {
		$('#code_barre_AR').trigger('focus');
	}
});

$('#code_barre_AR').keydown(function(event) {
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	if (keyCode == 13) {
		$('#valider_RD').trigger('click');
	}
});
function show_modal_renvoi_distruction() {
	$('#renvoi_distruction').modal('show');
	$("#code_barre").val("");
	$("#code_barre_AR").val("");
	$("#good_RD").text("");
	$("#bad_RD").text("");
}

function active_application_RD() {
	$.get('ChangePositionCarton?code=' + $("#code_barre_RD").val() + '&status=' + $("#rec_dist").val() + '&code_archive=' + $("#code_barre_AR").val(), function(data) {
	}).done(function(data) {
		console.log(data);
		$("#code_barre_RD").val("");
		$("#code_barre_RD").focus();
		$("#code_barre_AR").val("");
		if (data.status == false) {
			$("#bad_RD").css("display", "block")
			$("#good_RD").css("display", "none")
			$("#good_RD").text("");
			$("#bad_RD").text(data.msg);
		}
		else {
			$("#good_RD").css("display", "block")
			$("#bad_RD").css("display", "none")
			$("#bad_RD").text("");
			$("#good_RD").text(data.msg);
		}
	})
}
