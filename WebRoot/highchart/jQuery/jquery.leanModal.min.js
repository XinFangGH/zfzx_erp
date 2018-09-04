// leanModal v1.0 by Ray Stone - http://finelysliced.com.au
(function($) {
    $.fn.extend({
        leanModal: function(_1) {
            var _2 = {
                top: 100,
                overlay: 0.5
            };
            _1 = $.extend(_2, _1);
            return this.each(function() {
                var o = _1;
                $(this).click(function(e) {
                    var _3 = $("<div id='lean_overlay'></div>");
                    var _4 = $(this).attr("href");
                    $("body").append(_3);
					$(document).bind("contextmenu",function(e){   
          				return false;   
   					 });
                    $("#lean_overlay_close").click(function() {
                        _5(_4);
                    });             
                    $("#updatemoney").click(function() {
                        _5(_4);
                    });
                   
                    $("#lean_overlay_close_Income").click(function() {
                        _5(_4);
                    });
                    var _6 = $(_4).outerHeight();
                    var _7 = $(_4).outerWidth();
                    $("#lean_overlay").css({
                        "display": "block",
                        opacity: 0
                    });
                    $("#lean_overlay").fadeTo(200, o.overlay);
                    $(_4).css({
                        "display": "block",
                        "position": "fixed",
                        opacity: 0,
                        "z-index": 11000,
                        "left": 50 + "%",
                        "margin-left": -(_7 / 2) + "px",
                        "top":"120px"
                    });
                    $(_4).fadeTo(200, 1);
                    e.preventDefault();
                });
            });
            function _5(_8) {
                $("#lean_overlay").fadeOut(200);
                $(_8).css({
                    "display": "none"
                });
            };
            
        }
    });
})(jQuery);
// Downloads By http://down.liehuo.net
