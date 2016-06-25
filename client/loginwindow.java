import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.rmi.*;
import java.util.*;

public class  loginwindow extends JFrame   //the loginwindow main class
    
    {
  public loginwindow()//the constructor 
        {
                initComponents();
	}
        public static void main(String args[])
        {            
                new loginwindow();
        }
        public  Vector<String> sdusr  = new Vector<String>();
        boolean check = true;
        int port;
        
        private void initComponents()//this method is being used to design & display the login window 
        {
                panel1 = new JPanel();
		btnconnect = new JButton();
		btncancel = new JButton();
		lblserver = new JLabel();
		txtserver = new JTextField();
		lblport = new JLabel();
		txtport = new JTextField();
		lbluser = new JLabel();
		txtuser = new JTextField();
		label1 = new JLabel();
                action1 = new connectlistener();
		action2 = new cancellistener();
                lb1 = new JLabel();
                lb3 = new JLabel();
                lb2 = new JLabel();
                lb4 = new JLabel();
              
                
		//======== this ========
		setTitle(" LOGIN");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);               
		Container contentPane = getContentPane();             
		contentPane.setLayout(null);                
               
		//======== panel1 ========
		{
			panel1.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
			panel1.setLayout(null);

			//---- btnconnect ----
                        btnconnect.setAction(action1);
			btnconnect.setText("CONNECT");
			btnconnect.setBackground(Color.WHITE);
                        btnconnect.setForeground(Color.BLACK);
                        btnconnect.setBorder(LineBorder.createBlackLineBorder());
			btnconnect.setFont(new Font("segoe print", Font.PLAIN, 16));
			panel1.add(btnconnect);
			btnconnect.setBounds(490, 310, 100, 25);
                        
			//---- btncancel ----
			btncancel.setAction(action2);
                        btncancel.setText("CANCEL");
                        btncancel.setBackground(Color.WHITE);
                        btncancel.setForeground(Color.BLACK);
			btncancel.setBorder(LineBorder.createBlackLineBorder());
			btncancel.setFont(new Font("segoe print", Font.PLAIN, 16));
                        
			panel1.add(btncancel);
			btncancel.setBounds(490, 370, 100, 25);
                        
			//---- lblserver ----
			lblserver.setText("Server Name :");
			lblserver.setFont(new Font("segoe print", Font.PLAIN, 16));
                        lblserver.setForeground(Color.BLACK);
                       
			panel1.add(lblserver);
			lblserver.setBounds(470, 90, 120, 35);
                        txtserver.setBackground(Color.white);
                        txtserver.setBorder(LineBorder.createBlackLineBorder());
                        
			panel1.add(txtserver);
			txtserver.setBounds(470, 120, 170, 25);

			//---- lblport ----
			lblport.setText("Port Number :");
			lblport.setFont(new Font("segoe print", Font.PLAIN, 16));
                        lblport.setForeground(Color.BLACK);
			panel1.add(lblport);
			lblport.setBounds(470, 160, 120, 35);
                        txtport.setBackground(Color.white);
                        txtport.setBorder(LineBorder.createBlackLineBorder());
			panel1.add(txtport);
			txtport.setBounds(470, 190, 170, 25);

			//---- lbluser ----
			lbluser.setText("User Name :");
			lbluser.setFont(new Font("segoe print", Font.PLAIN, 16));
                        lbluser.setForeground(Color.BLACK);
			panel1.add(lbluser);
			lbluser.setBounds(470, 230, 120, 35);
                        txtuser.setBackground(Color.WHITE);
                        txtuser.setBorder(LineBorder.createBlackLineBorder());
			panel1.add(txtuser);
			txtuser.setBounds(470, 260, 170, 25);

			//---- label1 ----
			label1.setText("           Login Window");
			label1.setFont(new Font("Segoe WP", Font.PLAIN, 38));
			label1.setForeground(Color.BLUE);
                        panel1.add(label1);
			label1.setBounds(80, 10, 500, 50);

                        lb1.setText("Java");
                        lb1.setFont(new Font("Segoe print",Font.PLAIN,40));
                        lb1.setForeground(Color.BLACK);
                        panel1.add(lb1);
                        lb1.setBounds(100, 70, 150, 100);
                        lb2.setText("Chat");
                        lb2.setFont(new Font("Segoe print",Font.PLAIN,40));
                        lb2.setForeground(Color.BLACK);
                        panel1.add(lb2);
                        lb2.setBounds(140, 170, 150, 100);
                        lb3.setText("Server");
                        lb3.setFont(new Font("Segoe print",Font.PLAIN,40));
                        lb3.setForeground(Color.BLACK);
                        panel1.add(lb3);
                        lb3.setBounds(180, 270, 150, 100);
                        
                        lb4.setText("@Developers : Sukhdeep & Varinder");
                        lb4.setFont(new Font("segoe print",Font.BOLD,14));
                        lb4.setForeground(Color.white);
                        panel1.add(lb4);
                        lb4.setBounds(10, 460, 500, 50);
                         
		}
                panel1.setBackground(Color.GRAY);
                contentPane.add(panel1);
		panel1.setBounds(0, 0, 1364,705);
		contentPane.setPreferredSize(new Dimension(700,500));
		pack();
		setLocationRelativeTo(null);
                
	}//end of method initcomponents
     

	
	private JPanel panel1;
	private JButton btnconnect;
	private JButton btncancel;
	private JLabel lblserver;
	private JTextField txtserver;
	private JLabel lblport;
	private JTextField txtport;
	private JLabel lbluser;
	private JTextField txtuser;
	private JLabel label1;
        private connectlistener action1;
	private cancellistener action2;
        private JLabel lb1;
        private JLabel lb2;
        private JLabel lb3;
        private JLabel lb4;
	
    private class cancellistener extends AbstractAction 
    {
	private cancellistener() 
        {	
              putValue(SHORT_DESCRIPTION, " CLICK TO CANCEL");
	}
	public void actionPerformed(ActionEvent e) 
         {
            int kl = JOptionPane.showConfirmDialog(null,"ARE U SURE WANT TO EXIT?","      CONFIRM EXIT",JOptionPane.YES_NO_OPTION);
            if(kl == 0) 
            {
                System.exit(0);
            }
	}
    }//end of class cancellistner

    private class connectlistener extends AbstractAction 
    {
        private connectlistener() 
        {
            putValue(SHORT_DESCRIPTION, " CLICK TO CONNECT");
	}
	public void actionPerformed(ActionEvent e) 
        {
            String server = txtserver.getText();//retrieves data from "server name" box in login window
           try{
               port = Integer.parseInt(txtport.getText());//retrieves data from "port number" field and ensures that it is numeric data 
           }catch (NumberFormatException e2){
             JOptionPane.showMessageDialog(null, "INCORRECT FORMAT....!!!!!"+"\n"+"PLEASE ENTER PORT NUMBER IN NUMERIC DIGITS"); 
           }
            String user = txtuser.getText();//retrieves data from "username" field         
            if(user.isEmpty())//checks if user didn't gave the name because name is a mandatory field
            {
               JOptionPane.showMessageDialog(null, "USERNAME REQUIRED...!!!!!"+"\n"+"PLEASE ENTER THE USERNAME"); 
            }
            else//when all the data is correct move ahead
            {
            try//here this block checks if the username given by new user has already been given by some other user. every username must be unique...!!!
                {
                    MyRemote service = (MyRemote) Naming.lookup("rmi://localhost/Remotehello");// rmi syntax for remote method calling. It basically creates instance of MyRemote interface to access a method in Server.java
                    sdusr = service.usernamelist();//call to method in Server.java which returns list of currently active users
                   check =  sdusr.contains(user);//checking is done and its ensured that there must not be a 2 or more users with same name                   
                }catch (Exception ex ){
                    ex.printStackTrace();
                }
           
            if(!check)//if name is valid one then end the login window and call the connect methood of chatwindow.java
            {
            setVisible(false);
            chatwindow c1 = new chatwindow();
            c1.connect(server, port, user);//here is the call
            }
            else//if the username is already there this happens
            {
                JOptionPane.showMessageDialog(null, "USERNAME ALREADY THERE PLEASE TRY A DIFFERENT USERNAME");
                txtuser.setText("");
            }
            }
        }//end of action performed method
    }//end of connectlistner class
}//end of class loginwindow
