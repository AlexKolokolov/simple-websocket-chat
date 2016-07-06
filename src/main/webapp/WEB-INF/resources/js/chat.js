/**
 * Created by kolokolov on 7/1/16.
 */
var Chat = {};

Chat.socket = null;

Chat.connect = (function (host) {
    if ('WebSocket' in window) {
        Chat.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Chat.socket = new MozWebSocket(host);
    } else {
        Console.log('Error: WebSocket is not supported by this browser.');
        return;
    }

    Chat.socket.onopen = function () {
        Console.log('Info: WebSocket connection opened.');
        document.getElementById('chat').onkeydown = function (event) {
            if (event.keyCode == 13) {
                Chat.sendMessage();
            }
        };
    };

    Chat.socket.onclose = function () {
        document.getElementById('chat').onkeydown = null;
        Console.log('Info: WebSocket closed.');
    };

    Chat.socket.onmessage = function (event) {
        var message = JSON.parse(event.data);
        var type = message.type;
        if (type === "MESSAGE") {
            Console.log("<i>" + message.created + "</i> : <b>" + message.author + "</b> : " + message.body);
        } else {
            UserList.log(message.body);
        }
    };
});

Chat.initialize = function () {
    var wsPort = '';
    var wssPort = '';
    var locationHost = window.location.host;
    if (locationHost != 'localhost:8080') {
        wsPort = ':8000';
        wssPort = ':8443';
    }
    if (window.location.protocol == 'http:') {
        Chat.connect('ws://' + locationHost + wsPort + '/websocket/chat/' + nickname);
    } else {
        Chat.connect('wss://' + locationHost + wssPort + '/websocket/chat/' +  + nickname);
    }
};

Chat.sendMessage = (function () {
    var message = document.getElementById('chat').value;
    if (message != '') {
        Chat.socket.send(message);
        document.getElementById('chat').value = '';
    }
});

var UserList = {}

UserList.log = (function (userList) {
    var loggedUsers = document.getElementById('loggedUsers');
    var p = document.createElement('p');
    p.innerHTML = userList;
    while (loggedUsers.firstChild) {
        loggedUsers.removeChild(loggedUsers.firstChild)
    }
    loggedUsers.appendChild(p);
    loggedUsers.scrollTop = loggedUsers.scrollHeight;
});

var Console = {};

Console.log = (function (message) {
    var console = document.getElementById('console');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.innerHTML = message;
    console.appendChild(p);
    while (console.childNodes.length > 50) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
});

Chat.initialize();

document.addEventListener("DOMContentLoaded", function () {
    // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
    var noscripts = document.getElementsByClassName("noscript");
    for (var i = 0; i < noscripts.length; i++) {
        noscripts[i].parentNode.removeChild(noscripts[i]);
    }
}, false);