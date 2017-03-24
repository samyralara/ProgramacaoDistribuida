import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Servidor extends Thread {
    // socket deste cliente
    private Socket conexao;
    private String nomeCliente;
    // lista que armazena nome de clientes
	private static List<String> usuariosLista = new ArrayList<String>();
	//map que guarda todos os clientes, que guarda os conectados e seu printStream
	private static Map<String, PrintStream> clientesMap;

    
 
    public Servidor(Socket socket) {
        this.conexao = socket;
    }
 
    public boolean armazena(String newName) {
        for (int i = 0; i < usuariosLista.size(); i++) {
            if (usuariosLista.get(i).equals(newName))
                return false;
        }
        //adiciona na lista apenas se não existir o nome
        usuariosLista.add(newName);
        return true;
    }
    //remove da lista os clientes que já deixaram o chat
	public void remove(String oldName) {
        for (int i = 0; i < usuariosLista.size(); i++) {
            if (usuariosLista.get(i).equals(oldName))
                usuariosLista.remove(oldName);
        }
    }
	
	//muda o nome de um cliente do chat
	public void rename(PrintStream saida, String[] msg) {
        String novoNome = msg[1];
        String nomeAntigo = nomeCliente;
        if (armazena(novoNome)) {
        	clientesMap.remove(this.nomeCliente);
            remove(this.nomeCliente);
            nomeCliente = novoNome;
            clientesMap.put(novoNome, saida);
            if (clientesMap != null && !clientesMap.isEmpty()) {
                for (Map.Entry<String, PrintStream> cliente : clientesMap.entrySet()) {
                    PrintStream chat = cliente.getValue();
                    if (saida != chat) {
                        chat.println(nomeAntigo.toUpperCase() + " alterou seu nome para: " + novoNome.toUpperCase());
                    }
                }
            }
            saida.println("Nome de Usuário alterado com sucesso!");
 
       } else {
        	saida.println("Usuário já cadastrado, tente novamente!");
       		}
    }
	
	public void send(PrintStream saida, String[] msg) {
        int count = 1;
        String datahora = TimeUtil.getDateTime();
        
        
        for (Map.Entry<String, PrintStream> cliente : clientesMap.entrySet()) {
            PrintStream chat = cliente.getValue();
            
            /**
             * Se o array da msg tiver tamanho igual a 1, então envia para todos
             * Se o tamanho for 2, envia apenas para o cliente escolhido
             */
            
            if(chat != saida){
                if(msg.length >= 3 && msg[1].equals("-all")){
                    String mensagem = "";
                    for (int i = 2; i < msg.length; i++) {
                        mensagem += (msg[i]+" ");
                    }
                    chat.println(this.conexao.getInetAddress() + ":" + this.conexao.getPort() + "/~"+ this.nomeCliente + ": " +mensagem+ " "+ datahora);
                    
                } else if(msg.length >= 4 && msg[1].equals("-user")) {
                    
                	if (msg[2].equalsIgnoreCase(cliente.getKey())) {
                        String mensagem = "";
                        for (int i = 3; i < msg.length; i++) {
                            mensagem += (msg[i]+" ");
                        }
                        chat.println(this.conexao.getInetAddress() + ":" + this.conexao.getPort() + "/~"+ this.nomeCliente + ": " +mensagem+ " "+ datahora);
                        break;
                        
                    }else {
                        count++;
                    }
                	
                } else {
                    saida.println("Comando inválido!");
                }
            }
        }  
        
        if(count == clientesMap.size()) {
            saida.println("O usuário "+msg[2]+" não existe!");
        }
    }

 
    
 
    public static void main(String args[]) {
        // instancia o vetor de clientes conectados
        clientesMap = new HashMap<String, PrintStream>();
        try {
            // cria um socket que fica escutando a porta 5555.
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Servidor UP " + TimeUtil.getDateTime());
            while (true) {
            	// aguarda algum cliente se conectar.
                // A execução do servidor fica bloqueada na chamada do método accept da
                // classe ServerSocket até que algum cliente se conecte ao servidor.
                // O próprio método desbloqueia e retorna com um objeto da classe Socket
                Socket conexao = server.accept();
                // cria uma nova thread para tratar essa conexão
                Thread listener = new Servidor(conexao);
                listener.start();
                // voltando ao loop, esperando mais alguém se conectar.

            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
 
    // execução da thread
    public void run(){
		
		try {
            // objetos que permitem controlar fluxo de comunição que vem do cliente
			BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
			PrintStream saida = new PrintStream(this.conexao.getOutputStream());
            // recebe o nome do cliente
			this.nomeCliente = entrada.readLine();
            //chamada ao metodo que testa nomes iguais

			
			if (armazena(this.nomeCliente)) {
				System.out.println(this.conexao.getInetAddress() + ":" + this.conexao.getPort() + "/~" + this.nomeCliente + ": conectado ao bate-papo!");
				//adiciona os dados de saida do cliente no objeto clientesMap
	            //A chave será o nome e valor o printstream
				clientesMap.put(this.nomeCliente, saida);
				
				if (clientesMap != null && !clientesMap.isEmpty()) {
					saida.println(" seja bem-vindo "+ nomeCliente.toUpperCase());
					
					for (Map.Entry<String, PrintStream> cliente : clientesMap.entrySet()) {
						PrintStream chat = cliente.getValue(); 								// verificar isso
						
						if (chat != saida) {
							chat.println(nomeCliente.toUpperCase() + " conectou ao chat!");
						}
					}
				}
		                
			} else {
				saida.println("Este nome já existe! Tente novamente.");
				this.conexao.close();
				return;
		                
			}
		 
			if (this.nomeCliente == null) {
				return;
			}
		 
			String msg = entrada.readLine();
		 
			while (msg != null && !(msg.trim().equals(""))) {
				String[] cmd = msg.split(" ");
		                
				if(msg.equals("bye")){
					System.out.println(this.conexao.getInetAddress() + ":" + this.conexao.getPort() + "/~"+ this.nomeCliente +" saiu do bate-papo!");
					
					for (Map.Entry<String, PrintStream> cliente : clientesMap.entrySet()) {
						PrintStream chat = cliente.getValue();
		                        
						if (chat != saida) {
							chat.println(nomeCliente.toUpperCase() + " saiu do bate-papo!");
						}
					}
		                    
					remove(this.nomeCliente);
					clientesMap.remove(this.nomeCliente);
					this.conexao.close();
		                    
				}else if(msg.equals("list")){
					saida.println("Conectados: " + usuariosLista.toString());
		                    
				}else{
					switch(cmd[0].trim()){
					case "send":
						send(saida, cmd);
						break;
			                    
					case "rename":
						if (cmd.length == 2) {
							rename(saida, cmd);
						} else {
							saida.println("Comando inválido!");
						}
						break;
						
					default:
						saida.println("Comando inválido!");
					}
				}
		 
				msg = entrada.readLine();
			}
		 
		} catch (IOException e) {
			System.out.println("");
		}
	}

       
    
         
}