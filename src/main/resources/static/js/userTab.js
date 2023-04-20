$(async() => {
    await getTableWithUsers();
})
const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=utf-8'
    },

    findAllUsers: async () => await fetch('api/users')
}
async function getTableWithUsers() {
    let table = $('#data tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                         <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${getRoles(user)}</td>
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
