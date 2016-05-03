package com.vobi.team.modelo.control;

import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioArtefacto;
import com.vobi.team.modelo.dto.VtArtefactoDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;

import java.math.BigDecimal;

import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Zathura Code Generator http://zathuracode.org/ www.zathuracode.org
 *
 */
public interface IVtArtefactoLogic {
	public List<VtArtefacto> getVtArtefacto() throws Exception;

	/**
	 * Save an new VtArtefacto entity
	 */
	public void saveVtArtefacto(VtArtefacto entity, String esfuerzoEstimado, String esfuerzoRestante, String puntos)
			throws Exception;

	/**
	 * esfuerzoEstimado, esfuerzoReal, esfueroRestante, puntos Delete an
	 * existing VtArtefacto entity
	 *
	 */
	public void deleteVtArtefacto(VtArtefacto entity) throws Exception;

	/**
	 * Update an existing VtArtefacto entity
	 *
	 */
	public void updateVtArtefacto(VtArtefacto entity) throws Exception;

	/**
	 * Load an existing VtArtefacto entity
	 *
	 */
	public VtArtefacto getVtArtefacto(Long arteCodigo) throws Exception;

	public List<VtArtefacto> findByCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) throws Exception;

	public List<VtArtefacto> findPageVtArtefacto(String sortColumnName, boolean sortAscending, int startRow,
			int maxResults) throws Exception;

	public Long findTotalNumberVtArtefacto() throws Exception;

	public List<VtArtefactoDTO> getDataVtArtefacto() throws Exception;

	public List<VtArtefactoDTO> getDataVtArtefactoFiltro(Long codigoFiltro) throws Exception;

	public List<VtArtefactoDTO> getDataVtArtefactoActivo(Long codigoFiltro) throws Exception;

	public List<VtArtefactoDTO> getDataVtArtefactoFiltroI(Long codigoFiltro) throws Exception;

	public List<VtArtefacto> consultarArtefactosSinAsignarASprint() throws Exception;

	public List<VtArtefacto> consultarArtefactosAsignadosASprint(Long codigoSprint) throws Exception;

	public VtArtefacto consultarArtefactosAsignadosASprintYPila(Long artecodigo, Long codigoPila) throws Exception;

	public List<VtArtefacto> consultarTodosLosArtefactosAsignados() throws Exception;

	public List<VtArtefacto> obtenerArtefactosNoAsignados(VtUsuario vtUsuario) throws Exception;

	public List<VtArtefacto> obtenerArtefactosAsignados(VtUsuario vtUsuario) throws Exception;

	public VtUsuarioArtefacto consultarUsuarioArtefactoPorUsuarioYArtefacto(Long codigoUsuario, Long codigoArtefacto)
			throws Exception;

	public List<VtArtefactoDTO> obtenerArtefactosAsignadosDTO(VtUsuario vtUsuario) throws Exception;
}
