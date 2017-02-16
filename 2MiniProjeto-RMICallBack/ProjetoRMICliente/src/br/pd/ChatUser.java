package br.pd;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

import br.pd.*;


public class ChatUser extends UnicastRemoteObject implements UserIF{

	private static final long serialVersionUID = 1L;
	protected String name;
	protected ServerIF server;
	private static Scanner kb = new Scanner(System.in);
	

	protected ChatUser(String name) throws Exception {
		try {
			this.server = (ServerIF) Naming.lookup("rmi://localhost:1099/Server");
			this.name = name;
			this.server.register(this);
			
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	@Override
	public void receive(String message) throws RemoteException {
		System.out.println(message);
	}

	private static void menuPrincipal(){
		System.out.println("Escolha uma das opções");
		System.out.println("1 - Enviar no grupo");
		System.out.println("2 - Enviar direct");
		System.out.println("3 - Listar usuários");
		System.out.println("4 - Mudar nome de usuario");
		System.out.println("5 - Sair do chat\n");
	}
	
	public static void main(String[] args) {
				
		ChatUser user = null;
		boolean conect = true;
		while (conect){
			System.out.println("Informe seu nome: ");
			String nome = kb.nextLine();
		
			try {
				try {
					user = new ChatUser(nome);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Bem vindo " + user.getName()+ "\n");
				conect = false;
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		boolean exit = false;
		while (!exit){
			menuPrincipal();
			int opcao = kb.nextInt();
			switch(opcao){
				case 1:
					user.sendAll();
					break;
				case 2:
					user.sendTo();
					break;
				case 3:
					user.list();
					break;
				case 4:
					user.rename();
					break;
				case 5:
					exit = true;
					break;
				default:
					 System.err.println("O comando informado é inválido\n");
					break;
			}
		}
		try {
			user.server.unRegister(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	private void list(){
		try {
			System.out.println("Listando Usuarios:");
			List<String> list = this.server.list(this);
			for(String s : list){
				System.out.println( s );
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void rename(){
		Scanner in = new Scanner(System.in);
		String newName;
		while(true){
			System.out.println("Informe o seu novo nome de usuario:");
			newName = in.nextLine();
			try {
				if (this.server.rename(this, newName)) {
					this.name = newName;
					break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void sendAll(){
		Scanner in = new Scanner(System.in);
		String string;
		
		System.out.println("Mensagem:");
		string = in.nextLine();
				
		try {
			this.server.sendAll(this.name, string);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	private void sendTo(){
		Scanner in = new Scanner(System.in);
		String destinatary = "";
			
		System.out.println("Informe o nome do usuario:");
		destinatary = in.nextLine();
		
		String message;
		System.out.println("Direct para '" + destinatary + "':");
		message = in.nextLine();
		try {
			this.server.sendTo(this.name, destinatary, message);
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
		
	}
}
