let ordersCardDiv;
let allOrders;
let currentOrders;
let korisnik;
let commenter;
let commenterUsername;
let uloga;
let restoran;

function getAllOrders() {
    $('#searchRestaurantsDiv').hide();
    $('#restaurantCardDiv').hide();
    $('#noviRestoran').hide();
    $('#editRestaurant').hide();
    $('#usersTableDiv').hide();
    $('#userProfile').hide();
    $('#editUserProfile').hide();
    $('#orderCardDiv').show();
    $('#logovan').show();

    $(document).ready(function () {
        ordersCardDiv = document.getElementById('orderCardDiv');
        $.get({
            url: 'rest/order/all',
            contentType: 'application/json',
            success: function (all) {
                $('#orderCardDiv').html(" ");
                allOrders = all;
                currentOrders = allOrders;
                console.log(all);

                for (let order of all) {
                    orderCard(order);
                }
            }
        });
    });
}

function orderCard(order) {
    $.get({
        url: 'rest/user/currentUser',
        contnentType: 'application/json',
        success: function (user) {
            korisnik = user;
            commenter = user.name + " " + user.surname;
            commenterUsername = user.username;
            uloga = user.role;
            $.get({
                url: 'rest/restaurant/one/' + order.restaurantId,
                contentType: 'application/json',
                success: function (one) {
                    restoran = one;

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

                    const naslov = document.createElement('h3');
                    naslov.className = "card-title";
                    naslov.innerHTML = restaurant.name;

                    const kupac = document.createElement('h5');
                    kupac.className = "card-subtitle mb-2";
                    kupac.innerHTML = order.buyerUsername;

                    const datum = document.createElement('p');
                    datum.className = "card-text";
                    datum.innerHTML = order.date;

                    const status = document.createElement('p');
                    status.className = "card-text";
                    status.innerHTML = order.status;

                    data.appendChild(naslov);
                    data.appendChild(kupac);
                    data.appendChild(datum);
                    data.appendChild(status);

                    const quitButton = document.createElement('button');
                    quitButton.className = 'btn btn-info';
                    quitButton.innerHTML = 'Odustani';
                    quitButton.onclick = function () { changeStatusReservation(order, 'odustanak'); };


                    const acceptButton = document.createElement('button');
                    acceptButton.className = 'btn btn-info';
                    acceptButton.innerHTML = 'Prihvati';
                    acceptButton.onclick = function () { changeStatusReservation(order, 'prihvacena'); };

                    const declineButton = document.createElement('button');
                    declineButton.className = 'btn btn-danger';
                    declineButton.innerHTML = 'Odbiti';
                    declineButton.onclick = function () { changeStatusReservation(order, 'odbijena'); };

                    const commentButton = document.createElement('button');
                    commentButton.className = 'btn btn-info';
                    commentButton.innerHTML = 'Ostavite komentar';
                    commentButton.onclick = function () { addComment(order, 'odbijena'); };

                    if (uloga == 'BUYER') {
                        if (order.status == "u toku" || reservation.status == "prihvacena") {
                            data.appendChild(quitButton);
                            ordersCardDiv.appendChild(card);
                        }
                        if (order.buyerUsername == korisnik.username) {
                            card.appendChild(data);
                            ordersCardDiv.appendChild(card);
                        }
                        if (order.status == "odbijena" || reservation.status == "dostavljena") {
                            data.appendChild(commentButton);
                            ordersCardDiv.appendChild(card);
                        }
                    }
                    if (uloga == 'MANAGER') {
                        if (reservation.status == "u toku") {
                            data.appendChild(acceptButton);
                            data.appendChild(declineButton);
                            ordersCardDiv.appendChild(card);
                        } else if (reservation.status == "prihvacena") {
                            data.appendChild(declineButton);
                            ordersCardDiv.appendChild(card);
                        }
                        if (restoran.manager == korisnik.username) {
                            card.appendChild(data);
                            ordersCardDiv.appendChild(card);
                        }
                    }
                }
            });
        }
    });
}