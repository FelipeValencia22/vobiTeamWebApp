package com.vobi.team.send.email;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.utilities.FacesUtils;

@Repository("envioMensajePorElCorreo")
@Scope("singleton")
public class EnvioMensajePorElCorreo implements IEnvioMensajePorEmail {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage templateMessage;

	public void enviarMensaje(String from, String to, String subject, String body) throws Exception {

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(to);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(body);

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
