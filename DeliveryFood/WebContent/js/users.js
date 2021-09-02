let userList = [];
let korisnici;
let user;
let nadimak;
let uloga;

function getAllUsers() {
    $('#searchRestaurantsDiv').hide();
    $('#restaurantCardDiv').hide();
    $('#newRestaurant').hide();
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
