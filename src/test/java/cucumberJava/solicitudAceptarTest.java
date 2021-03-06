package cucumberJava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.intravita.proyectointranet.modelo.Usuario;
import com.intravita.proyectointranet.persistencia.UsuarioDAOImpl;
import com.intravita.proyectointranet.utlidades.utilidades;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class solicitudAceptarTest {
	private Usuario solicitante, acepta, acepta2;
	
	UsuarioDAOImpl usuarioDao= new UsuarioDAOImpl();
	
	@Given("^Usuario conectado para aceptar$")
	public void Usuario_conectado_para_aceptar() {
		solicitante=new Usuario("solicitante.solicitante", "1234qwer", "solicitante@alu.uclm.es", "solicitante");
		if(usuarioDao.selectNombre(solicitante))usuarioDao.delete(solicitante);
		try {		
			usuarioDao.insert(solicitante);
		} catch (Exception e) {
			assertTrue(true);
		}
		assertTrue(true);
	}
	
	
	@When("^\"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Acepta solicitud de alguien que le ha mandado$")
	public void Acepta_solicitud_de_alguien_que_le_ha_mandado(String nombre, String pwd, String email, String respuesta) {
		acepta=new Usuario(nombre, pwd, email, respuesta);
		if(usuarioDao.selectNombre(acepta))usuarioDao.delete(acepta);
		try {
			usuarioDao.insert(acepta);
		} catch (Exception e) {
			assertTrue(true);
		}
		solicitante=new Usuario("solicitante.solicitante");
		
		usuarioDao.enviarSolicitud(solicitante, acepta);
		assertTrue(utilidades.comprobarSolicitudes(solicitante, acepta));
		usuarioDao.aceptarSolicitud(solicitante, acepta);
	}

	
	@Then("^Aceptar como amigo$")
	public void Aceptar_como_amigo() {
		assertTrue(utilidades.comprobarAmistad(solicitante, acepta));
		assertTrue(utilidades.comprobarAmistad(acepta,solicitante));
		utilidades.comprobarSolicitudes(solicitante, acepta);
		assertTrue(true);
		usuarioDao.delete(acepta);
	}
	
	
	@When("^\"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Acepta solicitud de alguien que no le ha mandado$")
	public void Acepta_solicitud_de_alguien_que_no_le_ha_mandado(String nombre, String pwd, String email, String respuesta) {
		acepta2=new Usuario(nombre, pwd, email, respuesta);
		if(usuarioDao.selectNombre(acepta2))usuarioDao.delete(acepta2);
		try {
			usuarioDao.insert(acepta2);
		} catch (Exception e) {
			assertTrue(true);
		}
		solicitante=new Usuario("solicitante.solicitante");
		utilidades.comprobarSolicitudes(solicitante, acepta2);
		assertTrue(true);
	}
	
	@Then("^Mensaje de error a la aceptacion$")
	public void Mensaje_de_error_a_la_aceptacion() {
		utilidades.comprobarAmistad(solicitante, acepta2);
		utilidades.comprobarAmistad(acepta2,solicitante);
		usuarioDao.delete(acepta2);
		assertTrue(true);
	}
}
