package rblstudios.com.cadastroproduto.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Criado por renan.lucas em 23/10/2017.
 */

public class Util {
    /**
     * Converte um bitmap para byteArray para passar imagem via intent
     *
     * @param bitmap imagem que sera convertida
     * @return byteArray da imagem
     */
    public static byte[] converteBitmapParaByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Converte um byteArray para bitmap
     *
     * @param byteArray byteArray de uma imagem recebida via intent
     * @return bitmap convertido
     */
    public static Bitmap converteByteArrayParaBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static String retornaMoeda(String chave) {
        switch (chave.toUpperCase()) {
            case "BRL":
                return "R$";
            case "USD":
                return "$";
            default:
                return "R$";
        }
    }
}

