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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtPilaProducto;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

import com.vobi.team.modelo.dto.VtPilaProductoDTO;

@ManagedBean 
@ViewScoped
public class VtPilaProductoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(VtEmpresaView.class);

	Long proyectoSeleccionado;

	private SelectOneMenu somEmpresas;
	private SelectOneMenu somEmpresasCrear;
	private SelectOneMenu somActivo;
	private SelectOneMenu somActivoCrear;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somProyectosCrear;
	private SelectOneMenu somProyectoCambio;
	private SelectOneMenu somSprints;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnLimpiar;
	private CommandButton btnFiltrar;
	private CommandButton btnGuardar;
	private CommandButton btnCrearPdP;

	private Panel panelDataTableVtPilaProducto; 

	String stringActivo;

	String stringFiltrado;

	private InputText txtNombre;
	private InputText txtNombreCrear;
	private InputTextarea txtDescripcion;
	private InputTextarea txtDescripcionCrear;
	
	private List<SelectItem> esActivoItems;
	private List<SelectItem> losProyectosItems;
	private List<SelectItem> losProyectosItemsLista;
	private List<SelectItem> lasEmpresasItems;
	private List<SelectItem> losSprintsItems;
	private List<SelectItem> lasEmpresasItemsFiltro;
	private List<SelectItem> losProyectosFiltro;
	

	private List<VtPilaProductoDTO> data;
	private List<VtPilaProductoDTO> dataFiltro;
	private List<VtPilaProductoDTO> dataFiltroI;
	
	private VtPilaProducto entity;
	private VtPilaProductoDTO selectedVtPilaProducto;
	
	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;


	public VtPilaProductoView(){
		super();
		somEmpresas = new SelectOneMenu();
		somProyectos = new SelectOneMenu();
	}
	
	
	@PostConstruct
	public void vtArtefactoViewPostConstructor() {
		try {
			VtProyecto vtProyecto = (VtProyecto) FacesUtils.getfromSession("vtProyecto");
			VtEmpresa vtEmpresa = businessDelegatorView.getVtEmpresa(vtProyecto.getVtEmpresa().getEmprCodigo());
			
			if (vtProyecto != null) {
				somEmpresas.setValue(vtEmpresa.getEmprCodigo());
				filtrarEmpresa();
				somProyectos.setValue(vtProyecto.getProyCodigo());
				dataFiltro = businessDelegatorView.getDataVtPilaProductoNombreProyecto(vtProyecto.getProyCodigo());
				FacesUtils.putinSession("vtProyecto", null);
			} 
			vtProyecto = null;
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public List<SelectItem> getLasEmpresasItemsFiltro() {
		try {
			if(lasEmpresasItemsFiltro==null){
				List<VtEmpresa> listaEmpresas=businessDelegatorView.getVtEmpresa();
				lasEmpresasItemsFiltro=new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa: listaEmpresas) {
					if(vtEmpresa.getActivo().equalsIgnoreCase("S")){
						lasEmpresasItemsFiltro.add(new SelectItem(vtEmpresa.getEmprCodigo(), vtEmpresa.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return lasEmpresasItemsFiltro;
	}

	public void setLasEmpresasItemsFiltro(List<SelectItem> lasEmpresasItemsFiltro) {
		this.lasEmpresasItemsFiltro = lasEmpresasItemsFiltro;
	}

	public List<SelectItem> getLosProyectosFiltro() {
		return losProyectosFiltro;
	}

	public void setLosProyectosFiltro(List<SelectItem> losProyectosFiltro) {
		this.losProyectosFiltro = losProyectosFiltro;
	}

	public void setSomProyectosCrear(SelectOneMenu somProyectosCrear) {
		this.somProyectosCrear = somProyectosCrear;
	}

	public InputText getTxtNombreCrear() {
		return txtNombreCrear;
	}

	public void setTxtNombreCrear(InputText txtNombreCrear) {
		this.txtNombreCrear = txtNombreCrear;
	}

	public InputTextarea getTxtDescripcionCrear() {
		return txtDescripcionCrear;
	}

	public void setTxtDescripcionCrear(InputTextarea txtDescripcionCrear) {
		this.txtDescripcionCrear = txtDescripcionCrear;
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

	public List<SelectItem> getLosProyectosItems() {
		/*
		try {
			if (losProyectosItems == null) {
				List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
				losProyectosItems = new ArrayList<SelectItem>();

				for (VtProyecto vtProyecto : listaProyectos) {
					if(vtProyecto.getActivo().equalsIgnoreCase("S")){
						losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		*/
		return losProyectosItems;
	}

	public void setLosProyectosItems(List<SelectItem> losProyectosItems) {
		this.losProyectosItems = losProyectosItems;
	}

	public List<SelectItem> getLosProyectosItemsLista() {
		try {
			if (losProyectosItemsLista == null) {
				List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
				losProyectosItemsLista = new ArrayList<SelectItem>();

				for (VtProyecto vtProyecto : listaProyectos) {
					if(vtProyecto.getActivo().equalsIgnoreCase("S")){
						losProyectosItemsLista.add(new SelectItem(vtProyecto.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losProyectosItemsLista;
	}

	public void setLosProyectosItemsLista(List<SelectItem> losProyectosItemsLista) {
		this.losProyectosItemsLista = losProyectosItemsLista;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
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

	public String getStringActivo() {
		return stringActivo;
	}

	public void setStringActivo(String stringActivo) {
		this.stringActivo = stringActivo;
	}

	public SelectOneMenu getSomProyectoCambio() {
		return somProyectoCambio;
	}

	public void setSomProyectoCambio(SelectOneMenu somProyectoCambio) {
		this.somProyectoCambio = somProyectoCambio;
	}

	public Panel getPanelDataTableVtPilaProducto() {
		return panelDataTableVtPilaProducto;
	}

	public void setPanelDataTableVtPilaProducto(Panel panelDataTableVtPilaProducto) {
		this.panelDataTableVtPilaProducto = panelDataTableVtPilaProducto;
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

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public SelectOneMenu getSomEmpresasCrear() {
		return somEmpresasCrear;
	}

	public void setSomEmpresasCrear(SelectOneMenu somEmpresasCrear) {
		this.somEmpresasCrear = somEmpresasCrear;
	}

	public CommandButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(CommandButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public String getStringFiltrado() {
		return stringFiltrado;
	}

	public void setStringFiltrado(String stringFiltrado) {
		this.stringFiltrado = stringFiltrado;
	}

	public VtPilaProductoDTO getSelectedVtPilaProducto() {
		return selectedVtPilaProducto;
	}

	public void setSelectedVtPilaProducto(VtPilaProductoDTO selectedVtPilaProducto) {
		this.selectedVtPilaProducto = selectedVtPilaProducto;
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

	public List<VtPilaProductoDTO> getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(List<VtPilaProductoDTO> dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public List<VtPilaProductoDTO> getDataFiltroI() {
		return dataFiltroI;
	}

	public void setDataFiltroI(List<VtPilaProductoDTO> dataFiltroI) {
		this.dataFiltroI = dataFiltroI;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	public SelectOneMenu getSomSprints() {
		return somSprints;
	}

	public void setSomSprints(SelectOneMenu somSprints) {
		this.somSprints = somSprints;
	}

	public List<SelectItem> getLosSprintsItems() {
		try {
			if (losSprintsItems == null) {
				List<VtSprint> listaSprint = businessDelegatorView.getVtSprint();
				losSprintsItems = new ArrayList<SelectItem>();

				for (VtSprint vtSprint : listaSprint) {
					if(vtSprint.getActivo().equalsIgnoreCase("S")){
						losSprintsItems.add(new SelectItem(vtSprint.getSpriCodigo(),vtSprint.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losSprintsItems;
	}

	public void setLosSprintsItems(List<SelectItem> losSprintsItems) {
		this.losSprintsItems = losSprintsItems;
	}

	//TODO: Obtener DTO para el Filtro
	public List<VtPilaProductoDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtPilaProducto();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtPilaProductoDTO> vtPilaProductoDTO) {
		this.data = vtPilaProductoDTO;
	}

	//TODO: Metodos
	public String crearPilaProducto() throws Exception {
		log.info("Creando la pila de producto");

		VtPilaProducto vtPilaProducto = new VtPilaProducto();
		vtPilaProducto.setNombre(txtNombreCrear.getValue().toString().trim());
		vtPilaProducto.setDescripcion(txtDescripcionCrear.getValue().toString().trim());
		Date fecha = new Date();
		vtPilaProducto.setFechaCreacion(fecha);

		String activo = somActivoCrear.getValue().toString().trim();
		if (activo.equals("Si")) {
			vtPilaProducto.setActivo("S");
		} else {
			vtPilaProducto.setActivo("N");
		}

		String proyectos = somProyectos.getValue().toString().trim();
		Long proyecto = Long.parseLong(proyectos);
		VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(proyecto);
		vtPilaProducto.setVtProyecto(vtProyecto);

		VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
		vtPilaProducto.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());
		
		dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(getProyectoSeleccionado());

		try {
			businessDelegatorView.saveVtPilaProducto(vtPilaProducto);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La pila de producto se creo con exito"));
			limpiar();
			vtPilaProducto=null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "/XHTML/vtGestionPilaProducto.xhtml";
	}

	public String limpiar() {
		log.info("Limpiando campos de texto");
		txtNombreCrear.resetValue();
		txtDescripcionCrear.resetValue();
		somActivoCrear.setValue("-1");

		return "";
	}

	public String action_edit(ActionEvent evt) {
		selectedVtPilaProducto = (VtPilaProductoDTO) (evt.getComponent()
				.getAttributes()
				.get("selectedVtPilaProducto"));

		txtDescripcion.setValue(selectedVtPilaProducto.getDescripcion());
		txtDescripcion.setDisabled(false);

		txtNombre.setValue(selectedVtPilaProducto.getNombre());
		txtNombre.setDisabled(false);

		btnGuardar.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public Long getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}

	public void setProyectoSeleccionado(Long proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}

	public void localeChanged(ValueChangeEvent e){
		setProyectoSeleccionado(Long.parseLong(e.getNewValue().toString()));
		try {
			dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(getProyectoSeleccionado());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String filtrar(){
		try {
			String nombreProyecto=somProyectos.getValue().toString().trim();
			long codigoFiltro= Long.parseLong(nombreProyecto);
			dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(codigoFiltro);
			dataFiltroI=businessDelegatorView.getDataVtPilaProductoNombreProyectoI(codigoFiltro);
			btnCrearPdP.setDisabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	public String action_save() {
		try {
			if ((selectedVtPilaProducto == null) && (entity == null)) {

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
				Long pilaCodigo = new Long(selectedVtPilaProducto.getPilaCodigo());
				entity = businessDelegatorView.getVtPilaProducto(pilaCodigo);
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
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			entity.setDescripcion(FacesUtils.checkString(txtDescripcion));

			entity.setNombre(FacesUtils.checkString(txtNombre));

			String proyectos = somProyectoCambio.getValue().toString().trim();
			log.info("proyectos:"+proyectos);
			if(proyectos.equalsIgnoreCase("-1")){
				entity.setVtProyecto(entity.getVtProyecto());
			}
			else{
				Long proyecto = Long.parseLong(proyectos);
				VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(proyecto);
				entity.setVtProyecto(vtProyecto);
			}

			businessDelegatorView.updateVtPilaProducto(entity);

			String nombreProyecto=somProyectos.getValue().toString().trim();
			long codigoFiltro= Long.parseLong(nombreProyecto);
			dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(codigoFiltro);
			dataFiltroI=businessDelegatorView.getDataVtPilaProductoNombreProyectoI(codigoFiltro);

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La pila de producto se modificó con exito"));

		} catch (Exception e) {
			data = null;
			log.error(e.toString());
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);

		return "";
	}

	public String cambiarEstado(ActionEvent evt){

		selectedVtPilaProducto= (VtPilaProductoDTO)(evt.getComponent().getAttributes()
				.get("selectedVtPilaProducto"));

		try {
			if (entity == null) {
				Long pilaCodigo = new Long(selectedVtPilaProducto.getPilaCodigo());
				entity = businessDelegatorView.getVtPilaProducto(pilaCodigo);
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
			
			businessDelegatorView.updateVtPilaProducto(entity);

			String nombreProyecto=somProyectos.getValue().toString().trim();
			long codigoFiltro= Long.parseLong(nombreProyecto);
			dataFiltro=businessDelegatorView.getDataVtPilaProductoNombreProyecto(codigoFiltro);
			dataFiltroI=businessDelegatorView.getDataVtPilaProductoNombreProyectoI(codigoFiltro);

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("La pila de producto se modificó con exito"));
			
			selectedVtPilaProducto=null;
			entity=null;
			
			
			
		}catch (Exception e) {
			data = null;
			log.error(e.toString());
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String filtrarEmpresa(){
		try {
			VtEmpresa vtEmpresa=null;
			losProyectosFiltro=null;
			String empresaS=somEmpresas.getValue().toString().trim();
			if(empresaS.isEmpty() || empresaS.equals("-1")){
			}else{
				Long empresa=Long.parseLong(empresaS);
				vtEmpresa=businessDelegatorView.getVtEmpresa(empresa);
			}

			try{
				if(losProyectosFiltro==null){
					List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
					losProyectosFiltro=new ArrayList<SelectItem>();
					for (VtProyecto vtProyecto:listaProyectos) {
						if(vtProyecto.getActivo().equalsIgnoreCase("S") && vtProyecto.getVtEmpresa().getEmprCodigo().equals(vtEmpresa.getEmprCodigo())){
							losProyectosFiltro.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
						}
					}
				}

			}catch(Exception e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String filtrarEmpresaCrear(){
		try {
			VtEmpresa vtEmpresa=null;
			losProyectosItems=null;
			String empresaS=somEmpresasCrear.getValue().toString().trim();
			if(empresaS.isEmpty() || empresaS.equals("-1")){
			}else{
				Long empresa=Long.parseLong(empresaS);
				vtEmpresa=businessDelegatorView.getVtEmpresa(empresa);
			}

			try{
				if(losProyectosItems==null){
					List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
					losProyectosItems=new ArrayList<SelectItem>();
					for (VtProyecto vtProyecto:listaProyectos) {
						if(vtProyecto.getActivo().equalsIgnoreCase("S") && vtProyecto.getVtEmpresa().getEmprCodigo().equals(vtEmpresa.getEmprCodigo())){
							losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(),vtProyecto.getNombre()));
						}
					}
				}

			}catch(Exception e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}
	
	
	public String redireccionarASprint(ActionEvent evt){
		try {
			selectedVtPilaProducto = (VtPilaProductoDTO) (evt.getComponent().getAttributes()
					.get("selectedVtPilaProducto"));		
			String pilaProducto = selectedVtPilaProducto.getPilaCodigo().toString().trim();
			Long idPilaProducto= Long.parseLong(pilaProducto);
			VtPilaProducto vtPilaProducto = businessDelegatorView.getVtPilaProducto(idPilaProducto);
			FacesUtils.putinSession("vtPilaProducto", vtPilaProducto);
			selectedVtPilaProducto = null;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return "";
	}
	
	
	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
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

	public SelectOneMenu getSomActivoCrear() {
		return somActivoCrear;
	}

	public void setSomActivoCrear(SelectOneMenu somActivoCrear) {
		this.somActivoCrear = somActivoCrear;
	}

	public SelectOneMenu getSomProyectosCrear() {
		return somProyectosCrear;
	}

	public CommandButton getBtnCrearPdP() {
		return btnCrearPdP;
	}

	public void setBtnCrearPdP(CommandButton btnCrearPdP) {
		this.btnCrearPdP = btnCrearPdP;
	}
	
}
