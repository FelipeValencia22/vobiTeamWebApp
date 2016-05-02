package com.vobi.team.test.java;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.team.dataaccess.dao.IVtUsuarioArtefactoDAO;
import com.vobi.team.dataaccess.dao.VtUsuarioArtefactoDAO;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtUsuarioArtefacto;
import com.vobi.team.modelo.control.IVtArtefactoLogic;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class artefactosNoAsignadosAUsuarios {
	private static final Logger log = LoggerFactory.getLogger(artefactosNoAsignadosAUsuarios.class);
	
	@Autowired
	private IVtArtefactoLogic vtArtefactoLogic;
	
	
	@Autowired
	private IVtUsuarioArtefactoDAO vtUsuarioArtefactoDAO;
	
	
	
	
	@Test
	@Transactional(readOnly = true)
	public void test() {
		try {
			List<VtArtefacto> artefactosSources = vtArtefactoLogic.getVtArtefacto();
			List<VtUsuarioArtefacto> usuarioArtefacto = vtUsuarioArtefactoDAO
					.consultarUsuarioArtefactoPorUsuario(2L);
			if (usuarioArtefacto != null) {
				for (VtUsuarioArtefacto vtUsuarioArtefacto : usuarioArtefacto) {
					if (vtUsuarioArtefacto.getActivo().equals("S")) {
						artefactosSources.remove(vtUsuarioArtefacto.getVtArtefacto());
						log.info("Artefacto eliminado " + vtUsuarioArtefacto.getVtArtefacto().getTitulo());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
