package com.vobi.team.test.java;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.control.IVtArtefactoLogic;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class actualizarTiempoArtefacto {
	
	@Autowired
	private IVtArtefactoLogic vtArtefactoLogic;
	


	@Test
	public void test() {
	
		try {
			DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
			Calendar calendarFechaInicio = Calendar.getInstance();
			Calendar calendarFechaFin = Calendar.getInstance();
			
			String fechaInicio = "2016/15/04";
			String [] partes = fechaInicio.split("/");
			int añoFechaInicio = Integer.parseInt( partes[0]);
			int mesFechaInicio = Integer.parseInt(partes[1]);
			int diaFechaInicio = Integer.parseInt(partes[2]);
			
			calendarFechaInicio.set(añoFechaInicio, mesFechaInicio, diaFechaInicio);
			
			Date fechaIni = new Date();
			
			
			String fechaFin = "2014/18/04";
			String[] partesFin = fechaFin.split("/");
			int añoFechaFin = Integer.parseInt(partesFin[0]);
			int mesFechaFin = Integer.parseInt(partes[1]);
			int diaFechaFin = Integer.parseInt(partes[2]);
			
			calendarFechaFin.set(añoFechaFin, mesFechaFin, diaFechaFin);
			
			long milisFechaInicio = calendarFechaInicio.getTimeInMillis();
			long milisFechaFin = calendarFechaFin.getTimeInMillis();
			
			long diferencia = (milisFechaInicio-milisFechaFin)/(60*1000);
			long diferenciaHoras = (milisFechaInicio-milisFechaFin)/(60*60*1000);
			long diferenciaSegundos = (milisFechaInicio-milisFechaFin)/1000;
			System.out.println("Diferencia de las fechas en segundos = " + diferenciaSegundos);
			System.out.println("Diferencia de las fechas en minutos = " + diferencia);
			System.out.println("Diferencia de las fechas en horas = " + diferenciaHoras);

			/**	
			final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
			final long MINUTOS_PER_DAY = 60*60*1000; //Milisegundos al día 
			java.util.Date hoy = new Date(); //Fecha de hoy 
			    
	
			int año = 2016; int mes = 04; int dia = 18; //Fecha anterior 
			Calendar calendar = new GregorianCalendar(año, mes-1, dia); 
			java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

			long diferencia = ( hoy.getTime() - fecha.getTime() )/MINUTOS_PER_DAY; 
			System.out.println("Diferencia "+diferencia); 
			**/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
