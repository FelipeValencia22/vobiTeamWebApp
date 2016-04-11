package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtRol;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;

import com.vobi.team.utilities.FacesUtils;
import com.vobi.team.modelo.dto.VtEmpresaDTO;
import com.vobi.team.modelo.dto.VtRolDTO;
import com.vobi.team.modelo.dto.VtUsuarioDTO;

@ManagedBean
@ViewScoped
public class VtRolView implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtRolView.class);

	private InputText txtNombre;
	private InputText txtNombreCambio;
	private SelectOneMenu somActivo;
	private SelectOneMenu somActivoCambio;

	private List<SelectItem> esActivoItems;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	private CommandButton btnCrearU;
	private CommandButton btnCrear;
	private CommandButton btnGuardar;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	private List<VtRolDTO> data;
	private List<VtRolDTO> dataI;
	private VtRolDTO selectedVtRol;

	private CommandButton btnSave;

	private VtRol entity;

	private VtUsuarioDTO selectedVtUsuario;

	private boolean showDialog;

	private InputText txtUsuaCodigo;

	public List<VtRolDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtRol();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtRolDTO> data) {
		this.data = data;
	}

	public List<VtRolDTO> getDataI() {
		try {
			if (dataI == null) {
				dataI = businessDelegatorView.getDataVtRolInactivo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataI;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public VtRolDTO getSelectedVtRol() {
		return selectedVtRol;
	}

	public void setSelectedVtRol(VtRolDTO selectedVtRol) {
		this.selectedVtRol = selectedVtRol;
	}

	public void setDataI(List<VtRolDTO> dataI) {
		this.dataI = dataI;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public VtRolView() {
		super();
	}

	public CommandButton getBtnCrearU() {
		return btnCrearU;
	}

	public void setBtnCrearU(CommandButton btnCrearU) {
		this.btnCrearU = btnCrearU;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}
	public CommandButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(CommandButton btnSave) {
		this.btnSave = btnSave;
	}

	public VtRol getEntity() {
		return entity;
	}

	public void setEntity(VtRol entity) {
		this.entity = entity;
	}

	public InputText getTxtUsuaCodigo() {
		return txtUsuaCodigo;
	}

	public void setTxtUsuaCodigo(InputText txtUsuaCodigo) {
		this.txtUsuaCodigo = txtUsuaCodigo;
	}

	public VtUsuarioDTO getSelectedVtUsuario() {
		return selectedVtUsuario;
	}

	public void setSelectedVtUsuario(VtUsuarioDTO selectedVtUsuario) {
		this.selectedVtUsuario = selectedVtUsuario;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}


	public List<SelectItem> getEsActivoItems() {
		if (esActivoItems == null) {
			esActivoItems = new ArrayList<SelectItem>();
			esActivoItems.add(new SelectItem("Si"));
			esActivoItems.add(new SelectItem("No"));

		}
		return esActivoItems;
	}

	public void setEsActivoItems(List<SelectItem> esActivoItems) {
		this.esActivoItems = esActivoItems;
	}
	

	public InputText getTxtNombreCambio() {
		return txtNombreCambio;
	}

	public void setTxtNombreCambio(InputText txtNombreCambio) {
		this.txtNombreCambio = txtNombreCambio;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}


	public String crearRol() throws Exception {

		try {
			VtRol vtRol = new VtRol();
			vtRol.setRolNombre(txtNombre.getValue().toString().trim());
			Date fechaCreacion = new Date();
			vtRol.setFechaCreacion(fechaCreacion);
			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			vtRol.setUsuCreador(vtUsuario.getUsuaCodigo());
			String activo = somActivo.getValue().toString().trim();

			if (activo.equalsIgnoreCase("Si")) {
				vtRol.setActivo("S");
			} else {
				if (activo.equals("-1")) {
					vtRol.setActivo("S");
				} else {
					vtRol.setActivo("N");
				}
			}

			businessDelegatorView.saveVtRol(vtRol);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El rol se creó con exito"));
			data = businessDelegatorView.getDataVtRol();
			dataI = businessDelegatorView.getDataVtRolInactivo();
			limpiar();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}


	public String limpiar() {
		log.info("Limpiando campos de texto");
		txtNombre.resetValue();
		somActivo.setValue("-1");
		btnCrear.setDisabled(false);
		return "";
	}

	public String action_clear() {
		txtNombre.resetValue();
		somActivo.setValue("-1");
		return "";
	}

	public String modificar(ActionEvent evt) {
		selectedVtRol = (VtRolDTO) (evt.getComponent().getAttributes().get("selectedVtRol"));
		txtNombre.setValue(selectedVtRol.getRolNombre());
		txtNombre.setDisabled(false);
		somActivo.setValue(selectedVtRol.getActivo());
		somActivo.setDisabled(false);
		btnGuardar.setDisabled(false);
		setShowDialog(true);
		return "";
	}

	public String action_modify() {
		try {
			if (entity == null) {
				Long rolCodigo = new Long(selectedVtRol.getRolCodigo());
				entity = businessDelegatorView.getVtRol(rolCodigo);
			}

			String activo = somActivoCambio.getValue().toString().trim();
			if (activo.equalsIgnoreCase("Si")) {
				entity.setActivo("S");
			} else {
				if (activo.equals("-1")) {
					entity.setActivo(entity.getActivo());
				} else {
					entity.setActivo("N");
				}

			}
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			entity.setRolNombre(FacesUtils.checkString(txtNombreCambio));			
			log.info("Va a mostrar el mensaje de modificado con exito");
		
			businessDelegatorView.updateVtRol(entity);	
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El rol se modificó con éxito"));
			data = businessDelegatorView.getDataVtRol();
			dataI = businessDelegatorView.getDataVtRolInactivo();		
			selectedVtRol=null;
			entity=null;
		} catch (Exception e) {
			data = null;
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String cambiarEstado(ActionEvent evt) {
		log.info("Cambiando estado..");
		selectedVtRol = (VtRolDTO) (evt.getComponent().getAttributes().get("selectedVtRol"));
		try {

			if (entity == null) {
				Long rolCodigo = new Long(selectedVtRol.getRolCodigo());
				entity = businessDelegatorView.getVtRol(rolCodigo);
			}

			String cambioActivo = entity.getActivo().toString().trim();
			if (cambioActivo.equalsIgnoreCase("S")) {
				entity.setActivo("N");
			} else {
				entity.setActivo("S");
			}

			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			businessDelegatorView.updateVtRol(entity);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Se ha cambiado el estado del rol con éxito"));
			data = businessDelegatorView.getDataVtRol();
			dataI = businessDelegatorView.getDataVtRolInactivo();

			selectedVtRol=null;
			entity=null;
		} catch (Exception e) {
			data = null;
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}
	

	public String action_save() {
		try {
			if ((selectedVtRol == null) && (entity == null)) {

			} else {
				action_modify();
			}
			log.info("Modifcando el archivo y grabandolo");
			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		action_clear();
		return "";
	}


}
