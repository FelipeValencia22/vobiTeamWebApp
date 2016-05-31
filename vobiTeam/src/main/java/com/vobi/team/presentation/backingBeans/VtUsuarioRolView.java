package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtRol;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioRol;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

@ManagedBean
@ViewScoped
public class VtUsuarioRolView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtUsuarioRolView.class);

	private DualListModel<VtRol> vtRol;

	private SelectOneMenu somUsuarios;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somEmpresas;
	
	private List<SelectItem> losUsuariosItems;
	private List<SelectItem> lasEmpresasItems;
	
	private ValueChangeEvent somEmpresasEvent;
	
	private String usuarioSeleccionado;
	
	private List<SelectItem> losUsuariosFiltro;
	private List<SelectItem> losProyectosFiltro;
	
	List<VtRol> rolesSource;
	List<VtRol> rolesTarget;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	@PostConstruct
	public void init() {
		List<VtRol> rolesSource = new ArrayList<VtRol>();
		List<VtRol> rolesTarget = new ArrayList<VtRol>();

		vtRol = new DualListModel<>(rolesSource, rolesTarget);
	}

	public DualListModel<VtRol> getVtRol() {
		return vtRol;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public void setVtRol(DualListModel<VtRol> vtRol) {
		this.vtRol = vtRol;
	}

	public SelectOneMenu getSomUsuarios() {
		return somUsuarios;
	}

	public void setSomUsuarios(SelectOneMenu somUsuarios) {
		this.somUsuarios = somUsuarios;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public List<SelectItem> getLasEmpresasItems() {
		try {
			if (lasEmpresasItems == null) {
				List<VtEmpresa> listaEmpresas = businessDelegatorView.getVtEmpresa();
				lasEmpresasItems = new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa : listaEmpresas) {
					if (vtEmpresa.getActivo().equalsIgnoreCase("S")) {
						lasEmpresasItems.add(new SelectItem(vtEmpresa.getEmprCodigo(), vtEmpresa.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return lasEmpresasItems;
	}

	public void setLasEmpresasItems(List<SelectItem> lasEmpresasItems) {
		this.lasEmpresasItems = lasEmpresasItems;
	}

	public List<SelectItem> getLosUsuariosItems() {

		try {
			VtProyecto vtProyecto = businessDelegatorView
					.getVtProyecto(Long.parseLong(somProyectos.getValue().toString().trim()));
			if (losUsuariosItems == null) {
				List<VtUsuario> listaUsuarios = businessDelegatorView.obtenerUsuariosAsignados(vtProyecto);
				losUsuariosItems = new ArrayList<SelectItem>();

				for (VtUsuario vtUsuario : listaUsuarios) {
					losUsuariosItems.add(new SelectItem(vtUsuario.getUsuaCodigo(), vtUsuario.getLogin()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losUsuariosItems;
	}

	public String filtrarProyectoUsuario() {
		try {
			VtProyecto vtProyecto = null;
			losUsuariosFiltro = null;
			String proyectoS = somProyectos.getValue().toString().trim();

			if (proyectoS.isEmpty() || proyectoS.equals("-1")) {
			} else {
				vtProyecto = businessDelegatorView
						.getVtProyecto(Long.parseLong(somProyectos.getValue().toString().trim()));
			}

			try {

				if (losUsuariosFiltro == null) {
					List<VtUsuario> listaUsuarios = businessDelegatorView.obtenerUsuariosAsignados(vtProyecto);
					losUsuariosFiltro = new ArrayList<SelectItem>();

					for (VtUsuario vtUsuario : listaUsuarios) {
						if (vtUsuario.getActivo().equals("S")) {
							losUsuariosFiltro.add(new SelectItem(vtUsuario.getUsuaCodigo(), vtUsuario.getLogin()));
						}

					}
				}

			} catch (Exception e) {
				log.error(e.getMessage());
			}

		} catch (Exception e) {
			log.error(e.getMessage());

		}
		return "";
	}



	public void asignarRolAUsuario(VtUsuario vtUsuario, VtRol vtRol) {

		try {

			VtUsuarioRol vtUsuarioRol = businessDelegatorView
					.consultarRolUsuarioPorUsuarioYPorRol(vtUsuario.getUsuaCodigo(), vtRol.getRolCodigo());
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");

			if (vtUsuarioRol == null) {
				VtUsuarioRol usuarioRol = new VtUsuarioRol();
				usuarioRol.setActivo("S");
				usuarioRol.setFechaCreacion(new Date());
				usuarioRol.setFechaModificacion(new Date());
				usuarioRol.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());
				usuarioRol.setVtRol(vtRol);
				usuarioRol.setVtUsuario(vtUsuario);
				businessDelegatorView.saveVtUsuarioRol(usuarioRol);
			} else {
				vtUsuarioRol.setActivo("S");
				vtUsuarioRol.setFechaModificacion(new Date());
				vtUsuarioRol.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				businessDelegatorView.updateVtUsuarioRol(vtUsuarioRol);
			}
		} catch (Exception e) {

			log.error(e.getMessage());
		}
	}

	public void removerRol(VtUsuario vtUsuario, VtRol vtRol) {

		try {
			VtUsuarioRol vtUsuarioRol = businessDelegatorView
					.consultarRolUsuarioPorUsuarioYPorRol(vtUsuario.getUsuaCodigo(), vtRol.getRolCodigo());
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			vtUsuarioRol.setActivo("N");
			vtUsuarioRol.setFechaModificacion(new Date());
			vtUsuarioRol.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			businessDelegatorView.updateVtUsuarioRol(vtUsuarioRol);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void localeChanged() throws Exception {
		setUsuarioSeleccionado(somUsuarios.getValue().toString().trim());
		actualizarListaRoles();
	}

	public void actualizarListaRoles() throws Exception {

		try {
			Long idUsuario = Long.parseLong(somUsuarios.getValue().toString().trim());
			VtUsuario vtUsuario = businessDelegatorView.getVtUsuario(idUsuario);

			log.info("Codigo del usuario " + vtUsuario.getUsuaCodigo());
			rolesSource = businessDelegatorView.obtenerRolesNoAsignados(vtUsuario);
			rolesTarget = businessDelegatorView.obtenerRolesAsignados(vtUsuario);
			vtRol.setSource(rolesSource);
			vtRol.setTarget(rolesTarget);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void onTransfer(TransferEvent event) throws Exception {

		try {
			StringBuilder builder = new StringBuilder();
			Long idUsuario = Long.parseLong(somUsuarios.getValue().toString().trim());
			VtUsuario vtUsuario = businessDelegatorView.getVtUsuario(idUsuario);

			for (Object item : event.getItems()) {
				VtRol vtRol = (VtRol) item;

				builder.append(((VtRol) item).getRolNombre()).append("<br />");
				if (event.isAdd()) {
					asignarRolAUsuario(vtUsuario, vtRol);
				}
				if (event.isRemove()) {
					removerRol(vtUsuario, vtRol);
				}
			}
			FacesUtils.addInfoMessage("Roles(s) Asignado(s)");
		} catch (Exception e) {
			FacesUtils.addErrorMessage("No se pudo realizar la transferencia");
		}

	}
	public void setLosUsuariosItems(List<SelectItem> losUsuariosItems) {
		this.losUsuariosItems = losUsuariosItems;
	}

	public ValueChangeEvent getSomEmpresasEvent() {
		return somEmpresasEvent;
	}

	public void setSomEmpresasEvent(ValueChangeEvent somEmpresasEvent) {
		this.somEmpresasEvent = somEmpresasEvent;
	}

	public String getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(String usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public List<VtRol> getRolesSource() {
		return rolesSource;
	}

	public void setRolesSource(List<VtRol> rolesSource) {
		this.rolesSource = rolesSource;
	}

	public List<VtRol> getRolesTarget() {
		return rolesTarget;
	}

	public void setRolesTarget(List<VtRol> rolesTarget) {
		this.rolesTarget = rolesTarget;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}
	public List<SelectItem> getLosUsuariosFiltro() {
		return losUsuariosFiltro;
	}

	public void setLosUsuariosFiltro(List<SelectItem> losUsuariosFiltro) {
		this.losUsuariosFiltro = losUsuariosFiltro;
	}

	public List<SelectItem> getLosProyectosFiltro() {
		return losProyectosFiltro;
	}

	public void setLosProyectosFiltro(List<SelectItem> losProyectosFiltro) {
		this.losProyectosFiltro = losProyectosFiltro;
	}
	
	public String filtrarEmpresa() {
		try {
			VtEmpresa vtEmpresa = null;
			losProyectosFiltro = null;
			String empresaS = somEmpresas.getValue().toString().trim();
			if (empresaS.isEmpty() || empresaS.equals("-1")) {
			} else {
				Long empresa = Long.parseLong(empresaS);
				vtEmpresa = businessDelegatorView.getVtEmpresa(empresa);
			}
			try {
				if (losProyectosFiltro == null) {
					List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
					losProyectosFiltro = new ArrayList<SelectItem>();
					for (VtProyecto vtProyecto : listaProyectos) {
						if (vtProyecto.getActivo().equalsIgnoreCase("S")
								&& vtProyecto.getVtEmpresa().getEmprCodigo().equals(vtEmpresa.getEmprCodigo())) {
							losProyectosFiltro.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

}
