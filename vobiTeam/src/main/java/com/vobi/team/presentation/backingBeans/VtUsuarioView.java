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
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;

import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtUsuarioDTO;

@ManagedBean
@ViewScoped
public class VtUsuarioView implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtUsuarioView.class);

	private InputText txtLogin;
	private InputText txtNombre;
	private InputText txtLoginC;
	private InputText txtNombreC;
	private Password txtClave;
	private Password txtClaveR;
	private Password txtClaveC;
	private Password txtClaveRC;
	private SelectOneMenu somEmpresas;
	private SelectOneMenu somEmpresasCambio;
	private SelectOneMenu somActivo;

	private CommandButton btnCrearU;
	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<SelectItem> lasEmpresasItems;
	private List<SelectItem> esActivoItems;
	private List<SelectItem> esUsuarioActivo;
	private List<VtUsuarioDTO> data;
	private List<VtUsuarioDTO> dataI;


	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public List<SelectItem> getEsUsuarioActivo() {
		return esUsuarioActivo;
	}

	public void setEsUsuarioActivo(List<SelectItem> esUsuarioActivo) {
		this.esUsuarioActivo = esUsuarioActivo;
	}

	public VtUsuarioView() {
		super();
	}

	public CommandButton getBtnCrearU() {
		return btnCrearU;
	}

	public void setBtnCrearU(CommandButton btnCrearU) {
		this.btnCrearU = btnCrearU;
	}

	public InputText getTxtLoginC() {
		return txtLoginC;
	}

	public Password getTxtClaveC() {
		return txtClaveC;
	}

	public void setTxtClaveC(Password txtClaveC) {
		this.txtClaveC = txtClaveC;
	}

	public Password getTxtClaveRC() {
		return txtClaveRC;
	}

	public void setTxtClaveRC(Password txtClaveRC) {
		this.txtClaveRC = txtClaveRC;
	}

	public void setTxtLoginC(InputText txtLoginC) {
		this.txtLoginC = txtLoginC;
	}

	public InputText getTxtNombreC() {
		return txtNombreC;
	}

	public void setTxtNombreC(InputText txtNombreC) {
		this.txtNombreC = txtNombreC;
	}

	public InputText getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public Password getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}

	public Password getTxtClaveR() {
		return txtClaveR;
	}

	public void setTxtClaveR(Password txtClaveR) {
		this.txtClaveR = txtClaveR;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public SelectOneMenu getSomEmpresasCambio() {
		return somEmpresasCambio;
	}

	public void setSomEmpresasCambio(SelectOneMenu somEmpresasCambio) {
		this.somEmpresasCambio = somEmpresasCambio;
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

	public List<SelectItem> getLasEmpresasItems() {
		try {
			if(lasEmpresasItems==null){
				List<VtEmpresa> listaEmpresas=businessDelegatorView.getVtEmpresa();
				lasEmpresasItems=new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa: listaEmpresas) {
					lasEmpresasItems.add(new SelectItem(vtEmpresa.getEmprCodigo(), vtEmpresa.getNombre()));
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

	public boolean loginDisponible(String login){
		Boolean resultado;

		VtUsuario vtUsuario=null;
		vtUsuario=businessDelegatorView.consultarLogin(login);
		if(vtUsuario==null){
			resultado=true;
		}else{
			resultado=false;
		}

		return resultado;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
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

	public String crearUsuario() throws Exception{
		log.info("Creando usuario");
		String claveR=txtClaveRC.getValue().toString().trim();
		String clave=txtClaveC.getValue().toString().trim(); 

		if(clave.equals(claveR)){
			String Login=txtLoginC.getValue().toString().trim();
			if(loginDisponible(Login)){
				VtUsuario vtUsuario = new VtUsuario();
				vtUsuario.setActivo("S");
				vtUsuario.setClave(clave);
				Date fechaCreacion= new Date();
				vtUsuario.setFechaCreacion(fechaCreacion);
				vtUsuario.setLogin(Login);
				vtUsuario.setNombre(txtNombreC.getValue().toString().trim());

				VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
				vtUsuario.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());

				String empresaS=somEmpresas.getValue().toString().trim();
				if(empresaS.isEmpty() || empresaS.equals("-1")){
				}else{
					Long empresa=Long.parseLong(empresaS);
					VtEmpresa vtEmpresa=businessDelegatorView.getVtEmpresa(empresa);
					vtUsuario.setVtEmpresa(vtEmpresa);
				}

				try {
					businessDelegatorView.saveVtUsuario(vtUsuario);
					limpiar();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El usuario se guardo con exito"));
					data = businessDelegatorView.getDataVtUsuario();
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
				}

			}else{
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El correo ya est&#225; siendo usado"));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Las contraseñas no coinciden"));
		}

		return "";
	}

	public String limpiar(){
		log.info("Limpiando campos de texto");
		txtLoginC.resetValue();
		txtNombreC.resetValue();
		txtClave.resetValue();
		txtClaveR.resetValue();
		somEmpresas.setValue("-1");

		btnCrear.setDisabled(true);
		return "";
	}

	public void txtLoginListener(){
		log.info("Se ejecuto el listener");
		
		VtUsuario vtUsuario= null;

		String login= txtLoginC.getValue().toString().trim();
		log.info(login);
		vtUsuario=businessDelegatorView.consultarLogin(login);
		
		if(vtUsuario==null){
			txtClave.resetValue();
			txtClaveR.resetValue();
			txtNombreC.resetValue();
			btnCrearU.setDisabled(false);
		}

		else{
			txtNombreC.setValue(vtUsuario.getNombre());
			somEmpresas.setValue(vtUsuario.getVtEmpresa().getEmprCodigo());

			btnCrearU.setDisabled(true);
		}

	}


	/// Data Table
	private CommandButton btnSave;

	private VtUsuario entity;

	private VtUsuarioDTO selectedVtUsuario;

	private boolean showDialog;

	private InputText txtUsuaCodigo;

	public CommandButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(CommandButton btnSave) {
		this.btnSave = btnSave;
	}

	public VtUsuario getEntity() {
		return entity;
	}

	public void setEntity(VtUsuario entity) {
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

	public List<VtUsuarioDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtUsuario();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setDataI(List<VtUsuarioDTO> dataI) {
		this.dataI = dataI;
	}

	public List<VtUsuarioDTO> getDataI() {
		try {
			if (dataI == null) {
				dataI = businessDelegatorView.getDataVtUsuarioInactivo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataI;
	}

	public void setData(List<VtUsuarioDTO> data) {
		this.data = data;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public String action_edit(ActionEvent evt) {
		selectedVtUsuario = (VtUsuarioDTO) (evt.getComponent().getAttributes()
				.get("selectedVtUsuario"));	

		txtClave.setValue(selectedVtUsuario.getClave());
		txtClave.setDisabled(false);
		txtLogin.setValue(selectedVtUsuario.getLogin());
		txtLogin.setDisabled(false);
		txtNombre.setValue(selectedVtUsuario.getNombre());
		txtNombre.setDisabled(false);
		setShowDialog(true);


		return "";
	}

	public void listener_txtId() {

		try {
			Long usuaCodigo = FacesUtils.checkLong(txtUsuaCodigo);
			entity = (usuaCodigo != null)
					? businessDelegatorView.getVtUsuario(usuaCodigo) : null;
		} catch (Exception e) {
			entity = null;
		}

		if (entity == null) {
			txtClave.setDisabled(false);
			txtLogin.setDisabled(false);
			txtNombre.setDisabled(false);
			txtUsuaCodigo.setDisabled(false);
			btnSave.setDisabled(false);
		} else {
			txtClave.setValue(entity.getClave());
			txtClave.setDisabled(false);
			txtLogin.setValue(entity.getLogin());
			txtLogin.setDisabled(false);
			txtNombre.setValue(entity.getNombre());
			txtNombre.setDisabled(false);
			txtUsuaCodigo.setValue(entity.getUsuaCodigo());
			txtUsuaCodigo.setDisabled(true);
			btnSave.setDisabled(false);

		}
	}

	public String action_save() {
		try {
			if ((selectedVtUsuario == null) && (entity == null)) {

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

			String claveR=txtClaveR.getValue().toString().trim();
			String clave=txtClave.getValue().toString().trim(); 

			if(clave.equals(claveR)){
				if (entity == null) {
					Long usuaCodigo = new Long(selectedVtUsuario.getUsuaCodigo());
					entity = businessDelegatorView.getVtUsuario(usuaCodigo);
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
				Date fechaModificacion = new Date();
				entity.setFechaModificacion(fechaModificacion);

				entity.setLogin(FacesUtils.checkString(txtLogin));
				entity.setNombre(FacesUtils.checkString(txtNombre));

				VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
				entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				
				entity.setClave(claveR);

				businessDelegatorView.updateVtUsuario(entity);
				FacesUtils.addInfoMessage("El usuario ha sido modificado con exito");
				data = businessDelegatorView.getDataVtUsuario();
				dataI = businessDelegatorView.getDataVtUsuarioInactivo();
			}else{
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Las contraseñas no coinciden"));
			}
		} catch (Exception e) {
			data = null;
			log.error(e.getMessage());
			log.error(e.toString());
			log.error(e.getLocalizedMessage());
			FacesUtils.addErrorMessage(e.getMessage());
		}


		return "";
	}

	public String cambiarEstado(ActionEvent evt){
		log.info("Cambiando estado..");
		selectedVtUsuario = (VtUsuarioDTO) (evt.getComponent().getAttributes()
				.get("selectedVtUsuario"));	

		try {
			if (entity == null) {
				Long usuaCodigo = new Long(selectedVtUsuario.getUsuaCodigo());
				entity = businessDelegatorView.getVtUsuario(usuaCodigo);
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

			businessDelegatorView.updateVtUsuario(entity);
			FacesUtils.addInfoMessage("El usuario ha sido modificado con exito");
			data = businessDelegatorView.getDataVtUsuario();
			dataI = businessDelegatorView.getDataVtUsuarioInactivo();

			entity=null;
			selectedVtUsuario=null;
		}catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		action_clear();

		return "";
	}

	public String action_clear() {
		entity = null;
		selectedVtUsuario = null;

		if (somActivo != null) {
			somActivo.setValue("-1");
		}

		if (somEmpresasCambio != null) {
			somEmpresasCambio.setValue("-1");
		}

		if (txtLogin != null) {
			txtLogin.setValue(null);
		}

		if (txtNombre != null) {
			txtNombre.setValue(null);
		}

		if (btnSave != null) {
			btnSave.setDisabled(true);
		}

		return "";
	}





}
