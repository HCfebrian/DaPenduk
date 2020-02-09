package com.example.dapenduk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dapenduk.data.model.Penduduk;
import com.example.dapenduk.viewmodel.PendudukViewModel;

public class AddPendudukActivity extends AppCompatActivity implements View.OnClickListener {

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

        pendudukViewModel =  new ViewModelProvider(this).get(PendudukViewModel.class);

        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnCont = findViewById(R.id.btn_Count);
        btnCont.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

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

                Penduduk newPenduduk = new Penduduk();
                newPenduduk.setName(name);
                newPenduduk.setAddress(address);
                newPenduduk.setBornAt(bornAt);
                newPenduduk.setProfession(profession);
                newPenduduk.setIsMale(isMale);

                pendudukViewModel.insert(newPenduduk);
//                pendudukViewModel.updateData();
                finish();
                break;

            case R.id.btn_Count:
                pendudukViewModel.count();
                break;
        }


    }
}
