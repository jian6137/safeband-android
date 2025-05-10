package com.inclusitech.safeband.core.vms

import android.nfc.NfcAdapter
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

    var isScanning = mutableStateOf(false)
        private set

    val deviceData
        get() = _deviceData

    fun setNfcAdapter(adapter: NfcAdapter?) {
        _nfcAdapter = adapter
    }

    fun startScan() {
        isScanning.value = true
    }

    fun loadWristbandUID(uid: String) {
        _currentRegisteredTag = uid
    }

    fun stopScan() {
        isScanning.value = false
    }

    fun insertDeviceData(data: DevicesList) {
        _deviceData = arrayOf(data)
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