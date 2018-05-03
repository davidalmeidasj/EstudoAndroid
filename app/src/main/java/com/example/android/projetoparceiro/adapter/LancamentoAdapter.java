package com.example.android.projetoparceiro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projetoparceiro.R;
import com.example.android.projetoparceiro.data.AppDatabase;
import com.example.android.projetoparceiro.data.Lancamento;
import com.example.android.projetoparceiro.data.Pessoa;
import com.example.android.projetoparceiro.util.DataUtil;
import com.example.android.projetoparceiro.util.Helper;

import java.util.ArrayList;

public class LancamentoAdapter extends ArrayAdapter<Lancamento> implements View.OnClickListener {

    private ArrayList<Lancamento> dataSet;
    private Context mContext;
    private AppDatabase appDatabase;

    @Override
    public void onClick(View v) {

    }

    public LancamentoAdapter(ArrayList<Lancamento> data, Context context) {
        super(context, R.layout.row_item_lancamentos, data);
        this.dataSet = data;
        this.mContext = context;
        this.appDatabase = AppDatabase.Companion.getAppDatabase(this.mContext);
    }


    // View lookup cache
    private static class ViewHolder {
        TextView mDataView;
        TextView mTipoView;
        TextView mClienteView;
        TextView mValorView;
        ImageView mIconImageView;
        Lancamento mItem;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Lancamento lancamento = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_lancamentos, parent, false);
            viewHolder.mDataView = (TextView) convertView.findViewById(R.id.lancItemData);
            viewHolder.mTipoView = (TextView) convertView.findViewById(R.id.lancItemTipo);
            viewHolder.mClienteView = (TextView) convertView.findViewById(R.id.lancItemCliente);
            viewHolder.mValorView = (TextView) convertView.findViewById(R.id.lancItemValor);
            viewHolder.mIconImageView = (ImageView) convertView.findViewById(R.id.lancItemImg);


//            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        this.lastPosition = position;

        String clienteNome = "Cliente Não informado";

//        viewHolder.mDataView.setText(lancamento.getDataExecucao());

        if (lancamento != null) {

            if (lancamento.getPessoa() != null) {
                clienteNome = lancamento.getPessoa().getNome();
            } else if (lancamento.getPessoaId() != null) {
                Pessoa pessoa = appDatabase.pessoaDao().getPessoaByIdLocal(lancamento.getPessoaId());
                clienteNome = pessoa.getNome();
            }

            if (lancamento.getDataExecucao() != null) {
                DataUtil dataUtil = new Helper().getDataUtil();

                viewHolder.mDataView.setText(dataUtil.formatarData(lancamento.getDataExecucao()) + " " + dataUtil.formatarHora(lancamento.getDataExecucao()));
            }

            viewHolder.mTipoView.setText(lancamento.tipoString());
            viewHolder.mClienteView.setText(clienteNome);
            viewHolder.mValorView.setText("R$ " + new Helper().getNumeroUtil().formatarMoeda(lancamento.getValor()));
            viewHolder.mIconImageView.setImageResource(lancamento.tipoImage());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
