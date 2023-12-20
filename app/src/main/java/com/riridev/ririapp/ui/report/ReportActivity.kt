package com.riridev.ririapp.ui.report

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ReportLayoutBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.dialog.ReportDialogFragment
import com.riridev.ririapp.utils.getImageUri
import com.riridev.ririapp.utils.reduceFileImage
import com.riridev.ririapp.utils.uriToFile

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ReportLayoutBinding
    private var currentImageUri: Uri? = null
    private val reportViewModel by viewModels<ReportViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private val fakeDialog = ReportDialogFragment.newInstance("fake")
    private val succesDialog = ReportDialogFragment.newInstance("notFake")

    private val mapsActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == MapsActivity.RESULT_CODE && result.data != null) {
                val yourAddress = result.data?.getStringExtra(MapsActivity.EXTRA_ADDRESS_VALUE)
                binding.activityReport.etLocation.setText(yourAddress.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReportLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupAction()
        checkTitleWords()
    }

    private fun checkTitleWords(){
        binding.activityReport.etTitle.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim().split("\\s+".toRegex())
                if (text.size == 3) {
                    binding.activityReport.tilTitleReport.isErrorEnabled = false
                } else {
                    binding.activityReport.tilTitleReport.error = "Judul laporan minimal 3 kata"
                    binding.activityReport.btnSend.isEnabled = false
                }
            }
        })
    }
    private fun observeFakeDetection(){
        reportViewModel.fakeDetection.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val predict = result.data.prediction
                    if (predict == 1) {
                        fakeDialog.resultState = false
                        fakeDialog.show(supportFragmentManager, "fake")
                    } else {
                        currentImageUri?.let { uri ->
                            sendReport(uri)
                        }
                    }
                }

                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeReport(){
        reportViewModel.report.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                    binding.activityReport.btnSend.isEnabled = false
                }

                is Result.Success -> {
                    showLoading(false)
                    succesDialog.resultState = true
                    succesDialog.show(supportFragmentManager, "notFake")
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAction() {
        //click ambil bukti
        binding.activityReport.btnKameraReport.setOnClickListener {
            getImageProof()
        }

        binding.activityReport.btnGaleriReport.setOnClickListener {
            startGallery()
        }

        binding.activityReport.btnAmbilLokasi.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            mapsActivityResult.launch(intent)
        }


        binding.activityReport.cbAgreement.setOnCheckedChangeListener { _, isChecked ->
            binding.activityReport.btnSend.isEnabled = isChecked
        }

        binding.activityReport.btnSend.setOnClickListener {
            //send the form
            val title = binding.activityReport.etTitle.text.toString()
            val instansi = binding.activityReport.tvInstansiReport.text.toString()
            val category = binding.activityReport.tvCategoryReport.text.toString()
            val description = binding.activityReport.etDescription.text.toString()
            val location = binding.activityReport.etLocation.text.toString()
            val detailLocation = binding.activityReport.etDetailLocation.text.toString()


            if ( title.isEmpty() || instansi.isEmpty() || category.isEmpty() || description.isEmpty() || location.isEmpty() || detailLocation.isEmpty() || currentImageUri == null) {
                Toast.makeText(this, "Lengkapi Form Telebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                reportViewModel.fakeReportDetection(title)
                observeFakeDetection()
            }
        }
    }

    private fun sendReport(uri: Uri) {
        val title = binding.activityReport.etTitle.text.toString()
        val instansi = binding.activityReport.tvInstansiReport.text.toString()
        val category = binding.activityReport.tvCategoryReport.text.toString()
        val description = binding.activityReport.etDescription.text.toString()
        val location = binding.activityReport.etLocation.text.toString()
        val detailLocation = binding.activityReport.etDetailLocation.text.toString()

        val image = uriToFile(uri, this).reduceFileImage()
        val report = ReportModel(
            title,
            instansi,
            category,
            description,
            location,
            detailLocation,
            image
        )
        reportViewModel.sendReport(report)
        observeReport()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                currentImageUri?.let {
                    binding.activityReport.ivBukti.setImageURI(it)
                }
            } else {
                Toast.makeText(this, "Gambar Kosong", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getImageProof() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera =
        registerForActivityResult(
            ActivityResultContracts.TakePicture(),
        ) { isSuccess ->
            if (isSuccess) {
                currentImageUri?.let {
                    binding.activityReport.ivBukti.setImageURI(it)
                }
            }
        }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}