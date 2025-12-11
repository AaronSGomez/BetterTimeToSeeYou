package com.example.bettertimetoseeyou

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class LocationActivity : AppCompatActivity(), LocationListener{

    private lateinit var tvLatLon : TextView
    private lateinit var tvStatus : TextView
    private lateinit var locationManager: LocationManager
    private val locationRequestCode= 1001

    // Mapa de OpenStreetMap (osmdroid)
    private lateinit var mapView: MapView
    private lateinit var mapMarker: Marker



    //ONCREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_location)

        //vinculamos mapa a layout
        mapView = findViewById(R.id.mapView)
        //permitimos zoom con dos dedos
        mapView.setMultiTouchControls(true)
        //zoom inicial
        val mapController = mapView.controller
        mapController.setZoom(16.0)
        //posicion inicial
        val startPoint = GeoPoint(40.4168, -3.7038)
        mapController.setCenter(startPoint)
        // Creamos un marcador inicial
        mapMarker = Marker(mapView)
        mapMarker.position = startPoint
        mapMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapMarker.title = "Tu posición"
        mapView.overlays.add(mapMarker)



        tvLatLon = findViewById(R.id.tvLatLon)
        tvStatus = findViewById(R.id.tvStatus)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        //CHECKPERMISSIONANDSTART
        checkPermissionAndStart()

    }

    private fun checkPermissionAndStart() {
        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationRequestCode)
        } else {
            startLocationUpdates()
        }
    }

    //STARTLOCATIONUPDATE
    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        tvStatus.text = "Esperando localización..."

        // Pedimos actualizaciones al proveedor de GPS físico
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000L,  // cada 2 segundos
            0f,     // sin distancia mínima
            this    // esta Activity implementa LocationListener
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationRequestCode &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ){
            //aqui tenemos permisos
            startLocationUpdates()
        }else {
            tvStatus.text = "Permisos de localizacion DENEGADOS"
            Toast.makeText(this, "Permisos de localizacion DENEGADOS", Toast.LENGTH_SHORT).show()
        }

        }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lon = location.longitude
        tvLatLon.text = "Lat: $lat\nLon: $lon"
        tvStatus.text = "Localización recibida"

        Toast.makeText(this, "Coordenadas recibidas", Toast.LENGTH_SHORT).show()
        // Convertimos la localización a GeoPoint de osmdroid
        val newPoint = GeoPoint(lat, lon)
        // Movemos el mapa al nuevo punto
        val mapController = mapView.controller
        mapController.setCenter(newPoint)
        // Movemos el marcador a la nueva posición
        mapMarker.position = newPoint
        // Forzamos repintado del mapa
        mapView.invalidate()

    }

    // Necesario para el mapa
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    // Necesario para el mapa
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy(){
        super.onDestroy()
        locationManager.removeUpdates(this)
    }



}



