package com.lucas.curso.boot.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.lucas.curso.boot.util.PaginacaoUtil;
import com.lucas.curso.boot.web.validator.FuncionarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lucas.curso.boot.domain.Cargo;
import com.lucas.curso.boot.domain.Funcionario;
import com.lucas.curso.boot.domain.UF;
import com.lucas.curso.boot.service.CargoService;
import com.lucas.curso.boot.service.FuncionarioService;

import javax.validation.Valid;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private CargoService cargoService;

	@InitBinder("funcionario") //faz com que este método seja o primeiro a ser executado na classe
	public void initBinder(WebDataBinder binder) {
        binder.addValidators(new FuncionarioValidator());
    }
	
	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		return "funcionario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("order") Optional<String> order) {

	    int paginaAtual = page.orElse(1);
        String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Funcionario> pageFuncionario = funcionarioService.buscaPaginada(paginaAtual, ordenacao);

	    model.addAttribute("pageFuncionario", pageFuncionario);
		return "funcionario/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
	    if (result.hasErrors()) {
	        return "funcionario/cadastro";
        }

		funcionarioService.save(funcionario);
		attr.addFlashAttribute("success", "Funcionário inserido com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
	    model.addAttribute("funcionario", funcionarioService.findById(id));
	    return "funcionario/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
	    if (result.hasErrors()) {
	        return "funcionario/cadastro";
        }

	    funcionarioService.edit(funcionario);
	    attr.addFlashAttribute("success", "Funcionário editado com sucesso");
	    return "redirect:/funcionarios/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
	    funcionarioService.delete(id);
	    attr.addFlashAttribute("success", "Funcionário excluído com sucesso");
        return "redirect:/funcionarios/listar";
	}

	@GetMapping("/buscar/nome")
	public String getPorNome(ModelMap model, @RequestParam("nome") String nome,
                                             @RequestParam("page") Optional<Integer> page,
                                             @RequestParam("order") Optional<String> order) {

	    int paginaAtual = page.orElse(1);
	    String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Funcionario> pageFuncionario = funcionarioService.findByNome(paginaAtual, ordenacao, nome);

        model.addAttribute("pageFuncionario", pageFuncionario);
	    return "funcionario/lista";
    }

	@GetMapping("/buscar/cargo")
	public String getPorCargo(ModelMap model, @RequestParam("id") Long id,
                                              @RequestParam("page") Optional<Integer> page,
                                              @RequestParam("order") Optional<String> order) {

        int paginaAtual = page.orElse(1);
        String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Funcionario> pageFuncionario = funcionarioService.findByCargo(paginaAtual, ordenacao, id);

        model.addAttribute("pageFuncionario", pageFuncionario);
        return "funcionario/lista";
    }

    @GetMapping("/buscar/data")
    public String getPorDatas(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("order") Optional<String> order,
                              @RequestParam(name = "entrada", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
                              @RequestParam(name = "saida", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida,
                              ModelMap model) {

        int paginaAtual = page.orElse(1);
        String ordenacao = order.orElse("ASC");

        PaginacaoUtil<Funcionario> pageFuncionario = funcionarioService.findByDatas(paginaAtual, ordenacao, entrada, saida);

        model.addAttribute("pageFuncionario", pageFuncionario);

	    return "funcionario/lista";
    }

	@ModelAttribute("cargos")
	public List<Cargo> getCargos() {
		return cargoService.findAll();
	}
	
	@ModelAttribute("ufs")
	public UF[] getUfs() {
		return UF.values();
	}

}
