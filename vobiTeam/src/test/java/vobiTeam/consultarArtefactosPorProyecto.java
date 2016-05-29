package vobiTeam;

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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class consultarArtefactosPorProyecto {
	
	@Autowired
	private IVtArtefactoDAO vtArtefactoDAO;
	
	private static final Logger log = LoggerFactory.getLogger(consultarArtefactosPorProyecto.class);
	 

	@Test
	@Transactional(readOnly = true)
	public void testA() {
		List<VtArtefacto> losArtefactos = vtArtefactoDAO.consultarArtefactosPorProyecto(2L);
		int contador = 1;
		for (VtArtefacto vtArtefacto : losArtefactos) {
			log.info("Título del artefacto " + vtArtefacto.getTitulo() + ":" +contador );
			contador++;
		}
	}

}
