jQuery(document).ready(function($) {

    // initialization
    function insertSku() {
        $(".sku").each(function(){
            var skuHtml = "" +
                "    <a href=\"{url}\">" +
                "        <div class=\"sku-img\">" +
                "           <img src=\"{imgUrl}\">" +
                "        </div>\n" +
                "        <div class=\"sku-detail\">" +
                "           <div class=\"sku-title\">{title}</div>" +
                "           <div class=\"sku-subtitle\">{subTitle}</div>" +
                "           <div class=\"sku-price\">￥{price}</div>" +
                "        </div>" +
                "        <div class=\"sku-function\">" +
                "           <i class=\"fa fa-heart-o\" aria-hidden=\"true\"></i> {cnt}" +
                "        </div>" +
                "    </a>";

            var skuId = $(this).data("id");
            var infoDiv = $("#sku-info-" + skuId);
            var title = infoDiv.data("title");
            var subTitle = infoDiv.data("subtitle");
            var showId = infoDiv.data("showid");
            var url = infoDiv.data("url");
            var price = infoDiv.data("price");
            var imgUrl = "http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/5.png";
            skuHtml = skuHtml.replace("{id}", skuId);
            skuHtml = skuHtml.replace("{title}", title);
            skuHtml = skuHtml.replace("{subTitle}", subTitle);
            skuHtml = skuHtml.replace("{url}", "/s?sid=" + showId);
            skuHtml = skuHtml.replace("{price}", price);
            skuHtml = skuHtml.replace("{imgUrl}", imgUrl);
            skuHtml = skuHtml.replace("{cnt}", "12");
            $(this).append(skuHtml).removeClass("hide");
        });
    }

    insertSku();

    var breakpoints = new Array();
    var pointsCnt = 1;
    $('.list-item-user').each(function(i, elem) {
        breakpoints[pointsCnt] = elem.offsetTop;
        pointsCnt++;
    });

    var doc = $(document),
        win = $(window);
    win.scroll(function() {
        if (doc.scrollTop() > breakpoints[1] + 20) {
            for (var i = pointsCnt; i > 0; i--) {
                if (doc.scrollTop() > breakpoints[i] + 20) {
                    $('.list-item-user').removeClass('navbar-fixed-top').removeClass('scrolled');
                    $("#list-item-user-" + i).addClass('navbar-fixed-top').addClass('scrolled');
                    return;
                }
            }
        } else {
            $('.list-item-user').removeClass('navbar-fixed-top').removeClass('scrolled');
        }
    });
    win.scroll();

});
