package com.vobi.team.modelo.control;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vobi.team.dataaccess.dao.IVtUsuarioDAO;
import com.vobi.team.modelo.VtUsuario;

@Scope("singleton")
@Service
public class SeguridadLogica implements IVtSeguridadLogica {

	@Autowired
	private IVtUsuarioDAO vtUsuarioDAO;

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly =true)
	public VtUsuario autenticarUsuario(String login, String clave) throws Exception {
		String mensaje = "Usuario o clave inválida";
		VtUsuario vtUsuario = vtUsuarioDAO.consultarUsuarioUnicoPorLogin(login);
		if(vtUsuario==null){
			throw new Exception(mensaje);
		}
		if(vtUsuario.getClave().equals(clave)==false){
			throw new Exception(mensaje);
		}
		if(vtUsuario.getActivo().equalsIgnoreCase("N")==true){
			throw new Exception(mensaje);
		}
		return vtUsuario;
	}
	
	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly =true)
	public VtUsuario guardarUsuario(String login) throws Exception {
		String mensaje = "Usuario o clave inválida";
		VtUsuario vtUsuario = vtUsuarioDAO.consultarUsuarioUnicoPorLogin(login);
		if(vtUsuario==null){
			throw new Exception(mensaje);
		}
		if(vtUsuario.getActivo().equalsIgnoreCase("N")==true){
			throw new Exception(mensaje);
		}
		return vtUsuario;
	}

}
