var stompClient = null;
    var currentUser = null;
    var activeContact = null;
    var messages = [];
    document.addEventListener("DOMContentLoaded", function () {
    fetchCurrentUser().then(() => {
        connect();
        loadContacts();
    });
});

function fetchCurrentUser() {
    return fetch('/current-user')
        .then(response => response.json())
        .then(user => {
            currentUser = user;
            console.log("Logged in user:", currentUser);
            document.getElementById('profile-img').src = currentUser.profilePicture;
            document.querySelector('#profile p').textContent = currentUser.username;
        })
        .catch(error => {
            console.error('Error fetching current user:', error);
        });
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log("Connected as " + currentUser.name);
    stompClient.subscribe('/user/' + currentUser.id + '/queue/messages', onMessageReceived);
}

function onError(error) {
    console.error("Error connecting:", error);
}

function onMessageReceived(msg) {
    const notification = JSON.parse(msg.body);
    if (activeContact && activeContact.id === parseInt(notification.senderId)) {
        fetch(`/messages/${notification.id}`)
            .then(response => response.json())
            .then(message => {
                messages.push(message);
                displayMessages();
            });
    } else {
        alert('New message from ' + notification.senderName);
    }
    loadContacts();
}

function sendMessage() {
    var msgContent = document.getElementById("message-text").value.trim();
    if (msgContent && stompClient) {
        var message = {
            senderId: currentUser.id,
            recipientId: activeContact.id,
            senderName: currentUser.name,
            recipientName: activeContact.name,
            content: msgContent,
            timestamp: new Date().toISOString()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(message));
        messages.push(message);
        displayMessages();
        document.getElementById("message-text").value = "";
    }
}

function loadContacts() {
    fetch('/users')
        .then(response => response.json())
        .then(users => {
            let contactsList = document.getElementById("contact-list");
            contactsList.innerHTML = "";
            users.forEach(user => {
                let contactItem = document.createElement("li");
                contactItem.className = "contact";
                contactItem.onclick = () => setActiveContact(user);
                contactItem.innerHTML = `
                    <div class="wrap">
                        <img src="${user.profilePicture}" alt="">
                        <div class="meta">
                            <p class="name">${user.name}</p>
                        </div>
                    </div>`;
                contactsList.appendChild(contactItem);
            });
        });
}

function setActiveContact(contact) {
    activeContact = contact;
    document.getElementById("active-contact-img").src = contact.profilePicture;
    document.getElementById("active-contact-name").textContent = contact.name;
    fetch(`/messages?recipientId=${activeContact.id}`)
        .then(response => response.json())
        .then(msgs => {
            messages = msgs;
            displayMessages();
        });
}

function displayMessages() {
    let messagesDiv = document.getElementById("messages");
    messagesDiv.innerHTML = "";
    messages.forEach(msg => {
        let messageItem = document.createElement("div");
        messageItem.className = (msg.senderId === currentUser.id) ? "sent" : "replies";

        let date = new Date(msg.timestamp);
        let formattedTime = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

        messageItem.innerHTML = `
            <p>${msg.content}</p>
            <span class="message-timestamp">${formattedTime}</span>`;
        messagesDiv.appendChild(messageItem);
    });
}

function checkEnter(event) {
    if (event.key === "Enter") {
        sendMessage();
    }
}