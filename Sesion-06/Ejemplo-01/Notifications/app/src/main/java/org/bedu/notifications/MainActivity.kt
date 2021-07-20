package org.bedu.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //El id de nuestro canal a registrar
    val CHANNEL_ID = "Ejemplo"

    companion object{
        //el nombre de la acción a ejecutar por el botón en la notificación
        const val ACTION_RECEIVED = "action_received"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para android Oreo en adelante, s obligatorio registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        btnNotify.setOnClickListener{
            simpleNotification()
        }

        btnActionNotify.setOnClickListener {
            touchNotification()
        }

        btnNotifyWithBtn.setOnClickListener {
            buttonNotification()
        }
    }


    private fun simpleNotification(){

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.triforce) //seteamos el ícono de la push notification
            .setColor(getColor(R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto

        //lanzamos la notificación
        with(NotificationManagerCompat.from(this)) {
            notify(20, builder.build()) //en este caso pusimos un id genérico
        }
    }

    private fun touchNotification(){

        //Un PendingIntent para dirigirnos a una actividad pulsando la notificación
        val intent = Intent(this, NewBeduActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.action_title))
            .setContentText(getString(R.string.action_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) //se define aquí el content intend
            .setAutoCancel(true) //la notificación desaparece al dar click sobre ella

        with(NotificationManagerCompat.from(this)) {
            notify(20, builder.build())
        }

    }

    private fun buttonNotification(){

        //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
        val acceptIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_RECEIVED
        }
        //creamos un PendingIntent que describe el pending anterior
        val acceptPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, acceptIntent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.button_title))
            .setContentText(getString(R.string.button_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.bedu_icon, getString(R.string.button_text),//agregamos la acción
                acceptPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(20, builder.build())
        }

    }
    private fun setNotificationChannel(){
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }



}
