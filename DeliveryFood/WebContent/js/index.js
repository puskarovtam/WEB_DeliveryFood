let allRestaurants;
let cardDiv;

function restaurantCard(restaurant) {

	cardDiv = document.getElementById('restaurantCardDiv');

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
	grad.innerHTML += restaurant.location.postalCode +" "+ restaurant.location.city;

	const detalji = document.createElement('a');
	detalji.className = "card-link btn btn-primary";
	detalji.innerHTML = 'Detalji';
	detalji.href = "";

	const komentari = document.createElement('a');
	komentari.className = "card-link btn btn-primary";
	komentari.innerHTML = 'Komentari';
	komentari.href = "";

	data.appendChild(naslov);
	data.appendChild(tip);
	data.appendChild(adresa);
	data.appendChild(grad);
	data.appendChild(detalji);
	data.appendChild(komentari);

	card.appendChild(data);
	cardDiv.appendChild(card);

}

$(document).ready(function () {
	$('#logovan').hide();

	$.ajax({
		type: 'get',
		url: 'rest/restaurant/allOpen',
		contentType: 'application/json',
		success: function (all) {
			allRestaurants = all;
			console.log(all);
			for (let restaurant of all) {
				restaurantCard(restaurant);
			}
		}
	});
});
