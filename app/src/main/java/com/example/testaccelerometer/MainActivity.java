package com.example.testaccelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    SensorManager sensorManager;
    Sensor accelerometer;
    float[] deltaAccelerometer = new float[2];

    int X_Ban_Dau;
    int Y_Ban_Dau;
    int flag = 0;
    float DoNghiengBanDauX, DoNghiengBanDauY;
    static final float ACCELEROMETER_SENSITIVITY = 1.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setText("hihi");

        // Đặt vị trí ban đầu
        X_Ban_Dau = 530;
        Y_Ban_Dau = 1800;

        // Lấy sensor Accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Lấy độ nghiêng điện thoại ban đầu làm mốc cân bằng
        if (flag == 0) {
            flag++;
            DoNghiengBanDauX = event.values[0];     // Lấy giá trị X của Sensor
            DoNghiengBanDauY = event.values[1];     // Lấy giá trị Y của Sensor
        }

        // Tính độ lệch của máy hiện tại so với vị trí cân bằng
        deltaAccelerometer[0] = event.values[0] - DoNghiengBanDauX;
        deltaAccelerometer[1] = event.values[1] - DoNghiengBanDauY;

        // Nhân hệ số để chuyển động được xa
        float deltaX = -deltaAccelerometer[0] * ACCELEROMETER_SENSITIVITY * 80;
        float deltaY = deltaAccelerometer[1] * ACCELEROMETER_SENSITIVITY * 150;

        // Di chuyển nhân vật theo độ nghiêng hiện tại
        textView.setX(X_Ban_Dau + deltaX);
        textView.setY(Y_Ban_Dau + deltaY);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}