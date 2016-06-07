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
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;

import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtProyectoDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;

@ManagedBean
@ViewScoped
public class VtProyectoViewDesarrollador implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtProyectoViewDesarrollador.class);

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

	String stringActivo;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public VtProyectoViewDesarrollador() {
		super();
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

	

	public String limpiar(){
		log.info("Limpiando campos de texto");
		txtNombre.resetValue();
		txtDescripcion.resetValue();
		somPublico.setValue("-1");
		somActivo.setValue("-1");

		btnCrear.setDisabled(true);
		return "";
	}


	/// tabla con registros editables

	private VtProyecto entity;

	private CommandButton btnSave;

	private List<VtProyectoUsuario> data;

	private List<VtProyectoDTO> dataI;

	private VtProyectoDTO selectedVtProyecto;

	private boolean showDialog;

	private InputText txtProyCodigo;

	public List<VtProyectoUsuario> getData() {
		
		try {
			if (data == null) {
				VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
				data=businessDelegatorView.consultarProyectoUsuarioPorUsuario(vtUsuarioEnSession.getUsuaCodigo());
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			e.printStackTrace();
		}

		return dataI;
	}

	public void setData(List<VtProyectoUsuario> vtProyectoUsuario) {
		this.data = vtProyectoUsuario;
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


}
