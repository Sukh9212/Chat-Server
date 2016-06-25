import java.io.*; 
import java.net.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class ServerThread extends Thread  
    {
        private Server server;
        private Socket socket;
        String usrname;
        public ServerThread( Server server, Socket socket, String usrname ) 
        {   
            this.server = server;
            this.socket = socket;
            this.usrname = usrname;
            start();
        }
        public void run()   
        {
            try 
            {
                DataInputStream din = new DataInputStream( socket.getInputStream() );
                while (true) 
                {
                    String message = din.readUTF(); 
                    server.sendToAll( message );
                }
            } catch( EOFException ie ) {
            }catch( IOException ie ) {
                ie.printStackTrace();} 
            
            finally 
            {
            try {
                server.removeConnection( usrname,socket );
            } catch (RemoteException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    }
