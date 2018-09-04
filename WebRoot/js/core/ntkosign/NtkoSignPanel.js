/**
 * @author YungLocke
 * @class NtkoSignPanel
 * @extends Ext.Panel
 * @description 电子印章显示模板
 */
NtkoSignPanel=Ext.extend(Ext.Panel,{
     ekeyObj:null,
     constructor:function(_cfg){
        Ext.applyIf(this,_cfg);
        
        NtkoSignPanel.superclass.constructor.call(this,{
            border:false,
            layout:'fit'
        });
     },
     initComponent:function(){
     	NtkoSignPanel.superclass.initComponent.call(this);
        this.ekeyObj = document.createElement('object');
        
        this.ekeyObj.classid='clsid:97D0031E-4C58-4bc7-A9BA-872D5D572896';
        this.ekeyObj.codebase=__ctxPath+'/js/core/ntkosign/ntkosigntool.cab#version=4,0,0,0';
        this.ekeyObj.width = "100%";
	    this.ekeyObj.height = "100%";
        
        var p=document.createElement("param");
		p.setAttribute('name','BackColor');
		p.setAttribute('value','16744576');
		this.ekeyObj.appendChild(p);
		
		p=document.createElement('param');
		p.setAttribute('name','ForeColor');
		p.setAttribute('value','16777215');
		this.ekeyObj.appendChild(p);
		
		p=document.createElement('param');
		p.setAttribute('name','IsShowStatus');
		p.setAttribute('value','-1');
		this.ekeyObj.appendChild(p);
		
		var pp=document.createElement('SPAN');
		pp.innerHTML='<font color="red">不能装载印章管理控件。请在检查浏览器的选项中检查浏览器的安全设置。</font>';
		try{
			this.ekeyObj.appendChild(pp);
		}catch(e){
		}
     },
     afterRender:function(){
        NtkoSignPanel.superclass.afterRender.call(this);
        this.body.appendChild(this.ekeyObj);
		this.doLayout();
     },
     getEkeyObject:function(){
     	this.ekeyObj.IsShowRect=false
        return this.ekeyObj;
     },
     save:function(fileId,fileName,belongName,password){
     	this.ekeyObj.SignName=fileName;
     	this.ekeyObj.SignUser=belongName;
     	this.ekeyObj.Password=password;
     	this.ekeyObj.IsUseUTF8URL=true;
     	this.ekeyObj.IsUseUTF8Data=true;
        return this.ekeyObj.SaveToURL(__fullPath + '/file-upload',"FileName","fileId="+fileId+'&&file_cat=sealSign',fileName+'.esp',0);
     },
     openFormURL:function(fileId,password){
        this.ekeyObj.OpenFromURL(__ctxPath+'/file-download?fileId='+fileId,password);
        this.ekeyObj.IsShowRect=false
        return this.ekeyObj;
     }

});