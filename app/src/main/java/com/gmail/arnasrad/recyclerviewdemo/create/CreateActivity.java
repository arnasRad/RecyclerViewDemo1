package com.gmail.arnasrad.recyclerviewdemo.create;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmail.arnasrad.recyclerviewdemo.R;

import static com.gmail.arnasrad.recyclerviewdemo.util.BaseActivity.addFragmentToActivity;

public class CreateActivity extends AppCompatActivity {


    private static final String CREATE_FRAG = "CREATE_FRAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        FragmentManager manager = getSupportFragmentManager();

        CreateFragment fragment = (CreateFragment) manager.findFragmentByTag(CREATE_FRAG);

        if (fragment == null) {
            fragment = CreateFragment.newInstance();
        }

        addFragmentToActivity(manager,
                fragment,
                R.id.rootActivityCreate,
                CREATE_FRAG
        );

    }
}
