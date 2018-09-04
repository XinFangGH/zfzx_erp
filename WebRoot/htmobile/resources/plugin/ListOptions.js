Ext.define('UX.plugin.ListOptions', {
    //requires: ['Ext.Anim'],
    alias: 'plugin.listopt',
    config: {

        list: null,

        /**
        * An array of objects to be applied to the 'optionsTpl' to create the 
        * menu
        */
        items: [],

        /**
        * Selector to use to get individual List Options within the created Ext.Element
        * This is used when attaching event handlers to the menu options
        */
        optionSelector: 'option-button',

        /**
        * XTemplate to use to create the List Options view
        */
        optionsTpl: [
        	'<tpl for=".">',
        		'<div class="option-button {color}" action="{action}">{text}</div>',
        	'</tpl>'
        ].join(''),

        /**
        * Set to a function that takes in 2 arguments - your initial 'options' config option and the current 
        * item's Model instance
        * The function must return either the original 'options' variable or a revised one
        */
        itemFilter: null, //决定某个按钮是否显示的验证方法
        filter: null //决定是否可滑动显示整个menu的验证方法
    },

    constructor: function(config) {
        this.initConfig(config);
    },

    applyOptionsTpl: function(tpl){
        if(Ext.isString(tpl))
            return Ext.create('Ext.XTemplate', tpl);
        return tpl;
    },

    init: function(list) {
        var me = this;
        me.setList(list);

        list.addCls('x-list-options-plugin');

        var scroller = list.getScrollable().getScroller(),
            scrollerElement = scroller.getContainer();
        me.scrollerElement = scrollerElement;
        scroller.setDirectionLock(true);

        scrollerElement.onBefore({
            dragstart: 'onScrollerDragStart',
            scope: me
        });

        
        var wrapperCfg = {
            cls: 'item-options-wrapper',
            children: [{
                cls: 'item-options',
                html: me.getOptionsTpl().apply(me.getItems())
            }]
        };
        me.optionsWrapper1 = Ext.Element.create(wrapperCfg);
        me.optionsWrapper1.options = me.optionsWrapper1.child('.item-options');

        me.optionsWrapper2 = Ext.Element.create(wrapperCfg);
        me.optionsWrapper2.options = me.optionsWrapper2.child('.item-options');

        var swipeListener = {
            swipe: 'onOptionsSwipe',
            scope: me
        };
        me.optionsWrapper1.on(swipeListener);
        me.optionsWrapper2.on(swipeListener);

        var delegateListener = {
            delegate: '.' + this.getOptionSelector(),
            touchstart: 'onOptionTouchStart',
            touchend: 'onOptionTouchEnd',
            touchcancel: 'onOptionTouchEnd',
            tap: 'onOptionTap',
            scope: me
        };
        me.optionsWrapper1.options.on(delegateListener);
        me.optionsWrapper2.options.on(delegateListener);
    },
    destroy: function(){
        this.optionsWrapper1.destroy();
        this.optionsWrapper2.destroy();
        this.callParent(arguments);
    },
    onOptionsSwipe: function(e, target){
        if(e.direction == 'right'){
            var row = Ext.get(e.getTarget())._row;
            if(row)
                this.moveRow(row, 0)
        }
    },

    onScrollerDragStart: function(e, target) {
        if(e.absDeltaX > e.absDeltaY){
            this.onDragStart(e, target);
            return false;
        }
    },

    getAvailOptionsWrapper: function(row){
        if(row._isOptionsOpened && row._optionsWrapper) {
            return row._optionsWrapper;
        }
        else{
            if(this.optionsWrapper1._animating)
                return this.optionsWrapper2;
            return this.optionsWrapper1;
        }
    },

    onDragStart: function(e) {
        var me = this,
            list = me.getList(), 
            isList = list instanceof Ext.List,
            itemEl = e.getTarget(isList ? '.x-list-item' : '.x-dataview-item');
        if(!itemEl) return false;

        var list = me.getList(),
            row = isList ? Ext.getCmp(itemEl.id) : Ext.get(itemEl),
            record = isList ? row.getRecord() : list.getStore().getAt(list.getViewItems().indexOf(row.dom)),
            filter = me.getFilter();
        //是否可以划出菜单
        if (Ext.isFunction(filter) && !filter.call(me, list, record))
            return false;

        if((e.deltaX > 0 && !row._isOptionsOpened) || (e.deltaX < 0 && row._isOptionsOpened)) 
            return false;

        if(me.animateRow) me.moveRow(me.animateRow, 0);

        row._optionsWrapper = me.getAvailOptionsWrapper(row);

        //决定按钮的隐藏和显示
        var itemFilter = me.getItemFilter();
        if(Ext.isFunction(itemFilter)){
            row._optionsWrapper.select('.' + me.getOptionSelector()).each(function(btnEl){
                if(itemFilter.call(this, list, btnEl.getAttribute('action'), record)){
                    btnEl.show();
                }
                else{
                    btnEl.hide();
                }
            }, me);
        }

        row._optionsWrapper._row = row;
        if(!isList && !row.translatable){
            row.translatable = new Ext.util.Translatable();
            row.translatable.setElement(row);
        }
        me.scrollerElement.on({
            drag: 'onDrag',
            dragend: 'onDragEnd',
            scope: me
        });

        me.dragRow = row;
        me.dragRecord = record;

        var rowHeight = Math.round(isList ? row.element.getHeight() : row.getHeight()),
            positionY = 0; //optionsWrapper位置Y

        if (isList && list.getInfinite()) {
            row._startTranslateY = row.$position;

            positionY = row._startTranslateY;
        } 
        else {
            row._startTranslateY = 0;

            positionY = -rowHeight;
        }

        row._startOffsetX = (row._offsetX || 0);

        row._optionsWrapper.insertAfter(isList ? row.element : row);
        row._optionsWrapper.optionsWidth = row._optionsWrapper.options.getWidth();
        
        var offsetX = row._startOffsetX + e.deltaX;
        offsetX = Math.min(Math.max(-row._optionsWrapper.optionsWidth, offsetX), 0);
        row.translate(offsetX, row._startTranslateY);
        row._optionsWrapper.options.translate(offsetX, 0);
        row._optionsWrapper.options.setHeight(rowHeight);
        row._optionsWrapper.translate(0, positionY);

        row._offsetX = offsetX;

        //row.addCls(Ext.baseCSSPrefix + 'list-item-dragging');
    },

    onDrag: function(e) {
        var me = this,
            row = this.dragRow,
            list = me.getList(), 
            isList = list instanceof Ext.List,
            record = isList ? row.getRecord() : list.getStore().getAt(list.getViewItems().indexOf(row.dom));
        if (!record) return;

        if(Math.abs(row._offsetX) <= row._optionsWrapper.optionsWidth && row._offsetX <= 0){
            var offsetX = row._startOffsetX + e.deltaX;
            offsetX = Math.min(Math.max(-row._optionsWrapper.optionsWidth, offsetX), 0);
            row.translate(offsetX, row._startTranslateY);
            row._optionsWrapper.options.translate(offsetX, 0);

            row._offsetX = offsetX;
        }
    },
    onAnimationFrame: function(t, x, y){
        var isList = this.getList() instanceof Ext.List;

        var row = isList ? Ext.getCmp(t.getElement().getId()) : Ext.get(t.getElement().getId());
        row._optionsWrapper.options.translate(x, 0);
        row._offsetX = x;
    },
    onAnimationEnd: function(t, x, y){
        var isList = this.getList() instanceof Ext.List;

        var row = isList ? Ext.getCmp(t.getElement().getId()) : Ext.get(t.getElement().getId());
        if(this.animateRow === row)
            delete this.animateRow;

        //row.removeCls(Ext.baseCSSPrefix + 'list-item-dragging');
        row._offsetX = x;

        if(row._animateClosing){
            var wrapperDom = row._optionsWrapper.dom;
            if(wrapperDom.parentNode)
                wrapperDom.parentNode.removeChild(wrapperDom);
            delete row._optionsWrapper._row;
        }

        delete row._animateClosing;
        delete row._animateOpening;
        delete row._optionsWrapper._animating;

        var translatable = isList ? row.getTranslatable() : row.translatable;
        translatable.un({
            animationframe: 'onAnimationFrame',
            animationend: 'onAnimationEnd', 
            scope: this
        });

        if(x != 0){ //opened
            this.addViewportListener(row);
        }
    },

    onDragEnd: function(e) {
        var me = this,
            row = this.dragRow,
            list = this.getList();

        me.scrollerElement.un({
            drag: 'onDrag',
            dragend: 'onDragEnd',
            scope: me
        });

        delete this.dragRow;
        this.animateRow = row;

        var velocity = Math.abs(e.flick.velocity.x), 
            direction = e.deltaX > 0 ? "right" : "left", 
            offsetX = Math.abs(row._offsetX), 
            threshold = 0, width = 0;

        if (offsetX > 0) {
            width = row._optionsWrapper.optionsWidth;
            threshold = parseInt(width * .3);

            switch (direction) {
                case "right":
                    offsetX = (velocity > .3 || (width - offsetX) > threshold) ? 0 : -width;
                    break;

                case "left":
                    offsetX = (velocity > .3 || offsetX > threshold) ? -width : 0;
                    break;
            }
        }

        me.moveRow(row, offsetX);
    },
    moveRow: function(row, offsetX, duration) {

        if(offsetX == 0){
            row._animateClosing = true;
        }
        else{
            row._animateOpening = true;
        }
        row._optionsWrapper._animating = true;

        row._isOptionsOpened = offsetX != 0;
        var list = this.getList(),
          isList = list instanceof Ext.List;
        if(isList)
            row.element[offsetX == 0 ? 'removeCls' : 'addCls']("opened");
        else{
            row[offsetX == 0 ? 'removeCls' : 'addCls']("opened");
            row.translatable.x = row._offsetX;
        }

        var translatable = isList ? row.getTranslatable() : row.translatable;
        translatable.on({
            animationframe: 'onAnimationFrame',
            animationend: 'onAnimationEnd', 
            scope: this
        });

        translatable.translateAnimated(offsetX, row._startTranslateY, {
            duration: duration || 150,
            easing: "ease-out"
        });
    },

    addViewportListener: function(row){
        Ext.Viewport.element.on({
            touchstart: 'viewportTouchStart',
            tap: 'viewportTap',
            scope: this,
            args: [row]
        });
    },

    removeViewportListener: function(row){
        Ext.Viewport.element.un({
            touchstart: 'viewportTouchStart',
            tap: 'viewportTap',
            scope: this,
            args: [row]
        });
    },

    //某个dom元素是不是属于某个控件
    isDomInsideCmp: function(dom, cmp){
        var c = cmp instanceof Ext.Element ? cmp.dom : cmp.element.dom;
        if(dom === c) return true;
        var bb = dom;
        while(bb !== c) { 
            if(bb === document.body) break;
            else bb = bb.parentNode;
        }
        if(bb === c) return true;
        return false;
    },
    viewportTouchStart: function(row, e, target) {
        if(row.isDestroyed){
            this.removeViewportListener(row);
            return;
        }

        if (!this.isDomInsideCmp(target, row) && !Ext.fly(e.target).hasCls(this.getOptionSelector())){

            this.moveRow(row, 0);

            Ext.Viewport.element.un({
                touchstart: 'viewportTouchStart',
                scope: this,
                args: [row]
            });
        }
    },

    viewportTap: function(row, e, target) {
        if(row.isDestroyed){
            this.removeViewportListener(row);
            return;
        }
        //!row._animateClosing && !(!row._isOptionsOpened && this.isDomInsideCmp(target, row))
        if (e.getTarget((this.getList() instanceof Ext.List) ? '.x-list-item' : '.x-dataview-item') && !Ext.fly(e.target).hasCls(this.getOptionSelector())){

            this.moveRow(row, 0);

            this.removeViewportListener(row);

            e.stopPropagation();
        }
        else{
            this.removeViewportListener(row);
        }
    },

    /**
    * Handler for 'touchstart' event to add the Pressed class
    * @param {Object} e
    * @param {Object} el
    */
    onOptionTouchStart: function(e, el) {
        this.pressedTimeout = Ext.defer(this.addPressedClass, 100, this, [e]);
    },

    /**
    * Handler for the 'tap' event of the individual List Option menu items
    * @param {Object} e
    */
    onOptionTouchEnd: function(e, el) {
        if (this.pressedTimeout) {
            clearTimeout(this.pressedTimeout);
            delete this.pressedTimeout;
        }
        this.removePressedClass(e);
    },

    onOptionTap: function(e, el){
        var me = this,
            t = e.getTarget(),
            action = t.getAttribute('action'),
            wrapper = Ext.fly(t).up('.item-options-wrapper');
        me.moveRow(wrapper._row, 0);


        var list = me.getList(), 
            isList = list instanceof Ext.List,
            record = isList ? wrapper._row.getRecord() : list.getStore().getAt(list.getViewItems().indexOf(wrapper._row.dom));
        me.getList().fireEvent('listoptiontap', action, list, record);
    },

    /**
    * Adds the Pressed class on the Menu Option
    * @param {Object} e
    */
    addPressedClass: function(e) {
        Ext.fly(e.getTarget()).addCls('pressed');
    },

    /**
    * Removes the Pressed class on the Menu Option
    * @param {Object} e
    */
    removePressedClass: function(e) {
        Ext.fly(e.getTarget()).removeCls('pressed');
    }
});
