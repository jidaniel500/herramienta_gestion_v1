package com.claro.gestionrecursosapi.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claro.gestionrecursosapi.base.model.RespuestaBase;
import com.claro.gestionrecursosapi.base.model.RespuestaCustomizada;
import com.claro.gestionrecursosapi.presupuesto.domain.PresupuestoService;
import com.claro.gestionrecursosapi.presupuesto.entity.PresupuestoEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@RestController
@RequestMapping("/api/v1/presupuesto")
public class PresupuestoController {
	@Autowired
	EntityManager entityManager;

	@Autowired
	private PresupuestoService service;

	@GetMapping
	public ResponseEntity<RespuestaBase> buscarTodo() {
		try {
			setSessionDatesFormat();
			Iterable<PresupuestoEntity> listasPersonas = service.findAll();
			RespuestaCustomizada<Iterable<PresupuestoEntity>> respuesta = new RespuestaCustomizada<>();
			respuesta.setCodigoEstatus(HttpStatus.OK.value());
			respuesta.setMensaje("Consulta exitosa");
			respuesta.setData(listasPersonas);
			return new ResponseEntity<RespuestaBase>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaBase respuestaBase = new RespuestaBase(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return new ResponseEntity<RespuestaBase>(respuestaBase, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaBase> buscarPorId(@PathVariable Integer id) {
		try {
			setSessionDatesFormat();
			Optional<PresupuestoEntity> personaEntity = service.findById(id);
			RespuestaCustomizada<PresupuestoEntity> respuesta = new RespuestaCustomizada<>();
			respuesta.setCodigoEstatus(HttpStatus.OK.value());
			respuesta.setMensaje("Consulta exitosa");
			respuesta.setData(personaEntity.get());
			return new ResponseEntity<RespuestaBase>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaBase respuestaBase = new RespuestaBase(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return new ResponseEntity<RespuestaBase>(respuestaBase, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<RespuestaBase> crear(@RequestBody PresupuestoEntity entity) {
		try {
			setSessionDatesFormat();
			PresupuestoEntity personaEntity = service.save(entity);
			RespuestaCustomizada<PresupuestoEntity> respuesta = new RespuestaCustomizada<>();
			respuesta.setCodigoEstatus(HttpStatus.CREATED.value());
			respuesta.setMensaje("Presupuesto creada");
			respuesta.setData(personaEntity);
			return new ResponseEntity<RespuestaBase>(respuesta, HttpStatus.CREATED);
		} catch (Exception e) {
			RespuestaBase respuestaBase = new RespuestaBase(HttpStatus.CONFLICT.value(), e.getMessage());
			return new ResponseEntity<RespuestaBase>(respuestaBase, HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaBase> actualizar(@PathVariable Integer id, @RequestBody PresupuestoEntity entity) {
		try {
			setSessionDatesFormat();
			PresupuestoEntity personaEntity = service.save(entity);
			RespuestaCustomizada<PresupuestoEntity> respuesta = new RespuestaCustomizada<>();
			respuesta.setCodigoEstatus(HttpStatus.OK.value());
			respuesta.setMensaje("Presupuesto actualizado");
			respuesta.setData(personaEntity);
			return new ResponseEntity<RespuestaBase>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaBase respuestaBase = new RespuestaBase(HttpStatus.CONFLICT.value(), e.getMessage());
			return new ResponseEntity<RespuestaBase>(respuestaBase, HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaBase> eliminar(@PathVariable Integer id) {

		try {
			service.delete(service.findById(id).get());
			RespuestaBase respuesta = new RespuestaBase();
			respuesta.setCodigoEstatus(HttpStatus.OK.value());
			respuesta.setMensaje("Se elimino registro");
			return new ResponseEntity<RespuestaBase>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaBase respuestaBase = new RespuestaBase(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return new ResponseEntity<RespuestaBase>(respuestaBase, HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Asigna el formato de fechas de trabajo, para consultar las vistas de DB
	 * soluci??n a error ORA-01843: not a valid month, el formato de fechas usado por las vistas actualmente es DD/MM/YYYY
	 */
	@Scheduled(fixedRate = 180000)
	private void setSessionDatesFormat() {
		try {
			Query query = entityManager.createNativeQuery("ALTER SESSION SET nls_date_format = 'DD/MM/YYYY'");

			query.getResultList();
		} catch (Exception e) {
			//System.out.println("session alter: " + ExceptionUtils.getStackTraceString(e));
		}
	}

}

