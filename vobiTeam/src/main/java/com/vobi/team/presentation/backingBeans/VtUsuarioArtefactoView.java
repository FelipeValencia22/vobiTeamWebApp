package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vobi.team.modelo.VtArtefacto;
import com.vobi.team.modelo.VtEmpresa;
import com.vobi.team.modelo.VtInteres;
import com.vobi.team.modelo.VtProyecto;
import com.vobi.team.modelo.VtUsuario;
import com.vobi.team.modelo.VtUsuarioArtefacto;
import com.vobi.team.presentation.businessDelegate.IBusinessDelegatorView;
import com.vobi.team.utilities.FacesUtils;

@ManagedBean
@ViewScoped
public class VtUsuarioArtefactoView implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(VtUsuarioArtefactoView.class);
	
	private DualListModel<VtArtefacto> vtArtefacto;
	
	private SelectOneMenu somUsuarios;
	private SelectOneMenu somProyectos;
	private SelectOneMenu somIntereses;
	private SelectOneMenu somEmpresas;
	
	private List<SelectItem> losUsuariosItems;
	
	private String usuarioSeleccionado;
	
	private List<SelectItem> losUsuariosFiltro;
	private List<SelectItem> losProyectosFiltro;
	private List<SelectItem> losInteresesFiltro;
	private List<SelectItem> lasEmpresasItems;
	
	List<VtArtefacto> artefactosSource;
	List<VtArtefacto> artefactosTarget;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	@PostConstruct
	public void init() {

		try {
			artefactosSource = new ArrayList<VtArtefacto>();
			artefactosTarget = new ArrayList<VtArtefacto>();
			vtArtefacto = new DualListModel<>(artefactosSource, artefactosTarget);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void asignarArtefactoAUsuario(VtUsuario vtUsuario, VtArtefacto vtArtefacto, VtInteres vtInteres) throws Exception {

		try {

			VtUsuarioArtefacto vtUsuarioArtefacto = businessDelegatorView.consultarUsuarioArtefactoPorUsuarioYArtefacto(
					vtUsuario.getUsuaCodigo(), vtArtefacto.getArteCodigo());
			VtUsuario vtUsuarioEnSession = (VtUsuario) FacesUtils.getfromSession("vtUsuario");

			if (vtUsuarioArtefacto == null) {

				VtUsuarioArtefacto usuarioArtefacto = new VtUsuarioArtefacto();
				usuarioArtefacto.setActivo("S");
				usuarioArtefacto.setFechaCreacion(new Date());
				usuarioArtefacto.setUsuCreador(vtUsuarioEnSession.getUsuaCodigo());
				usuarioArtefacto.setVtInteres(vtInteres);
				usuarioArtefacto.setVtArtefacto(vtArtefacto);
				usuarioArtefacto.setVtUsuario(vtUsuario);

				businessDelegatorView.saveVtUsuarioArtefacto(usuarioArtefacto);
				FacesUtils.addInfoMessage("Artefacto asignado con éxito");
			} else {
				vtUsuarioArtefacto.setActivo("S");
				vtUsuarioArtefacto.setFechaModificacion(new Date());
				vtUsuarioArtefacto.setUsuModificador(vtUsuarioEnSession.getUsuaCodigo());
				businessDelegatorView.updateVtUsuarioArtefacto(vtUsuarioArtefacto);
				FacesUtils.addInfoMessage("Artefacto asignado con éxito");
			}
		} catch (Exception e) {
			actualizarListaArtefactos();
			e.printStackTrace();
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public void removerArtefactoDelUsuario(VtUsuario vtUsuario, VtArtefacto vtArtefacto, VtInteres vtInteres) throws Exception {

		try {
			VtUsuarioArtefacto vtUsuarioArtefacto = businessDelegatorView.consultarUsuarioArtefactoPorUsuarioYArtefacto(
					vtUsuario.getUsuaCodigo(), vtArtefacto.getArteCodigo());
			if(vtUsuarioArtefacto!=null){
				businessDelegatorView.deleteVtUsuarioArtefacto(vtUsuarioArtefacto);
				FacesUtils.addInfoMessage("Artefacto des-asignado con éxito con éxito");
			}
		} catch (Exception e) {
			actualizarListaArtefactos();
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public void actualizarListaArtefactos() throws Exception {

		try {
			Long idUsuario = Long.parseLong(somUsuarios.getValue().toString().trim());
			Long idProyecto = Long.parseLong(somProyectos.getValue().toString().trim());
			VtUsuario vtUsuario = businessDelegatorView.getVtUsuario(idUsuario);
			artefactosSource = businessDelegatorView.obtenerArtefactosNoAsignados(vtUsuario,idProyecto);
			artefactosTarget = businessDelegatorView.obtenerArtefactosAsignados(vtUsuario,idProyecto);
			vtArtefacto.setSource(artefactosSource);
			vtArtefacto.setTarget(artefactosTarget);

		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

	}

	public void onTransfer(TransferEvent event) throws Exception {

		try {
			StringBuilder builder = new StringBuilder();
			Long idUsuario = Long.parseLong(somUsuarios.getValue().toString().trim());
			VtUsuario vtUsuario = businessDelegatorView.getVtUsuario(idUsuario);
			VtInteres vtInteres = businessDelegatorView
					.getVtInteres(Long.parseLong(somIntereses.getValue().toString().trim()));
			for (Object item : event.getItems()) {
				VtArtefacto vtArtefacto = (VtArtefacto) item;
				
				builder.append(((VtArtefacto) item).getTitulo()).append("<br />");
				if (event.isAdd()) {
					asignarArtefactoAUsuario(vtUsuario, vtArtefacto, vtInteres);
				}
				if (event.isRemove()) {
					removerArtefactoDelUsuario(vtUsuario, vtArtefacto, vtInteres);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addErrorMessage(e.getMessage());
		}

	}

	public void localeChanged() throws Exception {
		
		try {
			setUsuarioSeleccionado(somUsuarios.getValue().toString().trim());
			actualizarListaArtefactos();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

	public void habilitarSelectOneMenu() {
		somProyectos.setDisabled(false);
		somUsuarios.setDisabled(false);
	}

	public DualListModel<VtArtefacto> getVtArtefacto() {
		return vtArtefacto;
	}

	public void setVtArtefacto(DualListModel<VtArtefacto> vtArtefacto) {
		this.vtArtefacto = vtArtefacto;
	}

	public SelectOneMenu getSomUsuarios() {
		return somUsuarios;
	}

	public void setSomUsuarios(SelectOneMenu somUsuarios) {
		this.somUsuarios = somUsuarios;
	}

	public SelectOneMenu getSomProyectos() {
		return somProyectos;
	}

	public void setSomProyectos(SelectOneMenu somProyectos) {
		this.somProyectos = somProyectos;
	}

	public List<SelectItem> getLosUsuariosItems() {

		try {
			VtProyecto vtProyecto = businessDelegatorView
					.getVtProyecto(Long.parseLong(somProyectos.getValue().toString().trim()));
			if (losUsuariosItems == null) {
				List<VtUsuario> listaUsuarios = businessDelegatorView.obtenerUsuariosAsignados(vtProyecto);
				losUsuariosItems = new ArrayList<SelectItem>();

				for (VtUsuario vtUsuario : listaUsuarios) {
					losUsuariosItems.add(new SelectItem(vtUsuario.getUsuaCodigo(), vtUsuario.getLogin()));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losUsuariosItems;
	}

	public String filtrarProyectoUsuario() {
		try {
			VtProyecto vtProyecto = null;
			losUsuariosFiltro = null;
			String proyectoS = somProyectos.getValue().toString().trim();

			if (proyectoS.isEmpty() || proyectoS.equals("-1")) {
			} else {
				vtProyecto = businessDelegatorView
						.getVtProyecto(Long.parseLong(somProyectos.getValue().toString().trim()));
			}

			try {

				if (losUsuariosFiltro == null) {
					List<VtUsuario> listaUsuarios = businessDelegatorView.obtenerUsuariosAsignados(vtProyecto);
					losUsuariosFiltro = new ArrayList<SelectItem>();

					for (VtUsuario vtUsuario : listaUsuarios) {
						if (vtUsuario.getActivo().equals("S")) {
							losUsuariosFiltro.add(new SelectItem(vtUsuario.getUsuaCodigo(), vtUsuario.getLogin()));
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

	public void setLosUsuariosItems(List<SelectItem> losUsuariosItems) {
		this.losUsuariosItems = losUsuariosItems;
	}

	public String getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(String usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public List<SelectItem> getLosUsuariosFiltro() {
		return losUsuariosFiltro;
	}

	public void setLosUsuariosFiltro(List<SelectItem> losUsuariosFiltro) {
		this.losUsuariosFiltro = losUsuariosFiltro;
	}

	public List<SelectItem> getLosProyectosFiltro() {
		return losProyectosFiltro;
	}

	public void setLosProyectosFiltro(List<SelectItem> losProyectosFiltro) {
		this.losProyectosFiltro = losProyectosFiltro;
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

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public SelectOneMenu getSomIntereses() {
		return somIntereses;
	}

	public void setSomIntereses(SelectOneMenu somIntereses) {
		this.somIntereses = somIntereses;
	}

	public List<SelectItem> getLosInteresesFiltro() {

		try {
			if (losInteresesFiltro == null) {
				List<VtInteres> listaInteres = businessDelegatorView.getVtInteres();
				losInteresesFiltro = new ArrayList<SelectItem>();

				for (VtInteres vtInteres : listaInteres) {
					if (vtInteres.getActivo().equals("S")) {
						losInteresesFiltro.add(new SelectItem(vtInteres.getInteCodigo(), vtInteres.getNombre()));
					}
				}
			}
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return losInteresesFiltro;
	}

	public void setLosInteresesFiltro(List<SelectItem> losInteresesFiltro) {
		this.losInteresesFiltro = losInteresesFiltro;
	}

	public SelectOneMenu getSomEmpresas() {
		return somEmpresas;
	}

	public void setSomEmpresas(SelectOneMenu somEmpresas) {
		this.somEmpresas = somEmpresas;
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
	
	public String filtrarEmpresa() {
		try {
			VtEmpresa vtEmpresa = null;
			losProyectosFiltro = null;
			String empresaS = somEmpresas.getValue().toString().trim();
			if (empresaS.isEmpty() || empresaS.equals("-1")) {
			} else {
				Long empresa = Long.parseLong(empresaS);
				vtEmpresa = businessDelegatorView.getVtEmpresa(empresa);
			}
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
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

}
