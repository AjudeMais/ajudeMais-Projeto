package br.edu.ifpb.ajudemais.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.DisponibilidadeHorarioAdapter;
import br.edu.ifpb.ajudemais.asycnTasks.UpdateEstadoDonativoTask;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.domain.EstadoDoacao;
import br.edu.ifpb.ajudemais.enumarations.Estado;
import br.edu.ifpb.ajudemais.utils.CustomToast;

/**
 * <p>
 * <b>{@link DonativoDetailFragment}</b>
 * </p>
 * <p>
 * Fragmento de detalhes para donativo.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

public class DonativoDetailFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Donativo donativo;
    private TextView descricaoDonativo;
    private TextView nomeInstituicao;
    private TextView stateDoacao;
    private EstadoDoacao estadoDoacao;
    private UpdateEstadoDonativoTask updateEstadoDonativoTask;
    private Button btnCancelDoacao;
    private Button btnListDisp;

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_donativo_detail, container, false);

        Intent intentDonativo = getActivity().getIntent();
        donativo = (Donativo) intentDonativo.getSerializableExtra("Donativo");
        setHasOptionsMenu(true);
        return view;
    }


    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        descricaoDonativo = (TextView) getView().findViewById(R.id.tv_description);
        nomeInstituicao = (TextView) getView().findViewById(R.id.tv_instituicao_name);
        stateDoacao = (TextView) getView().findViewById(R.id.tv_donative_estado_lb);
        btnCancelDoacao = (Button) getView().findViewById(R.id.btnCancelaDoacao);
        btnCancelDoacao.setOnClickListener(this);
        descricaoDonativo.setText(donativo.getDescricao());
        nomeInstituicao.setText(getString(R.string.doado_to) + " " + donativo.getCategoria().getInstituicaoCaridade().getNome());

        btnListDisp = (Button) view.findViewById(R.id.btn_lista_disponibilidade);
        btnListDisp.setOnClickListener(this);
        setAtrAddressIntoCard(donativo.getEndereco());
        validateAndSetStateDoacao();

    }

    /**
     * Valida o estado da doação e seta o estado na label.
     */
    private void validateAndSetStateDoacao() {
        for (EstadoDoacao estado : donativo.getEstadosDaDoacao()) {
            if (estado.getAtivo() != null && estado.getAtivo()) {

                if (estado.getEstadoDoacao().name().equals(Estado.CANCELADO.name())) {
                    stateDoacao.setBackground(getContext().getDrawable(R.drawable.screen_border_cancelado));
                    stateDoacao.setTextColor(Color.WHITE);

                } else if (estado.getEstadoDoacao().name().equals(Estado.DISPONIBILIZADO.name())) {
                    stateDoacao.setBackground(getContext().getDrawable(R.drawable.screen_border_disponibilizado));
                    stateDoacao.setTextColor(Color.parseColor("#665e5e"));
                }

                if (!estado.getEstadoDoacao().name().equals(Estado.DISPONIBILIZADO.name())) {
                    btnCancelDoacao.setVisibility(View.GONE);
                }
                stateDoacao.setText(estado.getEstadoDoacao().name());
                this.estadoDoacao = estado;
            }
        }

    }


    /**
     * Seta endereço no cardview
     */
    private void setAtrAddressIntoCard(Endereco endereco) {
        CardView cardView = (CardView) getView().findViewById(R.id.componentAddress);
        ((TextView) cardView.findViewById(R.id.tv_logradouro_name)).setText(endereco.getLogradouro());
        ((TextView) cardView.findViewById(R.id.tv_bairro)).setText(endereco.getBairro());
        ((TextView) cardView.findViewById(R.id.tv_number)).setText(endereco.getNumero());
        ((TextView) cardView.findViewById(R.id.tv_cep_name)).setText(endereco.getCep());
        ((TextView) cardView.findViewById(R.id.tv_city)).setText(endereco.getLocalidade());
        ((TextView) cardView.findViewById(R.id.tv_uf_name)).setText(endereco.getUf());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancelaDoacao) {
            showConfirmDialog();
        } else if (v.getId() == R.id.btn_lista_disponibilidade) {
            showDialogListDisponibilidade();
        }
    }

    /**
     * Executa Asycn task para atualizar o estado do donativo;
     */
    private void executeUpdateEstadoDoacaoTask(final Donativo donativo) {
        updateEstadoDonativoTask = new UpdateEstadoDonativoTask(getContext(), donativo);
        updateEstadoDonativoTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {
                validateAndSetStateDoacao();
                CustomToast.getInstance(getContext()).createSuperToastSimpleCustomSuperToast(getString(R.string.doacao_cancelada));
            }
        };
        updateEstadoDonativoTask.execute();
    }

    /**
     * Confirme dialog para cancelar uma doação.
     */
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.cancel_doacao));
        builder.setMessage(getString(R.string.dialog_message));
        String positiveText = getString(R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < donativo.getEstadosDaDoacao().size(); i++) {
                            if (donativo.getEstadosDaDoacao().get(i).getAtivo() != null && donativo.getEstadosDaDoacao().get(i).getAtivo()) {
                                if (donativo.getEstadosDaDoacao().get(i).getEstadoDoacao().name().equals(Estado.DISPONIBILIZADO.name())) {
                                    donativo.getEstadosDaDoacao().get(i).setAtivo(false);
                                }

                            }
                        }
                        EstadoDoacao estadoDoacao = new EstadoDoacao();
                        estadoDoacao.setData(new Date());
                        estadoDoacao.setEstadoDoacao(Estado.CANCELADO);
                        estadoDoacao.setAtivo(true);
                        donativo.getEstadosDaDoacao().add(estadoDoacao);
                        executeUpdateEstadoDoacaoTask(donativo);
                    }
                });

        String negativeText = getString(R.string.no);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.getInstance(getContext()).createSuperToastSimpleCustomSuperToast(getString(R.string.operacao_cancela));
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog para mostrar a lista de disponibilidade para coletar doação
     */
    private void showDialogListDisponibilidade() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_list_disponibilidades, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);

        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycle_view_list_dispo);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        DisponibilidadeHorarioAdapter disponibilidadeHorarioAdapter = new DisponibilidadeHorarioAdapter(donativo.getHorariosDisponiveis(), getContext());
        recyclerView.setAdapter(disponibilidadeHorarioAdapter);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setNegativeButton(R.string.close,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}

