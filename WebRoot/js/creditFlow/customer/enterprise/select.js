function selectLegalPerson(obj){
	Ext.onReady(function(){
		Ext.get('legalperson').dom.value = obj.name ;
		Ext.get('legalpersonid').dom.value = obj.id ;
	});
}
function selectControlPerson(obj){
	Ext.onReady(function(){
		Ext.get('controlperson').dom.value = obj.name ;
		Ext.get('controlpersonid').dom.value = obj.id ;
	});
}
function selectLinkPerson(obj){
	Ext.onReady(function(){
		Ext.get('linkperson').dom.value = obj.name ;
		Ext.get('linkmampersonid').dom.value = obj.id ;
	});
}