package com.intacta.doctoring.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;
import java.util.List;

public class SpinnerAdapter  extends BaseAdapter {

    private Context context;
    private List<Cliente> clienteList;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context,List<Cliente> clienteList) {
        this.context = context;
        this.clienteList = clienteList;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clienteList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView =  layoutInflater.inflate(R.layout.spinner_layout,null);
        TextView label = convertView.findViewById(R.id.txt_cliente);

        label.setText(clienteList.get(position).getNome());

        return convertView;
    }
}
