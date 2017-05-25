package com.android.soundlly.intern.jisu.bitsoundtest.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.soundlly.intern.jisu.bitsoundtest.R;

import io.bitsound.receiver.Bitsound;
import io.bitsound.receiver.BitsoundContents;
import io.bitsound.receiver.BitsoundContentsListener;
import io.bitsound.shaking.BitsoundShaking;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//    PublishSubject<Integer> micPermissionSubject = PublishSubject.create();

    ImageView initBS,releaseBS,startDetect,stopDetect,startShakingDetect,stopShakingDetect,micPermissionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBS = (ImageView) findViewById(R.id.init_bs);
        releaseBS = (ImageView) findViewById(R.id.release_bs);
        startDetect = (ImageView) findViewById(R.id.start_detect);
        stopDetect = (ImageView) findViewById(R.id.stop_detect);
        startShakingDetect = (ImageView) findViewById(R.id.start_shake_detect);
        stopShakingDetect = (ImageView) findViewById(R.id.stop_shake_detect);
        micPermissionState = (ImageView) findViewById(R.id.mic_permission_state);

        initBS.setOnClickListener(this);
        releaseBS.setOnClickListener(this);
        startDetect.setOnClickListener(this);
        stopDetect.setOnClickListener(this);
        startShakingDetect.setOnClickListener(this);
        stopShakingDetect.setOnClickListener(this);
        micPermissionState.setOnClickListener(this);

//        micPermissionSubject.onNext(Build.VERSION.SDK_INT);

//        micPermissionSubject.filter(version -> version >= Build.VERSION_CODES.M).subscribe(result->{Toast.makeText(this,""+Build.VERSION.SDK_INT,Toast.LENGTH_SHORT).show();});

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.init_bs:
                Bitsound.init(this,bitsoundContentsListener);
                Log.e("Bitsound Init",""+Bitsound.initialized());
                break;
            case R.id.release_bs:
                Bitsound.release();
                break;
            case R.id.start_detect:
                Bitsound.startDetection();
                break;
            case R.id.stop_detect:
                Bitsound.stopDetection();
                break;
            case R.id.start_shake_detect:
                BitsoundShaking.enable(getApplicationContext(), new BitsoundShaking.OnShakeListener() {
                    @Override
                    public void onShake() {
                        Log.d("TAG","Shaking Detected");
                        Bitsound.startDetection();
                    }
                });
                break;
            case R.id.stop_shake_detect:
                BitsoundShaking.disable(getApplicationContext());
                break;
            case R.id.mic_permission_state:
//                micPermissionSubject.onNext(Build.VERSION.SDK_INT);
                break;

        }
    }


    BitsoundContentsListener bitsoundContentsListener = new BitsoundContentsListener() {

        @Override
        public void onInitialized() {
            Log.d("TAG", "Bitsound SDK initialized");
        }

        @Override
        public void onError(int error) {
            Log.d("TAG", "Bitsound SDK Error Code : " + error);
            switch (error) {
                case BitsoundContents.Error.NETWORK:
                    Log.d("TAG", "Network Error");
                    break;
                case BitsoundContents.Error.MIC_PERMISSION_DENIED:
                    Log.d("TAG", "Mic Permission Denied");
                    break;
                case BitsoundContents.Error.MIC_FAILURE:
                    Log.d("TAG", "Mic Failure");
                    break;
                case BitsoundContents.Error.NOT_AUTHORIZED:
                    Log.d("TAG", "AppKey Not Authorized");
                    break;
                case BitsoundContents.Error.NOT_SCHEDULED:
                    Log.d("TAG", "Not Scheduled");
                    break;
            }
        }

        @Override
        public void onStateChanged(int state) {
            Log.d("TAG", "Bitsound SDK State Code : " + state);
            switch (state) {
                case BitsoundContents.State.STARTED: // 음파탐지 시작
                    Log.d("TAG", "Detection Started");
                    break;
                case BitsoundContents.State.STOPPED: // 음파탐지 종료
                    Log.d("TAG", "Detection Stopped");
                    break;
            }
        }



        @Override
        public void onResult(int result, BitsoundContents contents) {
            Log.d("TAG", "Bitsound SDK Result Code : " + result);
            if (result == BitsoundContents.Result.SUCCESS && contents != null) {
                String name = contents.getName();
                String url = contents.getUrl();
                String comment = contents.getComment();
                int sequence = contents.getSequence();

                Toast.makeText(getApplicationContext(),"name : " + name+"url : " + url+"comment : " + comment+"sequence : " + String.valueOf(sequence),Toast.LENGTH_SHORT).show();
            }

        }
    };



}
