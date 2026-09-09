package br.com.alura.screenmatch.model;

import java.util.ArrayList;
import java.util.List;

public enum Categoria {
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    MISTERIO("Mystery"),
    ACAO("Action"),
    AVENTURA("Adventure"),
    SUSPENSE("Thriller"),
    TERROR("Horror"),
    FICCAO_CIENTIFICA("Sci-Fi"),
    DOCUMENTARIO("Documentary"),
    REALITY("Reality-TV"),
    CRIME("Crime"),
    GAME_SHOW("Game-Show"),
    PARA_A_FAMILIA("Family");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
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
}
