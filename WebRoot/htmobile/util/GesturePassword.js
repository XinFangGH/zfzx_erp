 function CaculateNinePointLotion(diffX, diffY, _MM_OffsetX, _MM_OffsetY, _MM_R) {  
            var Re = [];  
            for (var row = 0; row < 3; row++) {  
                for (var col = 0; col < 3; col++) {  
                    var Point = {  
                        X: (_MM_OffsetX + col * diffX + ( col * 2 + 1) * _MM_R),  
                        Y: (_MM_OffsetY + row * diffY + (row * 2 + 1) * _MM_R)  
                    };  
                    Re.push(Point);  
                }  
            }  
            return Re;  
        }  
        var PointLocationArr = [];  
          
 /*       document.addEventListener('DOMContentLoaded',function(){  
            setTimeout(function(){  
                onMyLoad();
            },500);  
        },false);*/  
        //window.onload = function () {  
       windowonload= function () {  
            var _MM_R = 0, _MM_CW = 0, _MM_CH = 0, _MM_OffsetX = 0, _MM_OffsetY = 0;  
          
            _MM_CW = document.body.clientWidth;  
            //_MM_CW = window.innerWidth;  
            //alert(_MM_CW + ';;'+ window.innerWidth);  
            _MM_CH = document.documentElement.clientHeight;  
            //_MM_CH = window.innerHeight;  
           /* setTimeout(function(){  
                alert(window.innerHeight);  
            },500);*/  
            //alert(window.innerHeight);  
              
            _MM_OffsetY = 100;  
            _MM_OffsetX = _MM_CW * 1/12;  
            _MM_CH = _MM_CH - _MM_OffsetY;  
              
            var c = document.getElementById("myCanvas");  
            c.width = _MM_CW;  
            c.height = _MM_CH;  
              
            R1 = (_MM_CH - 2 * _MM_OffsetY)  / 12;  
            R2 = (_MM_CW - 2 * _MM_OffsetX)  / 12;  
              
            if(R1 < R2){  
                _MM_R = R1;  
            }else {  
                _MM_R = R2;  
            }  
              
            document.getElementById("myCanvas").style.marginTop = - _MM_OffsetY + "px";  
            //document.getElementById("myCanvas").style.marginBottom = - (_MM_OffsetY/2) + "px";  
              
            var cxt = c.getContext("2d");  
            //两个圆之间的外距离 就是说两个圆心的距离去除两个半径  
            var X = (_MM_CW - 2 * _MM_OffsetX - _MM_R * 2 * 3) / 2;  
            var Y = (_MM_CH - 2 * _MM_OffsetY - _MM_R * 2 * 3) / 2;  
              
            PointLocationArr = CaculateNinePointLotion(X, Y, _MM_OffsetX, _MM_OffsetY, _MM_R);  
            InitEvent(c, cxt, _MM_CW, _MM_CH, _MM_R);//InitEvent(canvasContainer, cxt, _MM_CW, _MM_CH, _MM_R)  
            //_MM_CW=2*offsetX+_MM_R*2*3+2*X  
            Draw(cxt, PointLocationArr, [], null, _MM_CW, _MM_CH, _MM_R);  
              
            var type = getQueryString('type');  
            if(type==1){  
                document.getElementById("noButton").innerHTML='忘记手势密码';  
                document.getElementById("okButton").innerHTML='使用账号登录';  
                document.getElementById("noButton").href='index.html#userLogin/1';  
                document.getElementById("okButton").href='index.html#userLogin/1';  
            } else if(type==2){  
                document.getElementById("noButton").innerHTML='重新绘制';  
                document.getElementById("okButton").innerHTML='确定';  
            } else if(type==3){  
                document.getElementById("noButton").innerHTML='取消';  
                document.getElementById("okButton").innerHTML='确定';  
            }  
              
        };  
          
        function Draw(cxt, _PointLocationArr, _LinePointArr,touchPoint, _MM_CW, _MM_CH, _MM_R) {  
            if (_LinePointArr.length > 0) {  
                cxt.beginPath();  
                for (var i = 0; i < _LinePointArr.length; i++) {  
                    var pointIndex = _LinePointArr[i];  
                    cxt.lineTo(_PointLocationArr[pointIndex].X, _PointLocationArr[pointIndex].Y);  
                }  
                cxt.lineWidth = 10;  
                cxt.strokeStyle = "#627eed";  
                cxt.stroke();  
                cxt.closePath();  
                if(touchPoint!=null)  
                {  
                    var lastPointIndex=_LinePointArr[_LinePointArr.length-1];  
                    var lastPoint=_PointLocationArr[lastPointIndex];  
                    cxt.beginPath();  
                    cxt.moveTo(lastPoint.X,lastPoint.Y);  
                    cxt.lineTo(touchPoint.X,touchPoint.Y);  
                    cxt.stroke();  
                    cxt.closePath();  
                }  
            }  
            for (var i = 0; i < _PointLocationArr.length; i++) {  
                var Point = _PointLocationArr[i];  
                cxt.fillStyle = "#627eed";  
                cxt.beginPath();  
                cxt.arc(Point.X, Point.Y, _MM_R, 0, Math.PI * 2, true);  
                cxt.closePath();  
                cxt.fill();  
                cxt.fillStyle = "#ffffff";  
                cxt.beginPath();  
                cxt.arc(Point.X, Point.Y, _MM_R - 3, 0, Math.PI * 2, true);  
                cxt.closePath();  
                cxt.fill();  
                if(_LinePointArr.indexOf(i)>=0)  
                {  
                    cxt.fillStyle = "#627eed";  
                    cxt.beginPath();  
                    cxt.arc(Point.X, Point.Y, _MM_R -16, 0, Math.PI * 2, true);  
                    cxt.closePath();  
                    cxt.fill();  
                }  
  
            }  
        }  
        function IsPointSelect(touches,LinePoint,_MM_R)  
        {  
            for (var i = 0; i < PointLocationArr.length; i++) {  
                var currentPoint = PointLocationArr[i];  
                var xdiff = Math.abs(currentPoint.X - touches.pageX);  
                var ydiff = Math.abs(currentPoint.Y - touches.pageY);  
                var dir = Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5);  
                if (dir < _MM_R ) {  
                    if(LinePoint.indexOf(i) < 0){ LinePoint.push(i);}  
                    break;  
                }  
            }  
        }  
        function InitEvent(canvasContainer, cxt, _MM_CW, _MM_CH, _MM_R) {  
            var LinePoint = [];  
            canvasContainer.addEventListener("touchstart", function (e) {  
                IsPointSelect(e.touches[0],LinePoint,_MM_R);  
            }, false);  
            canvasContainer.addEventListener("touchmove", function (e) {  
                e.preventDefault();  
                var touches = e.touches[0];  
                IsPointSelect(touches,LinePoint,_MM_R);  
                cxt.clearRect(0,0,_MM_CW,_MM_CH);  
                Draw(cxt,PointLocationArr,LinePoint,{X:touches.pageX,Y:touches.pageY}, _MM_CW, _MM_CH, _MM_R);  
            }, false);  
            canvasContainer.addEventListener("touchend", function (e) {  
                cxt.clearRect(0,0,_MM_CW,_MM_CH);  
                Draw(cxt,PointLocationArr,LinePoint,null, _MM_CW, _MM_CH, _MM_R);  
                if(LinePoint.length > 0 && LinePoint.length<4){  
                    alert('至少需要4个链接点！');      
                    setTimeout(function(){  
                        refreshRect(cxt, _MM_CW, _MM_CH, _MM_R);  
                    },500);  
                } else{  
                    //alert("密码结果是："+LinePoint.join("->"));  
                    refreshRect(cxt);  
                }  
                LinePoint=[];  
            }, false);  
        }  
        function refreshRect(cxt, _MM_CW, _MM_CH, _MM_R){  
            cxt.clearRect(0,0,_MM_CW,_MM_CH);  
            Draw(cxt,PointLocationArr,[],{}, _MM_CW, _MM_CH, _MM_R);  
        }  
        function getQueryString(name) {  
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
            var r = window.location.search.substr(1).match(reg);  
            if (r != null) return decodeURI(r[2]); return null;  
        }  