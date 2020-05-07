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

import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.implementation.EmpleadoService;
import com.unla.Grupo15OO22020.models.PedidoModel;
import com.unla.Grupo15OO22020.services.IClienteService;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.IPedidoService;
import com.unla.Grupo15OO22020.services.IProductoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;
	
	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;
	
	@Autowired
	@Qualifier("empleadoService")
	private IEmpleadoService empleadoService;
	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_INDEX);
		mAV.addObject("pedidos", pedidoService.getAll());
		mAV.addObject("pedido", new PedidoModel());
		return mAV;
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("pedido") PedidoModel pedidoModel) {
		return new RedirectView(ViewRouteHelpers.PEDIDO_INDEX);
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_ADD);
		mAV.addObject("pedido", new PedidoModel());
		mAV.addObject("productos",productoService.getAll());
		mAV.addObject("clientes",clienteService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());
		
		return mAV;
	}
	
	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("pedido") PedidoModel pedidoModel) {
		pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio() * pedidoModel.getCantidad());
		pedidoService.insertOrUpdate(pedidoModel);
		return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idPedido) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_UPDATE);
		mAV.addObject("pedido", pedidoService.findByIdPedido(idPedido));
		mAV.addObject("productos",productoService.getAll());
		mAV.addObject("clientes",clienteService.getAll());
		mAV.addObject("empleados",empleadoService.getAll());
		return mAV;
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("pedido") PedidoModel pedidoModel) {
		pedidoModel.setProducto(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()));
		pedidoModel.setCliente(clienteService.findByIdPersona(pedidoModel.getCliente().getIdPersona()));
		pedidoModel.setEmpleado(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()));
		pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio() * pedidoModel.getCantidad());
		pedidoService.insertOrUpdate(pedidoModel);
		return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idPedido) {
		pedidoService.remove(idPedido);
		return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);
	}	
	
	@PostMapping("/back")
	public RedirectView back() {	
		return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);
	}
	
}
