package com.lucas.curso.boot.service;

import java.util.List;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.curso.boot.dao.DepartamentoDao;
import com.lucas.curso.boot.domain.Departamento;


@Service
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	private DepartamentoDao dao;
	
	@Transactional(readOnly = false)
	@Override
	public void save(Departamento departamento) {
		dao.save(departamento);
	}

	@Transactional(readOnly = false)
	@Override
	public void edit(Departamento departamento) {
		dao.update(departamento);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Departamento findById(Long id) {
		return dao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Departamento> findAll() {
		return dao.findAll();
	}

	@Override
	public boolean departamentoTemCargos(Long id) {
		if (findById(id).getCargos().isEmpty()) {
			return false;
		}
		return true;
	}

    @Override
    public PaginacaoUtil<Departamento> buscaPaginada(int pagina, String ordenacao) {
        return dao.buscaPaginada(pagina, ordenacao);
    }
	
}
