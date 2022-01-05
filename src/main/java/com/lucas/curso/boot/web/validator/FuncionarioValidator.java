package com.lucas.curso.boot.web.validator;

import com.lucas.curso.boot.domain.Funcionario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Objects;

public class FuncionarioValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario f = (Funcionario) target;

        validateDataSaidaPosterior(f, errors);
    }

    private void validateDataSaidaPosterior(final Funcionario f, Errors errors) {
        LocalDate datSaida = f.getDataSaida();
        if (!Objects.isNull(datSaida)) {
            if (datSaida.isBefore(f.getDataEntrada())) {
                errors.rejectValue("dataSaida", "PosteriorDataEntrada.funcionario.data.dataSaida");
            }
        }
    }

}
