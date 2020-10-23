package com.kardelenapp.bluetoothdevices;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();


    SimpleAdapter adapter;

    @BindView(R.id.list_devices)
    ListView list_devices;

    @OnCheckedChanged(R.id.switch_scanner) void submit_switch(Switch switch_scanner) {
        if(switch_scanner.isChecked()){
            Log.v("gelid","geliyo");
            BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

            if (!bluetooth.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivity(enableBtIntent);
            }

            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mReceiver, filter);

            int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            bluetooth.startDiscovery();

           // setListAdapter(new ArrayAdapter<String>(this, R.layout.list, s));
        }
        else{
            Log.v("gelid","geliyo");
        }
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(context);
        ButterKnife.bind(this);

        context = this.getApplicationContext();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        LinearLayout layout = (LinearLayout) findViewById(R.id.adsContainer);
        AdsController adsController = new AdsController(this);
        adsController.loadBanner(layout);



        adapter = new SimpleAdapter(this, listItems,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list_devices.setAdapter(adapter);
    }

    public void addItems(String deviceName, String info) {
        Map<String, String> datum = new HashMap<String, String>(2);
        datum.put("First Line",deviceName);
        datum.put("Second Line",info);
        listItems.add(datum);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("deviceName","allag");
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.v("deviceName","bas");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.v("deviceName","bit");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.v("deviceName",deviceName);
                Log.v("deviceHardwareAddress",deviceHardwareAddress);
                addItems(deviceName,deviceHardwareAddress);
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                this.finish();
                System.exit(0);
                return true;

            case R.id.action_about:
                Intent myIntent = new Intent(this, Hakkinda.class);
                //myIntent.putExtra("key", value); //Optional parameters
                this.startActivity(myIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
