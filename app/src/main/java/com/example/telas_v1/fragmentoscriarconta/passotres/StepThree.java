package com.example.telas_v1.fragmentoscriarconta.passotres;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.telas_v1.R;
import com.google.android.material.textfield.TextInputEditText;

public class StepThree extends Fragment {

    private StepThreeViewModel step_three_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        step_three_viewModel = ViewModelProviders.of(this).get(StepThreeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_step_two, container, false);
        return root;
    }
}