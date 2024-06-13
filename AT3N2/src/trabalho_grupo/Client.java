package trabalho_grupo;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Client {
	private static final String HOST_STRING = "localhost";
	private static final Integer NUMERO_PORTA = 12345;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		boolean rodando = true;

		/*
		 * esse e um try que fecha automaticamente todos os objetos que sao declarados
		 * nos locais das variaveis
		 */
		try (Socket conexao = new Socket(HOST_STRING, NUMERO_PORTA);
				ObjectOutputStream ObjetoDeSaida = new ObjectOutputStream(conexao.getOutputStream());
				ObjectInputStream ObjetoDeEntrada = new ObjectInputStream(
						new BufferedInputStream(conexao.getInputStream()));
				Scanner sc = new Scanner(System.in)) {

			while (rodando) {
				System.out.println("""
						1. Listar livros
						2. Cadastrar livro
						3. Alugar livro
						4. Devolver livro
						5. Sair
						Escolha uma opção:
						""");

				int opcao = sc.nextInt()
						;
				while(opcao <1 || opcao > 5) {
					System.out.println("""
							Opçao invalida
							1. Listar livros
							2. Cadastrar livro
							3. Alugar livro
							4. Devolver livro
							5. Sair
							Escolha uma opção:
							""");
					opcao = sc.nextInt();
				}
				sc.nextLine();
				

				switch (opcao) {
				case 1:
					Listar(ObjetoDeSaida, ObjetoDeEntrada);
					break;
				case 2:
					Cadastrar(ObjetoDeSaida, ObjetoDeEntrada, sc);
					break;
				case 3:
					Alugar(ObjetoDeSaida, ObjetoDeEntrada, sc);
					break;
				case 4:
					Devolver(ObjetoDeSaida, ObjetoDeEntrada, sc);
					break;
				case 5:
					System.out.println("Encerrando o cliente.");
					ObjetoDeSaida.writeObject("SAIR");
					rodando = false;
					break;
				default:
					System.out.println("Opção inválida.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rodando = false;
		}
	}

	private static void Listar(ObjectOutputStream ObjetoDeSaida, ObjectInputStream ObjetoDeEntrada) {
		try {
			ObjetoDeSaida.writeObject("LISTAR");

			@SuppressWarnings("unchecked")
			List<Livros> livros = (List<Livros>) ObjetoDeEntrada.readObject();
			for (Livros livro : livros) {
				System.out.println(livro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void Cadastrar(ObjectOutputStream ObjetoDeSaida, ObjectInputStream ObjetoDeEntrada,
			Scanner scanner) {
		try {
			System.out.print("Autor: ");
			String autor = scanner.nextLine();
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("Gênero: ");
			String genero = scanner.nextLine();
			System.out.print("Número de exemplares: ");
			int numeroExemplares = scanner.nextInt();
			scanner.nextLine(); // Consumir nova linha

			Livros livroNovo = new Livros(autor, nome, genero, numeroExemplares);
			ObjetoDeSaida.writeObject("CADASTRAR");
			ObjetoDeSaida.writeObject(livroNovo);
			System.out.println(ObjetoDeEntrada.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void Alugar(ObjectOutputStream ObjetoDeSaida, ObjectInputStream ObjetoDeEntrada, Scanner sc) {
		try {
			System.out.print("Nome do livro a alugar: ");
			String nomeLivroAlugar = sc.nextLine();
			ObjetoDeSaida.writeObject("ALUGAR");
			ObjetoDeSaida.writeObject(nomeLivroAlugar);
			System.out.println(ObjetoDeEntrada.readObject());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void Devolver(ObjectOutputStream ObjetoDeSaida, ObjectInputStream ObjetoDeEntrada, Scanner sc) {
		try {
			System.out.print("Nome do livro a devolver: ");
			String nomeLivroDevolver = sc.nextLine();
			ObjetoDeSaida.writeObject("DEVOLVER");
			ObjetoDeSaida.writeObject(nomeLivroDevolver);
			System.out.println(ObjetoDeEntrada.readObject());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
