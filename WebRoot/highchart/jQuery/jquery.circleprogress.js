// JavaScript Document

function circleprogress(rd,classname){
	//rd：圆的半径
	//classname：div的样式名字
		$(classname).each(function(index, el) {
		var num = $(this).find('.num').text();  //进度的数值
		num = num.substring(0, num.length-1);   //去掉百分号
		var num1,num2;
		if(num<100 && num>=10){
			//$(this).addClass('progressbar-' + num);
			num1= num.substring(0, num.length-1) * rd;   //取得进度的十位数
			num2= num.substring(1, num.length) * rd;   //取得进度的个位数
		}
		else if(num<10){
			num1=0;		
			num2= num * rd;       //取得进度的个位数
		}
		else{
			num1= 10*rd;
			num2= 0;
		};
		
		$(this).css("background-position","-"+num2+"px -"+ num1 +"px");

		});
	
};