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
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.converters.EmpleadoConverter;
import com.unla.Grupo15OO22020.converters.ProductoConverter;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.implementation.SolicitudService;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
import com.unla.Grupo15OO22020.models.ProductoModel;
import com.unla.Grupo15OO22020.models.SolicitudStockModel;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.IProductoService;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

	@Autowired
	@Qualifier("solicitudService")
	private SolicitudService solicitudService;
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;
	
	@Autowired
	@Qualifier("empleadoService")
	private IEmpleadoService empleadoService;
	
	@Autowired
	@Qualifier("productoConverter")
	private ProductoConverter productoConverter;
	
	@Autowired
	@Qualifier("empleadoConverter")
	private EmpleadoConverter empleadoConverter;
	
	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.SOLICITUD_INDEX);
		mAV.addObject("solicitudes", solicitudService.getAll());
		mAV.addObject("solicitud", new SolicitudStockModel());
		return mAV;
		
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("solicitud") SolicitudStockModel solicitudStockModel){
		
		return new RedirectView(ViewRouteHelpers.SOLICITUD_INDEX);
		
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.SOLICITUD_ADD);
		mAV.addObject("solicitud", new SolicitudStockModel());
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());

		return mAV;
	}
	
	
	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("solicitud") SolicitudStockModel solicitudStockModel) {
		solicitudStockModel.setProducto(productoService.findByIdProducto(solicitudStockModel.getProducto().getIdProducto()));
		solicitudStockModel.setVendedor(empleadoService.findByIdPersona(solicitudStockModel.getVendedor().getIdPersona()));
		if(solicitudStockModel.getColaborador()==null) { solicitudStockModel.setColaborador(null);}
		else{ 
			solicitudStockModel.setColaborador(empleadoService.findByIdPersona(solicitudStockModel.getColaborador().getIdPersona()));
		}
		solicitudService.insertOrUpdate(solicitudStockModel);
		return new RedirectView(ViewRouteHelpers.SOLICITUD_ROOT);
	}
	
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idSolicitud) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.SOLICITUD_UPDATE);
		mAV.addObject("solicitud", solicitudService.findByIdSolicitud(idSolicitud));
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());

		return mAV;
	}
	
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("solicitud") SolicitudStockModel solicitudStockModel) {
		EmpleadoModel vm = empleadoService.findByIdPersona(solicitudStockModel.getVendedor().getIdPersona());
		EmpleadoModel cm = null;

		ProductoModel producto = productoService.findByIdProducto(solicitudStockModel.getProducto().getIdProducto());

		if(solicitudStockModel.getColaborador()!=null) {
			 cm = empleadoService.findByIdPersona(solicitudStockModel.getColaborador().getIdPersona());
		}
		
		solicitudStockModel.setVendedor(vm);
		solicitudStockModel.setColaborador(cm);
		solicitudStockModel.setProducto(producto);
		solicitudService.insertOrUpdate(solicitudStockModel);
		
		return new RedirectView(ViewRouteHelpers.SOLICITUD_ROOT);
	}

	
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idSolicitud) {
		solicitudService.remove(idSolicitud);
		return new RedirectView(ViewRouteHelpers.SOLICITUD_ROOT);
	}

	
	@PostMapping("/atras")
	public RedirectView atras() {
		
		return new RedirectView(ViewRouteHelpers.SOLICITUD_ROOT);
	}
}
