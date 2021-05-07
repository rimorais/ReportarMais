package com.example.reportarmais

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.reportarmais.api.EndPoints
import com.example.reportarmais.api.Incident
import com.example.reportarmais.api.RemoveIncPost
import com.example.reportarmais.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncidenteCrud : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidente_crud)

        val id = intent.getStringExtra(EXTRA_ID_MAPS)
        val ID = id!!.toInt()

        val textId = findViewById<TextView>(R.id.textView).apply {

            text = id

        }

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getIncidentById(ID)

        call.enqueue(object : Callback<Incident>{
            override fun onResponse(call: Call<Incident>, response: Response<Incident>) {
                if (response.isSuccessful){
                    val c: Incident = response.body()!!

                    val SharedPref: SharedPreferences = getSharedPreferences(

                        getString(R.string.spUm), Context.MODE_PRIVATE

                    )

                    val usernam = SharedPref.getString(getString(R.string.spUsername), "Nome")

                    val btEl = findViewById<Button>(R.id.button3)
                    val btAl = findViewById<Button>(R.id.button4)

                    if (usernam != c.usernm) {

                        btEl.visibility = View.GONE
                        btAl.visibility = View.GONE

                    }

                    val tipos = resources.getStringArray(R.array.Tipos)

                    val spinner = findViewById<Spinner>(R.id.spinner)
                    if (spinner != null) {
                        val adapter = ArrayAdapter(this@IncidenteCrud,
                            android.R.layout.simple_spinner_item, tipos)
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>,
                                                        view: View, position: Int, id: Long) {

                                val selecionado = tipos[position]

                                val tvSpinner = findViewById<TextView>(R.id.textView2).apply {

                                    text = selecionado

                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                                // write code to perform some action
                            }
                        }
                    }

                    val et = findViewById<EditText>(R.id.editText3).apply {

                        hint = c.des

                    }

                    val textV = findViewById<TextView>(R.id.textView3).apply {

                        text = c.usernm

                    }

                }
            }

            override fun onFailure(call: Call<Incident>, t: Throwable) {
                Toast.makeText(this@IncidenteCrud, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun eliminarIncidente(view: View) {

        val id = intent.getStringExtra(EXTRA_ID_MAPS)
        val ID = id!!.toInt()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.removeInc(ID)

        call.enqueue(object : Callback<RemoveIncPost> {
            override fun onResponse(call: Call<RemoveIncPost>, response: Response<RemoveIncPost>) {
                if (response.isSuccessful){
                    val c: RemoveIncPost = response.body()!!

                    Toast.makeText(this@IncidenteCrud, c.msg.toString(), Toast.LENGTH_LONG).show()

                    val intent = Intent(this@IncidenteCrud, MapsActivity::class.java)

                    startActivity(intent)

                }

            }

            override fun onFailure(call: Call<RemoveIncPost>, t: Throwable) {
                Toast.makeText(this@IncidenteCrud, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun alterarIncidente(view: View) {

        val id = intent.getStringExtra(EXTRA_ID_MAPS)
        val ID = id!!.toInt()

        val textoCat = findViewById<TextView>(R.id.textView2).text.toString()
        val textoDes = findViewById<EditText>(R.id.editText3).text.toString()

        if (textoDes.isNullOrEmpty()) {

            var descript = findViewById<EditText>(R.id.editText3).hint.toString()

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.updateInc(ID, textoCat, descript)

            call.enqueue(object : Callback<RemoveIncPost> {
                override fun onResponse(call: Call<RemoveIncPost>, response: Response<RemoveIncPost>) {
                    if (response.isSuccessful){
                        val c: RemoveIncPost = response.body()!!

                        Toast.makeText(this@IncidenteCrud, c.msg.toString(), Toast.LENGTH_LONG).show()

                        val intent = Intent(this@IncidenteCrud, MapsActivity::class.java)

                        startActivity(intent)

                    }

                }

                override fun onFailure(call: Call<RemoveIncPost>, t: Throwable) {
                    Toast.makeText(this@IncidenteCrud, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
        else {

            var descript = findViewById<EditText>(R.id.editText3).text.toString()

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.updateInc(ID, textoCat, descript)

            call.enqueue(object : Callback<RemoveIncPost> {
                override fun onResponse(call: Call<RemoveIncPost>, response: Response<RemoveIncPost>) {
                    if (response.isSuccessful){
                        val c: RemoveIncPost = response.body()!!

                        Toast.makeText(this@IncidenteCrud, c.msg.toString(), Toast.LENGTH_LONG).show()

                        val intent = Intent(this@IncidenteCrud, MapsActivity::class.java)

                        startActivity(intent)

                    }

                }

                override fun onFailure(call: Call<RemoveIncPost>, t: Throwable) {
                    Toast.makeText(this@IncidenteCrud, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }
}
