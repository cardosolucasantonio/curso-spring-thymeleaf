package com.lucas.curso.boot.web.controller;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lucas.curso.boot.domain.Departamento;
import com.lucas.curso.boot.service.DepartamentoService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("order") Optional<String> order) {

	    int paginaAtual = page.orElse(1);
	    String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Departamento> pageDepartamento = service.buscaPaginada(paginaAtual, ordenacao);

		// ModelMap - Atributos são adicionados via addAttribute()
		model.addAttribute("pageDepartamento", pageDepartamento);
		return "departamento/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
	    if (result.hasErrors()) {
	        return "departamento/cadastro";
        }

		service.save(departamento);
		// RedirectAttributes - Atributos são adicionados via addFlashAttribute()
		attr.addFlashAttribute("success", "Departamento inserido com sucesso");
		return "redirect:/departamentos/cadastrar";
	}
	
	/**
	 * Recupera os dados do objeto, lá no formulário de cadastro
	 * @param id
	 * @param model
	 * @return redirecionamento para a página de cadastro
	 */
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {		
		model.addAttribute("departamento", service.findById(id));
		return "departamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
	    if (result.hasErrors()) {
	        return "departamento/cadastro";
        }

		service.edit(departamento);
		attr.addFlashAttribute("success", "Departamento editado com sucesso");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if (service.departamentoTemCargos(id)) {
			model.addAttribute("fail", "Departamento não excluído, possui cargos vinculados");
		} else {
			service.delete(id);
			model.addAttribute("success", "Departamento excluído com sucesso!");
		}
		
		return "redirect:/departamentos/listar";
	}
	
}
