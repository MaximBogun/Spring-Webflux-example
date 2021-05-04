var ws = null;
var url = "ws://localhost:8080/book/stream";



function connect() {
    ws = new WebSocket(url);
    ws.onopen = function () {
        console.log("Connected")
        log('Info: Connection Established.');
    };

    ws.onmessage = function (event) {
        log("Get from server:" + event.data);
    };

    ws.onclose = function (event) {
        log('Info: Closing Connection.');
    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}

function sendChange() {
    if (ws != null) {
        var text = document.getElementById('text').value;
        var title = document.getElementById('title').value;
        var author = document.getElementById('author').value;
        var message = JSON.stringify({
            'text': text,
            'title': title,
            'author': author
        })
        log('Sent to server : ' + message);
        ws.send(message);
    } else {
        alert('connection not established, please connec    t.');
    }
}

function log(message) {
    var console = document.getElementById('logging');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
}