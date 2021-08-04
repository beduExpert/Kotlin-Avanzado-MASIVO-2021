package org.bedu.rxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress.visibility = View.VISIBLE

        var names = arrayListOf("Juan")

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)
        lista.adapter = adapter

        val numsObservable = listOf(1,2,3,4,5,6,7,8)
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread()) //correr en el main thread
            .map {number -> number*number}
            .subscribeBy (
                onError =  { it.printStackTrace() },
                onNext = { println("numero: $it") },
                onComplete = {  }
            )



        val observable = listOf("Manuel", "Agnès", "Frida", "Anaïs")
            .toObservable()
            .observeOn(Schedulers.computation()) //correr en el main thread
            .subscribeBy (
                    onError =  { it.printStackTrace() },
                    onNext = {
                        names.add(it)
                        adapter.notifyDataSetChanged()
                    },
                    onComplete = {
                        progress.visibility = View.GONE
                    }
                )

    }



}
