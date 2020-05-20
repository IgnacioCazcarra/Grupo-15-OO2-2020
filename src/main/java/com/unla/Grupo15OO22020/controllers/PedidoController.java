package com.unla.Grupo15OO22020.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.converters.LocalConverter;
import com.unla.Grupo15OO22020.converters.PedidoConverter;
import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.entities.Lote;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.LocalModel;
import com.unla.Grupo15OO22020.models.LoteModel;
import com.unla.Grupo15OO22020.models.PedidoModel;
import com.unla.Grupo15OO22020.models.ProductoModel;
import com.unla.Grupo15OO22020.services.IClienteService;
import com.unla.Grupo15OO22020.services.IEmpleadoService;
import com.unla.Grupo15OO22020.services.ILocalService;
import com.unla.Grupo15OO22020.services.ILoteService;
import com.unla.Grupo15OO22020.services.IPedidoService;
import com.unla.Grupo15OO22020.services.IProductoService;
import com.unla.Grupo15OO22020.services.IStockService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;

	@Autowired
	@Qualifier("stockService")
	private IStockService stockService;

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

	@Autowired
	@Qualifier("localConverter")
	private LocalConverter localConverter;

	@Autowired
	@Qualifier("stockConverter")
	private StockConverter stockConverter;


	@Autowired
	@Qualifier("pedidoConverter")
	private PedidoConverter pedidoConverter;

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
	public ModelAndView create(@ModelAttribute("pedido") PedidoModel pedidoModel, Model model, RedirectAttributes redirectAttrs) {
		pedidoModel.setLocal(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()));
		pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio() * pedidoModel.getCantidad());

		if(stockValido(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()), pedidoModel.getCantidad(), stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()).getIdStock())) {			
			consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),pedidoModel.getCantidad(), stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()).getIdStock());
			pedidoService.insertOrUpdate(pedidoModel);
				//Aca se deberia crear una solicitud de stock


		}else { //EN CASO DE QUE NO HAYA STOCK EN EL LOCAL PRINCIPAL

			Set<LocalModel> localesConStock = new HashSet<LocalModel>();
			Set<LocalModel> localesConStockPorCantidad = new HashSet<LocalModel>();
			try {
				int cantidadFaltante = pedidoModel.getCantidad() - cantidadLocalPrincipal(pedidoModel.getProducto(),  stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()).getIdStock());

				if(localesConStockConDistancia(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()), productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()), cantidadFaltante) != null) {
					localesConStock = localesConStockConDistancia(pedidoModel.getLocal(), pedidoModel.getProducto(), cantidadFaltante);

					if(localesConStock.size() <= 1) {	//SI EL UNICO LOCAL QUE TIENE STOCK DEL PRODUCTO ES EL PRINCIPAL
						for(LocalModel l: localesConStock) {
							if(l.getIdLocal() == localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()).getIdLocal()) {
								System.out.println(pedidoModel.getLocal().getStock().getIdStock()); //HACE SALTAR LA EXCEPCION A PROPOSITO
							}

						}
					}
					LocalModel buscarLocal;
					int i = 0;
				
					while(i < localesConStock.size()) {
						for(LocalModel l: localesConStock) {
							try {
								buscarLocal = localesConStockConDistanciaPorLocal(pedidoModel.getProducto(), cantidadFaltante, l);
								if(buscarLocal!= null && !buscarLocal.equals(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal())))
								{
									localesConStockPorCantidad.add(buscarLocal);
								}

							}catch(Exception e) {

							}
							finally {

							}


						}
						i++;
					}
		
					//LIMITAR A 3 COMO MAXIMO LOS LOCALES POSIBLES:
					Set<LocalModel> localesConCercania = new HashSet<LocalModel>();
					localesConCercania = limitarATresLocales(localService.findByIdLocal(empleadoService.findByIdPersona(pedidoModel.getEmpleado().getIdPersona()).getLocal().getIdLocal()), localesConStockPorCantidad);

					model.addAttribute("locales", localService.getAll());
					model.addAttribute("localesConStockPorCantidad", localesConCercania);
					ModelAndView mAV2 = new ModelAndView(ViewRouteHelpers.PEDIDO_LOCALPARAPETICION);
					
					mAV2.addObject("locales", localService.getAll());

					mAV2.addObject("localesConStockPorCantidad", localesConCercania);
					
					//Aca se deberia crear otra solicitudstock con el local q se eligio
					return mAV2;

				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("No hay locales que tengan stock de ese producto.");

				redirectAttrs.addFlashAttribute("mensaje", "No hay ningun local que pueda satisfacer su pedido.");
				redirectAttrs.addFlashAttribute("clase", "danger");
				ModelAndView mAV =  new ModelAndView("redirect:/pedidos");
				return mAV;

			}finally {
				if(localesConStock.isEmpty()) {
					redirectAttrs.addFlashAttribute("mensaje", "No hay ningun local que pueda satisfacer su pedido.");
					redirectAttrs.addFlashAttribute("clase", "danger");
					ModelAndView mAV =  new ModelAndView("redirect:/pedidos");
					return mAV;

				}

			}
		}
		ModelAndView mAV =  new ModelAndView("redirect:/pedidos");
		return mAV;
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

//	@GetMapping("/resolverPedido/{id}")
//	public ModelAndView resolverPedido(@PathVariable("id") long idPedido) {
//		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_LOCALPARAPETICION);
//		return mAV;
//	}

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

	public List<Lote> lotesDelProducto(ProductoModel productoModel, long id){
		List<Lote> lotesActivos = new ArrayList<Lote>();
		for(Lote l : loteService.getAll()) {
			if(l.getProducto().getIdProducto() == productoModel.getIdProducto() && l.isEstado() && l.getStock().getIdStock() == id) {
				lotesActivos.add(l);
			}
		}
		return lotesActivos;
	}


	public int calcularStock(ProductoModel productoModel, long id) {
		int total = 0;
		for(Lote l : lotesDelProducto(productoModel, id)) {
			total += l.getCantidadActual();
		}
		return total;
	}


	public boolean stockValido(ProductoModel productoModel, int cantidad, long id) {
		return (calcularStock(productoModel, id)>=cantidad)? true:false; //SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}


	public int cantidadLocalPrincipal(ProductoModel productoModel, long id) {
		int totalLocal = 0;
		totalLocal = calcularStock(productoModel, id);
		return totalLocal; //SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}


	public void consumoStock(ProductoModel productoModel, int cantidad, long id) {		
		int aux = cantidad;
		int x = 0;

		while (x < lotesDelProducto(productoModel, id).size() && aux > 0) {
			Lote l = lotesDelProducto(productoModel, id).get(x);

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



	public Set<LocalModel> localesConStock(ProductoModel producto, int cantidad) {
		Set<LocalModel> locales = new HashSet<LocalModel>();
		List<Lote> lotes = loteService.getAll();
		int total = 0;
		for (Lote l : lotes) {
			if(l.getProducto().getIdProducto() == producto.getIdProducto()) {
				total = total + l.getCantidadActual();

			}

		}

		if(total >= cantidad) {
			for (Lote l : lotes) {
				if(l.getProducto().getIdProducto() == producto.getIdProducto()) {
					locales.add(localService.findByIdLocal(l.getStock().getIdStock()));
				}
			}

		}

		return locales;
	}

	
	public Set<LocalModel> localesConStockConDistancia(LocalModel local, ProductoModel producto, int cantidad) {
		Set<LocalModel> listaLocales = new HashSet<LocalModel>();
		for (LocalModel l : this.localesConStock(producto, cantidad)) {
			//CALCULAR DISTANCIA.
			listaLocales.add(l);
		}
		return listaLocales;
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

	public LocalModel localesConStockConDistanciaPorLocal(ProductoModel producto, int cantidad, LocalModel local) {
		LocalModel localModel = null;
		try {
			localModel = this.localesConStockPorLocal(producto, cantidad, local);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Este local no tiene suficiente cantidad para ceder mercaderìa.");
		}

		return localModel;
	}


	public LocalModel localesConStockPorLocal(ProductoModel producto, int cantidad, LocalModel local) {
		LocalModel localRetorno = null;
		List<Lote> lotes = loteService.getAll();
		int total = 0;
		for (Lote l : lotes) {
			if(l.getProducto().getIdProducto() == producto.getIdProducto() && l.getStock().getIdStock() == local.getIdLocal()) {
				total = total + l.getCantidadActual();
			}

		}

		if(total >= cantidad) {
			localRetorno = local;
		}



		return localRetorno;
	}

	public Set<LocalModel> limitarATresLocales(LocalModel localPrincipal, Set<LocalModel> localesConStockPorCantidad) {
		Set<LocalModel> localesCercanos = new HashSet<LocalModel>();
		if(localesConStockPorCantidad.size() > 3) {


			int i = 0;
			List<Double>  distancias = new ArrayList<Double>();

			for(LocalModel l: localesConStockPorCantidad) {
				distancias.add(distanciaCoord(localPrincipal.getLatitud(), localPrincipal.getLongitud(), l.getLatitud(), l.getLongitud()));
			}
			Collections.sort(distancias);
			while(i< 3) {
				for(LocalModel l: localesConStockPorCantidad) {
					if(distancias.get(i) == distanciaCoord(localPrincipal.getLatitud(), localPrincipal.getLongitud(), l.getLatitud(), l.getLongitud())) {
						localesCercanos.add(l);

					}

				}




				i++;
			}

			return localesCercanos;
		}

		return localesConStockPorCantidad;


	}


}
