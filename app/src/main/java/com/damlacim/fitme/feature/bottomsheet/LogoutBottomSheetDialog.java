package com.damlacim.fitme.feature.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.damlacim.fitme.databinding.LayoutLogoutBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class LogoutBottomSheetDialog extends BottomSheetDialogFragment {

    private LayoutLogoutBottomSheetBinding binding;

    // set listener
    public interface LogoutListener {
        void onLogout();
    }
    public LogoutBottomSheetDialog(LogoutListener listener) {
        this.logoutListener = listener;
    }

    // create listener
    private LogoutListener logoutListener;

    // attach listener


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = LayoutLogoutBottomSheetBinding.inflate(inflater, container, false);

        binding.btnLogout.setOnClickListener(view -> {
            logoutListener.onLogout();
            dismiss();
        });
        binding.btnCancel.setOnClickListener(view -> dismiss());

        return binding.getRoot();
    }
}