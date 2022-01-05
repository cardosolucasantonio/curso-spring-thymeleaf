package com.lucas.curso.boot.dao;

import java.time.LocalDate;
import java.util.List;

import com.lucas.curso.boot.domain.Funcionario;
import com.lucas.curso.boot.util.PaginacaoUtil;

public interface FuncionarioDao {
	
	void save(Funcionario funcionario);
	
	void update(Funcionario funcionario);
	
	void delete(Long id);
	
	Funcionario findById(Long id);
	
	List<Funcionario> findAll();

    PaginacaoUtil<Funcionario> findByName(int pagina, String ordenacao, String nome);

    PaginacaoUtil<Funcionario> findByCargo(int pagina, String ordenacao, Long id);

    PaginacaoUtil<Funcionario> findByPeriodo(int pagina, String ordenacao, LocalDate entrada, LocalDate saida);

    PaginacaoUtil<Funcionario> findByDataEntrada(int pagina, String ordenacao, LocalDate entrada);

    PaginacaoUtil<Funcionario> findByDataSaida(int pagina, String ordenacao, LocalDate saida);

    PaginacaoUtil<Funcionario> buscaPaginada(int pagina, String ordenacao);
}
