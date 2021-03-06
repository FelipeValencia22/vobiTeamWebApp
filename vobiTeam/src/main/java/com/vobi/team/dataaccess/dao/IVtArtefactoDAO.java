package com.vobi.team.dataaccess.dao;

import java.util.List;

import com.vobi.team.dataaccess.api.Dao;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtUsuarioArtefacto;


/**
* Interface for   VtArtefactoDAO.
*
*/
public interface IVtArtefactoDAO extends Dao<VtArtefacto, Long> {
	 public VtUsuarioArtefacto consultarUsuarioArtefactoPorUsuarioYArtefacto(Long codigoUsuario, Long codigoArtefacto);
	 public List<VtArtefacto> consultarArtefactosPorProyecto(Long codigoProyecto);
	 public List<VtArtefacto> todosLosArtefactosDeUnUsuario(Long usuCodigo);
	 public List<VtArtefacto> consultarTodosLosArtefactosDeUnaPila(Long codigoPila);
}
