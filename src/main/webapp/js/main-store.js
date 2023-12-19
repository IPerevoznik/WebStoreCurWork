<!--main-store.js -->

function checkAuthorization() {
    fetch('/MainStore')
        .then(response => {
            if (response.ok) {
                location.href = '/statics/basket-page.html';
            } else {
                location.href = '/statics/auth-page.html';
            }
        })
        .catch(error => console.error('Ошибка при проверке авторизации:', error));
}

function filterProducts() {
    var input, filter, ul, li, txtValue, i;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    ul = document.getElementById('productList');
    li = ul.getElementsByClassName('product-container');
    for (i = 0; i < li.length; i++) {
        txtValue = li[i].textContent || li[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "flex";
        } else {
            li[i].style.display = "none";
        }
    }
}

function addToBasket(product) {
    fetch('/MainStoreListHandler?productName=' + product.name_product, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            console.log('Product added to basket:', product.name_product);
            location.reload();
        })
        .catch(error => console.error('Ошибка при добавлении товара в корзину:', error));
}

document.addEventListener('DOMContentLoaded', function() {
    fetch('/MainStoreListHandler')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            var productsList = document.getElementById('productList');
            var tagsContainer = document.getElementById('tagsContainer');
            var allTags = [];
            data.forEach(function(product){
                var productContainer = document.createElement('div');
                productContainer.classList.add('product-container');

                var productImg = document.createElement('img');
                productImg.src = product.photo_product;
                productImg.alt = product.name_product;
                productContainer.appendChild(productImg);

                var productDetails = document.createElement('div');
                productDetails.innerHTML = '<strong>' + product.name_product + '</strong><br>' + product.description + '<br> Цена: ' + product.price+'<br>Тег:'+product.tagProduct[0];
                productContainer.appendChild(productDetails);

                var productLink = document.createElement('a');
                productLink.href = '/statics/product-page.html?name=' + encodeURIComponent(product.name_product);
                productLink.textContent = '  подробнее'; // Текст ссылки
                productDetails.appendChild(productLink);


                var button = document.createElement('button');
                button.textContent = 'Добавить в корзину';
                button.addEventListener('click', function() {
                    addToBasket(product);
                });
                productContainer.appendChild(button);
                productsList.appendChild(productContainer);
                allTags = allTags.concat(product.tagProduct[0]);
            });
            var uniqueTags = [...new Set(allTags)];
            uniqueTags.forEach(function(tag) {
                var tagButton = document.createElement('button');
                tagButton.textContent = tag;
                tagButton.addEventListener('click', function() {
                    document.getElementById('searchInput').value = tag;
                    filterProducts();
                });
                tagsContainer.appendChild(tagButton);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
});
