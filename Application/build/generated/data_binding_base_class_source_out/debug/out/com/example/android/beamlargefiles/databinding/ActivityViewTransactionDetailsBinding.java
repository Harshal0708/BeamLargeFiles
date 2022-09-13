// Generated by view binder compiler. Do not edit!
package com.example.android.beamlargefiles.databinding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.android.beamlargefiles.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityViewTransactionDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView btPickDate;

  @NonNull
  public final ImageView imBack;

  @NonNull
  public final LinearLayout relativeLayout;

  @NonNull
  public final TextView tvAmounts;

  @NonNull
  public final TextView tvNames;

  @NonNull
  public final TextView tvTotalAmount;

  private ActivityViewTransactionDetailsBinding(@NonNull LinearLayout rootView,
      @NonNull TextView btPickDate, @NonNull ImageView imBack, @NonNull LinearLayout relativeLayout,
      @NonNull TextView tvAmounts, @NonNull TextView tvNames, @NonNull TextView tvTotalAmount) {
    this.rootView = rootView;
    this.btPickDate = btPickDate;
    this.imBack = imBack;
    this.relativeLayout = relativeLayout;
    this.tvAmounts = tvAmounts;
    this.tvNames = tvNames;
    this.tvTotalAmount = tvTotalAmount;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityViewTransactionDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityViewTransactionDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_view_transaction_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityViewTransactionDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btPickDate;
      TextView btPickDate = rootView.findViewById(id);
      if (btPickDate == null) {
        break missingId;
      }

      id = R.id.imBack;
      ImageView imBack = rootView.findViewById(id);
      if (imBack == null) {
        break missingId;
      }

      id = R.id.relativeLayout;
      LinearLayout relativeLayout = rootView.findViewById(id);
      if (relativeLayout == null) {
        break missingId;
      }

      id = R.id.tvAmounts;
      TextView tvAmounts = rootView.findViewById(id);
      if (tvAmounts == null) {
        break missingId;
      }

      id = R.id.tvNames;
      TextView tvNames = rootView.findViewById(id);
      if (tvNames == null) {
        break missingId;
      }

      id = R.id.tvTotalAmount;
      TextView tvTotalAmount = rootView.findViewById(id);
      if (tvTotalAmount == null) {
        break missingId;
      }

      return new ActivityViewTransactionDetailsBinding((LinearLayout) rootView, btPickDate, imBack,
          relativeLayout, tvAmounts, tvNames, tvTotalAmount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
