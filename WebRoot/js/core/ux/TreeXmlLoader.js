Ext.ns("zhiwei.ux");
/**
 * 按XML产生树
 * @class Ext.ux.TreeXmlLoader
 * @extends Ext.tree.TreeLoader
 */
zhiwei.ux.TreeXmlLoader = Ext.extend(Ext.tree.TreeLoader, {
    load : function(node, callback){
        if(this.clearOnLoad){
            while(node.firstChild){
                node.removeChild(node.firstChild);
            }
        }
        if(this.doPreload(node)){ // preloaded json children
            if(typeof callback == "function"){
                callback();
            }
        }else {
            this.loadXml(node, callback);
        }
    },
    doPreload : function(node){
        if(node.attributes.children){
            if(node.childNodes.length < 1){ // preloaded?
                var cs = node.attributes.children;
                node.beginUpdate();
                for(var i = 0, len = cs.length; i < len; i++){
                    var cn = node.appendChild(this.createNode(cs[i]));
                    if(this.preloadChildren){
                        this.doPreload(cn);
                    }
                }
                node.endUpdate();
            }
            return true;
        }else {
            return false;
        }
    },

    loadXml : function(node, callback){
        var xNode = node.attributes.xmlNode;
//      If the TreeNode's xmlNode is an Element, or a Document.
//      then we can load from it.
        if (xNode && ((xNode.nodeType == 1) || (xNode.nodeType == 9))) {
//          Load child elements as child nodes
            childNodes = xNode.childNodes, l = xNode.childNodes.length;
            for (var i = 0; i < l; i++) {
                var c = xNode.childNodes[i];
                //if(c.tagName=='Menus'){continue;}
                	
                if (c.nodeType == 1) {
                    node.appendChild(this.createNode({
                    	id:c.getAttribute('id'),
                    	iconCls:c.getAttribute('iconCls'),
                        text: c.getAttribute('text'),
                        iframe:c.getAttribute('iframe'),
                        model : c.getAttribute('model'),
                        url : c.getAttribute('url'),
                        defId : c.getAttribute('defId'),
                        flowName : c.getAttribute('flowName'),
                        flowNode : c.getAttribute('flowNode'),
                        params : c.getAttribute('params'),
                        xmlNode: c,
                        expanded:true,
                        leaf: ((c.childNodes.length) == 0)
                    }));
                } else if ((c.nodeType == 3) && (c.data.trim().length != 0)) {
                    node.appendChild(this.createNode({
                    	expanded:true,
                        text: c.data,
                        leaf: true
                    }));
                }
            }
        }

        callback(this, node);
    },

    /**
    * Override this function for custom TreeNode node implementation
    */
    createNode : function(attr){
        // apply baseAttrs, nice idea Corey!
        if(this.baseAttrs){
            Ext.applyIf(attr, this.baseAttrs);
        }
        if(this.applyLoader !== false){
            attr.loader = this;
        }
        if(typeof attr.uiProvider == 'string'){
           attr.uiProvider = this.uiProviders[attr.uiProvider] || eval(attr.uiProvider);
        }
        return(attr.leaf ?
                        new Ext.tree.TreeNode(attr) :
                        new Ext.tree.AsyncTreeNode(attr));
    }
});

