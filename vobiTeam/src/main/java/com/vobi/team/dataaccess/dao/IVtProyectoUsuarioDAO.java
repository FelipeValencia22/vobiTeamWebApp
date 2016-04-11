package com.vobi.team.dataaccess.dao;

import java.util.List;

import com.vobi.team.dataaccess.api.Dao;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtRol;


/**
* Interface for   VtProyectoUsuarioDAO.
*
*/
public interface IVtProyectoUsuarioDAO extends Dao<VtProyectoUsuario, Long> {
	 public VtProyectoUsuario consultarProyectoUsuarioPorProyectoYPorUsuario(Long proyectoId, Long usuarioId);
}
