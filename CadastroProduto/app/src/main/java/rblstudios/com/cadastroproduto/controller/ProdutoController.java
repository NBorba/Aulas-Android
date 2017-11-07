package rblstudios.com.cadastroproduto.controller;

import android.content.Context;

import java.util.List;

import rblstudios.com.cadastroproduto.dal.ProdutoDAO;
import rblstudios.com.cadastroproduto.interfaces.ICrud;
import rblstudios.com.cadastroproduto.model.Produto;

/**
 * Criado por renan.lucas 06/11/2017.
 */

public class ProdutoController implements ICrud<Produto> {

    private ProdutoDAO produtoDAO;

    public ProdutoController (Context ctx) {
        this.produtoDAO = new ProdutoDAO(ctx);
    }

    @Override
    public int inserir(Produto produto) {
        return produtoDAO.inserir(produto);
    }

    @Override
    public void atualizar(Produto produto) {
        produtoDAO.atualizar(produto);
    }

    @Override
    public void excluir(int id) {
        produtoDAO.excluir(id);
    }

    @Override
    public List<Produto> listarTudo() {
        return produtoDAO.listarTudo();
    }

    @Override
    public Produto listarPorId(int id) {
        return produtoDAO.listarPorId(id);
    }
}
