package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import co.edu.usbcali.banco.modelo.TipoDocumento;

class TestCliente {
	private final static Logger log = LogManager.getLogger(TestCliente.class);

	static EntityManagerFactory entityManagerFactory = null;
	static EntityManager entityManager = null;
	BigDecimal clieId = new BigDecimal(151515);
	// long tdocId = new Long(2L);

	@Test
	@DisplayName("ConsultarClientePorId")
	void btest() {
		assertNotNull(entityManager, "el entitymanager es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "el cliente ya existe");

		log.info("id:" + cliente.getClieId());
		log.info("Nombre:" + cliente.getNombre());
	}

	@Test
	@DisplayName("CrearCliente")
	void atest() {
		assertNotNull(entityManager, "el entitymanager es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "el cliente ya existe");
		cliente = new Cliente();

		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("avenida siempre viva 123");
		cliente.setEmail("pruebabanco@gmail.com");
		cliente.setNombre("homero");
		cliente.setTelefono("222 222 222");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, new Long(2L));
		assertNotNull(tipoDocumento, "el el tipo de documento no existe");
		cliente.setTipoDocumento(tipoDocumento);

		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();

		// log.info("se ejecuto la prueba A");
	}

	@Test
	@DisplayName("ModificarCliente")
	void ctest() {
		assertNotNull(entityManager, "el entitymanager es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "el cliente ya existe");
		cliente = new Cliente();

		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("avenida siempre viva 123");
		cliente.setEmail("pruebabanco@gmail.com");
		cliente.setNombre("homero");
		cliente.setTelefono("222 222 222");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, new Long(2L));
		assertNull(tipoDocumento, "el el tipo de documento no existe");
		cliente.setTipoDocumento(tipoDocumento);

		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();

		// log.info("se ejecuto la prueba A");
	}

	@Test
	@DisplayName("BorrarCliente")
	void dtest() {

		assertNotNull(entityManager, "el entitymanager es nulo");
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "el cliente ya existe");

		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();

		// log.info("se ejecuto la prueba A");
	}

	@Test
	@DisplayName("ConsultarCliente")
	void etest() {

		assertNotNull(entityManager, "el entitymanager es nulo");

		String jpql = "SELECT cli FROM Cliente cli";

		List<Cliente> losclientes = entityManager.createQuery(jpql).getResultList();

		losclientes.forEach(cliente -> {// esta parte es lo mismo del que hace el for se implementa en java 8

			log.info("id:" + cliente.getClieId());
			log.info("Nombre:" + cliente.getNombre());

		});

		/*
		 * for (Cliente cliente : losclientes) {
		 * 
		 * log.info("id:"+cliente.getClieId()); log.info("Nombre:"+cliente.getNombre());
		 * }
		 */
	}

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
