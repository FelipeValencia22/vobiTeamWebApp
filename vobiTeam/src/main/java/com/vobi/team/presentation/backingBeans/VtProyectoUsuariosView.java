package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

@ManagedBean
@ViewScoped
public class VtProyectoUsuariosView implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtProyectoUsuariosView.class);

	private DualListModel<VtUsuario> vtUsuario;

	private SelectOneMenu somProyectos;
	private List<SelectItem> losProyectosItems;
	private CommandButton btnCrear;
	private ValueChangeEvent somEmpresasEvent;
	private String proyectoSeleccionado;

	List<VtUsuario> usuariosSource;

	List<VtUsuario> usuariosTarget;

	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public DualListModel<VtUsuario> getVtUsuario() {
		return vtUsuario;
	}

	public void setVtUsuario(DualListModel<VtUsuario> vtUsuario) {
		this.vtUsuario = vtUsuario;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public ValueChangeEvent getSomEmpresasEvent() {
		return somEmpresasEvent;
	}

	public void setSomEmpresasEvent(ValueChangeEvent somEmpresasEvent) {
		this.somEmpresasEvent = somEmpresasEvent;
	}

	public String getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(String proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	@PostConstruct
	public void init() {
		List<VtUsuario> usuariosSource = new ArrayList<VtUsuario>();
		List<VtUsuario> usuariosTarget = new ArrayList<VtUsuario>();

		vtUsuario = new DualListModel<>(usuariosSource, usuariosTarget);
	}

	public void asignarUsuarioAProyecto(VtUsuario vtUsuario, VtProyecto vtProyecto) {

		try {
			VtProyectoUsuario vtProyectoUsuario = businessDelegatorView.consultarProyectoUsuarioPorProyectoYPorUsuario(
					vtProyecto.getProyCodigo(), vtUsuario.getUsuaCodigo());
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			if (vtProyectoUsuario == null) {
				VtProyectoUsuario proyectoUsuario = new VtProyectoUsuario();
				proyectoUsuario.setActivo("S");
				proyectoUsuario.setFechaCreacion(new Date());
				proyectoUsuario.setFechaModificacion(new Date());
				proyectoUsuario.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());
				proyectoUsuario.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				proyectoUsuario.setVtProyecto(vtProyecto);
				proyectoUsuario.setVtUsuario(vtUsuario);
				businessDelegatorView.saveVtProyectoUsuario(proyectoUsuario);
			} else {
				vtProyectoUsuario.setActivo("S");
				vtProyectoUsuario.setFechaModificacion(new Date());
				vtProyectoUsuario.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				businessDelegatorView.updateVtProyectoUsuario(vtProyectoUsuario);

			}
		} catch (Exception e) {

			log.error(e.getMessage());
		}
	}

	public void removerProyecto(VtUsuario vtUsuario, VtProyecto vtProyecto) {

		try {
			VtProyectoUsuario vtProyectoUsuario = businessDelegatorView.consultarProyectoUsuarioPorProyectoYPorUsuario(
					vtProyecto.getProyCodigo(), vtUsuario.getUsuaCodigo());
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			vtProyectoUsuario.setActivo("N");
			vtProyectoUsuario.setFechaModificacion(new Date());
			vtProyectoUsuario.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			businessDelegatorView.updateVtProyectoUsuario(vtProyectoUsuario);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void actualizarListaUsuarios() throws Exception {

		try {
			Long idProyecto = Long.parseLong(somProyectos.getValue().toString().trim());
			VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(idProyecto);

			log.info("Codigo del proyecto" + vtProyecto.getProyCodigo());
			usuariosSource = businessDelegatorView.obtenerUsuariosNoAsignados(vtProyecto);
			usuariosTarget = businessDelegatorView.obtenerUsuariosAsignados(vtProyecto);
			vtUsuario.setSource(usuariosSource);
			vtUsuario.setTarget(usuariosTarget);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void onTransfer(TransferEvent event) throws Exception {

		try {
			StringBuilder builder = new StringBuilder();
			Long idProyecto = Long.parseLong(somProyectos.getValue().toString().trim());
			VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(idProyecto);

			for (Object item : event.getItems()) {
				VtUsuario vtUsuario = (VtUsuario) item;

				builder.append(((VtUsuario) item).getNombre()).append("<br />");
				if (event.isAdd()) {
					asignarUsuarioAProyecto(vtUsuario, vtProyecto);
				}
				if (event.isRemove()) {
					removerProyecto(vtUsuario, vtProyecto);
				}
			}
			FacesUtils.addInfoMessage("Usuario(s) Asignado(s)");
		} catch (Exception e) {
			FacesUtils.addErrorMessage("No se pudo realizar la transferencia");
		}

	}

	public List<VtUsuario> getUsuariosSource() {
		return usuariosSource;
	}

	public void setUsuariosSource(List<VtUsuario> usuariosSource) {
		this.usuariosSource = usuariosSource;
	}

	public List<VtUsuario> getUsuariosTarget() {
		return usuariosTarget;
	}

	public void setUsuariosTarget(List<VtUsuario> usuariosTarget) {
		this.usuariosTarget = usuariosTarget;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public List<SelectItem> getLosProyectosItems() {
		try {
			if (losProyectosItems == null) {
				List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
				losProyectosItems = new ArrayList<SelectItem>();
				for (VtProyecto vtProyecto : listaProyectos) {
					losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losProyectosItems;
	}

	public void setLosProyectosItems(List<SelectItem> losProyectosItems) {
		this.losProyectosItems = losProyectosItems;
	}

	public void localeChanged() throws Exception {
		actualizarListaUsuarios();
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public String action_closeDialog() {
		setShowDialog(false);
		somProyectos.setValue("-1");
		return "";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	public String action_Guardar() {

		// Source
		Object[] objectUsuariosSource = usuariosSource.toArray();
		String[] stringUsuariosSource = new String[objectUsuariosSource.length];

		for (int i = 0; i < objectUsuariosSource.length; i++) {
			stringUsuariosSource[i] = "" + objectUsuariosSource[i];
		}

		String[] loginSource = new String[stringUsuariosSource.length];

		for (int i = 0; i < stringUsuariosSource.length; i++) {
			String[] parts = stringUsuariosSource[i].split("Login:");
			loginSource[i] = parts[1];
		}

		// Target
		Object[] objectUsuariosTarget = usuariosTarget.toArray();
		String[] stringUsuariosTarget = new String[objectUsuariosTarget.length];

		for (int i = 0; i < objectUsuariosTarget.length; i++) {
			stringUsuariosTarget[i] = "" + objectUsuariosTarget[i];
		}

		String[] loginTarget = new String[stringUsuariosTarget.length];

		for (int i = 0; i < stringUsuariosTarget.length; i++) {
			String[] parts = stringUsuariosTarget[i].split("Login:");
			loginTarget[i] = parts[1];
		}

		try {
			VtProyecto vtProyecto = null;
			List<VtProyectoUsuario> listaProyectoUsuarios = businessDelegatorView.getVtProyectoUsuario();
			for (VtProyectoUsuario vtProyectoUsuario : listaProyectoUsuarios) {
				if (vtProyectoUsuario.getVtProyecto().getNombre().equals(getProyectoSeleccionado())) {
					vtProyecto = businessDelegatorView.getVtProyecto(vtProyectoUsuario.getVtProyecto().getProyCodigo());

					for (int i = 0; i < loginSource.length; i++) {
						System.out.println("#" + i + ": " + loginSource[i]);
					}

					for (int i = 0; i < loginSource.length; i++) {
						if (vtProyectoUsuario.getVtUsuario().getLogin().equals(loginSource[i].toString())) {
						} else {
							VtProyectoUsuario vtProyectoUsuarioCrear = new VtProyectoUsuario();
							vtProyectoUsuarioCrear.setVtProyecto(vtProyecto);

							Date fechaCreacion = new Date();
							vtProyectoUsuarioCrear.setFechaCreacion(fechaCreacion);

							VtUsuario vtUsuarioCreador = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
							vtProyectoUsuarioCrear.setUsuCreador(vtUsuarioCreador.getUsuaCodigo());

							VtUsuario vtUsuarioBuscar = businessDelegatorView.findUsuarioByLogin(loginSource[i]);
							vtProyectoUsuarioCrear.setVtUsuario(vtUsuarioBuscar);

							vtProyectoUsuarioCrear.setActivo("S");

							businessDelegatorView.saveVtProyectoUsuario(vtProyectoUsuarioCrear);
							FacesContext.getCurrentInstance().addMessage("",
									new FacesMessage("Se realizo la asignacion del desarrollador(es)"));
						}
					}

				}

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

}
