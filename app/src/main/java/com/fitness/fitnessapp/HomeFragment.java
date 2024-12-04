package com.fitness.fitnessapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "queries";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private EditText input;
    private List<Exercise> exerciseList;
    private LinearLayout exerciseContainer;
    private List<String> queries;

    public HomeFragment(List<String> queries) {
        // Required empty public constructor
        exerciseList = new ArrayList<>();
        this.queries = queries;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment(new ArrayList<String>());
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Button createButton = rootView.findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWorkout(v);
            }
        });
        exerciseContainer = rootView.findViewById(R.id.exerciseContainer);
        input = rootView.findViewById(R.id.workoutQuery);
        return rootView;
    }


    public void createWorkout(View view) {
        String query = input.getText().toString();
        OkHttpClient client = new OkHttpClient();
        String url = "https://workout--api-72242e0a11da.herokuapp.com/api/search_exercises?query=" + query;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
                getActivity().runOnUiThread(() ->
                        Toast.makeText(view.getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse the response body as JSON
                    String responseBody = response.body().string();
                    queries.add(query);
                    try {
                        // Convert the response to a JSON object (if needed)
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        exerciseList = new ArrayList<>();

                        try {
                            JSONArray resultsArray = jsonResponse.getJSONArray("results");

                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject exerciseJson = resultsArray.getJSONObject(i);
                                String name = exerciseJson.getString("name");
                                String force = exerciseJson.optString("force", "N/A");
                                String level = exerciseJson.getString("level");
                                String mechanic = exerciseJson.getString("mechanic");
                                String equipment = exerciseJson.getString("equipment");

                                JSONArray primaryMusclesJson = exerciseJson.getJSONArray("primaryMuscles");
                                List<String> primaryMuscles = new ArrayList<>();
                                for (int j = 0; j < primaryMusclesJson.length(); j++) {
                                    primaryMuscles.add(primaryMusclesJson.getString(j));
                                }

                                JSONArray secondaryMusclesJson = exerciseJson.getJSONArray("secondaryMuscles");
                                List<String> secondaryMuscles = new ArrayList<>();
                                for (int j = 0; j < secondaryMusclesJson.length(); j++) {
                                    secondaryMuscles.add(secondaryMusclesJson.getString(j));
                                }

                                JSONArray instructionsJson = exerciseJson.getJSONArray("instructions");
                                List<String> instructions = new ArrayList<>();
                                for (int j = 0; j < instructionsJson.length(); j++) {
                                    instructions.add(instructionsJson.getString(j));
                                }

                                String category = exerciseJson.getString("category");

                                JSONArray imagesJson = exerciseJson.getJSONArray("images");
                                List<String> images = new ArrayList<>();
                                for (int j = 0; j < imagesJson.length(); j++) {
                                    images.add(imagesJson.getString(j));
                                }

                                String id = exerciseJson.getString("id");

                                // Create Exercise object and add to list
                                Exercise exercise = new Exercise(name, force, level, mechanic, equipment, primaryMuscles,
                                        secondaryMuscles, instructions, category, images, id);
                                exerciseList.add(exercise);
                            }
                            getActivity().runOnUiThread(() -> {
                                exerciseContainer.removeAllViews(); // Clear previous views
                                for (Exercise exercise : exerciseList) {
                                    // Create a new TextView for the name
                                    LinearLayout exerciseLayout = new LinearLayout(view.getContext());
                                    exerciseLayout.setOrientation(LinearLayout.VERTICAL);
                                    exerciseLayout.setPadding(16, 16, 16, 16);

                                    // Name
                                    TextView nameTextView = new TextView(view.getContext());
                                    nameTextView.setText("Name: " + exercise.getName());
                                    nameTextView.setTextSize(18);
                                    nameTextView.setTextColor(getResources().getColor(android.R.color.white));
                                    exerciseLayout.addView(nameTextView);

                                    // Force
                                    TextView forceTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);
                                    forceTextView.setText("Force: " + exercise.getForce());
                                    exerciseLayout.addView(forceTextView);

                                    // Level
                                    TextView levelTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    levelTextView.setText("Level: " + exercise.getLevel());
                                    exerciseLayout.addView(levelTextView);

                                    // Mechanic
                                    TextView mechanicTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    mechanicTextView.setText("Mechanic: " + exercise.getMechanic());
                                    exerciseLayout.addView(mechanicTextView);

                                    // Equipment
                                    TextView equipmentTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    equipmentTextView.setText("Equipment: " + exercise.getEquipment());
                                    exerciseLayout.addView(equipmentTextView);

                                    // Primary Muscles
                                    TextView primaryMusclesTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    primaryMusclesTextView.setText("Primary Muscles: " + exercise.getPrimaryMuscles());
                                    exerciseLayout.addView(primaryMusclesTextView);

                                    // Secondary Muscles
                                    TextView secondaryMusclesTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    secondaryMusclesTextView.setText("Secondary Muscles: " + exercise.getSecondaryMuscles());
                                    exerciseLayout.addView(secondaryMusclesTextView);

                                    // Instructions
                                    TextView instructionsTextView = new TextView(view.getContext());
                                    forceTextView.setTextColor(Color.LTGRAY);

                                    instructionsTextView.setTextSize(14);
                                    instructionsTextView.setPadding(8, 8, 8, 8);
                                    instructionsTextView.setMaxLines(10);  // Optional: limit lines if you want to truncate
                                    instructionsTextView.setEllipsize(TextUtils.TruncateAt.END); // Optionally, handle overflow
                                    instructionsTextView.setTextColor(getResources().getColor(android.R.color.white));

                                    // Wrap the instructions in a ScrollView if it’s too long

                                    instructionsTextView.setText("Instructions: \n" + String.join("\n", exercise.getInstructions()));
                                    ScrollView scrollView = new ScrollView(view.getContext());
                                    scrollView.addView(instructionsTextView);
                                    ;
                                    exerciseLayout.addView(scrollView);

                                    // Add the exercise layout to the main container
                                    exerciseContainer.addView(exerciseLayout);
                                    View spacerAfterDescription = new View(getContext());
                                    LinearLayout.LayoutParams spacerAfterParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, 16); // Another gap after description
                                    spacerAfterDescription.setLayoutParams(spacerAfterParams);
                                    exerciseContainer.addView(spacerAfterDescription);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(view.getContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    // Handle unsuccessful response
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(view.getContext(), "Request failed: " + response.message(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}