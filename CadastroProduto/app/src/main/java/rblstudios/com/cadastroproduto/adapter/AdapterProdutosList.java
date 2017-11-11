package rblstudios.com.cadastroproduto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.Util;

/**
 * Criado por renan.lucas on 10/11/2017.
 */

public class AdapterProdutosList extends RecyclerView.Adapter<AdapterProdutosList.ViewHolder> {

    private List<Produto> produtos;
    private RecyclerViewClickListener recyclerViewClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduto;
        TextView txtMarcaProduto;
        TextView txtNomeProduto;
        TextView txtPrecoVenda;
        TextView txtAtivo;

        private ViewHolder(View itemView) {
            super(itemView);
            this.imgProduto = (ImageView) itemView.findViewById(R.id.ItemListaProduto_imgProduto);
            this.txtMarcaProduto = (TextView) itemView.findViewById(R.id.ItemListaProduto_marcaProduto);
            this.txtNomeProduto = (TextView) itemView.findViewById(R.id.ItemListaProduto_nomeProduto);
            this.txtPrecoVenda = (TextView) itemView.findViewById(R.id.ItemListaProduto_precoVenda);
            this.txtAtivo = (TextView) itemView.findViewById(R.id.ItemListaProduto_ativoProduto);
        }
    }

    public AdapterProdutosList(List<Produto> produtos, RecyclerViewClickListener clickListener) {
        this.produtos = produtos;
        this.recyclerViewClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_produto, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ImageView imgProduto = holder.imgProduto;
        TextView txtMarcaProduto = holder.txtMarcaProduto;
        TextView txtNomeProduto = holder.txtNomeProduto;
        TextView txtPrecoVenda = holder.txtPrecoVenda;
        TextView txtAtivo = holder.txtAtivo;

        imgProduto.setImageBitmap(Util.converteByteArrayParaBitmap(produtos.get(position).getImagem()));
        txtMarcaProduto.setText(produtos.get(position).getMarca());
        txtNomeProduto.setText(produtos.get(position).getNome());
        txtPrecoVenda.setText(String.valueOf(produtos.get(position).getPrecoVenda()));
        txtAtivo.setText(String.valueOf(produtos.get(position).getAtivo()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}