package com.lucas.curso.boot.dao;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.stereotype.Repository;

import com.lucas.curso.boot.domain.Departamento;

import java.util.List;

import static com.lucas.curso.boot.util.PaginacaoUtil.registrosPorPagina;

@Repository
public class DepartamentoDaoImpl extends AbstractDao<Departamento, Long> implements DepartamentoDao {

    public PaginacaoUtil<Departamento> buscaPaginada(int pagina, String ordenacao) {
        final int inicio = (pagina - 1) * registrosPorPagina;

        final StringBuilder jpql = new StringBuilder("SELECT d FROM Departamento d ORDER BY d.nome ");
        jpql.append(ordenacao);

        List<Departamento> departamentos = getEntityManager().createQuery(jpql.toString(), Departamento.class)
                .setFirstResult(inicio)
                .setMaxResults(registrosPorPagina)
                .getResultList();

        final long totalRegistros = count();
        final long totalPaginas = (totalRegistros + (registrosPorPagina - 1)) / registrosPorPagina;

        return new PaginacaoUtil<>(registrosPorPagina, pagina, totalPaginas, ordenacao, departamentos);
    }

    public long count() {
        return getEntityManager()
                .createQuery("SELECT COUNT(*) FROM Departamento", Long.class)
                .getSingleResult();
    }

}
