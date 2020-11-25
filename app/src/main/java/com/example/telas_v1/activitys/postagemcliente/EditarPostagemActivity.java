package com.example.telas_v1.activitys.postagemcliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.telas_v1.R;
import com.example.telas_v1.models.PostagemAux;
import com.google.android.material.textfield.TextInputEditText;
import com.hootsuite.nachos.NachoTextView;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class EditarPostagemActivity extends AppCompatActivity {

    private boolean ax=false;
    private EditText txtPrice;
    private Spinner spnCategory;
    private Button btnEditar;
    private PostagemAux postagem;
    private GroupAdapter adapter;
    private ImageView imgLocation;
    private NachoTextView txtTags;
    private TextView txtCategory, txtInfo;
    private TextInputEditText txtTitle, txtDescription, txtLittleDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_postagem);
        initComponents();
    }

    private void initComponents(){
        postagem = getIntent().getExtras().getParcelable("post");

        adapter = new GroupAdapter();
        RecyclerView rcView = findViewById(R.id.rcView);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtInfo = findViewById(R.id.txtInfo);
        txtLittleDescription = findViewById(R.id.txtLittleDescription);
        txtTags = findViewById(R.id.txtTags);
        btnEditar = findViewById(R.id.btnEdit);
        txtCategory = findViewById(R.id.txtCategory);
        spnCategory = findViewById(R.id.spnCategory);
        txtPrice = findViewById(R.id.txtPrice);
        imgLocation = findViewById(R.id.imgLocation);

        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setInfoPost();
    }

    private void setInfoPost(){
        txtTitle.setText(postagem.getTitulo());
        txtDescription.setText(postagem.getDescricao());
        txtLittleDescription.setText(postagem.getMiniDescricao());
        txtTags.setText(postagem.getKeys());
        txtPrice.setText(String.valueOf(postagem.getPreco()));
        List<String> profs = Arrays.asList(getResources().getStringArray(R.array.profs));
        spnCategory.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, profs));
        txtCategory.setText(postagem.getFiltroFixo());
        if (!postagem.getFotos().isEmpty()) {
            txtInfo.setText("");
            for (String url : postagem.getFotos()) {
                adapter.add(new ListPhotos(url));
            }
        }
    }

    public void validateEdit(View view){
        ax=!ax;
        adjustViews();
    }

    private void adjustViews(){
        if (!ax){
            btnEditar.setText("Editar Postagem");
            txtTitle.setFocusable(false);
            txtDescription.setFocusable(false);
            txtLittleDescription.setFocusable(false);
            txtTags.setFocusable(false);
            txtPrice.setFocusable(false);
            spnCategory.setVisibility(View.INVISIBLE);
        }else{
            btnEditar.setText("Salvar a Edição");
            txtTitle.setFocusable(true);
            txtDescription.setFocusable(true);
            txtLittleDescription.setFocusable(true);
            txtTags.setFocusable(true);
            txtPrice.setFocusable(true);
            spnCategory.setVisibility(View.VISIBLE);
        }
    }

    private class ListPhotos extends Item<ViewHolder>{

        final private String url;

        private ListPhotos(String url) {
            this.url = url;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView img= viewHolder.itemView.findViewById(R.id.imgList);
            Picasso.get().load(url).into(img);
        }

        @Override
        public int getLayout() {
            return R.layout.item_foto_list;
        }
    }
}