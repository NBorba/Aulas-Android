package rblstudios.com.cadastroproduto.controller;

import android.content.Context;

import java.util.List;

import rblstudios.com.cadastroproduto.dal.MarcaDAO;
import rblstudios.com.cadastroproduto.interfaces.ICrud;
import rblstudios.com.cadastroproduto.model.Marca;

/**
 * Criado por renan.lucas 06/11/2017.
 */

public class MarcaController implements ICrud<Marca> {

    private MarcaDAO marcaDAO;

    public MarcaController(Context ctx) {
        this.marcaDAO = new MarcaDAO(ctx);
    }

    @Override
    public int inserir(Marca marca) {
        return marcaDAO.inserir(marca);
    }

    @Override
    public void atualizar(Marca marca) {
        marcaDAO.atualizar(marca);
    }

    @Override
    public void excluir(int id) {
        marcaDAO.excluir(id);
    }

    @Override
    public List<Marca> listarTudo() {
        return marcaDAO.listarTudo();
    }

    @Override
    public Marca listarPorId(int id) {
        return marcaDAO.listarPorId(id);
    }
}
