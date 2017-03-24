package br.pd;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;





public class ChatServer extends UnicastRemoteObject implements ServerIF{
	HashMap<String, UserIF> users;
	
	protected ChatServer() throws RemoteException {
		this.users = new HashMap<String, UserIF>();
	}

	@Override
	public void register(UserIF user) throws RemoteException, Exception {
		if (!users.containsKey(user.getName())){
			users.put(user.getName(), user);
			sendAll(user.getName(), "entrou no grupo.");
		} else {
			System.err.println("Usuário informado já existe.");
			throw new Exception("Usuário informado já existe.");
			//sendTo(user.getName(), user.getName(), "Usuário informado já existe.");
			
		}
		
	}

	@Override
	public void unRegister(UserIF user) throws RemoteException {
		users.remove(user.getName());
		sendAll(user.getName(), "saiu do grupo.");
		
	}

	@Override
	public boolean rename(UserIF user, String newName) throws RemoteException {
		if(!users.containsKey(newName)){
			//unRegister(user);
			users.put(newName, user);
			users.remove(user.getName());
			sendAll(user.getName(), "mudou o seu nome de usuário. Novo nome: '" + newName + "'.");
			return true;
		} else {
			//sendTo(user.getName(), user.getName(), "Nome de usuári já existe");
			users.get(user.getName()).receive("Nome de usuári já existe");
			return false;
		}
		
	}
	
	@Override
	public List<String> list(UserIF user) throws RemoteException {
		List<String> list = new ArrayList<String>(users.keySet());
		return list;
	}
	
	@Override
	public void sendAll(String sender, String message) throws RemoteException {
		String text = new String(sender + ": " + message+ " - " + TimeUtil.getDateTime());
		for(String userName : users.keySet()){
			if(!userName.equals(sender)){
				users.get(userName).receive(text);
			}
		}
		
	}

	@Override
	public void sendTo(String sender, String user, String message) throws RemoteException {
		if(users.containsKey(user)){
			if (message != null){
				String text = sender + " (direct): " + message + " - " + TimeUtil.getDateTime();
				users.get(user).receive(text);
			}
		} else {
			System.err.println("O usuário não existe.");
			users.get(sender).receive("O usuário não existe.");
		}
		
	}


	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Naming.bind("Server", new ChatServer());
			System.out.println("Servidor UP " + TimeUtil.getDateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}
