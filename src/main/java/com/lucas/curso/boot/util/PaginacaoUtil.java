package com.lucas.curso.boot.util;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoUtil<T> {

    public static final int registrosPorPagina = 5;

    private int quantidade;
    private int pagina;
    private long totalPaginas;
    private String ordenacao;
    private List<T> registros;

}
