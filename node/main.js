var sys = require("sys"),
	http = require("http");
	
http.createServer(function(req, res){
	res.writeHead(200, {"Content-Type": "text/html"});
	res.write("Hi,王懿");
	res.end();
}).listen(8000);

sys.puts("Server running at port 8000");

