package com.vobi.team.presentation.backingBeans;

import com.vobi.team.modelo.VtRol;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.*;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.jsf.FacesContextUtils;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

	@PostConstruct
	public void init() {

		model = new DefaultMenuModel();
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			List<VtUsuarioRol> usuRol = ((List<VtUsuarioRol>) businessDelegatorView
					.consultarRolUsuarioPorUsuario(vtUsuarioEnSession.getUsuaCodigo().longValue()));

			if (usuRol.isEmpty() == false) {
				VtRol rol = businessDelegatorView.getVtRol(usuRol.get(0).getVtRol().getRolCodigo());
				switch (rol.getRolNombre()) {
				case "ADMINISTRADOR":
					establecerPermisosADMIN();
					break;

				case "DESARROLLADOR":
					establecerPermisosDESARROLLADOR();
					break;

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
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

		DefaultMenuItem desarrolladorProyectoItem = new DefaultMenuItem("Desarrolladores-proyecto");
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

		DefaultMenuItem artefactosSprintItem = new DefaultMenuItem("Artefactos-sprint");
		artefactosSprintItem.setOutcome("/XHTML/vtArtefactoSprint.xhtml");
		artefactosSprintItem.setIcon("fa fa-calendar");
		artefactosSprintItem.setId("sm_VtArtefactosSprint");
		model.addElement(artefactosSprintItem);

		DefaultMenuItem artefactosItem = new DefaultMenuItem("Artefacto");
		artefactosItem.setOutcome("/XHTML/vtGestionArtefacto.xhtml");
		artefactosItem.setIcon("fa fa-files-o");
		artefactosItem.setId("sm_VtArtefacto");
		model.addElement(artefactosItem);

		DefaultMenuItem artefactosUsuarioItem = new DefaultMenuItem("Artefactos-usuario");
		artefactosUsuarioItem.setOutcome("/XHTML/vtUsuarioArtefacto.xhtml");
		artefactosUsuarioItem.setIcon("fa fa-calendar");
		artefactosUsuarioItem.setId("sm_VtArtefactosUsuario");
		model.addElement(artefactosUsuarioItem);

		DefaultMenuItem rolItem = new DefaultMenuItem("Roles");
		rolItem.setOutcome("/XHTML/vtGestionarRoles.xhtml");
		rolItem.setIcon("fa fa-users");
		rolItem.setId("sm_VtRol");
		model.addElement(rolItem);

		DefaultMenuItem rolUsuarioItem = new DefaultMenuItem("Roles por usuario");
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

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
}
