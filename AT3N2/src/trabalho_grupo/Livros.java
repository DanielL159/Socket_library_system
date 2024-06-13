package trabalho_grupo;

import java.io.Serializable;

public class Livros implements Serializable {
   
	private static final long serialVersionUID = 1L;
	private String autor;
    private String nome;
    private String genero;
    private Integer quantidade;

    // Construtores, getters e setters
    public Livros(String autor, String nome, String genero, Integer quantidade) {
        this.autor = autor;
        this.nome = nome;
        this.genero = genero;
        this.quantidade = quantidade;
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
}
