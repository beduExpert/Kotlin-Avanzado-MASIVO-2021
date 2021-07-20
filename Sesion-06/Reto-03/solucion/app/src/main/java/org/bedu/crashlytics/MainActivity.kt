package org.bedu.crashlytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PAYPAL = "PayPal"
    val VISA = "Visa"
    val WALLET = "Wallet"

    val payments = arrayOf(
        VISA,
        PAYPAL,
        WALLET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Crashlytics.setUserIdentifier("Bedu-LmtvK4ge-Fqox-blRy")
        Crashlytics.setUserEmail("manuel@bedu.org")
        Crashlytics.setUserName("Manuel Bedu")



        btnPago.setOnClickListener{
            payGame()
        }
    }

    private fun payGame(){

        var paymentType: Int

        if(rbtnPaypal.isChecked){
            paymentType = 1

        } else if(rbtnCard.isChecked){
            paymentType = 0

        } else{
            paymentType = 3
        }


        Crashlytics.setInt("Tipo de pago",paymentType)
        val payment = payments.get(paymentType)

        if(payment == PAYPAL){
            Snackbar.make(window.decorView.findViewById(android.R.id.content), "No se Admite PayPal", Snackbar.LENGTH_LONG)
                .show()
        } else{
            val intent = Intent(this,SuccessActivity::class.java)
            startActivity(intent)
        }


    }
}
