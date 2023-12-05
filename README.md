# ConcurrentChatServer

The project is a simple representation of the a Concurrent Chat Server.

The project was created to be run on the Terminal.
It is composed by a Server object and a Client object, both instantiate and manage a set of Threads to allow constant receival and send of messages to other users with the Chat open simultaneously, connected to the same host and Port. 
By default, and for testing purposes, the project is currently set to the localhost and port 9001.

Once the Client enters the Chat (assuming the Server is already running), the server requests for a username, which will be used to inform the remaining participants that a new User has joined and to flag the messages sent by the respective user throughout the chat.

The code is also prepared to close the respective socket once the user enters the message "/quit" which is subsequently inform the remaining participants that the user has left the conversation.

The program is fully functional, however improvements might be performed as I learn and perfect my own programming skills.
