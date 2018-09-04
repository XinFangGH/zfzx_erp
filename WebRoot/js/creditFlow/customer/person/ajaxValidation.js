function ajaxUniquenessValidator(object,action,msg){  
		var paraNameText = "";
		var Objt = object;
		alert(Objt.getName())
		var paraValue = Objt.getValue();
		var paraName = Objt.getName();
		if(Objt.getName().indexOf('.')!=-1){
			paraNameText = paraName.substr(paraName.indexOf('.')+1);
		}else{
			paraNameText = paraName;
		}
            Ext.Ajax.request({   
                url: __ctxPath+'/creditFlow/customer/person/'+action+'.do?'+paraNameText+'='+paraValue,   
                method:'post',   
                success: function(response, option) {   
                    var obj = Ext.decode(response.responseText);
                    if(obj.exsit==true){
                    	object.markInvalid(msg);   
                    	return false;   
	                }else{   
	                    return true;   
	                }  
                },   
                failure: function(response, option) {   
                    return true;   
                    Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
                }   
            });      
    }