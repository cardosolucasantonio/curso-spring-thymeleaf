package com.lucas.curso.boot.service;

import java.util.List;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.util.PaginacaoUtil;

public interface CargoService {

	void save(Cargo cargo);
	
	void edit(Cargo cargo);
	
	void delete(Long id);
	
	Cargo findById(Long id);
	
	List<Cargo> findAll();
	
	boolean cargoTemFuncioarios(Long id);

    PaginacaoUtil<Cargo> buscaPaginada(int pagina, String ordenacao);
	
}
