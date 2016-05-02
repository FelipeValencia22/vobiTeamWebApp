package com.vobi.team.presentation.backingBeans;

import com.vobi.team.modelo.VtRol;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.*;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;

import org.primefaces.model.menu.MenuModel;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "menuView")
public class MenuView {

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	private MenuModel model;
	boolean esAdmin = false;
	boolean esDesarrollador = false;
	boolean esCliente = false;

	@PostConstruct
	public void init() {

		model = new DefaultMenuModel();
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			List<VtUsuarioRol> usuRol = ((List<VtUsuarioRol>) businessDelegatorView
					.consultarRolUsuarioPorUsuario(vtUsuarioEnSession.getUsuaCodigo().longValue()));

			for (VtUsuarioRol vtUsuarioRol : usuRol) {
				if (vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equalsIgnoreCase("ADMINISTRADOR")
						&& vtUsuarioRol.getActivo().equals("S")) {
					establecerPermisosADMIN();
					esAdmin = true;
				}
				if (vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equalsIgnoreCase("DESARROLLADOR") && esAdmin
						&& vtUsuarioRol.getActivo().equals("S")) {
				} else {
					if (vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equalsIgnoreCase("DESARROLLADOR")
							&& vtUsuarioRol.getActivo().equals("S")) {
						establecerPermisosDESARROLLADOR();
						esDesarrollador = true;
					}
				}

				if (vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equalsIgnoreCase("CLIENTE") && esAdmin
						&& esDesarrollador && vtUsuarioRol.getActivo().equals("S")) {
				} else {
					if (vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equalsIgnoreCase("CLIENTE")
							&& vtUsuarioRol.getActivo().equals("S")) {
						establecerPermisosCliente();
						esCliente = true;
					}
				}
			}
			esAdmin = false;
			esCliente = false;
			esDesarrollador = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void establecerPermisosADMIN() {
		// Empresa

		DefaultMenuItem dashboardItem = new DefaultMenuItem("Dashboard");
		dashboardItem.setOutcome("/XHTML/dashboard");
		dashboardItem.setIcon("icon-home-outline");
		dashboardItem.setId("sm_dashboard");
		dashboardItem.setContainerStyleClass("layout-menubar-active");
		model.addElement(dashboardItem);

		DefaultMenuItem empresaItem = new DefaultMenuItem("Empresas");
		empresaItem.setOutcome("/XHTML/vtGestionEmpresa.xhtml");
		empresaItem.setIcon("fa fa-building-o");
		empresaItem.setId("sm_vtEmpresa");
		empresaItem.setContainerStyleClass("layout-menubar-active");
		model.addElement(empresaItem);

		// Usuario
		DefaultMenuItem usuarioItem = new DefaultMenuItem("Usuarios");
		usuarioItem.setOutcome("/XHTML/vtGestionUsuarios.xhtml");
		usuarioItem.setIcon("fa fa-user");
		usuarioItem.setId("sm_VtUsuario");
		model.addElement(usuarioItem);

		DefaultMenuItem proyectoItem = new DefaultMenuItem("Proyectos");
		proyectoItem.setOutcome("/XHTML/vtGestionProyecto.xhtml");
		proyectoItem.setIcon("fa fa-folder-open");
		proyectoItem.setId("sm_VtProyecto");
		model.addElement(proyectoItem);

		DefaultMenuItem desarrolladorProyectoItem = new DefaultMenuItem("Desarrolladores-Proyecto");
		desarrolladorProyectoItem.setOutcome("/XHTML/vtDesarrolladoresProyecto.xhtml");
		desarrolladorProyectoItem.setIcon("fa fa-folder-open");
		desarrolladorProyectoItem.setId("sm_VtDesarrolladoresProyecto");
		model.addElement(desarrolladorProyectoItem);

		// Pila de producto
		DefaultMenuItem pilaItem = new DefaultMenuItem("Pila de producto");
		pilaItem.setOutcome("/XHTML/vtGestionPilaProducto.xhtml");
		pilaItem.setIcon("fa fa-server");
		pilaItem.setId("sm_VtCrearProductBacklog");
		model.addElement(pilaItem);

		// Sprint
		DefaultMenuItem sprintItem = new DefaultMenuItem("Sprint");
		sprintItem.setOutcome("/XHTML/vtGestionSprint.xhtml");
		sprintItem.setIcon("fa fa-calendar");
		sprintItem.setId("sm_VtSprint");
		model.addElement(sprintItem);

		DefaultMenuItem artefactosItem = new DefaultMenuItem("Artefacto");
		artefactosItem.setOutcome("/XHTML/vtGestionArtefacto.xhtml");
		artefactosItem.setIcon("fa fa-files-o");
		artefactosItem.setId("sm_VtArtefacto");
		model.addElement(artefactosItem);
		
		DefaultMenuItem artefactosSprintItem = new DefaultMenuItem("Artefacto-Sprint");
		artefactosSprintItem.setOutcome("/XHTML/vtArtefactoSprint.xhtml");
		artefactosSprintItem.setIcon("fa fa-calendar");
		artefactosSprintItem.setId("sm_VtArtefactosSprint");
		model.addElement(artefactosSprintItem);

		DefaultMenuItem artefactosUsuarioItem = new DefaultMenuItem("Artefacto-Usuario");
		artefactosUsuarioItem.setOutcome("/XHTML/vtUsuarioArtefacto.xhtml");
		artefactosUsuarioItem.setIcon("fa fa-calendar");
		artefactosUsuarioItem.setId("sm_VtArtefactosUsuario");
		model.addElement(artefactosUsuarioItem);

		DefaultMenuItem rolItem = new DefaultMenuItem("Roles");
		rolItem.setOutcome("/XHTML/vtGestionarRoles.xhtml");
		rolItem.setIcon("fa fa-users");
		rolItem.setId("sm_VtRol");
		model.addElement(rolItem);

		DefaultMenuItem rolUsuarioItem = new DefaultMenuItem("Rol-Usuario");
		rolUsuarioItem.setOutcome("/XHTML/vtUsuarioRol.xhtml");
		rolUsuarioItem.setIcon("fa fa-users");
		rolUsuarioItem.setId("sm_VtUsuarioRol");
		model.addElement(rolUsuarioItem);

	}

	public void establecerPermisosDESARROLLADOR() {

		DefaultMenuItem dashboardItem = new DefaultMenuItem("Dashboard");
		dashboardItem.setOutcome("/XHTML/dashboard");
		dashboardItem.setIcon("icon-home-outline");
		dashboardItem.setId("sm_dashboard");
		dashboardItem.setContainerStyleClass("layout-menubar-active");
		model.addElement(dashboardItem);

	}

	public void establecerPermisosCliente() {

		DefaultMenuItem dashboardItem = new DefaultMenuItem("Dashboard");
		dashboardItem.setOutcome("/XHTML/dashboard");
		dashboardItem.setIcon("icon-home-outline");
		dashboardItem.setId("sm_dashboard");
		dashboardItem.setContainerStyleClass("layout-menubar-active");
		model.addElement(dashboardItem);

	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
}