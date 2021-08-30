$(document).ready(function() {
	$('#logovan').show();
});

function logout(){
	$.get({
		url: 'rest/user/signOut',
		contentType: 'application/json',
		success: function(){
			window.location = './index.html';
		}
	});
}