package fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.astudios.disastermanagement.R;

import essential.Essential;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment3 extends Fragment {


    public fragment3() {
        // Required empty public constructor
    }

    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        Button saveGenBtn = (Button) view.findViewById(R.id.saveGenBtnf3);

        sharedPreferences = getActivity().getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
        saveGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getBoolean(Essential.f3_Key, false) == false) {
                    //Toast.makeText(getContext(), "fdf", Toast.LENGTH_LONG).show();
                    int percen = sharedPreferences.getInt(Essential.Percent_Key, -1);
                    if (percen > -1) {
                        percen += 5;
                    } else {
                        percen = 5;
                    }

                    sharedPreferences.edit().putInt(Essential.Percent_Key, percen).apply();
                    sharedPreferences.edit().putBoolean(Essential.f3_Key, true).apply();
                }
            }
        });


        return view;
    }

}
