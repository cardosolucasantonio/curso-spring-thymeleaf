package com.lucas.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.lucas.curso.boot.domain.Departamento;
import com.lucas.curso.boot.service.DepartamentoService;

/**
 * Classe responsável por intercepctar os dados enviados para o CargoController
 * onde há um atributo Departamento esperado, porém é enviado uma String com o id
 * do departamento selecionado no combobox.
 * 
 * @author LUCASDEV
 */
@Component
public class StringToDepartamentoConverter implements Converter<String, Departamento> {

	@Autowired
	private DepartamentoService service;
	
	@Override
	public Departamento convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		
		Long id = Long.valueOf(text);
		return service.findById(id);
	}
	
}
