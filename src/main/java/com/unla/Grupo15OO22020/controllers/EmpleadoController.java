package com.unla.Grupo15OO22020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.entities.Empleado;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
import com.unla.Grupo15OO22020.models.LocalModel;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.ILocalService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController{
	
	@Autowired
    @Qualifier("empleadoService")
	private IEmpleadoService empleadoService;
	
	
	@Autowired
	@Qualifier("localService")
	private ILocalService localService;
	
	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.EMPLEADO_INDEX);
		mAV.addObject("empleados", empleadoService.getAll());
		mAV.addObject("empleado", new EmpleadoModel());
		return mAV;
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("empleado") EmpleadoModel empleadoModel) {
		return new RedirectView(ViewRouteHelpers.EMPLEADO_INDEX);
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.EMPLEADO_ADD);
		mAV.addObject("empleado", new EmpleadoModel());
		mAV.addObject("locales", localService.getAll());
		return mAV;
	}
	
	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("empleado") EmpleadoModel empleadoModel, RedirectAttributes redirectAttrs) {
		int i=0;
		boolean band = false;

		while(i<empleadoService.getAll().size() && !band){
			Empleado e = empleadoService.getAll().get(i);
				if(e.getDni() == empleadoModel.getDni()){
					band = true;
				}
			i++;
		}

		if(!band){
			empleadoService.insertOrUpdate(empleadoModel);
			redirectAttrs.addFlashAttribute("mensaje","Agregado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");
		}else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido agregar debido a que ya existe un empleado con ese dni");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idPersona) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.EMPLEADO_UPDATE);
		mAV.addObject("empleado", empleadoService.findByIdPersona(idPersona));
		mAV.addObject("locales", localService.getAll());
		return mAV;
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("empleado") EmpleadoModel empleadoModel,RedirectAttributes redirectAttrs) {
		LocalModel l  = localService.findByIdLocal(empleadoModel.getLocal().getIdLocal());
		empleadoModel.setLocal(l);
		empleadoService.insertOrUpdate(empleadoModel);

		redirectAttrs.addFlashAttribute("mensaje","Actualizado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");


		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idPersona,RedirectAttributes redirectAttrs) {
		empleadoService.remove(idPersona);

		redirectAttrs.addFlashAttribute("mensaje","Eliminado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");

		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
	@PostMapping("/back")
	public RedirectView back() {
		
		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
}
