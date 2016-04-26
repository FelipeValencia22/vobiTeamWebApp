package com.vobi.team.test.java;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.team.dataaccess.dao.IVtArtefactoDAO;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.control.IVtArtefactoLogic;
import com.vobi.team.modelo.control.IVtUsuarioLogic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class artefactosNoAsignadosASprintTest {
	
	@Autowired
	private IVtArtefactoLogic vtArtefactoLogic;
	
	@Autowired
	private IVtUsuarioLogic usuarioLogica;
	
	private static final Logger log = LoggerFactory.getLogger(artefactosNoAsignadosASprintTest.class);
	
	
	@Test
	public void testA() throws Exception{
		assertNotNull(vtArtefactoLogic);

		List<VtArtefacto> listaArtefacto = vtArtefactoLogic.getVtArtefacto();
		for (VtArtefacto vtArtefacto : listaArtefacto) {
				
	
				log.info("Titulo del artefacto "  + vtArtefacto.getTitulo());
	
		}
		
		
	}

	@Test
	public void testE() throws Exception {

		assertNotNull(usuarioLogica);

		List<VtUsuario> losUsuarios = usuarioLogica.getVtUsuario();

		for (VtUsuario vtUsuario : losUsuarios) {
			log.info(" Codigo del usuario " + vtUsuario.getUsuaCodigo() );
			log.info(" Nombre de usuario" +  vtUsuario.getNombre());
		}

	}

}
