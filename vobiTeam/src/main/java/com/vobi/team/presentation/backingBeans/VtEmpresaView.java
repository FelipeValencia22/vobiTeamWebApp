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
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtEmpresaDTO;

@ManagedBean
@ViewScoped
public class VtEmpresaView implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtEmpresaView.class);

	private InputTextarea txtId;
	private InputTextarea txtNombre;
	private InputTextarea txtIDCrear;
	private InputTextarea txtNombreCrear; 

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	private CommandButton btnGuardar;
	private SelectOneMenu somActivo;
	private List<SelectItem> esActivoItems;
	private List<VtEmpresaDTO> data;
	private List<VtEmpresaDTO> dataI;
	private VtEmpresaDTO selectedVtEmpresa;
	private VtEmpresaDTO selectedVtEmpresaI;
	private VtEmpresa entity;
	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public VtEmpresaView() {
		super();
	}

	public InputTextarea getTxtId() {
		return txtId;
	}

	public void setTxtId(InputTextarea txtId) {
		this.txtId = txtId;
	}

	public InputTextarea getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputTextarea txtNombre) {
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

	public List<VtEmpresaDTO> getDataI() {
		try {
			if (dataI == null) {
				dataI = businessDelegatorView.getDataVtEmpresaInactiva();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataI;
	}

	public void setDataI(List<VtEmpresaDTO> vtEmpresaDTO) {
		this.dataI = vtEmpresaDTO;
	}

	public List<VtEmpresaDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtEmpresa();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtEmpresaDTO> vtEmpresaDTO) {
		this.data = vtEmpresaDTO;
	}

	public VtEmpresaDTO getSelectedVtEmpresa() {
		return selectedVtEmpresa;
	}


	public void setSelectedVtEmpresa(VtEmpresaDTO vtEmpresa) {
		this.selectedVtEmpresa = vtEmpresa;
	}	

	public VtEmpresaDTO getSelectedVtEmpresaI() {
		return selectedVtEmpresaI;
	}

	public void setSelectedVtEmpresaI(VtEmpresaDTO selectedVtEmpresaI) {
		this.selectedVtEmpresaI = selectedVtEmpresaI;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public InputTextarea getTxtIDCrear() {
		return txtIDCrear;
	}

	public void setTxtIDCrear(InputTextarea txtIdCrear) {
		this.txtIDCrear = txtIdCrear;
	}

	public InputTextarea getTxtNombreCrear() {
		return txtNombreCrear;
	}

	public void setTxtNombreCrear(InputTextarea txtNombreCrear) {
		this.txtNombreCrear = txtNombreCrear;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public List<SelectItem> getEsActivoItems() {
		if(esActivoItems==null){
			esActivoItems=new ArrayList<SelectItem>();
			esActivoItems.add(new SelectItem("Si"));
			esActivoItems.add(new SelectItem("No"));

		}
		return esActivoItems;
	}

	public void setEsActivoItems(List<SelectItem> esActivoItems) {
		this.esActivoItems = esActivoItems;
	}

	public String crearEmpresa(){
		log.info("Creando empresa");

		VtEmpresa vtEmpresa= new VtEmpresa();
		vtEmpresa.setNombre(txtNombreCrear.getValue().toString().trim());
		vtEmpresa.setIdentificacion(txtIDCrear.getValue().toString().trim());
		vtEmpresa.setActivo("S");
		Date fechaCreacion = new Date();
		vtEmpresa.setFechaCreacion(fechaCreacion);
		VtUsuario vtUsuario =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
		vtEmpresa.setUsuCreador(vtUsuario.getUsuaCodigo());

		try {
			businessDelegatorView.saveVtEmpresa(vtEmpresa);
			limpiar();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La empresa se creó con exito"));
			data = businessDelegatorView.getDataVtEmpresa();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}
		return "";
	}

	public String modificarEmpresa(){
		log.info("Modificando empresa");

		VtEmpresa vtEmpresa = null;
		String identificacion= txtId.getValue().toString().trim();
		vtEmpresa=businessDelegatorView.consultarEmpresaPorId(identificacion);

		vtEmpresa.setIdentificacion(identificacion);
		vtEmpresa.setNombre(txtNombre.getValue().toString().trim());

		try {
			businessDelegatorView.updateVtEmpresa(vtEmpresa);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La empresa se modifico con exito"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String limpiar(){
		log.info("Limpiando campos de texto");
		txtNombreCrear.resetValue();
		txtIDCrear.resetValue();

		btnCrear.setDisabled(true);
		return "";
	}

	public void txtIdListener(){
		log.info("Se ejecuto el listener");

		VtEmpresa vtEmpresa= null;

		String codigo= txtIDCrear.getValue().toString().trim();
		vtEmpresa=businessDelegatorView.consultarEmpresaPorId(codigo);


		if(vtEmpresa==null){
			txtNombreCrear.resetValue();

			btnCrear.setDisabled(false);
		}else{

			txtNombreCrear.setValue(vtEmpresa.getNombre());

			btnCrear.setDisabled(true);
		}
	}

	public String action_edit(ActionEvent evt) {
		selectedVtEmpresa = (VtEmpresaDTO) (evt.getComponent().getAttributes()
				.get("selectedVtEmpresa"));

		txtNombre.setValue(selectedVtEmpresa.getNombre());
		txtNombre.setDisabled(false);
		btnGuardar.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public String action_save() {
		try {
			if ((selectedVtEmpresa == null) && (entity == null)) {

			} else {
				action_modify();
			}

			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modify() {
		try {
			if (entity == null) {
				Long emprCodigo = new Long(selectedVtEmpresa.getEmprCodigo());
				entity = businessDelegatorView.getVtEmpresa(emprCodigo);
			}

			String activo = somActivo.getValue().toString().trim();
			if (activo.equalsIgnoreCase("Si")) {
				entity.setActivo("S");
			} else {
				if(activo.equals("-1")){
					entity.setActivo(entity.getActivo());
				}
				else{
					entity.setActivo("N");
				}

			}

			Date fechaModificacion= new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			entity.setNombre(FacesUtils.checkString(txtNombre));

			businessDelegatorView.updateVtEmpresa(entity);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La empresa se modificó con exito"));
			data = businessDelegatorView.getDataVtEmpresa();
			dataI = businessDelegatorView.getDataVtEmpresaInactiva();
			selectedVtEmpresa=null;
			entity=null;
		} catch (Exception e) {
			data = null;
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String action_closeDialog() {
		VtEmpresaDTO vtEmpresaC = null;
		setSelectedVtEmpresa(vtEmpresaC);
		setShowDialog(false);
		return "";
	}

	public String cambiarEstado(ActionEvent evt){
		log.info("Cambiando estado..");
		selectedVtEmpresa = (VtEmpresaDTO) (evt.getComponent().getAttributes()
				.get("selectedVtEmpresa"));
		try {
			
			if (entity == null) {
				Long emprCodigo = new Long(selectedVtEmpresa.getEmprCodigo());
				entity = businessDelegatorView.getVtEmpresa(emprCodigo);
			} 
			
			String cambioActivo=entity.getActivo().toString().trim();
			if (cambioActivo.equalsIgnoreCase("S")) {
				entity.setActivo("N");
			}else{
				entity.setActivo("S");
			}			
			
			Date fechaModificacion= new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			
			businessDelegatorView.updateVtEmpresa(entity);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La empresa se modificó con exito"));
			data = businessDelegatorView.getDataVtEmpresa();
			dataI = businessDelegatorView.getDataVtEmpresaInactiva();
			selectedVtEmpresa=null;
			entity=null;
			
		}catch (Exception e) {
			data = null;
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}


		return "";
	}

}
