package com.example.telas_v1.fragmentos.fragmentosmenu.buscar;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.activitys.postagemcliente.*;
import com.example.telas_v1.R;
import com.example.telas_v1.activitys.users.myperfil.MyPerfilClienteActivity;
import com.example.telas_v1.models.MetodosUsers;
import com.example.telas_v1.activitys.users.myperfil.MyPerfilTrabalhadorActivity;
import com.example.telas_v1.activitys.users.otherperfil.PerfilTrabalhadorActivity;
import com.example.telas_v1.models.UserCliente;
import com.example.telas_v1.models.UserTrabalhador;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MenuBuscar extends Fragment {

    private MenuBuscarViewModel menubuscar_viewmodel;
    private MetodosUsers metodosUsers = new MetodosUsers();;
    public GroupAdapter adapter;
    private UserCliente cliente;
    private UserTrabalhador trabalhador;
    private FloatingActionButton btnAddPost;
    private TextView txtNome, txtDia;
    private ImageView imgFoto;
    private Date date;
    private Calendar calendar;
    private List<String> keys =new ArrayList<>();
    private String filtro = "Nenhum Selecionado";

    //tudo ok,dia: 40/09/2020 ás 00:24
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menubuscar_viewmodel = ViewModelProviders.of(this).get(MenuBuscarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_buscar, container, false);


        verificarUser();

        RecyclerView rcView = root.findViewById(R.id.rcViews);
        btnAddPost = root.findViewById(R.id.btnAddPost);
        FloatingActionButton btnFiltrar = root.findViewById(R.id.btnBarra);
        txtNome = root.findViewById(R.id.txtNome);
        txtDia = root.findViewById(R.id.txtDiaSemana);
        imgFoto = root.findViewById(R.id.imgFoto);

        if (getActivity().getIntent().getExtras()!=null) {
            if (getActivity().getIntent().getExtras().getString("filtragemTrab")!=null) {
                filtro = getActivity().getIntent().getExtras().getString("filtragemTrab");
            }
            else if (getActivity().getIntent().getExtras().getSerializable("filtros")!=null){
                keys = ((List<String>) getActivity().getIntent().getExtras().getSerializable("filtros"));
            }else keys=null;
        }

        adapter = new GroupAdapter();
        rcView.setHasFixedSize(true);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull final Item item, @NonNull View view) {
                if (trabalhador!=null){
                    Intent intent = new Intent(getContext(), PostagemActivity.class);
                    MetodosUsers.ListarPostagemViewModel model =(MetodosUsers.ListarPostagemViewModel) item;
                    intent.putExtra("post", model.postagem);
                    intent.putExtra("forma", "menu");
                    intent.putExtra("meT", trabalhador);
                    startActivity(intent);
                }
                else if(cliente!=null){
                    Intent intent = new Intent(getContext(), PerfilTrabalhadorActivity.class);
                    MetodosUsers.ListarTrabalhadorView model = (MetodosUsers.ListarTrabalhadorView) item;
                    intent.putExtra("toT", model.userTrabalhador);
                    intent.putExtra("meC", cliente);
                    intent.putExtra("forma", "menu");
                    startActivity(intent);
                }
            }
        });

        if (trabalhador!=null){
            btnAddPost.setVisibility(View.INVISIBLE);
            btnAddPost.setEnabled(false);
        }

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NovaPostagemClienteActivity.class));
            }
        });

        date = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (calendar != null) {
            txtDia.setText(weekDay(calendar));
        }

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente!=null){
                    startActivity(new Intent(getContext(), FiltrarTrabalhadorActivity.class));
                }
                if (trabalhador!=null){
                }
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trabalhador!=null) {
                    Intent intent = new Intent(getContext(), MyPerfilTrabalhadorActivity.class);
                    intent.putExtra("meT", trabalhador);
                    startActivity(intent);
                }
                else if(cliente!=null){
                    Intent intent = new Intent(getContext(), MyPerfilClienteActivity.class);
                    intent.putExtra("meC", cliente);
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    public static MenuBuscar newInstance() {
        return new MenuBuscar();
    }

    private void verificarUser(){
        metodosUsers.verificarUser(new MetodosUsers.OnResultUser() {
            @Override
            public void onResultCliente(UserCliente userCliente) {
                if (userCliente!=null){
                    cliente = userCliente;
                    if(cliente.getSobremim()==null){
                        Intent intent = new Intent(getContext(), MyPerfilClienteActivity.class);
                        intent.putExtra("meC", cliente);
                        intent.putExtra("first", "first");
                        startActivity(intent);
                        return;
                    }
                    txtNome.setText(cliente.getNome());
                    Picasso.get().load(cliente.getUrlFotoPerfil()).into(imgFoto);
                    metodosUsers.listarTrabalhador(adapter, filtro, keys);
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                    if (trabalhador.getSobreMim()==null){
                        Intent intent = new Intent(getContext(), MyPerfilTrabalhadorActivity.class);
                        intent.putExtra("meT", trabalhador);
                        intent.putExtra("first", "first");
                        startActivity(intent);
                        return;
                    }
                    txtNome.setText(trabalhador.getNome());
                    Picasso.get().load(trabalhador.getUrlFotoPerfil()).into(imgFoto);
                    metodosUsers.listarPostagens(adapter);
                    btnAddPost.setVisibility(View.INVISIBLE);
                    btnAddPost.setEnabled(false);
                }
            }
        });
    }

    public String weekDay(Calendar cal) {
        //String dia = new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
        Date data =  new Date();
        Locale local = new Locale("pt","BR");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM",local);
        return dateFormat.format(data);
    }

    public void voltarCliente(View view){

    }
}