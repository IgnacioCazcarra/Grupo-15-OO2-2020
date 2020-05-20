package com.unla.Grupo15OO22020.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.unla.Grupo15OO22020.entities.Lote;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.LoteModel;
import com.unla.Grupo15OO22020.models.PedidoModel;
import com.unla.Grupo15OO22020.models.ProductoModel;
import com.unla.Grupo15OO22020.services.IClienteService;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.ILocalService;
import com.unla.Grupo15OO22020.services.ILoteService;
import com.unla.Grupo15OO22020.services.IPedidoService;
import com.unla.Grupo15OO22020.services.IProductoService;
import com.unla.Grupo15OO22020.models.LoteModel;

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
	
	@Autowired
	@Qualifier("localService")
	private ILocalService localService;
	
	@Autowired
	@Qualifier("loteService")
	private ILoteService loteService;
	
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
		
		if(stockValido(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()), pedidoModel.getCantidad())) {
			
			pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio() * pedidoModel.getCantidad());
			pedidoModel.setLocal(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()));
			consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),pedidoModel.getCantidad());
			
			pedidoService.insertOrUpdate(pedidoModel);
		}else {
			System.out.println("STOCK INVALIDO PARA REALIZAR PEDIDO");
		}
		
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
		pedidoModel.setLocal(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()));
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
	
	public List<Lote> lotesDelProducto(ProductoModel productoModel){
		List<Lote> lotesActivos = new ArrayList<Lote>();

		for(Lote l : loteService.getAll()) {
			if(l.getProducto().getIdProducto() == productoModel.getIdProducto() && l.isEstado()) {
				lotesActivos.add(l);
			}
		}
		return lotesActivos;
	}
	
	
	public int calcularStock(ProductoModel productoModel) {
		int total = 0;
		for(Lote l : lotesDelProducto(productoModel)) {
			total += l.getCantidadActual();
		}
		return total;
	}
	

	public boolean stockValido(ProductoModel productoModel, int cantidad) {
		return (calcularStock(productoModel)>=cantidad)? true:false; //SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}
	
	public void consumoStock(ProductoModel productoModel, int cantidad) {		
		int aux = cantidad;
		int x = 0;
	
			while (x < lotesDelProducto(productoModel).size() && aux > 0) {
				Lote l = lotesDelProducto(productoModel).get(x);
				
				if (l.getCantidadActual() > aux) {
					l.setCantidadActual(l.getCantidadActual() - cantidad);
					aux = 0;
				}
				else if (l.getCantidadActual() < aux) {
					aux -= l.getCantidadActual();
					l.setCantidadActual(0);
					l.setEstado(false);
				}
				else if (l.getCantidadActual() == aux) {
					aux = 0;
					l.setCantidadActual(0);
					l.setEstado(false);
				}
				
				LoteModel lM = loteService.findByIdLote(l.getIdLote());
				lM.setCantidadActual(l.getCantidadActual());
				lM.setEstado(l.isEstado());
				
				loteService.insertOrUpdate(lM);
				
				x++;
			
			}
			 			
	}
	
	
}
