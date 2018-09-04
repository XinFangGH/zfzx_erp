Ext.namespace('Ext.ux.plugins');

Ext.ux.plugins.PageComboResizer = Ext.extend(Object, {

  pageSizes: [5, 10, 15, 20, 25, 30, 50, 75, 100, 200, 300, 500],
  prefixText: '每页显示 ',
  postfixText: '记录',

  constructor: function(config){
    Ext.apply(this, config);
    Ext.ux.plugins.PageComboResizer.superclass.constructor.call(this, config);
  },

  init : function(pagingToolbar) {
    var ps = this.pageSizes;
    var combo = new Ext.form.ComboBox({
      typeAhead: true,
      triggerAction: 'all',
      lazyRender:true,
      mode: 'local',
      width:45,
      store: ps,
      listeners: {
        select: function(c, r, i){
          pagingToolbar.pageSize = ps[i];
          var rowIndex = 0;
          var gp = pagingToolbar.findParentBy (
            function (ct, cmp) {return (ct instanceof Ext.grid.GridPanel) ? true : false;}
          );
          var sm = gp.getSelectionModel();
          if (undefined != sm && sm.hasSelection()) {
            if (sm instanceof Ext.grid.RowSelectionModel) {
              rowIndex = gp.store.indexOf( sm.getSelected() ) ;
            } else if (sm instanceof Ext.grid.CellSelectionModel) {
              rowIndex = sm.getSelectedCell()[0] ;
            }
          }
          rowIndex += pagingToolbar.cursor;
          pagingToolbar.doLoad(Math.floor(rowIndex/pagingToolbar.pageSize)*pagingToolbar.pageSize);
        }
      }
    });

    Ext.iterate(this.pageSizes, function(ps) {
      if (ps==pagingToolbar.pageSize) {
        combo.setValue (ps);
        return;
      }
    });

    var inputIndex = pagingToolbar.items.indexOf(pagingToolbar.refresh);
    pagingToolbar.insert(++inputIndex,'-');
    pagingToolbar.insert(++inputIndex, this.prefixText);
    pagingToolbar.insert(++inputIndex, combo);
    pagingToolbar.insert(++inputIndex, this.postfixText);
    pagingToolbar.on({
      beforedestroy: function(){
        combo.destroy();
      }
    });

  }
});