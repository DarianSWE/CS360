package com.example.cs360dariangernandeventtrackingappoption2projectone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;
    private TextView tvPermissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        tvPermissionStatus = findViewById(R.id.tv_sms_permission_status);
        Button btnRequest = findViewById(R.id.btn_request_sms_permission);
        Button btnDeny = findViewById(R.id.btn_deny_sms_permission);

        updatePermissionStatus();

        btnRequest.setOnClickListener(v -> ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.SEND_SMS},
                SMS_PERMISSION_CODE
        ));

        btnDeny.setOnClickListener(v -> {
            Toast.makeText(this, "Permission Denied by User", Toast.LENGTH_SHORT).show();
            tvPermissionStatus.setText("Permission: Denied");
        });
    }

    private void updatePermissionStatus() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            tvPermissionStatus.setText("Permission: Granted");

            // Simulate sending SMS alert (only if permission granted)
            sendSmsAlert("5551234567", "Test Alert: SMS permission granted and message sent!");
        } else {
            tvPermissionStatus.setText("Permission: Not Granted");
        }
    }

    private void sendSmsAlert(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Alert sent via SMS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvPermissionStatus.setText("Permission: Granted");
                sendSmsAlert("5551234567", "Test Alert: SMS permission granted and message sent!");
            } else {
                tvPermissionStatus.setText("Permission: Denied");
                Toast.makeText(this, "SMS permission denied. App will function without SMS alerts.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
