package trabalho_grupo;

import java.net.ServerSocket; // Um ServerSocket aguarda a chegada de solicitações de conexão 
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Server {
    // Define the JSON_FILE variable
    private static final String JSON_FILE = "Socket_library_system/AT3N2/src/trabalho_grupo/livros.json";

    // Create a Gson object
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws ClassNotFoundException {
        String mensagem_enviado_ao_Cliente = "Aguardando conexão com cliente";
        try (ServerSocket servidor = new ServerSocket(12345)) {

            Socket socket = servidor.accept();
            System.out.println(
                    "Conexão com o cliente de IP: " + socket.getInetAddress().getHostAddress() + " e Porta "
                            + socket.getPort());

            // criação dos streams de entrada e saida
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            saida.writeObject(mensagem_enviado_ao_Cliente);

            boolean rodando = true;

            while (rodando) {
                String comando = (String) entrada.readObject();
                System.out.println("O cliente escolheu: " + comando);

                switch (comando) {
                    case "LISTAR":
                        List<Livros> livros = readBooksFromJson();
                        saida.writeObject(livros);
                        break;
                    case "CADASTRAR":
                        Livros novoLivro = (Livros) entrada.readObject();
                        cadastrarLivro(novoLivro);
                        saida.writeObject("Livro cadastrado com sucesso.");
                        break;
                    case "ALUGAR":
                        String nomeLivroAlugar = (String) entrada.readObject();
                        String mensagemAlugar = alugarLivro(nomeLivroAlugar);
                        saida.writeObject(mensagemAlugar);
                        break;
                    case "DEVOLVER":
                        String nomeLivroDevolver = (String) entrada.readObject();
                        String mensagemDevolver = devolverLivro(nomeLivroDevolver);
                        saida.writeObject(mensagemDevolver);
                        break;
                    case "SAIR":
                        System.out.println("Encerrando conexão");
                        rodando = false;
                        break;
                    default:
                        System.out.println("Comando inválido");
                        break;
                }
            }
        } catch (IOException e) {
            Type listType = new TypeToken<ArrayList<Livros>>() {
            }.getType();
        }
    }

    private static List<Livros> readBooksFromJson() {
        try (Reader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<ArrayList<Livros>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void writeBooksToJson(List<Livros> livros) {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(livros, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarLivro(Livros livro) {
        List<Livros> livros = readBooksFromJson();
        livros.add(livro);
        writeBooksToJson(livros);
    }

    private static String alugarLivro(String nome) {
        List<Livros> livros = readBooksFromJson();
        for (Livros livro : livros) {
            if (livro.getNome().equalsIgnoreCase(nome)) {
                if (livro.getQuantidade() > 0) {
                    livro.setQuantidade(livro.getQuantidade() - 1);
                    writeBooksToJson(livros);
                    return "Livro alugado com sucesso.";
                } else {
                    return "Livro não disponível para aluguel.";
                }
            }
        }
        return "Livro não encontrado.";
    }

    private static String devolverLivro(String nome) {
        List<Livros> livros = readBooksFromJson();
        for (Livros livro : livros) {
            if (livro.getNome().equalsIgnoreCase(nome)) {
                livro.setQuantidade(livro.getQuantidade() + 1);
                writeBooksToJson(livros);
                return "Livro devolvido com sucesso.";
            }
        }
        return "Livro não encontrado.";
    }
}
