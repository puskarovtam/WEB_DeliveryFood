$(document).ready(function() {
	$('#formRegister').submit(function(event) {
		event.preventDefault();

		//Dobavljanje podataka sa forme
		let username = $('#usernameRegister').val();
		let password = $('#passwordRegister').val();
		let confirm = $('#confirmRegister').val();
		let name = $('#nameRegister').val();
		let surname = $('#surnameRegister').val();
		let gender = $('#genderRegister').val();
		let role = 'BUYER';

		//Validacija polja
		if (!username && !password && !confirm && !name && !surname && !gender) {
			$('#error').text('Sva polja su obavezna.');
			$('#error').css({ "color": "red", "font-size": "12px" });
			$('#error').show().delay(3000).fadeOut();
			return;
		}

		if (!username) {
			$('#emptyUsername').text('Username je obavezan.');
			$('#emptyUsername').css({ "color": "red", "font-size": "12px" });
			$('#emptyUsername').show().delay(3000).fadeOut();
			return;
		}

		if (username.length < 5) {
			$('#lengthUsername').text('Username mora sadržati najmanje 5 karaktera.');
			$('#lengthUsername').css({ "color": "red", "font-size": "12px" });
			$('#lengthUsername').show().delay(3000).fadeOut();
			return;
		}

		if (!password) {
			$('#emptyPassword').text('Password je obavezan.');
			$('#emptyPassword').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptyPassword').show().delay(3000).fadeOut();
			return;
		}

		if (password.length < 6) {
			$('#lengthPassword').text('Password mora sadržati najmanje 6 karaktera.');
			$('#lengthPassword').css({ "color": "red", "font-size": "12px" });
			$('#lengthPassword').show().delay(3000).fadeOut();
			return;
		}

		if (!confirm) {
			$('#emptyConfirm').text('Ponovite Vaš password.');
			$('#emptyConfirm').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptyConfirm').show().delay(3000).fadeOut();
			return;
		}

		if (password != confirm) {
			$('#matchPassword').text('Password-i se ne slažu.');
			$('#matchPassword').css({ "color": "red", "font-size": "12px" });
			$('#matchPassword').show().delay(3000).fadeOut();
			return;
		}

		if (!name) {
			$('#emptyName').text('Ime je obavezno.');
			$('#emptyName').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptyName').show().delay(3000).fadeOut();
			return;
		}

		if (name.length < 3) {
			$('#length').text('Ime mora sadržati najmanje 3 karaktera.');
			$('#length').css({ "color": "red", "font-size": "12px" });
			$('#length').show().delay(3000).fadeOut();
			return;
		}

		if (!surname) {
			$('#emptySurname').text('Prezime je obavezno.');
			$('#emptySurname').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptySurname').show().delay(3000).fadeOut();
			return;
		}

		if (surname.length < 3) {
			$('#length').text('Prezime mora sadržati najmanje 3 karaktera.');
			$('#length').css({ "color": "red", "font-size": "12px" });
			$('#length').show().delay(3000).fadeOut();
			return;
		}

		if (!gender) {
			$('#emptyGender').text('Izaberite Vaš pol.');
			$('#emptyGender').css({ "color": "red", "font-size": "12px", "text-align": "center" });
			$('#emptyGender').show().delay(3000).fadeOut();
			return;
		}

		//Poziv backenda
		$.ajax({
			type: 'post',
			url: 'rest/user/register',
			contentType: 'application/json',
			data: JSON.stringify({
				"username": username,
				"password": password,
				"name": name,
				"surname": surname,
				"gender": gender,
				"role": role
			}),
			success: function(data) {
				console.log(JSON.stringify(data));
				$('#success').text('Uspešno ste se registrovali');
				$('#success').css({ "color": "green", "font-size": "12px", "text-align": "center" });
				$('#success').show().delay(1000).fadeOut(function() {
					window.location = "./login.html";
				});
			}, error: function() {
				$('#error').text('Username je zauzet.');
				$('#error').css({ "color": "red", "font-size": "12px", "text-align": "center" });
				$('#error').show().delay(3000).fadeOut();
			}
		});
	});
});
