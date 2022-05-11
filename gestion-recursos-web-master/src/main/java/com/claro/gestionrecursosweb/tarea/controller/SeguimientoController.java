package com.claro.gestionrecursosweb.tarea.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.claro.gestionrecursosweb.base.controller.BaseController;
import com.claro.gestionrecursosweb.base.domain.ApiService;
import com.claro.gestionrecursosweb.empleado.dto.EmpleadoVUDto;
import com.claro.gestionrecursosweb.proyecto.dto.ProyectoDto;
import com.claro.gestionrecursosweb.tarea.dto.TareaDto;
import com.claro.gestionrecursosweb.tarea.dto.TareaTipoDto;

@Controller
@RequestMapping("/Seguimiento")
public class SeguimientoController extends BaseController {

	@Autowired
	private ApiService<ProyectoDto, Integer> serviceProyecto;
	@Autowired
	private ApiService<TareaTipoDto, Integer> serviceTareaTipo;
	@Autowired
	private ApiService<EmpleadoVUDto, Integer> serviceEmpleadoVU;
	
	@GetMapping("/Calendario")
	public String calendario(Model modelo) {
		mostrarTitulosYActiveNav(modelo, "Seguimiento", "Calendario", "Seguimiento");
		return "seguimiento/Calendario";
	}
	
	@GetMapping("/Gantt")
	public String gannt(Model modelo) {
		serviceProyecto.setapiservicename(dominio_proyecto);
		Iterable<ProyectoDto> proyectos = serviceProyecto.findAll(ProyectoDto.class);
		
		modelo.addAttribute("proyectos", proyectos);
		modelo.addAttribute("modelotarea", new TareaDto());
		modelo.addAttribute("cl_ocultarmiga", true);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		modelo.addAttribute("fechainicio", calendar.getTime());
		
		calendar = Calendar.getInstance();
		modelo.addAttribute("fechafin", calendar.getTime());
		
		cargarListas(modelo);
		mostrarTitulosYActiveNav(modelo, "Seguimiento", "Gantt", "Seguimiento");
		return "seguimiento/Gantt";
	}
	
	private void cargarListas(Model modelo) {
		serviceTareaTipo.setapiservicename(dominio_tareatipo);
		Iterable<TareaTipoDto> tareatipos = serviceTareaTipo.findAll(TareaTipoDto.class);
		modelo.addAttribute("tareatipos", tareatipos);
		
		serviceEmpleadoVU.setapiservicename(dominio_empleado + "/vu");
		Iterable<EmpleadoVUDto> empleados = serviceEmpleadoVU.findAll(EmpleadoVUDto.class);
		modelo.addAttribute("empleados", empleados);
	}
}
