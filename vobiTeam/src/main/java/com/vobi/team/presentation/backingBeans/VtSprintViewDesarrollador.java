package com.vobi.team.presentation.backingBeans;

import com.vobi.team.modelo.*;
import com.vobi.team.modelo.dto.VtArtefactoDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;
import com.vobi.team.presentation.businessDelegate.*;
import com.vobi.team.utilities.*;


import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

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


/**
 * @author Zathura Code Generator http://zathuracode.org
 * www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class VtSprintViewDesarrollador implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtSprintViewDesarrollador.class);

	private DualListModel<VtArtefacto> vtArtefacto;

	private MeterGaugeChartModel meterGaugeModel;

	VtSprint vtSprint=null;
	Long codigoSprint=null;

	private List<VtArtefacto> artefactosSource;
	private List<VtArtefacto> artefactosTarget;

	private PickList pickList;

	private SelectOneMenu somActivo;
	private SelectOneMenu somPilaProducto;
	private SelectOneMenu somPilaProductoCrear;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somPilaProductoCambio;
	private SelectOneMenu somProyectoCambio;
	private SelectOneMenu somEmpresas;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somEstadosSprint;

	private List<SelectItem> esActivoItems;
	private List<SelectItem> losEstadosSprintsItems;
	private List<SelectItem> losProyectosItems;
	private List<SelectItem> esPilaProductoItems;
	private List<SelectItem> lasEmpresasItems;
	private List<SelectItem> losProyectosFiltro;
	private List<SelectItem> lasPilasDeProductoFiltro;

	private InputText txtNombre;
	private InputText txtNombreCrear;
	private InputText txtEsfuerzoCrear;
	private InputText txtEsfuerzo;
	private InputTextarea txtObjetivo;
	private InputTextarea txtObjetivoCrear;
	private InputText txtSpriCodigo;
	private InputText txtNombrePilaProducto;
	private InputText txtCodigoPilaProducto;

	private Calendar txtFechaFin;
	private Calendar txtFechaInicio;
	private Calendar txtFechaFinM;
	private Calendar txtFechaInicioM;

	private CommandButton btnCrearS;
	private CommandButton btnCrear;
	private CommandButton btnLimpiarS;
	private CommandButton btnGuardar;
	private CommandButton btnLimpiar;
	private CommandButton btnFiltrar;
	private CommandButton btnCrearSprintFiltrado;

	private List<VtSprintDTO> data;
	private List<VtSprintDTO> dataFiltro;
	private List<VtSprintDTO> dataFiltroI;
	private List<VtArtefactoDTO> dataActivo;

	private VtSprintDTO selectedVtSprint;
	private VtSprint entity;
	VtSprint sprintSeleccionado;

	private Panel pnlToogle;

	private boolean showDialog;
	private boolean showDialogProgreso;
	
	String empresaUsuario;
	
	Long pilaCodigo=null;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public VtSprintViewDesarrollador() {
		super();
	}
	
	public InputText getTxtNombreCrear() {
		return txtNombreCrear;
	}

	public void setTxtNombreCrear(InputText txtNombreCrear) {
		this.txtNombreCrear = txtNombreCrear;
	}

	public PickList getPickList() {
		return pickList;
	}

	public void setPickList(PickList pickList) {
		this.pickList = pickList;
	}

	public VtSprint getVtSprint() {
		return vtSprint;
	}

	public void setVtSprint(VtSprint vtSprint) {
		this.vtSprint = vtSprint;
	}

	public boolean isShowDialogProgreso() {
		return showDialogProgreso;
	}

	public VtSprint getSprintSeleccionado() {
		return sprintSeleccionado;
	}

	public void setSprintSeleccionado(VtSprint sprintSeleccionado) {
		this.sprintSeleccionado = sprintSeleccionado;
	}

	public void setShowDialogProgreso(boolean showDialogProgreso) {
		this.showDialogProgreso = showDialogProgreso;
	}

	public DualListModel<VtArtefacto> getVtArtefacto() {
		return vtArtefacto;
	}

	public void setVtArtefacto(DualListModel<VtArtefacto> vtArtefacto) {
		this.vtArtefacto = vtArtefacto;
	}

	public InputText getTxtEsfuerzoCrear() {
		return txtEsfuerzoCrear;
	}

	public Long getCodigoSprint() {
		return codigoSprint;
	}

	public InputText getTxtEsfuerzo() {
		return txtEsfuerzo;
	}

	public void setTxtEsfuerzo(InputText txtEsfuerzo) {
		this.txtEsfuerzo = txtEsfuerzo;
	}

	public void setCodigoSprint(Long codigoSprint) {
		this.codigoSprint = codigoSprint;
	}

	public List<VtArtefacto> getArtefactosSource() {
		return artefactosSource;
	}

	public void setArtefactosSource(List<VtArtefacto> artefactosSource) {
		this.artefactosSource = artefactosSource;
	}

	public List<VtArtefacto> getArtefactosTarget() {
		return artefactosTarget;
	}

	public void setArtefactosTarget(List<VtArtefacto> artefactosTarget) {
		this.artefactosTarget = artefactosTarget;
	}

	public void setTxtEsfuerzoCrear(InputText txtEsfuerzoCrear) {
		this.txtEsfuerzoCrear = txtEsfuerzoCrear;
	}

	public MeterGaugeChartModel getMeterGaugeModel() {
		return meterGaugeModel;
	}

	public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
		this.meterGaugeModel = meterGaugeModel;
	}

	public Panel getPnlToogle() {
		return pnlToogle;
	}

	public void setPnlToogle(Panel pnlToogle) {
		this.pnlToogle = pnlToogle;
	}

	public InputTextarea getTxtObjetivoCrear() {
		return txtObjetivoCrear;
	}

	public void setTxtObjetivoCrear(InputTextarea txtObjetivoCrear) {
		this.txtObjetivoCrear = txtObjetivoCrear;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public SelectOneMenu getSomPilaProducto() {
		return somPilaProducto;
	}

	public Calendar getTxtFechaFinM() {
		return txtFechaFinM;
	}

	public void setTxtFechaFinM(Calendar txtFechaFinM) {
		this.txtFechaFinM = txtFechaFinM;
	}

	public Calendar getTxtFechaInicioM() {
		return txtFechaInicioM;
	}

	public void setTxtFechaInicioM(Calendar txtFechaInicioM) {
		this.txtFechaInicioM = txtFechaInicioM;
	}

	public void setSomPilaProducto(SelectOneMenu somPilaProducto) {
		this.somPilaProducto = somPilaProducto;
	}

	public SelectOneMenu getSomPilaProductoCrear() {
		return somPilaProductoCrear;
	}

	public void setSomPilaProductoCrear(SelectOneMenu somPilaProductoCrear) {
		this.somPilaProductoCrear = somPilaProductoCrear;
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

	public List<SelectItem> getEsPilaProductoItems() {

		try{
			if(esPilaProductoItems==null){
				List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
				esPilaProductoItems=new ArrayList<SelectItem>();
				for (VtProyecto vtProyecto:listaProyectos) {
					if(vtProyecto.getActivo().equalsIgnoreCase("S")){
						esPilaProductoItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
					}
				}
			}

		}catch(Exception e) {
			log.error(e.getMessage());
		}

		return esPilaProductoItems;
	}

	public List<SelectItem> getLasPilasDeProductoFiltro() {
		return lasPilasDeProductoFiltro;
	}

	public void setLasPilasDeProductoFiltro(List<SelectItem> lasPilasDeProductoFiltro) {
		this.lasPilasDeProductoFiltro = lasPilasDeProductoFiltro;
	}

	public List<SelectItem> getLosProyectosFiltro() {
		try{
			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			if(losProyectosFiltro==null){
				List<VtProyectoUsuario> listaProyectos= businessDelegatorView.consultarProyectoUsuario(vtUsuario.getUsuaCodigo());
				losProyectosFiltro = new ArrayList<SelectItem>();
				for(VtProyectoUsuario vtProyectoUsuario : listaProyectos){
					losProyectosFiltro.add(new SelectItem(vtProyectoUsuario.getVtProyecto().getProyCodigo(), vtProyectoUsuario.getVtProyecto().getNombre()));
				}
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return losProyectosFiltro;
	}

	public void setLosProyectosFiltro(List<SelectItem> losProyectosFiltro) {
		this.losProyectosFiltro = losProyectosFiltro;
	}

	public void setEsPilaProductoItems(List<SelectItem> esPilaProductoItems) {
		this.esPilaProductoItems = esPilaProductoItems;
	}

	public List<SelectItem> getLasEmpresasItems() {
		try {
			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			
			if(lasEmpresasItems==null){
				List<VtEmpresa> listaEmpresas=businessDelegatorView.getVtEmpresa();
				lasEmpresasItems=new ArrayList<SelectItem>();
				for (VtEmpresa vtEmpresa: listaEmpresas) {
					if(vtEmpresa.getActivo().equalsIgnoreCase("S") ){
						lasEmpresasItems.add(new SelectItem(vtEmpresa.getEmprCodigo(), vtEmpresa.getNombre()));
					}
				}
			}
			somEmpresas.setValue(vtUsuario.getVtEmpresa().getNombre());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return lasEmpresasItems;
	}

	public void setLasEmpresasItems(List<SelectItem> lasEmpresasItems) {
		this.lasEmpresasItems = lasEmpresasItems;
	}

	public List<VtArtefactoDTO> getDataActivo() {
		return dataActivo;
	}

	public void setDataActivo(List<VtArtefactoDTO> dataActivo) {
		this.dataActivo = dataActivo;
	}

	public CommandButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(CommandButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputTextarea getTxtObjetivo() {
		return txtObjetivo;
	}

	public void setTxtObjetivo(InputTextarea txtObjetivo) {
		this.txtObjetivo = txtObjetivo;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public Calendar getTxtFechaFin() {
		return txtFechaFin;
	}

	public void setTxtFechaFin(Calendar txtFechaFin) {
		this.txtFechaFin = txtFechaFin;
	}

	public Calendar getTxtFechaInicio() {
		return txtFechaInicio;
	}

	public void setTxtFechaInicio(Calendar txtFechaInicio) {
		this.txtFechaInicio = txtFechaInicio;
	}

	public void listener_txtFechaFin() {
		Date inputDate = (Date) txtFechaFin.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaInicio() {
		Date inputDate = (Date) txtFechaInicio.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaFinM() {
		Date inputDate = (Date) txtFechaFinM.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaInicioM() {
		Date inputDate = (Date) txtFechaInicioM.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance()
		.addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public CommandButton getBtnCrearS() {
		return btnCrearS;
	}

	public void setBtnCrearS(CommandButton btnCrearS) {
		this.btnCrearS = btnCrearS;
	}

	public CommandButton getBtnLimpiarS() {
		return btnLimpiarS;
	}

	public void setBtnLimpiarS(CommandButton btnLimpiarS) {
		this.btnLimpiarS = btnLimpiarS;
	}

	public List<VtSprintDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataVtSprint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<VtSprintDTO> vtSprintDTO) {
		this.data = vtSprintDTO;
	}

	public List<VtSprintDTO> getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(List<VtSprintDTO> dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public List<VtSprintDTO> getDataFiltroI() {
		return dataFiltroI;
	}

	public void setDataFiltroI(List<VtSprintDTO> dataFiltroI) {
		this.dataFiltroI = dataFiltroI;
	}

	public VtSprintDTO getSelectedVtSprint() {
		return selectedVtSprint;
	}

	public void setSelectedVtSprint(VtSprintDTO vtSprint) {
		this.selectedVtSprint = vtSprint;
	}

	public InputText getTxtSpriCodigo() {
		return txtSpriCodigo;
	}

	public void setTxtSpriCodigo(InputText txtSpriCodigo) {
		this.txtSpriCodigo = txtSpriCodigo;
	}

	public InputText getTxtNombrePilaProducto() {
		return txtNombrePilaProducto;
	}

	public void setTxtNombrePilaProducto(InputText txtNombrePilaProducto) {
		this.txtNombrePilaProducto = txtNombrePilaProducto;
	}

	public InputText getTxtCodigoPilaProducto() {
		return txtCodigoPilaProducto;
	}

	public void setTxtCodigoPilaProducto(InputText txtCodigoPilaProducto) {
		this.txtCodigoPilaProducto = txtCodigoPilaProducto;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	public SelectOneMenu getSomPilaProductoCambio() {
		return somPilaProductoCambio;
	}

	public void setSomPilaProductoCambio(SelectOneMenu somPilaProductoCambio) {
		this.somPilaProductoCambio = somPilaProductoCambio;
	}

	public SelectOneMenu getSomProyectoCambio() {
		return somProyectoCambio;
	}

	public void setSomProyectoCambio(SelectOneMenu somProyectoCambio) {
		this.somProyectoCambio = somProyectoCambio;
	}

	public List<SelectItem> getLosProyectosItems() {

		try{
			if(losProyectosItems==null){
				List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
				losProyectosItems=new ArrayList<SelectItem>();
				for (VtProyecto vtProyecto:listaProyectos) {
					losProyectosItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
				}
			}

		}catch(Exception e) {
			log.error(e.getMessage());
		}

		return losProyectosItems;
	}

	public void setLosProyectosItems(List<SelectItem> losProyectosItems) {
		this.losProyectosItems = losProyectosItems;
	}

	public CommandButton getBtnCrearSprintFiltrado() {
		return btnCrearSprintFiltrado;
	}

	public void setBtnCrearSprintFiltrado(CommandButton btnCrearSprintFiltrado) {
		this.btnCrearSprintFiltrado = btnCrearSprintFiltrado;
	}

	public String getEmpresaUsuario() {
		return empresaUsuario;
	}

	public void setEmpresaUsuario(String empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}

	// TODO: Metodos
	public String crearSprint(){
		log.info("Guardando..");
		String capacidadEstimada;
		try {
			VtSprint vtSprint = new VtSprint();

			vtSprint.setActivo("S");

			VtPilaProducto vtPilaProducto;
			String longPila=somPilaProducto.getValue().toString().trim();
			Long codigoPila= Long.valueOf(longPila);
			vtPilaProducto=businessDelegatorView.getVtPilaProducto(codigoPila);

			vtSprint.setVtPilaProducto(vtPilaProducto);

			Date fechaCreacion= new Date();
			vtSprint.setFechaCreacion(fechaCreacion);
			vtSprint.setFechaFin(FacesUtils.checkDate(txtFechaFin));
			vtSprint.setFechaInicio(FacesUtils.checkDate(txtFechaInicio));

			vtSprint.setNombre(txtNombreCrear.getValue().toString().trim());
			vtSprint.setObjetivo(txtObjetivoCrear.getValue().toString().trim());
//
//			VtEstadoSprint vtEstadoSprint = businessDelegatorView.getVtEstadoSprint(Long.parseLong(somEstadosSprint.getValue().toString().trim()));
//			vtSprint.setVtEstadoSprint(vtEstadoSprint);
			
			capacidadEstimada=txtEsfuerzoCrear.getValue().toString().trim();

			businessDelegatorView.saveVtSprint(vtSprint,capacidadEstimada);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El sprint se creó con exito"));
			dataFiltro=businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
			dataFiltroI=businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);
			limpiar();
			codigoSprint=vtSprint.getSpriCodigo();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String modificar(ActionEvent evt) {
		//TODO:ShowDialog
		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes()
				.get("selectedVtSprint"));

		txtFechaInicioM.setValue(selectedVtSprint.getFechaInicio());
		txtFechaInicioM.setDisabled(false);
		txtFechaFinM.setValue(selectedVtSprint.getFechaFin());
		txtFechaFinM.setDisabled(false);

		txtNombre.setValue(selectedVtSprint.getNombre());
		txtNombre.setDisabled(false);
		txtObjetivo.setValue(selectedVtSprint.getObjetivo());
		txtObjetivo.setDisabled(false);
		btnGuardar.setDisabled(false);
		setShowDialog(true);

		return "";
	}
	
	private void createMeterGaugeModels() {
		
		meterGaugeModel = initMeterGaugeModel();
		meterGaugeModel.setSeriesColors("cc6666,66cc66");
		meterGaugeModel.setGaugeLabelPosition("bottom");
		meterGaugeModel.setTitle("Tu progreso en el Sprint");
		meterGaugeModel.setShowTickLabels(true);
		meterGaugeModel.setLabelHeightAdjust(110);
		meterGaugeModel.setIntervalOuterRadius(150);
		meterGaugeModel.setValue(0);
		calcularEsfuerzo();
	}
	
	public String verProgreso(ActionEvent evt){
		setShowDialogProgreso(true);
		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes()
				.get("selectedVtSprint"));
		try {
			setSprintSeleccionado(businessDelegatorView.getVtSprint(selectedVtSprint.getSpriCodigo()));
			createMeterGaugeModels();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String limpiar(){
		log.info("Limpiando campos de texto..");

		txtNombreCrear.resetValue();
		txtObjetivoCrear.resetValue();
		txtFechaFin.setValue(null);
		txtFechaInicio.setValue(null);
		txtEsfuerzoCrear.resetValue();
		btnCrearS.setDisabled(true);

		return "";
	}

	public String action_save() {
		try {
			if ((selectedVtSprint == null) && (entity == null)) {

			} else {
				action_modify();
			}

			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	@PostConstruct
	public void init() {
		iniciarMeterGaugeModels();
	}
	
	@SuppressWarnings("serial")
	private MeterGaugeChartModel initMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>(){
			
			VtSprint vtSprintS=getSprintSeleccionado();
			double valores=0;
			
			{
				if(vtSprintS==null){
					add(100);
					add(200);
				}else{
					try {
						
						
						
						VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
						
						List<VtUsuarioArtefacto> listUsuarioArtefacto=businessDelegatorView.getVtUsuarioArtefacto();
						for(VtUsuarioArtefacto vtUsuarioArtefacto : listUsuarioArtefacto){
							if(vtUsuarioArtefacto.getVtArtefacto().getVtSprint()!=null){
								if(vtUsuarioArtefacto.getVtArtefacto().getVtSprint().getSpriCodigo().equals(vtSprintS.getSpriCodigo()) &&
										(vtUsuarioArtefacto.getVtUsuario().getUsuaCodigo().equals(vtUsuario.getUsuaCodigo()))
										){
									valores=valores+vtUsuarioArtefacto.getVtArtefacto().getEsfuerzoEstimado();
								}
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					int ochenta=(int) (valores*0.80);
					add(ochenta);
					add(valores);
				}
			}};

			return new MeterGaugeChartModel(140, intervals);
	}
	
	private void iniciarMeterGaugeModels() {
		
		meterGaugeModel = initMeterGaugeModel();		
		meterGaugeModel.setSeriesColors("cc6666,66cc66");
		meterGaugeModel.setShowTickLabels(true);
		meterGaugeModel.setLabelHeightAdjust(110);
		meterGaugeModel.setIntervalOuterRadius(150);
		meterGaugeModel.setTitle("Tu progreso en el Sprint");
	}
	
	
	
	public void calcularEsfuerzo(){
		double esfuerzo=0;
		try {
			VtSprint vtSprintS=getSprintSeleccionado();
			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			List<VtProgresoArtefacto> listaProgresoArtefacto=businessDelegatorView.getVtProgresoArtefacto();
			for(VtProgresoArtefacto vtProgresoArtefacto : listaProgresoArtefacto){
				if(vtProgresoArtefacto.getVtArtefacto().getVtSprint()!=null){
					if(vtProgresoArtefacto.getVtArtefacto().getVtSprint().getSpriCodigo().equals(vtSprintS.getSpriCodigo())
							&&
							(vtProgresoArtefacto.getUsuCreador().equals(vtUsuario.getUsuaCodigo()))
							){
						esfuerzo=esfuerzo+vtProgresoArtefacto.getTiempoDedicado();
					}
				}
				
			}
			meterGaugeModel.setValue(esfuerzo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String action_modify() {
		try {
			Long spriCodigo = null;
			if (entity == null) {
				spriCodigo = new Long(selectedVtSprint.getSpriCodigo());
				entity = businessDelegatorView.getVtSprint(spriCodigo);
			}

			if(!txtFechaFinM.toString().isEmpty()) entity.setFechaFin(FacesUtils.checkDate(txtFechaFinM));
			if(!txtFechaInicioM.toString().isEmpty()) entity.setFechaInicio(FacesUtils.checkDate(txtFechaInicioM));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			entity.setObjetivo(FacesUtils.checkString(txtObjetivo));
			entity.setActivo(entity.getActivo());
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);
			entity.setCapacidadEstimada(Integer.parseInt(txtEsfuerzo.getValue().toString().trim()));

			VtUsuario vtUsuarioEnSession =  (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			businessDelegatorView.updateVtSprint(entity);

			String pila=somPilaProducto.getValue().toString().trim();
			pilaCodigo=Long.valueOf(pila);
			dataFiltro=businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
			dataFiltroI=businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);

			FacesUtils.addInfoMessage("El Sprint ha sido modificado con exito");


		}catch (Exception e) {
			e.printStackTrace();
			data = null;
			log.error(e.getMessage());
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		limpiar();

		return "";
	}

	public String filtrarEmpresa(){
		try {
			VtEmpresa vtEmpresa=null;
			losProyectosFiltro=null;
			String empresaS=somEmpresas.getValue().toString().trim();

			Long empresa=Long.parseLong(empresaS);
			vtEmpresa=businessDelegatorView.getVtEmpresa(empresa);

			try{
				if(losProyectosFiltro==null){
					List<VtProyecto> listaProyectos=businessDelegatorView.getVtProyecto();
					losProyectosFiltro=new ArrayList<SelectItem>();
					for (VtProyecto vtProyecto:listaProyectos) {
						if(vtProyecto.getActivo().equalsIgnoreCase("S") && vtProyecto.getVtEmpresa().getEmprCodigo().equals(vtEmpresa.getEmprCodigo())){
							losProyectosFiltro.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
						}
						else{

						}
					}
				}

			}catch(Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}





		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String filtrarProyecto(){
		String proyectoS=somProyectos.getValue().toString().trim();
		if(!proyectoS.equals("-1")){
			try {
				VtProyecto vtProyecto=null;
				lasPilasDeProductoFiltro=null;


				Long proyecto=Long.parseLong(proyectoS);
				vtProyecto=businessDelegatorView.getVtProyecto(proyecto);	

				try{
					if(lasPilasDeProductoFiltro==null){
						List<VtPilaProducto> listaPilasDeProducto=businessDelegatorView.getVtPilaProducto();
						lasPilasDeProductoFiltro= new ArrayList<SelectItem>();
						for (VtPilaProducto vtPilaProducto:listaPilasDeProducto){
							if(vtPilaProducto.getActivo().equalsIgnoreCase("S") && vtPilaProducto.getVtProyecto().getProyCodigo().equals(vtProyecto.getProyCodigo())){
								lasPilasDeProductoFiltro.add(new SelectItem(vtPilaProducto.getPilaCodigo(), vtPilaProducto.getNombre()));
							}

						}
					}


				}catch(Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}


			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();

			}
		}else{
			somPilaProducto.resetValue();
			dataFiltro=null;
			dataFiltroI=null;
		}

		return "";
	}

	public String filtrar(){
		String pila=somPilaProducto.getValue().toString().trim();
		if(!pila.equals("-1")){
			try {
				pilaCodigo=Long.valueOf(pila); 
				dataFiltro=businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
				dataFiltroI=businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			dataFiltro=null;
			dataFiltroI=null;
		}
		return "";
	}

	public void actualizarListaUsuarios() throws Exception {

		try {
			Long idSprint =codigoSprint;
			vtSprint = businessDelegatorView.getVtSprint(idSprint);

			artefactosSource = businessDelegatorView.consultarArtefactosSinAsignarASprint(vtSprint.getVtPilaProducto().getVtProyecto().getProyCodigo());
			artefactosTarget = businessDelegatorView.consultarArtefactosAsignadosASprint(idSprint);

			vtArtefacto.setSource(artefactosSource);
			vtArtefacto.setTarget(artefactosTarget);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	public String cambiarEstado(ActionEvent evt){

		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes()
				.get("selectedVtSprint"));

		try {
			Long spriCodigo = null;
			if (entity == null) {
				spriCodigo = new Long(selectedVtSprint.getSpriCodigo());
				entity = businessDelegatorView.getVtSprint(spriCodigo);
			}

			dataActivo=null;
			dataActivo=businessDelegatorView.getDataVtArtefactoActivo(spriCodigo);

			if(dataActivo==null){

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

				businessDelegatorView.updateVtSprint(entity);

				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El sprint de producto se modificó con exito"));

				dataFiltro=businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
				dataFiltroI=businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);

				selectedVtSprint=null;
				entity=null;

			}
			else{
				FacesUtils.addInfoMessage("No se puede cambiar el estado porque tiene artefactos activos");
			} 
		}catch (Exception e) {
			data = null;
			log.error(e.toString());
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String redireccionarAArtefactos(ActionEvent evt){
		try {
			selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes()
					.get("selectedVtSprint"));		
			String sprint = selectedVtSprint.getSpriCodigo().toString().trim();
			Long idSprint = Long.parseLong(sprint);

			VtSprint vtSprint = businessDelegatorView.getVtSprint(idSprint);
			FacesUtils.putinSession("vtSprint", vtSprint);
			selectedVtSprint = null;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

	public SelectOneMenu getSomEstadosSprint() {
		return somEstadosSprint;
	}

	public void setSomEstadosSprint(SelectOneMenu somEstadosSprint) {
		this.somEstadosSprint = somEstadosSprint;
	}

	public List<SelectItem> getLosEstadosSprintsItems() {
		try{
			if(losEstadosSprintsItems==null){
				List<VtEstadoSprint> listaEstadosSprint=businessDelegatorView.getVtEstadoSprint();
				losEstadosSprintsItems=new ArrayList<SelectItem>();
				for (VtEstadoSprint vtEstadoSprint : listaEstadosSprint) {
					losEstadosSprintsItems.add(new SelectItem(vtEstadoSprint.getEstsprCodigo(), vtEstadoSprint.getNombre()));
				}

			}

		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return losEstadosSprintsItems;
	}

	public void setLosEstadosSprintsItems(List<SelectItem> losEstadosSprintsItems) {
		this.losEstadosSprintsItems = losEstadosSprintsItems;
	}
	



}