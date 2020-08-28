package com.example.telas_v1.fragmentos_criar_conta.passo_um;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.telas_v1.R;

public class Step_oneDirections {
  private Step_oneDirections() {
  }

  @NonNull
  public static NavDirections actionStepOneToStepTwo() {
    return new ActionOnlyNavDirections(R.id.action_step_one_to_step_two);
  }
}
