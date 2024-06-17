package main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.ServerSocket; // Um ServerSocket aguarda a chegada de solicitações de conexão 
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.reflect.TypeToken;

@SuppressWarnings("unchecked")
public class ServidorBiblioteca {
    // Define the JSON_FILE variable
    private static final String JSON_FILE = "./livros.json";
    
    private static JSONParser parser = new JSONParser();
    static Type listType= null ;

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
        	 try {
				listType = new TypeToken<ArrayList<Livros>>() {}.getType();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        }
    }


    private static List<Livros> readBooksFromJson() {
        List<Livros> refinedList = new ArrayList<>();
        
        try (Reader reader = new FileReader(JSON_FILE)) {
            Object obj = parser.parse(reader);
            
            JSONArray bruteList = (JSONArray) obj;
            
            bruteList.forEach(livro -> {
                try {
                    Livros.parseLivro((JSONObject) livro, refinedList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return refinedList;
    }

    private static void writeBooksToJson(List<Livros> livros) {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            
            writer.write(Livros.jsonizeList(livros).toJSONString());
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        /*
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(livros, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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
