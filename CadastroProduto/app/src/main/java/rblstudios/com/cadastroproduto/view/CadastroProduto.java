package rblstudios.com.cadastroproduto.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import rblstudios.com.cadastroproduto.CadastroPagerAdapter;
import rblstudios.com.cadastroproduto.fragment.CadastroProdutoDadosBasicos;
import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.ViewUtil;

public class CadastroProduto extends AppCompatActivity implements FragmentoCallback {
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        achaViewsPorID();

        // Cria o adaptador que irá retornar os fragmentos para a atividade
        FragmentPagerAdapter mAdaptadorPager;
        mAdaptadorPager = new CadastroPagerAdapter(getSupportFragmentManager(), this);

        // Define o view pager com o adaptador de seções
        mViewPager.setAdapter(mAdaptadorPager);

        // Define o tab layout com o view pager acima
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void achaViewsPorID() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    /**
     * Recebe parametro da interface implementada em CadastroClienteVendas.
     * Este parametro é a ID da tela na qual o adaptador de tela deve ir.
     *
     * @param posicao int da posição da tela
     * @see CadastroProdutoDadosBasicos
     */
    @Override
    public void posicaoDeTela(int posicao) {
        mViewPager.setCurrentItem(posicao);
    }

    @Override
    public void onBackPressed() {
        // Caso o view pager esteja no primeiro fragmento, volta para a atividade anterior,
        // caso o contrário, volta para o fragmento anterior
        switch (mViewPager.getCurrentItem()) {
            case 0:
                super.onBackPressed();
                break;
            default:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
        }
    }

    //****************************DESAPARECER TECLADO AO TOCAR NA TELA****************************//
    private View focusedViewOnActionDown;
    private boolean touchWasInsideFocusedView;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                focusedViewOnActionDown = getCurrentFocus();
                if (focusedViewOnActionDown != null) {
                    final Rect rect = new Rect();
                    final int[] coordinates = new int[2];

                    focusedViewOnActionDown.getLocationOnScreen(coordinates);

                    rect.set(coordinates[0], coordinates[1],
                            coordinates[0] + focusedViewOnActionDown.getWidth(),
                            coordinates[1] + focusedViewOnActionDown.getHeight());

                    final int x = (int) ev.getX();
                    final int y = (int) ev.getY();

                    touchWasInsideFocusedView = rect.contains(x, y);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (focusedViewOnActionDown != null) {
                    // dispatch to allow new view to (potentially) take focus
                    final boolean consumed = super.dispatchTouchEvent(ev);

                    final View currentFocus = getCurrentFocus();

                    // if the focus is still on the original view and the touch was inside that view,
                    // leave the keyboard open.  Otherwise, if the focus is now on another view and that view
                    // is an EditText, also leave the keyboard open.
                    if (currentFocus == focusedViewOnActionDown) {
                        if (touchWasInsideFocusedView) {
                            return consumed;
                        }
                    } else if (currentFocus instanceof EditText) {
                        return consumed;
                    }

                    // the touch was outside the originally focused view and not inside another EditText,
                    // so close the keyboard
                    ViewUtil.esconderTeclado(this);

                    return consumed;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    //**************************FIM DESAPARECER TECLADO AO TOCAR NA TELA**************************//
}