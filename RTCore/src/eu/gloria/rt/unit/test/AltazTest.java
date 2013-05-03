package eu.gloria.rt.unit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Altaz;

public class AltazTest {

	Altaz altaz = null;
	
	public AltazTest () throws RTException{
		altaz = new Altaz(79.5731,260.536);
	}
	
	
	@Test
	public void testGetAltDecimal() {
		assertEquals(79.5731, altaz.getAltDecimal(), 0.01);
	}

	@Test
	public void testGetAzDecimal() {
		assertEquals(260.536, altaz.getAzDecimal(), 0.01);
	}

	@Test
	public void testGetAltDD() {
		assertEquals("Result", 79, altaz.getAltDD());
	}

	@Test
	public void testGetAltMM() {
		assertEquals("Result", 34, altaz.getAltMM());
	}

	@Test
	public void testGetAltSS() {
		assertEquals(23.16, altaz.getAltSS(), 0.02);
	}

	@Test
	public void testGetAzDD() {
		assertEquals("Result", 260, altaz.getAzDD());
	}

	@Test
	public void testGetAzMM() {
		assertEquals("Result", 32, altaz.getAzMM());
	}

	@Test
	public void testGetAzSS() {
		assertEquals(9.60, altaz.getAzSS(), 0.02);
	}

}
