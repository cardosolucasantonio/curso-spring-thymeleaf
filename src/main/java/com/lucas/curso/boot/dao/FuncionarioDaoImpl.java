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
    public PaginacaoUtil<Funcionario> findByPeriodo(int pagina, String ordenacao, LocalDate entrada, LocalDate saida) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataEntrada BETWEEN :entradaParam AND :saidaParam ");
        jpql.append("ORDER BY dataEntrada ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .setParameter("entradaParam", entrada)
                .setParameter("saidaParam", saida)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
    }

    @Override
    public PaginacaoUtil<Funcionario> findByDataEntrada(int pagina, String ordenacao, LocalDate entrada) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataEntrada = :entradaParam ");
        jpql.append("ORDER BY dataEntrada ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .setParameter("entradaParam", entrada)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
    }

    @Override
    public PaginacaoUtil<Funcionario> findByDataSaida(int pagina, String ordenacao, LocalDate saida) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT f FROM Funcionario f ");
        jpql.append("WHERE f.dataSaida = :saidaParam ");
        jpql.append("ORDER BY dataSaida ");
        jpql.append(ordenacao);

        List<Funcionario> funcionarios = getEntityManager().createQuery(jpql.toString(), Funcionario.class)
                .setFirstResult(getFirstResult(pagina))
                .setMaxResults(registrosPorPagina)
                .setParameter("saidaParam", saida)
                .getResultList();

        return new PaginacaoUtil<>(registrosPorPagina, pagina, getTotalPaginas(), ordenacao, funcionarios);
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
