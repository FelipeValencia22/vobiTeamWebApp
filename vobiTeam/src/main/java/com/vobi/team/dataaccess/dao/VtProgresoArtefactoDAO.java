package com.vobi.team.dataaccess.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.stereotype.Repository;

import com.vobi.team.dataaccess.api.HibernateDaoImpl;
import com.vobi.team.modelo.VtProgresoArtefacto;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


/**
 * A data access object (DAO) providing persistence and search support for
 * VtProgresoArtefacto entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.VtProgresoArtefacto
 */
@Scope("singleton")
@Repository("VtProgresoArtefactoDAO")
public class VtProgresoArtefactoDAO extends HibernateDaoImpl<VtProgresoArtefacto, Long>
    implements IVtProgresoArtefactoDAO {
    private static final Logger log = LoggerFactory.getLogger(VtProgresoArtefactoDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IVtProgresoArtefactoDAO getFromApplicationContext(
        ApplicationContext ctx) {
        return (IVtProgresoArtefactoDAO) ctx.getBean("VtProgresoArtefactoDAO");
    }
}
