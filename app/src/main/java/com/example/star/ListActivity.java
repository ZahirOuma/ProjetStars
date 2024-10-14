package com.example.star;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.star.adapter.StarAdapter;
import com.example.star.beans.Star;
import com.example.star.service.StarService;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        service = StarService.getInstance();
        init();

        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configuration de la Toolbar
        // Pour éviter les avertissements sur l'ID manquant
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init() {
        service.create(new Star("Kate Bosworth", "https://resizing.flixster.com/XidxlyeQhyurO9IpTs7in5a3pOA=/218x280/v2/https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/173408_v9_bc.jpg", 3.5f));
        service.create(new Star("George Clooney", "https://resizing.flixster.com/N7qO-x-etsC6uzphu-aIOjZRjXE=/218x280/v2/https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/23213_v9_bb.jpg", 3));
        service.create(new Star("Michelle Rodriguez", "https://fr.web.img2.acsta.net/c_310_420/pictures/19/05/22/10/29/0914375.jpg", 5));
        service.create(new Star("Johnny Depp", "https://media.vogue.fr/photos/5c2f6ae1b2652c7c0ba8babe/2:3/w_1920,c_limit/johnny_depp__nouveau_visage_des_parfums_christian_dior_9749.jpeg", 1));
        service.create(new Star("Harold Perrineau", "https://fr.web.img6.acsta.net/c_310_420/pictures/16/05/13/16/41/471123.jpg", 5));
        service.create(new Star("Millie Bobby Brown", "https://static.wikia.nocookie.net/strangerthings/images/4/42/Millie_Bobby_Brown_2024.png/revision/latest/scale-to-width-down/327?cb=20240323043905&path-prefix=fr", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gonfle (inflate) le menu XML que vous avez défini dans res/menu/menu.xml
        getMenuInflater().inflate(R.menu.menu, menu);

        // Trouve l'élément du menu avec l'ID "app_bar_search"
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        // Convertit l'élément de menu en une SearchView (barre de recherche)
        SearchView searchView = (SearchView) menuItem.getActionView();

        // Définit un écouteur d'événements pour la SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Action à effectuer lorsque l'utilisateur soumet sa requête de recherche
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }

        });

        return true; // Indique que le menu a été correctement créé
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }



}