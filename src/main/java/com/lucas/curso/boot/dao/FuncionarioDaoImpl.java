package com.lucas.curso.boot.dao;

import com.lucas.curso.boot.util.PaginacaoUtil;
import com.lucas.curso.boot.domain.Funcionario;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.lucas.curso.boot.util.PaginacaoUtil.registrosPorPagina;

@Repository	
public class FuncionarioDaoImpl extends AbstractDao<Funcionario, Long> implements FuncionarioDao {

    @Override
    public PaginacaoUtil<Funcionario> findByName(int pagina, String ordenacao, String nome) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT f FROM Funcionario f WHERE f.nome LIKE CONCAT('%', :nomeParam, '%') ");
        jpql.append("ORDER BY f.nome ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .setParameter("nomeParam", nome)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
    }

    @Override
    public PaginacaoUtil<Funcionario> findByCargo(int pagina, java.lang.String ordenacao, Long id) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f WHERE f.cargo.id = :idParam ");
        jpql.append("ORDER BY f.cargo.nome ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .setParameter("idParam", id)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
    }

    @Override
    public List<Funcionario> findByPeriodo(LocalDate entrada, LocalDate saida) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataEntrada BETWEEN ?1 AND ?2 ");
        jpql.append("ORDER BY dataEntrada");

        return createQuery(jpql.toString(), entrada, saida);
    }

    @Override
    public List<Funcionario> findByDataEntrada(LocalDate entrada) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataEntrada = ?1 ");
        jpql.append("ORDER BY dataEntrada");

        return createQuery(jpql.toString(), entrada);
    }

    @Override
    public List<Funcionario> findByDataSaida(LocalDate saida) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataSaida = ?1 ");
        jpql.append("ORDER BY dataSaida");

        return createQuery(jpql.toString(), saida);
    }

    @Override
    public PaginacaoUtil<Funcionario> buscaPaginada(int pagina, String ordenacao) {
        final StringBuilder jpql = new StringBuilder("SELECT f FROM Funcionario f ORDER BY f.nome ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
    }

    private long getTotalRegistros() {
        return count();
    }

    public long count() {
        return getEntityManager()
                .createQuery("SELECT COUNT(*) FROM Funcionario", Long.class)
                .getSingleResult();
    }

    private long getTotalPaginas() {
        return (getTotalRegistros() + (registrosPorPagina - 1)) / registrosPorPagina;
    }

    private int getFirstResult(int pagina) {
        return (pagina - 1) * registrosPorPagina;
    }

}
