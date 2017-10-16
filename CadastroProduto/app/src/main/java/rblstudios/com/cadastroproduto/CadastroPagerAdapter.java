package rblstudios.com.cadastroproduto;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rblstudios.com.cadastroproduto.fragment.CadastroProdutoDadosAdicionais;
import rblstudios.com.cadastroproduto.fragment.CadastroProdutoDadosBasicos;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroPagerAdapter extends FragmentPagerAdapter {

    private static int QTD_ITEMS = 2; // Quantidade de fragmentos - Atualizar toda vez que utilizar um fragmento novo
    private Context mCtx = null;

    public CadastroPagerAdapter(FragmentManager fragmentManager, Context ctx) {
        super(fragmentManager);
        if (ctx != null) {
            mCtx = ctx;
        }
    }

    // Número total de páginas
    @Override
    public int getCount() {
        return QTD_ITEMS;
    }

    // Retorna o fragmento para cada posição do pager
    @Override
    public Fragment getItem(int posicao) {
        switch (posicao) {
            case 0:
                return new CadastroProdutoDadosBasicos();
            case 1:
                return new CadastroProdutoDadosAdicionais();
            default:
                return null;
        }
    }

    // Retorna o título da página
    @Override
    public CharSequence getPageTitle(int posicao) {
        switch (posicao) {
            case 0:
                return mCtx.getString(R.string.title_fragmentdadosbasicos);
            case 1:
                return mCtx.getString(R.string.title_fragmentdadosadicionais);
            default:
                return null;
        }
    }
}
