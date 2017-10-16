var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var md5 = require('md5');
var server_token = "sreejesh@kriyatma";
var hash_serv_token = md5(server_token);
io.on('connection',function(socket){
    console.log('on user connected '+socket.id);
    socket.auth = false;
    socket.on('authenticate',function(data){
        var token = data.token;
        console.log('token recieved is '+token);
        if(hash_serv_token == token){
            socket.auth = true;
            console.log('connection is authenticated '+socket.id);
            socket.emit("authenticate",true);
        }
    })
    socket.on('disconnect',function(){
        console.log('one user disconnected '+socket.id);
    })
    setTimeout(function(){
        if(!socket.auth){
            console.log('disconnecting the socket '+socket.id);
            socket.disconnect();
        }
    },1000);
})

http.listen(9000,function(){
    console.log('server listening on port 3000');
})
