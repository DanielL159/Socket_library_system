package trabalho_grupo; 
import java.net.ServerSocket; // Um ServerSocket aguarda a chegada de solicitações de conexão 
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;      
public class Server {
    

    
    public static void main (String [] args) throws ClassNotFoundException{
        String mensagem_enviado_ao_Cliente = "Aguardando conexão com cliente";
        try(ServerSocket servidor = new ServerSocket(12345)){
    
                Socket socket = servidor.accept();
                System.out.println("Conexão com o cliente de IP: " + socket.getInetAddress().getHostAddress() + " e Porta " + socket.getPort());

                // criação dos streams de entrada e saida
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                saida.writeObject(mensagem_enviado_ao_Cliente);

                 boolean rodando = true;

                  while(rodando){
                    String comando = (String) entrada.readObject();
                    System.out.println("O cliente escolheu: "+ comando);
                   

                   switch (comando.toString()) {
                    case "LISTAR":
                    // criar uma logica para pegar as infos do livro no arquivo json e mandar em formato de lista para o cliente
                        
                        break;
                    case "CADASTRAR":

                        break;
                    case "ALUGAR":

                        break;
                    
                    case "DEVOLVER":

                        break;
                    case "SAIR":
                    System.out.println("Encerrando conexão");
                       rodando = false;

                        break;
                    default:
                    System.out.println("Comando inválido");
                            
                   }
                   
                    
    
                   }

             
    

        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

}
