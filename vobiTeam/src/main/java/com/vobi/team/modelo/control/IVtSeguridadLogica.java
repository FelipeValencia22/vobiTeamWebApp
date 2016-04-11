package com.vobi.team.modelo.control;

import com.vobi.team.modelo.VtUsuario;

public interface IVtSeguridadLogica {
	public VtUsuario autenticarUsuario(String login, String clave)throws Exception;

	public VtUsuario guardarUsuario(String login) throws Exception;
}
