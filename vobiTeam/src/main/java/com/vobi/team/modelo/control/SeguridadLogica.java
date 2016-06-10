package com.vobi.team.modelo.control;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.vobi.team.dataaccess.dao.IVtUsuarioDAO;
import com.vobi.team.dataaccess.dao.IVtUsuarioRolDAO;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.send.email.IEnvioMensajePorEmail;

@Scope("singleton")
@Service
public class SeguridadLogica implements IVtSeguridadLogica {

	@Autowired
	private IVtUsuarioDAO vtUsuarioDAO;

	@Autowired
	private IVtUsuarioRolDAO vtUsuarioRolDAO;
	@Autowired
	private static BeanFactory beanFactory;

	@SuppressWarnings("unused")
	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public VtUsuario autenticarUsuario(String login, String clave) throws Exception {
		try {
			int contador = 0;
			String mensaje = "Correo electrónico o contraseña inválida";
			VtUsuario vtUsuario = vtUsuarioDAO.consultarUsuarioUnicoPorLogin(login);
			List<VtUsuarioRol> usuRol = vtUsuarioRolDAO.consultarRolUsuarioPorUsuario(vtUsuario.getUsuaCodigo());
			for (VtUsuarioRol vtUsuarioRol : usuRol) {
				if(vtUsuarioRol.getActivo().equals("N")){
					contador++;
					if(contador==usuRol.size()){
						throw new Exception(mensaje);
					}
				}
			}
			if (vtUsuario == null) {
				throw new Exception(mensaje);
			}
			if (vtUsuario.getClave().equals(clave) == false) {
				throw new Exception(mensaje);
			}
			if (vtUsuario.getActivo().equalsIgnoreCase("N") == true) {
				throw new Exception(mensaje);
			}

			return vtUsuario;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public String guardarUsuario(String login) throws Exception {
		String mensaje = "Lo sentimos, no existe este correo en el sistema";
		VtUsuario vtUsuario = vtUsuarioDAO.consultarUsuarioUnicoPorLogin(login);
		try {
			if (vtUsuario == null) {
				throw new Exception(mensaje);
			}
			if (vtUsuario.getActivo().equalsIgnoreCase("N") == true) {
				throw new Exception(mensaje);
			}
			enviarMensajeAlCorreo("manuelplaza719@gmail.com", vtUsuario.getLogin(), "Restauración de cuenta",
					"Se enviado una notificación de restauración de contraseña para este cuenta, la nueva contraseña es "
							+ vtUsuario.getNombre() + vtUsuario.getLogin());
			vtUsuario.setClave(vtUsuario.getNombre() + vtUsuario.getLogin());

		} catch (Exception e) {
			throw e;
		}

		return "";
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public void enviarMensajeAlCorreo(String from, String to, String subject, String body) throws Exception {
		beanFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
		IEnvioMensajePorEmail enviarMensajePorEmail = (IEnvioMensajePorEmail) beanFactory
				.getBean(IEnvioMensajePorEmail.class);
		enviarMensajePorEmail.enviarMensaje(from, to, subject, body);
		new Thread() {
			@SuppressWarnings("unused")
			public void enviaCorreos() throws Exception {
				try {
					enviarMensajePorEmail.enviarMensaje(from, to, subject, body);
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
				}

			}
		}.start();

	}

}
