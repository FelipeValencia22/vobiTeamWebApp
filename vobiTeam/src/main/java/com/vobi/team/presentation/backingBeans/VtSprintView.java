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
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.TransferEvent;
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
 * @author Zathura Code Generator http://zathuracode.org www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class VtSprintView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtSprintView.class);

	private DualListModel<VtArtefacto> vtArtefacto;

	private MeterGaugeChartModel meterGaugeModel;

	VtSprint vtSprint = null;
	Long codigoSprint = null;

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

	private Panel pnlToogle;

	private boolean showDialog;
	private boolean usoPostConstructor = false;

	String empresaUsuario;

	Long pilaCodigo = null;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public VtSprintView() {
		super();
		somEmpresas = new SelectOneMenu();
		somProyectos = new SelectOneMenu();
		somPilaProducto = new SelectOneMenu();
		pnlToogle =  new Panel();
		btnCrearS = new CommandButton();
		btnCrearS.setDisabled(true);
	}

	@PostConstruct
	public void init() {
		List<VtArtefacto> artefactosSource = new ArrayList<VtArtefacto>();
		List<VtArtefacto> artefactosTarget = new ArrayList<VtArtefacto>();
		vtArtefacto = new DualListModel<>(artefactosSource, artefactosTarget);
		iniciarMeterGaugeModels();
		usoPostConstructor = true;

		try {
			VtPilaProducto vtPilaProducto = (VtPilaProducto) FacesUtils.getfromSession("vtPilaProducto");

			if (vtPilaProducto != null) {
				VtProyecto vtProyecto = businessDelegatorView
						.getVtProyecto(vtPilaProducto.getVtProyecto().getProyCodigo());
				VtEmpresa vtEmpresa = vtProyecto.getVtEmpresa();
				somEmpresas.setValue(vtEmpresa.getEmprCodigo());
				filtrarEmpresa();
				somProyectos.setValue(vtProyecto.getProyCodigo());
				filtrarProyecto();
				somPilaProducto.setValue(vtPilaProducto.getPilaCodigo());
				filtrar();
				dataFiltro = businessDelegatorView.getDataVtSprintFiltro(vtPilaProducto.getPilaCodigo());
				dataFiltroI = businessDelegatorView.getDataVtSprintFiltroI(vtPilaProducto.getPilaCodigo());
				FacesUtils.putinSession("vtPilaProducto", null);
			}
			usoPostConstructor = false;
			vtPilaProducto = null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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

	public List<SelectItem> getEsPilaProductoItems() {

		try {
			if (esPilaProductoItems == null) {
				List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
				esPilaProductoItems = new ArrayList<SelectItem>();
				for (VtProyecto vtProyecto : listaProyectos) {
					if (vtProyecto.getActivo().equalsIgnoreCase("S")) {
						esPilaProductoItems.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
					}
				}
			}

		} catch (Exception e) {
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
		try {
			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			if (losProyectosFiltro == null) {
				List<VtProyectoUsuario> listaProyectos = businessDelegatorView
						.consultarProyectoUsuario(vtUsuario.getUsuaCodigo());
				losProyectosFiltro = new ArrayList<SelectItem>();
				for (VtProyectoUsuario vtProyectoUsuario : listaProyectos) {
					losProyectosFiltro.add(new SelectItem(vtProyectoUsuario.getVtProyecto().getProyCodigo(),
							vtProyectoUsuario.getVtProyecto().getNombre()));
				}
			}

		} catch (Exception e) {
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
		FacesContext.getCurrentInstance().addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaInicio() {
		Date inputDate = (Date) txtFechaInicio.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance().addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaFinM() {
		Date inputDate = (Date) txtFechaFinM.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance().addMessage("",
				new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
	}

	public void listener_txtFechaInicioM() {
		Date inputDate = (Date) txtFechaInicioM.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance().addMessage("",
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
	public String crearSprint() {
		log.info("Guardando..");
		String esfuerzoEstimado;
		try {
			VtSprint vtSprint = new VtSprint();

			vtSprint.setActivo("S");

			VtPilaProducto vtPilaProducto;
			String longPila = somPilaProducto.getValue().toString().trim();
			Long codigoPila = Long.valueOf(longPila);
			vtPilaProducto = businessDelegatorView.getVtPilaProducto(codigoPila);

			vtSprint.setVtPilaProducto(vtPilaProducto);

			Date fechaCreacion = new Date();
			vtSprint.setFechaCreacion(fechaCreacion);
			vtSprint.setFechaFin(FacesUtils.checkDate(txtFechaFin));
			vtSprint.setFechaInicio(FacesUtils.checkDate(txtFechaInicio));

			vtSprint.setNombre(txtNombreCrear.getValue().toString().trim());
			vtSprint.setObjetivo(txtObjetivoCrear.getValue().toString().trim());

			VtEstadoSprint vtEstadoSprint = businessDelegatorView
					.getVtEstadoSprint(Long.parseLong(somEstadosSprint.getValue().toString().trim()));
			vtSprint.setVtEstadoSprint(vtEstadoSprint);

			esfuerzoEstimado = txtEsfuerzoCrear.getValue().toString().trim();

			businessDelegatorView.saveVtSprint(vtSprint, esfuerzoEstimado);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El sprint se creó con exito"));
			dataFiltro = businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
			dataFiltroI = businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);
			limpiar();
			codigoSprint = vtSprint.getSpriCodigo();
			// TODO:AQUI
			pickList.setDisabled(false);
			actualizarListaUsuarios();
			createMeterGaugeModels();
			calcularEsfuerzo();
		} catch (Exception e) {
			log.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!" + "\n" + e.getMessage(), "Contacto admin"));
		}

		return "";
	}


	public String modificar(ActionEvent evt) {
		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes().get("selectedVtSprint"));

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

	public String limpiar() {
		log.info("Limpiando campos de texto..");

		txtNombreCrear.resetValue();
		txtObjetivoCrear.resetValue();
		txtFechaFin.setValue(null);
		txtEsfuerzoCrear.resetValue();
		somEstadosSprint.setValue("-1");
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

	public String action_modify() {
		try {
			Long spriCodigo = null;
			if (entity == null) {
				spriCodigo = new Long(selectedVtSprint.getSpriCodigo());
				entity = businessDelegatorView.getVtSprint(spriCodigo);
			}

			if (!txtFechaFinM.toString().isEmpty())
				entity.setFechaFin(FacesUtils.checkDate(txtFechaFinM));
			if (!txtFechaInicioM.toString().isEmpty())
				entity.setFechaInicio(FacesUtils.checkDate(txtFechaInicioM));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			entity.setObjetivo(FacesUtils.checkString(txtObjetivo));
			entity.setActivo(entity.getActivo());
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);
			entity.setCapacidadEstimada(Integer.parseInt(txtEsfuerzo.getValue().toString().trim()));

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			businessDelegatorView.updateVtSprint(entity);

			String pila = somPilaProducto.getValue().toString().trim();
			pilaCodigo = Long.valueOf(pila);
			dataFiltro = businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
			dataFiltroI = businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);

			createMeterGaugeModels();
			FacesUtils.addInfoMessage("El Sprint ha sido modificado con exito");

		} catch (Exception e) {
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

	public String filtrarEmpresa() {
		try {
			VtEmpresa vtEmpresa = null;
			losProyectosFiltro = null;
			lasPilasDeProductoFiltro = null;
			String empresaS = somEmpresas.getValue().toString().trim();
				
			if(!usoPostConstructor){
				somProyectos.setValue("-1");	
				somPilaProducto.setValue("-1");
				dataFiltro = null;
				dataFiltroI = null;
				btnCrearS.setDisabled(true);	
			}
	
			
			Long empresa = Long.parseLong(empresaS);
			vtEmpresa = businessDelegatorView.getVtEmpresa(empresa);

			try {
				if (losProyectosFiltro == null) {
					List<VtProyecto> listaProyectos = businessDelegatorView.getVtProyecto();
					losProyectosFiltro = new ArrayList<SelectItem>();
					for (VtProyecto vtProyecto : listaProyectos) {
						if (vtProyecto.getActivo().equalsIgnoreCase("S")
								&& vtProyecto.getVtEmpresa().getEmprCodigo().equals(vtEmpresa.getEmprCodigo())) {
							losProyectosFiltro.add(new SelectItem(vtProyecto.getProyCodigo(), vtProyecto.getNombre()));
						} else {

						}
					}
				}

			} catch (Exception e) {
				log.error(e.getMessage());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String filtrarProyecto() {
		String proyectoS = somProyectos.getValue().toString().trim();

		if (!proyectoS.equals("-1")) {
			try {
				VtProyecto vtProyecto = businessDelegatorView.getVtProyecto(Long.parseLong(proyectoS));
				lasPilasDeProductoFiltro = null;
				
				if(!usoPostConstructor){
					somPilaProducto.setValue("-1");
					dataFiltro = null;
					dataFiltroI = null;
					btnCrearS.setDisabled(true);
				}
			
				try {
					if (lasPilasDeProductoFiltro == null) {
						List<VtPilaProducto> listaPilasDeProducto = businessDelegatorView.getVtPilaProducto();
						lasPilasDeProductoFiltro = new ArrayList<SelectItem>();
						for (VtPilaProducto vtPilaProducto : listaPilasDeProducto) {
							if (vtPilaProducto.getActivo().equalsIgnoreCase("S") && vtPilaProducto.getVtProyecto()
									.getProyCodigo().equals(vtProyecto.getProyCodigo())) {
								lasPilasDeProductoFiltro.add(
										new SelectItem(vtPilaProducto.getPilaCodigo(), vtPilaProducto.getNombre()));
							}

						}
					}

				} catch (Exception e) {
					log.error(e.getMessage());

				}

			} catch (Exception e) {
				log.error(e.getMessage());

			}
		}else{
			if(!usoPostConstructor){
				somPilaProducto.setValue("-1");
				dataFiltro = null;
				dataFiltroI = null;
				btnCrearS.setDisabled(true);
			}
		}
		
		

		return "";
	}

	public String filtrar() {
		
		String pila = somPilaProducto.getValue().toString().trim();
		if (!pila.equals("-1")) {
			try {
				pilaCodigo = Long.valueOf(pila);
				dataFiltro = businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
				dataFiltroI = businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);
				btnCrearS.setDisabled(false);
				pnlToogle.setVisible(true);				
			} catch (Exception e) {
				log.error("Error al filtrar" ,e);
			}
		}else{
			dataFiltro = null;
			dataFiltroI = null;
			btnCrearS.setDisabled(true);
		}
		return "";
	}

	public void actualizarListaUsuarios() throws Exception {

		try {
			Long idSprint = codigoSprint;
			vtSprint = businessDelegatorView.getVtSprint(idSprint);

			artefactosSource = businessDelegatorView.consultarArtefactosSinAsignarASprint(vtSprint.getVtPilaProducto().getVtProyecto().getProyCodigo());
			artefactosTarget = businessDelegatorView.consultarArtefactosAsignadosASprint(idSprint);

			vtArtefacto.setSource(artefactosSource);
			vtArtefacto.setTarget(artefactosTarget);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public String cambiarEstado(ActionEvent evt) {

		selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes().get("selectedVtSprint"));

		try {
			Long spriCodigo = null;
			if (entity == null) {
				spriCodigo = new Long(selectedVtSprint.getSpriCodigo());
				entity = businessDelegatorView.getVtSprint(spriCodigo);
			}

			dataActivo = null;
			dataActivo = businessDelegatorView.getDataVtArtefactoActivo(spriCodigo);



			String cambioActivo = entity.getActivo().toString().trim();
			if (cambioActivo.equalsIgnoreCase("S")) {
				entity.setActivo("N");
				List<VtArtefacto> listaArtefactos=businessDelegatorView.getVtArtefacto();
				for(VtArtefacto vtArtefacto:listaArtefactos){
					if(vtArtefacto.getVtEstado().getEstaCodigo()!=6 
							&&  vtArtefacto.getVtSprint()!=null){
						if(vtArtefacto.getVtSprint().getSpriCodigo().equals(entity.getSpriCodigo())){
							VtEstado vtEstado=businessDelegatorView.getVtEstado(1L);
							vtArtefacto.setVtEstado(vtEstado);
							vtArtefacto.setVtSprint(null);
							Date fechaModificacion = new Date();
							vtArtefacto.setFechaModificacion(fechaModificacion);
							VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
							entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
							vtArtefacto.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
							businessDelegatorView.updateVtArtefacto(vtArtefacto);
						}
					}
				}
			} else {
				entity.setActivo("S");
			}

			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			businessDelegatorView.updateVtSprint(entity);
			if(entity.getActivo().equals("S")){
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Ok,!El sprint se ha activado con éxito!"));
			}else if(entity.getActivo().equals("N")){
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Ok,!El sprint se ha inactivado con éxito!"));
			}

			dataFiltro = businessDelegatorView.getDataVtSprintFiltro(pilaCodigo);
			dataFiltroI = businessDelegatorView.getDataVtSprintFiltroI(pilaCodigo);

			selectedVtSprint = null;
			entity = null;


		} catch (Exception e) {
			data = null;
			log.error(e.toString());
			e.printStackTrace();
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String redireccionarAArtefactos(ActionEvent evt) {
		try {
			selectedVtSprint = (VtSprintDTO) (evt.getComponent().getAttributes().get("selectedVtSprint"));
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

	public void onClose(CloseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed",
				"Closed panel id:'" + event.getComponent().getId() + "'");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onToggle(ToggleEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled",
				"Status:" + event.getVisibility().name());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled",
				"Visibility:" + event.getVisibility());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onTransfer(TransferEvent event) throws Exception {
		try {
			StringBuilder builder = new StringBuilder();
			Long idSprint = codigoSprint;
			VtSprint vtSprint = businessDelegatorView.getVtSprint(idSprint);
			VtPilaProducto vtPilaProducto = businessDelegatorView
					.getVtPilaProducto(vtSprint.getVtPilaProducto().getPilaCodigo());
			String mensaje = "";
			for (Object item : event.getItems()) {
				VtArtefacto vtArtefacto = (VtArtefacto) item;

				builder.append(((VtArtefacto) item).getTitulo()).append("<br />");
				if (event.isAdd()) {
					asignarArtefactoASprint(vtArtefacto, vtSprint, vtPilaProducto);
					mensaje = "Artefacto(s) asignado(s)";
					calcularEsfuerzo();
				}
				if (event.isRemove()) {
					removerArtefactoDelSprint(vtArtefacto, vtSprint, vtPilaProducto);
					mensaje = "Artefacto(s) retirado(s)";
					calcularEsfuerzo();
				}
			}
			FacesUtils.addInfoMessage("" + mensaje);
		} catch (Exception e) {
			FacesUtils.addErrorMessage("No se pudo realizar la transferencia");
		}

	}

	public void asignarArtefactoASprint(VtArtefacto vtArtefacto, VtSprint vtSprint, VtPilaProducto vtPilaProducto) {
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			vtArtefacto = (VtArtefacto) businessDelegatorView.consultarArtefactosAsignadosASprintYPila(
					vtArtefacto.getArteCodigo(), vtPilaProducto.getPilaCodigo());
			if (vtArtefacto != null) {
				vtArtefacto.setVtSprint(vtSprint);
				vtArtefacto.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				vtArtefacto.setFechaModificacion(new Date());
				vtArtefacto.setActivo("S");
				businessDelegatorView.updateVtArtefacto(vtArtefacto);

			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void removerArtefactoDelSprint(VtArtefacto vtArtefacto, VtSprint vtSprint, VtPilaProducto vtPilaProducto) {
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			vtArtefacto = (VtArtefacto) businessDelegatorView.consultarArtefactosAsignadosASprintYPila(
					vtArtefacto.getArteCodigo(), vtPilaProducto.getPilaCodigo());
			vtArtefacto.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			vtArtefacto.setFechaModificacion(new Date());
			vtArtefacto.setActivo("N");
			vtArtefacto.setVtSprint(null);
			businessDelegatorView.updateVtArtefacto(vtArtefacto);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@SuppressWarnings("serial")
	private MeterGaugeChartModel initMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>() {
			{
				if (vtSprint == null) {
					add(100);
					add(200);
					add(300);
				} else {
					int valores = vtSprint.getCapacidadEstimada();
					add(valores / 2);
					add(valores);
					add(valores * 2);
				}
			}
		};

		return new MeterGaugeChartModel(140, intervals);
	}

	private void createMeterGaugeModels() {
		try {
			meterGaugeModel = initMeterGaugeModel();
			meterGaugeModel.setTitle("Capacidad Sprint");
			meterGaugeModel.setSeriesColors("66cc66,E7E658,cc6666");
			meterGaugeModel.setGaugeLabel("Esfuerzo");
			meterGaugeModel.setGaugeLabelPosition("bottom");
			meterGaugeModel.setShowTickLabels(true);
			meterGaugeModel.setLabelHeightAdjust(110);
			meterGaugeModel.setIntervalOuterRadius(150);
			meterGaugeModel.setValue(0);
			calcularEsfuerzo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void iniciarMeterGaugeModels() {
		try {
			meterGaugeModel = initMeterGaugeModel();
			meterGaugeModel.setTitle("Capacidad Sprint");
			meterGaugeModel.setSeriesColors("66cc66,E7E658,cc6666");
			meterGaugeModel.setShowTickLabels(true);
			meterGaugeModel.setLabelHeightAdjust(110);
			meterGaugeModel.setIntervalOuterRadius(150);
			meterGaugeModel.setValue(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String convertirMinutosAHorasYMinutos(double esfuerzo) {
		double tiempoHoras = esfuerzo / 60;
		String timeHour = "" + tiempoHoras;
		return timeHour;
	}

	public void calcularEsfuerzo() {
		double esfuerzo = 0;
		double totalEsf = 0;
		try {
			List<VtArtefacto> listaArtefactos = businessDelegatorView.consultarTodosLosArtefactosAsignados();
			for (VtArtefacto vtArtefacto : listaArtefactos) {
				if (vtArtefacto.getVtSprint().getSpriCodigo().equals(vtSprint.getSpriCodigo())) {
					log.info("Artefacto: " + vtArtefacto.getTitulo());
					esfuerzo = esfuerzo + vtArtefacto.getEsfuerzoEstimado();
				}
			}
			totalEsf = Double.parseDouble(convertirMinutosAHorasYMinutos(esfuerzo));
			meterGaugeModel.setValue(totalEsf);
		} catch (Exception e) {
			log.error("Falla al calcular el esfuerzo estimado", e);
		}

	}

	public void txtEsfuerzoListener() {

	}

	public SelectOneMenu getSomEstadosSprint() {
		return somEstadosSprint;
	}

	public void setSomEstadosSprint(SelectOneMenu somEstadosSprint) {
		this.somEstadosSprint = somEstadosSprint;
	}

	public List<SelectItem> getLosEstadosSprintsItems() {
		try {
			if (losEstadosSprintsItems == null) {
				List<VtEstadoSprint> listaEstadosSprint = businessDelegatorView.getVtEstadoSprint();
				losEstadosSprintsItems = new ArrayList<SelectItem>();
				for (VtEstadoSprint vtEstadoSprint : listaEstadosSprint) {
					losEstadosSprintsItems
					.add(new SelectItem(vtEstadoSprint.getEstsprCodigo(), vtEstadoSprint.getNombre()));
				}

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losEstadosSprintsItems;
	}

	public void setLosEstadosSprintsItems(List<SelectItem> losEstadosSprintsItems) {
		this.losEstadosSprintsItems = losEstadosSprintsItems;
	}

	public boolean isUsoPostConstructor() {
		return usoPostConstructor;
	}

	public void setUsoPostConstructor(boolean usoPostConstructor) {
		this.usoPostConstructor = usoPostConstructor;
	}

}