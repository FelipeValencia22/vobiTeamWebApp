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
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;

import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtProyectoDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;

@ManagedBean
@ViewScoped
public class VtProyectoView implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtProyectoView.class);

	private InputText txtNombre;
	private InputText txtNombreC;
	private InputText txtActivo;
	private InputText txtPublico;
	private InputText txtEmpresa;
	private InputTextarea txtDescripcion;
	private InputTextarea txtDescripcionC;
	private List<SelectItem> esPublicoItems;
	private List<SelectItem> esActivoItems;
	private List<SelectItem> lasEmpresasItems;

	private SelectOneMenu somPublico;
	private SelectOneMenu somActivo;
	private SelectOneMenu somPublicoCambio;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somEmpresas;
	private SelectOneMenu somEmpresasFiltro;
	private SelectOneMenu somEmpresasCambio;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnLimpiar;
	private CommandButton btnCrearP;
	private boolean usoPostConstructor = false;
	String stringActivo;	
	VtEmpresa vtEmpresasTotales;
	
	public VtProyectoView() {
		super();
		somEmpresasFiltro = new SelectOneMenu();
		somEmpresasFiltro.setValue("-1");
		btnCrearP = new CommandButton();
		btnCrearP.setDisabled(true);
		vtEmpresasTotales = new VtEmpresa();
	}

	
	@PostConstruct
	public void VtProyectoViewPostConstructor() {
		try {
			
			VtEmpresa vtEmpresasTotales = (VtEmpresa) FacesUtils.getfromSession("vtEmpresa");
			setUsoPostConstructor(true);			
			if (vtEmpresasTotales != null) {				
				somEmpresasFiltro.setValue(vtEmpresasTotales.getEmprCodigo());
				filtrarEmpresa();
				
			} 
			FacesUtils.putinSession("vtEmpresa", null);
			setUsoPostConstructor(false);
			vtEmpresasTotales = null;
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}


	public InputText getTxtNombreC() {
		return txtNombreC;
	}

	public void setTxtNombreC(InputText txtNombreC) {
		this.txtNombreC = txtNombreC;
	}

	public InputTextarea getTxtDescripcionC() {
		return txtDescripcionC;
	}

	public void setTxtDescripcionC(InputTextarea txtDescripcionC) {
		this.txtDescripcionC = txtDescripcionC;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputTextarea getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputTextarea txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public SelectOneMenu getSomEmpresasFiltro() {
		return somEmpresasFiltro;
	}

	public void setSomEmpresasFiltro(SelectOneMenu somEmpresasFiltro) {
		this.somEmpresasFiltro = somEmpresasFiltro;
	}

	public CommandButton getBtnCrearP() {
		return btnCrearP;
	}

	public void setBtnCrearP(CommandButton btnCrearP) {
		this.btnCrearP = btnCrearP;
	}

	public List<SelectItem> getEsPublicoItems() {
		if(esPublicoItems==null){
			esPublicoItems=new ArrayList<SelectItem>();
			esPublicoItems.add(new SelectItem("Público"));
			esPublicoItems.add(new SelectItem("Privado"));

		}
		return esPublicoItems;
	}

	public void setEsPublicoItems(List<SelectItem> esPublicoItems) {
		this.esPublicoItems = esPublicoItems;
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

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public SelectOneMenu getSomPublico() {
		return somPublico;
	}

	public void setSomPublico(SelectOneMenu somPublico) {
		this.somPublico = somPublico;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public List<SelectItem> getLasEmpresasItems() {
		try {
			if(lasEmpresasItems==null){
				List<VtEmpresa> listaEmpresas=businessDelegatorView.getVtEmpresa();
				lasEmpresasItems=new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa: listaEmpresas) {
					if(vtEmpresa.getActivo().equalsIgnoreCase("S")){
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

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public String getStringActivo() {
		return stringActivo;
	}

	public void setStringActivo(String stringActivo) {
		this.stringActivo = stringActivo;
	}

	public InputText getTxtActivo() {
		return txtActivo;
	}

	public void setTxtActivo(InputText txtActivo) {
		this.txtActivo = txtActivo;
	}

	public InputText getTxtPublico() {
		return txtPublico;
	}

	public void setTxtPublico(InputText txtPublico) {
		this.txtPublico = txtPublico;
	}

	public InputText getTxtEmpresa() {
		return txtEmpresa;
	}

	public void setTxtEmpresa(InputText txtEmpresa) {
		this.txtEmpresa = txtEmpresa;
	}

	public SelectOneMenu getSomPublicoCambio() {
		return somPublicoCambio;
	}

	public void setSomPublicoCambio(SelectOneMenu somPublicoCambio) {
		this.somPublicoCambio = somPublicoCambio;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	public SelectOneMenu getSomEmpresasCambio() {
		return somEmpresasCambio;
	}

	public void setSomEmpresasCambio(SelectOneMenu somEmpresasCambio) {
		this.somEmpresasCambio = somEmpresasCambio;
	}

	/// CREAR PROYECTO Y LIMPIAR PANTALLA
	public String crearProyecto() throws Exception{
		log.info("Creando proyecto");

		VtProyecto vtProyecto= new VtProyecto();

		vtProyecto.setDescripcion(txtDescripcionC.getValue().toString().trim());
		Date fechaCreacion = new Date();
		vtProyecto.setFechaCreacion(fechaCreacion);
		vtProyecto.setNombre(txtNombreC.getValue().toString().trim());

		String publico=somPublico.getValue().toString().trim();
		if(publico.equals("Público")){
			vtProyecto.setPublico("S");
		}else{
			vtProyecto.setPublico("N");
		}

		String activo=somActivo.getValue().toString().trim();
		if(activo.equals("Si")){
			vtProyecto.setActivo("S");
		}else{
			vtProyecto.setActivo("N");
		}

		VtUsuario vtUsuario =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
		vtProyecto.setUsuCreador(vtUsuario.getUsuaCodigo());

		String empresaS=somEmpresasFiltro.getValue().toString().trim();
		if(empresaS.isEmpty() || empresaS.equals("-1")){
		}else{
			Long empresa=Long.parseLong(empresaS);
			VtEmpresa vtEmpresa=businessDelegatorView.getVtEmpresa(empresa);
			vtProyecto.setVtEmpresa(vtEmpresa);
		}

		try {
			businessDelegatorView.saveVtProyecto(vtProyecto);
			limpiar();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El proyecto se creo con exito"));
			Long codigoFiltro = Long.valueOf(empresaS);
			somEmpresasFiltro.resetValue();
			btnCrearP.setDisabled(true);
			data = businessDelegatorView.getDataVtProyectoActivo(codigoFiltro);
			dataI = businessDelegatorView.getDataVtProyectoInactivo(codigoFiltro);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}


	public String limpiar(){
		log.info("Limpiando campos de texto");
		txtNombreC.resetValue();
		txtDescripcionC.resetValue();
		somPublico.setValue("-1");
		somActivo.setValue("-1");
		btnCrearP.setDisabled(false);
		return "";
	}


	/// tabla con registros editables

	private VtProyecto entity;

	private CommandButton btnSave;

	private List<VtProyectoDTO> data;

	private List<VtProyectoDTO> dataI;

	private VtProyectoDTO selectedVtProyecto;

	private boolean showDialog;

	private InputText txtProyCodigo;

	public List<VtProyectoDTO> getData() {

		return data;
	}

	public void setDataI(List<VtProyectoDTO> vtProyectoDTO) {
		this.dataI = vtProyectoDTO;
	}

	public List<VtProyectoDTO> getDataI() {
		try {
			if (dataI == null) {
				VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
				dataI=businessDelegatorView.getDataVtProyectoInactivo(vtUsuarioEnSession.getVtEmpresa().getEmprCodigo());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dataI;
	}

	public void setData(List<VtProyectoDTO> vtProyectoDTO) {
		this.data = vtProyectoDTO;
	}

	public VtProyectoDTO getSelectedVtProyecto() {
		return selectedVtProyecto;
	}

	public void setSelectedVtProyecto(VtProyectoDTO vtProyecto) {
		this.selectedVtProyecto = vtProyecto;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public InputText getTxtProyCodigo() {
		return txtProyCodigo;
	}

	public void setTxtProyCodigo(InputText txtProyCodigo) {
		this.txtProyCodigo = txtProyCodigo;
	}

	public CommandButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(CommandButton btnSave) {
		this.btnSave = btnSave;
	}

	public String action_edit(ActionEvent evt) {
		selectedVtProyecto = (VtProyectoDTO) (evt.getComponent().getAttributes()
				.get("selectedVtProyecto"));

		txtDescripcion.setValue(selectedVtProyecto.getDescripcion());
		txtDescripcion.setDisabled(false);
		txtNombre.setValue(selectedVtProyecto.getNombre());
		txtNombre.setDisabled(false);
		somActivoCambio.setValue(selectedVtProyecto.getActivo());
		somActivoCambio.setDisabled(false);
		somPublicoCambio.setValue(selectedVtProyecto.getPublico());
		somPublicoCambio.setDisabled(false);
		btnSave.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public void listener_txtId() {
		try {
			Long proyCodigo = FacesUtils.checkLong(txtProyCodigo);
			entity = (proyCodigo != null)
					? businessDelegatorView.getVtProyecto(proyCodigo) : null;
		} catch (Exception e) {
			entity = null;
		}

		if (entity == null) {
			txtActivo.setDisabled(false);
			txtDescripcion.setDisabled(false);
			txtNombre.setDisabled(false);
			txtPublico.setDisabled(false);
			txtProyCodigo.setDisabled(false);
			btnSave.setDisabled(false);
		} else {
			txtActivo.setValue(entity.getActivo());
			txtActivo.setDisabled(false);
			txtDescripcion.setValue(entity.getDescripcion());
			txtDescripcion.setDisabled(false);
			txtNombre.setValue(entity.getNombre());
			txtNombre.setDisabled(false);
			txtPublico.setValue(entity.getPublico());
			txtPublico.setDisabled(false);
			txtEmpresa.setValue(entity.getVtEmpresa().getEmprCodigo());
			txtEmpresa.setDisabled(false);
			txtProyCodigo.setValue(entity.getProyCodigo());
			txtProyCodigo.setDisabled(true);
			btnSave.setDisabled(false);
		}
	}

	public String action_save() {
		try {
			if ((selectedVtProyecto == null) && (entity == null)) {

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
				Long proyCodigo = new Long(selectedVtProyecto.getProyCodigo());
				entity = businessDelegatorView.getVtProyecto(proyCodigo);
			}

			String activo = somActivoCambio.getValue().toString().trim();
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

			String publico = somPublicoCambio.getValue().toString().trim();
			if (publico.equalsIgnoreCase("Público")) {
				entity.setPublico("S");
			}
			if (publico.equalsIgnoreCase("Privado")) {
				entity.setPublico("N");
			}
			if (publico.equalsIgnoreCase("-1")) {
				entity.setPublico(entity.getPublico());
			}

			entity.setDescripcion(FacesUtils.checkString(txtDescripcion));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);
			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			
			Long codigoFiltro=entity.getVtEmpresa().getEmprCodigo();

			businessDelegatorView.updateVtProyecto(entity);
			FacesUtils.addInfoMessage("El proyecto ha sido modificado con exito");
			
			VtEmpresa vtEmpresaFiltro = businessDelegatorView.getVtEmpresa(codigoFiltro);
			
			data=businessDelegatorView.getDataVtProyectoActivo(vtEmpresaFiltro.getEmprCodigo());
			dataI=businessDelegatorView.getDataVtProyectoInactivo(vtEmpresaFiltro.getEmprCodigo());
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String cambiarEstado(ActionEvent evt){
		log.info("Cambiando estado..");
		selectedVtProyecto = (VtProyectoDTO) (evt.getComponent().getAttributes()
				.get("selectedVtProyecto"));

		try {
			if (entity == null) {
				Long proyCodigo = new Long(selectedVtProyecto.getProyCodigo());
				entity = businessDelegatorView.getVtProyecto(proyCodigo);
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
			
			Long codigoFiltro=entity.getVtEmpresa().getEmprCodigo();
			
			businessDelegatorView.updateVtProyecto(entity);
			if(entity.getActivo().equals("S")){
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Ok,!El proyecto se ha activado con éxito!"));
			}else if(entity.getActivo().equals("N")){
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Ok,!El proyecto se ha inactivado con éxito!"));
			}
			
			VtEmpresa vtEmpresaFiltro = businessDelegatorView.getVtEmpresa(codigoFiltro);
		
			data=businessDelegatorView.getDataVtProyectoActivo(vtEmpresaFiltro.getEmprCodigo());
			dataI=businessDelegatorView.getDataVtProyectoInactivo(vtEmpresaFiltro.getEmprCodigo());
			
			selectedVtProyecto=null;
			entity=null;
			
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	public String filtrarEmpresa() {
		btnCrearP.setDisabled(false);
		try {
			
			String empresaS = somEmpresasFiltro.getValue().toString().trim();
			
			if(empresaS.equals("-1")){
				btnCrearP.setDisabled(true);
				data = null;
				dataI = null;
			}else{
				Long codigoFiltro = Long.valueOf(empresaS);
				VtEmpresa vtEmpresa = businessDelegatorView.getVtEmpresa(codigoFiltro);
				data=businessDelegatorView.getDataVtProyectoActivo(vtEmpresa.getEmprCodigo());
				dataI=businessDelegatorView.getDataVtProyectoInactivo(vtEmpresa.getEmprCodigo());
				btnCrearP.setDisabled(false);
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String action_clear() {
		entity = null;
		selectedVtProyecto = null;

		if (txtActivo != null) {
			txtActivo.setValue(null);
			txtActivo.setDisabled(true);
		}

		if (txtDescripcion != null) {
			txtDescripcion.setValue(null);
			txtDescripcion.setDisabled(true);
		}

		if (txtNombre != null) {
			txtNombre.setValue(null);
			txtNombre.setDisabled(true);
		}

		if (txtPublico != null) {
			txtPublico.setValue(null);
			txtPublico.setDisabled(true);
		}



		if (txtProyCodigo != null) {
			txtProyCodigo.setValue(null);
			txtProyCodigo.setDisabled(false);
		}

		if (btnSave != null) {
			btnSave.setDisabled(true);
		}


		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		action_clear();

		return "";
	}
	
	public String redireccionarAPilas(ActionEvent evt){
		try {
			selectedVtProyecto = (VtProyectoDTO) (evt.getComponent().getAttributes()
					.get("selectedVtProyecto"));		
			String proyecto = selectedVtProyecto.getProyCodigo().toString().trim();
			Long idProyecto = Long.parseLong(proyecto);

			VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(idProyecto);
			VtEmpresa vtEmpresa = businessDelegatorView.getVtEmpresa(vtProyecto.getVtEmpresa().getEmprCodigo());
			FacesUtils.putinSession("vtProyecto", vtProyecto);
			FacesUtils.putinSession("vtEmpresa", vtEmpresa);
			selectedVtProyecto = null;
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "";
	}

	public boolean isUsoPostConstructor() {
		return usoPostConstructor;
	}

	public void setUsoPostConstructor(boolean usoPostConstructor) {
		this.usoPostConstructor = usoPostConstructor;
	}

}
