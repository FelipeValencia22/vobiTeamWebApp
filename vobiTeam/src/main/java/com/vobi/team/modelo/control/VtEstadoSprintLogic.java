package com.vobi.team.modelo.control;



import com.vobi.team.*;
import com.vobi.team.dataaccess.dao.IVtEstadoSprintDAO;
import com.vobi.team.dataaccess.dao.IVtSprintDAO;
import com.vobi.team.exceptions.ZMessManager;
import com.vobi.team.modelo.VtEstadoSprint;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.dto.VtEstadoSprintDTO;
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
@Service("VtEstadoSprintLogic")
public class VtEstadoSprintLogic implements IVtEstadoSprintLogic {
    private static final Logger log = LoggerFactory.getLogger(VtEstadoSprintLogic.class);

    /**
     * DAO injected by Spring that manages VtEstadoSprint entities
     *
     */
    @Autowired
    private IVtEstadoSprintDAO vtEstadoSprintDAO;

    /**
    * DAO injected by Spring that manages VtSprint entities
    *
    */
    @Autowired
    private IVtSprintDAO vtSprintDAO;

    @Transactional(readOnly = true)
    public List<VtEstadoSprint> getVtEstadoSprint() throws Exception {
        log.debug("finding all VtEstadoSprint instances");

        List<VtEstadoSprint> list = new ArrayList<VtEstadoSprint>();

        try {
            list = vtEstadoSprintDAO.findAll();
        } catch (Exception e) {
            log.error("finding all VtEstadoSprint failed", e);
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "VtEstadoSprint");
        } finally {
        }

        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveVtEstadoSprint(VtEstadoSprint entity)
        throws Exception {
        log.debug("saving VtEstadoSprint instance");

        try {
            if (entity.getActivo() == null) {
                throw new ZMessManager().new EmptyFieldException("activo");
            }

            if ((entity.getActivo() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getActivo(), 1) == false)) {
                throw new ZMessManager().new NotValidFormatException("activo");
            }

            if (entity.getEstsprCodigo() == null) {
                throw new ZMessManager().new EmptyFieldException("estsprCodigo");
            }

            if (entity.getFechaCreacion() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "fechaCreacion");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        255) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if (entity.getUsuCreador() == null) {
                throw new ZMessManager().new EmptyFieldException("usuCreador");
            }

            if (getVtEstadoSprint(entity.getEstsprCodigo()) != null) {
                throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
            }

            vtEstadoSprintDAO.save(entity);

            log.debug("save VtEstadoSprint successful");
        } catch (Exception e) {
            log.error("save VtEstadoSprint failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteVtEstadoSprint(VtEstadoSprint entity)
        throws Exception {
        log.debug("deleting VtEstadoSprint instance");

        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("VtEstadoSprint");
        }

        if (entity.getEstsprCodigo() == null) {
            throw new ZMessManager().new EmptyFieldException("estsprCodigo");
        }

        List<VtSprint> vtSprints = null;

        try {
            vtSprints = vtSprintDAO.findByProperty("vtEstadoSprint.estsprCodigo",
                    entity.getEstsprCodigo());

            if (Utilities.validationsList(vtSprints) == true) {
                throw new ZMessManager().new DeletingException("vtSprints");
            }

            vtEstadoSprintDAO.delete(entity);

            log.debug("delete VtEstadoSprint successful");
        } catch (Exception e) {
            log.error("delete VtEstadoSprint failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateVtEstadoSprint(VtEstadoSprint entity)
        throws Exception {
        log.debug("updating VtEstadoSprint instance");

        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion(
                    "VtEstadoSprint");
            }

            if (entity.getActivo() == null) {
                throw new ZMessManager().new EmptyFieldException("activo");
            }

            if ((entity.getActivo() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getActivo(), 1) == false)) {
                throw new ZMessManager().new NotValidFormatException("activo");
            }

            if (entity.getEstsprCodigo() == null) {
                throw new ZMessManager().new EmptyFieldException("estsprCodigo");
            }

            if (entity.getFechaCreacion() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "fechaCreacion");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        255) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if (entity.getUsuCreador() == null) {
                throw new ZMessManager().new EmptyFieldException("usuCreador");
            }

            vtEstadoSprintDAO.update(entity);

            log.debug("update VtEstadoSprint successful");
        } catch (Exception e) {
            log.error("update VtEstadoSprint failed", e);
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = true)
    public List<VtEstadoSprintDTO> getDataVtEstadoSprint()
        throws Exception {
        try {
            List<VtEstadoSprint> vtEstadoSprint = vtEstadoSprintDAO.findAll();

            List<VtEstadoSprintDTO> vtEstadoSprintDTO = new ArrayList<VtEstadoSprintDTO>();

            for (VtEstadoSprint vtEstadoSprintTmp : vtEstadoSprint) {
                VtEstadoSprintDTO vtEstadoSprintDTO2 = new VtEstadoSprintDTO();

                vtEstadoSprintDTO2.setEstsprCodigo(vtEstadoSprintTmp.getEstsprCodigo());
                vtEstadoSprintDTO2.setActivo((vtEstadoSprintTmp.getActivo() != null)
                    ? vtEstadoSprintTmp.getActivo() : null);
                vtEstadoSprintDTO2.setFechaCreacion(vtEstadoSprintTmp.getFechaCreacion());
                vtEstadoSprintDTO2.setFechaModificacion(vtEstadoSprintTmp.getFechaModificacion());
                vtEstadoSprintDTO2.setNombre((vtEstadoSprintTmp.getNombre() != null)
                    ? vtEstadoSprintTmp.getNombre() : null);
                vtEstadoSprintDTO2.setUsuCreador((vtEstadoSprintTmp.getUsuCreador() != null)
                    ? vtEstadoSprintTmp.getUsuCreador() : null);
                vtEstadoSprintDTO2.setUsuModificador((vtEstadoSprintTmp.getUsuModificador() != null)
                    ? vtEstadoSprintTmp.getUsuModificador() : null);
                vtEstadoSprintDTO.add(vtEstadoSprintDTO2);
            }

            return vtEstadoSprintDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public VtEstadoSprint getVtEstadoSprint(Long estsprCodigo)
        throws Exception {
        log.debug("getting VtEstadoSprint instance");

        VtEstadoSprint entity = null;

        try {
            entity = vtEstadoSprintDAO.findById(estsprCodigo);
        } catch (Exception e) {
            log.error("get VtEstadoSprint failed", e);
            throw new ZMessManager().new FindingException("VtEstadoSprint");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public List<VtEstadoSprint> findPageVtEstadoSprint(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<VtEstadoSprint> entity = null;

        try {
            entity = vtEstadoSprintDAO.findPage(sortColumnName, sortAscending,
                    startRow, maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException(
                "VtEstadoSprint Count");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public Long findTotalNumberVtEstadoSprint() throws Exception {
        Long entity = null;

        try {
            entity = vtEstadoSprintDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException(
                "VtEstadoSprint Count");
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
    public List<VtEstadoSprint> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<VtEstadoSprint> list = new ArrayList<VtEstadoSprint>();
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
            list = vtEstadoSprintDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
}
