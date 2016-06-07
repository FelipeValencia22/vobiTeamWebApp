package com.vobi.team.modelo.control;

import com.vobi.team.dataaccess.dao.*;

import com.vobi.team.exceptions.*;

import com.vobi.team.modelo.*;
import com.vobi.team.modelo.dto.VtUsuarioDTO;
import com.vobi.team.send.email.IEnvioMensajePorEmail;
import com.vobi.team.utilities.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Zathura Code Generator http://zathuracode.org www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("VtUsuarioLogic")
public class VtUsuarioLogic implements IVtUsuarioLogic {
	private static final Logger log = LoggerFactory.getLogger(VtUsuarioLogic.class);

	/**
	 * DAO injected by Spring that manages VtUsuario entities
	 *
	 */
	@Autowired
	private IVtUsuarioDAO vtUsuarioDAO;

	/**
	 * DAO injected by Spring that manages VtProyectoUsuario entities
	 *
	 */
	@Autowired
	private IVtProyectoUsuarioDAO vtProyectoUsuarioDAO;
	
	private static BeanFactory beanFactory;
	/**
	 * DAO injected by Spring that manages VtUsuarioArtefacto entities
	 *
	 */
	@Autowired
	private IVtUsuarioArtefactoDAO vtUsuarioArtefactoDAO;

	/**
	 * DAO injected by Spring that manages VtUsuarioRol entities
	 *
	 */
	@Autowired
	private IVtUsuarioRolDAO vtUsuarioRolDAO;

	/**
	 * Logic injected by Spring that manages VtEmpresa entities
	 *
	 */
	@Autowired
	IVtEmpresaLogic logicVtEmpresa1;

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validate given email with regular expression.
	 * 
	 * @param email
	 *            email for validation
	 * @return true valid email, otherwise false
	 */
	public static boolean validateEmail(String email) {

		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	@Transactional(readOnly = true)
	public List<VtUsuario> getVtUsuario() throws Exception {
		log.debug("finding all VtUsuario instances");

		List<VtUsuario> list = new ArrayList<VtUsuario>();

		try {
			list = vtUsuarioDAO.findAll();
		} catch (Exception e) {
			log.error("finding all VtUsuario failed", e);
			throw new ZMessManager().new GettingException(ZMessManager.ALL + "VtUsuario");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveVtUsuario(VtUsuario entity) throws Exception {
		log.debug("saving VtUsuario instance");

		try {

			if (entity == null) {
				log.error("La entidad es nula");
			}
			if (entity.getVtEmpresa()==null) {
				throw new Exception("Seleccionar la empresa a la que pertenecera el nuevo usuario");
			}

			if (entity.getLogin().trim().equals("") || entity.getLogin().isEmpty()) {
				throw new Exception("El campo para el correo electrónico es obligatorio, digite un correo electrónico");
			}
			if (!validateEmail(entity.getLogin().toString().trim())) {
				throw new Exception("Lo sentimos, el formato de escritura del correo es incorrecto");
			}
			if (entity.getNombre().trim().equals("") || entity.getNombre().isEmpty()) {
				throw new Exception("El campo para el nombre del usuario es obligatorio, digite el nombre por favor.");
			}
			if (entity.getClave().trim().equals("") || entity.getClave().isEmpty()) {
				throw new Exception("La clave es obligatoria");
			}


			VtUsuario usuarioSession = vtUsuarioDAO.findById(entity.getUsuCreador());
			enviarMensajeAlCorreo(usuarioSession.getLogin(), entity.getLogin(), "Creación de cuenta",
					"Bienvenido, se le informa que se le ha creado su cuenta para vobiTeam con los siguientes Datos \n1.Contraseña asignada : "
							+ entity.getClave().toString().trim() + "\n2. Dirección de correo electrónico : "
							+ entity.getLogin()
							+ "\n\n\n Nota: Cualquier inquietud generar una no conformidad en www.vobiteam.com");
			
			
			vtUsuarioDAO.save(entity);

			log.debug("save VtUsuario successful");
		} catch (Exception e) {
			log.error("save VtUsuario failed", e);
			throw e;
		} finally {
		}
	}
	@Async
	public void enviarMensajeAlCorreo(String from, String to, String subject, String body) throws Exception {
		beanFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
		IEnvioMensajePorEmail enviarMensajePorEmail = (IEnvioMensajePorEmail) beanFactory
				.getBean(IEnvioMensajePorEmail.class);
		log.info("Entro al metodo de enviar correos");
		enviarMensajePorEmail.enviarMensaje(from, to, subject, body);
		new Thread() {
			@SuppressWarnings("unused")
			public void enviaCorreos() throws Exception {
				log.info("Enviando mensaje al correo");
				try {
					enviarMensajePorEmail.enviarMensaje(from, to, subject, body);
				} catch (Exception e) {
					log.error(e.getMessage());
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
				}

			}
		}.start();

	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteVtUsuario(VtUsuario entity) throws Exception {
		log.debug("deleting VtUsuario instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("VtUsuario");
		}

		if (entity.getUsuaCodigo() == null) {
			throw new ZMessManager().new EmptyFieldException("usuaCodigo");
		}

		List<VtProyectoUsuario> vtProyectoUsuarios = null;
		List<VtUsuarioArtefacto> vtUsuarioArtefactos = null;
		List<VtUsuarioRol> vtUsuarioRols = null;

		try {
			vtProyectoUsuarios = vtProyectoUsuarioDAO.findByProperty("vtUsuario.usuaCodigo", entity.getUsuaCodigo());

			if (Utilities.validationsList(vtProyectoUsuarios) == true) {
				throw new ZMessManager().new DeletingException("vtProyectoUsuarios");
			}

			vtUsuarioArtefactos = vtUsuarioArtefactoDAO.findByProperty("vtUsuario.usuaCodigo", entity.getUsuaCodigo());

			if (Utilities.validationsList(vtUsuarioArtefactos) == true) {
				throw new ZMessManager().new DeletingException("vtUsuarioArtefactos");
			}

			vtUsuarioRols = vtUsuarioRolDAO.findByProperty("vtUsuario.usuaCodigo", entity.getUsuaCodigo());

			if (Utilities.validationsList(vtUsuarioRols) == true) {
				throw new ZMessManager().new DeletingException("vtUsuarioRols");
			}

			vtUsuarioDAO.delete(entity);

			log.debug("delete VtUsuario successful");
		} catch (Exception e) {
			log.error("delete VtUsuario failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateVtUsuario(VtUsuario entity) throws Exception {
		log.debug("updating VtUsuario instance");

		try {
			/*
			 * if (entity == null) { throw new ZMessManager().new
			 * NullEntityExcepcion("VtUsuario"); }
			 * 
			 * if (entity.getVtEmpresa() == null) { throw new ZMessManager().new
			 * ForeignException("vtEmpresa"); }
			 * 
			 * if (entity.getActivo() == null) { throw new ZMessManager().new
			 * EmptyFieldException("activo"); }
			 * 
			 * if ((entity.getActivo() != null) &&
			 * (Utilities.checkWordAndCheckWithlength(entity.getActivo(), 1) ==
			 * false)) { throw new ZMessManager().new
			 * NotValidFormatException("activo"); }
			 * 
			 * if (entity.getClave() == null) { throw new ZMessManager().new
			 * EmptyFieldException("clave"); }
			 * 
			 * if ((entity.getClave() != null) &&
			 * (Utilities.checkWordAndCheckWithlength(entity.getClave(), 255) ==
			 * false)) { throw new ZMessManager().new
			 * NotValidFormatException("clave"); }
			 * 
			 * if (entity.getFechaCreacion() == null) { throw new
			 * ZMessManager().new EmptyFieldException( "fechaCreacion"); }
			 * 
			 * if (entity.getLogin() == null) { throw new ZMessManager().new
			 * EmptyFieldException("login"); }
			 * 
			 * if ((entity.getLogin() != null) &&
			 * (Utilities.checkWordAndCheckWithlength(entity.getLogin(), 255) ==
			 * false)) { throw new ZMessManager().new
			 * NotValidFormatException("login"); }
			 * 
			 * if (entity.getNombre() == null) { throw new ZMessManager().new
			 * EmptyFieldException("nombre"); }
			 * 
			 * if ((entity.getNombre() != null) &&
			 * (Utilities.checkWordAndCheckWithlength(entity.getNombre(), 255)
			 * == false)) { throw new ZMessManager().new
			 * NotValidFormatException("nombre"); }
			 * 
			 * if (entity.getUsuaCodigo() == null) { throw new
			 * ZMessManager().new EmptyFieldException("usuaCodigo"); }
			 * 
			 * if (entity.getVtEmpresa().getEmprCodigo() == null) { throw new
			 * ZMessManager().new EmptyFieldException( "emprCodigo_VtEmpresa");
			 * }
			 */
			vtUsuarioDAO.update(entity);

			log.debug("update VtUsuario successful");
		} catch (Exception e) {
			log.error("update VtUsuario failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<VtUsuarioDTO> getDataVtUsuario() throws Exception {
		try {
			List<VtUsuario> vtUsuario = vtUsuarioDAO.findAll();

			List<VtUsuarioDTO> vtUsuarioDTOA = new ArrayList<VtUsuarioDTO>();

			for (VtUsuario vtUsuarioTmp : vtUsuario) {
				VtUsuarioDTO vtUsuarioDTOActivo = new VtUsuarioDTO();

				if (vtUsuarioTmp.getActivo().toString().trim().equalsIgnoreCase("S")) {
					vtUsuarioDTOActivo.setUsuaCodigo(vtUsuarioTmp.getUsuaCodigo());

					vtUsuarioDTOActivo.setActivo((vtUsuarioTmp.getActivo() != null) ? vtUsuarioTmp.getActivo()+"i" : null);

					vtUsuarioDTOActivo.setClave((vtUsuarioTmp.getClave() != null) ? vtUsuarioTmp.getClave() : null);

					vtUsuarioDTOActivo.setFechaCreacion(vtUsuarioTmp.getFechaCreacion());

					vtUsuarioDTOActivo.setFechaModificacion(vtUsuarioTmp.getFechaModificacion());

					vtUsuarioDTOActivo.setLogin((vtUsuarioTmp.getLogin() != null) ? vtUsuarioTmp.getLogin() : null);

					vtUsuarioDTOActivo.setNombre((vtUsuarioTmp.getNombre() != null) ? vtUsuarioTmp.getNombre() : null);

					vtUsuarioDTOActivo.setUsuCreador(
							(vtUsuarioTmp.getUsuCreador() != null) ? vtUsuarioTmp.getUsuCreador() : null);

					vtUsuarioDTOActivo.setUsuModificador(
							(vtUsuarioTmp.getUsuModificador() != null) ? vtUsuarioTmp.getUsuModificador() : null);

					vtUsuarioDTOActivo.setEmprCodigo_VtEmpresa((vtUsuarioTmp.getVtEmpresa().getEmprCodigo() != null)
							? vtUsuarioTmp.getVtEmpresa().getEmprCodigo() : null);

					vtUsuarioDTOActivo.setNombre_VtEmpresa(vtUsuarioTmp.getVtEmpresa().getNombre());

					vtUsuarioDTOA.add(vtUsuarioDTOActivo);
				}

			}

			return vtUsuarioDTOA;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public List<VtUsuarioDTO> getDataVtUsuarioInactivo() throws Exception {
		try {
			List<VtUsuario> vtUsuario = vtUsuarioDAO.findAll();

			List<VtUsuarioDTO> vtUsuarioDTOI = new ArrayList<VtUsuarioDTO>();

			for (VtUsuario vtUsuarioTmp : vtUsuario) {
				VtUsuarioDTO vtUsuarioDTOInactivo = new VtUsuarioDTO();

				if (vtUsuarioTmp.getActivo().toString().trim().equalsIgnoreCase("N")) {

					vtUsuarioDTOInactivo.setUsuaCodigo(vtUsuarioTmp.getUsuaCodigo());

					vtUsuarioDTOInactivo
							.setActivo((vtUsuarioTmp.getActivo() != null) ? vtUsuarioTmp.getActivo()+"o" : null);

					vtUsuarioDTOInactivo.setClave((vtUsuarioTmp.getClave() != null) ? vtUsuarioTmp.getClave() : null);

					vtUsuarioDTOInactivo.setFechaCreacion(vtUsuarioTmp.getFechaCreacion());

					vtUsuarioDTOInactivo.setFechaModificacion(vtUsuarioTmp.getFechaModificacion());

					vtUsuarioDTOInactivo.setLogin((vtUsuarioTmp.getLogin() != null) ? vtUsuarioTmp.getLogin() : null);

					vtUsuarioDTOInactivo
							.setNombre((vtUsuarioTmp.getNombre() != null) ? vtUsuarioTmp.getNombre() : null);

					vtUsuarioDTOInactivo.setUsuCreador(
							(vtUsuarioTmp.getUsuCreador() != null) ? vtUsuarioTmp.getUsuCreador() : null);

					vtUsuarioDTOInactivo.setUsuModificador(
							(vtUsuarioTmp.getUsuModificador() != null) ? vtUsuarioTmp.getUsuModificador() : null);

					vtUsuarioDTOInactivo.setEmprCodigo_VtEmpresa((vtUsuarioTmp.getVtEmpresa().getEmprCodigo() != null)
							? vtUsuarioTmp.getVtEmpresa().getEmprCodigo() : null);

					vtUsuarioDTOInactivo.setNombre_VtEmpresa(vtUsuarioTmp.getVtEmpresa().getNombre());

					vtUsuarioDTOI.add(vtUsuarioDTOInactivo);
				}
			}

			return vtUsuarioDTOI;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public VtUsuario getVtUsuario(Long usuaCodigo) throws Exception {
		log.debug("getting VtUsuario instance");

		VtUsuario entity = null;

		try {
			entity = vtUsuarioDAO.findById(usuaCodigo);
		} catch (Exception e) {
			log.error("get VtUsuario failed", e);
			throw new ZMessManager().new FindingException("VtUsuario");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<VtUsuario> findPageVtUsuario(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		List<VtUsuario> entity = null;

		try {
			entity = vtUsuarioDAO.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtUsuario Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberVtUsuario() throws Exception {
		Long entity = null;

		try {
			entity = vtUsuarioDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtUsuario Count");
		} finally {
		}

		return entity;
	}

	/**
	 *
	 * @param varibles
	 *            este arreglo debera tener:
	 *
	 *            [0] = String variable = (String) varibles[i]; representa como
	 *            se llama la variable en el pojo
	 *
	 *            [1] = Boolean booVariable = (Boolean) varibles[i + 1];
	 *            representa si el valor necesita o no ''(comillas simples)usado
	 *            para campos de tipo string
	 *
	 *            [2] = Object value = varibles[i + 2]; representa el valor que
	 *            se va a buscar en la BD
	 *
	 *            [3] = String comparator = (String) varibles[i + 3]; representa
	 *            que tipo de busqueda voy a hacer.., ejemplo: where
	 *            nombre=william o where nombre<>william, en este campo iria el
	 *            tipo de comparador que quiero si es = o <>
	 *
	 *            Se itera de 4 en 4..., entonces 4 registros del arreglo
	 *            representan 1 busqueda en un campo, si se ponen mas pues el
	 *            continuara buscando en lo que se le ingresen en los otros 4
	 *
	 *
	 * @param variablesBetween
	 *
	 *            la diferencia son estas dos posiciones
	 *
	 *            [0] = String variable = (String) varibles[j]; la variable ne
	 *            la BD que va a ser buscada en un rango
	 *
	 *            [1] = Object value = varibles[j + 1]; valor 1 para buscar en
	 *            un rango
	 *
	 *            [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en
	 *            un rango ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria
	 *            value2
	 *
	 *            [3] = String comparator1 = (String) varibles[j + 3];
	 *            comparador 1 ejemplo: a comparator1 1 and a < 5
	 *
	 *            [4] = String comparator2 = (String) varibles[j + 4];
	 *            comparador 2 ejemplo: a comparador1>1 and a comparador2<5 (el
	 *            original: a > 1 and a < 5) *
	 * @param variablesBetweenDates(en
	 *            este caso solo para mysql) [0] = String variable = (String)
	 *            varibles[k]; el nombre de la variable que hace referencia a
	 *            una fecha
	 *
	 *            [1] = Object object1 = varibles[k + 2]; fecha 1 a
	 *            comparar(deben ser dates)
	 *
	 *            [2] = Object object2 = varibles[k + 3]; fecha 2 a
	 *            comparar(deben ser dates)
	 *
	 *            esto hace un between entre las dos fechas.
	 *
	 * @return lista con los objetos que se necesiten
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<VtUsuario> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		List<VtUsuario> list = new ArrayList<VtUsuario>();
		String where = new String();
		String tempWhere = new String();

		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				if ((variables[i] != null) && (variables[i + 1] != null) && (variables[i + 2] != null)
						&& (variables[i + 3] != null)) {
					String variable = (String) variables[i];
					Boolean booVariable = (Boolean) variables[i + 1];
					Object value = variables[i + 2];
					String comparator = (String) variables[i + 3];

					if (booVariable.booleanValue()) {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " \'" + value + "\' )")
								: (tempWhere + " AND (model." + variable + " " + comparator + " \'" + value + "\' )");
					} else {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " " + value + " )")
								: (tempWhere + " AND (model." + variable + " " + comparator + " " + value + " )");
					}
				}

				i = i + 3;
			}
		}

		if (variablesBetween != null) {
			for (int j = 0; j < variablesBetween.length; j++) {
				if ((variablesBetween[j] != null) && (variablesBetween[j + 1] != null)
						&& (variablesBetween[j + 2] != null) && (variablesBetween[j + 3] != null)
						&& (variablesBetween[j + 4] != null)) {
					String variable = (String) variablesBetween[j];
					Object value = variablesBetween[j + 1];
					Object value2 = variablesBetween[j + 2];
					String comparator1 = (String) variablesBetween[j + 3];
					String comparator2 = (String) variablesBetween[j + 4];
					tempWhere = (tempWhere.length() == 0)
							? ("(" + value + " " + comparator1 + " " + variable + " and " + variable + " " + comparator2
									+ " " + value2 + " )")
							: (tempWhere + " AND (" + value + " " + comparator1 + " " + variable + " and " + variable
									+ " " + comparator2 + " " + value2 + " )");
				}

				j = j + 4;
			}
		}

		if (variablesBetweenDates != null) {
			for (int k = 0; k < variablesBetweenDates.length; k++) {
				if ((variablesBetweenDates[k] != null) && (variablesBetweenDates[k + 1] != null)
						&& (variablesBetweenDates[k + 2] != null)) {
					String variable = (String) variablesBetweenDates[k];
					Object object1 = variablesBetweenDates[k + 1];
					Object object2 = variablesBetweenDates[k + 2];
					String value = null;
					String value2 = null;

					try {
						Date date1 = (Date) object1;
						Date date2 = (Date) object2;
						value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
						value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
					} catch (Exception e) {
						list = null;
						throw e;
					}

					tempWhere = (tempWhere.length() == 0)
							? ("(model." + variable + " between \'" + value + "\' and \'" + value2 + "\')")
							: (tempWhere + " AND (model." + variable + " between \'" + value + "\' and \'" + value2
									+ "\')");
				}

				k = k + 2;
			}
		}

		if (tempWhere.length() == 0) {
			where = null;
		} else {
			where = "(" + tempWhere + ")";
		}

		try {
			list = vtUsuarioDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = true)
	public VtUsuario consultarLogin(String login) {
		return vtUsuarioDAO.consultarLogin(login);
	}

	@Transactional(readOnly = true)
	public VtUsuario consultarUsuarioPorCodigo(Long usua_codigo) {
		return vtUsuarioDAO.consultarUsuarioPorCodigo(usua_codigo);
	}

	@Transactional(readOnly = true)
	public VtUsuario findUsuarioByLogin(String login) {

		List<VtUsuario> usuario = new ArrayList<>();

		try {
			Object[] variables = { "login", true, login, "=" };

			usuario = findByCriteria(variables, null, null);

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return (usuario != null && !usuario.isEmpty() ? usuario.get(0) : null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VtUsuario> obtenerUsuariosAsignados(VtProyecto vtproyecto) throws Exception {
		List<VtUsuario> usuariosSource = getVtUsuario();

		List<VtProyectoUsuario> proyectosUsuarios = vtProyectoUsuarioDAO
				.consultarProyectoUsuarioPorProyecto(vtproyecto.getProyCodigo());

		List<VtUsuario> usuariosTarget = new ArrayList<>();
		if (proyectosUsuarios != null) {
			for (VtUsuario vtUsuario : usuariosSource) {
				for (VtProyectoUsuario vtProyectoUsuario : proyectosUsuarios) {
					if (vtUsuario.getLogin().equals(vtProyectoUsuario.getVtUsuario().getLogin()) == true
							&& vtProyectoUsuario.getActivo().equals("S")){
						usuariosTarget.add(vtUsuario);
						
					}
						
					
				}
			}
		}
		return usuariosTarget;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VtUsuario> obtenerUsuariosNoAsignados(VtProyecto vtproyecto) throws Exception {
		List<VtUsuario> usuariosSource = getVtUsuario();
		List<VtProyectoUsuario> proyectosUsuarios = vtProyectoUsuarioDAO
				.consultarProyectoUsuarioPorProyecto(vtproyecto.getProyCodigo());
		if (proyectosUsuarios != null) {
			for (VtProyectoUsuario vtProyectoUsuario : proyectosUsuarios) {
				if (vtProyectoUsuario.getActivo().equals("S")){
					usuariosSource.remove(vtProyectoUsuario.getVtUsuario());	
				}					
			}
		}
		return usuariosSource;
	}


}
