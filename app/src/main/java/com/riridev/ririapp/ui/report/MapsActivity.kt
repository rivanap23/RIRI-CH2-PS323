package com.riridev.ririapp.ui.report

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.riridev.ririapp.R
import com.riridev.ririapp.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLastLocation()
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
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mMap.isMyLocationEnabled = true
            if (mMap.isMyLocationEnabled){
                try {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            // Handle the location result
                            location?.let {
                                showStartMarker(it)
                            }
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

    private fun showStartMarker(location: Location) {
        val yourLocation = LatLng(location.latitude, location.longitude)
        val mapIcon = BitmapFactory.decodeResource(resources, R.drawable.location)
        mMap.addMarker(
            MarkerOptions()
                .position(yourLocation)
                .title(getString(R.string.lokasi_saya))
                .icon(BitmapDescriptorFactory.fromBitmap(mapIcon.scale(120,120)))
        )
        binding.btnGetLocation.setOnClickListener {
            getAddress(yourLocation.latitude, yourLocation.longitude)
            finish()
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 15f))
    }


    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION") val addressList =
                    geocoder.getFromLocation(latitude, longitude, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    val addressText = address.getAddressLine(0)
                    // Use the addressText as needed (e.g., display in a TextView)
                    val resulIntent = Intent()
                    resulIntent.putExtra(EXTRA_ADDRESS_VALUE, addressText)
                    setResult(RESULT_CODE, resulIntent)
                } else {
                    Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
                }

            } else {
                geocoder.getFromLocation(latitude, longitude, 1) { addressList ->
                    if (addressList.isNotEmpty()) {
                        val address = addressList[0]
                        val addressText = address.getAddressLine(0)
                        // Use the addressText as needed (e.g., display in a TextView)
                        val resulIntent = Intent()
                        resulIntent.putExtra(EXTRA_ADDRESS_VALUE, addressText)
                        setResult(RESULT_CODE, resulIntent)
                    } else {
                        Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val EXTRA_ADDRESS_VALUE = "extra_address_value"
        const val RESULT_CODE = 110
    }
}