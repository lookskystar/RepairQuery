function setPrint(){
	//设置网页打印的页眉页脚为空
	var RegWsh = new ActiveXObject("WScript.Shell");
	RegWsh.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\header","");
	RegWsh.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\footer","");
}
function screenPrint(){
	setPrint();
	window.print();
}