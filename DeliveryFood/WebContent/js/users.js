function getAllUsers() {
	$('#restaurantCardDiv').hide();

	$('#usersTableDiv').show();
	$('#usersTableBody').html();

	$.ajax({
		type: 'get',
		url: 'rest/user/currentUser',
		contentType: 'application/json',
		success: function (user){
			console.log('Korisnik je: ' + user);
		}
	});
}