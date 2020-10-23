package com.example.telas_v1.fragmentosmenu.buscar;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.telas_v1.PostagemActivity;
import com.example.telas_v1.postagemcliente.NovaPostagemClienteActivity;
import com.example.telas_v1.R;
import com.example.telas_v1.metodosusers.MetodosUsers;
import com.example.telas_v1.users.PerfilTrabalhadorActivity;
import com.example.telas_v1.users.UserCliente;
import com.example.telas_v1.users.UserTrabalhador;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class MenuBuscar extends Fragment {

    private MenuBuscarViewModel menubuscar_viewmodel;
    private MetodosUsers metodosUsers = new MetodosUsers();;
    private GroupAdapter adapter;
    private Spinner tipoTrab;
    private UserCliente cliente;
    private UserTrabalhador trabalhador;
    private FloatingActionButton btnAddPost, btnBarra;
    private int barra=0;
    private ConstraintLayout layout;
    private AppBarLayout appbar;
    private LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();

    ObjectAnimator objectAnimator;

    //tudo ok,dia: 40/09/2020 ás 00:24
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        menubuscar_viewmodel = ViewModelProviders.of(this).get(MenuBuscarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu_buscar, container, false);

        final Animation animation_trab = AnimationUtils.loadAnimation(getContext(), R.anim.anim_item_trab);

        RecyclerView rcView = root.findViewById(R.id.rcView);
        final EditText txtCidade = root.findViewById(R.id.txtCidade);
        final EditText txtPreco = root.findViewById(R.id.txtPreco);
        FloatingActionsMenu btnMenu = root.findViewById(R.id.btnMenu);
        tipoTrab = root.findViewById(R.id.tipoTrab);
        btnAddPost = root.findViewById(R.id.btnAddPost);
        btnBarra= root.findViewById(R.id.btnBarra);
        layout = root.findViewById(R.id.layout);
        appbar = root.findViewById(R.id.appbar);

        btnBarra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barra==0) {
                    prepareObjectAnimatorDown(linearOutSlowInInterpolator);
                    barra=1;
                }
                else if (barra==1){
                    prepareObjectAnimatorUp(linearOutSlowInInterpolator);
                    barra = 0;
                }
            }
        });

        adapter = new GroupAdapter();
        rcView.setHasFixedSize(true);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull final Item item, @NonNull View view) {
                view.startAnimation(animation_trab);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
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
                }, 350);
            }
        });


        Button btnPesq = root.findViewById(R.id.btnPesq);
        btnPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente!=null) {
                    String cidade;
                    float preco;

                    if (txtPreco.getText().toString().isEmpty()) preco = 0;
                    else preco = Float.valueOf(txtPreco.getText().toString());
                    if (txtCidade.getText().toString().isEmpty()) cidade = "Qualquer";
                    else cidade = txtCidade.getText().toString();

                    metodosUsers.listarTrabalhador(adapter, tipoTrab.getSelectedItem().toString(), cidade, preco);
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
                    metodosUsers.listarTrabalhador(adapter, tipoTrab.getSelectedItem().toString(), "Qualquer", 0);
                }
            }

            @Override
            public void onResultTrabalhador(UserTrabalhador userTrabalhador) {
                if (userTrabalhador!=null){
                    trabalhador = userTrabalhador;
                    metodosUsers.listarPostagens(adapter);
                    btnAddPost.setVisibility(View.INVISIBLE);
                    btnAddPost.setEnabled(false);
                }
            }
        });
    }

    private void prepareObjectAnimatorDown(TimeInterpolator timeInterpolator){
            float h = (float)layout.getHeight();
            float propertyStart = 0f;
            float propertyEnd = (h/2 - (float)appbar.getHeight()/2)+100;
            String propertyName = "translationY";
            objectAnimator
                    = ObjectAnimator.ofFloat(appbar, propertyName, propertyStart, propertyEnd);
            objectAnimator.setDuration(1700);
            //objectAnimator.setRepeatCount(1);
            //objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            objectAnimator.setInterpolator(timeInterpolator);
            objectAnimator.start();

    }
    private void prepareObjectAnimatorUp(TimeInterpolator timeInterpolator){
        float h = (float)layout.getHeight();
        float propertyEnd = 0f;
        float propertyStart = (h/2 - (float)appbar.getHeight()/2)+100;
        String propertyName = "translationY";
        objectAnimator
                = ObjectAnimator.ofFloat(appbar, propertyName, propertyStart, propertyEnd);
        objectAnimator.setDuration(1700);
//        objectAnimator.setRepeatCount(1);
//        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.start();
    }
}