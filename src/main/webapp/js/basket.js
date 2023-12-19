function deleteToBasket(product) {
    fetch('/Basket?product=' + product.name_product, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            location.reload();
        })
        .catch(error => console.error('Ошибка при удалении товара из корзины:', error));
}
document.addEventListener('DOMContentLoaded', function() {
    fetch('/Basket')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            var productsList = document.getElementById('productList');
            var totalPrice = 0;

            data.forEach(function(product){
                var li = document.createElement('li');

                var productImg = document.createElement('img');
                productImg.src = product.photo_product;
                li.appendChild(productImg);

                var productInfo = document.createElement('div');
                productInfo.innerHTML = '<p>' + product.name_product + '</p>' +
                    '<p>Описание: ' + product.description + '</p>' +
                    '<p>Цена: ' + product.price + '</p>';
                li.appendChild(productInfo);


                if (!isNaN(product.price)) {
                    totalPrice += parseFloat(product.price);
                }

                var button = document.createElement('button');
                button.textContent = 'Удалить';
                button.addEventListener('click', function() {
                    deleteToBasket(product);
                });
                li.appendChild(button);
                productsList.appendChild(li);
            });


            var totalPriceElement = document.createElement('p');
            if (totalPrice > 0) {
                totalPriceElement.textContent = 'Итоговая цена: ' + totalPrice.toFixed(2);
            } else {
                totalPriceElement.textContent = 'Корзина пуста';
            }
            document.body.appendChild(totalPriceElement);
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
});