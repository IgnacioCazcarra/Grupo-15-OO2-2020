package com.unla.Grupo15OO22020.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.helpers.ViewRouteHelpers;
import com.unla.Grupo15OO22020.models.LocalModel;
import com.unla.Grupo15OO22020.models.RankingProductoModel;
import com.unla.Grupo15OO22020.services.IPedidoService;


@Controller
@RequestMapping("/ranking")
public class RankingController {

	@Autowired
	@Qualifier("pedidoService")
	private IPedidoService pedidoService;
	
	@GetMapping("")
	public ModelAndView index() {
													// ranking/index
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.RANKING_INDEX);
		
		List<RankingProductoModel> ranking = rankingProducto(pedidoService.getAll());

		mAV.addObject("rankingProductos",ranking);
		return mAV;		
	}
	
	@PostMapping("")
	public RedirectView redirect(@ModelAttribute("ranking") RankingProductoModel rankingProductoModel){
		
		return new RedirectView(ViewRouteHelpers.RANKING_INDEX);
		
	}
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") long idProducto) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelpers.RANKING_PRODUCTO);
		List<RankingProductoModel> ranking = rankingProducto(pedidoService.getAll());

		mAV.addObject("rankingProductos",ranking);

		return mAV;
	}	
	
	public List<RankingProductoModel> rankingProducto(List<Pedido> pedidos){
				
		Map<String,Integer> ranking = new HashMap<String,Integer>();
		List<RankingProductoModel> rankingProd = new ArrayList<RankingProductoModel>();
		
		for(Pedido p: pedidos) {
			if(p.isAceptado()) {
				if(!ranking.containsKey(p.getProducto().getNombre())) {
					ranking.put(p.getProducto().getNombre(), p.getCantidad());
				}
				else {
					ranking.replace(p.getProducto().getNombre(), ranking.get(p.getProducto().getNombre())+p.getCantidad());
				}
			}
		}

		for(String key : ranking.keySet()) {
			rankingProd.add(new RankingProductoModel(key, ranking.get(key)));
		}
		
		Collections.sort(rankingProd, Collections.reverseOrder(Comparator.comparing(RankingProductoModel::getCantidadVendida)));
		
		return rankingProd;
	}
	
	
}
