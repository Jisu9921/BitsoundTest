package com.android.soundlly.intern.jisu.bitsoundtest.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.android.soundlly.intern.jisu.bitsoundtest.R

import io.bitsound.receiver.Bitsound
import io.bitsound.receiver.BitsoundContents
import io.bitsound.receiver.BitsoundContentsListener
import io.bitsound.shaking.BitsoundShaking

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val initBS: ImageView = findViewById(R.id.init_bs) as ImageView
        val releaseBS: ImageView = findViewById(R.id.release_bs) as ImageView
        val startDetect: ImageView = findViewById(R.id.start_detect) as ImageView
        val stopDetect: ImageView = findViewById(R.id.stop_detect) as ImageView
        val startShakingDetect: ImageView = findViewById(R.id.start_shake_detect) as ImageView
        val stopShakingDetect: ImageView = findViewById(R.id.stop_shake_detect) as ImageView

        initBS.setOnClickListener(this)
        releaseBS.setOnClickListener(this)
        startDetect.setOnClickListener(this)
        stopDetect.setOnClickListener(this)
        startShakingDetect.setOnClickListener(this)
        stopShakingDetect.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.init_bs -> {
                Bitsound.init(this, bitsoundContentsListener)
            }
            R.id.release_bs -> Bitsound.release()
            R.id.start_detect -> Bitsound.startDetection()
            R.id.stop_detect -> Bitsound.stopDetection()
            R.id.start_shake_detect -> BitsoundShaking.enable(applicationContext, BitsoundShaking.OnShakeListener {
                Log.d("TAG", "Shaking Detected")
                Bitsound.startDetection()
            })
            R.id.stop_shake_detect -> BitsoundShaking.disable(applicationContext)
        }
    }


    internal var bitsoundContentsListener: BitsoundContentsListener = object : BitsoundContentsListener {

        override fun onInitialized() {
            Log.d("TAG", "Bitsound SDK initialized")
        }

        override fun onError(error: Int) {
            Log.d("TAG", "Bitsound SDK Error Code : " + error)
            when (error) {
                BitsoundContents.Error.NETWORK -> Log.d("TAG", "Network Error")
                BitsoundContents.Error.MIC_PERMISSION_DENIED -> Log.d("TAG", "Mic Permission Denied")
                BitsoundContents.Error.MIC_FAILURE -> Log.d("TAG", "Mic Failure")
                BitsoundContents.Error.NOT_AUTHORIZED -> Log.d("TAG", "AppKey Not Authorized")
                BitsoundContents.Error.NOT_SCHEDULED -> Log.d("TAG", "Not Scheduled")
            }
        }

        override fun onStateChanged(state: Int) {
            Log.d("TAG", "Bitsound SDK State Code : " + state)
            when (state) {
                BitsoundContents.State.STARTED // 음파탐지 시작
                -> Log.d("TAG", "Detection Started")
                BitsoundContents.State.STOPPED // 음파탐지 종료
                -> Log.d("TAG", "Detection Stopped")
            }
        }


        override fun onResult(result: Int, contents: BitsoundContents?) {
            Log.d("TAG", "Bitsound SDK Result Code : " + result)
            if (result == BitsoundContents.Result.SUCCESS && contents != null) {
                val name = contents.name
                val url = contents.url
                val comment = contents.comment
                val sequence = contents.sequence

                Toast.makeText(applicationContext, "name : " + name + "url : " + url + "comment : " + comment + "sequence : " + sequence.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }


}
