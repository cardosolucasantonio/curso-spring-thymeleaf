package com.lucas.curso.boot.service;

import java.time.LocalDate;
import java.util.List;

import com.lucas.curso.boot.domain.Funcionario;
import com.lucas.curso.boot.util.PaginacaoUtil;

public interface FuncionarioService {
	
	void save(Funcionario funcionario);
	
	void edit(Funcionario funcionario);
	
	void delete(Long id);
	
	Funcionario findById(Long id);
	
	List<Funcionario> findAll();

    PaginacaoUtil<Funcionario> findByNome(int pagina, String ordenacao, String nome);

    PaginacaoUtil<Funcionario> findByCargo(int pagina, String ordenacao, Long id);

    PaginacaoUtil<Funcionario> findByDatas(int pagina, String ordenacao, LocalDate entrada, LocalDate saida);

    PaginacaoUtil<Funcionario> buscaPaginada(int pagina, String ordenacao);
}
