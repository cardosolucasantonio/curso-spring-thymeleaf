package com.lucas.curso.boot.dao;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.stereotype.Repository;
import com.lucas.curso.boot.domain.Cargo;

import java.util.List;

import static com.lucas.curso.boot.util.PaginacaoUtil.registrosPorPagina;

@Repository
public class CargoDaoImpl extends AbstractDao<Cargo, Long> implements CargoDao {

    public PaginacaoUtil<Cargo> buscaPaginada(int pagina, String ordenacao) {
        final int inicio = (pagina - 1) * registrosPorPagina;

        final StringBuilder jpql = new StringBuilder("SELECT c FROM Cargo c ORDER BY c.nome ");
        jpql.append(ordenacao);

        List<Cargo> cargos = getEntityManager().createQuery(jpql.toString(), Cargo.class)
                .setFirstResult(inicio)
                .setMaxResults(registrosPorPagina)
                .getResultList();

        final long totalRegistros = count();
        final long totalPaginas = (totalRegistros + (registrosPorPagina - 1)) / registrosPorPagina;

        return new PaginacaoUtil<>(registrosPorPagina, pagina, totalPaginas, ordenacao, cargos);
    }

    public long count() {
        return getEntityManager()
                .createQuery("SELECT COUNT(*) FROM Cargo", Long.class)
                .getSingleResult();
    }

}
