package com.example.barcodedetection;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView resultTextView;
    Button scanBtn;
    String[] permission = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = findViewById(R.id.scan_btn);
        resultTextView = findViewById(R.id.result_tv);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, Config.PERMISSION_REQUEST);
        }

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Scan button clicked");

                Intent mIntent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(mIntent, Config.REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Config.REQUEST_CODE && resultCode == RESULT_OK)

            if (data != null) {

                final Barcode barcode = data.getParcelableExtra(Config.BARCODE_GET_KEY);

                resultTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (barcode != null) {
                            resultTextView.setText(barcode.displayValue);
                        }

                    }
                });
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
