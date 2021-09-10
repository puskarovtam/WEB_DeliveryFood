let pronadjeniRestorani;

function search() {
    let nameSearch = $('#nameIndexSearch').val();
    let typeSearch = $('#typeIndexSearch').val();
    let locationSearch = $('#locationIndexSearch').val();
    let minReviewSearch = $('#minReviewIndexSearch').val();
    let maxReviewSearch = $('#maxReviewIndexSearch').val();

    var searchIndex = new RestaurantSearchDTO(nameSearch, typeSearch, locationSearch, minReviewSearch, maxReviewSearch)

    $.ajax({
        type: 'post',
        url: 'rest/restaurant/search',
        contentType: 'application/json',
        data: JSON.stringify(searchIndex),
        success: function (pronadjeni) {
            pronadjeniRestorani = pronadjeni;
            document.getElementById('restaurantCardDiv').innerHTML = '';
            for (let restoran of pronadjeni) {
                restaurantCard(restoran);
            }
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

class RestaurantSearchDTO {
    constructor(name, type, city, minReview, maxReview) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.minReview = minReview;
        this.maxReview = maxReview;
    }
}
