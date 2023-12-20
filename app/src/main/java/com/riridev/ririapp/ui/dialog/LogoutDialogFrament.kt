package com.riridev.ririapp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.riridev.ririapp.R
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.custom.CustomButton
import com.riridev.ririapp.ui.profile.ProfileViewModel

class LogoutDialogFragment : DialogFragment() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rootView: View = layoutInflater.inflate(R.layout.dialog_logout, null)
        val btnYes = rootView.findViewById<CustomButton>(R.id.btn_yes)
        val btnNo = rootView.findViewById<MaterialButton>(R.id.btn_no)
        btnYes.setOnClickListener {
            profileViewModel.logout()
            activity?.finish()
        }
        btnNo.setOnClickListener {
            dismiss()
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setView(rootView)
            .create()
    }

    companion object {
        const val TAG = "LogoutDialogFragment"
    }
}