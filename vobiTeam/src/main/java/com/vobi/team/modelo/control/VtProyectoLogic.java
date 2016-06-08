package com.vobi.team.modelo.control;

import com.vobi.team.dataaccess.dao.*;

import com.vobi.team.exceptions.*;

import com.vobi.team.modelo.*;
import com.vobi.team.modelo.dto.VtProyectoDTO;

import com.vobi.team.utilities.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @author Zathura Code Generator http://zathuracode.org
 * www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("VtProyectoLogic")
public class VtProyectoLogic implements IVtProyectoLogic {
	private static final Logger log = LoggerFactory.getLogger(VtProyectoLogic.class);

	/**
	 * DAO injected by Spring that manages VtProyecto entities
	 *
	 */
	@Autowired
	private IVtProyectoDAO vtProyectoDAO;

	/**
	 * DAO injected by Spring that manages VtPilaProducto entities
	 *
	 */
	@Autowired
	private IVtPilaProductoDAO vtPilaProductoDAO;

	/**
	 * DAO injected by Spring that manages VtProyectoUsuario entities
	 *
	 */
	@Autowired
	private IVtProyectoUsuarioDAO vtProyectoUsuarioDAO;

	/**
	 * Logic injected by Spring that manages VtEmpresa entities
	 *
	 */
	@Autowired
	IVtEmpresaLogic logicVtEmpresa1;

	@Transactional(readOnly = true)
	public List<VtProyecto> getVtProyecto() throws Exception {
		log.debug("finding all VtProyecto instances");

		List<VtProyecto> list = new ArrayList<VtProyecto>();

		try {
			list = vtProyectoDAO.findAll();
		} catch (Exception e) {
			log.error("finding all VtProyecto failed", e);
			throw new ZMessManager().new GettingException(ZMessManager.ALL +
					"VtProyecto");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveVtProyecto(VtProyecto entity) throws Exception {
		log.debug("saving VtProyecto instance");

		try {
			if(entity == null) {
				throw new Exception("La entidad es nula");
			}

			if(entity.getVtEmpresa()==null){
				throw new Exception("Seleccione la empresa para el nuevo proyecto a crear.");
			}

			if(entity.getNombre().toString().equals("") || entity.getNombre().toString().isEmpty()){
				throw new Exception("El campo para el nombre del proyecto no puede estar vacío, digite un nombre por favor.");
			}
			if(entity.getDescripcion().equals("") || entity.getDescripcion().isEmpty() ) {
				throw new Exception("La campo para la descripción del proyecto no puede estar vacío, digite una descripción porfavor.");
			}
			if(entity.getPublico().equals("-1") || entity.getPublico().toString().equals("") ) {
				throw new Exception("Seleccione el nivel de privacidad para el proyecto a crear.");
			}
			if(entity.getFechaCreacion().toString().equals("") || entity.getFechaCreacion().toString().isEmpty()){
				throw new Exception("Fecha incorrecta");
			}
			VtProyecto vtProyecto= new VtProyecto();

			vtProyecto.setActivo(entity.getActivo());
			vtProyecto.setDescripcion(entity.getDescripcion());
			vtProyecto.setFechaCreacion(entity.getFechaCreacion());
			vtProyecto.setNombre(entity.getNombre());
			vtProyecto.setPublico(entity.getPublico());
			vtProyecto.setVtEmpresa(entity.getVtEmpresa());
			vtProyecto.setUsuCreador(1L);
			vtProyectoDAO.save(vtProyecto);

			VtPilaProducto vtPilaProducto= new VtPilaProducto();
			vtPilaProducto.setActivo("S");
			vtPilaProducto.setDescripcion("Pila de Producto. Proyecto: "+vtProyecto.getNombre());
			vtPilaProducto.setFechaCreacion(vtProyecto.getFechaCreacion());
			vtPilaProducto.setNombre("PilaProducto@"+vtProyecto.getNombre());
			vtPilaProducto.setUsuCreador(1L);
			vtPilaProducto.setVtProyecto(vtProyecto);

			vtPilaProductoDAO.save(vtPilaProducto);

			log.debug("save VtProyecto successful");
		} catch (Exception e) {
			log.error("save VtProyecto failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteVtProyecto(VtProyecto entity) throws Exception {
		log.debug("deleting VtProyecto instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("VtProyecto");
		}

		if (entity.getProyCodigo() == null) {
			throw new ZMessManager().new EmptyFieldException("proyCodigo");
		}

		List<VtPilaProducto> vtPilaProductos = null;
		List<VtProyectoUsuario> vtProyectoUsuarios = null;

		try {
			vtPilaProductos = vtPilaProductoDAO.findByProperty("vtProyecto.proyCodigo",
					entity.getProyCodigo());

			if (Utilities.validationsList(vtPilaProductos) == true) {
				throw new ZMessManager().new DeletingException(
						"vtPilaProductos");
			}

			vtProyectoUsuarios = vtProyectoUsuarioDAO.findByProperty("vtProyecto.proyCodigo",
					entity.getProyCodigo());

			if (Utilities.validationsList(vtProyectoUsuarios) == true) {
				throw new ZMessManager().new DeletingException(
						"vtProyectoUsuarios");
			}

			vtProyectoDAO.delete(entity);

			log.debug("delete VtProyecto successful");
		} catch (Exception e) {
			log.error("delete VtProyecto failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateVtProyecto(VtProyecto entity) throws Exception {
		log.debug("updating VtProyecto instance");

		try {
			if(entity == null) {
				throw new Exception("La entidad es nula");
			}

			if(entity.getVtEmpresa()==null){
				throw new Exception("Seleccione la empresa para el nuevo proyecto a modificar.");
			}

			if(entity.getNombre().toString().equals("") || entity.getNombre().toString().isEmpty()){
				throw new Exception("El campo para el nombre del proyecto no puede estar vacío, digite un nombre por favor.");
			}
			if(entity.getDescripcion().equals("") || entity.getDescripcion().isEmpty() ) {
				throw new Exception("La campo para la descripción del proyecto no puede estar vacío, digite una descripción porfavor.");
			}
			if(entity.getPublico().equals("-1") || entity.getPublico().toString().equals("") ) {
				throw new Exception("Seleccione el nivel de privacidad para el proyecto a modificar.");
			}
			vtProyectoDAO.update(entity);

			log.debug("update VtProyecto successful");
		} catch (Exception e) {
			log.error("update VtProyecto failed", e);
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<VtProyectoDTO> getDataVtProyecto(Long codigoFiltro) throws Exception {
		try {
			List<VtProyecto> vtProyecto = vtProyectoDAO.findAll();

			List<VtProyectoUsuario> vtProyectoUsuarioLista= vtProyectoUsuarioDAO.findAll();

			List<VtProyectoDTO> vtProyectoDTO = new ArrayList<VtProyectoDTO>();
			
			log.info("Codigo usuario:"+codigoFiltro);

			for (VtProyecto vtProyectoTmp : vtProyecto) {
				VtProyectoDTO vtProyectoDTO2 = new VtProyectoDTO();
				
				log.info("Nombre Proyecto:"+vtProyectoTmp.getNombre());

				if(vtProyectoTmp.getActivo().equalsIgnoreCase("S")){

					for(VtProyectoUsuario vtProyectoUsuario:vtProyectoUsuarioLista){
						
						log.info("Proyecto usuario:"+vtProyectoUsuario.getVtProyecto().getNombre());

						if(vtProyectoUsuario.getVtProyecto().getProyCodigo().equals(vtProyectoTmp.getProyCodigo())
								&& vtProyectoUsuario.getVtUsuario().equals(codigoFiltro)){

							vtProyectoDTO2.setProyCodigo(vtProyectoTmp.getProyCodigo());

							vtProyectoDTO2.setActivo((vtProyectoTmp.getActivo() != null)
									? vtProyectoTmp.getActivo()+"i" : null);

							vtProyectoDTO2.setDescripcion((vtProyectoTmp.getDescripcion() != null)
									? vtProyectoTmp.getDescripcion() : null);

							vtProyectoDTO2.setFechaCreacion(vtProyectoTmp.getFechaCreacion());

							vtProyectoDTO2.setFechaModificacion(vtProyectoTmp.getFechaModificacion());

							vtProyectoDTO2.setNombre((vtProyectoTmp.getNombre() != null)
									? vtProyectoTmp.getNombre() : null);

							vtProyectoDTO2.setPublico((vtProyectoTmp.getPublico() != null)
									? vtProyectoTmp.getPublico() : null);

							vtProyectoDTO2.setUsuCreador((vtProyectoTmp.getUsuCreador() != null)
									? vtProyectoTmp.getUsuCreador() : null);

							vtProyectoDTO2.setUsuModificador((vtProyectoTmp.getUsuModificador() != null)
									? vtProyectoTmp.getUsuModificador() : null);

							vtProyectoDTO2.setEmprCodigo_VtEmpresa((vtProyectoTmp.getVtEmpresa()
									.getEmprCodigo() != null)
									? vtProyectoTmp.getVtEmpresa().getEmprCodigo() : null);

							vtProyectoDTO2.setNombre_VtEmpresa(vtProyectoTmp.getVtEmpresa().getNombre());

							vtProyectoDTO.add(vtProyectoDTO2);
						}
					}
				}
			}

			return vtProyectoDTO;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	
	
	@Transactional(readOnly = true)
	public List<VtProyectoDTO> getDataVtProyectoActivo(Long codigoFiltro) throws Exception {
		try {
			List<VtProyecto> vtProyecto = vtProyectoDAO.findAll();

			List<VtProyectoDTO> vtProyectoDTOI = new ArrayList<VtProyectoDTO>();

			for (VtProyecto vtProyectoTmp : vtProyecto) {
				log.info("Entro al filtro activo");

				if(vtProyectoTmp.getActivo().equalsIgnoreCase("S")){
					VtProyectoDTO vtProyectoDTO2 = new VtProyectoDTO();
					if(vtProyectoTmp.getVtEmpresa().getEmprCodigo().equals(codigoFiltro)){
				
						vtProyectoDTO2.setProyCodigo(vtProyectoTmp.getProyCodigo());

						vtProyectoDTO2.setActivo((vtProyectoTmp.getActivo() != null)
								? vtProyectoTmp.getActivo()+"i" : null);

						vtProyectoDTO2.setDescripcion((vtProyectoTmp.getDescripcion() != null)
								? vtProyectoTmp.getDescripcion() : null);

						vtProyectoDTO2.setFechaCreacion(vtProyectoTmp.getFechaCreacion());

						vtProyectoDTO2.setFechaModificacion(vtProyectoTmp.getFechaModificacion());

						vtProyectoDTO2.setNombre((vtProyectoTmp.getNombre() != null)
								? vtProyectoTmp.getNombre() : null);

						vtProyectoDTO2.setPublico((vtProyectoTmp.getPublico() != null)
								? vtProyectoTmp.getPublico() : null);

						vtProyectoDTO2.setUsuCreador((vtProyectoTmp.getUsuCreador() != null)
								? vtProyectoTmp.getUsuCreador() : null);

						vtProyectoDTO2.setUsuModificador((vtProyectoTmp.getUsuModificador() != null)
								? vtProyectoTmp.getUsuModificador() : null);

						vtProyectoDTO2.setEmprCodigo_VtEmpresa((vtProyectoTmp.getVtEmpresa()
								.getEmprCodigo() != null)
								? vtProyectoTmp.getVtEmpresa().getEmprCodigo() : null);

						vtProyectoDTO2.setNombre_VtEmpresa(vtProyectoTmp.getVtEmpresa().getNombre());

						vtProyectoDTOI.add(vtProyectoDTO2);
					}
				}
			}

			return vtProyectoDTOI;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public List<VtProyectoDTO> getDataVtProyectoInactivo(Long codigoFiltro) throws Exception {
		try {
			List<VtProyecto> vtProyecto = vtProyectoDAO.findAll();

			List<VtProyectoDTO> vtProyectoDTOI = new ArrayList<VtProyectoDTO>();

			for (VtProyecto vtProyectoTmp : vtProyecto) {
				log.info("Entro al filtro inactivo");

				if(vtProyectoTmp.getActivo().equalsIgnoreCase("N")){
					VtProyectoDTO vtProyectoDTO2 = new VtProyectoDTO();
					if(vtProyectoTmp.getVtEmpresa().getEmprCodigo().equals(codigoFiltro)){
				
						vtProyectoDTO2.setProyCodigo(vtProyectoTmp.getProyCodigo());

						vtProyectoDTO2.setActivo((vtProyectoTmp.getActivo() != null)
								? vtProyectoTmp.getActivo()+"o" : null);

						vtProyectoDTO2.setDescripcion((vtProyectoTmp.getDescripcion() != null)
								? vtProyectoTmp.getDescripcion() : null);

						vtProyectoDTO2.setFechaCreacion(vtProyectoTmp.getFechaCreacion());

						vtProyectoDTO2.setFechaModificacion(vtProyectoTmp.getFechaModificacion());

						vtProyectoDTO2.setNombre((vtProyectoTmp.getNombre() != null)
								? vtProyectoTmp.getNombre() : null);

						vtProyectoDTO2.setPublico((vtProyectoTmp.getPublico() != null)
								? vtProyectoTmp.getPublico() : null);

						vtProyectoDTO2.setUsuCreador((vtProyectoTmp.getUsuCreador() != null)
								? vtProyectoTmp.getUsuCreador() : null);

						vtProyectoDTO2.setUsuModificador((vtProyectoTmp.getUsuModificador() != null)
								? vtProyectoTmp.getUsuModificador() : null);

						vtProyectoDTO2.setEmprCodigo_VtEmpresa((vtProyectoTmp.getVtEmpresa()
								.getEmprCodigo() != null)
								? vtProyectoTmp.getVtEmpresa().getEmprCodigo() : null);

						vtProyectoDTO2.setNombre_VtEmpresa(vtProyectoTmp.getVtEmpresa().getNombre());

						vtProyectoDTOI.add(vtProyectoDTO2);
					}
				}
			}

			return vtProyectoDTOI;
		} catch (Exception e) {
			throw e;
		}
	}



	@Transactional(readOnly = true)
	public VtProyecto getVtProyecto(Long proyCodigo) throws Exception {
		log.debug("getting VtProyecto instance");

		VtProyecto entity = null;

		try {
			entity = vtProyectoDAO.findById(proyCodigo);
		} catch (Exception e) {
			log.error("get VtProyecto failed", e);
			throw new ZMessManager().new FindingException("VtProyecto");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<VtProyecto> findPageVtProyecto(String sortColumnName,
			boolean sortAscending, int startRow, int maxResults)
					throws Exception {
		List<VtProyecto> entity = null;

		try {
			entity = vtProyectoDAO.findPage(sortColumnName, sortAscending,
					startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtProyecto Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberVtProyecto() throws Exception {
		Long entity = null;

		try {
			entity = vtProyectoDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("VtProyecto Count");
		} finally {
		}

		return entity;
	}

	/**
	 *
	 * @param varibles
	 *            este arreglo debera tener:
	 *
	 * [0] = String variable = (String) varibles[i]; representa como se llama la
	 * variable en el pojo
	 *
	 * [1] = Boolean booVariable = (Boolean) varibles[i + 1]; representa si el
	 * valor necesita o no ''(comillas simples)usado para campos de tipo string
	 *
	 * [2] = Object value = varibles[i + 2]; representa el valor que se va a
	 * buscar en la BD
	 *
	 * [3] = String comparator = (String) varibles[i + 3]; representa que tipo
	 * de busqueda voy a hacer.., ejemplo: where nombre=william o where nombre<>william,
	 * en este campo iria el tipo de comparador que quiero si es = o <>
	 *
	 * Se itera de 4 en 4..., entonces 4 registros del arreglo representan 1
	 * busqueda en un campo, si se ponen mas pues el continuara buscando en lo
	 * que se le ingresen en los otros 4
	 *
	 *
	 * @param variablesBetween
	 *
	 * la diferencia son estas dos posiciones
	 *
	 * [0] = String variable = (String) varibles[j]; la variable ne la BD que va
	 * a ser buscada en un rango
	 *
	 * [1] = Object value = varibles[j + 1]; valor 1 para buscar en un rango
	 *
	 * [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en un rango
	 * ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria value2
	 *
	 * [3] = String comparator1 = (String) varibles[j + 3]; comparador 1
	 * ejemplo: a comparator1 1 and a < 5
	 *
	 * [4] = String comparator2 = (String) varibles[j + 4]; comparador 2
	 * ejemplo: a comparador1>1  and a comparador2<5  (el original: a > 1 and a <
	 * 5) *
	 * @param variablesBetweenDates(en
	 *            este caso solo para mysql)
	 *  [0] = String variable = (String) varibles[k]; el nombre de la variable que hace referencia a
	 *            una fecha
	 *
	 * [1] = Object object1 = varibles[k + 2]; fecha 1 a comparar(deben ser
	 * dates)
	 *
	 * [2] = Object object2 = varibles[k + 3]; fecha 2 a comparar(deben ser
	 * dates)
	 *
	 * esto hace un between entre las dos fechas.
	 *
	 * @return lista con los objetos que se necesiten
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<VtProyecto> findByCriteria(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
					throws Exception {
		List<VtProyecto> list = new ArrayList<VtProyecto>();
		String where = new String();
		String tempWhere = new String();

		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				if ((variables[i] != null) && (variables[i + 1] != null) &&
						(variables[i + 2] != null) &&
						(variables[i + 3] != null)) {
					String variable = (String) variables[i];
					Boolean booVariable = (Boolean) variables[i + 1];
					Object value = variables[i + 2];
					String comparator = (String) variables[i + 3];

					if (booVariable.booleanValue()) {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " \'" +
										value + "\' )")
										: (tempWhere + " AND (model." + variable + " " +
												comparator + " \'" + value + "\' )");
					} else {
						tempWhere = (tempWhere.length() == 0)
								? ("(model." + variable + " " + comparator + " " +
										value + " )")
										: (tempWhere + " AND (model." + variable + " " +
												comparator + " " + value + " )");
					}
				}

				i = i + 3;
			}
		}

		if (variablesBetween != null) {
			for (int j = 0; j < variablesBetween.length; j++) {
				if ((variablesBetween[j] != null) &&
						(variablesBetween[j + 1] != null) &&
						(variablesBetween[j + 2] != null) &&
						(variablesBetween[j + 3] != null) &&
						(variablesBetween[j + 4] != null)) {
					String variable = (String) variablesBetween[j];
					Object value = variablesBetween[j + 1];
					Object value2 = variablesBetween[j + 2];
					String comparator1 = (String) variablesBetween[j + 3];
					String comparator2 = (String) variablesBetween[j + 4];
					tempWhere = (tempWhere.length() == 0)
							? ("(" + value + " " + comparator1 + " " + variable +
									" and " + variable + " " + comparator2 + " " + value2 +
									" )")
									: (tempWhere + " AND (" + value + " " + comparator1 +
											" " + variable + " and " + variable + " " +
											comparator2 + " " + value2 + " )");
				}

				j = j + 4;
			}
		}

		if (variablesBetweenDates != null) {
			for (int k = 0; k < variablesBetweenDates.length; k++) {
				if ((variablesBetweenDates[k] != null) &&
						(variablesBetweenDates[k + 1] != null) &&
						(variablesBetweenDates[k + 2] != null)) {
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
							? ("(model." + variable + " between \'" + value +
									"\' and \'" + value2 + "\')")
									: (tempWhere + " AND (model." + variable +
											" between \'" + value + "\' and \'" + value2 + "\')");
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
			list = vtProyectoDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VtProyectoUsuario> consultarProyectoUsuarioPorUsuario(Long usuarioCodigo) {
		return vtProyectoDAO.consultarProyectoUsuarioPorUsuario(usuarioCodigo);
	}

}
