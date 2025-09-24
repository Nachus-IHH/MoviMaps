package com.example.movimaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TutorialFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE_RES = "image_res";

    private String title;
    private String description;
    private int imageRes;

    public static TutorialFragment newInstance(String title, String description, int imageRes) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_IMAGE_RES, imageRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESCRIPTION);
            imageRes = getArguments().getInt(ARG_IMAGE_RES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        TextView tvTitle = view.findViewById(R.id.tvTutorialTitle);
        TextView tvDescription = view.findViewById(R.id.tvTutorialDescription);
        ImageView ivTutorial = view.findViewById(R.id.ivTutorial);

        tvTitle.setText(title);
        tvDescription.setText(description);
        ivTutorial.setImageResource(imageRes);

        return view;
    }
}
