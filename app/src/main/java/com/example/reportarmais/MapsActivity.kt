package com.example.reportarmais

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.reportarmais.api.EndPoints
import com.example.reportarmais.api.Incident
import com.example.reportarmais.api.ServiceBuilder
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MESSAGE_MAPS = "maps"
const val EXTRA_ID_MAPS = "id"

class MapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var incidents: List<Incident>

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
/*
        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getIncidents()
        var position: LatLng

        call.enqueue(object : Callback<List<Incident>>{
            override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                if (response.isSuccessful){

                    incidents = response.body()!!
                    for (incident in incidents) {

                        position = LatLng(incident.lat.toString().toDouble(),
                            incident.lon.toString().toDouble())

                        if (usernam == incident.usernm) {

                            mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                .snippet(incident.cat + " - " + incident.usernm).icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

                        }
                        else {

                            mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                .snippet(incident.cat + " - " + incident.usernm))

                        }

                    }

                    val viana = LatLng(41.6931623, -8.85015)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                }
            }
            override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
*/

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //added to implement location periodic updates
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                Log.d("**** SARA", "new location received - " + loc.latitude + " -" + loc.longitude)

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                with (SharedPref.edit()) {

                    putString(getString(R.string.spLat), loc.latitude.toString())

                    putString(getString(R.string.spLon), loc.longitude.toString())

                    commit()

                }

            }
        }

        createLocationRequest()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getIncidents()
        var position: LatLng

        call.enqueue(object : Callback<List<Incident>>{
            override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                if (response.isSuccessful){

                    incidents = response.body()!!
                    for (incident in incidents) {

                        position = LatLng(incident.lat.toString().toDouble(),
                            incident.lon.toString().toDouble())

                        if (usernam == incident.usernm) {

                            mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                .snippet(incident.cat + " - " + incident.usernm).icon(
                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

                        }
                        else {

                            mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                .snippet(incident.cat + " - " + incident.usernm))

                        }

                    }

                    val viana = LatLng(41.6931623, -8.85015)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                }
            }
            override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        mMap.setOnInfoWindowClickListener(this)

/*
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
 */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.reportarAcidente-> {

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                val spLAT = SharedPref.getString(getString(R.string.spLat), "0")

                val spLON = SharedPref.getString(getString(R.string.spLon), "0")

                Toast.makeText(this, spLAT + " " + spLON, Toast.LENGTH_LONG).show()

                true

            }
            R.id.logout-> {

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                with (SharedPref.edit()) {

                    //putBoolean(getString(R.string.spLogado), false)

                    clear()

                    commit()

                }

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)

                true

            }
            R.id.vernotas-> {

                val intent = Intent(this, pagNotas::class.java).apply {

                    putExtra(MESSAGE_MAPS, "maps")

                }

                startActivity(intent)

                true

            }
            R.id.tudonormal-> {

                mMap.clear()

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getIncidents()
                var position: LatLng

                call.enqueue(object : Callback<List<Incident>>{
                    override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                        if (response.isSuccessful){

                            incidents = response.body()!!
                            for (incident in incidents) {

                                position = LatLng(incident.lat.toString().toDouble(),
                                    incident.lon.toString().toDouble())

                                if (usernam == incident.usernm) {

                                    mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                        .snippet(incident.cat + " - " + incident.usernm).icon(
                                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

                                }
                                else {

                                    mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                        .snippet(incident.cat + " - " + incident.usernm))

                                }

                            }

                            val viana = LatLng(41.6931623, -8.85015)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                        }
                    }
                    override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

                mMap.setOnInfoWindowClickListener(this)

                true

            }
            R.id.tubaroes-> {

                mMap.clear()

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getIncidents()
                var position: LatLng

                call.enqueue(object : Callback<List<Incident>>{
                    override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                        if (response.isSuccessful){

                            incidents = response.body()!!
                            for (incident in incidents) {

                                position = LatLng(incident.lat.toString().toDouble(),
                                    incident.lon.toString().toDouble())

                                if (incident.cat == "Acidentes") {

                                    if (usernam == incident.usernm) {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.acidentenossos)))

                                    }
                                    else {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.acidentedeles)))

                                    }

                                }

                            }

                            val viana = LatLng(41.6931623, -8.85015)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                        }
                    }
                    override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

                mMap.setOnInfoWindowClickListener(this)

                true

            }
            R.id.derrocada-> {

                mMap.clear()

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getIncidents()
                var position: LatLng

                call.enqueue(object : Callback<List<Incident>>{
                    override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                        if (response.isSuccessful){

                            incidents = response.body()!!
                            for (incident in incidents) {

                                position = LatLng(incident.lat.toString().toDouble(),
                                    incident.lon.toString().toDouble())

                                if (incident.cat == "Avistamentos") {

                                    if (usernam == incident.usernm) {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.avistamentosnossos)))

                                    }
                                    else {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.avistamentosdeles)))

                                    }

                                }

                            }

                            val viana = LatLng(41.6931623, -8.85015)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                        }
                    }
                    override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

                mMap.setOnInfoWindowClickListener(this)

                true

            }
            R.id.bnopasseio-> {

                mMap.clear()

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getIncidents()
                var position: LatLng

                call.enqueue(object : Callback<List<Incident>>{
                    override fun onResponse(call: Call<List<Incident>>, response: Response<List<Incident>>) {
                        if (response.isSuccessful){

                            incidents = response.body()!!
                            for (incident in incidents) {

                                position = LatLng(incident.lat.toString().toDouble(),
                                    incident.lon.toString().toDouble())

                                if (incident.cat == "Obras") {

                                    if (usernam == incident.usernm) {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.obrasnossas)))

                                    }
                                    else {

                                        mMap.addMarker(MarkerOptions().position(position).title(incident.id.toString())
                                            .snippet(incident.cat + " - " + incident.usernm)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.obrasdeles)))

                                    }

                                }

                            }

                            val viana = LatLng(41.6931623, -8.85015)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 15f))

                        }
                    }
                    override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

                mMap.setOnInfoWindowClickListener(this)

                true

            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {

        val intent = Intent(this, IncidenteCrud::class.java).apply {

            putExtra(EXTRA_ID_MAPS, marker.title)

        }

        startActivity(intent)
    }
        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()

            finishAffinity()

            return true
        }
    }




