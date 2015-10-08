package com.goldenpie.devs.kievrest.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    private AlertDialog dialog;
    @Getter
    @Setter
    private String city;

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        setTitle(R.string.settings);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.act_settings_city_choose)
    protected void onCityChoose() {
        if (helper.getCitesList() != null && !helper.getCitesList().isEmpty()) {
            final String[] ITEMS = new String[helper.getCitesList().size()];
            for (int i = 0; i < helper.getCitesList().size(); i++) {
                ITEMS[i] = helper.getCitesList().get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, ITEMS);
            if (dialog == null)
                dialog = new AlertDialog.Builder(this).setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setCity(ITEMS[i]);
                        onCityChosen();
                    }
                }).setNegativeButton(R.id.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();

            dialog.show();
        } else {
            service.loadCites(preferences.getLang());
        }
    }

    private void onCityChosen() {
        for (int j = 0; j < helper.getCitesList().size(); j++) {
            if (helper.getCitesList().get(j).getName().equals(getCity())) {
                preferences.setCurrentCity(helper.getCitesList().get(j).getSlug());
                preferences.setCurrentCityName(helper.getCitesList().get(j).getName());
                break;
            }
        }
        helper.getDataMap().clear();
        Intent i = new Intent(TownyApplication.getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        this.finish();
    }

    public void onEvent(ArrayList<CityModel> cityModels) {
        helper.getDataMap().put(ModelTypeEnum.CITES, cityModels);
        onCityChoose();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
