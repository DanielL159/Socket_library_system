package main;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class Livros implements Serializable {
    
    public static JSONArray books = new JSONArray();
    private static final long serialVersionUID = 1L;
    private String autor;
    private String nome;
    private String genero;
    private Integer quantidade;
    private Integer quantMax;

    // static methods
    public static void parseLivro(JSONObject livro, List<Livros> livros) {
        String nome = (String) livro.get("titulo");
        String autor = (String) livro.get("autor");
        String genero = (String) livro.get("genero");
        Integer quantidade = ((Long) livro.get("exemplares")).intValue();
        
        Livros book = new Livros(autor,nome,genero,quantidade);
        livros.add(book);
    }
    
    public static JSONArray jsonizeList(List<Livros> livros) {
        JSONArray jsonArray = new JSONArray();
        livros.forEach(livro -> Livros.jsonizeElement((Livros) livro, jsonArray));
        return jsonArray;
    }
    

	public static void jsonizeElement(Livros livro, JSONArray jsonArray) {
        jsonArray.add(livro.toJson());
    }
    
    // Construtores, getters e setters
    public Livros(String autor, String nome, String genero, Integer quantidade) {
        this.autor = autor;
        this.nome = nome;
        this.genero = genero;
        this.quantidade = quantidade;
        this.quantMax = quantidade;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    public int getQuantMax() {
        return quantMax;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("""
                Autor: %s
                Nome: %s
                Gênero: %s
                Número de Exemplares: %d
                """, autor, nome, genero, quantidade);
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("titulo", nome);
        json.put("autor",autor);
        json.put("genero", genero);
        json.put("exemplares",quantidade);
        return json;
    }
}