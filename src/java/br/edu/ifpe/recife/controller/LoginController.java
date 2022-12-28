package br.edu.ifpe.recife.controller;

import br.edu.ifpe.recife.model.dao.ManagerDao;
import br.edu.ifpe.recife.model.negocio.Puxador;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginCtrl")
@SessionScoped
public class LoginController {

    private Puxador puxadorLogado;

    @PostConstruct
    public void init() {
        this.puxadorLogado = null;
    }

    public Puxador getPuxadorLogado() {
        return puxadorLogado;
    }

    public void setPuxadorLogado(Puxador puxadorLogado) {
        this.puxadorLogado = puxadorLogado;
    }

    public String loga(String login, String senha) {
        String puxadorQuery = String.format(
            "SELECT p FROM Puxador p WHERE p.codinome = '%s' AND p.senha = '%s'", login, senha);
        List<Puxador> puxadores = ManagerDao.getInstance().read(puxadorQuery, Puxador.class);

        if (!puxadores.isEmpty()) {
            this.puxadorLogado = puxadores.get(0);
            return this.puxadorLogado.getCodinome().equals("admin")
                ? "indexAdministrador" : "indexPuxador";
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Login ou senha inválidos", null));
        return null;
    }

    public void desloga() {
        this.puxadorLogado = null;
    }
}
