package com.inclusitech.safeband.core.vms

import android.content.Context
import android.content.SharedPreferences
import android.nfc.NfcAdapter
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.inclusitech.safeband.core.config.APIConfig
import com.inclusitech.safeband.core.data.DevicesList
import com.inclusitech.safeband.core.data.LinkedPatientInfo
import com.inclusitech.safeband.core.data.LinkedPatientsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class MainVMService : ViewModel() {

    private var _currentRegisteredTag: String = ""

    private var _deviceData: Array<DevicesList>? = null

    private var _currentUserType = "CAREGIVER"

    val currentUserType: String
        get() = _currentUserType

    val currentRegisteredTag: String
        get() = _currentRegisteredTag

    private val _isScanning = mutableStateOf(false)
    val isScanning: State<Boolean> get() = _isScanning

    val deviceData
        get() = _deviceData

    private var _linkedPatients = MutableLiveData<List<LinkedPatientInfo>?>(null)
    val linkedPatients: LiveData<List<LinkedPatientInfo>?> get() = _linkedPatients

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

    fun getNFCStatus(context: Context): String {
        val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
        return when {
            nfcAdapter == null -> {
                "NO_SUPPORT"
            }
            nfcAdapter.isEnabled -> {
                "NFC_ENABLED"
            }
            else -> {
                "NFC_DISABLED"
            }
        }
    }
    
    fun loadLinkedPatientsResult(data: List<LinkedPatientInfo>){
        _linkedPatients.value = data
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(APIConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private interface GetLinkedPatientsApi {
        @GET(APIConfig.GET_LINKED_PATIENTS)
        suspend fun getLinkedPatients(
            @Header("Authorization") authToken: String
        ): Response<LinkedPatientsResponse>
    }

    fun fetchLinkedPatients(
        authKey: String,
        onDataFetched: (LinkedPatientsResponse) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val service = retrofit.create(GetLinkedPatientsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<LinkedPatientsResponse> =
                service.getLinkedPatients("Bearer $authKey")

            CoroutineScope(Dispatchers.Main).launch {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        onDataFetched(body)
                    } else {
                        onError(Exception("NO_DATA"))
                    }
                } else {
                    Log.d("MainVMService", "Fetch FAIL ")
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(errorBody, LinkedPatientsResponse::class.java)
                        onDataFetched(errorResponse)
                    } else {
                        onError(Exception("ERROR_${response.code()}_NO_SYS_MESSAGE"))
                    }
                }
            }
        }
    }
}