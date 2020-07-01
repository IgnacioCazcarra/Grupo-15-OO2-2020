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

import com.unla.Grupo15OO22020.entities.Producto;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.ProductoModel;
import com.unla.Grupo15OO22020.services.IProductoService;
import com.unla.Grupo15OO22020.services.IPedidoService;
import com.unla.Grupo15OO22020.services.ILoteService;
import com.unla.Grupo15OO22020.entities.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;

	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;

	@Autowired
	@Qualifier("loteService")
	private ILoteService loteService;

	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_INDEX);
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("producto", new ProductoModel());
		
		return mAV;
	}
	
	@GetMapping("/new")
	public ModelAndView crear() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_ADD);
		mAV.addObject("producto", new Producto());
		
		return mAV;
	}
	
	@PostMapping("/create")
	public RedirectView agregar(@ModelAttribute(name="productos") ProductoModel producto, RedirectAttributes redirectAttrs ) {
		int i=0;
		boolean band = false;

		while(i<productoService.getAll().size() && !band){
			Producto p = productoService.getAll().get(i);
				if(p.getNombre().equalsIgnoreCase(producto.getNombre())){
					band = true;
				}
			i++;
		}

		if(!band){
			productoService.insertOrUpdate(producto);
			redirectAttrs.addFlashAttribute("mensaje","Agregado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");
		}else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido agregar debido a que ya existe un producto con ese nombre");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView eliminar(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {

		if(!existeEnLote(id) && !existeEnPedido(id)){
			productoService.remove(id);
			redirectAttrs.addFlashAttribute("mensaje","Eliminado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");
		}else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido eliminar, debido a que se encuentra en pedidos o lotes");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}
		
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idProducto) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PRODUCTO_UPDATE);
		mAV.addObject("producto", productoService.findByIdProducto(idProducto));
		return mAV;
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("producto") ProductoModel productoModel,RedirectAttributes redirectAttrs) {
		productoService.insertOrUpdate(productoModel);
		redirectAttrs.addFlashAttribute("mensaje","Actualizado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");

		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}

	@PostMapping("/back")
	public RedirectView back() {
		
		return new RedirectView(ViewRouteHelpers.PRODUCTO_ROOT);
	}

	public boolean existeEnPedido(long idProducto){
		boolean band = false;
		int i=0;

		while(i<pedidoService.getAll().size() && !band){
			Pedido p = pedidoService.getAll().get(i);
				if(p.getProducto().getIdProducto() == idProducto){
					band = true;
				}
			i++;
		}

		return band;
	}

	public boolean existeEnLote(long idProducto){
		boolean band = false;
		int i=0;

		while(i<loteService.getAll().size() && !band){
			Lote l = loteService.getAll().get(i);
				if(l.getProducto().getIdProducto() == idProducto){
					band = true;
				}
			i++;
		}

		return band;
	}

}
