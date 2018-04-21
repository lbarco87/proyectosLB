package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoUsuario;
import co.edu.usbcali.banco.modelo.Usuario;

class TestUsuario {
	
	private final static Logger log = LogManager.getLogger(TestUsuario.class);

	static EntityManagerFactory entityManagerFactory = null;
	static EntityManager entityManager = null;
	String usuUsuario = new String ("calico");

	@Test
	@DisplayName("CrearUsuario")
	void test() {
		
		assertNotNull(entityManager, "el entitymanager es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(usuario, "el usuario ya existe");
		usuario = new Usuario();
		
		usuario.setActivo('S');
		usuario.setClave("Mnbg5T");
		usuario.setIdentificacion(new BigDecimal(679855));
		usuario.setNombre("Lina Barco");
		usuario.setUsuUsuario("calico");
		
		TipoUsuario tipousuario = entityManager.find(TipoUsuario.class, new Long(1));
		assertNotNull(tipousuario, "el tipo de usuario no existe");
		usuario.setTipoUsuario(tipousuario);
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		
	}
	
	@Test
	@DisplayName("ConsultarUsuarioPorId")
	void atest() {
		
		assertNotNull(entityManager, "el entitymanager es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "el usuario ya existe");

		log.info("usuUsuario:" + usuario.getUsuUsuario());
		log.info("Nombre:" + usuario.getNombre());
	}
	
	@Test
	@DisplayName("ModificarUsuario")
	void btest() {
		assertNotNull(entityManager, "el entitymanager es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "el usuario ya existe");

		usuario = new Usuario();

		usuario.setActivo('N');
		usuario.setClave("Mnbg5T");
		usuario.setIdentificacion(new BigDecimal(679855));
		usuario.setNombre("Lina M Barco");
		usuario.setUsuUsuario("calico");

		TipoUsuario tipousuario = entityManager.find(TipoUsuario.class, new Long(1));
		assertNull(tipousuario, "el tipo de usuario no existe");
		
		usuario.setTipoUsuario(tipousuario);

		entityManager.getTransaction().begin();
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
		
	}

	@Test
	@DisplayName("BorrarCliente")
	void ctest() {

		assertNotNull(entityManager, "el entitymanager es nulo");
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "el usuario ya existe");

		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
		entityManager.getTransaction().commit();

	}
	
/*	@Test
	@DisplayName("ConsultarUsuario")
	void dtest() {

		assertNotNull(entityManager, "el entitymanager es nulo");

		String jpql = "SELECT usu FROM Usuario usu";
		
		List<Usuario> losusuarios = entityManager.createQuery(jpql).getResultList();
		
		losusuarios.forEach(usuario->{
			log.info("usuUsuario:" + usuario.getUsuUsuario());
			log.info("Nombre:" + usuario.getNombre()); 
		});*/


		/*losusuarios.forEach(usuario->{// esta parte es lo mismo del que hace el for se implementa en java 8

			log.info("usuUsuario:" + usuario.getUsuUsuario());
			log.info("Nombre:" + usuario.getNombre()); 
			
		}

		 );*/	
	
	
	@BeforeAll
	public static void iniciar() {

		log.info("ejecuto el beforeAll");
		entityManagerFactory = Persistence.createEntityManagerFactory("banco-logica");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@AfterAll
	public static void finalizar() {

		log.info("ejecuto el AfterAll");
		entityManager.close();
		entityManagerFactory.close();
	}
	/**/

	@BeforeEach
	public void antes() {

		log.info("antes el BeforeEach");
	}

	@AfterEach
	public void despues() {

		log.info("despues el AfterEach");

	}

}
