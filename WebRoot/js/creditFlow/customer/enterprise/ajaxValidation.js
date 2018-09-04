function existenterNameAction(object,action,msg,enterId){   
		var enterObj = object;
		//var enterNameValue = Ext.get('enterName').dom.value;
		var enterNameValue = enterObj.getValue();
            Ext.Ajax.request({   
                url: __ctxPath+'/creditFlow/customer/enterprise/'+action+'.do',   
                method:'post',   
                params:{enterName:enterNameValue ,organizecode : enterNameValue , msg : msg,enterId:enterId},   
                success: function(response, option) {  
                	var obj = Ext.decode(response.responseText);  
                    if(obj.exsit==true){
                    	Ext.ux.Toast.msg('友情提示',msg);   
                       // object.markInvalid(msg); 
                        object.setValue();
                        return false;   
                    }else{   
                        return false;   
                    }      
                },   
                failure: function(response, option) {   
                    return true;   
                    Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
                }   
            });      
    }  
