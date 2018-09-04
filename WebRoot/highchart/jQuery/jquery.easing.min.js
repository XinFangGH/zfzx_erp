jQuery.extend(jQuery.easing, { 
	backout: function(x, t, b, c, d) {
        var s = 1.70158;
        return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b
    },
    linear: function(x, t, b, c, d) {
        return c * t / d + b
    }
});