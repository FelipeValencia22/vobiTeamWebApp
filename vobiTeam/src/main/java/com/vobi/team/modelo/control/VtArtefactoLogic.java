package com.vobi.team.modelo.control;

import com.vobi.team.dataaccess.dao.*;
import com.vobi.team.exceptions.*;
import com.vobi.team.modelo.*;
import com.vobi.team.modelo.dto.VtArtefactoDTO;
import com.vobi.team.utilities.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Zathura Code Generator http://zathuracode.org/ www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("VtArtefactoLogic")
public class VtArtefactoLogic implements IVtArtefactoLogic {
	private static final Logger log = LoggerFactory.getLogger(VtArtefactoLogic.class);

	/**
	 * DAO injected by Spring that manages VtArtefacto entities
	 *
	 */
	@Autowired
	private IVtArtefactoDAO vtArtefactoDAO;

	/**
	 * DAO injected by Spring that manages VtArchivo entities
	 *
	 */
	@Autowired
	private IVtArchivoDAO vtArchivoDAO;

	/**
	 * DAO injected by Spring that manages VtHistoriaArtefacto entities
	 *
	 */
	@Autowired
	private IVtHistoriaArtefactoDAO vtHistoriaArtefactoDAO;

	/**
	 * DAO injected by Spring that manages VtUsuarioArtefacto entities
	 *
	 */
	@Autowired
	private IVtUsuarioArtefactoDAO vtUsuarioArtefactoDAO;

	/**
	 * Logic injected by Spring that manages VtEstado entities
	 *
	 */
	@Autowired
	IVtEstadoLogic logicVtEstado1;

	/**
	 * Logic injected by Spring that manages VtPilaProducto entities
	 *
	 */
	@Autowired
	IVtPilaProductoLogic logicVtPilaProducto2;

	/**
	 * Logic injected by Spring that manages VtPrioridad entities
	 *
	 */
	@Autowired
	IVtPrioridadLogic logicVtPrioridad3;

	/**
	 * Logic injected by Spring that manages VtSprint entities
	 *
	 */
	@Autowired
	IVtSprintLogic logicVtSprint4;

	/**
	 * Logic injected by Spring that manages VtTipoArtefacto entities
	 *
	 */
	@Autowired
	IVtTipoArtefactoLogic logicVtTipoArtefacto5;

	@Transactional(readOnly = true)
	public List<VtArtefacto> getVtArtefacto() throws Exception {
		log.debug("finding all VtArtefacto instances");

		List<VtArtefacto> list = new ArrayList<VtArtefacto>();

		try {
			list = vtArtefactoDAO.findAll();
		} catch (Exception e) {
			log.error("finding all VtArtefacto failed", e);
			throw new ZMessManager().new GettingException(ZMessManager.ALL + "VtArtefacto");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveVtArtefacto(VtArtefacto entity, String esfuerzoEstimado, String esfuerzoRestantes, String puntos)
			throws Exception {
		String horas, minutos;

		try {

			if (entity.getVtTipoArtefacto() == null) {
				throw new Exception("Seleccione el tipo de artefacto que sera el nuevo artefacto a crear.");
			}

			if (entity.getVtPilaProducto() == null) {
				throw new Exception(
						"Seleccione la pila de producto a la que va a pertenecer el nuevo artefacto a crear.");
			}
			if (entity.getTitulo() == null || entity.getTitulo().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception(
						"El campo para el título del artefacto no puede estar vacío, digite el título por favor.");
			}
			if ((entity.getTitulo() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getTitulo(), 255) == false)) {
				throw new ZMessManager().new NotValidFormatException("titulo");
			}
			if (entity.getDescripcion().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception("El campo para la descripción no puede estar en vacío, escriba una descripción.");
			}
			if ((entity.getDescripcion() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getDescripcion(), 255) == false)) {
				throw new ZMessManager().new NotValidFormatException("descripcion");
			}

			if (entity.getActivo() == null || entity.getActivo().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception("Defina si el artefacto va a estar activo o inactivo");
			}

			if (entity.getVtPrioridad() == null) {
				throw new Exception("Seleccione la prioridad que tendra el nuevo artefacto a crear.");
			}

			if (entity.getVtEstado() == null) {
				throw new Exception("Seleccione el estado que va a tener el nuevo artefacto a crear.");
			}

			if (esfuerzoEstimado.toString().trim().equals("") || esfuerzoEstimado == null) {
				throw new Exception(
						"El campo para el esfuerzo estimado no puede ser vacio, digite el esfuerzo estimado del artefacto a crear.");
			} else {
				horas = esfuerzoEstimado.substring(0, 2);
				minutos = esfuerzoEstimado.substring(3, 5);
				esfuerzoEstimado = convertirHorasAMinutos(horas, minutos).toString().trim();
				if ((Utilities.isNumeric(esfuerzoEstimado) == true)) {
					entity.setEsfuerzoEstimado(Integer.parseInt(esfuerzoEstimado));
				} else {
					throw new Exception("El campo para el esfuerzo" + " estimado no acepta cadenas de texto o "
							+ "números negativos, solo se aceptan valores númericos positivos.");
				}
			}

			if (esfuerzoRestantes.toString().trim().equals("") || esfuerzoRestantes == null) {
				throw new Exception("El campo para el esf" + "uerzo restante no p"
						+ "uede ser vacio, digite el esfuerzo restante del artefacto a crear.");
			} else {
				
				horas = esfuerzoRestantes.substring(0, 2);
				minutos = esfuerzoRestantes.substring(3, 5);
				esfuerzoRestantes = convertirHorasAMinutos(horas, minutos).toString().trim();
				
				if (Utilities.isNumeric(esfuerzoRestantes) == true) {
					entity.setEsfuerzoRestante(Integer.parseInt(esfuerzoRestantes));
				} else {
					throw new Exception("El campo para el " + "esfuerzo restante no acepta cadenas "
							+ "de texto o números negativos, solo se aceptan valores númericos positivos.");
				}

			}

			if (puntos.toString().trim().equals("") || puntos == null) {
				throw new Exception(
						"El campo para los puntos no puede ser vacio, digite los puntos del artefacto a crear.");
			} else {
				
				horas = puntos.substring(0, 2);
				minutos = puntos.substring(3, 5);
				puntos = convertirHorasAMinutos(horas, minutos).toString().trim();
				
				if (Utilities.isNumeric(puntos) == true) {
					entity.setPuntos(Integer.parseInt(puntos));
				} else {
					throw new Exception("El campo para los puntos no acepta cadenas de texto"
							+ " o números negativos, solo se aceptan valores numéricos positivos.");
				}

			}
		
			if (entity.getOrigen().toString().trim().equals("") || entity.getOrigen() == null) {
				throw new Exception("El campo para el origen no puede ser vacio, digite e"
						+ "l origen del nuevo artefacto a crear.");
			}
			if (entity.getOrigen() != null && (Utilities.isNumeric(entity.getOrigen()) == true)) {
				throw new Exception(
						"El campo para el origen no puede recibir n" + "úmeros, solo se aceptan cadenas de texto.");
			}
			if (entity.getFechaCreacion() == null) {
				throw new Exception("La fecha de creación es obligatoria");
			}

			if (entity.getUsuCreador() == null) {
				throw new Exception("El usuario es obligatorio");
			}
			vtArtefactoDAO.save(entity);

			log.debug("save VtArtefacto successful");
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@Transactional(readOnly = true)
	public Long convertirHorasAMinutos(String horas, String minutos) {
		Long minutosTotales = 0L;
		Long horasEsfuerzo = Long.parseLong(horas);
		Long horasEsfuerzoEnMinutos = TimeUnit.HOURS.toMinutes(horasEsfuerzo);
		Long minutosEsfuerzo = Long.parseLong(minutos);
		minutosTotales = horasEsfuerzoEnMinutos + minutosEsfuerzo;
		return minutosTotales;
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteVtArtefacto(VtArtefacto entity) throws Exception {
		log.debug("deleting VtArtefacto instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("VtArtefacto");
		}

		if (entity.getArteCodigo() == null) {
			throw new ZMessManager().new EmptyFieldException("arteCodigo");
		}

		List<VtArchivo> vtArchivos = null;
		List<VtHistoriaArtefacto> vtHistoriaArtefactos = null;
		List<VtUsuarioArtefacto> vtUsuarioArtefactos = null;

		try {
			vtArchivos = vtArchivoDAO.findByProperty("vtArtefacto.arteCodigo", entity.getArteCodigo());

			if (Utilities.validationsList(vtArchivos) == true) {
				throw new ZMessManager().new DeletingException("vtArchivos");
			}

			vtHistoriaArtefactos = vtHistoriaArtefactoDAO.findByProperty("vtArtefacto.arteCodigo",
					entity.getArteCodigo());

			if (Utilities.validationsList(vtHistoriaArtefactos) == true) {
				throw new ZMessManager().new DeletingException("vtHistoriaArtefactos");
			}

			vtUsuarioArtefactos = vtUsuarioArtefactoDAO.findByProperty("vtArtefacto.arteCodigo",
					entity.getArteCodigo());

			if (Utilities.validationsList(vtUsuarioArtefactos) == true) {
				throw new ZMessManager().new DeletingException("vtUsuarioArtefactos");
			}

			vtArtefactoDAO.delete(entity);

			log.debug("delete VtArtefacto successful");
		} catch (Exception e) {
			log.error("delete VtArtefacto failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateVtArtefacto(VtArtefacto entity) throws Exception {
		log.debug("updating VtArtefacto instance");

		try {
			if (entity.getVtTipoArtefacto() == null) {
				throw new Exception("Seleccione el tipo de artefacto que sera el nuevo artefacto a modificar.");
			}

			if (entity.getVtPilaProducto() == null) {
				throw new Exception(
						"Seleccione la pila de producto a la que va a pertenecer el nuevo artefacto a modificar.");
			}
			if (entity.getTitulo() == null || entity.getTitulo().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception(
						"El campo para el título del artefacto no puede estar vacío, digite el título por favor.");
			}
			if ((entity.getTitulo() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getTitulo(), 255) == false)) {
				throw new ZMessManager().new NotValidFormatException("titulo");
			}
			if (entity.getDescripcion().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception("El campo para la descripción no puede estar en vacío, escriba una descripción.");
			}
			if ((entity.getDescripcion() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getDescripcion(), 255) == false)) {
				throw new ZMessManager().new NotValidFormatException("descripcion");
			}

			if (entity.getActivo() == null || entity.getActivo().toString().trim().equalsIgnoreCase("") == true) {
				throw new Exception("Defina si el artefacto va a estar activo o inactivo");
			}

			if (entity.getVtPrioridad() == null) {
				throw new Exception("Seleccione la prioridad que tendra el nuevo artefacto a modificar.");
			}

			if (entity.getVtEstado() == null) {
				throw new Exception("Seleccione el estado que va a tener el nuevo artefacto a crear.");
			}
			if (entity.getUsuCreador() == null) {
				throw new Exception("El usuario es obligatorio");
			}

			vtArtefactoDAO.update(entity);

			log.debug("update VtArtefacto successful");
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefacto() throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.findAll();

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();

			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {
				VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();

				vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
				vtArtefactoDTO2.setActivo((vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() : null);
				vtArtefactoDTO2.setDescripcion(
						(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
				vtArtefactoDTO2.setEsfuerzoEstimado(
						(vtArtefactoTmp.getEsfuerzoEstimado() != null) ? vtArtefactoTmp.getEsfuerzoEstimado() : null);
				vtArtefactoDTO2.setEsfuerzoReal(
						(vtArtefactoTmp.getEsfuerzoReal() != null) ? vtArtefactoTmp.getEsfuerzoReal() : null);
				vtArtefactoDTO2.setEsfuerzoRestante(
						(vtArtefactoTmp.getEsfuerzoRestante() != null) ? vtArtefactoTmp.getEsfuerzoRestante() : null);
				vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
				vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
				vtArtefactoDTO2.setOrigen((vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
				vtArtefactoDTO2.setPuntos((vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
				vtArtefactoDTO2.setTitulo((vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
				vtArtefactoDTO2.setUsuCreador(
						(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
				vtArtefactoDTO2.setUsuModificador(
						(vtArtefactoTmp.getUsuModificador() != null) ? vtArtefactoTmp.getUsuModificador() : null);
				vtArtefactoDTO2.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
						? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
				vtArtefactoDTO2
				.setPilaCodigo_VtPilaProducto((vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
						? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
				vtArtefactoDTO2.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
						? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
				vtArtefactoDTO2.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
						? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
				vtArtefactoDTO2
				.setTparCodigo_VtTipoArtefacto((vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
						? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);
				vtArtefactoDTO.add(vtArtefactoDTO2);
			}

			return vtArtefactoDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefactoFiltro(Long codigoFiltro) throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.consultarTodosLosArtefactosAsignados();

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();


			String codigoSprint = "";
			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {
				codigoSprint = vtArtefactoTmp.getVtSprint().getSpriCodigo().toString().trim();
				if (!codigoSprint.equalsIgnoreCase("") || !codigoSprint.equalsIgnoreCase(null)) {
					if (vtArtefactoTmp.getActivo().equalsIgnoreCase("S")) {

						if (vtArtefactoTmp.getVtSprint().getSpriCodigo().equals(codigoFiltro)) {

									VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();
									vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
									vtArtefactoDTO2.setActivo(
											(vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() + "i" : null);
									vtArtefactoDTO2.setDescripcion(
											(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
									vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
											? vtArtefactoTmp.getEsfuerzoEstimado() : null);
									vtArtefactoDTO2.setEsfuerzoReal((vtArtefactoTmp.getEsfuerzoReal() != null)
											? vtArtefactoTmp.getEsfuerzoReal() : null);
									vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
											? vtArtefactoTmp.getEsfuerzoRestante() : null);
									vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
									vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
									vtArtefactoDTO2.setOrigen(
											(vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
									vtArtefactoDTO2.setPuntos(
											(vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
									vtArtefactoDTO2.setTitulo(
											(vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
									vtArtefactoDTO2.setUsuCreador(
											(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
									vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
											? vtArtefactoTmp.getUsuModificador() : null);
									vtArtefactoDTO2
									.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
											? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
									vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
											(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
											? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
									vtArtefactoDTO2
									.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
											? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
									vtArtefactoDTO2
									.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
											? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
									vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
											(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
											? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);

									vtArtefactoDTO2.setTpar_Nombre(vtArtefactoTmp.getVtTipoArtefacto().getNombre());
									vtArtefactoDTO2.setEstado_Nombre(vtArtefactoTmp.getVtEstado().getNombre());
									vtArtefactoDTO2.setPrioridad_Nombre(vtArtefactoTmp.getVtPrioridad().getNombre());

									vtArtefactoDTO.add(vtArtefactoDTO2);
						}
					}
				}
				codigoSprint = "";
			}
			return vtArtefactoDTO;
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroDesarrollador(Long codigoFiltro, Long codigoUsuario) throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.consultarTodosLosArtefactosAsignados();

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();

			List<VtUsuarioArtefacto> listaUsuarioArtefacto=  vtUsuarioArtefactoDAO.consultarUsuarioArtefactoPorUsuario(codigoUsuario);

			String codigoSprint = "";
			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {
				codigoSprint = vtArtefactoTmp.getVtSprint().getSpriCodigo().toString().trim();
				if (!codigoSprint.equalsIgnoreCase("") || !codigoSprint.equalsIgnoreCase(null)) {
					if (vtArtefactoTmp.getActivo().equalsIgnoreCase("S")) {

						if (vtArtefactoTmp.getVtSprint().getSpriCodigo().equals(codigoFiltro)) {

							for(VtUsuarioArtefacto vtUsuarioArtefacto: listaUsuarioArtefacto){

								if(vtUsuarioArtefacto.getVtArtefacto().getArteCodigo().equals(vtArtefactoTmp.getArteCodigo())){

									VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();
									vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
									vtArtefactoDTO2.setActivo(
											(vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() + "i" : null);
									vtArtefactoDTO2.setDescripcion(
											(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
									vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
											? vtArtefactoTmp.getEsfuerzoEstimado() : null);
									vtArtefactoDTO2.setEsfuerzoReal((vtArtefactoTmp.getEsfuerzoReal() != null)
											? vtArtefactoTmp.getEsfuerzoReal() : null);
									vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
											? vtArtefactoTmp.getEsfuerzoRestante() : null);
									vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
									vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
									vtArtefactoDTO2.setOrigen(
											(vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
									vtArtefactoDTO2.setPuntos(
											(vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
									vtArtefactoDTO2.setTitulo(
											(vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
									vtArtefactoDTO2.setUsuCreador(
											(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
									vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
											? vtArtefactoTmp.getUsuModificador() : null);
									vtArtefactoDTO2
									.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
											? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
									vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
											(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
											? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
									vtArtefactoDTO2
									.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
											? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
									vtArtefactoDTO2
									.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
											? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
									vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
											(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
											? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);

									vtArtefactoDTO2.setTpar_Nombre(vtArtefactoTmp.getVtTipoArtefacto().getNombre());
									vtArtefactoDTO2.setEstado_Nombre(vtArtefactoTmp.getVtEstado().getNombre());
									vtArtefactoDTO2.setPrioridad_Nombre(vtArtefactoTmp.getVtPrioridad().getNombre());

									vtArtefactoDTO.add(vtArtefactoDTO2);

								}
							}
						}
					}
				}
				codigoSprint = "";
			}
			return vtArtefactoDTO;
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroI(Long codigoFiltro) throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.consultarTodosLosArtefactosAsignados();
			

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();
			String codigoSprint = "";
			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {
				codigoSprint = vtArtefactoTmp.getVtSprint().getSpriCodigo().toString().trim();
				if (!codigoSprint.equalsIgnoreCase("") || !codigoSprint.equalsIgnoreCase(null)) {
					if (vtArtefactoTmp.getActivo().equalsIgnoreCase("N")) {

						if (vtArtefactoTmp.getVtSprint().getSpriCodigo().equals(codigoFiltro)) {


									VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();

									vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
									vtArtefactoDTO2.setActivo(
											(vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() + "o" : null);
									vtArtefactoDTO2.setDescripcion(
											(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
									vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
											? vtArtefactoTmp.getEsfuerzoEstimado() : null);
									vtArtefactoDTO2.setEsfuerzoReal((vtArtefactoTmp.getEsfuerzoReal() != null)
											? vtArtefactoTmp.getEsfuerzoReal() : null);
									vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
											? vtArtefactoTmp.getEsfuerzoRestante() : null);
									vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
									vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
									vtArtefactoDTO2.setOrigen(
											(vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
									vtArtefactoDTO2.setPuntos(
											(vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
									vtArtefactoDTO2.setTitulo(
											(vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
									vtArtefactoDTO2.setUsuCreador(
											(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
									vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
											? vtArtefactoTmp.getUsuModificador() : null);
									vtArtefactoDTO2
									.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
											? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
									vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
											(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
											? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
									vtArtefactoDTO2
									.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
											? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
									vtArtefactoDTO2
									.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
											? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
									vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
											(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
											? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);

									vtArtefactoDTO2.setTpar_Nombre(vtArtefactoTmp.getVtTipoArtefacto().getNombre());
									vtArtefactoDTO2.setEstado_Nombre(vtArtefactoTmp.getVtEstado().getNombre());
									vtArtefactoDTO2.setPrioridad_Nombre(vtArtefactoTmp.getVtPrioridad().getNombre());

									vtArtefactoDTO.add(vtArtefactoDTO2);
							
						}
					}
				}
				codigoSprint = "";
			}
			return vtArtefactoDTO;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroIDesarrollador(Long codigoFiltro, Long codigoUsuario) throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.consultarTodosLosArtefactosAsignados();
			
			List<VtUsuarioArtefacto> listaUsuarioArtefacto=  vtUsuarioArtefactoDAO.consultarUsuarioArtefactoPorUsuario(codigoUsuario);

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();
			String codigoSprint = "";
			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {
				codigoSprint = vtArtefactoTmp.getVtSprint().getSpriCodigo().toString().trim();
				if (!codigoSprint.equalsIgnoreCase("") || !codigoSprint.equalsIgnoreCase(null)) {
					if (vtArtefactoTmp.getActivo().equalsIgnoreCase("N")) {

						if (vtArtefactoTmp.getVtSprint().getSpriCodigo().equals(codigoFiltro)) {

							for(VtUsuarioArtefacto vtUsuarioArtefacto: listaUsuarioArtefacto){

								if(vtUsuarioArtefacto.getVtArtefacto().getArteCodigo().equals(vtArtefactoTmp.getArteCodigo())){

									VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();

									vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
									vtArtefactoDTO2.setActivo(
											(vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() + "o" : null);
									vtArtefactoDTO2.setDescripcion(
											(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
									vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
											? vtArtefactoTmp.getEsfuerzoEstimado() : null);
									vtArtefactoDTO2.setEsfuerzoReal((vtArtefactoTmp.getEsfuerzoReal() != null)
											? vtArtefactoTmp.getEsfuerzoReal() : null);
									vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
											? vtArtefactoTmp.getEsfuerzoRestante() : null);
									vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
									vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
									vtArtefactoDTO2.setOrigen(
											(vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
									vtArtefactoDTO2.setPuntos(
											(vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
									vtArtefactoDTO2.setTitulo(
											(vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
									vtArtefactoDTO2.setUsuCreador(
											(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
									vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
											? vtArtefactoTmp.getUsuModificador() : null);
									vtArtefactoDTO2
									.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
											? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
									vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
											(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
											? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
									vtArtefactoDTO2
									.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
											? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
									vtArtefactoDTO2
									.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
											? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
									vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
											(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
											? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);

									vtArtefactoDTO2.setTpar_Nombre(vtArtefactoTmp.getVtTipoArtefacto().getNombre());
									vtArtefactoDTO2.setEstado_Nombre(vtArtefactoTmp.getVtEstado().getNombre());
									vtArtefactoDTO2.setPrioridad_Nombre(vtArtefactoTmp.getVtPrioridad().getNombre());

									vtArtefactoDTO.add(vtArtefactoDTO2);
								}
							}
						}
					}
				}
				codigoSprint = "";
			}
			return vtArtefactoDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> getDataVtArtefactoActivo(Long codigoFiltro) throws Exception {
		try {
			List<VtArtefacto> vtArtefacto = vtArtefactoDAO.consultarTodosLosArtefactosAsignados();

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();

			for (VtArtefacto vtArtefactoTmp : vtArtefacto) {

				if (vtArtefactoTmp.getActivo().equalsIgnoreCase("S")) {

					if (vtArtefactoTmp.getVtSprint().getSpriCodigo().equals(codigoFiltro)) {
						VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();

						vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
						vtArtefactoDTO2
						.setActivo((vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() : null);
						vtArtefactoDTO2.setDescripcion(
								(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
						vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
								? vtArtefactoTmp.getEsfuerzoEstimado() : null);
						vtArtefactoDTO2.setEsfuerzoReal(
								(vtArtefactoTmp.getEsfuerzoReal() != null) ? vtArtefactoTmp.getEsfuerzoReal() : null);
						vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
								? vtArtefactoTmp.getEsfuerzoRestante() : null);
						vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
						vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
						vtArtefactoDTO2
						.setOrigen((vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
						vtArtefactoDTO2
						.setPuntos((vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
						vtArtefactoDTO2
						.setTitulo((vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
						vtArtefactoDTO2.setUsuCreador(
								(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
						vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
								? vtArtefactoTmp.getUsuModificador() : null);
						vtArtefactoDTO2.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
								? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
						vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
								(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
								? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
						vtArtefactoDTO2
						.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
								? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
						vtArtefactoDTO2.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
								? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
						vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
								(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
								? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);
						vtArtefactoDTO.add(vtArtefactoDTO2);
					}
				}
			}
			return vtArtefactoDTO;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public VtArtefacto getVtArtefacto(Long arteCodigo) throws Exception {
		log.debug("getting VtArtefacto instance");

		VtArtefacto entity = null;

		try {
			entity = vtArtefactoDAO.findById(arteCodigo);
		} catch (Exception e) {
			log.error("get VtArtefacto failed", e);
			throw new ZMessManager().new FindingException("VtArtefacto");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> findPageVtArtefacto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		List<VtArtefacto> entity = null;

		try {
			entity = vtArtefactoDAO.findPage(sortColumnName, sortAscending, startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtArtefacto Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberVtArtefacto() throws Exception {
		Long entity = null;

		try {
			entity = vtArtefactoDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtArtefacto Count");
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
	public List<VtArtefacto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		List<VtArtefacto> list = new ArrayList<VtArtefacto>();
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
			list = vtArtefactoDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> consultarArtefactosSinAsignarASprint(Long codigoProyecto) throws Exception {
		List<VtArtefacto> artefactosSoruce = vtArtefactoDAO.consultarArtefactosSinAsignarASprint(codigoProyecto);
		return artefactosSoruce;
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> consultarArtefactosAsignadosASprint(Long codigoSprint) throws Exception {
		List<VtArtefacto> artefactosTarget = vtArtefactoDAO.consultarArtefactosAsignadosASprint(codigoSprint);
		return artefactosTarget;
	}

	@Transactional(readOnly = true)
	public VtArtefacto consultarArtefactosAsignadosASprintYPila(Long artecodigo, Long codigoPila) throws Exception {
		return vtArtefactoDAO.consultarArtefactosAsignadosASprintYPila(artecodigo, codigoPila);
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> consultarTodosLosArtefactosAsignados() throws Exception {
		return vtArtefactoDAO.consultarTodosLosArtefactosAsignados();
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> obtenerArtefactosNoAsignados(VtUsuario vtUsuario,Long codigoProyecto) throws Exception {
		List<VtArtefacto> artefactosSources = vtArtefactoDAO.consultarArtefactosPorProyecto(codigoProyecto);
		List<VtUsuarioArtefacto> usuarioArtefacto = vtUsuarioArtefactoDAO.findAll();

		if (usuarioArtefacto != null) {
			for (VtUsuarioArtefacto vtUsuarioArtefacto : usuarioArtefacto) {
				if (vtUsuarioArtefacto.getActivo().equals("S")) {
					artefactosSources.remove(vtUsuarioArtefacto.getVtArtefacto());
				}
			}

		}
		return artefactosSources;
	}

	@Transactional(readOnly = true)
	public List<VtArtefacto> obtenerArtefactosAsignados(VtUsuario vtUsuario,Long codigoProyecto) throws Exception {

		List<VtArtefacto> artefactosSources = vtArtefactoDAO.consultarArtefactosPorProyecto(codigoProyecto);

		List<VtUsuarioArtefacto> usuarioArtefacto = vtUsuarioArtefactoDAO
				.consultarUsuarioArtefactoPorUsuario(vtUsuario.getUsuaCodigo());
		List<VtArtefacto> artefactosTarget = new ArrayList<>();
		if(usuarioArtefacto!=null){
			for (VtArtefacto vtArtefacto : artefactosSources) {
				for (VtUsuarioArtefacto vtUsuarioArtefacto : usuarioArtefacto) {
					if (vtArtefacto.getTitulo().equals(vtUsuarioArtefacto.getVtArtefacto().getTitulo())==true
							&& vtArtefacto.getActivo().equals("S")) {
						artefactosTarget.add(vtArtefacto);
					}

				}
			}
		}


		return artefactosTarget;
	}

	@Transactional(readOnly = true)
	public VtUsuarioArtefacto consultarUsuarioArtefactoPorUsuarioYArtefacto(Long codigoUsuario, Long codigoArtefacto)
			throws Exception {
		return vtArtefactoDAO.consultarUsuarioArtefactoPorUsuarioYArtefacto(codigoUsuario, codigoArtefacto);
	}

	@Transactional(readOnly = true)
	public List<VtArtefactoDTO> obtenerArtefactosAsignadosDTO(VtUsuario vtUsuario) throws Exception {

		try {
			List<VtArtefacto> artefactosAsignados = vtArtefactoDAO.todosLosArtefactosDeUnUsuario(vtUsuario.getUsuaCodigo());

			List<VtArtefactoDTO> vtArtefactoDTO = new ArrayList<VtArtefactoDTO>();

			for (VtArtefacto vtArtefactoTmp : artefactosAsignados) {

				if (vtArtefactoTmp.getActivo().equalsIgnoreCase("S")) {
					VtArtefactoDTO vtArtefactoDTO2 = new VtArtefactoDTO();
					vtArtefactoDTO2.setArteCodigo(vtArtefactoTmp.getArteCodigo());
					vtArtefactoDTO2.setActivo(
							(vtArtefactoTmp.getActivo() != null) ? vtArtefactoTmp.getActivo() + "i" : null);
					vtArtefactoDTO2.setDescripcion(
							(vtArtefactoTmp.getDescripcion() != null) ? vtArtefactoTmp.getDescripcion() : null);
					vtArtefactoDTO2.setEsfuerzoEstimado((vtArtefactoTmp.getEsfuerzoEstimado() != null)
							? vtArtefactoTmp.getEsfuerzoEstimado() : null);
					vtArtefactoDTO2.setEsfuerzoReal((vtArtefactoTmp.getEsfuerzoReal() != null)
							? vtArtefactoTmp.getEsfuerzoReal() : null);
					vtArtefactoDTO2.setEsfuerzoRestante((vtArtefactoTmp.getEsfuerzoRestante() != null)
							? vtArtefactoTmp.getEsfuerzoRestante() : null);
					vtArtefactoDTO2.setFechaCreacion(vtArtefactoTmp.getFechaCreacion());
					vtArtefactoDTO2.setFechaModificacion(vtArtefactoTmp.getFechaModificacion());
					vtArtefactoDTO2.setOrigen(
							(vtArtefactoTmp.getOrigen() != null) ? vtArtefactoTmp.getOrigen() : null);
					vtArtefactoDTO2.setPuntos(
							(vtArtefactoTmp.getPuntos() != null) ? vtArtefactoTmp.getPuntos() : null);
					vtArtefactoDTO2.setTitulo(
							(vtArtefactoTmp.getTitulo() != null) ? vtArtefactoTmp.getTitulo() : null);
					vtArtefactoDTO2.setUsuCreador(
							(vtArtefactoTmp.getUsuCreador() != null) ? vtArtefactoTmp.getUsuCreador() : null);
					vtArtefactoDTO2.setUsuModificador((vtArtefactoTmp.getUsuModificador() != null)
							? vtArtefactoTmp.getUsuModificador() : null);
					vtArtefactoDTO2
					.setEstaCodigo_VtEstado((vtArtefactoTmp.getVtEstado().getEstaCodigo() != null)
							? vtArtefactoTmp.getVtEstado().getEstaCodigo() : null);
					vtArtefactoDTO2.setPilaCodigo_VtPilaProducto(
							(vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() != null)
							? vtArtefactoTmp.getVtPilaProducto().getPilaCodigo() : null);
					vtArtefactoDTO2
					.setPrioCodigo_VtPrioridad((vtArtefactoTmp.getVtPrioridad().getPrioCodigo() != null)
							? vtArtefactoTmp.getVtPrioridad().getPrioCodigo() : null);
					vtArtefactoDTO2
					.setSpriCodigo_VtSprint((vtArtefactoTmp.getVtSprint().getSpriCodigo() != null)
							? vtArtefactoTmp.getVtSprint().getSpriCodigo() : null);
					vtArtefactoDTO2.setTparCodigo_VtTipoArtefacto(
							(vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() != null)
							? vtArtefactoTmp.getVtTipoArtefacto().getTparCodigo() : null);

					vtArtefactoDTO2.setTpar_Nombre(vtArtefactoTmp.getVtTipoArtefacto().getNombre());
					vtArtefactoDTO2.setEstado_Nombre(vtArtefactoTmp.getVtEstado().getNombre());
					vtArtefactoDTO2.setPrioridad_Nombre(vtArtefactoTmp.getVtPrioridad().getNombre());

					vtArtefactoDTO.add(vtArtefactoDTO2);
				}
			}

			return vtArtefactoDTO;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}

	}
}
