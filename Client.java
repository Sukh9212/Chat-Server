import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;
import java.rmi.*;
   
    public class chatwindow extends JFrame implements Runnable 
    {
            private Socket socket;
            private DataInputStream din;
            private DataOutputStream dout;
            static private String username;
            String a,user;
            String us[] = {""};
            public  Vector<String> sdusr  = new Vector<String>();

            public static void main (String args[])
            {
                 
                        new loginwindow();
                
            }
            //Establish a connection with the Server
            public void connect(String serverip , int port , String user )
            {
                try
                {
                    this.user = user;
                    socket = new Socket("localhost",port); //creates a new Socket and binds it to localhost and given port number
                    din = new DataInputStream(socket.getInputStream()); //obtain streams 
                    dout =new DataOutputStream(socket.getOutputStream());
                    dout.writeUTF(user); //send new username to server
                    initComponents(); //design of chat window
                    new Thread(this).start();
                    username = user + " : ";
                    go();
                }catch(IOException ie)
                 {
                    JOptionPane.showMessageDialog(null,"Unable to connect","Error",JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                 }
            }
            //go() method is used for inital display of current users and uses RMI feature of JAVA
            public void go()
            {
                try
                {
                    MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/Remotehello");
                    a = service.sayhello(); //call to sayhello() method of server which returns a welcome string 
                    System.out.println(a+" "+user);
                    sdusr = service.usernamelist(); //call to usernamelist() methd of server which returns current active users
                    us = sdusr.toArray(us); // adds the usernames to the vector
                    ulist.setListData(us); // adds the vector to the JList for display in chat window
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
            }
            // go2() is used when a new user enters and there are already other users present 
            // it obtains updated list after a new user is entered
            public void go2()
            {
                try
                {
                    MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/Remotehello");
                    sdusr = service.usernamelist(); //obtains updated list
                    us = sdusr.toArray(us);
                    System.out.println("current users : ");
                    for(String a : us)
                    System.out.println(a);
                    ulist.setListData(us);       //sets JList contents to new updated list           
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
            }
            // go3() is used when a user is to be removed from the list
            //it removes the user and then obatian the updated list
            public void go3()
            {
                try
                {
                    MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/Remotehello");
                    service.rmvusr(user);          // call to the rmvusr method of server         
                    sdusr = service.usernamelist(); //again obtain updated list
                    us = sdusr.toArray(us);                  
                    ulist.setListData(us); // sets the new contents to JList
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
            }
            private void initComponents() 
            {
                    textField1 = new JTextField();
                    scrollPane1 = new JScrollPane();
                    textArea1 = new JTextArea();
                    label1 = new JLabel();
                    button1 = new JButton();
                    button2 = new JButton();
                    scrollPane2 = new JScrollPane();
                    ulist = new JList();
                    label2 = new JLabel();
                    action1 = new sendmessage();
                    action2 = new exit();

                    //======== this ========
                    setTitle("CONNECTED AS :  " + user);
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    setResizable(true);
                    setVisible(true);
                    Container contentPane = getContentPane();
                    contentPane.setBackground(Color.DARK_GRAY);
                    contentPane.setLayout(null);

                    //---- textField1 ----
                    textField1.setBorder(LineBorder.createBlackLineBorder());
                    textField1.setToolTipText(" Write Your Message Here");
                    textField1.setFont(new Font("Agency FB", Font.PLAIN, 14));
                    textField1.setBackground(Color.LIGHT_GRAY);
                   
                    contentPane.add(textField1);
                    textField1.setBounds(5, 450, 575, 110);

                    //======== scrollPane1 ========
                    {

                            //---- textArea1 ----
                    textArea1.setBorder(LineBorder.createBlackLineBorder());
                    textArea1.setFont(new Font("Agency FB", Font.PLAIN, 14));
                    textArea1.setBackground(Color.LIGHT_GRAY);
                    textArea1.setEditable(false);
                    scrollPane1.setViewportView(textArea1);
                    }
                    contentPane.add(scrollPane1);
                    scrollPane1.setBounds(5, 5, 575, 410);

                    //---- label1 ----
                    label1.setText("         Write Your Message Here");

                    label1.setFont(new Font("segoe print", Font.BOLD, 18));
                    label1.setForeground(Color.WHITE);

                    contentPane.add(label1);
                    label1.setBounds(140, 420, 290, label1.getPreferredSize().height);

                    //---- button1 ----
                    button1.setBorder(LineBorder.createBlackLineBorder());
                    button1.setFont(new Font("segoe print", Font.PLAIN, 24));
                    button1.setAction(action1);
                    contentPane.add(button1);
                    button1.setBounds(630, 450, 120, 40);

                    //---- button2 ----
                    button2.setBorder(LineBorder.createBlackLineBorder());
                    button2.setFont(new Font("segoe print", Font.PLAIN, 24));
                    button2.setAction(action2);
                    contentPane.add(button2);
                    button2.setBounds(630, 520, 120, 40);

                    //======== scrollPane2 ========
                    {

                            //---- USERLIST ----

                            ulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            ulist = new JList();
                            ulist.setFont(new Font("segoe print", Font.BOLD, 16));
                            scrollPane2 = new JScrollPane(ulist);
                            scrollPane2.setViewportView(ulist);
                            ulist.setBackground(Color.LIGHT_GRAY);
                    }
                    contentPane.add(scrollPane2);
                    scrollPane2.setBounds(630, 45, 130, 370);
                     //---- label2 ----
                    label2.setText("CURRENT USERS");
                    label2.setFont(new Font("segoe print", Font.BOLD, 16));
                    label2.setForeground(Color.white);
                    contentPane.add(label2);
                    label2.setBounds(625, 0, 180, 55);
                    contentPane.setPreferredSize(new Dimension(800, 600));
                    pack();
                    setLocationRelativeTo(getOwner());
                    
                    ulist.addMouseListener(new java.awt.event.MouseAdapter() 
                    {
                        public void mouseClicked(java.awt.event.MouseEvent evt) //when mouse click occurs in a JList
                        {
                            try 
                            {
                                nickListMouseClicked(evt); //handles the operation of mouse click
                            }catch (IOException ex) {
                                Logger.getLogger(chatwindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // JList mouse click i.e. private messaging
                        private void nickListMouseClicked(java.awt.event.MouseEvent evt) throws IOException, RemoteException
                        {
                            if (!ulist.getSelectedValue().equals(user)) // makes sure the clicked username is not its own name
                            {
                                String msg =  JOptionPane.showInputDialog(null, "WRITE A PRIVATE MESSAGE TO : " + ulist.getSelectedValue()); //takes the input from user
                                if (!msg.isEmpty()) //message is not supposed to be empty
                                {
                                    String gg = ulist.getSelectedValue().toString(); //obtains the receiver's username
                                    int ss = gg.length();
                                    int kk = user.length();
                                    try 
                                    {
                                        MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/Remotehello");
                                        service.ulen(ss, kk); //sends the length of sender's and receiver's username length to server via RMI 
                                    }catch (Exception ex ){
                                        ex.printStackTrace();
                                    }
                                    dout.writeUTF("ppost" +ulist.getSelectedValue() +user+ msg ); //sends (ppost + reciever name + sender name + message) to server
                                    textArea1.append("ppost to  " + ulist.getSelectedValue() +"  :  " + msg+"\n"); //writes in own window
                                    System.out.println("private post to : " + ulist.getSelectedValue()); //writes on console
                                }

                            }
                        }

                    });
                   
            }
            private JTextField textField1;
            private JScrollPane scrollPane1;
            private JTextArea textArea1;
            private JLabel label1;
            private JButton button1;
            private JButton button2;
            private JScrollPane scrollPane2;
            private JList ulist;
            private JLabel label2;
            private sendmessage action1;
            private exit action2;

            @Override
            public void run() 
            {
                try
                {
                    while(true)
                    {
                          String message = din.readUTF();
                          if(message.startsWith("chat")) // displays normal message which goes to everyone
                          {
                              String msg = message.substring(4);
                              textArea1.append(msg + "\n");
                          }
                          else if(message.startsWith("new")) //if message starts with new a new user has entered call go2()
                          {
                              go2();
                          }
                          else if(message.startsWith("ppost")) // if message starts with ppost its a private post to ourself
                          {
                              String pmsg = message.substring(5);
                              textArea1.append("ppost from " + pmsg + "\n");
                          }
                          else
                          {
                              textArea1.append("");
                          }
                    }
                }catch(IOException ie){}
            }
            //used for sending a message
            private class sendmessage extends AbstractAction 
            {
                private sendmessage() 
                {
                     putValue(NAME, "SEND");
                     putValue(SHORT_DESCRIPTION,"CLICK TO SEND A MESSAGE");
                }

                public void actionPerformed(ActionEvent e) 
                {
                    try
                    {
                        if(textField1.getText().trim().isEmpty()){} //if empty dont do anything
                        else
                        {
                            String message = ("chat" +username + textField1.getText().trim());
                            dout.writeUTF(message); //sends the message to server which further sends to all users
                            textField1.setText("");
                        }
                    }catch (IOException ie){}
                 }
             }
            // when a user makes an exit
             private class exit extends AbstractAction 
             {
                 private exit() 
                 {
                     putValue(NAME,"EXIT");
                     putValue(SHORT_DESCRIPTION,"CLICK TO EXIT");
                 }

                public void actionPerformed(ActionEvent e) 
                {  
                    // shows option window to be sure to make an exit
                    int kl = JOptionPane.showConfirmDialog(null,"ARE U SURE WANT TO EXIT?","      CONFIRM EXIT",JOptionPane.YES_NO_OPTION);
                    if(kl == 0) // if true
                    {
                        go3(); //go3() makes user disappear
                        System.exit(0); //exits the user
                    }
                 }   
             }               
    }
