package com.example.telas_v1.activitys.postagemcliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_v1.R;
import com.example.telas_v1.models.Postagem;
import com.example.telas_v1.models.PostagemAux;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hootsuite.nachos.NachoTextView;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditarPostagemActivity extends AppCompatActivity {

    private boolean ax=false;
    private EditText txtPrice;
    private Button btnEditar;
    private List<String> keys;
    private Spinner spnCategory;
    private PostagemAux postagem;
    private ProgressBar progress;
    private GroupAdapter adapter;
    private ImageView imgLocation;
    private NachoTextView txtTags;
    private FloatingActionButton btnAddPhoto;
    private TextView txtCategory, txtInfo, txtData;
    private List<String> uriFoto = new ArrayList<>();
    private static List<Uri>uriAx = new ArrayList<>();
    private String title, description, littleDescription,filter;
    private TextInputEditText txtTitle, txtDescription, txtLittleDescription;
    private double price, latitude, longitude, latitudeax=-1, longitudeax=-1;

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
        progress = findViewById(R.id.progress);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        txtData = findViewById(R.id.txtData);

        List<String> profs = Arrays.asList(getResources().getStringArray(R.array.profs));
        spnCategory.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, profs));

        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (initPost()){
            initPost();
            setAllTrue();
            ax=!ax;
        }else {
            setInfoPost();
            setAllFalse();
        }
    }

    private boolean initPost(){
        boolean ax=false;
        if (Postagem.getTitulo()!=null){
            txtTitle.setText(Postagem.getTitulo());
            ax=true;
        }if (Postagem.getDescricao()!=null){
            txtDescription.setText(Postagem.getDescricao());
            ax= true;
        }if (Postagem.getMiniDescricao()!=null){
            txtLittleDescription.setText(Postagem.getMiniDescricao());
            ax= true;
        }if (Postagem.getPreco()!=0){
            txtPrice.setText(String.valueOf(Postagem.getPreco()));
            ax= true;
        }if (Postagem.getKeys()!=null){
            txtTags.setText(Postagem.getKeys());
            ax= true;
        }if (Postagem.getData()!=null){
            txtData.setText(Postagem.getData());
            ax= true;
        }if (Postagem.getFotos()!=null){
            txtInfo.setText("");
            for (String url: Postagem.getFotos()){
                adapter.add(new ListPhotos(url));
            }
            ax= true;
        }
        return ax;
    }

    private void setInfoPost(){
        txtTitle.setText(postagem.getTitulo());
        txtDescription.setText(postagem.getDescricao());
        txtLittleDescription.setText(postagem.getMiniDescricao());
        txtTags.setText(postagem.getKeys());
        txtPrice.setText(String.valueOf(postagem.getPreco()));
        txtCategory.setText(postagem.getFiltroFixo());
        txtData.setText(postagem.getData());
        keys = new ArrayList<>();
        if (!postagem.getFotos().isEmpty()) {
            txtInfo.setText("");
            for (String url : postagem.getFotos()) {
                adapter.add(new ListPhotos(url));
            }
            uriFoto = postagem.getFotos();
        }

        if (latitudeax!=-1 && longitude!=-1){
            latitudeax = getIntent().getExtras().getDouble("latitude");
            longitudeax= getIntent().getExtras().getDouble("longitude");
        }else{
            longitude = postagem.getLongitude();
            latitude = postagem.getLatitude();
        }
    }

    public void validateEdit(View view){
        ax=!ax;
        adjustViews();
    }

    private void adjustViews(){
        if (!ax){
            if (validateChanges()) {
                progress.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Espere um momento...", Toast.LENGTH_LONG).show();
                saveChanges();
                return;
            }
        }
        setAllTrue();
    }

    public void addPhoto(View view){
        if (ax) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 0);
        }
    }

    private void saveChanges() {
        getValues();
        postagem.setTitulo(title);
        postagem.setDescricao(description);
        postagem.setMiniDescricao(littleDescription);
        postagem.setKeys(keys);
        postagem.setLatitude(latitude);
        postagem.setLongitude(longitude);
        postagem.setPreco(price);
        postagem.setFiltroFixo(filter);

        int cont=uriFoto.size();
        if (uriAx.size()!=0) {
            int totalFotos = uriAx.size() + uriFoto.size();
            for (Uri uuri : uriAx) {
                cont++;
                uploadarFotos(uuri, totalFotos, cont);
            }
        }else saveFirebase();;
    }

    public void uploadarFotos(Uri uuri, final int totalFotos, int cont){
        String nmr = "imagem"+cont;
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/postagens/"+postagem.getIdPost()+"/"+nmr);
        ref.putFile(uuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri){
                        uriFoto.add(uri.toString());
                        if(totalFotos == uriFoto.size()){
                            postagem.setFotos(uriFoto);
                            saveFirebase();
                        }
                    }
                });
            }
        });
    }

    private void saveFirebase(){
        FirebaseFirestore.getInstance().collection("postagens").document(postagem.getIdPost()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                btnEditar.setText("Editar Postagem");
                Toast.makeText(getApplicationContext(), "Postagem alterada com sucesso!", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
                initComponents();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Ocorreu algum erro... tente novamente", Toast.LENGTH_LONG).show();
                setAllTrue();
                Log.e("TESTE", e.getMessage(), e);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==0 && resultCode==RESULT_OK){
            Uri uri = data.getData();
            uriAx.add(uri);
            adapter.add(new ListPhotos(uri.toString()));
            adapter.notifyDataSetChanged();
            txtInfo.setText("");
        }
    }

    private boolean validateChanges(){
        getValues();
        if (!title.isEmpty() && !description.isEmpty() && !littleDescription.isEmpty() && !filter.isEmpty()){
            if (price!=0) {
                return true;
            }else Toast.makeText(this, "O preço não poder ser 0", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void getValues(){
        keys = new ArrayList<>();
        title = txtTitle.getText().toString();
        description = txtDescription.getText().toString();
        littleDescription = txtLittleDescription.getText().toString();
        filter = spnCategory.getSelectedItem().toString();
        price = Double.valueOf(txtPrice.getText().toString());
        for (String key: txtTags.getChipValues()){
            keys.add(key);
        }
    }

    private void setAllFalse(){
        txtTitle.setEnabled(false);
        txtDescription.setEnabled(false);
        txtLittleDescription.setEnabled(false);
        txtTags.setEnabled(false);
        txtPrice.setEnabled(false);
        txtCategory.setVisibility(View.VISIBLE);
        spnCategory.setVisibility(View.INVISIBLE);
        btnAddPhoto.setVisibility(View.INVISIBLE);
    }

    private void setAllTrue(){
        btnEditar.setText("Salvar a Edição");
        txtTitle.setEnabled(true);
        txtDescription.setEnabled(true);
        txtLittleDescription.setEnabled(true);
        txtTags.setEnabled(true);
        txtPrice.setEnabled(true);
        txtCategory.setVisibility(View.INVISIBLE);
        btnAddPhoto.setVisibility(View.VISIBLE);
        spnCategory.setVisibility(View.VISIBLE);
    }

    public void backEditPost(View view){
        if (ax) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tem certeza?")
                    .setMessage("Você perderá tudo o que editou até aqui")
                    .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setNegativeButton("Cancelar", null).create().show();
        }else
            finish();
    }

    @Override
    public void onBackPressed() {
        if (ax) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tem certeza?")
                    .setMessage("Você perderá tudo o que editou até aqui")
                    .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditarPostagemActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("Cancelar", null).create().show();
        }else
            super.onBackPressed();
    }

    private void getInfoPost(){
        if (!txtTitle.getText().toString().trim().isEmpty()){
            Postagem.setTitulo(txtTitle.getText().toString().trim());
        }if (!txtDescription.getText().toString().trim().isEmpty()){
            Postagem.setDescricao(txtDescription.getText().toString().trim());
        }if (!txtLittleDescription.getText().toString().trim().isEmpty()){
            Postagem.setMiniDescricao(txtLittleDescription.getText().toString().trim());
        }if (!txtPrice.getText().toString().trim().isEmpty()){
            Postagem.setPreco(Double.valueOf(txtPrice.getText().toString().trim()));
        }if (!txtData.getText().toString().trim().isEmpty()) {
            Postagem.setData(txtData.getText().toString().trim());
        }if (!txtTags.getText().toString().isEmpty()){
            keys.clear();
            for (String chip: txtTags.getChipValues()){
                keys.add(chip);
            }
            Postagem.setKeys(keys);
        }
        if (!uriFoto.isEmpty()){
            Postagem.setFotos(uriFoto);
        }
    }

    public void openMap(View view){
        if (ax){
            getInfoPost();
            Intent intent = new Intent(this, MapNewPostActivity.class);
            intent.putExtra("edit", "edit");
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        }else{
            final double latitude = postagem.getLatitude();
            final double longitude = postagem.getLongitude();

            String strUri = "http://maps.google.com/maps?q=loc:" +
                    latitude + "," + longitude;
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(strUri));

            intent.setClassName("com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity");
            EditarPostagemActivity.this.startActivity(intent);
        }
    }

    public void deletePost(View view){
        progress.setVisibility(View.VISIBLE);
        postagem.setStatus("Excluída");
        FirebaseFirestore.getInstance().collection("postagens").document(postagem.getIdPost()).set(postagem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Postagem excluída, obrigado!", Toast.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
                initComponents();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Ocorreu algum erro... tente novamente", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ListPhotos extends Item<ViewHolder>{

        final private String url;

        private ListPhotos(String url) {
            this.url = url;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            ImageView img= viewHolder.itemView.findViewById(R.id.imgPostNew);
            Picasso.get().load(url).into(img);
        }

        @Override
        public int getLayout() {
            return R.layout.item_post_img;
        }
    }
}