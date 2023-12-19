document.getElementById('userForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('emailInput').value;
    const address = document.getElementById('addressInput').value;

    // Отправляем данные на сервер
    fetch('/LkUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email, address: address })
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // Возвращаем текст ответа
            } else {
                throw new Error('Ошибка при сохранении данных');
            }
        })
        .then(message => {
            document.getElementById('messageDiv').innerText = message;
            document.getElementById('goToStoreBtn').style.display = 'block';
        })
        .catch(error => {
            document.getElementById('messageDiv').innerText = error.message;
        });
});

document.getElementById('goToStoreBtn').addEventListener('click', function() {
    window.location.href = '/statics/main-store.html';
});