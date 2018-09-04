<%@page import="com.zhiwei.core.util.RequestUtil" isErrorPage="true" pageEncoding="UTF-8"%>
<%@page import="com.zhiwei.credit.util.GetMACUtil"%>
<%
	String basePath=request.getContextPath();
    String macAddress=GetMACUtil.getMacStr();
    macAddress=java.net.URLEncoder.encode(macAddress,"UTF-8");
%>
<html>
	<head>
		 <title>授权失败</title>
		 <link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		 <script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
		 <script type="text/javascript">
		 
		    function getAuthorization(type)
		    {
		            var _window=new Ext.Window({ 
                    title:"软件认证", 
				    enderTo:Ext.getBody(), 
				    frame:true, 
					plain:true, 
					bodyStyle : 'overflowX:hidden',
					resizable:false, 
					modal:true,
					buttonAlign:"right", 
					closeAction:"hide", 
					maximizable:true, 
					closable:true, 
				    bodyStyle:"padding:4px", 
					width:310, 
				    height:150, 
					layout:"form", 
					lableWidth:45, 
					listeners:{ 
					  "show":function(){ 
					  }, 
					  "hide":function(){ 
					      _window.close();
				      }, 
					  "close":function(){ 
					  } 
					} 
				})
				if(type=="password"){
				   var panl=new Ext.FormPanel({
                        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:65,monitorValid:true,
                        defaults:{xtype:"textfield",width:"200"},
                        items:[
                          {fieldLabel:"授权密码", vtype:"alpha",name:"password",id:"password", allowBlank:false, blankText:"授权密码不能为空"},
                          {fieldLabel:"密匙", name:"key",id:"key",allowBlank:false, blankText:"密匙不能为空"}
                        ],
                        buttons:[{
                            text:"认证",
    						type:"button",
    						handler:function(){
    						   if(panl.getForm().isValid()){
    						    var password= Ext.getCmp('password').getValue();
    						    var key= Ext.getCmp('key').getValue();
    						   	Ext.Ajax.request({
        							url:'<%=request.getContextPath()%>'+"/authorization.do",
          							method:"post",
          							success:function(response,opts){
          							     var data = Ext.decode(response.responseText);
          							     var isFalg=data.success;
          							     if(isFalg){
          							           _window.close();
          							          Ext.Msg.alert("提示","授权成功");
          							          window.top.location.reload();
          							     }
          							     else{
          							          Ext.Msg.alert("提示","授权失败，输入的密码或者密匙不正确");
          							     }
          							},
          							params:{
            							 password:password,
            							 key:key
          							} 
          						})}
    						}
                        },{
                            text:"取消",
    						type:"button",
    						handler:function(){
    						   _window.close();
    						}
                        }]
                    });
                    _window.add(panl);
				} 
                _window.show(); 
		    }
		 </script>
	</head>
	<body style="">
		<h2>您的机器码是:<%=macAddress%>.请联系软件提供商购买授权密码</h2><br/>
		<a href="javascript:void(0)" onClick="getAuthorization('password')">输入授权密码</a></br>
	</body>
	
</html>