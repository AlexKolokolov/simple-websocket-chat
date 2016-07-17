/**
 * Created by kolokolov on 7/1/16.
 */
$(document).ready(function() {

    $('.noscript').empty();

    var Console = {};
    Console.printMessage = function(message) {
        $('#console').append('<p>' + message + '</p>');
        while ($('#console').children('p').length > 50) {
            $('#console').children('p').first().remove();
        }
    };

    var UserList = {};
    UserList.displayUsers = function(userList) {
        var $loggedUsers = $('#loggedUsers');
        $loggedUsers.empty();
        $loggedUsers.append('<p>' + userList + '</p>');
    };

    var Chat = {};

    Chat.connect = function (host) {
        if ('WebSocket' in window) {
            Chat.socket = new WebSocket(host);
        } else if ('MozWebSocket' in window) {
            Chat.socket = new MozWebSocket(host);
        } else {
            Console.printMessage('Error: WebSocket is not supported by this browser.');
            return;
        }
    };

    Chat.initialize = function () {
        var wsPort = '';
        var wssPort = '';
        var locationHost = window.location.host;
        if (locationHost != 'localhost:8080') {
            wsPort = ':8000';
            wssPort = ':8443';
        }
        if (window.location.protocol == 'http:') {
            Chat.connect('ws://' + locationHost + wsPort + '/websocket/chat');
        } else {
            Chat.connect('wss://' + locationHost + wssPort + '/websocket/chat');
        }
    };

    Chat.initialize();

    var $chat = $('#chat');

    Chat.socket.onopen = function() {
        Console.printMessage('Info: WebSocket connection opened.');
        $chat.keydown(function(event) {
            if (event.keyCode == 13) {
                Chat.socket.sendMessage();
            }
        });
    };

    Chat.socket.onclose = function() {
        Console.printMessage('Info: WebSocket closed.');
        $chat.keydown(function() {
            return null;
        });
    };

    Chat.socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        var type = message.type;
        if (type === "MESSAGE") {
            Console.printMessage("<i>" + message.created + "</i> : <b>" + message.author + "</b> : " + message.body);
        } else {
            UserList.displayUsers(message.body);
        }
    };

    Chat.socket.sendMessage = function() {
        var message = $chat.val();
        if (message != '') {
            Chat.socket.send(message);
            $chat.val('');
        }
    };
});