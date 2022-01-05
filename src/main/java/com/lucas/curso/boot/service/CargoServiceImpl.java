package com.lucas.curso.boot.service;

import java.util.List;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.curso.boot.dao.CargoDao;
import com.lucas.curso.boot.domain.Cargo;

@Service
@Transactional(readOnly = false)
public class CargoServiceImpl implements CargoService {
	
	@Autowired
	private CargoDao dao;

	@Override
	public void save(Cargo cargo) {
		dao.save(cargo);
	}

	@Override
	public void edit(Cargo cargo) {
		dao.update(cargo);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cargo findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cargo> findAll() {
		return dao.findAll();
	}

	@Override
	public boolean cargoTemFuncioarios(Long id) {
		if (findById(id).getFuncionarios().isEmpty()) {
			return false;
		}
		return true;
	}

    @Override
    public PaginacaoUtil<Cargo> buscaPaginada(int pagina, String ordenacao) {
        return dao.buscaPaginada(pagina, ordenacao);
    }

}
