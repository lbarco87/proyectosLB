package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
class TestInyeccion {


	private static final Logger log = LogManager.getLogger(TestInyeccion.class);
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	
	
	@Test
	
	void test() {
		
		assertNotNull(entityManagerFactory, "el dato es nulo");
		
		
	}
	

}
