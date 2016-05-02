package com.vobi.team.dataaccess.dao;

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
}
