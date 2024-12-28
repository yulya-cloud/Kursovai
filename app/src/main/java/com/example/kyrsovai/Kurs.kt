package com.example.kyrsovai


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URL
import java.util.Scanner


@Serializable
data class CurrencyRates(
    @SerialName("result")
    val result: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("documentation")
    val documentation: String,
    @SerialName("terms_of_use")
    val termsOfUse: String,
    @SerialName("time_last_update_unix")
    val timeLastUpdateUnix: Long,
    @SerialName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerialName("time_next_update_unix")
    val timeNextUpdateUnix: Long,
    @SerialName("time_next_update_utc")
    val timeNextUpdateUtc: String,
    @SerialName("time_eol_unix")
    val timeEolUnix: Long,
    @SerialName("base_code")
    val baseCode: String,
    @SerialName("rates")
    val rates: Map<String, Double>
)

class Kurs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kurs)
    }

    private inner class FetchCurrencyTask : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {
            try {
                val response = URL(params[0]).openStream()
                return Scanner(response).useDelimiter("\\A").next()
            } catch (e: Exception) {
                return "Error: ${e.message}"
            }
        }

        override fun onPostExecute(result: String) {
            try {
                val currencyRates = Json.decodeFromString<CurrencyRates>(result)

                val textViewCHF = findViewById<TextView>(R.id.textViewCHF)
                val textViewUSD = findViewById<TextView>(R.id.textViewUSD)
                val textViewEUR = findViewById<TextView>(R.id.textViewEUR)
                val textViewJPY = findViewById<TextView>(R.id.textViewJPY)
                val textViewGBP = findViewById<TextView>(R.id.textViewGBP)
                val textViewCNY = findViewById<TextView>(R.id.textViewCNY)

                // информация об API
                val result = currencyRates.result
                val provider = currencyRates.provider
                val documentation = currencyRates.documentation
                val termsOfUse = currencyRates.termsOfUse
                val timeLastUpdateUtc = currencyRates.timeLastUpdateUtc

                val usdRate = currencyRates.rates["USD"] ?: error("USD rate not found")
                val eurRate = currencyRates.rates["EUR"] ?: error("EUR rate not found")
                val jpyRate = currencyRates.rates["JPY"] ?: error("JPY rate not found")
                val gbpRate = currencyRates.rates["GBP"] ?: error("GBP rate not found")
                val cnyRate = currencyRates.rates["CNY"] ?: error("CNY rate not found")
                val chfRate = currencyRates.rates["CHF"] ?: error("CHF rate not found")


                val editTextRate = findViewById<EditText>(R.id.editTextNumber)


                val userInput = editTextRate.text.toString()


                val userEnteredRate = if (userInput.isNotEmpty()) userInput.toDouble() else 0.0



                val usdValue = (usdRate * userEnteredRate).format(2)
                val eurValue = (eurRate * userEnteredRate).format(2)
                val jpyValue = (jpyRate * userEnteredRate).format(2)
                val gbpValue = (gbpRate * userEnteredRate).format(2)
                val cnyValue = (cnyRate * userEnteredRate).format(2)
                val chfValue = (chfRate * userEnteredRate).format(2)


                textViewUSD.text = "$usdValue"
                textViewEUR.text = "$eurValue"
                textViewJPY.text = "$jpyValue"
                textViewGBP.text = "$gbpValue"
                textViewCNY.text = "$cnyValue"
                textViewCHF.text = "$chfValue"


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun BackBegin(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Pos4it11(view: View) {
        val appId = "180c49b6f8a444cf961bae295d53a844"
        val valuta = "RUB"
        val apiUrl = "https://open.er-api.com/v6/latest/$valuta?app_id=$appId"


        FetchCurrencyTask().execute(apiUrl)
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
