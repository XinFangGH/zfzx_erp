function ScrollText(content,btnPrevious,btnNext,autoStart)
{
    this.Delay = 2;
    this.LineHeight = 286;//20
	this.Amount = 1;//注意:LineHeight一定要能整除Amount.
	this.Direction = "up";
    this.Timeout = 1500;
    this.ScrollContent = this.$(content);
    this.ScrollContent.innerHTML += this.ScrollContent.innerHTML;
    //this.ScrollContent.scrollTop = 0;
    if(btnNext)
    {
        this.NextButton = this.$(btnNext);
        this.NextButton.onclick = this.GetFunction(this,"Next");
        this.NextButton.onmouseover = this.GetFunction(this,"Stop");
        this.NextButton.onmouseout = this.GetFunction(this,"Start");
    }
    if(btnPrevious)
    {
        this.PreviousButton = this.$(btnPrevious);
        this.PreviousButton.onclick = this.GetFunction(this,"Previous");
        this.PreviousButton.onmouseover = this.GetFunction(this,"Stop");
        this.PreviousButton.onmouseout = this.GetFunction(this,"Start");
    }
    this.ScrollContent.onmouseover = this.GetFunction(this,"Stop");
    this.ScrollContent.onmouseout = this.GetFunction(this,"Start");
    if(autoStart)
    {
        this.Start();
    }
}

ScrollText.prototype.$ = function(element)
{
    return document.getElementById(element);
};

ScrollText.prototype.Previous = function()
{
    clearTimeout(this.AutoScrollTimer);
    clearTimeout(this.ScrollTimer);
    this.Scroll("up");
};

ScrollText.prototype.Next = function()
{
    clearTimeout(this.AutoScrollTimer);
    clearTimeout(this.ScrollTimer);
    this.Scroll("down");
};

ScrollText.prototype.Start = function()
{
    clearTimeout(this.AutoScrollTimer);
    this.AutoScrollTimer = setTimeout(this.GetFunction(this,"AutoScroll"), this.Timeout);
};

ScrollText.prototype.Stop = function()
{
    clearTimeout(this.ScrollTimer);
    clearTimeout(this.AutoScrollTimer);
};

ScrollText.prototype.AutoScroll = function()
{
    if(this.Direction == "up")
    {
        if(parseInt(this.ScrollContent.scrollTop) >0){
    	if(parseInt(this.ScrollContent.scrollTop) >= parseInt(this.ScrollContent.scrollHeight) / 2)
        {
            this.ScrollContent.scrollTop = 0;
        }
        }
        try{
        	this.ScrollContent.scrollTop += this.Amount;        	
        }catch(e){}
    }
    else
    {
        if(parseInt(this.ScrollContent.scrollTop) <= 0)
        {
            this.ScrollContent.scrollTop = parseInt(this.ScrollContent.scrollHeight) / 2;
        }
        this.ScrollContent.scrollTop -= this.Amount;
    }
    if(parseInt(this.ScrollContent.scrollTop) % this.LineHeight != 0)
    {
        this.ScrollTimer = setTimeout(this.GetFunction(this,"AutoScroll"), this.Delay);
    }
    else
    {
        this.AutoScrollTimer = setTimeout(this.GetFunction(this,"AutoScroll"), this.Timeout);
    }
};

ScrollText.prototype.Scroll = function(direction)
{
    if(direction=="up")
    {
        if(this.ScrollContent.scrollTop == 0)
        {
            this.ScrollContent.scrollTop = parseInt(this.ScrollContent.scrollHeight) / 2;
        }
        this.ScrollContent.scrollTop -= this.Amount;
    }
    else
    {
        this.ScrollContent.scrollTop += this.Amount;
    }
    if(parseInt(this.ScrollContent.scrollTop) >= parseInt(this.ScrollContent.scrollHeight) / 2)
        {
            this.ScrollContent.scrollTop = 0;
        }
    if(parseInt(this.ScrollContent.scrollTop) % this.LineHeight != 0)
    {
        this.ScrollTimer = setTimeout(this.GetFunction(this,"Scroll",direction), this.Delay);
    }
};

ScrollText.prototype.GetFunction = function(variable,method,param)
{
    return function()
    {
        variable[method](param);
    };
};
/**
 * 以下代码实现页面noticeScrollPage.jsp 信息滚动
 */
var notices=document.getElementById("rollDiv").getElementsByTagName("div");
if(notices.length>2){
	var scrollup = new ScrollText("rollText");
	scrollup.LineHeight = 286;
	scrollup.Amount = 1;
	scrollup.Start();
}
