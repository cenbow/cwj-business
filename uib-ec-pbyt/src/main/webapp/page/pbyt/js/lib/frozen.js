! function(a) {
    function b(b, d) {
        var i = b[h],
            j = i && e[i];
        if (void 0 === d) return j || c(b);
        if (j) {
            if (d in j) return j[d];
            var k = g(d);
            if (k in j) return j[k] }
        return f.call(a(b), d) }

    function c(b, c, f) {
        var i = b[h] || (b[h] = ++a.uuid),
            j = e[i] || (e[i] = d(b));
        return void 0 !== c && (j[g(c)] = f), j }

    function d(b) {
        var c = {};
        return a.each(b.attributes || i, function(b, d) { 0 == d.name.indexOf("data-") && (c[g(d.name.replace("data-", ""))] = a.$.deserializeValue(d.value)) }), c }
    var e = {},
        f = a.fn.data,
        g = a.camelCase,
        h = a.expando = "JQuery" + +new Date,
        i = [];
    a.fn.data = function(d, e) {
        return void 0 === e ? a.isPlainObject(d) ? this.each(function(b, e) { a.each(d, function(a, b) { c(e, a, b) }) }) : 0 in this ? b(this[0], d) : void 0 : this.each(function() { c(this, d, e) }) }, a.fn.removeData = function(b) {
        return "string" == typeof b && (b = b.split(/\s+/)), this.each(function() {
            var c = this[h],
                d = c && e[c];
            d && a.each(b || d, function(a) { delete d[b ? g(this) : a] }) }) }, ["remove", "empty"].forEach(function(b) {
        var c = a.fn[b];
        a.fn[b] = function() {
            var a = this.find("*");
            return "remove" === b && (a = a.add(this)), a.removeData(), c.call(this) } }) }(window.$), ! function(a) {
    var b = {};
    b.cache = {}, a.tpl = function(a, c, d) {
        var e = /[^\w\-\.:]/.test(a) ? function(a, b) {
            var c, d = [],
                f = [];
            for (c in a) d.push(c), f.push(a[c]);
            return new Function(d, e.code).apply(b || a, f) } : b.cache[a] = b.cache[a] || this.get(document.getElementById(a).innerHTML);
        return e.code = e.code || "var $parts=[]; $parts.push('" + a.replace(/\\/g, "\\\\").replace(/[\r\t\n]/g, " ").split("<%").join("	").replace(/(^|%>)[^\t]*/g, function(a) {
            return a.replace(/'/g, "\\'") }).replace(/\t=(.*?)%>/g, "',$1,'").split("	").join("');").split("%>").join("$parts.push('") + "'); return $parts.join('');", c ? e(c, d) : e }, a.adaptObject = function(b, c, d, e, f, g) {
        var h = b;
        if ("string" != typeof d) {
            var i = a.extend({}, c, "object" == typeof d && d),
                j = !1;
            a.isArray(h) && h.length && "script" == a(h)[0].nodeName.toLowerCase() ? (h = a(a.tpl(h[0].innerHTML, i)).appendTo("body"), j = !0) : a.isArray(h) && h.length && "" == h.selector ? (h = a(a.tpl(h[0].outerHTML, i)).appendTo("body"), j = !0) : a.isArray(h) || (h = a(a.tpl(e, i)).appendTo("body"), j = !0) }
        return h.each(function() {
            var b = a(this),
                e = b.data("fz." + g);
            e || b.data("fz." + g, e = new f(this, a.extend({}, c, "object" == typeof d && d), j)), "string" == typeof d && e[d]() }) } }(window.$);
! function(a) {
    function b() {
        return !1 }

    function c(b) {
        return a.adaptObject(this, e, b, d, f, "dialog") }
    var d = '<div class="ui-dialog"><div class="ui-dialog-cnt"><div class="ui-dialog-bd"><div><h4 class="my-title"><%=title%></h4><div class="content"><%=content%></div></div></div><div class="ui-dialog-ft ui-btn-group"><% for (var i = 0; i < button.length; i++) { %><% if (i == select) { %><button type="button" data-role="button"  class="select" id="dialogButton<%=i%>"><%=button[i]%></button><% } else { %><button type="button" data-role="button" id="dialogButton<%=i%>"><%=button[i]%></div><% } %><% } %></div></div></div>',
        e = { title: "", content: "", button: ["确认"], select: 0, allowScroll: !1, callback: function() {} },
        f = function(b, c, d) { this.option = a.extend(e, c), this.element = a(b), this._isFromTpl = d, this.button = a(b).find('[data-role="button"]'), this._bindEvent(), this.toggle() };
    f.prototype = { _bindEvent: function() {
            var b = this;
            b.button.on("tap", function() {
                var c = a(b.button).index(a(this)),
                    d = a.Event("dialog:action");
                d.index = c, b.element.trigger(d), b.hide.apply(b) }) }, toggle: function() { this.element.hasClass("show") ? this.hide() : this.show() }, show: function() {
            var c = this;
            c.element.trigger(a.Event("dialog:show")), c.element.addClass("show"), this.option.allowScroll && c.element.on("touchmove", b) }, hide: function() {
            var c = this;
            c.element.trigger(a.Event("dialog:hide")), c.element.off("touchmove", b), c.element.removeClass("show"), c._isFromTpl && c.element.remove() } }, a.fn.dialog = a.dialog = c }(window.$);
