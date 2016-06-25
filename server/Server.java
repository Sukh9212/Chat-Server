import java.io.*; 
import java.net.*; 
import java.util.*;
import java.rmi.*;
import  java.rmi.server.*;

    public class Server extends UnicastRemoteObject implements MyRemote
    {
        private ServerSocket ss;
        public String username ;
        private Hashtable<String, DataOutputStream> outputStreams = new Hashtable<>();
        public Vector<String>userlist = new Vector<String>();
        int flag,a,b,size;
        boolean flag1 = true;
    
        public Server() throws RemoteException {}

        private void listen() throws IOException 
        {
            ss = new ServerSocket(9999);   //listens to the port 9999 
            while(true)
            {
                Socket s = ss.accept(); //keep accepting new users
                System.out.println(" connection from :" + s);
                DataOutputStream dout = new DataOutputStream( s.getOutputStream() ); //obtain streams
                DataInputStream tdin = new DataInputStream( s.getInputStream() );
                username = tdin.readUTF(); //read the username sent by client
                String nwstr = "new" +username;
                userlist.add(username); //add username to vector userlist
                System.out.println("current users : ") ;   //prints the current users on the console   
                for(String a : userlist)
                {
                   dout.writeUTF(a);        
                   System.out.println(a);
                } 
                outputStreams.put( username, dout ); // put the username & its corresponding stream into a hashtable
                sendToAll(nwstr); //to send newusername to all the users
                ServerThread serverThread = new ServerThread( this, s, username ); // create a new ServerThread used to recieve and send messages 
            }   
        }
        // to return userlist
        public  Vector<String> usernamelist()
        {
            return userlist;
        }
        //welcome message
        public String sayhello() throws RemoteException
        {
            return("server says hellllloooooo ");
        }
        // to remove user from the list
        public void rmvusr(String rmv) throws RemoteException
        {
            userlist.removeElement(rmv);
            System.out.println("current users : ");
            System.out.println(userlist);
        }
        //accepts username length sent by client
        public void ulen(int aa,int bb) throws RemoteException
        {
            a = aa;
            b = bb;
        }
        Enumeration getOutputStreams()
        {
            return outputStreams.elements();
        }
        //used for sending of messages fron server to all the clients or if private post then to only particular client
        void sendToAll( String message ) 
        {
            synchronized( outputStreams ) 
            {
                String str;
                if(message.startsWith("ppost")) //if starts with ppost then it is a private message sends only to particular client
                {
                    String pmsg = message.substring(5); //removes word ppost from message leaving reciever's name at front
                    Enumeration<String> names;
                    Enumeration name;
                    names = outputStreams.keys(); //obatain the usernames from the hashtable
                    name = outputStreams.elements(); //obtains the streams from the hashtable
                    while(names.hasMoreElements()&&name.hasMoreElements()&&flag1) //all the fields in hashtable
                     {
                        str = names.nextElement().toString();//obtain the first string name in a hashtable
                        DataOutputStream dout = (DataOutputStream)name.nextElement(); //obtains corresponding first outputstream
                        System.out.println( str);
                        if(pmsg.regionMatches(0, str, 0, a)) //compares all the names untill correct match occurs
                        {
                            flag1 = false;
                            int i = str.length();
                            String msg2 = pmsg.substring(i+b); //extracts the message 
                            String sname = pmsg.substring(i,i+b); //extracts the sendername
                            try 
                            {
                                dout.writeUTF("ppost" +" "+sname+" : " + msg2 ); //sends the private message
                            } catch( IOException ie ) { 
                            System.out.println( ie ); }
                        }
                     }
                    flag1 = true;
                    flag = 1;
                }
                if(flag != 1)//when it is a normal message send to all the clients
                {
                    for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) 
                    {
                        DataOutputStream dout = (DataOutputStream)e.nextElement();
                        try 
                        {
                            dout.writeUTF( message );
                        } catch( IOException ie ) { 
                        System.out.println( ie ); }
                    }

                }
                flag = 0;
            }
        }
        // removes the connection of client and updates the list when user simply clicks the X button
        void removeConnection( String usr,Socket s ) throws RemoteException 
         {     
             synchronized( outputStreams ) 
                {
                    outputStreams.remove( usr );
                    userlist.removeElement(usr);
                    sendToAll("new");
                    try 
                    {
                        s.close();
                    } catch( IOException ie ) 
                    {
                        System.out.println( "Error closing "+s ); ie.printStackTrace();
                    }
                }
         }
        public static void main( String args[] ) throws Exception 
        {
            MyRemote service = new Server();
            Naming.rebind("Remotehello",service);
            Server s1 = (Server) service ;
            s1.listen(); //starts listening
        }
    }
