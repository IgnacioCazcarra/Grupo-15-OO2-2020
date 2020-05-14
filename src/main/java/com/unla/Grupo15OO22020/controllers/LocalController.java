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
import org.springframework.web.servlet.view.RedirectView;

import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
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
	public RedirectView create(@ModelAttribute("local") LocalModel localModel) {
		
		int i = 0;
		while(i < localService.getAll().size()) {
			if(localService.getAll().get(i).getDireccion().equalsIgnoreCase((localModel.getDireccion()))) {
				System.out.println("Ya hay un local que tiene esa direccion.");
				return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
			}
			i++; 
		}
		
		
		
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
	public RedirectView update(@ModelAttribute("local") LocalModel localModel) {
		localService.insertOrUpdate(localModel);
		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable("id") long idLocal) {
		localService.remove(idLocal);
		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	
	@PostMapping("/atras")
	public RedirectView atras() {
		
		return new RedirectView(ViewRouteHelpers.LOCAL_ROOT);
	}

	
//	@RequestParam double lat1
	@GetMapping("/distanciaentrelocales")
	public ModelAndView distanciaentrelocales() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.LOCAL_DISTANCE);
	//	mAV.addObject("locales", localService.getAll());
	//	mAV.addObject("local", new LocalModel());
		mAV.addObject("locales", localService.getAll());
		
		return mAV;
	}
	

//@RequestMapping(value="/sacardistancia", method=RequestMethod.POST)
//public ModelAndView sacardistancia(@RequestParam("lat1") double lat1,
//		@RequestParam("lng1") double lng1,
//		@RequestParam("lat2") double lat2,
//		@RequestParam("lng2") double lng2
//		, Model model) {
//	model.addAttribute("lat1", lat1);
//	model.addAttribute("lng1", lng1);
//	model.addAttribute("lat2", lat2);
//	model.addAttribute("lng2", lng2);
//	ModelAndView mAV = new ModelAndView("local/mostrarDistancia");
//	double distancia=	distanciaCoord(lat1, lng1, lat2, lng2);
//	model.addAttribute("distancia", distancia);
//	return mAV;
//}

@RequestMapping(value="/sacardistancia", method=RequestMethod.POST)
public ModelAndView sacardistancia(LocalesModel locales, Model model) {
	System.out.println("ID: " + locales.getLocal1().getIdLocal());
	System.out.println("ID 2: " + locales.getLocal2().getIdLocal());
	
	model.addAttribute("lat1", localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLatitud());
	model.addAttribute("lng1", localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLongitud());
	model.addAttribute("lat2", localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLatitud());
	model.addAttribute("lng2", localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLongitud());
	ModelAndView mAV = new ModelAndView("local/mostrarDistancia");
	double distancia=	distanciaCoord(localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLatitud(), localService.findByIdLocal(locales.getLocal1().getIdLocal()).getLongitud(), localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLatitud(), localService.findByIdLocal(locales.getLocal2().getIdLocal()).getLongitud());
	model.addAttribute("distancia", distancia);
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

	
	
}