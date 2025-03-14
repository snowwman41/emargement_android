package com.example.testappqr.presentation.student.views

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.altbeacon.beacon.*

class BeaconScannerService : Service(), MonitorNotifier, RangeNotifier {
    private lateinit var beaconManager: BeaconManager
    private val beaconId: String = "007435"  // Remplacer par l'ID écrit sur le beacon

    override fun onCreate() {
        super.onCreate()
        setupBeaconManager()
        startForegroundService()
    }

    private fun setupBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))

        val region = Region("all-beacons", Identifier.parse(beaconId), null, null)

        beaconManager.addMonitorNotifier(this)
        beaconManager.addRangeNotifier(this)
        beaconManager.startMonitoring(region)
        beaconManager.startRangingBeacons(region)
    }

    override fun didEnterRegion(region: Region?) {
        Log.d("Beacon", "Beacon detected !")
    }

    override fun didExitRegion(region: Region?) {
        Log.d("Beacon", "Lost beacon...")
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
        Log.d("Beacon", "Beacon status changed: $state")
    }

    override fun didRangeBeaconsInRegion(beacons: Collection<Beacon>, region: Region) {
        for (beacon in beacons) {
            if (beacon.id1.toString() == beaconId && beacon.distance < 5.0) {
                Log.d("Beacon", "Presence validated at ${beacon.distance} meters")
                //Signatures lien avec la BDD
            }
        }
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "beacon_channel",
                "Beacon Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val notification: Notification = NotificationCompat.Builder(this, "beSSacon_channel")
                .setContentTitle("Beacon scanning")
                .setContentText("Search for beacons in progress...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager.stopMonitoring(Region("all-beacons", null, null, null))
        beaconManager.stopRangingBeacons(Region("all-beacons", null, null, null))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
