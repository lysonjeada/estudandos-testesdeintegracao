package br.com.alura.dao;

import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach () {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado () {
        Usuario usuario = criarUsuario();
        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado () {
        criarUsuario();
        Assert.assertThrows(NoResultException.class,() -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    void deveriaRemoverUmUsuario () {
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);

        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
    }

    private Usuario criarUsuario () {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
