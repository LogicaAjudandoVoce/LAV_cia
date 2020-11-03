package com.example.telas_v1.fragmentosmenu.buscar;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_v1.PostagemActivity;
import com.example.telas_v1.postagemcliente.NovaPostagemClienteActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.otherperfil.PerfilTrabalhadorActivity;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MenuBuscar extends Fragment {

    private MenuBuscarViewModel menubuscar_viewmodel;
    private MetodosUsers metodosUsers = new MetodosUsers();;
    private GroupAdapter adapter;
    private UserCliente cliente;
    private UserTrabalhador trabalhador;
    private FloatingActionButton btnAddPost;
    private TextView txtNome, txtDia;
    private ImageView imgFoto;
    private Date date;
    private Calendar calendar;
    //private LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();

    ObjectAnimator objectAnimator;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menubuscar_viewmodel = ViewModelProviders.of(this).get(MenuBuscarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_buscar, container, false);

        RecyclerView rcView = root.findViewById(R.id.rcView);
        btnAddPost = root.findViewById(R.id.btnAddPost);
        FloatingActionButton btnFiltrar = root.findViewById(R.id.btnBarra);
        txtNome = root.findViewById(R.id.txtNome);
        txtDia = root.findViewById(R.id.txtDiaSemana);
        imgFoto = root.findViewById(R.id.imgFoto);

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

        verificarUser();

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
            Log.d("TESTE", calendar.toString());
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
                    txtNome.setText(cliente.getNome());
                    Picasso.get().load(cliente.getUrlFotoPerfil()).into(imgFoto);
                    metodosUsers.listarTrabalhador(adapter, "Nenhum Selecionado", "Qualquer", 0);
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                    txtNome.setText(trabalhador.getNome());
                    Picasso.get().load(trabalhador.getUrlFotoPerfil()).into(imgFoto);
                    metodosUsers.listarPostagens(adapter);
                    btnAddPost.setVisibility(View.INVISIBLE);
                    btnAddPost.setEnabled(false);
                }
            }
        });
    }

//    private void prepareObjectAnimatorDown(TimeInterpolator timeInterpolator){
//            float h = (float)layout.getHeight();
//            float propertyStart = 0f;
//            float propertyEnd = (h/2 - (float)appbar.getHeight()/2)+100;
//            String propertyName = "translationY";
//            objectAnimator
//                    = ObjectAnimator.ofFloat(appbar, propertyName, propertyStart, propertyEnd);
//            objectAnimator.setDuration(1700);
//            //objectAnimator.setRepeatCount(1);
//            //objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
//            objectAnimator.setInterpolator(timeInterpolator);
//            objectAnimator.start();
//
//    }
//    private void prepareObjectAnimatorUp(TimeInterpolator timeInterpolator){
//        float h = (float)layout.getHeight();
//        float propertyEnd = 0f;
//        float propertyStart = (h/2 - (float)appbar.getHeight()/2)+100;
//        String propertyName = "translationY";
//        objectAnimator
//                = ObjectAnimator.ofFloat(appbar, propertyName, propertyStart, propertyEnd);
//        objectAnimator.setDuration(1700);
////        objectAnimator.setRepeatCount(1);
////        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
//        objectAnimator.setInterpolator(timeInterpolator);
//        objectAnimator.start();
//    }

    public String weekDay(Calendar cal) {
        //String dia = new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
        Date data =  new Date();
        Locale local = new Locale("pt","BR");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM",local);
        return dateFormat.format(data);
    }
}