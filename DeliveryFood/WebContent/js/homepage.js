let cardDiv;
let restaurantID;


$(document).ready(function () {
	$('#noviRestoran').hide();
	$('#editRestaurant').hide();
	$('#usersTableDiv').hide();
	$('#userProfile').hide();
	$('#editUserProfile').hide();
	$('#orderCardDiv').hide();
	$('#logovan').show();

	getAllRestaurants();

	$("#userInput").on("keyup", function () {
		var value = $(this).val().toLowerCase();
		$("#usersTableBody tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

function logout() {
	$.get({
		url: 'rest/user/signOut',
		contentType: 'application/json',
		success: function () {
			window.location = './index.html';
		}
	});
}

function restaurantCard(restaurant) {

	const card = document.createElement('div');
	card.className = "card";
	card.style = "width: 18rem";

	//logo
	const image = document.createElement('img');
	image.className = "card-img-top";
	image.src = restaurant.logo;
	card.appendChild(image);

	//podaci
	const data = document.createElement('div');
	data.className = "card-body";

	const naslov = document.createElement('h5');
	naslov.className = "card-title";
	naslov.innerHTML = restaurant.name;

	const tip = document.createElement('h6');
	tip.className = "card-subtitle mb-2 text-muted";
	tip.innerHTML = restaurant.type;

	const adresa = document.createElement('p');
	adresa.className = "card-text";
	adresa.innerHTML = restaurant.location.address;

	const grad = document.createElement('p');
	grad.className = "card-text";
	grad.innerHTML += restaurant.location.postalCode + " " + restaurant.location.city;

	const detalji = document.createElement('a');
	detalji.className = "card-link btn btn-primary";
	detalji.innerHTML = 'Detalji';
	detalji.onclick = function () { detailsModal(restaurant); };

	const komentari = document.createElement('a');
	komentari.className = "card-link btn btn-primary";
	komentari.innerHTML = 'Komentari';
	komentari.onclick = function () { commentsModal(restaurant); };

	data.appendChild(naslov);
	data.appendChild(tip);
	data.appendChild(adresa);
	data.appendChild(grad);
	data.appendChild(detalji);
	data.appendChild(komentari);

	card.appendChild(data);
	cardDiv.appendChild(card);
}

function getAllRestaurants() {

	cardDiv = document.getElementById('restaurantCardDiv');
	$('#restaurantCardDiv').html('');
	$('#restaurantCardDiv').show();
	$('#searchRestaurantsDiv').show();

	$.ajax({
		type: 'get',
		url: 'rest/user/currentUser',
		contentType: 'application/json',
		success: function (user) {
			if (user.role === "ADMIN") {
				$('#orders').hide();
				$.ajax({
					type: 'get',
					url: 'rest/restaurant/all',
					contentType: 'application/json',
					success: function (all) {
						allRestaurants = all;
						for (let restaurant of all) {
							restaurantCard(restaurant);
						}
					}
				});
			} else if (user.role === "MANAGER") {
				$('#newRestaurant').hide();
				$('#newOrder').hide();
				let username = user.username;
				$.ajax({
					type: 'get',
					url: 'rest/restaurant/' + username,
					contentType: 'application/json',
					success: function (all) {
						allRestaurants = all;
						for (let restaurant of all) {
							restaurantCard(restaurant);
						}
					}
				});
			} else if (user.role === "DELIVERY MAN") {
				$('#newRestaurant').hide();
				$('#newOrder').hide();
				$.ajax({
					type: 'get',
					url: 'rest/restaurant/allOpen',
					contentType: 'application/json',
					success: function (all) {
						allRestaurants = all;
						for (let restaurant of all) {
							restaurantCard(restaurant);
						}
					}
				});
			} else if (user.role === "BUYER") {
				$('#newRestaurant').hide();
				$('#allUsers').hide();
				$.ajax({
					type: 'get',
					url: 'rest/restaurant/allOpen',
					contentType: 'application/json',
					success: function (all) {
						allRestaurants = all;
						for (let restaurant of all) {
							restaurantCard(restaurant);
						}
					}
				});
			}
		}
	});
}

function detailsModal(restaurant) {
	restaurantID = restaurant.id;
	$('#detailsRestaurantHeader').html(restaurant.name);
	$('#detailsRestaurantImage').html('<img class="img-rounded" src="' + restaurant.logo + '">');
	$('#detailsRestaurantType').html('<i>' + restaurant.type + '</i>');
	if (restaurant.status) {
		$('#detailsRestaurantStatus').html('<i>otvoren</i>');
	} else {
		$('#detailsRestaurantStatus').html('<i>zatvoren</i>');
	}
	$('#detailsRestaurantLocation').html(restaurant.location.address + '<br>' + restaurant.location.postalCode + ',' + restaurant.location.city);

	$.ajax({
		type: 'get',
		url: 'rest/user/currentUser',
		contentType: 'application/json',
		success: function (user) {
			if (user.role === "ADMIN") {
				const editButton = document.createElement('button');
				editButton.className = 'btn btn-success';
				editButton.innerHTML = 'Izmeni';
				editButton.onclick = function () { editRestaurant(restaurant) };
				$('#detailsRestaurantFooter').append(editButton);
				const deleteButton = document.createElement('button');
				deleteButton.className = 'btn btn-danger';
				deleteButton.innerHTML = 'Obriši';
				deleteButton.onclick = function () { deleteRestaurant(restaurantID) };
				$('#detailsRestaurantFooter').append(deleteButton);
			} else if (user.role === "MANAGER") {
				const addArticleButton = document.createElement('button');
				addArticleButton.className = 'btn btn-success';
				addArticleButton.innerHTML = 'Dodajte artikal';
				addArticleButton.onclick = function () { addArticle(restaurant) };
			} else if (user.role === "BUYER") {
				const orderButton = document.createElement('button');
				orderButton.className = 'btn btn-success';
				orderButton.innerHTML = 'Kreirajte porudžbinu';
				orderButton.onclick = function () { addOrder(restaurant) };
			}
		}
	});

	$('#detailsRestaurantModal').modal('show');
}

function editRestaurant(restaurant) {
	$('#detailsRestaurantModal').modal('hide');
	$('#noviRestoran').hide();
	$('#editRestaurant').show();
	$('#usersTableDiv').hide();
	$('#userProfile').hide();
	$('#editUserProfile').hide();
	$('#searchRestaurantsDiv').hide();
	cardDiv = document.getElementById('restaurantCardDiv');
	$('#restaurantCardDiv').html('');
	$('#restaurantCardDiv').hide();

	document.getElementById('nameEditRestaurant').value = restaurant.name;
	document.getElementById('typeEditRestaurant').value = restaurant.type;
	document.getElementById('addressEditRestaurant').value = restaurant.location.address;
	document.getElementById('postalCodeEditRestaurant').value = restaurant.location.postalCode;
	document.getElementById('cityEditRestaurant').value = restaurant.location.city;
	document.getElementById('imageResult').value = restaurant.logo;
}

function deleteRestaurant(restaurantID) {
	console.log("U delete restaurant funkciji sam sa id: " + restaurantID);
	$("#detailsRestaurantModal").modal("hide");
	$("#deleteRestaurantModal").modal("show");
}

function confirmDeleteRestaurant() {
	console.log("U potvrdi brisanja sam sa id: " + restaurantID);
	$.ajax({
		type: 'delete',
		url: 'rest/restaurant/delete/' + restaurantID,
		contentType: 'application/json',
		success: function () {
			$("#deleteRestaurantModal").modal("hide");
			window.location = "./homepage.html";
		}
	});
}

function commentsModal(restaurant) {
	$('#commentsHeader').html(restaurant.name + " komentari");


	$('#commentsIndexModal').modal('show');
}