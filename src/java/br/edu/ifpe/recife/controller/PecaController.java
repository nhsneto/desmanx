package br.edu.ifpe.recife.controller;

import br.edu.ifpe.recife.model.dao.ManagerDao;
import br.edu.ifpe.recife.model.negocio.Peca;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "pecaCtrl")
@SessionScoped
public class PecaController {

    private Peca peca;
    private Peca selecionada;
    private DualListModel<Peca> pecas;

    @PostConstruct
    public void init() {
        this.peca = new Peca();
        this.selecionada = null;
        this.pecas = new DualListModel<>();
    }

    public void atualizaListaPecas() {
        List<Peca> pecasSource = readAll();
        pecasSource.sort((a, b) -> a.getNome().compareTo(b.getNome()));
        this.pecas = new DualListModel<>(pecasSource, new ArrayList<>());
    }

    public void create() {
        ManagerDao.getInstance().create(this.peca);
        FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage("Peça cadastrada com sucesso!"));
        this.peca = new Peca();
    }

    public void update() {
        ManagerDao.getInstance().update(this.selecionada);
        FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage("Peça alterada com sucesso!"));
    }

    public void delete() {
        ManagerDao.getInstance().delete(this.selecionada);
        FacesContext.getCurrentInstance()
            .addMessage(null, new FacesMessage("Peça deletada com sucesso!"));
    }

    public List<Peca> readAll() {
        return ManagerDao.getInstance().read("SELECT p FROM Peca p", Peca.class);
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Peca getSelecionada() {
        return selecionada;
    }

    public void setSelecionada(Peca selecionada) {
        this.selecionada = selecionada;
    }

    public DualListModel<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(DualListModel<Peca> pecas) {
        this.pecas = pecas;
    }
}
