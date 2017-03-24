import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ListenerCliente extends Thread{

	Socket conexao;
	public ListenerCliente(Socket socket) {
		this.conexao = socket;
	}
	
	public void run(){
		try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            
            String msg;
            
            while (true){
                msg = entrada.readLine();
                if (msg == null) {
                    System.out.println("Conexão encerrada com o bate-papo!");
                    System.exit(0);
                }
                System.out.println(msg);
                
                System.out.print("> ");
            }
            
        } catch (IOException e) {
            System.out.println("Ocorreu uma falha... .. ." + " IOException: " + e);
        }
    }

}
