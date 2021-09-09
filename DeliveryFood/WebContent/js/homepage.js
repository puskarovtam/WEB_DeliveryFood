$(document).ready(function() {
	$('#noviRestoran').hide();	
	$('#editRestaurant').hide();
	$('#usersTableDiv').hide();
	$('#userProfile').hide();
	$('#logovan').show();
	
	$("#userInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#usersTableBody tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

function logout() {
	$.get({
		url: 'rest/user/signOut',
		contentType: 'application/json',
		success: function() {
			window.location = './index.html';
		}
	});
}