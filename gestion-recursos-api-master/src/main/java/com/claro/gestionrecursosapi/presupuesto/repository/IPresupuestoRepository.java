package com.claro.gestionrecursosapi.presupuesto.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.claro.gestionrecursosapi.presupuesto.entity.PresupuestoEntity;

import java.util.List;

public interface IPresupuestoRepository extends CrudRepository<PresupuestoEntity, Integer> {

    @Query("SELECT p FROM PresupuestoEntity p WHERE p.elemento_pep in (?1)")
    public List<PresupuestoEntity> buscarPresupuestoPorCodigoPEP(List<String> listaPEP);

}
