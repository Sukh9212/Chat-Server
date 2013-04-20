import java.rmi.*;
import java.util.*;
public interface MyRemote extends Remote 
{

   public  Vector<String> usernamelist()throws RemoteException;
   public String sayhello()throws RemoteException;
   public void rmvusr(String rmv)throws RemoteException;
   public void ulen(int aa,int bb) throws RemoteException;
   
}

