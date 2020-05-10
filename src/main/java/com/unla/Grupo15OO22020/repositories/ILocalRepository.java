package com.unla.Grupo15OO22020.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.entities.Producto;




@Repository("localRepository")
public interface ILocalRepository extends JpaRepository<Local, Serializable>{

	public abstract Local findByIdLocal(long idLocal);
	
	public abstract Local findByDireccion(String direccion);
	
}