$(document).ready(function() {
	$('#formLogin').submit(function(event) {
		event.preventDefault();

		//Dobavljanje podataka sa forme
		let username = $('#usernameLogin').val();
		let password = $('#passwordLogin').val();

		//Validacija polja
		if (!username && !password) {
			$('#error').text('Sva polja su obavezna');
			$('#error').css({ "color": "red", "font-size": "12px" });
			$('#error').show().delay(3000).fadeOut();
			return;
		}

		if (!username) {
			$('#emptyUsername').text('Username je obavezan');
			$('#emptyUsername').css({ "color": "red", "font-size": "12px" });
			$('#emptyUsername').show().delay(3000).fadeOut();
			return;
		}

		if (!password) {
			$('#emptyPassword').text('Password je obavezan');
			$('#emptyPassword').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptyPassword').show().delay(3000).fadeOut();
			return;
		}

		//Poziv backenda
		$.ajax({
			type: 'post',
			url: 'rest/user/login',
			contentType: 'application/json',
			data: JSON.stringify({
				"username": username,
				"password": password
			}),
			success: function() {
				$('#success').text('Uspešno logovanje');
				$('#success').css({ "color": "green", "font-size": "12px", "text-align": "center" });
				$('#success').show().delay(1000).fadeOut(function() {
					window.location = "./homepage.html";
				});
			}, error: function() {
				$('#error').text('Pogrešan username ili password');
				$('#error').css({ "color": "red", "font-size": "12px", "text-align": "center" });
				$('#error').show().delay(3000).fadeOut();
			}
		});
	});
});
