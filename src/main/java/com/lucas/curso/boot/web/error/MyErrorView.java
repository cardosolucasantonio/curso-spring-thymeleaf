package com.lucas.curso.boot.web.error;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class MyErrorView implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", status.value());

        switch (status.value()) {
            case 404:
                modelAndView.addObject("error", "Página não encontrada.");
                modelAndView.addObject("message", "A URL para a página '" + map.get("path") + "' não existe.");
                break;
            case 500:
                modelAndView.addObject("error", "Ocorreu um erro interno no servidor.");
                modelAndView.addObject("message", "Ocorreu um erro inexperado, tente mais tarde.");
                break;
            default:
                modelAndView.addObject("error", map.get("error"));
                modelAndView.addObject("message", map.get("message"));
                break;
        }

        return modelAndView;
    }

}
