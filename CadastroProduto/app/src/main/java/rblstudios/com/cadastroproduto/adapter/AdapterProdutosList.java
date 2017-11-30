package rblstudios.com.cadastroproduto.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.controller.ProdutoController;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.Util;

/**
 * Criado por renan.lucas on 10/11/2017.
 */

public class AdapterProdutosList extends RecyclerView.Adapter<AdapterProdutosList.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_VAZIO = 2;

    private List<Produto> produtos;
    private RecyclerViewClickListener recyclerViewClickListener;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduto;
        private TextView txtMarcaProduto;
        private TextView txtNomeProduto;
        private TextView txtPrecoVenda;
        private TextView txtAtivo;
        private Button btnExcluir;

        private ViewHolder(View itemView) {
            super(itemView);
            this.imgProduto = (ImageView) itemView.findViewById(R.id.ItemListaProduto_imgProduto);
            this.txtMarcaProduto = (TextView) itemView.findViewById(R.id.ItemListaProduto_marcaProduto);
            this.txtNomeProduto = (TextView) itemView.findViewById(R.id.ItemListaProduto_nomeProduto);
            this.txtPrecoVenda = (TextView) itemView.findViewById(R.id.ItemListaProduto_precoVenda);
            this.txtAtivo = (TextView) itemView.findViewById(R.id.ItemListaProduto_ativoProduto);
            this.btnExcluir = (Button) itemView.findViewById(R.id.ItemListaProduto_btnExcluir);
        }
    }

    public AdapterProdutosList(Context ctx, List<Produto> produtos, RecyclerViewClickListener clickListener) {
        this.ctx = ctx;
        this.produtos = produtos;
        this.recyclerViewClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_produto, parent, false);
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
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Produto produtoSelecionado = produtos.get(viewHolder.getAdapterPosition());
                                    ProdutoController produtoController = new ProdutoController(ctx);
                                    produtoController.excluir(produtoSelecionado.getId());
                                    produtos.remove(viewHolder.getAdapterPosition());
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_produto_vazio, parent, false);
            viewHolder = new ViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_NORMAL) {
            ImageView imgProduto = holder.imgProduto;
            TextView txtMarcaProduto = holder.txtMarcaProduto;
            TextView txtNomeProduto = holder.txtNomeProduto;
            TextView txtPrecoVenda = holder.txtPrecoVenda;
            TextView txtAtivo = holder.txtAtivo;

            imgProduto.setImageBitmap(Util.converteByteArrayParaBitmap(produtos.get(position).getImagem()));
            txtMarcaProduto.setText(produtos.get(position).getMarca());
            txtNomeProduto.setText(produtos.get(position).getNome());
            txtPrecoVenda.setText(String.valueOf(produtos.get(position).getPrecoVenda()));
            txtAtivo.setText(produtos.get(position).getAtivo() == 1 ? ctx.getString(R.string.title_ativo) : ctx.getString(R.string.title_inativo));
        }
    }

    @Override
    public int getItemCount() {
        if (produtos.size() == 0) {
            return 1;
        } else {
            return produtos.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (produtos.size() == 0) {
            return VIEW_TYPE_VAZIO;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
}