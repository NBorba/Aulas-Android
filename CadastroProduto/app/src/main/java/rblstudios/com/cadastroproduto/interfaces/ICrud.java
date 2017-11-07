package rblstudios.com.cadastroproduto.interfaces;

import java.util.List;

/**
 * Criado por renan.lucas em 06/11/2017.
 */

public interface ICrud<T> {
    int inserir(T t);
    void atualizar(T t);
    void excluir(int id);
    List<T> listarTudo();
    T listarPorId(int id);
}
