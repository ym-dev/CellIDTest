package com.gmail.devtech.ym.cellidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager telephonyManager;
    private List<CellInfo> cellInfoList;
    private PhoneStateListener PSL;
    private int event;
    private EditText ET;
    private EditText ET2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CellIdTest","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        ET = (EditText)findViewById(R.id.ET);
        ET2 = (EditText)findViewById(R.id.ET2);
    }

    @Override
    protected void onResume() {
        Log.d("CellIdTest","onResume");
        super.onResume();
        PSL = new PhoneStateListener();
        event = PSL.LISTEN_CELL_INFO | PSL.LISTEN_CELL_LOCATION;
        telephonyManager.listen(PSL, event);
        cellInfoList = telephonyManager.getAllCellInfo();     //



        if(cellInfoList == null)
            ET.setText("cellInfoList = null");
        else{
            ET.setText("There are " + cellInfoList.size() + " Cells\n");
            List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
            for (CellInfo cellInfo : cellInfoList)
            {
                if (cellInfo instanceof CellInfoLte)
                {
                    // cast to CellInfoLte and call all the CellInfoLte methods you need
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    if (cellInfoLte != null) {
                        int cellId = Integer.MAX_VALUE;
                        cellId = cellInfoLte.getCellIdentity().getCi();
                        int pcellId = Integer.MAX_VALUE;
                        pcellId = cellInfoLte.getCellIdentity().getPci();
                        ET.setText("Cell ID = " + cellId);

                    }
                }
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener(){

        public void onCellInfoChanged(java.util.List<android.telephony.CellInfo> cellInfoList) {
            Log.d("CellIdTest","onCellInfoChanged");

            if (cellInfoList == null){
                Log.d("CellIdTest","Cell ID is NULL");
                ET2.setText("cellInfoList = null");
                return;
            }
            Log.d("CellIdTest","There are " + cellInfoList.size());
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte)cellInfo;
                    CellIdentityLte cellIdentityLte= cellInfoLte.getCellIdentity();
                    int ci = cellIdentityLte.getCi();
                    Log.d("CellIdTest", "Cell ID is "+ci);
                    ET2.setText("Cell ID is "+ci);
                }
            }
        };

    };

}
