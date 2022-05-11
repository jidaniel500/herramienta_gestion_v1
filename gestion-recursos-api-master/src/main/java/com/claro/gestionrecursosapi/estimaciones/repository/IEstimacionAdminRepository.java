package com.claro.gestionrecursosapi.estimaciones.repository;

import com.claro.gestionrecursosapi.estimaciones.dto.DonutChartDataDTO;
import com.claro.gestionrecursosapi.estimaciones.dto.EstimacionDownloadDTO;
import com.claro.gestionrecursosapi.estimaciones.dto.EstimacionXProyectoDTO;
import com.claro.gestionrecursosapi.estimaciones.entity.EstimacionAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEstimacionAdminRepository extends JpaRepository<EstimacionAdmin, Integer> {


    @Query(value = "SELECT new com.claro.gestionrecursosapi.estimaciones.dto.EstimacionXProyectoDTO( p.id, p.nombre ) FROM  ProyectoEntity p WHERE p.id IN (SELECT et.codProyecto FROM  EstimacionAdmin et GROUP BY et.codProyecto) ")
    List<EstimacionXProyectoDTO> estimacionXProyecto();

    List<EstimacionAdmin> findByCodProyecto(int codProyecto);

    @Query(value = "SELECT new com.claro.gestionrecursosapi.estimaciones.dto.DonutChartDataDTO( p.nombre, SUM(e.costo) )  " +
            "FROM EstimacionAdmin e " +
            "JOIN ProyectoEntity p ON p.id = e.codProyecto " +
            "where e.codProyecto = :codProyecto " +
            "GROUP BY p.nombre")
    DonutChartDataDTO costoEstimacionXProyecto(int codProyecto);

    @Query(value = "SELECT new com.claro.gestionrecursosapi.estimaciones.dto.DonutChartDataDTO( p.nombre, SUM(e.cantidad)  )  " +
            "FROM EstimacionAdmin e " +
            "JOIN ProyectoEntity p ON p.id = e.codProyecto " +
            "where e.codProyecto = :codProyecto " +
            "GROUP BY p.nombre")
    DonutChartDataDTO horaEstimacionXProyecto(int codProyecto);

    @Query(value = "SELECT SUM((SUM(e.horas) * em.factorPunto) * em.valor) " +
            "FROM ProyectoEntity p " +
            "JOIN TareaEntity t ON t.codproyecto = p.id " +
            "JOIN EmpleadocontrolEntity e ON e.codtarea = t.id " +
            "JOIN EmpleadoVU em ON em.codempleado = e.codempleado " +
            "where p.id = :codProyecto " +
            "GROUP BY e.horas, em.factorPunto, em.valor")
    Long costoHoraReportadaXProyecto(Integer codProyecto);

    @Query(value = "SELECT new com.claro.gestionrecursosapi.estimaciones.dto.DonutChartDataDTO( CONCAT(per.nombre1, ' ', per.nombre2, ' ', per.apellido1, ' ', per.apellido2), ((SUM(e.horas)*ce.factorPunto) * ce.costoPunto)   )  " +
            "FROM ProyectoEntity p " +
            "JOIN TareaEntity t ON t.codproyecto = p.id " +
            "JOIN EmpleadocontrolEntity e ON e.codtarea = t.id " +
            "JOIN EmpleadoEntity emp ON emp.id = e.codempleado " +
            "JOIN CostoEmpleadoEntity ce ON ce.codEmpleado = emp.id " +
            "JOIN PersonaEntity per ON per.id = emp.codpersona " +
            "JOIN EstructuraorganizacionalEntity eo ON eo.id = emp.codestructuraorganizacional  " +
            "WHERE p.id = :codProyecto and ce.hasta is null AND emp.codestructuraorganizacional = :codestructuraDesarrollador " +
            "GROUP BY CONCAT(per.nombre1, ' ', per.nombre2, ' ', per.apellido1, ' ', per.apellido2), ce.factorPunto, ce.costoPunto " )
    List<DonutChartDataDTO> horaReportadaXEmpleado(Integer codProyecto, Integer codestructuraDesarrollador);

    @Query(value = "SELECT new com.claro.gestionrecursosapi.estimaciones.dto.EstimacionDownloadDTO(e.entregable, ce.perfil, e.preRequisito, e.cantidad, e.fechaInicio, e.fechaFin, e.costo, e.porcentaje) " +
            "FROM EstimacionAdmin e " +
            "JOIN CostoEstimacion ce on ce.id = e.perfil " +
            "WHERE e.codProyecto = :codProyecto")
    List<EstimacionDownloadDTO> downloadExcel(Integer codProyecto);

}