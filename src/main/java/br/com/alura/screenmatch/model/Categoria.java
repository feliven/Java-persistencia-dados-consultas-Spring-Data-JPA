package br.com.alura.screenmatch.model;

import java.util.ArrayList;
import java.util.List;

public enum Categoria {
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    MISTERIO("Mystery", "Mistério"),
    ACAO("Action", "Ação"),
    AVENTURA("Adventure", "Aventura"),
    SUSPENSE("Thriller", "Suspense"),
    TERROR("Horror", "Terror"),
    FICCAO_CIENTIFICA("Sci-Fi", "Ficção científica"),
    DOCUMENTARIO("Documentary", "Documentário"),
    REALITY("Reality-TV", "Reality"),
    CRIME("Crime", "Crime"),
    GAME_SHOW("Game-Show", "Game show"),
    PARA_A_FAMILIA("Family", "Família");

    private String categoriaOmdb;
    private String categoriaEmPortugues;

    Categoria(String categoriaOmdb, String categoriaEmPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEmPortugues = categoriaEmPortugues;
    }

    public String getCategoriaEmPortugues() {
        return categoriaEmPortugues;
    }

    public static List<Categoria> fromStringArray(String[] arrayCategorias) {
        var listaCategorias = new ArrayList<Categoria>();

        for (Categoria categoria : Categoria.values()) {
            for (String c : arrayCategorias) {
                if (categoria.categoriaOmdb.equalsIgnoreCase(c.trim())) {
                    listaCategorias.add(categoria);
                }
            }
        }
        return listaCategorias;
    }

    public static Categoria fromPortugues(String textoEmPortugues) {

        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEmPortugues.equalsIgnoreCase(textoEmPortugues.trim())) {
                return categoria;
            }
        }

        throw new IllegalArgumentException("Nenhuma categoria encontrada para " + textoEmPortugues);

    }

    public static void exibirCategoriasEmPortugues() {
        List<String> listaEmPortugues = new ArrayList<String>();

        for (Categoria c : Categoria.values()) {
            listaEmPortugues.add(c.getCategoriaEmPortugues());
        }

        System.out.println(listaEmPortugues);
    }
}
