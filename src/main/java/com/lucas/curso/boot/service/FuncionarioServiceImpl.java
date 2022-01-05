package com.lucas.curso.boot.service;

import java.time.LocalDate;
import java.util.*;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.curso.boot.dao.FuncionarioDao;
import com.lucas.curso.boot.domain.Funcionario;

@Service
@Transactional(readOnly = true)
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	private FuncionarioDao dao;
	
	@Transactional(readOnly = false)
	@Override
	public void save(Funcionario funcionario) {
		dao.save(funcionario);
	}

	@Transactional(readOnly = false)
	@Override
	public void edit(Funcionario funcionario) {
		dao.update(funcionario);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public Funcionario findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Funcionario> findAll() {
		return dao.findAll();
	}

    @Override
    public PaginacaoUtil<Funcionario> findByNome(int pagina, String ordenacao, String nome) {
        return dao.findByName(pagina, ordenacao, nome);
    }

    @Override
    public PaginacaoUtil<Funcionario> findByCargo(int pagina, String ordenacao, Long id) {
        return dao.findByCargo(pagina, ordenacao, id);
    }

    @Override
    public List<Funcionario> findByDatas(LocalDate entrada, LocalDate saida) {
        if (!Objects.isNull(entrada) && !Objects.isNull(saida)) {
            return dao.findByPeriodo(entrada, saida);

        } else if (!Objects.isNull(entrada)) {
            return dao.findByDataEntrada(entrada);

        } if (!Objects.isNull(saida)) {
            return dao.findByDataSaida(saida);
        }

        return new ArrayList<>();
    }

    public PaginacaoUtil<Funcionario> buscaPaginada(int pagina, String ordenacao) {
	    return dao.buscaPaginada(pagina, ordenacao);
    }

}
