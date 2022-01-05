package com.lucas.curso.boot.web.conversor;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * Classe responsável por intercepctar os dados enviados para o FuncionarioController
 * onde há um atributo Cargo esperado, porém é enviado uma String com o id
 * do cargo selecionado no combobox.
 *
 * @author LUCASDEV
 */
@Component
public class StringToCargoConverter implements Converter<String, Cargo> {

    @Autowired
    private CargoService service;

    @Override
    public Cargo convert(String text) {
        if (text.isEmpty()) {
            return null;
        }

        Long id = Long.valueOf(text);
        return service.findById(id);
    }

}
