package com.lucas.curso.boot.dao;

import java.util.List;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.domain.Departamento;
import com.lucas.curso.boot.util.PaginacaoUtil;

public interface DepartamentoDao {
	
	void save(Departamento departamento);
	
	void update(Departamento departamento);
	
	void delete(Long id);
	
	Departamento findById(Long id);
	
	List<Departamento> findAll();

    PaginacaoUtil<Departamento> buscaPaginada(int pagina, String ordenacao);
	
}
