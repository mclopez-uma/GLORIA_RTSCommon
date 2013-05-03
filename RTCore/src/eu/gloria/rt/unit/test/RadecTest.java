package eu.gloria.rt.unit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.gloria.rt.exception.RTException;
import eu.gloria.rt.unit.Radec;
import eu.gloria.tools.conversion.DegreeFormat;

public class RadecTest {
	
	Radec radec = null;
	
	Radec radecPositive = null;
	
	public RadecTest () throws RTException{
		radec = new Radec(286.37388305,21.1356912);
		radecPositive = new Radec("+80:01:02", DegreeFormat.HHMMSS, "+80:01:02" , DegreeFormat.DDMMSS);
	}

	@Test
	public void testGetDecGr() {		
		assertEquals("Result", 21, radec.getDecGr());
	}

	@Test
	public void testGetDecMM() {
		assertEquals("Result", 8, radec.getDecMM());
	}

	@Test
	public void testGetDecSS() {
		assertEquals(8.49, radec.getDecSS(), 0.02);
	}

	@Test
	public void testGetRaDecimal() {
		assertEquals(286.37388305, radec.getRaDecimal(), 0.02);
		
		assertEquals(1200.258, radecPositive.getRaDecimal(), 0.02);
	}

	@Test
	public void testGetDecDecimal() {
		assertEquals(21.1356912, radec.getDecDecimal(), 0.02);
		
		assertEquals(80.017, radecPositive.getDecDecimal(), 0.02);
	}

	@Test
	public void testGetRaHH() {
		assertEquals("Result", 19, radec.getRaHH());
	}

	@Test
	public void testGetRaMM() {
		assertEquals("Result", 5, radec.getRaMM());
	}

	@Test
	public void testGetRaSS() {
		assertEquals(29.73, radec.getRaSS(),0.02);
	}
	
	
	

}
