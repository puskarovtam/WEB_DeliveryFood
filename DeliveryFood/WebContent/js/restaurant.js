function addNewRestaurant() {
    $.ajax({
        type: 'get',
        url: 'rest/user/currentUser',
        contentType: 'application/json',
        success: function (user) {
            logovan = user;
            $('#searchRestaurantsDiv').hide();
            $('#restaurantCardDiv').hide();
            $('#noviRestoran').show();
            $('#editRestaurant').hide();
            $('#usersTableDiv').hide();
        }
    });
}

function saveNewApartment() {
    let name = $('#nameNewRestaurant').val();
    let type = $('#typeNewRestaurant').val();
    let address = $('#addressNewRestaurant').val();
    let postalCode = $('#postalCodeNewRestaurant').val();
    let city = $('#cityNewRestaurant').val();
    //let logo = ;

    if (!name) {
        $('#emptyName').text('Naziv restorana je obavezan.');
        $('#emptyName').css({ "color": "red", "font-size": "12px" });
        $('#emptyName').show().delay(3000).fadeOut();
        return;
    }

    if (!type) {
        $('#emptyType').text('Tip restorana je obavezan.');
        $('#emptyType').css({ "color": "red", "font-size": "12px" });
        $('#emptyType').show().delay(3000).fadeOut();
        return;
    }

    if (!address) {
        $('#emptyLocation').text('Adresa restorana je obavezna.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    if (!postalCode) {
        $('#emptyLocation').text('Poštanski broj restorana je obavezan.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    if (!city) {
        $('#emptyLocation').text('Grad je obavezan.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    let lokacija = new Location(address, city, postalCode, latitude, longitude);
    let restoran = new Restaurant(name, type, lokacija, logo, articles, manager);

    $.ajax({
        type: 'post',
        url: 'rest/restaurant/add',
        contentType: 'application/json',
        data: JSON.stringify(restoran),
        success: function () {
            $('#success').text('Uspešno ste dodali novi restoran.');
            $('#success').css({ "color": "green", "font-size": "12px", "text-align": "center" });
            $('#success').show().delay(1000).fadeOut(function () {
                window.location = "./homepage.html";
            });
        }, error: function () {
            $('#error').text('Greška pri dodavanju novog restorana.');
            $('#error').css({ "color": "red", "font-size": "12px", "text-align": "center" });
            $('#error').show().delay(3000).fadeOut();
        }
    });
}

function updateRestaurant() {
    let idEdit = restaurantEdit.id;
    let nameEdit = $('#nameEditRestaurant').val();
    let typeEdit = $('#typeEditRestaurant').val();
    let addressEdit = $('#addressEditRestaurant').val();
    let cityEdit = $('#cityEditRestaurant').val();
    let postalCodeEdit = $('#postalCodeEditRestaurant').val();

    if (!nameEdit) {
        $('#emptyName').text('Naziv restorana je obavezan.');
        $('#emptyName').css({ "color": "red", "font-size": "12px" });
        $('#emptyName').show().delay(3000).fadeOut();
        return;
    }

    if (!typeEdit) {
        $('#emptyType').text('Tip restorana je obavezan.');
        $('#emptyType').css({ "color": "red", "font-size": "12px" });
        $('#emptyType').show().delay(3000).fadeOut();
        return;
    }

    if (!addressEdit) {
        $('#emptyLocation').text('Adresa restorana je obavezna.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    if (!postalCodeEdit) {
        $('#emptyLocation').text('Poštanski broj restorana je obavezan.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    if (!cityEdit) {
        $('#emptyLocation').text('Grad je obavezan.');
        $('#emptyLocation').css({ "color": "red", "font-size": "12px" });
        $('#emptyLocation').show().delay(3000).fadeOut();
        return;
    }

    let lokacijaEdit = new Location(addressEdit, cityEdit, postalCodeEdit, latitudeEdit, longitudeEdit);
    let restoranEdit = new Restaurant(nameEdit, typeEdit, lokacijaEdit, logoEdit, articlesEdit, managerEdit);

    $.ajax({
        type: 'put',
        url: 'rest/restaurant/edit/' + idEdit,
        contentType: 'application/json',
        data: JSON.stringify(restoranEdit),
        success: function () {
            $('#success').text('Uspešno ste izmenili restoran.');
            $('#success').css({ "color": "green", "font-size": "12px", "text-align": "center" });
            $('#success').show().delay(1000).fadeOut(function () {
                window.location = "./homepage.html";
            });
        }, error: function () {
            $('#error').text('Greška pri izmeni restorana.');
            $('#error').css({ "color": "red", "font-size": "12px", "text-align": "center" });
            $('#error').show().delay(3000).fadeOut();
        }
    });
}

function deleteRestaurant(restaurantID) {
    $("#deleteRestaurantModal").modal("show");
}

function confirmDeleteRestaurant() {
    let restID = restaurantID;
    $.ajax({
        type: 'delete',
        url: 'rest/restaurant/delete/' + restID,
        contentType: 'application/json',
        success: function () {
            $("#deleteRestaurantModal").modal("hide");
            window.location = "./homepage.html";
        }
    });
}

function detailsRestaurant(){
    
}

/*  ==========================================
    SHOW UPLOADED IMAGE
* ========================================== */
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imageResult')
                .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $('#upload').on('change', function () {
        readURL(input);
    });
});

/*  ==========================================
    SHOW UPLOADED IMAGE NAME
* ========================================== */
var input = document.getElementById('upload');
var infoArea = document.getElementById('upload-label');

input.addEventListener('change', showFileName);
function showFileName(event) {
    var input = event.srcElement;
    var fileName = input.files[0].name;
    infoArea.textContent = 'File name: ' + fileName;
}



class Restaurant {
    constructor(name, type, location, logo, articles, manager) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.logo = logo;
        this.articles = articles;
        this.manager = manager;
    }
}

class Location {
    constructor(address, city, postalCode, latitude, longitude) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}