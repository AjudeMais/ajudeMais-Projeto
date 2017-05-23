package br.edu.ifpb.ajudemais.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Endereco;

/**
 * Created by amsv on 21/05/17.
 */

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoAdapter.ViewHolder> {

    private List<Endereco> enderecos;
    private Context context;

    public EnderecoAdapter(List<Endereco> enderecos, Context context) {
        this.enderecos = enderecos;
        this.context = context;
    }

    @Override
    public EnderecoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail_endereco_mensageiro, null, false);
        return new EnderecoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.logradouroName.setText(enderecos.get(position).getLogradouro());
        holder.ufName.setText(enderecos.get(position).getUf());
    }

    @Override
    public int getItemCount() {
        return enderecos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView logradouroName;
        TextView ufName;

        public ViewHolder(View itemView) {
            super(itemView);
            logradouroName = (TextView) itemView.findViewById(R.id.tv_logradouro_name);
            ufName = (TextView) itemView.findViewById(R.id.tv_uf_name);
        }
    }

}
