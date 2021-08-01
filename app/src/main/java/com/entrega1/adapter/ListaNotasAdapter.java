package com.entrega1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entrega1.dto.NotaDTO;
import com.entrega1.formCadastro.R;

import java.util.List;

public class ListaNotasAdapter extends BaseAdapter {

    private Context context;
    private List<NotaDTO> listaNotas;

    private static class ListaNotasHolder {
        public TextView textViewBimestre;
        public TextView textViewDisciplina;
        public TextView textViewAtividade;
        public TextView textViewNota;
    }

    public ListaNotasAdapter(Context context, List<NotaDTO> listaNotas) {
        this.context = context;
        this.listaNotas = listaNotas;

    }

    @Override
    public int getCount() {
        return listaNotas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaNotas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListaNotasHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_notas, parent, false);

            holder = new ListaNotasHolder();

            holder.textViewBimestre = convertView.findViewById(R.id.textView1AdapterNotas);
            holder.textViewDisciplina = convertView.findViewById(R.id.textView2AdapterNotas);
            holder.textViewAtividade = convertView.findViewById(R.id.textView3AdapterNotas);
            holder.textViewNota = convertView.findViewById(R.id.textView4AdapterNotas);

            convertView.setTag(holder);

        } else {
            holder = (ListaNotasHolder) convertView.getTag();
        }

        holder.textViewBimestre.setText(listaNotas.get(position).getBimestre());
        holder.textViewDisciplina.setText(listaNotas.get(position).getDisciplina());
        holder.textViewAtividade.setText(listaNotas.get(position).getAtividade());
        holder.textViewNota.setText(listaNotas.get(position).getNota());

        return convertView;
    }
}
