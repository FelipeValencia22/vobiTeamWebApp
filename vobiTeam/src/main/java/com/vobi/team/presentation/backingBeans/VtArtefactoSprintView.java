package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtPilaProducto;
import com.vobi.team.modelo.VtSprint;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;


@ManagedBean
@ViewScoped
public class VtArtefactoSprintView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtArtefactoSprintView.class);
	private DualListModel<VtArtefacto> vtArtefacto;
	private SelectOneMenu somSprints;
	private List<SelectItem> losSprintsItems;
	private String sprintSeleccionado;

	List<VtArtefacto> artefactosSource;

	List<VtArtefacto> artefactosTarget;

	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	@PostConstruct
	public void init() {
		List<VtArtefacto> artefactosSource = new ArrayList<VtArtefacto>();
		List<VtArtefacto> artefactosTarget = new ArrayList<VtArtefacto>();
		vtArtefacto = new DualListModel<>(artefactosSource, artefactosTarget);
	}
	public void asignarArtefactoASprint(VtArtefacto vtArtefacto,VtSprint vtSprint, VtPilaProducto vtPilaProducto) {
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			 vtArtefacto = (VtArtefacto) businessDelegatorView
					.consultarArtefactosAsignadosASprintYPila(vtArtefacto.getArteCodigo(), vtPilaProducto.getPilaCodigo());
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
	
	public void removerArtefactoDelSprint(VtArtefacto vtArtefacto,VtSprint vtSprint, VtPilaProducto vtPilaProducto){
		try {
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");
			 vtArtefacto = (VtArtefacto) businessDelegatorView
						.consultarArtefactosAsignadosASprintYPila(vtArtefacto.getArteCodigo(), vtPilaProducto.getPilaCodigo());
			 vtArtefacto.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
			 vtArtefacto.setFechaModificacion(new Date());
			 vtArtefacto.setActivo("N");
			 vtArtefacto.setVtSprint(null);
			 businessDelegatorView.updateVtArtefacto(vtArtefacto);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void onTransfer(TransferEvent event) throws Exception {
		try {
			StringBuilder builder = new StringBuilder();
			Long idSprint= Long.parseLong(somSprints.getValue().toString().trim());
			VtSprint vtSprint = businessDelegatorView.getVtSprint(idSprint);
			VtPilaProducto vtPilaProducto = businessDelegatorView.getVtPilaProducto(vtSprint.getVtPilaProducto().getPilaCodigo());
			for (Object item : event.getItems()) {
				VtArtefacto vtArtefacto =(VtArtefacto) item;

				builder.append(((VtArtefacto) item).getTitulo()).append("<br />");
				if (event.isAdd()) {
					asignarArtefactoASprint(vtArtefacto, vtSprint, vtPilaProducto);
				}
				if (event.isRemove()) {
					removerArtefactoDelSprint(vtArtefacto, vtSprint, vtPilaProducto);
				}
			}
			FacesUtils.addInfoMessage("Artefactos(s) Asignado(s)");
		} catch (Exception e) {
			FacesUtils.addErrorMessage("No se pudo realizar la transferencia");
		}

	}
	public void actualizarListaUsuarios() throws Exception {

		try {
			Long idSprint = Long.parseLong(somSprints.getValue().toString().trim());
			VtSprint vtSprint = businessDelegatorView.getVtSprint(idSprint);

			log.info("Codigo del sprint" + vtSprint.getSpriCodigo());

			artefactosSource = businessDelegatorView.consultarArtefactosSinAsignarASprint();
			artefactosTarget = businessDelegatorView.consultarArtefactosAsignadosASprint(idSprint);

			vtArtefacto.setSource(artefactosSource);
			vtArtefacto.setTarget(artefactosTarget);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
	public DualListModel<VtArtefacto> getVtArtefacto() {
		return vtArtefacto;
	}

	public void setVtArtefacto(DualListModel<VtArtefacto> vtArtefacto) {
		this.vtArtefacto = vtArtefacto;
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
					losSprintsItems.add(new SelectItem(vtSprint.getSpriCodigo(), vtSprint.getNombre()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losSprintsItems;
	}
	
	public void localeChanged() throws Exception {
		setSprintSeleccionado(somSprints.getValue().toString().trim());
		actualizarListaUsuarios();

	}
	public String action_closeDialog() {
		setShowDialog(false);
		somSprints.setValue("-1");
		return "";
	}
	public void setLosSprintsItems(List<SelectItem> losSprintsItems) {
		this.losSprintsItems = losSprintsItems;
	}

	public String getSprintSeleccionado() {
		return sprintSeleccionado;
	}

	public void setSprintSeleccionado(String sprintSeleccionado) {
		this.sprintSeleccionado = sprintSeleccionado;
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

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

}
