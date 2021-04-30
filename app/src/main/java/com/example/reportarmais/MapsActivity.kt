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

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MESSAGE_MAPS = "maps"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var incidents: List<Incident>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val usernm = SharedPref.getString(getString(R.string.spUsername), "Nome")

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

                        mMap.addMarker(MarkerOptions().position(position).title(incident.descrip))
                    }
                }
            }
            override fun onFailure(call: Call<List<Incident>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

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

                Toast.makeText(this, "Ainda Estou em Desenvolvimento", Toast.LENGTH_LONG).show()

                true

            }
            R.id.logout-> {

                val SharedPref: SharedPreferences = getSharedPreferences(

                    getString(R.string.spUm), Context.MODE_PRIVATE

                )

                with (SharedPref.edit()) {

                    putBoolean(getString(R.string.spLogado), false)

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
            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        finishAffinity()

        return true
    }

}
