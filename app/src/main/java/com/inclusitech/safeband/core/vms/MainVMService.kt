package com.inclusitech.safeband.core.vms

import android.content.Context
import android.content.SharedPreferences
import android.nfc.NfcAdapter
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.inclusitech.safeband.core.data.DevicesList

class MainVMService : ViewModel() {
    private var _nfcAdapter: NfcAdapter? = null
    private var _currentRegisteredTag: String = ""

    private var _deviceData: Array<DevicesList>? = null

    private var _currentUserType = "CAREGIVER"

    val currentUserType: String
        get() = _currentUserType

    val nfcAdapter: NfcAdapter?
        get() = _nfcAdapter

    val currentRegisteredTag: String
        get() = _currentRegisteredTag

    private val _isScanning = mutableStateOf(false)
    val isScanning: State<Boolean> get() = _isScanning

    val deviceData
        get() = _deviceData

    fun startScan() {
        if(_isScanning.value == false) {
            _isScanning.value = true
        }
    }

    fun stopScan() { // this can be observable
        if(_isScanning.value == true) {
            _isScanning.value = false
        }
    }

    fun loadWristbandUID(uid: String) {
        _currentRegisteredTag = uid
    }

    fun insertDeviceData(data: DevicesList) {
        _deviceData = arrayOf(data)
    }

    fun saveAccountRole(context: Context, role: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("accountInfo", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) { putString("accountRole", role).apply() }
    }

    fun getAccountRole(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("accountInfo", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accountRole", "")
    }

    fun getNFCStatus(): String {
        return when {
            nfcAdapter == null -> {
                "NO_SUPPORT"
            }

            !nfcAdapter!!.isEnabled -> {
                "NFC_DISABLED"
            }

            else -> {
                "NFC_ENABLED"
            }
        }
    }
}