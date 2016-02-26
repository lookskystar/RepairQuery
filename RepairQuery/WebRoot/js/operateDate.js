/**
关于日期操作相关JS通用代码，注意其中函数之间会相互调用
**/
//得到今天的日期,得到2013-12-18
function geToday(){
	var myDate=new Date();
	var date=myDate.getYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
	return date;
}

//将字符串转换为日期,传入字符串2013-12-18
function strToDate(str){
	var array=str.split("-");
	var date=new Date(array[0],parseInt(array[1],10)-1,array[2]);
	return date;
}

//判断两个日期之间相差多少天，参数为日期格式，如果是字符串，可通过上面函数进行转换
//传入日期：2013-12-12,2013-12-19，得到结果7
function days_between(s_date,e_date){
	var ONE_DAY=1000*60*60*24;   
	var date1_ms = s_date.getTime();   
	var date2_ms = e_date.getTime();       
	var difference_ms = Math.abs(date1_ms- date2_ms);   
	return Math.round(difference_ms/ONE_DAY); 
}

//获取两日期之间的所有日期
//传入参数：字符串类型，通过上面函数转换为相应日期类型
//传入：2013-12-18,2013-12-21
//得到结果：[2013-12-18,2013-12-19,2013-12-20,2013-12-21]
function getBetweenDate(startTime,endTime){
	var s_date=strToDate(startTime);
	var e_date=strToDate(endTime);
	var days=days_between(s_date,e_date);
	var dateArray=[];
	for(var i=0;i<=days;i++){
		var temp=new Date(s_date.getYear(),s_date.getMonth(),s_date.getDate()+i);
		temp = temp.getFullYear() + "-" + (temp.getMonth()+1) + "-" + temp.getDate();
		dateArray.push(temp);
	}
	return dateArray;
}

//获取两日期之间的所有日期,过滤掉周末
//和上面的方法一样，只是把日期对应如果为星期六和星期天则过滤掉
function getBetweenDateNoWeek(startTime,endTime){
	var s_date=strToDate(startTime);
	var e_date=strToDate(endTime);
	var days=days_between(s_date,e_date);
	var dateArray=[];
	for(var i=0;i<=days;i++){
		var temp=new Date(s_date.getYear(),s_date.getMonth(),s_date.getDate()+i);
		if(temp.getDay()!=0&&temp.getDay()!=6){
			temp = temp.getFullYear() + "-" + (temp.getMonth()+1) + "-" + temp.getDate();
			dateArray.push(temp);
		}
	}
	return dateArray;
}

//根据日期获得星期几
//传入参数：字符串2013-12-18
//得到将结果：星期三
function getWeekByDate(strDate){
	var weekArray=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	var date=strToDate(strDate);
	return weekArray[date.getDay()];
}

//获取前一个月时间
//传入字符串：2013-12-18
//得到结果：字符串2013-11-18
function getUpMonth(t){
    var tarr = t.split('-');
    var year = tarr[0];                //获取当前日期的年
    var month = tarr[1];            //获取当前日期的月
    var day = tarr[2];                //获取当前日期的日
    var days = new Date(year,month,0);    
    days = days.getDate();//获取当前日期中的月的天数

    var year2 = year;
    var month2 = parseInt(month)-1;
    if(month2==0) {
        year2 = parseInt(year2)-1;
        month2 = 12;
    }
    var day2 = day;
    var days2 = new Date(year2,month2,0);
    days2 = days2.getDate();
    if(day2>days2) {
        day2 = days2;
    }
    if(month2<10) {
        month2 = '0'+month2;
    }
    var t2 = year2+'-'+month2+'-'+day2;
    return t2;
}



