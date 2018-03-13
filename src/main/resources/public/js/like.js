(function (window) {

    function prepareCookies() {
        if (!Cookies.getJSON('data_likes')) {
            Cookies.set('data_likes', {}, { expires: 365 });
        }
    }

    var likesObj = {

        hasLiked : function (productId) {
            prepareCookies();
            return this.all()[productId];
        },

        like : function (productId) {
            prepareCookies();
            var likes = this.all();
            likes[productId] = true;
            this.set(likes);
        },

        unlike : function (productId) {
            prepareCookies();
            var likes = this.all();
            delete likes[productId];
            this.set(likes);
        },

        set : function (productLikes) {
            Cookies.set('data_likes', productLikes, { expires: 365 });
        },

        all : function () {
            prepareCookies();
            return Cookies.getJSON('data_likes');
        }

    };
    window.Likes = likesObj;
})(window);