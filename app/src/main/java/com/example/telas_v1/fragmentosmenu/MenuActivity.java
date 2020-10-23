package com.example.telas_v1.fragmentosmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.fragmentoscriarconta.passodois.StepTwo;
import com.example.telas_v1.fragmentoscriarconta.passoum.StepOne;
import com.example.telas_v1.fragmentosmenu.buscar.MenuBuscar;
import com.example.telas_v1.fragmentosmenu.contratar.MenuContratos;
import com.example.telas_v1.fragmentosmenu.perfil.MenuPerfil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_menu, new MenuBuscar());
        ft.commit();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_buscar: {
                Fragment mFragment = MenuBuscar.newInstance();
                openFragment(mFragment);
                break;
            }
            case R.id.nav_contratos: {
                Fragment mFragment = MenuContratos.newInstance();
                openFragment(mFragment);
                break;
            }
            case R.id.nav_perfil: {
                Fragment mFragment = MenuPerfil.newInstance();
                openFragment(mFragment);
                break;
            }
        }
        return true;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_menu, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
