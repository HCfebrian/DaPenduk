package com.example.dapenduk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dapenduk.data.model.Penduduk;
import com.example.dapenduk.ui.adapter.ListPendudukAdapter;
import com.example.dapenduk.viewmodel.PendudukViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListPendudukActivity extends AppCompatActivity implements View.OnClickListener {

    

    private PendudukViewModel pendudukViewModel;
    private RecyclerView rvListPenduduk;
    private ListPendudukAdapter listPendudukAdapter;
    private FloatingActionButton floatingActionButton,fabcount;
    private static final String TAG = "updatebos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penduduk);

        rvListPenduduk = findViewById(R.id.rv_penduduk);
        floatingActionButton = findViewById(R.id.fab_add_penduduk);
        fabcount = findViewById(R.id.fab_count_list);

        pendudukViewModel =  new ViewModelProvider(this).get(PendudukViewModel.class);
        listPendudukAdapter = new ListPendudukAdapter();

        rvListPenduduk.setLayoutManager(new LinearLayoutManager(this));
        rvListPenduduk.setHasFixedSize(true);
        rvListPenduduk.setAdapter(listPendudukAdapter);


        pendudukViewModel.getAllPenduduk().observe(this, new Observer<List<Penduduk>>() {
            @Override
            public void onChanged(List<Penduduk> penduduks) {
                listPendudukAdapter.setPendudukList(penduduks);
                Log.d(TAG, "onChanged: ");
                Toast.makeText(ListPendudukActivity.this, "Onchange", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(this);
        fabcount.setOnClickListener(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pendudukViewModel.delete(listPendudukAdapter.getpendudukPos(direction));
                Toast.makeText(ListPendudukActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                pendudukViewModel.updateData();
            }
        }).attachToRecyclerView(rvListPenduduk);

    }

    @Override
    protected void onStart() {
        super.onStart();
        pendudukViewModel.updateData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                pendudukViewModel.deleteAll();
                Toast.makeText(this, "All Note Deleted", Toast.LENGTH_SHORT).show();
                pendudukViewModel.updateData();
        return true;
        default:
            return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_penduduk:
                startActivity(new Intent(this, AddPendudukActivity.class));
                break;
            case R.id.fab_count_list:
                pendudukViewModel.count();
        }
    }
}


