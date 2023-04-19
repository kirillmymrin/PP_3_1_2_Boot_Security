fetch("http://localhost:8189/api/currentUser")
    .then(res => res.json())
    .then(user => {
        document.getElementById('authorizeUserLastName').innerText = user.lastName;
        document.getElementById('authorizeUserEmail').innerText = user.email;
        document.getElementById('authorizeUserRole').innerText = user.roles[0].name;
    });