package com.vobi.team.presentation.backingBeans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtArchivo;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtEstado;
import com.vobi.team.modelo.VtHistoriaArtefacto;
import com.vobi.team.modelo.VtInteres;
import com.vobi.team.modelo.VtPilaProducto;
import com.vobi.team.modelo.VtPrioridad;
import com.vobi.team.modelo.VtProgresoArtefacto;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtProyectoUsuario;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtTipoArtefacto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioArtefacto;
import com.vobi.team.modelo.dto.VtArchivoDTO;
import com.vobi.team.modelo.dto.VtArtefactoDTO;
import com.vobi.team.modelo.dto.VtHistoriaArtefactoDTO;
import com.vobi.team.modelo.dto.VtProgresoArtefactoDTO;
import com.vobi.team.modelo.dto.VtSprintDTO;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;
import com.vobi.team.utilities.Utilities;

@ManagedBean
@ViewScoped
public class VtArtefactoViewDesarrollador implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtArtefactoViewDesarrollador.class);

	private SelectOneMenu somActivo;
	private SelectOneMenu somEstados;
	private SelectOneMenu somEstadosP;
	private SelectOneMenu somPrioridades;
	private SelectOneMenu somTiposDeArtefactos;
	private SelectOneMenu somSprintsCrear;
	private SelectOneMenu somPilaProductoCrear;
	private SelectOneMenu somSprints;
	private SelectOneMenu somPilaProducto;
	private SelectOneMenu somUsuariosArtefactos;
	private SelectOneMenu somEmpresas;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somTiposDeArtefactosCambio;
	private SelectOneMenu somPilaProductoCambio;
	private SelectOneMenu somSprintsCambio;
	private SelectOneMenu somActivoCambio;
	private SelectOneMenu somEstadosCambio;
	private SelectOneMenu somPrioridadesCambio;

	private List<SelectItem> esUsuarioArtefactoItems;
	private List<SelectItem> esPilaProductoItems;
	private List<SelectItem> esActivoItems;
	private List<SelectItem> esPrioridadItems;
	private List<SelectItem> esTipoArtefactoItems;
	private List<SelectItem> esEstadoItems;
	private List<SelectItem> esEstadoItemsP;
	private List<SelectItem> esSprintItems;
	private List<SelectItem> lasEmpresasItems;
	private List<SelectItem> losProyectosFiltro;
	private List<SelectItem> lasPilasDeProductoFiltro;
	private List<SelectItem> losSprintsFiltro;

	private List<VtArtefactoDTO> data;
	private List<VtArtefactoDTO> dataFiltro;
	private VtArchivoDTO selectedVtAchivo;

	private List<VtArtefactoDTO> dataFiltroI;
	private List<VtSprintDTO> dataSprint;
	private List<VtArchivoDTO> dataFiltroArchivo;
	private List<VtArchivoDTO> dataFiltroArchivoInactivo;
	private List<VtHistoriaArtefactoDTO> dataFiltroHistoriaArtefacto;
	private VtArtefactoDTO selectedVtArtefacto;
	private VtProgresoArtefactoDTO selectedVtProgreso;
	private VtHistoriaArtefactoDTO selectedVtHistoriaArtefacto;

	private InputTextarea txtdescripcion;
	private InputTextarea txtdescripcionCambio;

	private InputMask txtEsfuerzoEstimado;
	private InputMask txtEsfuerzoEstimadoCambio;
	private InputMask txtEsfuerzoRestante;
	private InputMask txtEsfuerzoRestanteCambio;
	private InputMask txtEsfuerzoReal;

	private InputMask txtEsfuerzoRealCambio;
	private InputMask txtPuntos;
	private InputMask txtPuntosCambio;

	private InputText txtnombre;
	private InputText txtnombreCambio;

	private InputText txtOrigen;
	private InputText txtOrigenCambio;
	private InputText txtEsfuerzoEstimadoP;
	private InputText txtEsfuerzoRestanteP;
	private InputText txtEsfuerzoRealP;
	private InputText txtTiempoDedicadoP;
	private InputTextarea txtDescripcionP;

	private Calendar txtFechaFin;
	private Calendar txtFechaInicio;

	private CommandButton btnCrearS;
	private CommandButton btnCrear;
	private CommandButton btnLimpiarS;
	private CommandButton btnCrearP;
	private CommandButton btnLimpiarP;
	private CommandButton btnGuardar;
	private CommandButton btnDescargar;
	private CommandButton btnLimpiar;
	private CommandButton btnCrearArtefactoFiltrado;
	private CommandButton btnFiltrar;
	private CommandButton btnProgreso;

	private Long spriCodigo = null;

	private VtArtefacto entity;

	private VtArchivo archivoEntity;

	private VtArtefacto artefactoSubirArchivo;

	private boolean showDialog;
	private boolean showDialogArchivos;
	private boolean showDialogHistorial;
	private boolean showDialogSubirArchivo;
	private boolean showDialogProgreso;
	private boolean restablecioVersion = false;

	VtUsuario vtUsuario = null;

	public VtProgresoArtefactoDTO getSelectedVtProgreso() {
		return selectedVtProgreso;
	}

	public void setSelectedVtProgreso(VtProgresoArtefactoDTO selectedVtProgreso) {
		this.selectedVtProgreso = selectedVtProgreso;
	}

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;
	
	public VtArtefactoViewDesarrollador() {
		super();
		somEmpresas = new SelectOneMenu();
		somProyectos = new SelectOneMenu();
		somPilaProducto = new SelectOneMenu();
		somSprints = new SelectOneMenu();
		btnCrearArtefactoFiltrado = new CommandButton();
		btnCrearArtefactoFiltrado.setDisabled(true);
	}

	@PostConstruct
	public void vtArtefactoViewPostConstructor() {
		try {

			VtSprint vtSprint = (VtSprint) FacesUtils.getfromSession("vtSprint");

			if (vtSprint != null) {
				btnCrearArtefactoFiltrado.setDisabled(false);
				VtPilaProducto vtPilaProducto = vtSprint.getVtPilaProducto();
				VtProyecto vtProyecto = vtPilaProducto.getVtProyecto();
				
				somProyectos.setValue(vtProyecto.getProyCodigo());
				filtrarProyecto();
				somPilaProducto.setValue(vtPilaProducto.getPilaCodigo());
				imprimirValue();
				somSprints.setValue(vtSprint.getSpriCodigo());
				vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
				filtrar();
				dataFiltro = businessDelegatorView.getDataVtArtefactoFiltroDesarrollador(
						vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
				dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroIDesarrollador(
						vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
				FacesUtils.putinSession("vtSprint", null);
			} else {
				dataFiltro = null;
				dataFiltroI = null;
			}
			vtSprint = null;
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public InputText getTxtEsfuerzoEstimadoP() {
		return txtEsfuerzoEstimadoP;
	}

	public void setTxtEsfuerzoEstimadoP(InputText txtEsfuerzoEstimadoP) {
		this.txtEsfuerzoEstimadoP = txtEsfuerzoEstimadoP;
	}

	public InputText getTxtEsfuerzoRestanteP() {
		return txtEsfuerzoRestanteP;
	}

	public boolean isShowDialogProgreso() {
		return showDialogProgreso;
	}

	public void setShowDialogProgreso(boolean showDialogProgreso) {
		this.showDialogProgreso = showDialogProgreso;
	}

	public void setTxtEsfuerzoRestanteP(InputText txtEsfuerzoRestanteP) {
		this.txtEsfuerzoRestanteP = txtEsfuerzoRestanteP;
	}

	public InputText getTxtEsfuerzoRealP() {
		return txtEsfuerzoRealP;
	}

	public void setTxtEsfuerzoRealP(InputText txtEsfuerzoRealP) {
		this.txtEsfuerzoRealP = txtEsfuerzoRealP;
	}

	public InputText getTxtTiempoDedicadoP() {
		return txtTiempoDedicadoP;
	}

	public void setTxtTiempoDedicadoP(InputText txtTiempoDedicadoP) {
		this.txtTiempoDedicadoP = txtTiempoDedicadoP;
	}

	public InputTextarea getTxtDescripcionP() {
		return txtDescripcionP;
	}

	public void setTxtDescripcionP(InputTextarea txtDescripcionP) {
		this.txtDescripcionP = txtDescripcionP;
	}

	public CommandButton getBtnCrearP() {
		return btnCrearP;
	}

	public void setBtnCrearP(CommandButton btnCrearP) {
		this.btnCrearP = btnCrearP;
	}

	public CommandButton getBtnLimpiarP() {
		return btnLimpiarP;
	}

	public void setBtnLimpiarP(CommandButton btnLimpiarP) {
		this.btnLimpiarP = btnLimpiarP;
	}

	public CommandButton getBtnProgreso() {
		return btnProgreso;
	}

	public void setBtnProgreso(CommandButton btnProgreso) {
		this.btnProgreso = btnProgreso;
	}

	public String crearArtefacto() {
		String esfuerzoEstimado, esfuerzoRestante, puntos;
		log.info("Creando artefacto");

		try {

			entity = new VtArtefacto();
			entity.setDescripcion(txtdescripcion.getValue().toString().trim());
			entity.setTitulo(txtnombre.getValue().toString().trim());
			Date fechaCreacion = new Date();
			entity.setFechaCreacion(fechaCreacion);
			esfuerzoEstimado = txtEsfuerzoEstimado.getValue().toString().trim();
			esfuerzoRestante = txtEsfuerzoRestante.getValue().toString().trim();
			puntos = (txtPuntos.getValue().toString().trim());
			entity.setOrigen(txtOrigen.getValue().toString().trim());

			String pilasProducto = somPilaProducto.getValue().toString().trim();
			Long idPilaProducto = Long.parseLong(pilasProducto);
			VtPilaProducto vtPilaProducto = businessDelegatorView.getVtPilaProducto(idPilaProducto);
			entity.setVtPilaProducto(vtPilaProducto);

			Long idTipoArtefacto = 200L;
			VtTipoArtefacto vtTipoArtefacto = businessDelegatorView.getVtTipoArtefacto(idTipoArtefacto);
			entity.setVtTipoArtefacto(vtTipoArtefacto);

			String estados = somEstados.getValue().toString().trim();
			Long estadoArtefacto = Long.parseLong(estados);
			VtEstado vtEstado = businessDelegatorView.getVtEstado(estadoArtefacto);
			entity.setVtEstado(vtEstado);

			Long idSprint = Long.parseLong(somSprints.getValue().toString().trim());
			VtSprint vtSprint = businessDelegatorView.getVtSprint(idSprint);
			entity.setVtSprint(vtSprint);

			String tipoPri = somPrioridades.getValue().toString().trim();
			Long tipoPrioridad = Long.parseLong(tipoPri);
			VtPrioridad vtPrioridad = businessDelegatorView.getVtPrioridad(tipoPrioridad);
			entity.setVtPrioridad(vtPrioridad);

			VtUsuario vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuCreador(vtUsuario.getUsuaCodigo());

			String activo = somActivo.getValue().toString().trim();
			if (activo.equalsIgnoreCase("Si")) {
				entity.setActivo("S");
			} else {
				entity.setActivo("N");
			}
			businessDelegatorView.saveVtArtefacto(entity, esfuerzoEstimado, esfuerzoRestante, puntos);
			FacesUtils.addInfoMessage("Se ha creado el artefacto con éxito");
			
			dataFiltro = businessDelegatorView.getDataVtArtefactoFiltro(vtSprint.getSpriCodigo());
			dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroI(vtSprint.getSpriCodigo());

			VtUsuarioArtefacto vtUsuarioArtefacto = new VtUsuarioArtefacto();
			vtUsuarioArtefacto.setActivo("S");
			vtUsuarioArtefacto.setFechaCreacion(new Date());
			vtUsuarioArtefacto.setUsuCreador(vtUsuario.getUsuaCodigo());
			vtUsuarioArtefacto.setVtArtefacto(entity);
			vtUsuarioArtefacto.setVtUsuario(vtUsuario);

			VtInteres vtInteres = businessDelegatorView.getVtInteres(200L);
			vtUsuarioArtefacto.setVtInteres(vtInteres);
			businessDelegatorView.saveVtUsuarioArtefacto(vtUsuarioArtefacto);

			VtHistoriaArtefacto vtHistoriaArtefacto = new VtHistoriaArtefacto();
			vtHistoriaArtefacto.setActivo(entity.getActivo());
			vtHistoriaArtefacto.setEstaCodigo(entity.getVtEstado().getEstaCodigo());
			vtHistoriaArtefacto.setTparCodigo(entity.getVtTipoArtefacto().getTparCodigo());
			vtHistoriaArtefacto.setPilaCodigo(entity.getVtPilaProducto().getPilaCodigo());
			vtHistoriaArtefacto.setPrioCodigo(entity.getVtPrioridad().getPrioCodigo());
			vtHistoriaArtefacto.setEsfuerzoEstimado(entity.getEsfuerzoEstimado());
			vtHistoriaArtefacto.setFechaCreacion(entity.getFechaCreacion());
			vtHistoriaArtefacto.setFechaModificacion(entity.getFechaModificacion());
			vtHistoriaArtefacto.setOrigen(entity.getOrigen());
			vtHistoriaArtefacto.setDescripcion(entity.getDescripcion());
			vtHistoriaArtefacto.setPuntos(entity.getPuntos());
			vtHistoriaArtefacto.setTitulo(entity.getTitulo());
			vtHistoriaArtefacto.setUsuCreador(entity.getUsuCreador());
			vtHistoriaArtefacto.setUsuModificador(entity.getUsuModificador());
			vtHistoriaArtefacto.setVtArtefacto(entity);
			businessDelegatorView.saveVtHistoriaArtefacto(vtHistoriaArtefacto);
			FacesUtils.addInfoMessage("Se ha creado el historial del artefacto con éxito");
			
			
			
			vtHistoriaArtefacto = null;
			limpiar();
			action_closeDialog();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return "";
	}

	public void asignarDatosACampos() {
		try {
			String esfuerzo = txtEsfuerzoEstimado.getValue().toString().trim();
			txtEsfuerzoRestante.setValue(esfuerzo);
			txtPuntos.setValue(esfuerzo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String handleFileUpload(FileUploadEvent event) throws IOException {
		log.info("Entrando para subir archivos");
		try {

			VtUsuario vtUsuarioSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			VtArchivo vtArchivo = null;
			if (entity != null) {
				vtArchivo = new VtArchivo();
				vtArchivo.setNombre(event.getFile().getFileName());
				vtArchivo.setFechaCreacion(new Date());
				vtArchivo.setFechaModificacion(new Date());
				vtArchivo.setUsuCreador(vtUsuarioSession.getUsuaCodigo());
				vtArchivo.setUsuModificador(vtUsuarioSession.getUsuaCodigo());
				vtArchivo.setActivo("S");
				vtArchivo.setArchivo(event.getFile().getContents());
				vtArchivo.setVtArtefacto(entity);
			} else {
				if (restablecioVersion == false && artefactoSubirArchivo != null) {
					vtArchivo = new VtArchivo();
					vtArchivo.setNombre(event.getFile().getFileName());
					vtArchivo.setFechaCreacion(new Date());
					vtArchivo.setFechaModificacion(new Date());
					vtArchivo.setUsuCreador(vtUsuarioSession.getUsuaCodigo());
					vtArchivo.setUsuModificador(vtUsuarioSession.getUsuaCodigo());
					vtArchivo.setActivo("S");
					vtArchivo.setArchivo(event.getFile().getContents());
					vtArchivo.setVtArtefacto(artefactoSubirArchivo);
				}

			}

			businessDelegatorView.saveVtArchivo(vtArchivo);
			FacesUtils.addInfoMessage("Ok", "Fichero " + event.getFile().getFileName() + " subido correctamente.");

		} catch (Exception e) {
			log.info(e.getMessage());
			FacesUtils.addInfoMessage(e.getMessage());
		}
		return "";
	}

	public void fileDownloadView(ActionEvent evt) {

		try {
			selectedVtAchivo = ((VtArchivoDTO) (evt.getComponent().getAttributes().get("selectedVtAchivo")));
			Long archivoId = Long.parseLong(selectedVtAchivo.getArchCodigo().toString());
			VtArchivo vtArchivo = businessDelegatorView.getVtArchivo(archivoId.longValue());

			if (vtArchivo != null) {
				byte[] archivo = vtArchivo.getArchivo();
				InputStream inputStream = new ByteArrayInputStream(archivo);
				archivos = new DefaultStreamedContent(inputStream, null, vtArchivo.getNombre());
				FacesUtils.addInfoMessage("Archivo descargado correctamente");
			}

		} catch (Exception e) {
			FacesUtils.addInfoMessage("Falla al descargar el archivo");
		}
	}

	public void deleteFileView(ActionEvent evt) {
		try {
			selectedVtAchivo = ((VtArchivoDTO) (evt.getComponent().getAttributes().get("selectedVtAchivo")));
			Long archivoId = new Long(selectedVtAchivo.getArchCodigo());
			VtArchivo vtArchivo = businessDelegatorView.getVtArchivo(archivoId);
			businessDelegatorView.deleteVtArchivo(vtArchivo);
			dataFiltroArchivo = businessDelegatorView.getDataVtArchivoActivo(selectedVtArtefacto.getArteCodigo());
			dataFiltroArchivoInactivo = (businessDelegatorView
					.getDataVtArchivoInactivo(artefactoSubirArchivo.getArteCodigo().longValue()));
			FacesUtils.addInfoMessage(
					"Ok, el fichero " + "'" + vtArchivo.getNombre() + "'" + " ha sido eliminado con éxito");
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public void restablecerArtefactoView(ActionEvent evt) {
		try {
			selectedVtHistoriaArtefacto = ((VtHistoriaArtefactoDTO) (evt.getComponent().getAttributes()
					.get("selectedVtHistoriaArtefacto")));
			VtArtefacto vtArtefacto = businessDelegatorView
					.getVtArtefacto(selectedVtHistoriaArtefacto.getArteCodigo_VtArtefacto().longValue());
			VtPrioridad prioridadArtefacto = businessDelegatorView
					.getVtPrioridad(selectedVtHistoriaArtefacto.getPrioCodigo());
			VtEstado estadoArtefacto = businessDelegatorView.getVtEstado(selectedVtHistoriaArtefacto.getEstaCodigo());
			VtSprint sprintArtefacto = businessDelegatorView.getVtSprint(selectedVtHistoriaArtefacto.getSpriCodigo());

			vtArtefacto.setDescripcion(selectedVtHistoriaArtefacto.getDescripcion());
			vtArtefacto.setEsfuerzoEstimado(selectedVtHistoriaArtefacto.getEsfuerzoEstimado());
			vtArtefacto.setEsfuerzoReal(selectedVtHistoriaArtefacto.getEsfuerzoReal());
			vtArtefacto.setEsfuerzoRestante(selectedVtHistoriaArtefacto.getEsfuerzoRestante());
			vtArtefacto.setFechaCreacion(selectedVtHistoriaArtefacto.getFechaCreacion());
			vtArtefacto.setFechaModificacion(selectedVtHistoriaArtefacto.getFechaModificacion());
			vtArtefacto.setUsuCreador(selectedVtHistoriaArtefacto.getUsuCreador().longValue());
			vtArtefacto.setUsuModificador(selectedVtHistoriaArtefacto.getUsuModificador().longValue());
			vtArtefacto.setOrigen(selectedVtHistoriaArtefacto.getOrigen());
			vtArtefacto.setPuntos(selectedVtHistoriaArtefacto.getPuntos());
			vtArtefacto.setTitulo(selectedVtHistoriaArtefacto.getTitulo());
			vtArtefacto.setVtEstado(estadoArtefacto);
			vtArtefacto.setVtPrioridad(prioridadArtefacto);
			vtArtefacto.setVtSprint(sprintArtefacto);
			vtArtefacto.setActivo(selectedVtHistoriaArtefacto.getActivo());
			vtArtefacto.setVtArchivos(vtArtefacto.getVtArchivos());
			businessDelegatorView.updateVtArtefacto(vtArtefacto);

			VtHistoriaArtefacto vtHistoriaArtefacto = new VtHistoriaArtefacto();
			vtHistoriaArtefacto.setActivo(entity.getActivo());
			vtHistoriaArtefacto.setEstaCodigo(entity.getVtEstado().getEstaCodigo());
			vtHistoriaArtefacto.setTparCodigo(entity.getVtTipoArtefacto().getTparCodigo());
			vtHistoriaArtefacto.setPilaCodigo(entity.getVtPilaProducto().getPilaCodigo());
			vtHistoriaArtefacto.setPrioCodigo(entity.getVtPrioridad().getPrioCodigo());
			vtHistoriaArtefacto.setSpriCodigo(entity.getVtSprint().getSpriCodigo());
			vtHistoriaArtefacto.setEsfuerzoEstimado(vtArtefacto.getEsfuerzoEstimado());
			vtHistoriaArtefacto.setEsfuerzoReal(vtArtefacto.getEsfuerzoReal());
			vtHistoriaArtefacto.setEsfuerzoRestante(vtArtefacto.getEsfuerzoRestante());
			vtHistoriaArtefacto.setFechaCreacion(vtArtefacto.getFechaCreacion());
			vtHistoriaArtefacto.setFechaModificacion(vtArtefacto.getFechaModificacion());
			vtHistoriaArtefacto.setOrigen(vtArtefacto.getOrigen());
			vtHistoriaArtefacto.setDescripcion(vtArtefacto.getDescripcion());
			vtHistoriaArtefacto.setPuntos(vtArtefacto.getPuntos());
			vtHistoriaArtefacto.setTitulo(vtArtefacto.getTitulo());
			vtHistoriaArtefacto.setUsuCreador(vtArtefacto.getUsuCreador());
			vtHistoriaArtefacto.setUsuModificador(vtArtefacto.getUsuModificador());
			vtHistoriaArtefacto.setVtArtefacto(vtArtefacto);
			businessDelegatorView.saveVtHistoriaArtefacto(vtHistoriaArtefacto);

			dataFiltro = businessDelegatorView.getDataVtArtefactoFiltroDesarrollador(
					vtArtefacto.getVtSprint().getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
			dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroIDesarrollador(
					vtArtefacto.getVtSprint().getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
			FacesUtils.addInfoMessage("Se ha restablecido el artefacto a la versión elegida con éxito");

			restablecioVersion = true;

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public String limpiar() {
		txtdescripcion.resetValue();
		txtEsfuerzoEstimado.resetValue();
		txtnombre.resetValue();
		somActivo.resetValue();
		somEstados.resetValue();
		somPrioridades.resetValue();
		txtEsfuerzoRestante.resetValue();
		txtPuntos.resetValue();
		txtOrigen.resetValue();
		return "";
	}

	public String filtrar() {
		btnCrearArtefactoFiltrado.setDisabled(false);
		try {

			if (somSprints.equals("-1")) {
				dataFiltro = null;
				dataFiltroI = null;
			} else {
				VtSprint vtSprint = businessDelegatorView
						.getVtSprint(Long.parseLong(somSprints.getValue().toString().trim()));

				vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");

				dataFiltro = businessDelegatorView.getDataVtArtefactoFiltroDesarrollador(
						vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
				dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroIDesarrollador(
						vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String filtrarVersionHistorialView(ActionEvent evt) {

		selectedVtArtefacto = (VtArtefactoDTO) (evt.getComponent().getAttributes().get("selectedVtArtefacto"));
		setShowDialogHistorial(true);
		try {
			Long arteCodigo = new Long(selectedVtArtefacto.getArteCodigo());
			VtArtefacto vtArtefacto = businessDelegatorView.getVtArtefacto(arteCodigo);
			dataFiltroHistoriaArtefacto = businessDelegatorView
					.getDataVtHistoriaArtefactoPorIdArtefacto(vtArtefacto.getArteCodigo());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "";
	}

	public String filtrarArchivo(ActionEvent evt) {
		selectedVtArtefacto = (VtArtefactoDTO) (evt.getComponent().getAttributes().get("selectedVtArtefacto"));
		artefactoSubirArchivo = null;
		VtArtefacto vtArtefacto = null;
		setShowDialogArchivos(true);
		try {

			Long arteCodigo = new Long(selectedVtArtefacto.getArteCodigo());
			vtArtefacto = businessDelegatorView.getVtArtefacto(arteCodigo);
			artefactoSubirArchivo = vtArtefacto;
			dataFiltroArchivo = businessDelegatorView
					.getDataVtArchivoActivo(artefactoSubirArchivo.getArteCodigo().longValue());
			dataFiltroArchivoInactivo = (businessDelegatorView
					.getDataVtArchivoInactivo(artefactoSubirArchivo.getArteCodigo().longValue()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		return "";
	}

	public String cerrarDialogArchivos() {
		try {
			setShowDialogArchivos(false);
			dataFiltroArchivo = businessDelegatorView
					.getDataVtArchivoActivo(artefactoSubirArchivo.getArteCodigo().longValue());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "";
	}

	public String cerrarDialogSubirArchivos() {
		artefactoSubirArchivo = null;
		setShowDialogArchivos(false);
		return "";
	}

	public String cerrarDialogHistoriaArtefacto() {
		setShowDialogHistorial(false);
		restablecioVersion = false;
		return "";
	}

	public String cerrarDialogProgreso() {
		setShowDialogProgreso(false);
		return "";
	}

	public String limpiarP() {
		txtTiempoDedicadoP.resetValue();
		txtDescripcionP.resetValue();
		somEstadosP.resetValue();
		return "";
	}

	public String modificar(ActionEvent evt) {

		try {
			selectedVtArtefacto = null;
			selectedVtArtefacto = (VtArtefactoDTO) (evt.getComponent().getAttributes().get("selectedVtArtefacto"));
			txtdescripcionCambio.setValue(selectedVtArtefacto.getDescripcion());
			txtdescripcionCambio.setDisabled(false);
			txtnombreCambio.setValue(selectedVtArtefacto.getTitulo());
			txtnombreCambio.setDisabled(false);
			txtEsfuerzoEstimadoCambio.setValue(selectedVtArtefacto.getTiempoConvertidoEstimado());
			txtEsfuerzoEstimadoCambio.setDisabled(false);
			txtEsfuerzoRealCambio.setValue(selectedVtArtefacto.getTiempoConvertidoReal());
			txtEsfuerzoRealCambio.setDisabled(false);
			txtEsfuerzoRestanteCambio.setValue(selectedVtArtefacto.getTiempoConvertidoRestante());
			txtEsfuerzoRestanteCambio.setDisabled(false);
			txtOrigenCambio.setValue(selectedVtArtefacto.getOrigen());
			txtOrigenCambio.setDisabled(false);
			txtPuntosCambio.setValue(selectedVtArtefacto.getTiempoConvertidoPuntos());
			txtPuntosCambio.setDisabled(false);
			somTiposDeArtefactosCambio.setValue(selectedVtArtefacto.getTparCodigo_VtTipoArtefacto());
			btnGuardar.setDisabled(false);
			setShowDialog(true);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String progresoArtefacto(ActionEvent evt) {
		setShowDialogProgreso(true);
		try {
			selectedVtArtefacto = null;
			selectedVtArtefacto = (VtArtefactoDTO) evt.getComponent().getAttributes().get("selectedVtArtefacto");
			txtEsfuerzoEstimadoP.setValue(selectedVtArtefacto.getEsfuerzoEstimado());
			txtEsfuerzoRestanteP.setValue(selectedVtArtefacto.getEsfuerzoRestante());
			txtEsfuerzoRealP.setValue(selectedVtArtefacto.getEsfuerzoReal());
			somEstadosP.setValue(selectedVtArtefacto.getEstado_Nombre());

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String limpiarProgreso() {

		txtEsfuerzoEstimadoP.resetValue();
		txtEsfuerzoRestanteP.resetValue();
		txtEsfuerzoRealP.resetValue();
		somEstadosP.resetValue();
		txtTiempoDedicadoP.resetValue();
		txtDescripcionP.resetValue();

		return "";
	}

	public String crearProgeso() {
		vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");

		VtProgresoArtefacto vtProgresoArtefacto = new VtProgresoArtefacto();
		VtArtefacto vtArtefacto;
		String tiempoTrabajado;

		try {
			tiempoTrabajado = txtTiempoDedicadoP.getValue().toString().trim();
			vtArtefacto = businessDelegatorView.getVtArtefacto(selectedVtArtefacto.getArteCodigo());
			vtProgresoArtefacto.setVtArtefacto(vtArtefacto);
			vtProgresoArtefacto.setActivo("S");
			vtProgresoArtefacto.setDescripcion(txtDescripcionP.getValue().toString().trim());
			Date fechaCreacion = new Date();
			vtProgresoArtefacto.setFechaCreacion(fechaCreacion);
			vtProgresoArtefacto.setPuntos(selectedVtArtefacto.getPuntos());
			vtProgresoArtefacto.setUsuCreador(vtUsuario.getUsuaCodigo());

			vtProgresoArtefacto.setEsfuerzoReal(0);
			if (somEstadosP.getValue().toString().trim().equalsIgnoreCase("-1")) {
				throw new Exception("Seleccione el estado para el artefacto");
			}
			if (tiempoTrabajado.equalsIgnoreCase("")) {
				throw new Exception("Digite el tiempo trabajado para el artefacto");
			}

			int dedicado = Integer.parseInt(txtTiempoDedicadoP.getValue().toString().trim());
			int esfuerzoRestante = selectedVtArtefacto.getEsfuerzoRestante();
			vtProgresoArtefacto.setTiempoDedicado(Integer.parseInt(txtTiempoDedicadoP.getValue().toString().trim()));

			int esfuerzo = esfuerzoRestante - dedicado;
			double esfuerzoD = esfuerzoRestante - dedicado;

			if (esfuerzoD < 0) {
				vtArtefacto.setEsfuerzoReal((int) (vtArtefacto.getEsfuerzoEstimado() + (esfuerzoD * -1)));
			}

			vtProgresoArtefacto.setEsfuerzoRestante(esfuerzo);
			vtArtefacto.setEsfuerzoRestante(esfuerzo);
			vtArtefacto.setFechaModificacion(fechaCreacion);
			vtArtefacto.setUsuModificador(vtUsuario.getUsuaCodigo());
			String estado = somEstadosP.getValue().toString().trim();
			VtEstado vtEstado = businessDelegatorView.getVtEstado(Long.parseLong(estado));
			vtArtefacto.setVtEstado(vtEstado);

			businessDelegatorView.saveVtProgresoArtefacto(vtProgresoArtefacto);
			businessDelegatorView.updateVtArtefacto(vtArtefacto);
			setShowDialogProgreso(false);
			limpiarProgreso();

			VtSprint vtSprint = businessDelegatorView
					.getVtSprint(Long.parseLong(somSprints.getValue().toString().trim()));

			vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");

			dataFiltro = businessDelegatorView.getDataVtArtefactoFiltroDesarrollador(
					vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
			dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroIDesarrollador(
					vtSprint.getSpriCodigo().longValue(), vtUsuario.getUsuaCodigo());
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("El progreso se registró con exito"));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}

		return "";
	}

	public String actualizarCampos() {
		txtEsfuerzoRealP.setValue(txtEsfuerzoRealP.getValue().toString().trim());
		return "";
	}

	public String action_save() {
		try {
			if ((selectedVtArtefacto == null) && (entity == null)) {
			} else {
				action_modify();
			}
			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return "";
	}

	public String action_clear() {
		somActivo.setValue("-1");
		txtdescripcion.resetValue();
		txtEsfuerzoEstimado.resetValue();
		txtnombre.resetValue();
		somEstados.setValue("-1");
		somPrioridades.setValue("-1");
		somTiposDeArtefactos.setValue("-1");
		txtEsfuerzoRestante.resetValue();
		txtPuntos.resetValue();
		txtOrigen.resetValue();

		return "";
	}

	public String filtrarEmpresa() {
		try {
			VtEmpresa vtEmpresa = null;
			losProyectosFiltro = null;
			vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			Long empresa = vtUsuario.getVtEmpresa().getEmprCodigo();
			vtEmpresa = businessDelegatorView.getVtEmpresa(empresa);
			log.info("Empresa:" + vtEmpresa.getNombre() + ", Codigo:" + vtEmpresa.getEmprCodigo());
			somEmpresas.setValue(vtEmpresa.getNombre());

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
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "";
	}

	public String filtrarProyecto() {
		try {
			somPilaProducto.resetValue();
			somSprints.resetValue();
			dataFiltro = null;
			dataFiltroI = null;

			VtProyecto vtProyecto = null;
			lasPilasDeProductoFiltro = null;
			String proyectoS = somProyectos.getValue().toString().trim();

			if (proyectoS.isEmpty() || proyectoS.equals("-1")) {
			} else {
				Long proyecto = Long.parseLong(proyectoS);
				vtProyecto = businessDelegatorView.getVtProyecto(proyecto);
			}

			try {
				if (lasPilasDeProductoFiltro == null) {
					List<VtPilaProducto> listaPilasDeProducto = businessDelegatorView.getVtPilaProducto();
					lasPilasDeProductoFiltro = new ArrayList<SelectItem>();
					for (VtPilaProducto vtPilaProducto : listaPilasDeProducto) {
						if (vtPilaProducto.getActivo().equalsIgnoreCase("S")
								&& vtPilaProducto.getVtProyecto().getProyCodigo().equals(vtProyecto.getProyCodigo())) {
							lasPilasDeProductoFiltro
									.add(new SelectItem(vtPilaProducto.getPilaCodigo(), vtPilaProducto.getNombre()));
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

	public String filtrarPila() {
		// TODO:AQUI
		VtPilaProducto vtPilaProducto = null;
		try {
			somSprints.resetValue();
			dataFiltro = null;
			dataFiltroI = null;

			vtPilaProducto = businessDelegatorView
					.getVtPilaProducto(Long.parseLong(somPilaProducto.getValue().toString().trim()));

			if (losSprintsFiltro == null) {
				List<VtSprint> listaSprints = businessDelegatorView.getVtSprint();
				losSprintsFiltro = new ArrayList<SelectItem>();
				for (VtSprint vtSprint : listaSprints) {
					if (vtSprint.getVtPilaProducto().getPilaCodigo().equals(vtPilaProducto.getPilaCodigo())) {
						losSprintsFiltro.add(new SelectItem(vtSprint.getSpriCodigo(), vtSprint.getNombre()));
					}
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public String imprimirValue() {

		try {
			VtPilaProducto vtPilaProducto = null;
			dataSprint = null;
			losSprintsFiltro = null;
			String pila = somPilaProducto.getValue().toString();
			if (pila.isEmpty() || pila.equals("-1")) {
			} else {
				Long idPila = Long.parseLong(pila);
				vtPilaProducto = businessDelegatorView.getVtPilaProducto(idPila);
			}

			try {

				if (dataSprint == null) {
					dataSprint = businessDelegatorView.getDataVtSprintFiltro(vtPilaProducto.getPilaCodigo());
					losSprintsFiltro = new ArrayList<SelectItem>();
					for (VtSprintDTO vtSprintDTO : dataSprint) {
						if (vtSprintDTO.getPilaCodigo_VtPilaProducto().equals(vtPilaProducto.getPilaCodigo())) {

							losSprintsFiltro.add(new SelectItem(vtSprintDTO.getSpriCodigo(), vtSprintDTO.getNombre()));
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

	public Long convertirHorasAMinutos(String horas, String minutos) {
		Long minutosTotales = 0L;
		Long horasEsfuerzo = Long.parseLong(horas);
		Long horasEsfuerzoEnMinutos = TimeUnit.HOURS.toMinutes(horasEsfuerzo);
		Long minutosEsfuerzo = Long.parseLong(minutos);
		minutosTotales = horasEsfuerzoEnMinutos + minutosEsfuerzo;
		return minutosTotales;
	}

	public String action_modify() {
		String esfuerzoEstimado, esfuerzoRestante, puntos, esfuerzoReal;
		String horas, minutos;
		entity = null;
		try {
			VtHistoriaArtefacto vtHistoriaArtefacto = new VtHistoriaArtefacto();
			if (entity == null) {
				Long artefactoCodigo = new Long(selectedVtArtefacto.getArteCodigo());
				entity = businessDelegatorView.getVtArtefacto(artefactoCodigo);
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

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);
			esfuerzoEstimado = txtEsfuerzoEstimadoCambio.getValue().toString().trim();
			esfuerzoRestante = txtEsfuerzoRestanteCambio.getValue().toString().trim();
			esfuerzoReal = txtEsfuerzoRealCambio.getValue().toString().trim();
			puntos = txtPuntosCambio.getValue().toString().trim();

			if (esfuerzoReal != null && !esfuerzoReal.equals("0000")) {
				horas = esfuerzoReal.substring(0, 2);
				minutos = esfuerzoReal.substring(3, 5);
				esfuerzoReal = convertirHorasAMinutos(horas, minutos).toString();
				entity.setEsfuerzoReal(Integer.parseInt(esfuerzoReal));
			}
			if (esfuerzoEstimado == null || esfuerzoEstimado.equals("")) {
				throw new Exception("El campo para el esfuerzo estimado no puede estar vacio");
			} else {
				horas = esfuerzoEstimado.substring(0, 2);
				minutos = esfuerzoEstimado.substring(3, 5);
				esfuerzoEstimado = convertirHorasAMinutos(horas, minutos).toString();
				if ((Utilities.isNumeric(esfuerzoEstimado) == true)) {
					entity.setEsfuerzoEstimado(Integer.parseInt(esfuerzoEstimado));
				} else {
					throw new Exception("El campo para el esfuerzo" + " estimado no acepta cadenas de texto o "
							+ "números negativos, solo se aceptan valores númericos positivos.");
				}
			}
			if (esfuerzoRestante == null || esfuerzoRestante.equals("")) {
				throw new Exception("El campo para el esfuerzo restante no puede estar vacio");
			} else {
				horas = esfuerzoRestante.substring(0, 2);
				minutos = esfuerzoRestante.substring(3, 5);
				esfuerzoRestante = convertirHorasAMinutos(horas, minutos).toString();
				if (Utilities.isNumeric(esfuerzoRestante) == true) {

					entity.setEsfuerzoRestante(Integer.parseInt(esfuerzoRestante));
				} else {
					throw new Exception("El campo para el " + "esfuerzo restante no acepta cadenas "
							+ "de texto o números negativos, solo se aceptan valores númericos positivos.");
				}
			}
			if (puntos == null || puntos.equals("")) {
				throw new Exception("El campo para los puntos no puede ser vacio");
			} else {
				horas = puntos.substring(0, 2);
				minutos = puntos.substring(3, 5);
				puntos = convertirHorasAMinutos(horas, minutos).toString();
				if (Utilities.isNumeric(puntos) == true) {

					entity.setPuntos(Integer.parseInt(puntos));

				} else {
					throw new Exception("El campo para los puntos no acepta cadenas de texto"
							+ " o números negativos, solo se aceptan valores numéricos positivos.");
				}
			}

			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			entity.setTitulo(FacesUtils.checkString(txtnombreCambio));
			entity.setDescripcion(FacesUtils.checkString(txtdescripcionCambio));
			entity.setOrigen(FacesUtils.checkString(txtOrigenCambio));
			String artefactoCadena = somTiposDeArtefactosCambio.getValue().toString().trim();
			Long idArtefacto = Long.parseLong(artefactoCadena);
			VtTipoArtefacto vtTipoArtefacto = businessDelegatorView.getVtTipoArtefacto(idArtefacto);
			entity.setVtTipoArtefacto(vtTipoArtefacto);

			VtEstado vtEstado = businessDelegatorView
					.getVtEstado(Long.parseLong(somEstadosCambio.getValue().toString().trim()));
			entity.setVtEstado(vtEstado);

			VtPrioridad vtPrioridad = businessDelegatorView
					.getVtPrioridad(Long.parseLong(somPrioridadesCambio.getValue().toString().trim()));
			entity.setVtPrioridad(vtPrioridad);

			businessDelegatorView.updateVtArtefacto(entity);

			Long sprintCodigo = Long.valueOf(entity.getVtSprint().getSpriCodigo());
			VtSprint vtSprint = businessDelegatorView.getVtSprint(sprintCodigo);
			FacesUtils.addInfoMessage("El Artefacto se ha sido modificado con exito");
			dataFiltro = businessDelegatorView.getDataVtArtefactoFiltro(vtSprint.getSpriCodigo());
			dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroI((vtSprint.getSpriCodigo()));
			vtHistoriaArtefacto.setActivo(entity.getActivo());
			vtHistoriaArtefacto.setEstaCodigo(entity.getVtEstado().getEstaCodigo());
			vtHistoriaArtefacto.setTparCodigo(entity.getVtTipoArtefacto().getTparCodigo());
			vtHistoriaArtefacto.setPilaCodigo(entity.getVtPilaProducto().getPilaCodigo());
			vtHistoriaArtefacto.setSpriCodigo(entity.getVtSprint().getSpriCodigo());
			vtHistoriaArtefacto.setPrioCodigo(entity.getVtPrioridad().getPrioCodigo());
			vtHistoriaArtefacto.setEsfuerzoEstimado(entity.getEsfuerzoEstimado());
			vtHistoriaArtefacto.setEsfuerzoReal(entity.getEsfuerzoReal());
			vtHistoriaArtefacto.setEsfuerzoRestante(entity.getEsfuerzoRestante());
			vtHistoriaArtefacto.setFechaCreacion(entity.getFechaCreacion());
			vtHistoriaArtefacto.setFechaModificacion(entity.getFechaModificacion());
			vtHistoriaArtefacto.setOrigen(entity.getOrigen());
			vtHistoriaArtefacto.setDescripcion(entity.getDescripcion());
			vtHistoriaArtefacto.setPuntos(entity.getPuntos());
			vtHistoriaArtefacto.setTitulo(entity.getTitulo());
			vtHistoriaArtefacto.setUsuCreador(entity.getUsuCreador());
			vtHistoriaArtefacto.setUsuModificador(entity.getUsuModificador());
			vtHistoriaArtefacto.setVtArtefacto(entity);
			businessDelegatorView.saveVtHistoriaArtefacto(vtHistoriaArtefacto);

			vtHistoriaArtefacto = null;
			entity = null;

		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return "";
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public String actualizarTiempos(VtArtefacto vtArtefacto, VtSprint vtSprint) {

		try {

			Date fechaActual = new Date();
			final long milisegundosPorDia = 24 * 60 * 60 * 1000; // Milisegundos
			// al
			// día
			final long minutosPorDia = 60 * 1000; // minutos al día
			final long horasPorDia = 60 * 60 * 1000; // horas al día
			String fechaInicioSprint = vtSprint.getFechaInicio().toString() + "-";

			String[] partes = fechaInicioSprint.split("-");
			int añoFechaInicio = vtSprint.getFechaInicio().getYear();
			int mesFechaInicio = vtSprint.getFechaInicio().getMonth();
			int diaFechaInicio = vtSprint.getFechaInicio().getDay();

			GregorianCalendar calendar = new GregorianCalendar(añoFechaInicio, mesFechaInicio - 1, diaFechaInicio);
			Date fecha = new Date(calendar.getTimeInMillis());
			Long horasTranscurridas = (fechaActual.getTime() - fecha.getTime()) / horasPorDia;
			Long minutosTranscurridos = (fechaActual.getTime() - fecha.getTime()) / minutosPorDia;
			Long segundosTranscurridos = (fechaActual.getTime() - fecha.getTime()) / 1000;

			log.info("Cantidad de minutos transcurridos desde el inicio del sprint hasta hoy " + minutosTranscurridos
					+ "\nEsfuerzo Estimado " + vtArtefacto.getEsfuerzoEstimado() + "\nEsfuerzo restante "
					+ vtArtefacto.getEsfuerzoRestante());

			String fechaFinalSprint = vtSprint.getFechaFin().toString() + "-";
			String[] partesFin = fechaFinalSprint.split("-");
			int añoFechaFin = vtSprint.getFechaFin().getYear();
			int mesFechaFin = vtSprint.getFechaFin().getMonth();
			int diaFechaFin = vtSprint.getFechaFin().getDay();

			GregorianCalendar calendarFinal = new GregorianCalendar(añoFechaFin, mesFechaFin - 1, diaFechaFin);
			Date tiempoDisponible = new Date(calendarFinal.getTimeInMillis());

			Long horasTotalesDisponibles = (tiempoDisponible.getTime() - fecha.getTime()) / horasPorDia;
			Long minutosTotalesDisponibles = (tiempoDisponible.getTime() - fecha.getTime()) / minutosPorDia;
			Long segundosTotalesDisponibles = (tiempoDisponible.getTime() - fecha.getTime()) / 1000;

			if (fechaActual.getTime() >= tiempoDisponible.getTime()) {

			} else if (fechaActual.getTime() < tiempoDisponible.getTime()) {
				log.info("Usted dispone de " + horasTotalesDisponibles + " horas con " + minutosTotalesDisponibles
						+ " minutos y " + segundosTotalesDisponibles + " Segundos\n Y esta actividad le restan  "
						+ vtArtefacto.getEsfuerzoRestante() + "horas/puntos");

				int tiempoTrabajado = vtArtefacto.getEsfuerzoEstimado() - vtArtefacto.getEsfuerzoRestante();
			}

			if (vtArtefacto.getEsfuerzoRestante() == 0) {
				FacesUtils.addInfoMessage("Has terminado el artefacto del sprint");
			}
		} catch (Exception e) {
			log.error(e.getMessage());

		}
		return "";
	}

	public String cambiarEstadoArchivo(ActionEvent evt) {
		try {
			selectedVtAchivo = ((VtArchivoDTO) (evt.getComponent().getAttributes().get("selectedVtAchivo")));
			Long archivoId = new Long(selectedVtAchivo.getArchCodigo());
			VtArchivo vtArchivo = businessDelegatorView.getVtArchivo(archivoId);
			String cambioActivo = vtArchivo.getActivo().toString().trim();
			if (cambioActivo.equalsIgnoreCase("S")) {
				vtArchivo.setActivo("N");
				FacesUtils.addInfoMessage("Ok, el fichero " + vtArchivo.getNombre() + " ahora esta inactivo");
			} else {
				vtArchivo.setActivo("S");
				FacesUtils.addInfoMessage("Ok, el fichero " + vtArchivo.getNombre() + " se encuentra activo");
			}

			businessDelegatorView.updateVtArchivo(vtArchivo);

			dataFiltroArchivo = businessDelegatorView
					.getDataVtArchivoActivo(artefactoSubirArchivo.getArteCodigo().longValue());
			dataFiltroArchivoInactivo = (businessDelegatorView
					.getDataVtArchivoInactivo(artefactoSubirArchivo.getArteCodigo().longValue()));
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return "";
	}

	public String cambiarEstado(ActionEvent evt) {

		selectedVtArtefacto = (VtArtefactoDTO) (evt.getComponent().getAttributes().get("selectedVtArtefacto"));
		VtHistoriaArtefacto vtHistoriaArtefacto = new VtHistoriaArtefacto();

		try {
			if (entity == null) {
				Long arteCodigo = new Long(selectedVtArtefacto.getArteCodigo());
				entity = businessDelegatorView.getVtArtefacto(arteCodigo);
			}

			String cambioActivo = entity.getActivo().toString().trim();
			if (cambioActivo.equalsIgnoreCase("S")) {
				entity.setActivo("N");
			} else {
				entity.setActivo("S");
			}
			Long sprintCodigo = Long.valueOf(entity.getVtSprint().getSpriCodigo());
			VtSprint vtSprint = businessDelegatorView.getVtSprint(sprintCodigo);

			Date fechaModificacion = new Date();
			entity.setFechaModificacion(fechaModificacion);

			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			entity.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());

			businessDelegatorView.updateVtArtefacto(entity);
			dataFiltro = businessDelegatorView.getDataVtArtefactoFiltroDesarrollador(vtSprint.getSpriCodigo(),
					vtUsuario.getUsuaCodigo());
			dataFiltroI = businessDelegatorView.getDataVtArtefactoFiltroIDesarrollador(vtSprint.getSpriCodigo(),
					vtUsuario.getUsuaCodigo());
			vtHistoriaArtefacto.setActivo(entity.getActivo());
			vtHistoriaArtefacto.setEstaCodigo(entity.getVtEstado().getEstaCodigo());
			vtHistoriaArtefacto.setTparCodigo(entity.getVtTipoArtefacto().getTparCodigo());
			vtHistoriaArtefacto.setPilaCodigo(entity.getVtPilaProducto().getPilaCodigo());
			vtHistoriaArtefacto.setPrioCodigo(entity.getVtPrioridad().getPrioCodigo());
			vtHistoriaArtefacto.setEsfuerzoEstimado(entity.getEsfuerzoEstimado());
			vtHistoriaArtefacto.setEsfuerzoReal(entity.getEsfuerzoReal());
			vtHistoriaArtefacto.setEsfuerzoRestante(entity.getEsfuerzoRestante());
			vtHistoriaArtefacto.setFechaCreacion(entity.getFechaCreacion());
			vtHistoriaArtefacto.setFechaModificacion(entity.getFechaModificacion());
			vtHistoriaArtefacto.setOrigen(entity.getOrigen());
			vtHistoriaArtefacto.setDescripcion(entity.getDescripcion());
			vtHistoriaArtefacto.setPuntos(entity.getPuntos());
			vtHistoriaArtefacto.setSpriCodigo(entity.getVtSprint().getSpriCodigo());
			vtHistoriaArtefacto.setTitulo(entity.getTitulo());
			vtHistoriaArtefacto.setUsuCreador(entity.getUsuCreador());
			vtHistoriaArtefacto.setUsuModificador(entity.getUsuModificador());
			vtHistoriaArtefacto.setVtArtefacto(entity);
			businessDelegatorView.saveVtHistoriaArtefacto(vtHistoriaArtefacto);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("El estado del artefacto se modificó con exito"));

			vtHistoriaArtefacto = null;
			selectedVtArtefacto = null;
			entity = null;

		} catch (Exception e) {
			data = null;
			log.error(e.toString());
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public InputTextarea getTxtdescripcion() {
		return txtdescripcion;
	}

	public VtArchivo getArchivoEntity() {
		return archivoEntity;
	}

	public CommandButton getBtnCrearArtefactoFiltrado() {
		return btnCrearArtefactoFiltrado;
	}

	public void setBtnCrearArtefactoFiltrado(CommandButton btnCrearArtefactoFiltrado) {
		this.btnCrearArtefactoFiltrado = btnCrearArtefactoFiltrado;
	}

	public boolean isShowDialogHistorial() {
		return showDialogHistorial;
	}

	public void setShowDialogHistorial(boolean showDialogHistorial) {
		this.showDialogHistorial = showDialogHistorial;
	}

	public List<SelectItem> getLosSprintsFiltro() {
		/*
		 * if (losSprintsFiltro == null) { try { VtEmpresa vtEmpresa = null;
		 * vtUsuario = (VtUsuario) FacesUtils.getfromSession("vtUsuario"); Long
		 * empresa = vtUsuario.getVtEmpresa().getEmprCodigo(); vtEmpresa =
		 * businessDelegatorView.getVtEmpresa(empresa);
		 * 
		 * List<VtSprint> listaSprint; listaSprint =
		 * businessDelegatorView.getVtSprint(); losSprintsFiltro = new
		 * ArrayList<SelectItem>();
		 * 
		 * for(VtSprint vtSprint: listaSprint){
		 * if(vtSprint.getVtPilaProducto().getVtProyecto().getVtEmpresa().
		 * getEmprCodigo().equals(vtEmpresa.getEmprCodigo())){
		 * losSprintsFiltro.add(new SelectItem(vtSprint.getSpriCodigo(),
		 * vtSprint.getNombre())); } } } catch (Exception e) {
		 * e.printStackTrace(); } }
		 */
		return losSprintsFiltro;
	}

	public void setLosSprintsFiltro(List<SelectItem> losSprintsFiltro) {
		this.losSprintsFiltro = losSprintsFiltro;
	}

	public VtArchivoDTO getSelectedVtAchivo() {
		return selectedVtAchivo;
	}

	public void setSelectedVtAchivo(VtArchivoDTO selectedVtAchivo) {
		this.selectedVtAchivo = selectedVtAchivo;
	}

	public VtHistoriaArtefactoDTO getSelectedVtHistoriaArtefacto() {
		return selectedVtHistoriaArtefacto;
	}

	public void setSelectedVtHistoriaArtefacto(VtHistoriaArtefactoDTO selectedVtHistoriaArtefacto) {
		this.selectedVtHistoriaArtefacto = selectedVtHistoriaArtefacto;
	}

	public List<VtHistoriaArtefactoDTO> getDataFiltroHistoriaArtefacto() {
		return dataFiltroHistoriaArtefacto;
	}

	public void setDataFiltroHistoriaArtefacto(List<VtHistoriaArtefactoDTO> dataFiltroHistoriaArtefacto) {
		this.dataFiltroHistoriaArtefacto = dataFiltroHistoriaArtefacto;
	}

	public List<VtArchivoDTO> getDataFiltroArchivo() {
		return dataFiltroArchivo;
	}

	public void setDataFiltroArchivo(List<VtArchivoDTO> dataFiltroArchivo) {
		this.dataFiltroArchivo = dataFiltroArchivo;
	}

	public InputText getTxtOrigen() {
		return txtOrigen;
	}

	public void setTxtOrigen(InputText txtOrigen) {
		this.txtOrigen = txtOrigen;
	}

	public InputText getTxtOrigenCambio() {
		return txtOrigenCambio;
	}

	public void setTxtOrigenCambio(InputText txtOrigenCambio) {
		this.txtOrigenCambio = txtOrigenCambio;
	}

	public DefaultStreamedContent getArchivos() {
		return archivos;
	}

	public void setArchivos(DefaultStreamedContent archivos) {
		this.archivos = archivos;
	}

	public void setArchivoEntity(VtArchivo archivoEntity) {
		this.archivoEntity = archivoEntity;
	}

	public SelectOneMenu getSomUsuariosArtefactos() {
		return somUsuariosArtefactos;
	}

	public VtArtefactoDTO getSelectedVtArtefacto() {
		return selectedVtArtefacto;
	}

	public Long getSpriCodigo() {
		return spriCodigo;
	}

	public InputTextarea getTxtdescripcionCambio() {
		return txtdescripcionCambio;
	}

	private DefaultStreamedContent archivos;

	public DefaultStreamedContent getArchivo() {
		return archivos;
	}

	public void setArchivo(DefaultStreamedContent archivo) {
		this.archivos = archivo;
	}

	public void setTxtdescripcionCambio(InputTextarea txtdescripcionCambio) {
		this.txtdescripcionCambio = txtdescripcionCambio;
	}

	public InputText getTxtnombreCambio() {
		return txtnombreCambio;
	}

	public void setTxtnombreCambio(InputText txtnombreCambio) {
		this.txtnombreCambio = txtnombreCambio;
	}

	public void setSpriCodigo(Long spriCodigo) {
		this.spriCodigo = spriCodigo;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public void setSelectedVtArtefacto(VtArtefactoDTO selectedVtArtefacto) {
		this.selectedVtArtefacto = selectedVtArtefacto;
	}

	public void setSomUsuariosArtefactos(SelectOneMenu somUsuariosArtefactos) {
		this.somUsuariosArtefactos = somUsuariosArtefactos;
	}

	public CommandButton getBtnDescargar() {
		return btnDescargar;
	}

	public void setBtnDescargar(CommandButton btnDescargar) {
		this.btnDescargar = btnDescargar;
	}

	public List<VtSprintDTO> getDataSprint() {
		return dataSprint;
	}

	public void setDataSprint(List<VtSprintDTO> dataSprint) {
		this.dataSprint = dataSprint;
	}

	public List<SelectItem> getEsUsuarioArtefactoItems() {
		return esUsuarioArtefactoItems;
	}

	public List<SelectItem> getLasEmpresasItems() {
		try {
			if (lasEmpresasItems == null) {
				lasEmpresasItems = new ArrayList<SelectItem>();
				lasEmpresasItems.add(
						new SelectItem(vtUsuario.getVtEmpresa().getEmprCodigo(), vtUsuario.getVtEmpresa().getNombre()));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return lasEmpresasItems;
	}

	public void setLasEmpresasItems(List<SelectItem> lasEmpresasItems) {
		this.lasEmpresasItems = lasEmpresasItems;
	}

	public void setEsUsuarioArtefactoItems(List<SelectItem> esUsuarioArtefactoItems) {
		this.esUsuarioArtefactoItems = esUsuarioArtefactoItems;
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

	public List<SelectItem> getLasPilasDeProductoFiltro() {
		return lasPilasDeProductoFiltro;
	}

	public void setLasPilasDeProductoFiltro(List<SelectItem> lasPilasDeProductoFiltro) {
		this.lasPilasDeProductoFiltro = lasPilasDeProductoFiltro;
	}

	public CommandButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public SelectOneMenu getSomSprintsCrear() {
		return somSprintsCrear;
	}

	public void setSomSprintsCrear(SelectOneMenu somSprintsCrear) {
		this.somSprintsCrear = somSprintsCrear;
	}

	public SelectOneMenu getSomPilaProductoCrear() {
		return somPilaProductoCrear;
	}

	public void setSomPilaProductoCrear(SelectOneMenu somPilaProductoCrear) {
		this.somPilaProductoCrear = somPilaProductoCrear;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public void setBtnFiltrar(CommandButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public void setTxtdescripcion(InputTextarea txtdescripcion) {
		this.txtdescripcion = txtdescripcion;
	}

	public InputText getTxtnombre() {
		return txtnombre;
	}

	public void setTxtnombre(InputText txtnombre) {
		this.txtnombre = txtnombre;
	}

	public SelectOneMenu getSomActivo() {
		return somActivo;
	}

	public void setSomActivo(SelectOneMenu somActivo) {
		this.somActivo = somActivo;
	}

	public SelectOneMenu getSomEstados() {
		return somEstados;
	}

	public void setSomEstados(SelectOneMenu somEstados) {
		this.somEstados = somEstados;
	}

	public SelectOneMenu getSomPrioridades() {
		return somPrioridades;
	}

	public void setSomPrioridades(SelectOneMenu somPrioridades) {
		this.somPrioridades = somPrioridades;
	}

	public SelectOneMenu getSomTiposDeArtefactos() {
		return somTiposDeArtefactos;
	}

	public void setSomTiposDeArtefactos(SelectOneMenu somTiposDeArtefactos) {
		this.somTiposDeArtefactos = somTiposDeArtefactos;
	}

	public SelectOneMenu getSomSprints() {
		return somSprints;
	}

	public void setSomSprints(SelectOneMenu somSprints) {
		this.somSprints = somSprints;
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

	public List<SelectItem> getEsPrioridadItems() {

		try {
			if (esPrioridadItems == null) {
				List<VtPrioridad> listaPrioridad = businessDelegatorView.getVtPrioridad();
				esPrioridadItems = new ArrayList<SelectItem>();

				for (VtPrioridad vtPrioridad : listaPrioridad) {
					esPrioridadItems.add(new SelectItem(vtPrioridad.getPrioCodigo(), vtPrioridad.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return esPrioridadItems;
	}

	public void setEsPrioridadItems(List<SelectItem> esPrioridadItems) {
		this.esPrioridadItems = esPrioridadItems;
	}

	public List<SelectItem> getEsTipoArtefactoItems() {
		try {
			if (esTipoArtefactoItems == null) {
				List<VtTipoArtefacto> listaTiposArtefacto = businessDelegatorView.getVtTipoArtefacto();
				esTipoArtefactoItems = new ArrayList<SelectItem>();
				for (VtTipoArtefacto vtTipoArtefacto : listaTiposArtefacto) {
					esTipoArtefactoItems
							.add(new SelectItem(vtTipoArtefacto.getTparCodigo(), vtTipoArtefacto.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return esTipoArtefactoItems;
	}

	public void setEsTipoArtefactoItems(List<SelectItem> esTipoArtefactoItems) {
		this.esTipoArtefactoItems = esTipoArtefactoItems;
	}

	public SelectOneMenu getSomEstadosP() {
		return somEstadosP;
	}

	public void setSomEstadosP(SelectOneMenu somEstadosP) {
		this.somEstadosP = somEstadosP;
	}

	public List<SelectItem> getEsEstadoItemsP() {

		try {
			if (esEstadoItemsP == null) {
				List<VtEstado> listaTiposEstado = businessDelegatorView.getVtEstado();
				esEstadoItemsP = new ArrayList<SelectItem>();
				for (VtEstado vtEstado : listaTiposEstado) {
					esEstadoItemsP.add(new SelectItem(vtEstado.getEstaCodigo(), vtEstado.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return esEstadoItemsP;
	}

	public void setEsEstadoItemsP(List<SelectItem> esEstadoItemsP) {
		this.esEstadoItemsP = esEstadoItemsP;
	}

	public List<SelectItem> getEsEstadoItems() {
		try {
			if (esEstadoItems == null) {
				List<VtEstado> listaTiposEstado = businessDelegatorView.getVtEstado();
				esEstadoItems = new ArrayList<SelectItem>();
				for (VtEstado vtEstado : listaTiposEstado) {
					esEstadoItems.add(new SelectItem(vtEstado.getEstaCodigo(), vtEstado.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return esEstadoItems;
	}

	public void setEsEstadoItems(List<SelectItem> esEstadoItems) {
		this.esEstadoItems = esEstadoItems;
	}

	public List<SelectItem> getEsSprintItems() {
		try {
			if (esSprintItems == null) {
				List<VtSprint> listaSprints = businessDelegatorView.getVtSprint();
				esSprintItems = new ArrayList<SelectItem>();

				for (VtSprint vtSprint : listaSprints) {
					esSprintItems.add(new SelectItem(vtSprint.getSpriCodigo(), vtSprint.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return esSprintItems;
	}

	public void setEsSprintItems(List<SelectItem> esSprintItems) {
		this.esSprintItems = esSprintItems;
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

	public CommandButton getBtnCrearS() {
		return btnCrearS;
	}

	public void setBtnCrearS(CommandButton btnCrearS) {
		this.btnCrearS = btnCrearS;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnLimpiarS() {
		return btnLimpiarS;
	}

	public void setBtnLimpiarS(CommandButton btnLimpiarS) {
		this.btnLimpiarS = btnLimpiarS;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public VtArtefacto getEntity() {
		return entity;
	}

	public void setEntity(VtArtefacto entity) {
		this.entity = entity;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public SelectOneMenu getSomPilaProducto() {
		return somPilaProducto;
	}

	public void setSomPilaProducto(SelectOneMenu somPilaProducto) {
		this.somPilaProducto = somPilaProducto;
	}

	public List<VtArtefactoDTO> getData() {
		return data;
	}

	public void setData(List<VtArtefactoDTO> data) {
		this.data = data;
	}

	public List<VtArtefactoDTO> getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(List<VtArtefactoDTO> dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public List<VtArtefactoDTO> getDataFiltroI() {
		return dataFiltroI;
	}

	public void setDataFiltroI(List<VtArtefactoDTO> dataFiltroI) {
		this.dataFiltroI = dataFiltroI;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public List<SelectItem> getEsPilaProductoItems() {

		try {
			if (esPilaProductoItems == null) {
				List<VtPilaProducto> listaPilasProducto = businessDelegatorView.getVtPilaProducto();
				esPilaProductoItems = new ArrayList<SelectItem>();
				for (VtPilaProducto vtPilaProducto : listaPilasProducto) {
					esPilaProductoItems.add(new SelectItem(vtPilaProducto.getPilaCodigo(), vtPilaProducto.getNombre()));
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return esPilaProductoItems;
	}

	public void setEsPilaProductoItems(List<SelectItem> esPilaProductoItems) {
		this.esPilaProductoItems = esPilaProductoItems;
	}

	public SelectOneMenu getSomTiposDeArtefactosCambio() {
		return somTiposDeArtefactosCambio;
	}

	public void setSomTiposDeArtefactosCambio(SelectOneMenu somTiposDeArtefactosCambio) {
		this.somTiposDeArtefactosCambio = somTiposDeArtefactosCambio;
	}

	public SelectOneMenu getSomPilaProductoCambio() {
		return somPilaProductoCambio;
	}

	public void setSomPilaProductoCambio(SelectOneMenu somPilaProductoCambio) {
		this.somPilaProductoCambio = somPilaProductoCambio;
	}

	public SelectOneMenu getSomSprintsCambio() {
		return somSprintsCambio;
	}

	public void setSomSprintsCambio(SelectOneMenu somSprintsCambio) {
		this.somSprintsCambio = somSprintsCambio;
	}

	public SelectOneMenu getSomActivoCambio() {
		return somActivoCambio;
	}

	public void setSomActivoCambio(SelectOneMenu somActivoCambio) {
		this.somActivoCambio = somActivoCambio;
	}

	public SelectOneMenu getSomEstadosCambio() {
		return somEstadosCambio;
	}

	public void setSomEstadosCambio(SelectOneMenu somEstadosCambio) {
		this.somEstadosCambio = somEstadosCambio;
	}

	public SelectOneMenu getSomPrioridadesCambio() {
		return somPrioridadesCambio;
	}

	public void setSomPrioridadesCambio(SelectOneMenu somPrioridadesCambio) {
		this.somPrioridadesCambio = somPrioridadesCambio;
	}

	public boolean getShowDialogArchivos() {
		return showDialogArchivos;
	}

	public void setShowDialogArchivos(boolean showDialogArchivos) {
		this.showDialogArchivos = showDialogArchivos;
	}

	public VtArtefacto getArtefactoSubirArchivo() {
		return artefactoSubirArchivo;
	}

	public void setArtefactoSubirArchivo(VtArtefacto artefactoSubirArchivo) {
		this.artefactoSubirArchivo = artefactoSubirArchivo;
	}

	public boolean isShowDialogSubirArchivo() {
		return showDialogSubirArchivo;
	}

	public void setShowDialogSubirArchivo(boolean showDialogSubirArchivo) {
		this.showDialogSubirArchivo = showDialogSubirArchivo;
	}

	public List<VtArchivoDTO> getDataFiltroArchivoInactivo() {
		return dataFiltroArchivoInactivo;
	}

	public void setDataFiltroArchivoInactivo(List<VtArchivoDTO> dataFiltroArchivoInactivo) {
		this.dataFiltroArchivoInactivo = dataFiltroArchivoInactivo;
	}

	public InputMask getTxtEsfuerzoEstimado() {
		return txtEsfuerzoEstimado;
	}

	public void setTxtEsfuerzoEstimado(InputMask txtEsfuerzoEstimado) {
		this.txtEsfuerzoEstimado = txtEsfuerzoEstimado;
	}

	public InputMask getTxtEsfuerzoEstimadoCambio() {
		return txtEsfuerzoEstimadoCambio;
	}

	public void setTxtEsfuerzoEstimadoCambio(InputMask txtEsfuerzoEstimadoCambio) {
		this.txtEsfuerzoEstimadoCambio = txtEsfuerzoEstimadoCambio;
	}

	public InputMask getTxtEsfuerzoRestante() {
		return txtEsfuerzoRestante;
	}

	public void setTxtEsfuerzoRestante(InputMask txtEsfuerzoRestante) {
		this.txtEsfuerzoRestante = txtEsfuerzoRestante;
	}

	public InputMask getTxtEsfuerzoRestanteCambio() {
		return txtEsfuerzoRestanteCambio;
	}

	public void setTxtEsfuerzoRestanteCambio(InputMask txtEsfuerzoRestanteCambio) {
		this.txtEsfuerzoRestanteCambio = txtEsfuerzoRestanteCambio;
	}

	public InputMask getTxtEsfuerzoReal() {
		return txtEsfuerzoReal;
	}

	public void setTxtEsfuerzoReal(InputMask txtEsfuerzoReal) {
		this.txtEsfuerzoReal = txtEsfuerzoReal;
	}

	public InputMask getTxtEsfuerzoRealCambio() {
		return txtEsfuerzoRealCambio;
	}

	public void setTxtEsfuerzoRealCambio(InputMask txtEsfuerzoRealCambio) {
		this.txtEsfuerzoRealCambio = txtEsfuerzoRealCambio;
	}

	public InputMask getTxtPuntos() {
		return txtPuntos;
	}

	public void setTxtPuntos(InputMask txtPuntos) {
		this.txtPuntos = txtPuntos;
	}

	public InputMask getTxtPuntosCambio() {
		return txtPuntosCambio;
	}

	public void setTxtPuntosCambio(InputMask txtPuntosCambio) {
		this.txtPuntosCambio = txtPuntosCambio;
	}

}
