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

	public void setBusinessDelegatorView(
			IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	private MenuModel model;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		
		model = new DefaultMenuModel();
		try {
			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			List<VtUsuarioRol> 	usuRol = ((List<VtUsuarioRol>) businessDelegatorView.consultarRolUsuarioPorUsuario(
					vtUsuarioEnSession.getUsuaCodigo().longValue()));
			
			if(usuRol.isEmpty()==false){
			VtRol rol = businessDelegatorView.getVtRol(usuRol.get(0).getVtRol().getRolCodigo());
			switch (rol.getRolNombre()) {
			case "ADMINISTRADOR":establecerPermisosADMIN();
				break;
			
			case "DESARROLLADOR":establecerPermisosDESARROLLADOR();
			break;

			}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}


	public void establecerPermisosADMIN(){
		//Empresa
		DefaultSubMenu empresaSubmenu = new DefaultSubMenu("Empresas");
		DefaultMenuItem empresaItem = new DefaultMenuItem("Empresas");

		empresaItem.setOutcome("empresaVista");
		empresaItem.setIcon("icon-building-filled");
		empresaItem.setId("sm_empresaVista");
		empresaItem.setContainerStyleClass("layout-menubar-active");

		empresaSubmenu.setId("sm_Empresas");
		empresaSubmenu.setIcon("icon-building");
		empresaSubmenu.addElement(empresaItem);
		model.addElement(empresaSubmenu);

		//Usuario
		DefaultSubMenu usuarioSubmenu = new DefaultSubMenu("Usuarios");
		DefaultMenuItem usuarioItem = new DefaultMenuItem("Usuarios");

		usuarioItem.setOutcome("usuarioVista");
		usuarioItem.setIcon("icon-user-add");
		usuarioItem.setId("sm_usuarioVista");

		usuarioSubmenu.setId("sm_Usuarios");
		usuarioSubmenu.setIcon("icon-user-1");
		usuarioSubmenu.addElement(usuarioItem);


		DefaultMenuItem rolItem = new DefaultMenuItem("Roles");
		rolItem.setOutcome("rolVista");
		rolItem.setIcon("icon-users");
		rolItem.setId("sm_rolVista");

		usuarioSubmenu.addElement(rolItem);
		model.addElement(usuarioSubmenu);

		//Pila de producto
		DefaultSubMenu pilaSubmenu = new DefaultSubMenu("Pila de producto");
		DefaultMenuItem pilaItem = new DefaultMenuItem("Gestionar Pila");

		pilaItem.setOutcome("pilaProductoVista");
		pilaItem.setIcon("icon-align-justify");
		pilaItem.setId("sm_pilaProductoVista");

		pilaSubmenu.setId("sm_Pila_Producto");
		pilaSubmenu.setIcon("icon-align-center");
		pilaSubmenu.addElement(pilaItem);
		model.addElement(pilaSubmenu);


		//Sprint
		DefaultSubMenu sprintSubmenu = new DefaultSubMenu("Sprint");
		DefaultMenuItem sprintItem = new DefaultMenuItem("Gestionar Sprint");

		sprintItem.setOutcome("sprintVista");
		sprintItem.setIcon("icon-th-list-1");
		sprintItem.setId("sm_sprintVista");

		sprintSubmenu.setId("sm_Sprint");
		sprintSubmenu.setIcon("icon-book");
		sprintSubmenu.addElement(sprintItem);
		model.addElement(sprintSubmenu);


		//Comportamiento artefacto
		DefaultSubMenu cpArtefactoSubmenu = new DefaultSubMenu("Comportamiento Artefacto");

		//Estado Artefacto
		DefaultMenuItem estadoArtefactoItem = new DefaultMenuItem("Estado Artefacto");

		estadoArtefactoItem.setOutcome("estadoVista");
		estadoArtefactoItem.setIcon("icon-vcard");
		estadoArtefactoItem.setId("sm_estadoVista");
		cpArtefactoSubmenu.addElement(estadoArtefactoItem);

		//Prioridad Artefacto
		DefaultMenuItem prioridadArtefactoItem = new DefaultMenuItem("Prioridad Artefacto");

		prioridadArtefactoItem.setOutcome("prioridadVista");
		prioridadArtefactoItem.setIcon("icon-news");
		prioridadArtefactoItem.setId("sm_prioridadVista");
		cpArtefactoSubmenu.addElement(prioridadArtefactoItem);

		//tipo Artefacto
		DefaultMenuItem tpArtefactoItem = new DefaultMenuItem("Tipo de Artefacto");

		tpArtefactoItem.setOutcome("tipoArtefactoVista");
		tpArtefactoItem.setIcon("icon-calendar-2");
		tpArtefactoItem.setId("sm_tipoArtefactoVista");
		cpArtefactoSubmenu.addElement(tpArtefactoItem);

		cpArtefactoSubmenu.setId("sm_Comportamiento_Artefacto");
		cpArtefactoSubmenu.setIcon("icon-list-alt");

		model.addElement(cpArtefactoSubmenu);

	}

	
	public void establecerPermisosDESARROLLADOR(){
		
		DefaultSubMenu empresaSubmenu = new DefaultSubMenu("Sprint");
		DefaultMenuItem empresaItem = new DefaultMenuItem("Gestionar Sprints");

		empresaItem.setOutcome("desarrolladorSprintVista");
		empresaItem.setIcon("icon-book");
		empresaItem.setId("sm_ArtefactiVista");
		empresaItem.setContainerStyleClass("layout-menubar-active");

		empresaSubmenu.setId("sm_Artefactos");
		empresaSubmenu.setIcon("icon-briefcase");
		empresaSubmenu.addElement(empresaItem);
		model.addElement(empresaSubmenu);
		
		
		DefaultSubMenu desarrolladorDatosSubmenu = new DefaultSubMenu("Cuenta");
		DefaultMenuItem desarrolladorDatosItem = new DefaultMenuItem("Datos");

		desarrolladorDatosItem.setOutcome("desarrolladorDatos");
		desarrolladorDatosItem.setIcon("icon-list-alt");
		desarrolladorDatosItem.setId("sm_DatosVista");

		desarrolladorDatosSubmenu.setId("sm_Cuenta");
		desarrolladorDatosSubmenu.setIcon("icon-user");
		desarrolladorDatosSubmenu.addElement(desarrolladorDatosItem);
		model.addElement(desarrolladorDatosSubmenu);
		

		
	}
	
	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}    
}
