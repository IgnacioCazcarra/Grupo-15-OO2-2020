package com.unla.Grupo15OO22020.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.Grupo15OO22020.entities.Empleado;

@Repository("empleadoRepository")
public interface IEmpleadoRepository extends JpaRepository<Empleado, Serializable>{
	
	public abstract Empleado findByIdPersona(long idPersona);
	
	public abstract Empleado findByNombre(String nombre);
		
	@Query("SELECT e from Empleado e where e.gerente= (:gerente)")
	public abstract List<Empleado> findByGerente(boolean gerente);

	
}
