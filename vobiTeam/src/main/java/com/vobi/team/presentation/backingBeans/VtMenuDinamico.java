package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.presentation.businessDelegate.BusinessDelegatorView;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

@ManagedBean 
@ViewScoped
public class VtMenuDinamico implements Serializable{
	private static final long serialVersionUID = 1L;

    private MenuModel model;
	private static final Logger log = LoggerFactory.getLogger(VtMenuDinamico.class);
	
	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	
	@PostConstruct
	public void init(){	
		
		try {
			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			VtUsuarioRol vtUsuarioRol   = businessDelegatorView.consultarRolUsuarioPorUsuario(
					vtUsuarioEnSession.getUsuaCodigo().longValue());
			
			if(vtUsuarioRol.getVtRol().getRolNombre().toUpperCase().equals("DESARROLLADOR")){
				DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
			    DefaultMenuItem item = new DefaultMenuItem("Empresa");
		        item.setUrl("/XHTML/vtGestionEmpresa.xhtml");
		        item.setIcon("icon-home-outline");
		        item.setContainerStyle("layout-menubar-active");
		        firstSubmenu.addElement(item);
			}
			
			
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
	    DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("icon-home-outline");
        firstSubmenu.addElement(item);
        
        model.addElement(firstSubmenu);
	}
	
}
