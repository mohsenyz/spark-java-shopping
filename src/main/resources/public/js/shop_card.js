(function (window) {

    function prepareCookies() {
        if (!Cookies.getJSON('data_sc')) {
            Cookies.set('data_sc', {});
        }
    }

    var shopCardObject = {

        exists : function (productId) {
            prepareCookies();
            return this.all()[productId] != undefined;
        },

        count : function () {
            var products = this.all();
            var count = 0;
            for (i = 0; i < products.length; i++) {
                var product = products[i];
                if (!product)
                    continue;
                count++;
            }
        },

        inc : function (productId) {
            prepareCookies();
            var products = this.all();
            if (!products[productId])
                products[productId] = {"id" : productId, "count" : 0};
            products[productId]["count"]++;
            this.set(products);
        },

        dec : function (productId) {
            prepareCookies();
            var products = this.all();
            if (!products[productId])
                products[productId] = {"id" : productId, "count" : 0};
            products[productId]["count"]--;
            if (products[productId]["count"] <= 0) {
                this.remove(productId);
            }
            this.set(products);
        },

        remove : function (productId) {
            prepareCookies();
            var products = this.all();
            delete products[productId];
            this.set(products);
        },

        get : function (productId) {
            var product = this.all()[productId];
            if (!product)
                return {"id" : -1, "count" : 0};
            return product;
        },

        set : function (productLikes) {
            Cookies.set('data_sc', productLikes);
        },

        all : function () {
            prepareCookies();
            return Cookies.getJSON('data_sc');
        }

    };
    window.ShopCard = shopCardObject;
})(window);