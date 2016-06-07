package com.vobi.team.dataaccess.dao;

import java.util.List;

import com.vobi.team.dataaccess.api.Dao;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtRol;


/**
* Interface for   VtRolDAO.
*
*/
public interface IVtRolDAO extends Dao<VtRol, Long> {
	 public List<VtRol> consultarRolesActivos();
}
