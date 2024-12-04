package com.fitness.fitnessapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<String> queries;

    public SettingsFragment(List<String> queries) {
        this.queries = queries;
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment(new ArrayList<String>());
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        LinearLayout exerciseContainer = rootView.findViewById(R.id.exerciseContainer);

        exerciseContainer.removeAllViews();

        for (String exerciseName : queries) {
            // Create a new TextView for each string
            TextView exerciseTextView = new TextView(getContext());
            exerciseTextView.setText("Query " + exerciseName);
            exerciseTextView.setTextSize(18);
            exerciseTextView.setTextColor(Color.WHITE);
            exerciseTextView.setTypeface(null, Typeface.BOLD);
            exerciseTextView.setPadding(0, 8, 0, 4);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 12);
            exerciseTextView.setLayoutParams(params);

            exerciseContainer.addView(exerciseTextView);
        }
        return rootView;
    }
}