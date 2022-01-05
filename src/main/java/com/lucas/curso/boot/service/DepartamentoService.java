package com.lucas.curso.boot.service;

import java.util.List;

import com.lucas.curso.boot.domain.Departamento;
import com.lucas.curso.boot.util.PaginacaoUtil;

public interface DepartamentoService {

	void save(Departamento departamento);
	
	void edit(Departamento departamento);
	
	void delete(Long id);
	
	Departamento findById(Long id);
	
	List<Departamento> findAll();

	boolean departamentoTemCargos(Long id);

    PaginacaoUtil<Departamento> buscaPaginada(int pagina, String ordenacao);
	
}
