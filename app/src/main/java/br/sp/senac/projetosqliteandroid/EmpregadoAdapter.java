package br.sp.senac.projetosqliteandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class EmpregadoAdapter extends ArrayAdapter<Empregados> {

    Context mCtx;
    int listaLayoutRes;
    List<Empregados> listaEmpregados;
    SQLiteDatabase meuBancoDeDados;

    public EmpregadoAdapter(Context mCtx, int listaLayoutRes, List<Empregados> listaEmpregados, SQLiteDatabase meuBancoDeDados) {
        super(mCtx, listaLayoutRes, listaEmpregados);

        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.listaEmpregados = listaEmpregados;
        this.meuBancoDeDados = meuBancoDeDados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        Empregados empregados = listaEmpregados.get(position);

        TextView txtViewNome = view.findViewById(R.id.txtNomeViewFuncionario);
        TextView txttViewDepto = view.findViewById(R.id.txtDepartamentoViewfuncionario);
        TextView txtViewSalario = view.findViewById(R.id.txtSalarioViewFuncionario);
        TextView txtViewDataEntrada = view.findViewById(R.id.txtEntradaviewFuncionario);

        txtViewNome.setText(empregados.getNome());
        txttViewDepto.setText(empregados.getDepto());
        txtViewSalario.setText(String.valueOf(empregados.getSalario()));
        txtViewDataEntrada.setText(empregados.getDataEntrada());

        Button btnExcluir = view.findViewById(R.id.btnExcluirViewFunciorio);
        Button btnEditar = view.findViewById(R.id.btnEditarViewFuncionario);

        return view;


    }
}
