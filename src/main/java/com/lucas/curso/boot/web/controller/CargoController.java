package com.lucas.curso.boot.web.controller;

import java.util.List;
import java.util.Optional;

import com.lucas.curso.boot.util.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.domain.Departamento;
import com.lucas.curso.boot.service.CargoService;
import com.lucas.curso.boot.service.DepartamentoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("order") Optional<String> order) {

	    int paginaAtual = page.orElse(1);
	    String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Cargo> pageCargo = cargoService.buscaPaginada(paginaAtual, ordenacao);

		// ModelMap - Atributos são adicionados via addAttribute()
		model.addAttribute("pageCargo", pageCargo);
		return "cargo/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
		    return "cargo/cadastro";
        }

		cargoService.save(cargo);
		// RedirectAttributes - Atributos são adicionados via addFlashAttribute()
		attr.addFlashAttribute("success", "Cargo inserido com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	/**
	 * Recupera os dados do objeto, lá no formulário de cadastro
	 * @param id
	 * @param model
	 * @return redirecionamento para a página de cadastro
	 */
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cargoService.findById(id));
		return "cargo/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "cargo/cadastro";
        }

		cargoService.edit(cargo);
		attr.addFlashAttribute("success", "Cargo editado com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (cargoService.cargoTemFuncioarios(id)) {
			attr.addFlashAttribute("fail", "Cargo não excluído, possui funcionário(s) vinculados");
		} else {
			cargoService.delete(id);
			attr.addFlashAttribute("success", "Cargo excluído com sucesso!");
		}
				
		return "redirect:/cargos/listar";
	}
	
	
	/**
	 * Método responsável por listar os departamentos no combo
	 * da tela de cadastro de cargo
	 * @return lista de departamentos
	 */	
	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos() {
		return departamentoService.findAll();
	}
	
	
	
}
