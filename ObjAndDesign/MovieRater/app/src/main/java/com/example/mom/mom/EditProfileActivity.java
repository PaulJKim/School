package com.example.mom.mom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mom.mom.Model.Major;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.Presenter.EditProfilePresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.EditProfileView;

import java.util.Arrays;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView {

    private User m_oCurrentUser; //User that has logged in
    private TextView m_oUsername;
    private EditText m_oName;
    private EditText m_oBio;
    private Spinner m_oMajor;

    private EditProfilePresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get fields
        m_oUsername = (TextView) findViewById(R.id.txtUsername);
        m_oName = (EditText) findViewById(R.id.etName);
        m_oBio = (EditText) findViewById(R.id.etBio);
        m_oMajor = (Spinner) findViewById(R.id.spMajor);
        //EditText oMajor = (EditText) findViewById(R.id.etMajor);

        //Set spinner
        setMajorSpinner();

        m_oPresenter = new EditProfilePresenter(this, new NavigatorModel(this), this);

        m_oPresenter.onCreate();
    }

    private void setMajorSpinner() {
        List<Major> items = Arrays.asList(Major.values());

        Spinner oMajor = (Spinner) findViewById(R.id.spMajor);

        ArrayAdapter<Major> oAdapter = new ArrayAdapter<Major>(this, R.layout.support_simple_spinner_dropdown_item, items);
        oMajor.setAdapter(oAdapter);
    }

    /**
     * Sets profile attributes
     * @param view View that called this method
     */
    public void onSetPress(View view) {
        /*EditText oName = (EditText) findViewById(R.id.etName);
        EditText oBio = (EditText) findViewById(R.id.etBio);
        Spinner oMajor = (Spinner) findViewById(R.id.spMajor);

        String szName = oName.getText().toString();
        String szBio = oBio.getText().toString();
        Major eMajor = (Major) oMajor.getSelectedItem();

        m_oCurrentUser.setName(szName);
        m_oCurrentUser.setMajor(eMajor);
        m_oCurrentUser.setBio(szBio);*/

        m_oPresenter.onClick();

        //returnHome();
    }

    /**
     * Cancels edit
     * @param view view that called this method
     */
    public void onCancelPress(View view) {
        confirmExit();
    }

    /**
     * Returns back to home activity
     */
    private void returnHome() {
        //Return to home
        Intent oIntent = new Intent(this, ProfileActivity.class);
        oIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //oIntent.putExtra(LoginActivity.EXTRA_USERNAME, m_oCurrentUser.getUsername());

        startActivity(oIntent);

        finish();
    }

    /**
     * On layout click. Clears soft keyboard. //Push
     * @param view view that is passed in
     */
    public void layoutClear(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        confirmExit();
    }

    private void confirmExit() {
        //TODO: Fix this confirmation
        //Check if changes have been made

        //Popup confirmation
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you want really want to exit? Changes will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnHome();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void setName(String szName) {
        m_oName.setText(szName);
    }

    @Override
    public void setBio(String szBio) {
        m_oBio.setText(szBio);
    }

    @Override
    public void setMajor(Major eMajor) {
        m_oMajor.setSelection(eMajor.ordinal());
    }

    @Override
    public String getName() {
        return m_oName.getText().toString();
    }

    @Override
    public String getBio() {
        return m_oBio.getText().toString();
    }

    @Override
    public Major getMajor() {
        return (Major) m_oMajor.getSelectedItem();
    }
}
