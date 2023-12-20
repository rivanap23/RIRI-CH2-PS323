package com.riridev.ririapp.ui.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.riridev.ririapp.R
import com.riridev.ririapp.ui.history.HistoryActivity
import com.riridev.ririapp.ui.report.ReportActivity

class ReportDialogFragment : DialogFragment() {
    var resultState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rootView: View = layoutInflater.inflate(R.layout.dialog_finish_report, null)
        val tvTitle = rootView.findViewById<TextView>(R.id.tv_result_title)
        val tvDesc = rootView.findViewById<TextView>(R.id.tv_result_desc)
        val ivResult = rootView.findViewById<ImageView>(R.id.iv_result_report)
        val btnAction = rootView.findViewById<MaterialButton>(R.id.btn_action)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(rootView)
            .create()

        if (resultState) {
            tvTitle.text = resources.getString(R.string.laporan_diterima)
            tvDesc.text = resources.getString(R.string.laporan_akan_segera_diproses)
            ivResult.setImageResource(R.drawable.verified_report)
            btnAction.apply {
                setText(R.string.lanjutkan)
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
            }
        } else {
            tvTitle.text = resources.getString(R.string.laporan_ditolak)
            tvDesc.text = resources.getString(R.string.laporan_anda_terdeteksi_spam)
            ivResult.setImageResource(R.drawable.declined_report)
            btnAction.apply {
                setText(R.string.ulangi)
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
        btnAction.setOnClickListener {
            if (resultState){
                val intent = Intent(requireContext(), HistoryActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                val intent = Intent(requireContext(), ReportActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    companion object {
        fun newInstance(message: String): ReportDialogFragment {
            val fragment = ReportDialogFragment()
            val args = Bundle()
            args.putString("message", message)
            fragment.arguments = args
            return fragment
        }
    }
}