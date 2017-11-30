package rblstudios.com.cadastroproduto.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.controller.MarcaController;
import rblstudios.com.cadastroproduto.model.Marca;

/**
 * Criado por renan.lucas on 10/11/2017.
 */

public class AdapterMarcasList extends RecyclerView.Adapter<AdapterMarcasList.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_VAZIO = 2;

    private List<Marca> marcas;
    private RecyclerViewClickListener recyclerViewClickListener;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMarcaProduto;
        private Button btnExcluir;

        private ViewHolder(View itemView) {
            super(itemView);
            this.txtMarcaProduto = (TextView) itemView.findViewById(R.id.ItemListaMarca_nomeMarca);
            this.btnExcluir = (Button) itemView.findViewById(R.id.ItemListaMarca_btnExcluir);
        }
    }

    public AdapterMarcasList(Context ctx, List<Marca> marcas, RecyclerViewClickListener clickListener) {
        this.ctx = ctx;
        this.marcas = marcas;
        this.recyclerViewClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_marca, parent, false);
            viewHolder = new ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            });

            viewHolder.btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    Marca marcaSelecionada = marcas.get(viewHolder.getAdapterPosition());
                                    MarcaController marcaController = new MarcaController(ctx);
                                    marcaController.excluir(marcaSelecionada.getId());
                                    marcas.remove(viewHolder.getAdapterPosition());
                                    notifyItemRemoved(viewHolder.getAdapterPosition());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage(ctx.getString(R.string.mensagem_confirmacaoexclusao)).setPositiveButton(ctx.getString(R.string.botaoSim), dialogClickListener)
                            .setNegativeButton(ctx.getString(R.string.botaoNao), dialogClickListener).show();
                }
            });
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_marca_vazio, parent, false);
            viewHolder = new ViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_NORMAL) {
            TextView txtMarcaProduto = holder.txtMarcaProduto;
            txtMarcaProduto.setText(marcas.get(position).getNome());
        }
    }

    @Override
    public int getItemCount() {
        if (marcas.size() == 0) {
            return 1;
        } else {
            return marcas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (marcas.size() == 0) {
            return VIEW_TYPE_VAZIO;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
}