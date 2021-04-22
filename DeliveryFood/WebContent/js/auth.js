$(document).ready(function () {
	$('#formRegister').bootstrapValidator({
		message: 'Ovo polje nije validno.', fields:
		{
			usernameRegister: {
				message: 'Ovo polje nije validno.',
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					},
					stringLength: {
						min: 4,
						message: 'Username mora imati najmanje 4 slova.'
					}
				}
			},
			passwordRegister: {
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					},
					identical: {
						field: 'confirmRegister',
						message: 'Passwordi moraju da budu jednaki.'
					}
				}
			},
			confirmRegister: {
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					},
					identical: {
						field: 'passwordRegister',
						message: 'Passwordi moraju da budu jednaki.'
					}
				}
			},
			nameRegister: {
				message: 'Ovo polje nije validno.',
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					}
				}
			},
			surnameRegister: {
				message: 'Ovo polje nije validno.',
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					}
				}
			},
			genderRegister: {
				message: 'Ovo polje nije validno.',
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					}
				}
			}
		}
	});
});

$(document).ready(function () {
	$('#formLogin').bootstrapValidator({
		message: 'Ovo polje nije validno.',
		fields: {
			usernameLogin: {
				message: 'Ovo polje nije validno.',
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					}
				}
			},
			passwordLogin: {
				validators: {
					notEmpty: {
						message: 'Ovo polje je obavezno i ne može da bude prazno.'
					}
				}
			}
		}
	}).on('success.form.bv', function (e) {
		console.log("Ulazim u success.form.bv")
		e.preventDefault();
		logovanje();
	});
});

function logovanje() {
	let username = $('#usernameLogin').val();
	let password = $('#passwordLogin').val();
	console.log("Ulazim u logovanje");

	$.post({
		url: 'rest/auth/login',
		contentType: 'application/json',
		data: JSON.stringify({
			"username": username,
			"password": password
		}),
		success: function () {
			console.log("Usla sam u logovanje");
			window.location = './index.html';
			console.log("Idem na stranu index.html");
		},
		error: function () {
			console.log("Logovanje je neuspešno.");
		}
	});
	$('#formLogin').bootstrapValidator('defaultSubmit');
}