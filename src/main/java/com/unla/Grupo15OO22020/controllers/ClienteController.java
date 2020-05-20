package com.unla.Grupo15OO22020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.entities.Cliente;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.ClienteModel;
import com.unla.Grupo15OO22020.services.IClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;
	
	
	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.CLIENT_INDEX);
		mAV.addObject("clientes", clienteService.getAll());
		mAV.addObject("cliente", new ClienteModel());
		return mAV;
		
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("cliente") ClienteModel clienteModel){
		
		return new RedirectView(ViewRouteHelpers.CLIENT_INDEX);
		
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.CLIENT_ADD);
		mAV.addObject("cliente", new ClienteModel());
		return mAV;
	}
	
	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("cliente") ClienteModel clienteModel,  RedirectAttributes redirectAttrs ) {
		int i=0;
		boolean band = false;

		while(i<clienteService.getAll().size() && !band){
			Cliente c = clienteService.getAll().get(i);
				if(c.getDni() == clienteModel.getDni()){
					band = true;
				}
			i++;
		}

		if(!band){
			clienteService.insertOrUpdate(clienteModel);
			redirectAttrs.addFlashAttribute("mensaje","Agregado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");
		}else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido agregar debido a que ya existe un cliente con ese dni");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		return new RedirectView(ViewRouteHelpers.CLIENT_ROOT);
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idPersona) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.CLIENT_UPDATE);
		mAV.addObject("cliente", clienteService.findByIdPersona(idPersona));
		return mAV;
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("cliente") ClienteModel clienteModel, RedirectAttributes redirectAttrs) {
		clienteService.insertOrUpdate(clienteModel);

		redirectAttrs.addFlashAttribute("mensaje","Actualizado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");

		return new RedirectView(ViewRouteHelpers.CLIENT_ROOT);
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idPersona, RedirectAttributes redirectAttrs) {
		clienteService.remove(idPersona);

		redirectAttrs.addFlashAttribute("mensaje","Eliminado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");

		return new RedirectView(ViewRouteHelpers.CLIENT_ROOT);
	}
	
	@PostMapping("/back")
	public RedirectView back() {
		
		return new RedirectView(ViewRouteHelpers.CLIENT_ROOT);
	}
	
}
