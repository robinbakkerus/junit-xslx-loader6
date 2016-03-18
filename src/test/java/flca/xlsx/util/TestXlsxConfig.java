package flca.xlsx.util;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Test;

import flca.xlsx.util.XlsxConfig;
import flca.xlsx.util.XlsxSetValueException;

public class TestXlsxConfig {

	@Test
	public void test1() {
		SimpleDateFormat df1 = XlsxConfig.getDateFormats().get(0);
		SimpleDateFormat df2 = XlsxConfig.defaultDateFormat();
		assertTrue(df1.equals(df2));
	}

	@Test
	public void test2() throws XlsxSetValueException {
		SimpleDateFormat df1 = XlsxConfig.findDateFormat("02-02-2014");
		SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
		assertTrue(df1.equals(df2));
	}

	@Test
	public void test3() throws XlsxSetValueException {
		SimpleDateFormat df1 = XlsxConfig.findDateFormat("02-02-2014 12:15");
		SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		assertTrue(df1.equals(df2));
	}
	
	@Test
	public void test4() throws XlsxSetValueException {
		SimpleDateFormat df1 = XlsxConfig.findDateFormat("02-02-2014 12:15:40");
		SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		assertTrue(df1.equals(df2));
	}
	
	@Test
	public void test5() throws XlsxSetValueException {
		SimpleDateFormat df1 = XlsxConfig.findDateFormat("02-02-2014 12:15:40");
		SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		assertTrue(df1.equals(df2));
	}
	
	@Test
	public void test6() {
		int n1 = XlsxConfig.getDateFormats().size();
		XlsxConfig.setDateFormats(new ArrayList<SimpleDateFormat>());
		int n2 = XlsxConfig.getDateFormats().size();
		XlsxConfig.reset();
		int n3 = XlsxConfig.getDateFormats().size();
		assertTrue(n1==n3 && n2 == 0);
		
	}
}