package com.claro.gestionrecursosapi;

import com.claro.gestionrecursosapi.devops.domain.AutenticacionService;
import com.claro.gestionrecursosapi.devops.domain.UsuariosInfoDevops;
import com.claro.gestionrecursosapi.estructura.domain.EstructuraOrganizacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.util.Assert;



@SpringBootTest
class GestionRecursosApiApplicationTests {


	@Autowired
	private EstructuraOrganizacionService service;

	/*
	 * @Test void contextLoads() { try { ClientResponse responseclient =
	 * authservice.getClientWebAutenticado().get() .uri(
	 * "https://vssps.dev.azure.com/alminspiraclaro/_apis/graph/users?api-version=6.0-preview.1")
	 * .exchange().block(); HttpStatus status = responseclient.statusCode(); if
	 * (status == HttpStatus.OK) { List<Usuarios> lista =
	 * responseclient.bodyToFlux(Usuarios.class).collectList().block(); } } catch
	 * (Exception ex) { System.out.println(ex.getMessage()); } }
	 * 
	 * @Test public void fechaTest() {
	 * 
	 * try { DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
	 * 
	 * // Set the formatter to use a different timezone
	 * formatter.setTimeZone(TimeZone.getTimeZone("EST"));
	 * 
	 * Calendar calendar = GregorianCalendar.getInstance(); Date fecha = new
	 * Date(Long.valueOf("1649826000000")); calendar.setTime(fecha); String format =
	 * formatter.format(fecha);
	 * 
	 * // SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-05:00'");
	 * sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	 * 
	 * String sdfformat = sdf.format(fecha);
	 * 
	 * SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	 * isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); String cstring =
	 * calendar.toString(); isoFormat.parse(cstring);
	 * 
	 * Timestamp ts = new Timestamp(fecha.getTime()); String ls = ts.toString();
	 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	 * LocalDateTime now = LocalDateTime.now(); calendar.setTime(fecha); String
	 * fechadtf = dtf.format(fecha.toInstant()); String d = ""; } catch (Exception
	 * e) { System.out.println(e.getMessage()); }
	 * 
	 * }
	 */

    @Test
    public void testCadena(){
		
		String cadena = "000000002000000000060000000028";
		String nuevacada =  cadena.substring(0,10);
		Integer cadenaparse = Integer.parseInt(nuevacada);
		Assert.notNull(cadenaparse,"prueba exitosa");

    }
}
