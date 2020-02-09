package com.example.dapenduk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dapenduk.viewmodel.PendudukViewModel;

public class AddEditPendudukActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "com.example.dapenduk.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.dapenduk.EXTRA_NAME";
    public static final String EXTRA_IS_MALE = "com.example.dapenduk.EXTRA_ISMALE";
    public static final String EXTRA_ADDRESS = "com.example.dapenduk.EXTRA_ADDRESS";
    public static final String EXTRA_BORN_AT = "com.example.dapenduk.EXTRA_BORN_AT";
    public static final String EXTRA_PROFESSION = "com.example.dapenduk.EXTRA_PROFESSION";
    private EditText etName, etAdress, etBornAt,etProffesion;
    private RadioGroup rgGender;
    private PendudukViewModel pendudukViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_penduduk);


        etName = findViewById(R.id.et_name);
        etAdress = findViewById(R.id.et_address);
        etBornAt = findViewById(R.id.et_born_at);
        etProffesion = findViewById(R.id.et_profession);
        rgGender = findViewById(R.id.rg_gender);
        RadioButton rbMale = findViewById(R.id.rb_is_male);
        RadioButton rbFemale = findViewById(R.id.rb_is_female);

        pendudukViewModel =  new ViewModelProvider(this).get(PendudukViewModel.class);

        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnCont = findViewById(R.id.btn_Count);
        btnCont.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Penduduk");
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etAdress.setText(intent.getStringExtra(EXTRA_ADDRESS));
            etBornAt.setText(intent.getStringExtra(EXTRA_BORN_AT));
            etProffesion.setText(intent.getStringExtra(EXTRA_PROFESSION));
            if (intent.getBooleanExtra(EXTRA_IS_MALE,false)) {
                rbFemale.isChecked();
            } else {
                rbMale.isChecked();
            }
        }else{
            setTitle("Add Penduduk");
        }

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

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                String name = etName.getText().toString();
                String address = etAdress.getText().toString();
                String bornAt = etBornAt.getText().toString();
                String profession = etProffesion.getText().toString();
                int selectedId = rgGender.getCheckedRadioButtonId();
                boolean isMale = (selectedId == R.id.rb_is_male);

                if(     name.trim().isEmpty() || address.trim().isEmpty() ||
                        bornAt.trim().isEmpty()|| profession.trim().isEmpty()){
                    Toast.makeText(this, "Tolong isi kolom yang wajib diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_NAME,name);
                data.putExtra(EXTRA_ADDRESS,name);
                data.putExtra(EXTRA_BORN_AT,name);
                data.putExtra(EXTRA_PROFESSION,name);
                data.putExtra(EXTRA_IS_MALE,isMale);

                long id = getIntent().getLongExtra(EXTRA_ID,-1);
                if(id != -1){
                    data.putExtra(EXTRA_ID,id);
                }
                setResult(RESULT_OK,data);

                finish();
                break;

            case R.id.btn_Count:
                pendudukViewModel.count();
                break;
        }


    }
}
