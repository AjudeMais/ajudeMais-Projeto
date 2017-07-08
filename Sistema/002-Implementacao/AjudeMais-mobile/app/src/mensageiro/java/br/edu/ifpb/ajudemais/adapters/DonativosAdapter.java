package br.edu.ifpb.ajudemais.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Campanha;
import br.edu.ifpb.ajudemais.domain.Donativo;

/**
 * Created by amsv on 08/07/17.
 */

public class DonativosAdapter extends RecyclerView.Adapter<DonativosAdapter.ViewHolder> {

    private List<Donativo> donativos;
    private Context context;

    /**
     * @param donativos
     * @param context
     */
    public DonativosAdapter(List<Donativo> donativos, Context context) {
        this.donativos = donativos;
        this.context = context;

    }

    @Override
    public DonativosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_search_solicitacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonativosAdapter.ViewHolder holder, int position) {
        holder.nomeDonativo.setText(donativos.get(position).getNome());
        holder.descricaoDonativo.setText(donativos.get(position).getDescricao());
        holder.quantidadeDonativo.setText(donativos.get(position).getQuantidade());
        holder.nomeDoador.setText(donativos.get(position).getDoador().getNome());
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (donativos == null) {
            donativos = new ArrayList<>();
        }
        return donativos.size();
    }

    /**
     * @param donativos
     */
    public void setFilter(List<Donativo> donativos) {
        this.donativos = donativos;
        notifyDataSetChanged();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomeDonativo;
        TextView descricaoDonativo;
        TextView quantidadeDonativo;
        TextView nomeDoador;
        /**
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            nomeDonativo = (TextView) itemView.findViewById(R.id.tv_row_donativo_name);
            descricaoDonativo = (TextView) itemView.findViewById(R.id.tv_row_donativo_descricao);
            quantidadeDonativo = (TextView) itemView.findViewById(R.id.tv_row_donativo_quantidade);
            nomeDoador = (TextView) itemView.findViewById(R.id.tv_row_doador_nome);
        }

        /**
         *
         * @param donativo
         */
        public void bind(Donativo donativo) {
            nomeDonativo.setText(donativo.getNome());
            descricaoDonativo.setText(donativo.getDescricao());
            quantidadeDonativo.setText(donativo.getQuantidade());
            nomeDoador.setText(donativo.getDoador().getNome());
        }
    }
}
