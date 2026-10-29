package ar.com.kelia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KeliaApplicationTests {

	@Test
	void contextLoads() {
	}

	/**
	 * Prueba que el método main de la aplicación se ejecute correctamente.
	 * Esto asegura que la aplicación pueda arrancar sin errores desde el método
	 * main.
	 */
	@Test
	void mainMethodRuns() {
		KeliaApplication.main(new String[] {});
	}

}
