let userList = [];
let korisnici;
let user;
let nadimak;
let uloga;

function getAllUsers() {
    $('#noviRestoran').hide();
    $('#searchRestaurantsDiv').hide();
    $('#restaurantCardDiv').hide();
    $('#editRestaurant').hide();

    $('#usersTableDiv').show();
    $('#usersTableBody').html();

    $.ajax({
        type: 'get',
        url: 'rest/user/currentUser',
        contentType: 'application/json',
        success: function (user) {
            console.log('Korisnik je: ' + user.username);
            nadimak = user.username;
            uloga = user.role;
            $.ajax({
                type: 'get',
                url: 'rest/user/findAll',
                contentType: 'application/json',
                success: function (korisnici) {
                    if (uloga == "ADMIN") {
                        userList = korisnici;
                        let number = 0;
                        for (let u of userList) {

                            number = number + 1;
                            const tr = document.createElement('tr');
                            tr.className = 'rowHeight';
                            const tdId = document.createElement('td');
                            tdId.scope = 'row';
                            tdId.className = 'rowHeight';
                            tdId.innerHTML = number;
                            tr.append(tdId);

                            const tdUsername = document.createElement('td');
                            tdUsername.className = 'rowHeight';
                            tdUsername.innerHTML = u.username;
                            tr.append(tdUsername);

                            const tdName = document.createElement('td');
                            tdName.className = 'rowHeight';
                            tdName.innerHTML = u.name;
                            tr.append(tdName);

                            const tdSurname = document.createElement('td');
                            tdSurname.className = 'rowHeight';
                            tdSurname.innerHTML = u.surname;
                            tr.append(tdSurname);

                            const tdBirthday = document.createElement('td');
                            tdBirthday.className = 'rowHeight';
                            tdBirthday.innerHTML = u.dateOfBirth;
                            tr.append(tdBirthday);

                            const tdGender = document.createElement('td');
                            tdGender.className = 'rowHeight';
                            tdGender.innerHTML = u.gender;
                            tr.append(tdGender);

                            const tdRole = document.createElement('td');
                            tdRole.className = 'rowHeight';
                            tdRole.innerHTML = u.role;
                            tr.append(tdRole);

                            const tbody = $('#usersTableBody');
                            tbody.append(tr);
                        }
                    }
                }
            });
        }
    });
}

function myProfile() {
    $.ajax({
        type: 'get',
        url: 'rest/user/currentUser',
        contentType: 'application/json',
        success: function (user) {
            $('#searchRestaurantsDiv').hide();
            $('#restaurantCardDiv').hide();
            $('#noviRestoran').hide();
            $('#editRestaurant').hide();
            $('#usersTableDiv').hide();
            $('#userProfile').show();
            $('#editUserProfile').hide();
            $('#profileNameSurname').html(user.name + ' ' + user.surname);
            $('#profileUsername').html(user.username);
            $('#profileDate').html(user.dateOfBirth);
            $('#profileGender').html(user.gender);
            $('#profileRole').html(user.role);
        }
    });
}

function editProfile() {
    $.ajax({
        type: 'get',
        url: 'rest/user/currentUser',
        contentType: 'application/json',
        success: function (user) {
            console.log(" Korisnik je: " + user.username);
            $('#searchRestaurantsDiv').hide();
            $('#restaurantCardDiv').hide();
            $('#noviRestoran').hide();
            $('#editRestaurant').hide();
            $('#usersTableDiv').hide();
            $('#userProfile').hide();
            $('#editUserProfile').show();

            let usernameEdit = user.username;
            let nameEdit = user.name;
            let surnameEdit = user.surname;
            let passwordEdit = user.password;
            let dateEdit = user.dateOfBirth;
            let genderEdit = user.gender;

            document.getElementById('editProfileName').value = nameEdit;
            document.getElementById('editProfileSurname').value = surnameEdit;
            document.getElementById('editProfileUsername').value = usernameEdit;
            document.getElementById('editProfilePassword').value = passwordEdit;
            document.getElementById('editProfileDate').value = dateEdit;
            document.getElementById('editProfileGender').value = genderEdit;
        }
    });
}

function saveEditChange() {
    $('#formEditUser').submit(function (event) {
        event.preventDefault();

        let username = $('#editProfileUsername').val();
        let ime = $('#editProfileName').val();
        let prezime = $('#editProfileSurname').val();
        let password = $('#editProfilePassword').val();
        let datum = $('#editProfileDate').val();
        let pol = $('#editProfileGender').val();

        if (!ime && !prezime && !password) {
            $('#error').text('Sva polja su obavezna.');
            $('#error').css({ "color": "red", "font-size": "12px" });
            $('#error').show().delay(3000).fadeOut();
            return;
        }

        if (!ime) {
            $('#emptyEditName').text('Ime je obavezno.');
            $('#emptyEditName').css({ "color": "red", "font-size": "12px", "text-align": "center" });
            $('#emptyEditName').show().delay(3000).fadeOut();
            return;
        }

        if (!prezime) {
            $('#emptyEditSurname').text('Prezime je obavezno.');
            $('#emptyEditSurname').css({ "color": "red", "font-size": "12px", "text-align": "center" });
            $('#emptyEditSurname').show().delay(3000).fadeOut();
            return;
        }

        if (!password) {
            $('#emptyEditPassword').text('Password je obavezan.');
            $('#emptyEditPassword').css({ "color": "red", "font-size": "12px", "text-align": "center" });
            $('#emptyEditPassword').show().delay(3000).fadeOut();
            return;
        }

        if (password.length < 5) {
            $('#lengthEditPassword').text('Password mora sadržati najmanje 5 karaktera.');
            $('#lengthEditPassword').css({ "color": "red", "font-size": "12px" });
            $('#lengthEditPassword').show().delay(3000).fadeOut();
            return;
        }

        $.ajax({
            type: 'put',
            url: 'rest/user/edit/' + username + '/' + ime + '/' + prezime + '/' + password + '/' + datum + '/' + pol,
            contentType: 'application/json',
            success: function () {
                $('#success').text('Uspešno ste izmenili podatke.');
                $('#success').css({ "color": "green", "font-size": "12px", "text-align": "center" });
                $('#success').show().delay(1000).fadeOut(function () {
                    window.location = "./homepage.html";
                });
            }, error: function () {
                $('#error').text('Došlo je do greške prilikom izmene podataka.');
                $('#error').css({ "color": "red", "font-size": "12px", "text-align": "center" });
                $('#error').show().delay(3000).fadeOut();
            }
        });

    });

}
