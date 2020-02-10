package com.example.dapenduk.ui.daftarpenduduk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapenduk.ui.addpenduduk.AddEditPendudukActivity;
import com.example.dapenduk.R;
import com.example.dapenduk.data.model.Penduduk;
import com.example.dapenduk.ui.adapter.ListPendudukAdapter;
import com.example.dapenduk.utils.Static;
import com.example.dapenduk.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.dapenduk.ui.login.LoginActivity.IS_LOGGED;
import static com.example.dapenduk.ui.login.LoginActivity.SHARED_PREFS;

public class listPendudukActivity extends AppCompatActivity implements View.OnClickListener {

    private listPendudukViewModel listPendudukViewModel;
    private ListPendudukAdapter listPendudukAdapter;
    private RecyclerView rvListPenduduk;
    private CoordinatorLayout coordinatorLayout;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penduduk);

        listPendudukViewModel = new ViewModelProvider(this).get(listPendudukViewModel.class);
        listPendudukAdapter = new ListPendudukAdapter();

        coordinatorLayout = findViewById(R.id.list_penduduk);
        rvListPenduduk = findViewById(R.id.rv_penduduk);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add_penduduk);
        FloatingActionButton fabcount = findViewById(R.id.fab_count_list);

        rvListPenduduk.setLayoutManager(new LinearLayoutManager(this));
        rvListPenduduk.setHasFixedSize(true);
        rvListPenduduk.setAdapter(listPendudukAdapter);

        floatingActionButton.setOnClickListener(this);
        fabcount.setOnClickListener(this);
        if (isLogIn()) {
            enableSwipe();
        }

        listPendudukViewModel.getAllPenduduk().observe(this,
                new Observer<List<Penduduk>>() {
                    @Override
                    public void onChanged(List<Penduduk> penduduks) {
                        listPendudukAdapter.submitList(penduduks);
                        listPendudukAdapter.setPendudukListFull(listPendudukViewModel.getAllPenduduk().getValue());

                    }
                });


        listPendudukAdapter.setOnItemClickListener(
                new ListPendudukAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Penduduk penduduk) {
                        Intent intent = putDataIntent(penduduk);
                        startActivityForResult(intent, EDIT_NOTE_REQUEST);
                    }
                }
        );
    }


    @Override
    protected void onStart() {
        super.onStart();
        listPendudukViewModel.updateData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {

            Penduduk penduduk;
            penduduk = getDataIntent(data);

            listPendudukViewModel.insert(penduduk);
            Utils.showSnackbar(coordinatorLayout,"Data Penduduk Tersimpan");
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            long id = data.getLongExtra(Static.EXTRA_ID, -1);
            if (id == -1) {
                Utils.showSnackbar(coordinatorLayout,"Penduduk Tidak Dapat Diupdate" );
                return;
            }
            Penduduk penduduk;
            penduduk = getDataIntent(data);
            penduduk.setId(id);

            listPendudukViewModel.update(penduduk);
        } else {
            Utils.showSnackbar(coordinatorLayout,"Data Penduduk Tidak Tersimpan");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (isLogIn()) {
            enableAdminMenu(menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            listPendudukViewModel.deleteAll();

            Utils.showSnackbar(coordinatorLayout,"Semua Data Terhapus");
            listPendudukViewModel.updateData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_penduduk:
                startActivityForResult(new Intent(this, AddEditPendudukActivity.class), ADD_NOTE_REQUEST);
                break;
            case R.id.fab_count_list:
                listPendudukViewModel.count();
        }
    }

    private boolean isLogIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED, false);
    }

    private void enableAdminMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Name or Address");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listPendudukAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void enableSwipe() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                listPendudukViewModel.delete(listPendudukAdapter.getPendudukPos(viewHolder.getAdapterPosition()));
                listPendudukViewModel.updateData();
            }
        }).attachToRecyclerView(rvListPenduduk);
    }

    private Penduduk getDataIntent(Intent data) {
        String name = data.getStringExtra(Static.EXTRA_NAME);
        String address = data.getStringExtra(Static.EXTRA_ADDRESS);
        String bornAt = data.getStringExtra(Static.EXTRA_BORN_AT);
        String profession = data.getStringExtra(Static.EXTRA_PROFESSION);
        boolean isMale = data.getBooleanExtra(Static.EXTRA_IS_MALE, false);

        Penduduk penduduk = new Penduduk();

        penduduk.setName(name);
        penduduk.setAddress(address);
        penduduk.setBornAt(bornAt);
        penduduk.setProfession(profession);
        penduduk.setIsMale(isMale);

        return penduduk;

    }

    private Intent putDataIntent(Penduduk penduduk) {

        Intent intent = new Intent(listPendudukActivity.this, AddEditPendudukActivity.class);
        intent.putExtra(Static.EXTRA_NAME, penduduk.getName());
        intent.putExtra(Static.EXTRA_ID, penduduk.getId());
        intent.putExtra(Static.EXTRA_ADDRESS, penduduk.getAddress());
        intent.putExtra(Static.EXTRA_BORN_AT, penduduk.getBornAt());
        intent.putExtra(Static.EXTRA_IS_MALE, penduduk.getIsMale());
        intent.putExtra(Static.EXTRA_PROFESSION, penduduk.getProfession());
        return intent;

    }
}


