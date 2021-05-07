package com.example.reportarmais

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.reportarmais.api.EndPoints
import com.example.reportarmais.api.RemoveIncPost
import com.example.reportarmais.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportarIncidente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportar_incidente)

        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val spLAT = SharedPref.getString(getString(R.string.spLat), "0")

        val spLON = SharedPref.getString(getString(R.string.spLon), "0")

        val rLat = findViewById<TextView>(R.id.textView7).apply {

            text = spLAT

        }
        val rLon = findViewById<TextView>(R.id.textView8).apply {

            text = spLON

        }

        val rCc = findViewById<TextView>(R.id.textView9)

        rCc.visibility = View.GONE

        val tipos = resources.getStringArray(R.array.Tipos)

        val spinner = findViewById<Spinner>(R.id.spinner2)
        if (spinner != null) {
            val adapter = ArrayAdapter(this@ReportarIncidente,
                android.R.layout.simple_spinner_item, tipos)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    val selecionado = tipos[position]

                    val tvSpinner = findViewById<TextView>(R.id.textView9).apply {

                        text = selecionado

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }

    fun criarIncidente(view: View) {

        val SharedPref: SharedPreferences = getSharedPreferences(

            getString(R.string.spUm), Context.MODE_PRIVATE

        )

        val iduser = SharedPref.getString(getString(R.string.spIdUser), "0")

        val eCat = findViewById<TextView>(R.id.textView9).text.toString()
        val eLat = findViewById<TextView>(R.id.textView7).text.toString()
        val eLon = findViewById<TextView>(R.id.textView8).text.toString()
        val eDes = findViewById<EditText>(R.id.editText4).text.toString()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.insertInc(eCat, "", eLat, eLon, iduser, eDes)

        call.enqueue(object : Callback<RemoveIncPost> {
            override fun onResponse(call: Call<RemoveIncPost>, response: Response<RemoveIncPost>) {
                if (response.isSuccessful){
                    val c: RemoveIncPost = response.body()!!

                    Toast.makeText(this@ReportarIncidente, c.msg.toString(), Toast.LENGTH_LONG).show()

                    val intent = Intent(this@ReportarIncidente, MapsActivity::class.java)

                    startActivity(intent)

                }

            }

            override fun onFailure(call: Call<RemoveIncPost>, t: Throwable) {
                Toast.makeText(this@ReportarIncidente, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        val intent = Intent(this, MapsActivity::class.java)

        startActivity(intent)

        return true
    }

}
