package com.unla.Grupo15OO22020.controllers;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

import com.unla.Grupo15OO22020.converters.LocalConverter;
import com.unla.Grupo15OO22020.converters.PedidoConverter;
import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.entities.Lote;
import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.entities.Producto;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
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
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("clientes", clienteService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());

		return mAV;
	}

	@PostMapping("/create")
	public ModelAndView create(@ModelAttribute("pedido") PedidoModel pedidoModel, Model model,
			RedirectAttributes redirectAttrs) {

		pedidoModel.setLocal(localService.findByIdLocal(
				empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()));
		pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio()
				* pedidoModel.getCantidad());

		if (stockValido(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
				pedidoModel.getCantidad(),
				stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
						.getLocal().getIdLocal()).getIdStock(),
				pedidoModel.getFecha())) {

			pedidoService.insertOrUpdate(pedidoModel);

			if (pedidoModel.isAceptado()) {
				consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
						pedidoModel.getCantidad(),
						stockService.findByIdStock(empleadoService
								.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
								.getIdStock(),
						pedidoModel.getFecha());
			}

		} else { // EN CASO DE QUE NO HAYA STOCK EN EL LOCAL PRINCIPAL

			Set<LocalModel> localesConStock = new HashSet<LocalModel>();
			Set<LocalModel> localesConStockPorCantidad = new HashSet<LocalModel>();
			try {
				int cantidadFaltante = pedidoModel.getCantidad() - cantidadLocalPrincipal(pedidoModel.getProducto(),
						stockService.findByIdStock(empleadoService
								.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
								.getIdStock(),
						pedidoModel.getFecha());

				if (localesConStockConDistancia(
						localService.findByIdLocal(empleadoService
								.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()),
						productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()), cantidadFaltante,
						pedidoModel.getFecha()) != null) {
					localesConStock = localesConStockConDistancia(pedidoModel.getLocal(), pedidoModel.getProducto(),
							cantidadFaltante, pedidoModel.getFecha());

					if (localesConStock.size() <= 1) { // SI EL UNICO LOCAL QUE TIENE STOCK DEL PRODUCTO ES EL PRINCIPAL
						for (LocalModel l : localesConStock) {
							if (l.getIdLocal() == localService.findByIdLocal(empleadoService
									.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
									.getIdLocal()) {
								System.out.println(pedidoModel.getLocal().getStock().getIdStock()); // HACE SALTAR LA
																									// EXCEPCION A
																									// PROPOSITO
							}

						}
					}
					LocalModel buscarLocal;
					int i = 0;

					while (i < localesConStock.size()) {
						for (LocalModel l : localesConStock) {
							try {
								buscarLocal = localesConStockConDistanciaPorLocal(pedidoModel.getProducto(),
										cantidadFaltante, l, pedidoModel.getFecha());
								if (buscarLocal != null && !buscarLocal.equals(localService.findByIdLocal(
										empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
												.getLocal().getIdLocal()))) {
									localesConStockPorCantidad.add(buscarLocal);
								}

							} catch (Exception e) {

							} finally {

							}

						}
						i++;
					}

					// LIMITAR A 3 COMO MAXIMO LOS LOCALES POSIBLES:
					Set<LocalModel> localesConCercania = new HashSet<LocalModel>();
					localesConCercania = limitarATresLocales(
							localService.findByIdLocal(empleadoService
									.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()),
							localesConStockPorCantidad);

					model.addAttribute("locales", localService.getAll());
					model.addAttribute("localesConStockPorCantidad", localesConCercania);
					model.addAttribute("local1", new LocalModel());

					model.addAttribute("localPrincipal",
							localService.findByIdLocal(empleadoService
									.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
									.getDireccion());

					ArrayList<Double> mostrarDistancias = new ArrayList<Double>();

					for (LocalModel l : localesConCercania) {
						double distancia = l.distanciaCoord(
								localService.findByIdLocal(
										empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
												.getLocal().getIdLocal())
										.getLatitud(),
								localService.findByIdLocal(
										empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
												.getLocal().getIdLocal())
										.getLongitud(),
								l.getLatitud(), l.getLongitud());

						mostrarDistancias.add(Math.round(distancia * 100) / 100.00);
					}

					model.addAttribute("listaDistancias", mostrarDistancias);

					ModelAndView mAV2 = new ModelAndView(ViewRouteHelpers.PEDIDO_LOCALPARAPETICION);

					mAV2.addObject("locales", localService.getAll());

					mAV2.addObject("localesConStockPorCantidad", localesConCercania);

					if (!localesConCercania.isEmpty()) {
						pedidoService.insertOrUpdate(pedidoModel);
					}

					mAV2.addObject("pedidos", pedidoService.findByIdPedido(
							pedidoService.getAll().get(pedidoService.getAll().size() - 1).getIdPedido()));
					model.addAttribute("pedidos", pedidoService.findByIdPedido(
							pedidoService.getAll().get(pedidoService.getAll().size() - 1).getIdPedido()));

					mAV2.addObject("local1", new LocalModel());

					return mAV2;

				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("No hay locales que tengan stock de ese producto.");

				redirectAttrs.addFlashAttribute("mensaje", "No hay ningun local que pueda satisfacer su pedido.");
				redirectAttrs.addFlashAttribute("clase", "danger");
				ModelAndView mAV = new ModelAndView("redirect:/pedidos");
				return mAV;

			} finally {
				if (localesConStock.isEmpty()) {
					redirectAttrs.addFlashAttribute("mensaje", "No hay ningun local que pueda satisfacer su pedido.");
					redirectAttrs.addFlashAttribute("clase", "danger");
					ModelAndView mAV = new ModelAndView("redirect:/pedidos");
					return mAV;

				}

			}
		}
		ModelAndView mAV = new ModelAndView("redirect:/pedidos");
		return mAV;
	}

	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idPedido) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_UPDATE);
		mAV.addObject("pedido", pedidoService.findByIdPedido(idPedido));
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("clientes", clienteService.getAll());
		mAV.addObject("empleados", empleadoService.getAll());
		return mAV;
	}

	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("pedido") PedidoModel pedidoModel, RedirectAttributes redirectAttrs,
			Model model) {

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		pedidoModel.setProducto(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()));
		pedidoModel.setCliente(clienteService.findByIdPersona(pedidoModel.getCliente().getIdPersona()));
		pedidoModel.setVendedor(empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona()));
		pedidoModel.setLocal(localService.findByIdLocal(
				empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()));
		pedidoModel.setSubtotal(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getPrecio()
				* pedidoModel.getCantidad());
		pedidoModel.setFecha(sqlDate);

		if (pedidoModel.getColaborador().getIdPersona() != 0) {
			pedidoModel.setColaborador(empleadoService.findByIdPersona(pedidoModel.getColaborador().getIdPersona()));
			pedidoModel.getColaborador().setLocal(localService.findByIdLocal(empleadoService
					.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal()));
		} else {
			pedidoModel.setColaborador(null);
		}

		if (!stockValido(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
				pedidoModel.getCantidad(),
				stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
						.getLocal().getIdLocal()).getIdStock(),
				pedidoModel.getFecha()) && pedidoModel.getColaborador() == null) {

			if (calcularStock(pedidoModel.getProducto(), stockService.findByIdStock(
					empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
					.getIdStock(), pedidoModel.getFecha()) == 0) {

				redirectAttrs.addFlashAttribute("mensaje",
						"No se pudo realizar el pedido debido a que no hay stock de " + productoService
								.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getNombre()
								+ " en ningun local.");
				redirectAttrs.addFlashAttribute("clase", "danger");

				return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);

			} else {

				redirectAttrs.addFlashAttribute("mensaje",
						"El pedido excede la capacidad del local. Si desea comprar una cantidad superior a "
								+ calcularStock(pedidoModel.getProducto(),
										stockService.findByIdStock(empleadoService
												.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal()
												.getIdLocal()).getIdStock(),
										pedidoModel.getFecha())
								+ " realice otro pedido.");
				redirectAttrs.addFlashAttribute("clase", "danger");
				return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);

			}

		} else if (pedidoModel.getColaborador() != null) {
			
			int cantLocal = calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
					.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha());
			
			
			if (!stockValido(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),(pedidoModel.getCantidad()-cantLocal),
					stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal()).getIdStock(),
					pedidoModel.getFecha())) {

				if ((calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
					.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha())
						+calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
								.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha())) == 0) {

					redirectAttrs.addFlashAttribute("mensaje","No se pudo realizar el pedido debido a que no hay stock de " + productoService
						.findByIdProducto(pedidoModel.getProducto().getIdProducto()).getNombre()+ " en ningun local.");
					redirectAttrs.addFlashAttribute("clase", "danger");
					return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);

				} else {

					redirectAttrs.addFlashAttribute("mensaje",
							"El pedido excede la capacidad del stock de los dos locales juntos. Si desea comprar una cantidad superior a "
									+ (calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
											.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha())
											+calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
													.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha()))
									+ " realice otro pedido.");
					redirectAttrs.addFlashAttribute("clase", "danger");
					return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);

				}

			}

		}

		pedidoService.insertOrUpdate(pedidoModel);

		if (pedidoModel.isAceptado() && pedidoModel.getColaborador() == null) {

			consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
					pedidoModel.getCantidad(),
					stockService.findByIdStock(empleadoService.findByIdPersona(pedidoModel.getVendedor().getIdPersona())
							.getLocal().getIdLocal()).getIdStock(),
					pedidoModel.getFecha());

		} else if (pedidoModel.isAceptado() && pedidoModel.getColaborador() != null) {
			
			int cantidadMaxLP = calcularStock(pedidoModel.getProducto(),stockService.findByIdStock(empleadoService
					.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal()).getIdStock(),pedidoModel.getFecha());
			
			consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
					cantidadMaxLP,
					stockService.findByIdStock(empleadoService
							.findByIdPersona(pedidoModel.getVendedor().getIdPersona()).getLocal().getIdLocal())
							.getIdStock(),
					pedidoModel.getFecha());
			
			consumoStock(productoService.findByIdProducto(pedidoModel.getProducto().getIdProducto()),
					(pedidoModel.getCantidad()-cantidadMaxLP),
					stockService.findByIdStock(empleadoService
							.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal())
							.getIdStock(),
					pedidoModel.getFecha());

		}

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

	@GetMapping("/productosentrefechas")
	public ModelAndView productosentrefechas() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.PEDIDO_DATE);
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("local", new LocalModel());
		mAV.addObject("locales", localService.getAll());

		return mAV;
	}

	@RequestMapping(value = "/sacarprodfechas", method = RequestMethod.POST)
	public ModelAndView sacarprodfechas(@ModelAttribute("local") LocalModel local,
			@RequestParam("fecha1") @DateTimeFormat(pattern = "yy-MM-dd") Date fecha1,
			@RequestParam("fecha2") @DateTimeFormat(pattern = "yy-MM-dd") Date fecha2, Model model) {

		ModelAndView mAV = new ModelAndView("pedido/mostrarprodfechas");
		Set<Producto> listProduc = productosVendidosEntreFechas(local, fecha1, fecha2);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		mAV.addObject("fecha1", formatter.format(fecha1));
		mAV.addObject("fecha2", formatter.format(fecha2));
		mAV.addObject("local", local);
		mAV.addObject("productosFecha", listProduc);

		return mAV;
	}


	@PostMapping("/solicitudstock")
	public RedirectView pedidoExterno(@ModelAttribute PedidoModel pedidoModel, LocalModel local) {

		PedidoModel p = pedidoService.findByIdPedido(pedidoModel.getIdPedido());
		LocalModel l = localService.findByIdLocal(local.getIdLocal());
		l.setListaEmpleados(empleadoService.findByLocal(l));
		int empleadoSorteado = (int) Math.random() * (l.getListaEmpleados().size() - 1);
		EmpleadoModel empleadoColaborador = empleadoService
				.findByIdPersona(l.getListaEmpleados().get(empleadoSorteado).getIdPersona());
		pedidoModel.setColaborador(empleadoColaborador);

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		pedidoModel.setProducto(productoService.findByIdProducto(p.getProducto().getIdProducto()));
		pedidoModel.setCliente(clienteService.findByIdPersona(p.getCliente().getIdPersona()));
		pedidoModel.setVendedor(empleadoService.findByIdPersona(p.getVendedor().getIdPersona()));
		pedidoModel.setLocal(localService.findByIdLocal(
				empleadoService.findByIdPersona(p.getVendedor().getIdPersona()).getLocal().getIdLocal()));
		pedidoModel.setSubtotal(
				productoService.findByIdProducto(p.getProducto().getIdProducto()).getPrecio() * p.getCantidad());
		pedidoModel.setFecha(sqlDate);
		pedidoModel.setCantidad(p.getCantidad());
		pedidoModel.setAceptado(p.isAceptado());

		if (pedidoModel.isAceptado()) {
			consumoStock(pedidoModel.getProducto(), pedidoModel.getCantidad(),
					stockService.findByIdStock(empleadoService
							.findByIdPersona(pedidoModel.getColaborador().getIdPersona()).getLocal().getIdLocal())
							.getIdStock(),
					pedidoModel.getFecha());
		}

		pedidoService.insertOrUpdate(pedidoModel);
		return new RedirectView(ViewRouteHelpers.PEDIDO_ROOT);
	}

	public List<Lote> lotesDelProducto(ProductoModel productoModel, long id, Date fecha) {
		List<Lote> lotesActivos = new ArrayList<Lote>();
		for (Lote l : loteService.getAll()) {
			if (l.getProducto().getIdProducto() == productoModel.getIdProducto() && l.isEstado()
					&& l.getStock().getIdStock() == id && l.getFechaIngreso().before(fecha)) {
				lotesActivos.add(l);
			}
		}
		return lotesActivos;
	}

	public int calcularStock(ProductoModel productoModel, long id, Date fecha) {
		int total = 0;
		for (Lote l : lotesDelProducto(productoModel, id, fecha)) {
			total += l.getCantidadActual();
		}
		return total;
	}

	public boolean stockValido(ProductoModel productoModel, int cantidad, long id, Date fecha) {
		return (calcularStock(productoModel, id, fecha) >= cantidad) ? true : false; // SI EL STOCK DISPONIBLE ES MAYOR
																						// O IGUAL
		// A LA CANTIDAD
	}

	public int cantidadLocalPrincipal(ProductoModel productoModel, long id, Date fecha) {
		int totalLocal = 0;
		totalLocal = calcularStock(productoModel, id, fecha);
		return totalLocal; // SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}

	public void consumoStock(ProductoModel productoModel, int cantidad, long id, Date fecha) {
		int aux = cantidad;
		int x = 0;

		while (x < lotesDelProducto(productoModel, id, fecha).size() && aux > 0) {
			Lote l = lotesDelProducto(productoModel, id, fecha).get(x);

			if (l.getCantidadActual() > aux) {
				l.setCantidadActual(l.getCantidadActual() - aux);
				aux = 0;
			} else if (l.getCantidadActual() < aux) {
				aux -= l.getCantidadActual();
				l.setCantidadActual(0);
				l.setEstado(false);
			} else if (l.getCantidadActual() == aux) {
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

	public Set<Producto> productosVendidosEntreFechas(LocalModel l1, Date comienzo, Date fin) {

		List<Pedido> pedidos = pedidoService.getAll();
		Set<Producto> productoList = new HashSet<Producto>();

		for (Pedido p : pedidos) {
			if (p.getFecha().before(fin) && p.getFecha().after(comienzo)) {
				if (p.isAceptado() && p.getLocal().equals(localConverter.modelToEntity(l1))) {
					productoList.add(p.getProducto());
				}
			}
		}
		return productoList;

	}

	public Set<LocalModel> localesConStock(ProductoModel producto, int cantidad, Date fecha) {
		Set<LocalModel> locales = new HashSet<LocalModel>();
		List<Lote> lotes = loteService.getAll();
		int total = 0;
		for (Lote l : lotes) {
			if (l.getProducto().getIdProducto() == producto.getIdProducto() && l.getFechaIngreso().before(fecha)) {
				total = total + l.getCantidadActual();

			}

		}

		if (total >= cantidad) {
			for (Lote l : lotes) {
				if (l.getProducto().getIdProducto() == producto.getIdProducto() && l.getFechaIngreso().before(fecha)) {
					locales.add(localService.findByIdLocal(l.getStock().getIdStock()));
				}
			}

		}

		return locales;
	}

	public Set<LocalModel> localesConStockConDistancia(LocalModel local, ProductoModel producto, int cantidad,
			Date fecha) {
		Set<LocalModel> listaLocales = new HashSet<LocalModel>();
		for (LocalModel l : this.localesConStock(producto, cantidad, fecha)) {
			// CALCULAR DISTANCIA.
			listaLocales.add(l);
		}
		return listaLocales;
	}

	public double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371; // en kilómetros
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		return radioTierra * va2;
	}

	public LocalModel localesConStockConDistanciaPorLocal(ProductoModel producto, int cantidad, LocalModel local,
			Date fecha) {
		LocalModel localModel = null;
		try {
			localModel = this.localesConStockPorLocal(producto, cantidad, local, fecha);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Este local no tiene suficiente cantidad para ceder mercaderìa.");
		}

		return localModel;
	}

	public LocalModel localesConStockPorLocal(ProductoModel producto, int cantidad, LocalModel local, Date fecha) {
		LocalModel localRetorno = null;
		List<Lote> lotes = loteService.getAll();
		int total = 0;
		for (Lote l : lotes) {
			if (l.getProducto().getIdProducto() == producto.getIdProducto()
					&& l.getStock().getIdStock() == local.getIdLocal() && l.getFechaIngreso().before(fecha)) {
				total = total + l.getCantidadActual();
			}

		}

		if (total >= cantidad) {
			localRetorno = local;
		}

		return localRetorno;
	}

	public Set<LocalModel> limitarATresLocales(LocalModel localPrincipal, Set<LocalModel> localesConStockPorCantidad) {
		Set<LocalModel> localesCercanos = new HashSet<LocalModel>();
		if (localesConStockPorCantidad.size() > 3) {

			int i = 0;
			List<Double> distancias = new ArrayList<Double>();

			for (LocalModel l : localesConStockPorCantidad) {
				distancias.add(distanciaCoord(localPrincipal.getLatitud(), localPrincipal.getLongitud(), l.getLatitud(),
						l.getLongitud()));
			}
			Collections.sort(distancias);
			while (i < 3) {
				for (LocalModel l : localesConStockPorCantidad) {
					if (distancias.get(i) == distanciaCoord(localPrincipal.getLatitud(), localPrincipal.getLongitud(),
							l.getLatitud(), l.getLongitud())) {
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
