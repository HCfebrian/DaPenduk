package com.example.dapenduk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static com.example.dapenduk.LoginActivity.IS_LOGGED;
import static com.example.dapenduk.LoginActivity.SHARED_PREFS;

public class AddEditPendudukActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "com.example.dapenduk.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.dapenduk.EXTRA_NAME";
    public static final String EXTRA_IS_MALE = "com.example.dapenduk.EXTRA_ISMALE";
    public static final String EXTRA_ADDRESS = "com.example.dapenduk.EXTRA_ADDRESS";
    public static final String EXTRA_BORN_AT = "com.example.dapenduk.EXTRA_BORN_AT";
    public static final String EXTRA_PROFESSION = "com.example.dapenduk.EXTRA_PROFESSION";

    private EditText etName, etAddress, etBornAt, etProfession;
    private RadioGroup rgGender;
    private Button btnSubmit;
    private RadioButton rbMale, rbFemale;
    private CoordinatorLayout addPendudukLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_penduduk);

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etBornAt = findViewById(R.id.et_born_at);
        etProfession = findViewById(R.id.et_profession);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_is_male);
        rbFemale = findViewById(R.id.rb_is_female);
        btnSubmit = findViewById(R.id.btn_submit);
        Button btnCont = findViewById(R.id.btn_Count);
        addPendudukLayout = findViewById(R.id.add_penduduk_layout);

        btnCont.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Penduduk");
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etAddress.setText(intent.getStringExtra(EXTRA_ADDRESS));
            etBornAt.setText(intent.getStringExtra(EXTRA_BORN_AT));
            etProfession.setText(intent.getStringExtra(EXTRA_PROFESSION));
            if (intent.getBooleanExtra(EXTRA_IS_MALE,false)) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }
        }else{
            setTitle("Add Penduduk");
        }

        Log.d("cobabos", "onCreate: "+etName.getBackground().toString());
        if(!isLogIn()){
            disableEdit();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String bornAt = etBornAt.getText().toString();
                String profession = etProfession.getText().toString();
                int selectedId = rgGender.getCheckedRadioButtonId();
                boolean isMale = (selectedId == R.id.rb_is_male);

                if(     name.trim().isEmpty() || address.trim().isEmpty() ||
                        bornAt.trim().isEmpty()|| profession.trim().isEmpty()){
                    Utils.showSnackbar(addPendudukLayout,"Isi Data Yang Wajib Diisi");
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_NAME,name);
                data.putExtra(EXTRA_ADDRESS,address);
                data.putExtra(EXTRA_BORN_AT,bornAt);
                data.putExtra(EXTRA_PROFESSION,profession);
                data.putExtra(EXTRA_IS_MALE,isMale);

                long id = getIntent().getLongExtra(EXTRA_ID,-1);
                if(id != -1){
                    data.putExtra(EXTRA_ID,id);
                }
                setResult(RESULT_OK,data);

                finish();
                break;

            case R.id.btn_Count:
                break;
        }

    }

    private void disableEdit(){
        etName.setInputType(InputType.TYPE_NULL);
        etName.setTextIsSelectable(false);
        etName.setBackground(null);

        etBornAt.setInputType(InputType.TYPE_NULL);
        etBornAt.setTextIsSelectable(false);
        etBornAt.setBackground(null);

        etAddress.setInputType(InputType.TYPE_NULL);
        etAddress.setTextIsSelectable(false);
        etAddress.setBackground(null);

        etProfession.setInputType(InputType.TYPE_NULL);
        etProfession.setTextIsSelectable(false);
        etProfession.setBackground(null);

        rbFemale.setClickable(false);
        rbMale.setClickable(false);

        btnSubmit.setClickable(false);
        btnSubmit.setVisibility(View.INVISIBLE);

    }

    private void enableEdit(){

        //ToDo : enable this
        etName.setInputType(InputType.TYPE_CLASS_TEXT);
        etName.setTextIsSelectable(true);


        etBornAt.setInputType(InputType.TYPE_NULL);
        etBornAt.setTextIsSelectable(false);
        etBornAt.setBackground(null);

        etAddress.setInputType(InputType.TYPE_NULL);
        etAddress.setTextIsSelectable(false);
        etAddress.setBackground(null);

        etProfession.setInputType(InputType.TYPE_NULL);
        etProfession.setTextIsSelectable(false);
        etProfession.setBackground(null);

        rbFemale.setClickable(false);
        rbMale.setClickable(false);

        btnSubmit.setClickable(false);
        btnSubmit.setVisibility(View.INVISIBLE);

    }

    private boolean isLogIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED, false);
    }

}
