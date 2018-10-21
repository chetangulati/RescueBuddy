package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.astudios.disastermanagement.R;

import essential.Essential;

public class fragment1 extends Fragment {

    private android.support.design.widget.TextInputEditText homeAddressgen;
    private android.support.design.widget.TextInputEditText secMbNgen;
    private android.support.design.widget.TextInputEditText secEmlgen;
    private android.widget.Button saveGenBtn;
    private SharedPreferences sharedPreferences;

    public fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_ui, container, false);
        this.saveGenBtn = (Button) view.findViewById(R.id.saveGenBtn);
        sharedPreferences = getActivity().getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
        saveGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getBoolean(Essential.f1_Key, false) == false) {
                    //Toast.makeText(getContext(), "fdf", Toast.LENGTH_LONG).show();
                    int percen = sharedPreferences.getInt(Essential.Percent_Key, -1);
                    if (percen > -1) {
                        percen += 5;
                    } else {
                        percen = 5;
                    }

                    sharedPreferences.edit().putInt(Essential.Percent_Key, percen).apply();
                    sharedPreferences.edit().putBoolean(Essential.f1_Key, true).apply();
                }
            }
        });


        return view;

    }

}
