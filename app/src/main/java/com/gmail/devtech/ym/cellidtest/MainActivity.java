package com.gmail.devtech.ym.cellidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CELL_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener(){

        public void onCellInfoChanged(java.util.List<android.telephony.CellInfo> cellInfoList) {
            if (cellInfoList == null){
                return;
            }
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte)cellInfo;
                    CellIdentityLte cellIdentityLte= cellInfoLte.getCellIdentity();
                    int ci = cellIdentityLte.getCi();
                    Log.d("TestApp", "Cell ID is "+ci);
                }
            }
        };

    };

}
