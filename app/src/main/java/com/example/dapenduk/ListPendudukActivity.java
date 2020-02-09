package com.example.dapenduk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapenduk.data.model.Penduduk;
import com.example.dapenduk.ui.adapter.ListPendudukAdapter;
import com.example.dapenduk.viewmodel.PendudukViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListPendudukActivity extends AppCompatActivity implements View.OnClickListener {


    private PendudukViewModel pendudukViewModel;
    private ListPendudukAdapter listPendudukAdapter;
    private static final String TAG = "updatebos";
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penduduk);

        RecyclerView rvListPenduduk = findViewById(R.id.rv_penduduk);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add_penduduk);
        FloatingActionButton fabcount = findViewById(R.id.fab_count_list);

        pendudukViewModel = new ViewModelProvider(this).get(PendudukViewModel.class);
        listPendudukAdapter = new ListPendudukAdapter();

        rvListPenduduk.setLayoutManager(new LinearLayoutManager(this));
        rvListPenduduk.setHasFixedSize(true);
        rvListPenduduk.setAdapter(listPendudukAdapter);


        pendudukViewModel.getAllPenduduk().observe(this, new Observer<List<Penduduk>>() {
            @Override
            public void onChanged(List<Penduduk> penduduks) {
                listPendudukAdapter.setPendudukList(penduduks);
                Log.d(TAG, "onChanged: ");
//                Toast.makeText(ListPendudukActivity.this, "Onchange", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(this);
        fabcount.setOnClickListener(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pendudukViewModel.delete(listPendudukAdapter.getpendudukPos(viewHolder.getAdapterPosition()));
                Toast.makeText(ListPendudukActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                pendudukViewModel.updateData();
            }
        }).attachToRecyclerView(rvListPenduduk);

        listPendudukAdapter.setOnItemClickListener(new ListPendudukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Penduduk penduduk) {
                Intent intent = new Intent(ListPendudukActivity.this, AddEditPendudukActivity.class);
                intent.putExtra(AddEditPendudukActivity.EXTRA_NAME, penduduk.getName());
                intent.putExtra(AddEditPendudukActivity.EXTRA_ID, penduduk.getId());
                intent.putExtra(AddEditPendudukActivity.EXTRA_ADDRESS, penduduk.getAddress());
                intent.putExtra(AddEditPendudukActivity.EXTRA_BORN_AT, penduduk.getBornAt());
                intent.putExtra(AddEditPendudukActivity.EXTRA_IS_MALE, penduduk.getIsMale());
                intent.putExtra(AddEditPendudukActivity.EXTRA_PROFESSION, penduduk.getProfession());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        pendudukViewModel.updateData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra(AddEditPendudukActivity.EXTRA_NAME);
            String address = data.getStringExtra(AddEditPendudukActivity.EXTRA_ADDRESS);
            String bornAt = data.getStringExtra(AddEditPendudukActivity.EXTRA_BORN_AT);
            String profession = data.getStringExtra(AddEditPendudukActivity.EXTRA_PROFESSION);
            boolean isMale = data.getBooleanExtra(AddEditPendudukActivity.EXTRA_IS_MALE, false);
            Penduduk penduduk = new Penduduk();
            penduduk.setName(name);
            penduduk.setAddress(address);
            penduduk.setBornAt(bornAt);
            penduduk.setProfession(profession);
            penduduk.setIsMale(isMale);

            pendudukViewModel.insert(penduduk);
            Toast.makeText(this, "Data Penduduk Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data!= null) {
            long id = data.getLongExtra(AddEditPendudukActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Pendduk can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditPendudukActivity.EXTRA_NAME);
            String address = data.getStringExtra(AddEditPendudukActivity.EXTRA_ADDRESS);
            String bornAt = data.getStringExtra(AddEditPendudukActivity.EXTRA_BORN_AT);
            String profession = data.getStringExtra(AddEditPendudukActivity.EXTRA_PROFESSION);
            boolean isMale = data.getBooleanExtra(AddEditPendudukActivity.EXTRA_IS_MALE, false);
            Penduduk penduduk = new Penduduk(id, name, isMale, address, bornAt, profession);
            pendudukViewModel.update(penduduk);
        } else {
            Toast.makeText(this, "Penduduk Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            pendudukViewModel.deleteAll();
            Toast.makeText(this, "All Note Deleted", Toast.LENGTH_SHORT).show();
            pendudukViewModel.updateData();
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
                pendudukViewModel.count();
        }
    }
}


