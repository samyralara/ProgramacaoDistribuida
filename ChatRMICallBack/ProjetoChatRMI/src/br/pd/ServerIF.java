package br.pd;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;




public interface ServerIF extends Remote{
	
	public void register(UserIF user) throws RemoteException, Exception;
	public void unRegister(UserIF user) throws RemoteException;
	public void sendAll(String sender, String message) throws RemoteException;
	public void sendTo(String sender, String user, String message) throws RemoteException;
	public boolean rename(UserIF user, String newName) throws RemoteException;
	public List<String> list(UserIF user) throws RemoteException;


}
