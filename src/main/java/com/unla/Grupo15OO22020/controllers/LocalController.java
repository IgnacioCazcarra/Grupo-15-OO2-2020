package com.unla.Grupo15OO22020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.entities.Cliente;
import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.implementation.ClienteService;
import com.unla.Grupo15OO22020.implementation.EmpleadoService;
import com.unla.Grupo15OO22020.implementation.PedidoService;
import com.unla.Grupo15OO22020.models.ClienteModel;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
import com.unla.Grupo15OO22020.models.LocalModel;
import com.unla.Grupo15OO22020.models.LocalesModel;
import com.unla.Grupo15OO22020.services.ILocalService;
import com.unla.Grupo15OO22020.services.IStockService;


@Controller
@RequestMapping("/locales")
public class LocalController {

	@Autowired
	@Qualifier("localService")
	private ILocalService localService;
	
	@Autowired
	@Qualifier("stockService")
	private IStockService stockService;
	
	@Autowired
	@Qualifier("stockConverter")
	private StockConverter stockConverter;
	
	@Autowired
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired
	@Qualifier("empleadoService")
	private EmpleadoService empleadoService;
	
	@Autowired
	@Qualifier("pedidoService")
	private PedidoService pedidoService;
	
	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_INDEX);
		mAV.addObject("locales", localService.getAll());
		mAV.addObject("local", new LocalModel());
	//	mAV.addObject("stock", new StockModel());
		return mAV;
		
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("local") LocalModel localModel){
		
		return new RedirectView(ViewRouteHelpers.LOCAL_INDEX);
		
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_ADD);
		mAV.addObject("local", new LocalModel());

		return mAV;
	}

	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("local") LocalModel localModel, RedirectAttributes redirectAttrs ) {
		
		int i = 0;
		while(i < localService.getAll().size()) {
			if(localService.getAll().get(i).getDireccion().equalsIgnoreCase((localModel.getDireccion()))) {
				System.out.println("Ya hay un local que tiene esa direccion.");
				redirectAttrs.addFlashAttribute("mensaje","No se ha podido agregar debido a que ya existe un local con esa direccion");
				redirectAttrs.addFlashAttribute("clase", "danger");
				return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
			}
			i++; 
		}
		
		redirectAttrs.addFlashAttribute("mensaje","Agregado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");
		
		localService.insertOrUpdate(localModel);

		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
		
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idLocal) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_UPDATE);
		mAV.addObject("local", localService.findByIdLocal(idLocal));	
		mAV.addObject("stock", stockService.getAll());

		return mAV;
		
	}
	
	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("local") LocalModel localModel, RedirectAttributes redirectAttrs) {
		localService.insertOrUpdate(localModel);

		redirectAttrs.addFlashAttribute("mensaje","Actualizado Correctamente");
		redirectAttrs.addFlashAttribute("clase", "success");
		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idLocal, RedirectAttributes redirectAttrs) {
		if(localService.remove(idLocal)) {
			redirectAttrs.addFlashAttribute("mensaje","Eliminado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");			
		}else 
		{
			
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido eliminar, debido a que esta vinculado con algun pedido");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	
	@PostMapping("/atras")
	public RedirectView atras() {
		
		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	@GetMapping("/distanciaentrelocales")
	public ModelAndView distanciaentrelocales() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_DISTANCE);
		mAV.addObject("locales", localService.getAll());
		
		return mAV;
	}

@RequestMapping(value="/sacardistancia", method=RequestMethod.POST)
public ModelAndView sacardistancia(LocalesModel locales, Model model) {
	System.out.println("ID: " + locales.getLocal1().getIdLocal());
	System.out.println("ID 2: " + locales.getLocal2().getIdLocal());
	
	model.addAttribute("lat1", localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLatitud());
	model.addAttribute("lng1", localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLongitud());
	model.addAttribute("dir1", localService.findByIdLocal(locales.getLocal1().getIdLocal()).getDireccion());

	model.addAttribute("lat2", localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLatitud());
	model.addAttribute("lng2", localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLongitud());
	model.addAttribute("dir2", localService.findByIdLocal(locales.getLocal2().getIdLocal()).getDireccion());

	ModelAndView mAV = new ModelAndView("local/mostrarDistancia");
	double distancia=	distanciaCoord(localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLatitud(), localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLongitud(), localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLatitud(), localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLongitud());
	model.addAttribute("distancia", Math.round(distancia*100)/100.00); // REDONDEA A DOS DECIMALES
	return mAV;
}



	
	public double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371; //en kilómetros
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
		+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		return radioTierra * va2;
		}

	@GetMapping("/eliminarcliente")
	public ModelAndView eliminarcliente() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_ELIMINARCLIENTE);
		mAV.addObject("locales", localService.getAll());
		mAV.addObject("clientes", clienteService.getAll());
		mAV.addObject("cliente", new ClienteModel());
		return mAV;
	}
	
	@GetMapping("/eliminarempleado")
	public ModelAndView eliminarempleado() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_ELIMINAREMPLEADO);
		mAV.addObject("locales", localService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());
		mAV.addObject("empleado", new EmpleadoModel());
		return mAV;
	}
	

	@RequestMapping(value="/deletecliente", method=RequestMethod.POST)
	public ModelAndView deleteCliente(ClienteModel cliente, RedirectAttributes redirectAttrs) {
		try {
			for (Pedido p : pedidoService.getAll()) {
				if(p.getCliente().getIdPersona() == cliente.getIdPersona()) {
					pedidoService.remove(p.getIdPedido());			
					redirectAttrs.addFlashAttribute("mensaje","La persona ya no es cliente del local.");
					redirectAttrs.addFlashAttribute("clase", "success");
				}
			}
					
	
		}catch(Exception e) {
			
			System.out.println("Se produjo un error al querer eliminar un cliente de un local.");
			redirectAttrs.addFlashAttribute("mensaje", "No se ha podido eliminar el cliente del local.");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}		
		finally {
			// TODO: handle finally clause
		}
		ModelAndView mAV = new ModelAndView("redirect:/locales");
		return mAV;
	}

	@RequestMapping(value="/deleteempleado", method=RequestMethod.POST)
	public ModelAndView deleteEmpleado(EmpleadoModel empleado, RedirectAttributes redirectAttrs) {
		try {
			for (Pedido p : pedidoService.getAll()) {
				if(p.getVendedor().getIdPersona() == empleado.getIdPersona()) {
					pedidoService.remove(p.getIdPedido());			
					redirectAttrs.addFlashAttribute("mensaje","La persona ya no es empleado del local.");
					redirectAttrs.addFlashAttribute("clase", "success");
				}
			}
					
	
		}catch(Exception e) {
			
			System.out.println("Se produjo un error al querer eliminar un empleado de un local.");
			redirectAttrs.addFlashAttribute("mensaje", "No se ha podido eliminar el empleado del local.");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}		
		finally {
			// TODO: handle finally clause
		}
		ModelAndView mAV = new ModelAndView("redirect:/locales");
		return mAV;
	}
	
}