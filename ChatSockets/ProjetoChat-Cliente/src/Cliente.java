import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente{
	static Scanner kB;
	private Socket conexao;
	
	public Cliente(Socket socket) {
        this.conexao = socket;
    }
	
	public static void main(String[] args) {
		kB = new Scanner(System.in);
		try {
			Socket s = new Socket("127.0.0.1", 5555);
			PrintStream saida = new PrintStream(s.getOutputStream());
			BufferedReader teclado = 
					new BufferedReader(new InputStreamReader(System.in));
			System.out.print("> Digite seu nome: ");
            String nome = teclado.readLine();
            saida.println(nome.toUpperCase());
	
            Thread listener  = new ListenerCliente(s);
			listener.start();
			
			String msg = null;
            while (true){
            	System.out.print("> ");
                msg = teclado.readLine();
                saida.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Falha na Conexão... .. ." + " IOException: " + e);
        }
    }
}
