package trabalho_grupo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Client {
    private static final String HOST_STRING = "localhost";
    private static final Integer NUMERO_PORTA = 12345;
    
    

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        
        /*esse e um try que fecha automaticamente todos os objetos que sao declarados nos locais das variaveis*/
        try (Socket conexao = new Socket(HOST_STRING, NUMERO_PORTA);
             ObjectOutputStream ObjetoDeSaida = new ObjectOutputStream(conexao.getOutputStream());
             ObjectInputStream ObjetoDeEntrada = new ObjectInputStream(new BufferedInputStream(conexao.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("""	
                	 1. Listar livros
                	 2. Cadastrar livro
                	 3. Alugar livro
                	 4. Devolver livro
                	 5. Sair
                	 Escolha uma opção:
                	 """);
              
                int opcao = scanner.nextInt();
                scanner.nextLine();  

                switch (opcao) {
                case 1:
                	ObjetoDeSaida.writeObject("LISTAR");
                    List<Livros> livros = (List<Livros>) ObjetoDeEntrada.readObject();
                    for (Livros livro : livros) {
                        System.out.println(livro);
                    }
                    break;
                    case 2:
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Gênero: ");
                        String genero = scanner.nextLine();
                        System.out.print("Número de exemplares: ");
                        int numeroExemplares = scanner.nextInt();
                        scanner.nextLine();  // Consumir nova linha

                        Livros livroNovo = new Livros(autor, nome, genero, numeroExemplares);
                        ObjetoDeSaida.writeObject("CADASTRAR");
                        ObjetoDeSaida.writeObject(livroNovo);
                        System.out.println(ObjetoDeEntrada.readObject());
                        break;
                    case 3:
                        System.out.print("Nome do livro a alugar: ");
                        String nomeLivroAlugar = scanner.nextLine();
                        ObjetoDeSaida.writeObject("ALUGAR");
                        ObjetoDeSaida.writeObject(nomeLivroAlugar);
                        System.out.println(ObjetoDeEntrada.readObject());
                        break;
                    case 4:
                        System.out.print("Nome do livro a devolver: ");
                        String nomeLivroDevolver = scanner.nextLine();
                        ObjetoDeSaida.writeObject("DEVOLVER");
                        ObjetoDeSaida.writeObject(nomeLivroDevolver);
                        System.out.println(ObjetoDeEntrada.readObject());
                        break;
                    case 5:
                        System.out.println("Encerrando o cliente.");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
