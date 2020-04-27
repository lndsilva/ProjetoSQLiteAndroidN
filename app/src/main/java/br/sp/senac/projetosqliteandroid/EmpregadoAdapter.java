package br.sp.senac.projetosqliteandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        final Empregados empregados = listaEmpregados.get(position);

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

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarEmpregado(empregados);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Você tem certeza?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM funcionarios WHERE id = ?";
                        meuBancoDeDados.execSQL(sql, new Integer[]{empregados.getId()});
                        recarregarEmpregadosDB();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;


    }

    public void alterarEmpregado(final Empregados empregados) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.caixa_alterar_empregado, null);
        builder.setView(view);

        final EditText txtEditarFuncionario = view.findViewById(R.id.txtEditarFuncionario);
        final EditText txtEditarSalario = view.findViewById(R.id.txtEditarSalario);
        final Spinner spnDepartamentos = view.findViewById(R.id.spnDepartamentos);

        txtEditarFuncionario.setText(empregados.getNome());
        txtEditarSalario.setText(String.valueOf(empregados.getSalario()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarFuncionario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtEditarFuncionario.getText().toString().trim();
                String salario = txtEditarSalario.getText().toString().trim();
                String depto = spnDepartamentos.getSelectedItem().toString().trim();

                if (nome.isEmpty()) {
                    txtEditarFuncionario.setError("Nome está em branco");
                    txtEditarFuncionario.requestFocus();
                    return;
                }
                if (salario.isEmpty()) {
                    txtEditarSalario.setError("Salario está em branco");
                    txtEditarSalario.requestFocus();
                    return;
                }

                String sql = "UPDATE funcionarios SET nome = ?, departamento = ?, salario = ? WHERE id = ?";
                meuBancoDeDados.execSQL(sql,
                        new String[]{nome, depto, salario, String.valueOf(empregados.getId())});
                Toast.makeText(mCtx, "Empregado alterado com sucesso!!!", Toast.LENGTH_LONG).show();

                recarregarEmpregadosDB();

                dialog.dismiss();
            }
        });

    }

    public void recarregarEmpregadosDB() {
        Cursor cursorEmpregados = meuBancoDeDados.rawQuery("SELECT * FROM funcionarios", null);
        if (cursorEmpregados.moveToFirst()) {
            listaEmpregados.clear();
            do {
                listaEmpregados.add(new Empregados(
                        cursorEmpregados.getInt(0),
                        cursorEmpregados.getString(1),
                        cursorEmpregados.getString(2),
                        cursorEmpregados.getString(3),
                        cursorEmpregados.getDouble(4)
                ));
            } while (cursorEmpregados.moveToNext());
        }
        cursorEmpregados.close();
        notifyDataSetChanged();
    }
}
