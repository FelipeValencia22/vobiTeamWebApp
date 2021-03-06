package com.vobi.team.presentation.businessDelegate;

import com.vobi.team.modelo.VtArchivo;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtEstado;
import com.vobi.team.modelo.VtEstadoSprint;
import com.vobi.team.modelo.VtHistoriaArtefacto;
import com.vobi.team.modelo.VtInteres;
import com.vobi.team.modelo.VtPilaProducto;
import com.vobi.team.modelo.VtPrioridad;
import com.vobi.team.modelo.VtProgresoArtefacto;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtRol;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtTipoArtefacto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioArtefacto;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.modelo.control.IVtArchivoLogic;
import com.vobi.team.modelo.control.IVtArtefactoLogic;
import com.vobi.team.modelo.control.IVtEmpresaLogic;
import com.vobi.team.modelo.control.IVtEstadoLogic;
import com.vobi.team.modelo.control.IVtEstadoSprintLogic;
import com.vobi.team.modelo.control.IVtHistoriaArtefactoLogic;
import com.vobi.team.modelo.control.IVtInteresLogic;
import com.vobi.team.modelo.control.IVtPilaProductoLogic;
import com.vobi.team.modelo.control.IVtPrioridadLogic;
import com.vobi.team.modelo.control.IVtProgresoArtefactoLogic;
import com.vobi.team.modelo.control.IVtProyectoLogic;
import com.vobi.team.modelo.control.IVtProyectoUsuarioLogic;
import com.vobi.team.modelo.control.IVtRolLogic;
import com.vobi.team.modelo.control.IVtSeguridadLogica;
import com.vobi.team.modelo.control.IVtSprintLogic;
import com.vobi.team.modelo.control.IVtTipoArtefactoLogic;
import com.vobi.team.modelo.control.IVtUsuarioArtefactoLogic;
import com.vobi.team.modelo.control.IVtUsuarioLogic;
import com.vobi.team.modelo.control.IVtUsuarioRolLogic;
import com.vobi.team.modelo.dto.VtArchivoDTO;
import com.vobi.team.modelo.dto.VtArtefactoDTO;
import com.vobi.team.modelo.dto.VtEmpresaDTO;
import com.vobi.team.modelo.dto.VtEstadoDTO;
import com.vobi.team.modelo.dto.VtEstadoSprintDTO;
import com.vobi.team.modelo.dto.VtHistoriaArtefactoDTO;
import com.vobi.team.modelo.dto.VtInteresDTO;
import com.vobi.team.modelo.dto.VtPilaProductoDTO;
import com.vobi.team.modelo.dto.VtPrioridadDTO;
import com.vobi.team.modelo.dto.VtProgresoArtefactoDTO;
import com.vobi.team.modelo.dto.VtProyectoDTO;
import com.vobi.team.modelo.dto.VtProyectoUsuarioDTO;
import com.vobi.team.modelo.dto.VtRolDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;
import com.vobi.team.modelo.dto.VtTipoArtefactoDTO;
import com.vobi.team.modelo.dto.VtUsuarioArtefactoDTO;
import com.vobi.team.modelo.dto.VtUsuarioDTO;
import com.vobi.team.modelo.dto.VtUsuarioRolDTO;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import java.util.List;

@Scope("singleton")
@Service("BusinessDelegatorView")
public class BusinessDelegatorView implements IBusinessDelegatorView {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(BusinessDelegatorView.class);
	@Autowired
	private IVtArchivoLogic vtArchivoLogic;

	@Autowired
	private IVtSeguridadLogica seguridadLogica;
	@Autowired
	private IVtEstadoSprintLogic vtEstadoSprintLogic;
	@Autowired
	private IVtArtefactoLogic vtArtefactoLogic;
	@Autowired
	private IVtEmpresaLogic vtEmpresaLogic;
	@Autowired
	private IVtEstadoLogic vtEstadoLogic;
	@Autowired
	private IVtHistoriaArtefactoLogic vtHistoriaArtefactoLogic;
	@Autowired
	private IVtInteresLogic vtInteresLogic;
	@Autowired
	private IVtPilaProductoLogic vtPilaProductoLogic;
	@Autowired
	private IVtPrioridadLogic vtPrioridadLogic;
	@Autowired
	private IVtProyectoLogic vtProyectoLogic;
	@Autowired
	private IVtProgresoArtefactoLogic vtProgresoArtefactoLogic;
	@Autowired
	private IVtProyectoUsuarioLogic vtProyectoUsuarioLogic;
	@Autowired
	private IVtRolLogic vtRolLogic;
	@Autowired
	private IVtSprintLogic vtSprintLogic;
	@Autowired
	private IVtTipoArtefactoLogic vtTipoArtefactoLogic;
	@Autowired
	private IVtUsuarioLogic vtUsuarioLogic;
	@Autowired
	private IVtUsuarioArtefactoLogic vtUsuarioArtefactoLogic;
	@Autowired
	private IVtUsuarioRolLogic vtUsuarioRolLogic;
	@Autowired
	private IVtSeguridadLogica vtSeguridadLogic;

	public List<VtProgresoArtefacto> getVtProgresoArtefacto() throws Exception {
		return vtProgresoArtefactoLogic.getVtProgresoArtefacto();
	}

	public void saveVtProgresoArtefacto(VtProgresoArtefacto entity) throws Exception {
		vtProgresoArtefactoLogic.saveVtProgresoArtefacto(entity);
	}

	public void deleteVtProgresoArtefacto(VtProgresoArtefacto entity) throws Exception {
		vtProgresoArtefactoLogic.deleteVtProgresoArtefacto(entity);
	}

	public void updateVtProgresoArtefacto(VtProgresoArtefacto entity) throws Exception {
		vtProgresoArtefactoLogic.updateVtProgresoArtefacto(entity);
	}

	public VtProgresoArtefacto getVtProgresoArtefacto(Long proartCodigo) throws Exception {
		VtProgresoArtefacto vtProgresoArtefacto = null;

		try {
			vtProgresoArtefacto = vtProgresoArtefactoLogic.getVtProgresoArtefacto(proartCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtProgresoArtefacto;
	}

	public List<VtProgresoArtefacto> findByCriteriaInVtProgresoArtefacto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtProgresoArtefactoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtProgresoArtefacto> findPageVtProgresoArtefacto(String sortColumnName, boolean sortAscending,
			int startRow, int maxResults) throws Exception {
		return vtProgresoArtefactoLogic.findPageVtProgresoArtefacto(sortColumnName, sortAscending, startRow,
				maxResults);
	}

	public Long findTotalNumberVtProgresoArtefacto() throws Exception {
		return vtProgresoArtefactoLogic.findTotalNumberVtProgresoArtefacto();
	}

	public List<VtProgresoArtefactoDTO> getDataVtProgresoArtefacto() throws Exception {
		return vtProgresoArtefactoLogic.getDataVtProgresoArtefacto();
	}

	public List<VtEstadoSprint> getVtEstadoSprint() throws Exception {
		return vtEstadoSprintLogic.getVtEstadoSprint();
	}

	public void saveVtEstadoSprint(VtEstadoSprint entity) throws Exception {
		vtEstadoSprintLogic.saveVtEstadoSprint(entity);
	}

	public void deleteVtEstadoSprint(VtEstadoSprint entity) throws Exception {
		vtEstadoSprintLogic.deleteVtEstadoSprint(entity);
	}

	public void updateVtEstadoSprint(VtEstadoSprint entity) throws Exception {
		vtEstadoSprintLogic.updateVtEstadoSprint(entity);
	}

	public VtEstadoSprint getVtEstadoSprint(Long estsprCodigo) throws Exception {
		VtEstadoSprint vtEstadoSprint = null;

		try {
			vtEstadoSprint = vtEstadoSprintLogic.getVtEstadoSprint(estsprCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtEstadoSprint;
	}

	public List<VtEstadoSprint> findByCriteriaInVtEstadoSprint(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtEstadoSprintLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtEstadoSprint> findPageVtEstadoSprint(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtEstadoSprintLogic.findPageVtEstadoSprint(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtEstadoSprint() throws Exception {
		return vtEstadoSprintLogic.findTotalNumberVtEstadoSprint();
	}

	public List<VtEstadoSprintDTO> getDataVtEstadoSprint() throws Exception {
		return vtEstadoSprintLogic.getDataVtEstadoSprint();
	}

	public List<VtArchivo> getVtArchivo() throws Exception {
		return vtArchivoLogic.getVtArchivo();
	}

	public void saveVtArchivo(VtArchivo entity) throws Exception {
		vtArchivoLogic.saveVtArchivo(entity);
	}

	public void deleteVtArchivo(VtArchivo entity) throws Exception {
		vtArchivoLogic.deleteVtArchivo(entity);
	}

	public void updateVtArchivo(VtArchivo entity) throws Exception {
		vtArchivoLogic.updateVtArchivo(entity);
	}

	public VtArchivo getVtArchivo(Long archCodigo) throws Exception {
		VtArchivo vtArchivo = null;

		try {
			vtArchivo = vtArchivoLogic.getVtArchivo(archCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtArchivo;
	}

	public List<VtArchivo> findByCriteriaInVtArchivo(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtArchivoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtArchivo> findPageVtArchivo(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtArchivoLogic.findPageVtArchivo(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtArchivo() throws Exception {
		return vtArchivoLogic.findTotalNumberVtArchivo();
	}

	public List<VtArchivoDTO> getDataVtArchivo() throws Exception {
		return vtArchivoLogic.getDataVtArchivo();
	}

	public List<VtArtefacto> getVtArtefacto() throws Exception {
		return vtArtefactoLogic.getVtArtefacto();
	}

	public void deleteVtArtefacto(VtArtefacto entity) throws Exception {
		vtArtefactoLogic.deleteVtArtefacto(entity);
	}

	public void updateVtArtefacto(VtArtefacto entity) throws Exception {
		vtArtefactoLogic.updateVtArtefacto(entity);
	}

	public VtArtefacto getVtArtefacto(Long arteCodigo) throws Exception {
		VtArtefacto vtArtefacto = null;

		try {
			vtArtefacto = vtArtefactoLogic.getVtArtefacto(arteCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtArtefacto;
	}

	public List<VtArtefacto> findByCriteriaInVtArtefacto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtArtefactoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtArtefacto> findPageVtArtefacto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtArtefactoLogic.findPageVtArtefacto(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtArtefacto() throws Exception {
		return vtArtefactoLogic.findTotalNumberVtArtefacto();
	}

	public List<VtArtefactoDTO> getDataVtArtefacto() throws Exception {
		return vtArtefactoLogic.getDataVtArtefacto();
	}

	public List<VtEmpresa> getVtEmpresa() throws Exception {
		return vtEmpresaLogic.getVtEmpresa();
	}

	public void saveVtEmpresa(VtEmpresa entity) throws Exception {
		vtEmpresaLogic.saveVtEmpresa(entity);
	}

	public void deleteVtEmpresa(VtEmpresa entity) throws Exception {
		vtEmpresaLogic.deleteVtEmpresa(entity);
	}

	public void updateVtEmpresa(VtEmpresa entity) throws Exception {
		vtEmpresaLogic.updateVtEmpresa(entity);
	}

	public VtEmpresa getVtEmpresa(Long emprCodigo) throws Exception {
		VtEmpresa vtEmpresa = null;

		try {
			vtEmpresa = vtEmpresaLogic.getVtEmpresa(emprCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtEmpresa;
	}

	public List<VtEmpresa> findByCriteriaInVtEmpresa(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtEmpresaLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtEmpresa> findPageVtEmpresa(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtEmpresaLogic.findPageVtEmpresa(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtEmpresa() throws Exception {
		return vtEmpresaLogic.findTotalNumberVtEmpresa();
	}

	public List<VtEmpresaDTO> getDataVtEmpresa() throws Exception {
		return vtEmpresaLogic.getDataVtEmpresa();
	}

	public List<VtEmpresaDTO> getDataVtEmpresaInactiva() throws Exception {
		return vtEmpresaLogic.getDataVtEmpresaInactiva();
	}

	public List<VtEstado> getVtEstado() throws Exception {
		return vtEstadoLogic.getVtEstado();
	}

	public void saveVtEstado(VtEstado entity) throws Exception {
		vtEstadoLogic.saveVtEstado(entity);
	}

	public void deleteVtEstado(VtEstado entity) throws Exception {
		vtEstadoLogic.deleteVtEstado(entity);
	}

	public void updateVtEstado(VtEstado entity) throws Exception {
		vtEstadoLogic.updateVtEstado(entity);
	}

	public VtEstado getVtEstado(Long estaCodigo) throws Exception {
		VtEstado vtEstado = null;

		try {
			vtEstado = vtEstadoLogic.getVtEstado(estaCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtEstado;
	}

	public List<VtEstado> findByCriteriaInVtEstado(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtEstadoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtEstado> findPageVtEstado(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtEstadoLogic.findPageVtEstado(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtEstado() throws Exception {
		return vtEstadoLogic.findTotalNumberVtEstado();
	}

	public List<VtEstadoDTO> getDataVtEstado() throws Exception {
		return vtEstadoLogic.getDataVtEstado();
	}

	public List<VtHistoriaArtefacto> getVtHistoriaArtefacto() throws Exception {
		return vtHistoriaArtefactoLogic.getVtHistoriaArtefacto();
	}

	public void saveVtHistoriaArtefacto(VtHistoriaArtefacto entity) throws Exception {
		vtHistoriaArtefactoLogic.saveVtHistoriaArtefacto(entity);
	}

	public void deleteVtHistoriaArtefacto(VtHistoriaArtefacto entity) throws Exception {
		vtHistoriaArtefactoLogic.deleteVtHistoriaArtefacto(entity);
	}

	public void updateVtHistoriaArtefacto(VtHistoriaArtefacto entity) throws Exception {
		vtHistoriaArtefactoLogic.updateVtHistoriaArtefacto(entity);
	}

	public VtHistoriaArtefacto getVtHistoriaArtefacto(Long historiaCodigo) throws Exception {
		VtHistoriaArtefacto vtHistoriaArtefacto = null;

		try {
			vtHistoriaArtefacto = vtHistoriaArtefactoLogic.getVtHistoriaArtefacto(historiaCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtHistoriaArtefacto;
	}

	public List<VtHistoriaArtefacto> findByCriteriaInVtHistoriaArtefacto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtHistoriaArtefactoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtHistoriaArtefacto> findPageVtHistoriaArtefacto(String sortColumnName, boolean sortAscending,
			int startRow, int maxResults) throws Exception {
		return vtHistoriaArtefactoLogic.findPageVtHistoriaArtefacto(sortColumnName, sortAscending, startRow,
				maxResults);
	}

	public Long findTotalNumberVtHistoriaArtefacto() throws Exception {
		return vtHistoriaArtefactoLogic.findTotalNumberVtHistoriaArtefacto();
	}

	public List<VtHistoriaArtefactoDTO> getDataVtHistoriaArtefacto() throws Exception {
		return vtHistoriaArtefactoLogic.getDataVtHistoriaArtefacto();
	}

	public List<VtInteres> getVtInteres() throws Exception {
		return vtInteresLogic.getVtInteres();
	}

	public void saveVtInteres(VtInteres entity) throws Exception {
		vtInteresLogic.saveVtInteres(entity);
	}

	public void deleteVtInteres(VtInteres entity) throws Exception {
		vtInteresLogic.deleteVtInteres(entity);
	}

	public void updateVtInteres(VtInteres entity) throws Exception {
		vtInteresLogic.updateVtInteres(entity);
	}

	public VtInteres getVtInteres(Long inteCodigo) throws Exception {
		VtInteres vtInteres = null;

		try {
			vtInteres = vtInteresLogic.getVtInteres(inteCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtInteres;
	}

	public List<VtInteres> findByCriteriaInVtInteres(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtInteresLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtInteres> findPageVtInteres(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtInteresLogic.findPageVtInteres(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtInteres() throws Exception {
		return vtInteresLogic.findTotalNumberVtInteres();
	}

	public List<VtInteresDTO> getDataVtInteres() throws Exception {
		return vtInteresLogic.getDataVtInteres();
	}

	public List<VtPilaProducto> getVtPilaProducto() throws Exception {
		return vtPilaProductoLogic.getVtPilaProducto();
	}

	public void saveVtPilaProducto(VtPilaProducto entity) throws Exception {
		vtPilaProductoLogic.saveVtPilaProducto(entity);
	}

	public void deleteVtPilaProducto(VtPilaProducto entity) throws Exception {
		vtPilaProductoLogic.deleteVtPilaProducto(entity);
	}

	public void updateVtPilaProducto(VtPilaProducto entity) throws Exception {
		vtPilaProductoLogic.updateVtPilaProducto(entity);
	}

	public VtPilaProducto getVtPilaProducto(Long pilaCodigo) throws Exception {
		VtPilaProducto vtPilaProducto = null;

		try {
			vtPilaProducto = vtPilaProductoLogic.getVtPilaProducto(pilaCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtPilaProducto;
	}

	public List<VtPilaProducto> findByCriteriaInVtPilaProducto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtPilaProductoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtPilaProducto> findPageVtPilaProducto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtPilaProductoLogic.findPageVtPilaProducto(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtPilaProducto() throws Exception {
		return vtPilaProductoLogic.findTotalNumberVtPilaProducto();
	}

	public List<VtPilaProductoDTO> getDataVtPilaProducto() throws Exception {
		return vtPilaProductoLogic.getDataVtPilaProducto();
	}

	public List<VtPilaProductoDTO> getDataVtPilaProductoNombreProyecto(Long codigoFiltro) throws Exception {
		return vtPilaProductoLogic.getDataVtPilaProductoNombreProyecto(codigoFiltro);
	}

	public List<VtPilaProductoDTO> getDataVtPilaProducto(String empresa) throws Exception {
		return vtPilaProductoLogic.getDataVtPilaProductoEmpresa(empresa);
	}

	public List<VtPrioridad> getVtPrioridad() throws Exception {
		return vtPrioridadLogic.getVtPrioridad();
	}

	public void saveVtPrioridad(VtPrioridad entity) throws Exception {
		vtPrioridadLogic.saveVtPrioridad(entity);
	}

	public void deleteVtPrioridad(VtPrioridad entity) throws Exception {
		vtPrioridadLogic.deleteVtPrioridad(entity);
	}

	public void updateVtPrioridad(VtPrioridad entity) throws Exception {
		vtPrioridadLogic.updateVtPrioridad(entity);
	}

	public VtPrioridad getVtPrioridad(Long prioCodigo) throws Exception {
		VtPrioridad vtPrioridad = null;

		try {
			vtPrioridad = vtPrioridadLogic.getVtPrioridad(prioCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtPrioridad;
	}

	public List<VtPrioridad> findByCriteriaInVtPrioridad(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtPrioridadLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtPrioridad> findPageVtPrioridad(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtPrioridadLogic.findPageVtPrioridad(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtPrioridad() throws Exception {
		return vtPrioridadLogic.findTotalNumberVtPrioridad();
	}

	public List<VtPrioridadDTO> getDataVtPrioridad() throws Exception {
		return vtPrioridadLogic.getDataVtPrioridad();
	}

	public List<VtProyecto> getVtProyecto() throws Exception {
		return vtProyectoLogic.getVtProyecto();
	}

	public void saveVtProyecto(VtProyecto entity) throws Exception {
		vtProyectoLogic.saveVtProyecto(entity);
	}

	public void deleteVtProyecto(VtProyecto entity) throws Exception {
		vtProyectoLogic.deleteVtProyecto(entity);
	}

	public void updateVtProyecto(VtProyecto entity) throws Exception {
		vtProyectoLogic.updateVtProyecto(entity);
	}

	public VtProyecto getVtProyecto(Long proyCodigo) throws Exception {
		VtProyecto vtProyecto = null;

		try {
			vtProyecto = vtProyectoLogic.getVtProyecto(proyCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtProyecto;
	}

	public List<VtProyecto> findByCriteriaInVtProyecto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtProyectoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtProyecto> findPageVtProyecto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtProyectoLogic.findPageVtProyecto(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtProyecto() throws Exception {
		return vtProyectoLogic.findTotalNumberVtProyecto();
	}

	public List<VtProyectoDTO> getDataVtProyecto(Long codigoFiltro) throws Exception {
		return vtProyectoLogic.getDataVtProyecto(codigoFiltro);
	}

	public List<VtProyectoDTO> getDataVtProyectoInactivo(Long codigoFiltro) throws Exception {
		return vtProyectoLogic.getDataVtProyectoInactivo(codigoFiltro);
	}

	public List<VtProyectoUsuario> getVtProyectoUsuario() throws Exception {
		return vtProyectoUsuarioLogic.getVtProyectoUsuario();
	}

	public void saveVtProyectoUsuario(VtProyectoUsuario entity) throws Exception {
		vtProyectoUsuarioLogic.saveVtProyectoUsuario(entity);
	}

	public void deleteVtProyectoUsuario(VtProyectoUsuario entity) throws Exception {
		vtProyectoUsuarioLogic.deleteVtProyectoUsuario(entity);
	}

	public void updateVtProyectoUsuario(VtProyectoUsuario entity) throws Exception {
		vtProyectoUsuarioLogic.updateVtProyectoUsuario(entity);
	}

	public VtProyectoUsuario getVtProyectoUsuario(Long prusCodigo) throws Exception {
		VtProyectoUsuario vtProyectoUsuario = null;

		try {
			vtProyectoUsuario = vtProyectoUsuarioLogic.getVtProyectoUsuario(prusCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtProyectoUsuario;
	}

	public List<VtProyectoUsuario> findByCriteriaInVtProyectoUsuario(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtProyectoUsuarioLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtProyectoUsuario> findPageVtProyectoUsuario(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtProyectoUsuarioLogic.findPageVtProyectoUsuario(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtProyectoUsuario() throws Exception {
		return vtProyectoUsuarioLogic.findTotalNumberVtProyectoUsuario();
	}

	public List<VtProyectoUsuarioDTO> getDataVtProyectoUsuario() throws Exception {
		return vtProyectoUsuarioLogic.getDataVtProyectoUsuario();
	}

	public List<VtRol> getVtRol() throws Exception {
		return vtRolLogic.getVtRol();
	}

	public void saveVtRol(VtRol entity) throws Exception {
		vtRolLogic.saveVtRol(entity);
	}

	public void deleteVtRol(VtRol entity) throws Exception {
		vtRolLogic.deleteVtRol(entity);
	}

	public void updateVtRol(VtRol entity) throws Exception {
		vtRolLogic.updateVtRol(entity);
	}

	public VtRol getVtRol(Long rolCodigo) throws Exception {
		VtRol vtRol = null;

		try {
			vtRol = vtRolLogic.getVtRol(rolCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtRol;
	}

	public List<VtRol> findByCriteriaInVtRol(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtRolLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtRol> findPageVtRol(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtRolLogic.findPageVtRol(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtRol() throws Exception {
		return vtRolLogic.findTotalNumberVtRol();
	}

	public List<VtRolDTO> getDataVtRol() throws Exception {
		return vtRolLogic.getDataVtRol();
	}

	public List<VtSprint> getVtSprint() throws Exception {
		return vtSprintLogic.getVtSprint();
	}

	public void saveVtSprint(VtSprint entity,String esfuerzoEstimado) throws Exception {
		vtSprintLogic.saveVtSprint(entity,esfuerzoEstimado);
	}

	public void deleteVtSprint(VtSprint entity) throws Exception {
		vtSprintLogic.deleteVtSprint(entity);
	}

	public void updateVtSprint(VtSprint entity) throws Exception {
		vtSprintLogic.updateVtSprint(entity);
	}

	public VtSprint getVtSprint(Long spriCodigo) throws Exception {
		VtSprint vtSprint = null;

		try {
			vtSprint = vtSprintLogic.getVtSprint(spriCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtSprint;
	}

	public List<VtSprint> findByCriteriaInVtSprint(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtSprintLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtSprint> findPageVtSprint(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtSprintLogic.findPageVtSprint(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtSprint() throws Exception {
		return vtSprintLogic.findTotalNumberVtSprint();
	}

	public List<VtSprintDTO> getDataVtSprint() throws Exception {
		return vtSprintLogic.getDataVtSprint();
	}

	public List<VtTipoArtefacto> getVtTipoArtefacto() throws Exception {
		return vtTipoArtefactoLogic.getVtTipoArtefacto();
	}

	public void saveVtTipoArtefacto(VtTipoArtefacto entity) throws Exception {
		vtTipoArtefactoLogic.saveVtTipoArtefacto(entity);
	}

	public void deleteVtTipoArtefacto(VtTipoArtefacto entity) throws Exception {
		vtTipoArtefactoLogic.deleteVtTipoArtefacto(entity);
	}

	public void updateVtTipoArtefacto(VtTipoArtefacto entity) throws Exception {
		vtTipoArtefactoLogic.updateVtTipoArtefacto(entity);
	}

	public VtTipoArtefacto getVtTipoArtefacto(Long tparCodigo) throws Exception {
		VtTipoArtefacto vtTipoArtefacto = null;

		try {
			vtTipoArtefacto = vtTipoArtefactoLogic.getVtTipoArtefacto(tparCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtTipoArtefacto;
	}

	public List<VtTipoArtefacto> findByCriteriaInVtTipoArtefacto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtTipoArtefactoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtTipoArtefacto> findPageVtTipoArtefacto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtTipoArtefactoLogic.findPageVtTipoArtefacto(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtTipoArtefacto() throws Exception {
		return vtTipoArtefactoLogic.findTotalNumberVtTipoArtefacto();
	}

	public List<VtTipoArtefactoDTO> getDataVtTipoArtefacto() throws Exception {
		return vtTipoArtefactoLogic.getDataVtTipoArtefacto();
	}

	public List<VtUsuario> getVtUsuario() throws Exception {
		return vtUsuarioLogic.getVtUsuario();
	}

	public void saveVtUsuario(VtUsuario entity) throws Exception {
		vtUsuarioLogic.saveVtUsuario(entity);
	}

	public void deleteVtUsuario(VtUsuario entity) throws Exception {
		vtUsuarioLogic.deleteVtUsuario(entity);
	}

	public void updateVtUsuario(VtUsuario entity) throws Exception {
		vtUsuarioLogic.updateVtUsuario(entity);
	}

	public VtUsuario getVtUsuario(Long usuaCodigo) throws Exception {
		VtUsuario vtUsuario = null;

		try {
			vtUsuario = vtUsuarioLogic.getVtUsuario(usuaCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtUsuario;
	}

	public List<VtUsuario> findByCriteriaInVtUsuario(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtUsuarioLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtUsuario> findPageVtUsuario(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		return vtUsuarioLogic.findPageVtUsuario(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtUsuario() throws Exception {
		return vtUsuarioLogic.findTotalNumberVtUsuario();
	}

	public List<VtUsuarioDTO> getDataVtUsuario() throws Exception {
		return vtUsuarioLogic.getDataVtUsuario();
	}

	public List<VtUsuarioDTO> getDataVtUsuarioInactivo() throws Exception {
		return vtUsuarioLogic.getDataVtUsuarioInactivo();
	}

	public List<VtUsuarioArtefacto> getVtUsuarioArtefacto() throws Exception {
		return vtUsuarioArtefactoLogic.getVtUsuarioArtefacto();
	}

	public void saveVtUsuarioArtefacto(VtUsuarioArtefacto entity) throws Exception {
		vtUsuarioArtefactoLogic.saveVtUsuarioArtefacto(entity);
	}

	public void deleteVtUsuarioArtefacto(VtUsuarioArtefacto entity) throws Exception {
		vtUsuarioArtefactoLogic.deleteVtUsuarioArtefacto(entity);
	}

	public void updateVtUsuarioArtefacto(VtUsuarioArtefacto entity) throws Exception {
		vtUsuarioArtefactoLogic.updateVtUsuarioArtefacto(entity);
	}

	public VtUsuarioArtefacto getVtUsuarioArtefacto(Long usuartCodigo) throws Exception {
		VtUsuarioArtefacto vtUsuarioArtefacto = null;

		try {
			vtUsuarioArtefacto = vtUsuarioArtefactoLogic.getVtUsuarioArtefacto(usuartCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtUsuarioArtefacto;
	}

	public List<VtUsuarioArtefacto> findByCriteriaInVtUsuarioArtefacto(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtUsuarioArtefactoLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtUsuarioArtefacto> findPageVtUsuarioArtefacto(String sortColumnName, boolean sortAscending,
			int startRow, int maxResults) throws Exception {
		return vtUsuarioArtefactoLogic.findPageVtUsuarioArtefacto(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtUsuarioArtefacto() throws Exception {
		return vtUsuarioArtefactoLogic.findTotalNumberVtUsuarioArtefacto();
	}

	public List<VtUsuarioArtefactoDTO> getDataVtUsuarioArtefacto() throws Exception {
		return vtUsuarioArtefactoLogic.getDataVtUsuarioArtefacto();
	}

	public List<VtUsuarioRol> getVtUsuarioRol() throws Exception {
		return vtUsuarioRolLogic.getVtUsuarioRol();
	}

	public void saveVtUsuarioRol(VtUsuarioRol entity) throws Exception {
		vtUsuarioRolLogic.saveVtUsuarioRol(entity);
	}

	public void deleteVtUsuarioRol(VtUsuarioRol entity) throws Exception {
		vtUsuarioRolLogic.deleteVtUsuarioRol(entity);
	}

	public void updateVtUsuarioRol(VtUsuarioRol entity) throws Exception {
		vtUsuarioRolLogic.updateVtUsuarioRol(entity);
	}

	public VtUsuarioRol getVtUsuarioRol(Long usroCodigo) throws Exception {
		VtUsuarioRol vtUsuarioRol = null;

		try {
			vtUsuarioRol = vtUsuarioRolLogic.getVtUsuarioRol(usroCodigo);
		} catch (Exception e) {
			throw e;
		}

		return vtUsuarioRol;
	}

	public List<VtUsuarioRol> findByCriteriaInVtUsuarioRol(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception {
		return vtUsuarioRolLogic.findByCriteria(variables, variablesBetween, variablesBetweenDates);
	}

	public List<VtUsuarioRol> findPageVtUsuarioRol(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception {
		return vtUsuarioRolLogic.findPageVtUsuarioRol(sortColumnName, sortAscending, startRow, maxResults);
	}

	public Long findTotalNumberVtUsuarioRol() throws Exception {
		return vtUsuarioRolLogic.findTotalNumberVtUsuarioRol();
	}

	public List<VtUsuarioRolDTO> getDataVtUsuarioRol() throws Exception {
		return vtUsuarioRolLogic.getDataVtUsuarioRol();
	}

	@Override
	public VtUsuario consultarLogin(String login) {
		return vtUsuarioLogic.consultarLogin(login);
	}

	@Override
	public VtEmpresa consultarEmpresaPorId(String identificacion) {
		return vtEmpresaLogic.consultarEmpresaPorId(identificacion);
	}

	@Override
	public VtUsuario consultarUsuarioPorCodigo(Long usuacodigo) {
		return vtUsuarioLogic.consultarUsuarioPorCodigo(usuacodigo);
	}

	@Override
	public VtUsuario autenticarUsuario(String login, String clave) throws Exception {
		return seguridadLogica.autenticarUsuario(login, clave);
	}

	@Override
	public VtUsuario findUsuarioByLogin(String login) {
		return vtUsuarioLogic.findUsuarioByLogin(login);
	}

	@Override
	public List<VtPilaProductoDTO> getDataVtPilaProductoNombreProyectoI(Long codigoFiltro) throws Exception {
		return vtPilaProductoLogic.getDataVtPilaProductoNombreProyectoI(codigoFiltro);
	}

	@Override
	public List<VtSprintDTO> getDataVtSprintFiltro(Long codigoFiltro) throws Exception {
		return vtSprintLogic.getDataVtSprintFiltro(codigoFiltro);
	}

	@Override
	public List<VtSprintDTO> getDataVtSprintFiltroI(Long codigoFiltro) throws Exception {
		return vtSprintLogic.getDataVtSprintFiltroI(codigoFiltro);
	}

	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoFiltro(Long codigoFiltro) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoFiltro(codigoFiltro);
	}
	
	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroDesarrollador(Long codigoFiltro, Long codigoUsuario) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoFiltroDesarrollador(codigoFiltro, codigoUsuario);
	}

	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoActivo(Long codigoFiltro) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoActivo(codigoFiltro);
	}

	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroI(Long codigoFiltro) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoFiltroI(codigoFiltro);
	}
	
	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoFiltroIDesarrollador(Long codigoFiltro, Long codigoUsuario) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoFiltroIDesarrollador(codigoFiltro, codigoUsuario);
	}

	@Override
	public List<VtRolDTO> getDataVtRolInactivo() throws Exception {
		return vtRolLogic.getDataVtRolInactivo();
	}

	@Override
	public List<VtUsuario> obtenerUsuariosAsignados(VtProyecto vtproyecto) throws Exception {
		return vtUsuarioLogic.obtenerUsuariosAsignados(vtproyecto);
	}

	@Override
	public List<VtUsuario> obtenerUsuariosNoAsignados(VtProyecto vtproyecto) throws Exception {
		return vtUsuarioLogic.obtenerUsuariosNoAsignados(vtproyecto);
	}

	@Override
	public VtProyectoUsuario consultarProyectoUsuarioPorProyectoYPorUsuario(Long proyectoId, Long usuarioId) {

		return vtProyectoUsuarioLogic.consultarProyectoUsuarioPorProyectoYPorUsuario(proyectoId, usuarioId);
	}


	@Override
	public List<VtArchivoDTO> getDataVtArchivoActivo(Long codigoArtefacto) throws Exception {
		return vtArchivoLogic.getDataVtArchivoActivo(codigoArtefacto);
	}

	@Override
	public List<VtHistoriaArtefactoDTO> getDataVtHistoriaArtefactoPorIdArtefacto(Long codigoArtefacto)
			throws Exception {
		return vtHistoriaArtefactoLogic.getDataVtHistoriaArtefactoPorIdArtefacto(codigoArtefacto);
	}

	@Override
	public List<VtArtefacto> consultarArtefactosSinAsignarASprint(Long codigoProyecto) throws Exception {
		return vtArtefactoLogic.consultarArtefactosSinAsignarASprint(codigoProyecto);
	}

	@Override
	public List<VtArtefacto> consultarArtefactosAsignadosASprint(Long codigoSprint) throws Exception {
		return vtArtefactoLogic.consultarArtefactosAsignadosASprint(codigoSprint);
	}

	public VtArtefacto consultarArtefactosAsignadosASprintYPila(Long artecodigo, Long codigoPila) throws Exception {
		return vtArtefactoLogic.consultarArtefactosAsignadosASprintYPila(artecodigo, codigoPila);
	}

	@Override
	public List<VtRol> obtenerRolesAsignados(VtUsuario vtUsuario) throws Exception {
		return vtRolLogic.obtenerRolesAsignados(vtUsuario);
	}

	@Override
	public List<VtRol> obtenerRolesNoAsignados(VtUsuario vtUsuario) throws Exception {
		return vtRolLogic.obtenerRolesNoAsignados(vtUsuario);
	}

	@Override
	public VtUsuarioRol consultarRolUsuarioPorUsuarioYPorRol(Long usuarioId, Long rolId) throws Exception {

		return vtUsuarioRolLogic.consultarRolUsuarioPorUsuarioYPorRol(usuarioId, rolId);
	}

	@Override
	public List<VtArtefacto> consultarTodosLosArtefactosAsignados() throws Exception {
		return vtArtefactoLogic.consultarTodosLosArtefactosAsignados();
	}

	public List<VtUsuarioRol> consultarRolUsuarioPorUsuario(Long usuarioId) throws Exception {
		return vtUsuarioRolLogic.consultarRolUsuarioPorUsuario(usuarioId);
	}

	@Override
	public void saveVtArtefacto(VtArtefacto vtArtefacto, String esfuerzoEstimado, String esfuerzoRestante,
			String puntos) throws Exception {
		vtArtefactoLogic.saveVtArtefacto(vtArtefacto, esfuerzoEstimado, esfuerzoRestante, puntos);

	}

	@Override
	public List<VtArtefacto> obtenerArtefactosNoAsignados(VtUsuario vtUsuario,Long codigoProyecto) throws Exception {
		return vtArtefactoLogic.obtenerArtefactosNoAsignados(vtUsuario,codigoProyecto);
	}

	@Override
	public List<VtArtefacto> obtenerArtefactosAsignados(VtUsuario vtUsuario,Long codigoProyecto) throws Exception {
		return vtArtefactoLogic.obtenerArtefactosAsignados(vtUsuario, codigoProyecto);
	}

	@Override
	public VtUsuarioArtefacto consultarUsuarioArtefactoPorUsuarioYArtefacto(Long codigoUsuario, Long codigoArtefacto)
			throws Exception {
		return vtArtefactoLogic.consultarUsuarioArtefactoPorUsuarioYArtefacto(codigoUsuario, codigoArtefacto);
	}

	@Override
	public List<VtArchivoDTO> getDataVtArchivoInactivo(Long codigoArtefacto) throws Exception {
		return vtArchivoLogic.getDataVtArchivoInactivo(codigoArtefacto);
	}

	@Override
	public List<VtArtefactoDTO> obtenerArtefactosAsignadosDTO(VtUsuario vtUsuario) throws Exception {
		return vtArtefactoLogic.obtenerArtefactosAsignadosDTO(vtUsuario);
	}

	@Override
	public List<VtProyectoUsuario> consultarProyectoUsuarioPorUsuario(Long usuarioCodigo) throws Exception {
		return vtProyectoLogic.consultarProyectoUsuarioPorUsuario(usuarioCodigo);
	}

	@Override
	public VtSprint consultarSprintUsuario(Long codigoEmpresa) {
		return vtSprintLogic.consultarSprintUsuario(codigoEmpresa);
	}

	@Override
	public List<VtProyectoUsuario> consultarProyectoUsuario(Long codigoUsuario) {
		return vtProyectoUsuarioLogic.consultarProyectoUsuario(codigoUsuario);
	}

	@Override
	public VtUsuarioRol consultarRolUsuario(Long codigoUsuario) throws Exception {
		return vtUsuarioRolLogic.consultarRolUsuario(codigoUsuario);
	}

	@Override
	public String recuperarContraseña(String login) throws Exception {
		return vtSeguridadLogic.guardarUsuario(login);
	}

	@Override
	public VtUsuario guardarUsuario(String login) throws Exception {
		return null;
	}
	
	public List<VtArtefacto> consultarTodosLosArtefactosDeUnaPila(Long codigoPila)throws Exception{
		return vtArtefactoLogic.consultarTodosLosArtefactosDeUnaPila(codigoPila) ;
	}

	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoPilaFiltroA(Long codigoFiltro) throws Exception {
		return vtArtefactoLogic.getDataVtArtefactoPilaFiltroA(codigoFiltro);
	}

	@Override
	public List<VtArtefactoDTO> getDataVtArtefactoPilaFiltroI(Long codigoFiltro) throws Exception {

		return vtArtefactoLogic.getDataVtArtefactoPilaFiltroI(codigoFiltro);
	}

	@Override
	public void enviarMensajeAlCorreo(String from, String to, String subject, String body) throws Exception {
		vtUsuarioLogic.enviarMensajeAlCorreo(from, to, subject, body);
		
	}

	@Override
	public List<VtProyectoDTO> getDataVtProyectoActivo(Long codigoFiltro) throws Exception {
		return vtProyectoLogic.getDataVtProyectoActivo(codigoFiltro);
	}

	@Override
	public VtUsuarioArtefacto consultarUsuarioArtefactoPorArtefacto(Long codigoArtefacto) throws Exception {
		return vtUsuarioArtefactoLogic.consultarUsuarioArtefactoPorArtefacto(codigoArtefacto);
	}

}
