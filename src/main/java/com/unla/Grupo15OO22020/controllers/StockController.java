package com.unla.Grupo15OO22020.controllers;


import java.util.List;

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

import com.unla.Grupo15OO22020.converters.LoteConverter;
import com.unla.Grupo15OO22020.converters.ProductoConverter;
import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.entities.Stock;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.implementation.LocalService;
import com.unla.Grupo15OO22020.implementation.LoteService;
import com.unla.Grupo15OO22020.implementation.ProductoService;
import com.unla.Grupo15OO22020.models.LoteModel;
import com.unla.Grupo15OO22020.models.ProductoModel;
import com.unla.Grupo15OO22020.models.StockModel;
import com.unla.Grupo15OO22020.services.IStockService;


@Controller
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	@Qualifier("stockService")
	private IStockService stockService;

	@Autowired
	@Qualifier("localService")
	private LocalService localService;

	@Autowired
	@Qualifier("stockConverter")
	private StockConverter stockConverter;

	@Autowired
	@Qualifier("loteService")
	private LoteService loteService;

	@Autowired
	@Qualifier("productoService")
	private ProductoService productoService;

	@Autowired
	@Qualifier("productoConverter")
	private ProductoConverter productoConverter;

	@Autowired
	@Qualifier("loteConverter")
	private LoteConverter loteConverter;

	@GetMapping("")
	public ModelAndView index() {		
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.STOCK_INDEX);
		mAV.addObject("stocks", stockService.getAll());
		mAV.addObject("stock", new StockModel());
		mAV.addObject("locales", localService.getAll());
		mAV.addObject("lotes", loteService.getAll());
		return mAV;

	}

	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("stock") StockModel stockModel){

		return new RedirectView(ViewRouteHelpers.STOCK_INDEX);

	}

	@GetMapping("/new")
	public ModelAndView create() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.STOCK_ADD);
		mAV.addObject("stock", new StockModel());
		mAV.addObject("locales", localService.getAll());
		return mAV;
	}

	@PostMapping("/create")
	public RedirectView create(@ModelAttribute("stock") StockModel stockModel, RedirectAttributes redirectAttrs ) {	
		int i=0;
		boolean band = false;

		while(i<stockService.getAll().size() && !band){
			Stock s = stockService.getAll().get(i);
				if(s.getLocal().getIdLocal() == stockModel.getLocal().getIdLocal()){
					band = true;
				}
			i++;
		}

		if(!band){
			stockService.insertOrUpdate(stockModel);
			redirectAttrs.addFlashAttribute("mensaje","Agregado Correctamente");
			redirectAttrs.addFlashAttribute("clase", "success");
		}else{
			redirectAttrs.addFlashAttribute("mensaje","No se ha podido agregar debido a que ya existe stock en ese local");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		
		return new RedirectView(ViewRouteHelpers.STOCK_ROOT);
	}

	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idStock) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.STOCK_UPDATE);
		mAV.addObject("stock", stockService.findByIdStock(idStock));
		mAV.addObject("locales", localService.getAll());
		mAV.addObject("lotes", loteService.getAll());
		return mAV;
	}

	@PostMapping("/update")
	public RedirectView update(@ModelAttribute("stock") StockModel stockModel) {

		int i = 0;
		while(i < stockService.getAll().size()) {
			if(stockService.getAll().get(i).getLocal().getDireccion().equalsIgnoreCase(stockService.findByIdStock(stockModel.getIdStock()).getLocal().getDireccion())) {
				System.out.println("Ya hay un local que tiene ese stock.");
				return new RedirectView(ViewRouteHelpers.STOCK_ROOT);
			}
			i++; 
		}

		stockModel.setLocal(localService.findByIdLocal(stockModel.getLocal().getIdLocal()));
		stockService.insertOrUpdate(stockModel);
		return new RedirectView(ViewRouteHelpers.STOCK_ROOT);
	}


	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idStock) {
		stockService.remove(idStock);
		return new RedirectView(ViewRouteHelpers.STOCK_ROOT);
	}


	@PostMapping("/atras")
	public RedirectView atras() {

		return new RedirectView(ViewRouteHelpers.STOCK_ROOT);
	}

	@GetMapping("/calcularStockPorProducto")
	public ModelAndView calcularStockPorProducto() {		
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.STOCK_CALCULOSTOCK);
		mAV.addObject("producto", new ProductoModel());
		mAV.addObject("stock", new StockModel());
		mAV.addObject("productos", productoService.getAll());
		mAV.addObject("stocks", stockService.getAll());
		return mAV;
	}

	@RequestMapping(value="/mostrarStockPorProducto", method=RequestMethod.POST)
	public ModelAndView mostrarStockPorProducto(@ModelAttribute("stock") StockModel stockModel,
			@ModelAttribute("producto") ProductoModel productoModel,
			Model model) {
		model.addAttribute("stock", stockModel);
		model.addAttribute("producto", productoService.findByIdProducto(productoModel.getIdProducto()));
		ModelAndView mAV = new ModelAndView("stock/mostrarStockPorProducto");
		int i = 0;
		int total = 0;

		while(i < stockService.getAll().size()) {
			if(stockService.getAll().get(i).getIdStock() == stockService.findByIdStock(stockModel.getIdStock()).getIdStock()) {
				List<LoteModel> lote =	loteConverter.loteToLoteModel(stockService.getAll().get(i).getLotes());

				stockModel.setLotes(lote);



			}
			i++;
		}
		total = total + stockModel.calcularStock(productoModel);
		
		model.addAttribute("total", total);
		return mAV;
	}







}
