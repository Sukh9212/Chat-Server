Chat-Server
===========

This is a Java Chat Server designed for localhost only. It uses RMI for Communicating across client and server.
To Run this Project keep Server.java, ServerThread.java, MyRemote.java in a Server folder and
Client.java, LoginWindow.java, MyRemote.java in a Client Folder. Please Make sure that MyRemote.java is in both Directories.

Steps to run:

1. Open two instances of Command prompt and navigate one to client directory and other to server directory
2. in server CMD first run the followinf command: start rmiregistry
3. then in server CMD run the following command: java Server
4. Above command will start the server. Now in client CMD run the following command: java chatwindow
5. When the LoginWindow appears enter port number 9999 and don't give any server name because its just a local system. Enter your name and hit connect.

Send message to all the users by typing in a message box.
Or if You want to message to someone in particular just click on the person's name and type.

Enjoy chatting..!!
