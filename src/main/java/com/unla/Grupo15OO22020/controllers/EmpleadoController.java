package com.unla.Grupo15OO22020.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.entities.Empleado;
import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.entities.Producto;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
import com.unla.Grupo15OO22020.models.LocalModel;
import com.unla.Grupo15OO22020.models.RankingProductoModel;
import com.unla.Grupo15OO22020.models.SueldoEmpleadoModel;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.ILocalService;
import com.unla.Grupo15OO22020.services.IPedidoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController{
	
	@Autowired
    @Qualifier("empleadoService")
	private IEmpleadoService empleadoService;
	
	@Autowired
    @Qualifier("pedidoService")
	private IPedidoService pedidoService;
	
	
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
		
		if(empleadoService.remove(idPersona)) {
			redirectAttrs.addFlashAttribute("mensaje","Eliminado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");			
		} 	else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido eliminar, debido a que el empleado tiene dependencias con algún pedido o local.");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}
		
		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
	@PostMapping("/back")
	public RedirectView back() {
		
		return new RedirectView(ViewRouteHelpers.EMPLEADO_ROOT);
	}
	
	@GetMapping("/sueldos")
	public ModelAndView sueldos() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.EMPLEADO_SUELDOS);
		return mAV;		
	}
	
	@RequestMapping(value="/calcularSueldos", method=RequestMethod.POST)
	public ModelAndView calcularSueldos(@RequestParam("mes") int mes, @RequestParam("anio") int anio, Model model) {

		ModelAndView mAV = new ModelAndView("empleado/mostrarsueldos");
		Set<SueldoEmpleadoModel> listaSueldos = sueldoEmpleados(mes,anio, pedidoService.getAll(), empleadoService.getAll());
		System.out.println(listaSueldos.size());
		mAV.addObject("listaSueldos", listaSueldos);
		mAV.addObject("mes", mes);
		mAV.addObject("año", anio);
		model.addAttribute("listaSueldos", listaSueldos);
		model.addAttribute("mes", mes);
		model.addAttribute("anio", anio);
		
		return mAV;
	}
	
	public Set<SueldoEmpleadoModel> sueldoEmpleados(int mes, int año, List<Pedido> pedidos, List<Empleado> empleados){
		
		Map<Long, Double> map = new HashMap<Long, Double>();
		
		int porcentaje=0;
		int porcentaje2=0;
		
		Set<SueldoEmpleadoModel> empleadosConSueldo = new HashSet<SueldoEmpleadoModel>();
	
		//Pasamos al mapa todos los empleados con un sueldo base de 20k.
		for(Empleado e : empleados) {
			if(!map.containsKey(e.getIdPersona())) {
				map.put(e.getIdPersona(), 20000.0);
			}
		}
		
		//Calculamos los sueldos de cada empleado
		for(Pedido p : pedidos) {
			if(p.isAceptado() && p.getFecha().toLocalDate().getMonthValue()==mes && p.getFecha().toLocalDate().getYear()==año) {

				
				if(map.containsKey(p.getVendedor().getIdPersona()) && p.getColaborador()==null) {
					porcentaje = (int) (0.05*p.getSubtotal());
					map.replace(p.getVendedor().getIdPersona(), map.get(p.getVendedor().getIdPersona())+porcentaje);
				}
				else if(map.containsKey(p.getVendedor().getIdPersona()) && p.getColaborador()!=null){
					porcentaje = (int) (0.03*p.getSubtotal());
					map.replace(p.getVendedor().getIdPersona(), map.get(p.getVendedor().getIdPersona())+porcentaje);
					
					porcentaje2 = (int) (0.02*p.getSubtotal());
					map.replace(p.getColaborador().getIdPersona(), map.get(p.getColaborador().getIdPersona())+porcentaje2);
				}				
			}
		}
		
		for(Long key : map.keySet()) {
			
			//Le pasamos los demas datos
			EmpleadoModel e = empleadoService.findByIdPersona(key);
			SueldoEmpleadoModel s = new SueldoEmpleadoModel(e.getNombre(), e.getApellido(), e.getDni(), e.getLocal(), map.get(key));
			empleadosConSueldo.add(s);
		}
			
		return empleadosConSueldo;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
