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

public final class HistoryListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout relativeLayout;

  @NonNull
  public final TextView tvAmounts;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvNames;

  @NonNull
  public final ImageView tvPDF;

  @NonNull
  public final TextView tvTotalAmount;

  private HistoryListItemBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout relativeLayout, @NonNull TextView tvAmounts, @NonNull TextView tvDate,
      @NonNull TextView tvNames, @NonNull ImageView tvPDF, @NonNull TextView tvTotalAmount) {
    this.rootView = rootView;
    this.relativeLayout = relativeLayout;
    this.tvAmounts = tvAmounts;
    this.tvDate = tvDate;
    this.tvNames = tvNames;
    this.tvPDF = tvPDF;
    this.tvTotalAmount = tvTotalAmount;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static HistoryListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HistoryListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.history_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HistoryListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout relativeLayout = (LinearLayout) rootView;

      id = R.id.tvAmounts;
      TextView tvAmounts = rootView.findViewById(id);
      if (tvAmounts == null) {
        break missingId;
      }

      id = R.id.tvDate;
      TextView tvDate = rootView.findViewById(id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tvNames;
      TextView tvNames = rootView.findViewById(id);
      if (tvNames == null) {
        break missingId;
      }

      id = R.id.tvPDF;
      ImageView tvPDF = rootView.findViewById(id);
      if (tvPDF == null) {
        break missingId;
      }

      id = R.id.tvTotalAmount;
      TextView tvTotalAmount = rootView.findViewById(id);
      if (tvTotalAmount == null) {
        break missingId;
      }

      return new HistoryListItemBinding((LinearLayout) rootView, relativeLayout, tvAmounts, tvDate,
          tvNames, tvPDF, tvTotalAmount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
