$(async() => {
    await getTableWithUsers();
})
const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=utf-8'
    },

    findAllUsers: async () => await fetch('api/admin/users'),
    addNewUser: async (user, addRoles) => await fetch(`api/admin/users/?selectedRoles=` + addRoles,
        {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, editRoles) => await fetch(`api/admin/users/?selectedRoles=` + editRoles,
        {method: 'PATCH', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (deleteId) => await fetch(`/api/admin/users/${deleteId}`,
        {method: 'DELETE', headers: userFetchService.head})
}

async function getTableWithUsers() {
    let table = $('#data tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())

        .then(users => {
            console.log(users.length)
            users.forEach(user => {
                let tableFilling = `$(
                         <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${getRoles(user)}</td>
                            <td> <a href="/api/admin/users/${user.id}" class="btn btn-primary editBtn" >Edit</a> </td>
                            <td> <a href="/api/admin/users/${user.id}" class="btn btn-danger deleteBtn ">Delete</a> </td> 
                            </td>
                            <td>
                            </td>
                            </tr>
                )`;
                table.append(tableFilling);
            })
        })
}

function getRoles(user) {
    let roleList = ""
    for (let i = 0; i < 1; i++) {
        roleList += (user.roles[0].name).substring(5);
    }
    return roleList;
}
document.onclick = function (event){
    //edit modal call
    if ($(event.target).hasClass('editBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.editForm #idEdit').val(user.id);
            $('.editForm #firstnameEdit').val(user.username);
            $('.editForm #lastnameEdit').val(user.lastName);
            $('.editForm #ageEdit').val(user.age);
            $('.editForm #emailEdit').val(user.email);
        });

        $('.editForm #editModal').modal();
    }
    //delete modal call

    if ($(event.target).hasClass('deleteBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.deleteForm #idDelete').val(user.id);
            $('.deleteForm #firstNameDelete').val(user.username);
            $('.deleteForm #lastnameDelete').val(user.lastName);
            $('.deleteForm #ageDelete').val(user.age);
            $('.deleteForm #emailDelete').val(user.email);
            $('.deleteForm #passwordDelete').val(user.password);
        });

        $('.deleteForm #deleteModal').modal();
    }
}
$( document ).ready( function () {
    //edit button
    $('.editSuccess').on('click', async function (e) {
        e.preventDefault();

        let user = {
            id: $('#idEdit').val(),
            username: $('#firstnameEdit').val(),
            lastName: $('#lastnameEdit').val(),
            age: $('#ageEdit').val(),
            email: $('#emailEdit').val(),
            password: $('#passwordEdit').val(),
        }

        let selectedRoles = $('#editRoles option:selected')
            .toArray().map(item => item.text);

        await userFetchService.updateUser(user, selectedRoles)
        $(".editForm #editClose").click()
        await getTableWithUsers()
    })
    //delete button

    $('.deleteSuccess').on('click', async function (e) {
        e.preventDefault();
        let deleteId = $('#idDelete').val()

        await userFetchService.deleteUser(deleteId)
        $(".deleteForm #deleteClose").click()
        await getTableWithUsers()

    })
    //add+field reload
    $('.addSuccess').on('click', async function (e) {

        let user = {
            username: $('#addFirstName').val(),
            lastName: $('#addLastName').val(),
            age: $('#addAge').val(),
            email: $('#addEmail').val(),
            password: $('#addPassword').val(),
        }

        let addRoles = $('#rolesAdd option:selected')
            .toArray().map(item => item.text);

        await userFetchService.addNewUser(user, addRoles)
        $('#addFirstName').val("")
        $('#addLastName').val(""),
            $('#addAge').val(""),
            $('#addEmail').val(""),
            $('#addPassword').val(""),
            await getTableWithUsers()
        $("#usersBtn").click()
    })
})

