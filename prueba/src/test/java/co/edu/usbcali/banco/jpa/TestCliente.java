package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoDocumento;

class TestCliente {
	
	private final static Logger log=LoggerFactory.getLogger(TestCliente.class);
	
	static EntityManagerFactory entityManagerFactory=null;
	static EntityManager entityManager=null;
	
	BigDecimal clieId=new BigDecimal(142020);
	
	
	@Test
	@DisplayName("ConsultarCliente")
	void etest() {
		
		assertNotNull(entityManager, "El entitymanager es nulo");
		
		String jpql="SELECT cli FROM Cliente cli";
		
		List<Cliente> losClientes= entityManager.createQuery(jpql).getResultList();
		
		losClientes.forEach(cliente->{
			log.info("ID:"+cliente.getClieId());
			log.info("NOMBRE:"+cliente.getNombre());
		});
		
		/*
		for (Cliente cliente : losClientes) {
			log.info("ID:"+cliente.getClieId());
			log.info("NOMBRE:"+cliente.getNombre());
		}
		*/
		
		
	}
	
	@Test
	@DisplayName("BorrarCliente")
	void dtest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		entityManager.getTransaction().begin();
			entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ModificarCliente")
	void ctest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente.setActivo('N');
		cliente.setDireccion("Avenida Siempre Viva 123 C");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, new Long(2));
		assertNotNull(tipoDocumento,"El tipo de documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
			entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ConsultarClientePorId")
	void btest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente ya existe");
		
		log.info("Id:"+cliente.getClieId());
		log.info("Nombre:"+cliente.getNombre());
	}
	
	
	
	@Test
	@DisplayName("CreaCliente")
	void atest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");
		cliente=new Cliente();
		
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Avenida Siempre Viva 123");
		cliente.setEmail("hsimpson@gmail.com");
		cliente.setNombre("Homero J Simpson");
		cliente.setTelefono("555 555 5555");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class, new Long(2));
		assertNotNull(tipoDocumento,"El tipo de documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
			entityManager.persist(cliente);
		entityManager.getTransaction().commit();
	}
	
	@BeforeAll
	public static void iniciar() {
		log.info("Ejectuo el @BeforeAll");
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-logica");
		entityManager=entityManagerFactory.createEntityManager();
	}
	
	@AfterAll
	public static void finalizar() {
		log.info("Ejectuo el @AfterAll");
		entityManager.close();
		entityManagerFactory.close();
	}

	

	
	@BeforeEach
	public  void antes() {
		log.info("Antes de la prueba");
	}
	
	@AfterEach
	public  void despues() {
		log.info("Despues de la prueba");
	}
	
	
	

}
