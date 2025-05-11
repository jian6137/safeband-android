package com.inclusitech.safeband.core.vms

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.inclusitech.safeband.core.config.APIConfig
import com.google.gson.Gson
import com.inclusitech.safeband.core.data.PatientProvisionAPIResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inclusitech.safeband.core.data.CreateUserApiRequest
import com.inclusitech.safeband.core.data.CreateUserApiResponse
import retrofit2.http.POST
import android.content.Context
import android.content.SharedPreferences
import retrofit2.http.Body

class SetupVMService : ViewModel() {

    private val _userProvision = MutableLiveData<PatientProvisionAPIResponse?>(null)
    val userProvision: LiveData<PatientProvisionAPIResponse?> get() = _userProvision

    private interface ApiService {
        @GET(APIConfig.USER_PROVISION_URL)
        suspend fun getPatientData(
            @Query("patientUID") patientID: String,
            @Query("birthday") birthdate: String
        ): Response<PatientProvisionAPIResponse>
    }

    private interface CreateUserApiService {
        @POST(APIConfig.SIGNUP_URL)
        suspend fun createNewUser(
            @Body inputData: CreateUserApiRequest
        ): Response<CreateUserApiResponse>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(APIConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    var registerType = mutableStateOf("CAREGIVER")
        private set

    var name = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var patientName = mutableStateOf("")
        private set

    var patientProfileURL = mutableStateOf("")
        private set

    var patientGender = mutableStateOf("")
        private set

    var patientID = mutableStateOf("")
        private set

    fun updateName(newName: String) {
        name.value = newName
    }

    fun updatePatientName(newName: String) {
        patientName.value = newName
    }

    fun updatePatientProfileURL(newURL: String) {
        patientProfileURL.value = newURL
    }

    fun updatePatientGender(newGender: String) {
        patientGender.value = newGender
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateRegisterType(newType: String) {
        registerType.value = newType
    }

    fun updatePatientID(newID: String){
        patientID.value = newID
    }

    fun saveAccountRole(context: Context, role: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("accountInfo", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) { putString("accountRole", role).apply() }
    }

    fun deleteAccountRole(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("accountInfo", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) { remove("accountRole").apply() }
    }


    fun fetchUserProvisionData(
        patientID: String,
        birthdate: String,
        onDataFetched: (PatientProvisionAPIResponse) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val service = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PatientProvisionAPIResponse> =
                service.getPatientData(patientID, birthdate)
            CoroutineScope(Dispatchers.Main).launch {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _userProvision.value = body
                        onDataFetched(body)
                    } else {
                        onError(Exception("NO_DATA"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(errorBody, PatientProvisionAPIResponse::class.java)
                        _userProvision.value = errorResponse
                        onDataFetched(errorResponse)
                    } else {
                        onError(Exception("ERROR_${response.code()}_NO_SYS_MESSAGE"))
                    }
                }
            }
        }
    }

    fun createUser(
        onDataFetched: (CreateUserApiResponse) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val service = retrofit.create(CreateUserApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<CreateUserApiResponse> =
                service.createNewUser(
                    CreateUserApiRequest(
                        email.value,
                        password.value,
                        name.value,
                        registerType.value,
                        patientID.value
                    )
                )
            CoroutineScope(Dispatchers.Main).launch {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        onDataFetched(body)
                    } else {
                        onError(Exception("NO_DATA"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(errorBody, CreateUserApiResponse::class.java)

                        onDataFetched(errorResponse)
                    } else {
                        onError(Exception("ERROR_${response.code()}_NO_SYS_MESSAGE"))
                    }
                }
            }
        }
    }
}