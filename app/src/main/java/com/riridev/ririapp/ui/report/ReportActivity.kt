package com.riridev.ririapp.ui.report

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.riridev.ririapp.data.model.ReportModel
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.ReportLayoutBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.utils.getImageUri
import com.riridev.ririapp.utils.reduceFileImage
import com.riridev.ririapp.utils.uriToFile
import java.io.IOException
import java.util.Locale

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ReportLayoutBinding
    private var currentImageUri: Uri? = null
    private val reportViewModel by viewModels<ReportViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReportLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupAction()

        reportViewModel.report.observe(this){result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                        binding.activityReport.btnSend.isEnabled = false
                    }

                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                        finish()
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
            getMyLastLocation()
        }

        binding.activityReport.cbAgreement.setOnCheckedChangeListener { _, isChecked ->
            binding.activityReport.btnSend.isEnabled = isChecked
        }

        binding.activityReport.btnSend.setOnClickListener {
            //send the form
            currentImageUri?.let { uri ->
                sendReport(uri)
            }
        }
    }

    private fun sendReport(uri: Uri){
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }

                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            try {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Handle the location result
                        location?.let { getAddress(it.latitude, it.longitude) }
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure
                        Toast.makeText(
                            this,
                            "Error getting location: ${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @Suppress("DEPRECATION")
    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressList: List<Address>?

        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addressList.isNullOrEmpty()) {
                val address = addressList[0]
                val addressText = address.getAddressLine(0)
                // Use the addressText as needed (e.g., display in a TextView)
                binding.activityReport.etLocation.setText(addressText)
            } else {
                Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

}