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

    List<Funcionario> findByPeriodo(LocalDate entrada, LocalDate saida);

    List<Funcionario> findByDataEntrada(LocalDate entrada);

    List<Funcionario> findByDataSaida(LocalDate saida);

    PaginacaoUtil<Funcionario> buscaPaginada(int pagina, String ordenacao);
}
