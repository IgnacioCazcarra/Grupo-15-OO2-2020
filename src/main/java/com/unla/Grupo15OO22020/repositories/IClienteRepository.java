package com.unla.Grupo15OO22020.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.Grupo15OO22020.entities.Cliente;




@Repository("clienteRepository")
public interface IClienteRepository extends JpaRepository<Cliente, Serializable>{

	public abstract Cliente findByEmail(String email);

	public abstract Cliente findByIdPersona(long idPersona);
	
}