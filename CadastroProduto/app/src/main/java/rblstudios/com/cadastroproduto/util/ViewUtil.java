package rblstudios.com.cadastroproduto.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class ViewUtil {
    /**
     * Verifica se na atividade passada via parâmetro exista alguma view com foco
     * e esconde o teclado
     *
     * @param activity Instância da atividade que contém a view do teclado
     */
    public static void esconderTeclado(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        // Acha a view que está com foco
        View view = activity.getCurrentFocus();

        // Se nenhuma view tem foco, cria uma nova para poder esconder o teclado
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Cria e mostra um alert customizado
     *
     * @param ctx           Contexto em que o aplicativo se encontra
     * @param titulo        Titulo do alerta
     * @param mensagem      Mensagem do alerta
     * @param mensagemBotao Mensagem do botão do alerta
     * @param cancelavel    Indica se o alerta é cancelavel ou não
     */
    public static void criaEMostraAlert(Context ctx, String titulo, String mensagem, String mensagemBotao, boolean cancelavel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setCancelable(cancelavel);

        builder.setPositiveButton(
                mensagemBotao,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alerta = builder.create();
        alerta.show();
    }
}