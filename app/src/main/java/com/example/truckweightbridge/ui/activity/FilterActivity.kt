package com.example.truckweightbridge.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.truckweightbridge.ui.screen.FilterTicketScreen

class FilterActivity : ComponentActivity() {

    companion object {
        const val FLAG_DATE_TIME = "dateTime"
        const val FLAG_DRIVER_NAME = "driverName"
        const val FLAG_LICENSE_NUMBER = "licenseNumber"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val existingFilterDate = intent.getStringExtra(FLAG_DATE_TIME)
        val existingFilterDriverName = intent.getStringExtra(FLAG_DRIVER_NAME)
        val existingFilterLicense = intent.getStringExtra(FLAG_LICENSE_NUMBER)
        setContent {
            FilterTicketScreen(onApplyFilter = { dateTime, driverName, licenseNumber ->
                apply(dateTime, driverName, licenseNumber)
            }, onClearFilter = {
                apply()
            },{
                onBackPressed()
            },existingFilterDate,existingFilterDriverName,existingFilterLicense)
        }
    }

    private fun apply(dateTime: String? = null, driverName: String? = null, licenseNumber: String? = null) {
        val resultIntent = Intent()
        if (dateTime?.isNotEmpty() == true){
            resultIntent.putExtra(FLAG_DATE_TIME, dateTime)
        }

        if (driverName?.isNotEmpty() == true){
            resultIntent.putExtra(FLAG_DRIVER_NAME, driverName)
        }

        if (licenseNumber?.isNotEmpty() == true){
            resultIntent.putExtra(FLAG_LICENSE_NUMBER, licenseNumber)
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
