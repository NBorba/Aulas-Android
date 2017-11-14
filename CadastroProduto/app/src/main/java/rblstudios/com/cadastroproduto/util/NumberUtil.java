package rblstudios.com.cadastroproduto.util;

import java.text.DecimalFormat;

/**
 * Criado por renan.lucas on 10/11/2017.
 */

public class NumberUtil {

    public static double parseParaDouble(String texto) {
        try {
            return Double.parseDouble(texto.replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }
}
