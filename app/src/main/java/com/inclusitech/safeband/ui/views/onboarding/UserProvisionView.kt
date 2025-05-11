package com.inclusitech.safeband.ui.views.onboarding

import java.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import android.content.Intent
import android.provider.ContactsContract
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.inclusitech.safeband.R
import com.inclusitech.safeband.ui.core.SetupRoutes
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inclusitech.safeband.core.data.ContactInfo
import com.inclusitech.safeband.core.data.FirestoreTimestamp
import com.inclusitech.safeband.core.vms.SetupVMService
import org.json.JSONObject
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UserProvisionView(
    navHostController: NavHostController,
    setupVMService: SetupVMService,
    patientID: String?
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.calendar_select_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    val composition2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val progress2 by animateLottieCompositionAsState(
        composition = composition2,
        iterations = LottieConstants.IterateForever
    )

    var isBDayEntered by rememberSaveable {
        mutableStateOf(false)
    }
    var isFetching by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    var patientName by rememberSaveable { mutableStateOf("") }
    var doctorName by rememberSaveable { mutableStateOf("") }
    var hospitalName by rememberSaveable { mutableStateOf("") }

    val userProvision by setupVMService.userProvision.observeAsState()

    val parsedUserData = rememberSaveable(userProvision?.data) {
        userProvision?.data?.let { data ->
            if (data != "") {
                parseCustomUserData(data) { key, value ->
                    when (key) {
                        "hospitalName" -> {
                            hospitalName = value
                        }

                        "doctorName" -> {
                            doctorName = value
                        }

                        "fullName" -> {
                            setupVMService.updatePatientName(value)
                            patientName = value
                        }
                        "gender" -> {
                            setupVMService.updatePatientGender(value)
                        }
                        "profileURL" -> {
                            setupVMService.updatePatientProfileURL(value)
                        }
                    }
                }
            } else mapOf()
        } ?: mapOf()
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isBDayEntered) {
                LottieAnimation(
                    composition = composition,
                    progress = {
                        progress
                    },
                    modifier = Modifier.size(250.dp)
                )

                Text(
                    text = "Verify Birthdate",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp),
                )
                Text(
                    text = "To protect the patient's privacy, you will need to verify the date of birth of the patient.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = { showDatePicker = true }
                ) {
                    Text(text = "Select Birthday", style = MaterialTheme.typography.labelLarge)
                }

                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState()
                    val confirmEnabled =
                        remember {
                            derivedStateOf { datePickerState.selectedDateMillis != null }
                        }
                    val context = LocalContext.current
                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                    datePickerState.selectedDateMillis?.let {

                                        val formatter =
                                            SimpleDateFormat(
                                                "MM-dd-yyyy",
                                                Locale.getDefault()
                                            )


                                        val calendar: Calendar = Calendar.getInstance()
                                        calendar.setTimeInMillis(it)
                                        isBDayEntered = true
                                        isFetching = true

                                        setupVMService.fetchUserProvisionData(
                                            patientID = patientID!!,
                                            birthdate = formatter.format(calendar.time),
                                            onDataFetched = {
                                                if (userProvision?.status.equals("PATIENT_BIRTHDAY_NOT_MATCH")) {
                                                    navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Error!/Birthdate selected was not correct!")
                                                    isBDayEntered = false
                                                    isFetching = false
                                                } else if (userProvision?.status.equals("OK")) {
                                                    isBDayEntered = true
                                                    isFetching = false
                                                } else if(userProvision?.status.equals("PATIENT_NOT_FOUND")){
                                                    navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Error!/The Patient Information was not found. Maybe the QR Code you scanned was invalid or expired.")
                                                    isBDayEntered = false
                                                    isFetching = false
                                                } else if(userProvision?.status.equals("PROVIDE_PATIENT_UID")){
                                                    navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Error!/QR Code Information was invalid. Please double check if the QR Code you are trying to scan is valid.")
                                                    isBDayEntered = false
                                                    isFetching = false
                                                } else {
                                                    Log.e("UserProvisionView", userProvision?.status.toString())
                                                    navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Internal Error!/An Internal Error Occurred. If issue persists, please Contact Us")
                                                    isBDayEntered = false
                                                    isFetching = false
                                                }
                                            },
                                            onError = {
                                                navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Internal Error!/An Internal Error Occurred. If issue persists, Please Contact Us")
                                                isBDayEntered = false
                                                isFetching = false
                                            }
                                        )

                                    }

                                },
                                enabled = confirmEnabled.value
                            ) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

            } else {
                if (isFetching) {
                    LottieAnimation(
                        composition = composition2,
                        progress = {
                            progress2
                        },
                        modifier = Modifier.size(250.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .padding(bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.princess),
                            contentDescription = "User Profile",
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = patientName,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .padding(top = 16.dp, start = 8.dp, end = 4.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.stethoscope),
                                        contentDescription = "Stethoscope Icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(
                                                top = 4.dp, bottom = 4.dp, start = 8.dp, end = 4.dp
                                            )
                                            .size(18.dp),
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
                                    )
                                    Text(
                                        text = doctorName,
                                        modifier = Modifier.padding(
                                            top = 4.dp, bottom = 4.dp, start = 4.dp, end = 8.dp
                                        ),
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .padding(top = 16.dp, start = 4.dp, end = 8.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.hospital),
                                        contentDescription = "Hospital Icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(
                                                top = 4.dp, bottom = 4.dp, start = 8.dp, end = 4.dp
                                            )
                                            .size(18.dp),
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiaryContainer)
                                    )
                                    Text(
                                        text = hospitalName,
                                        modifier = Modifier.padding(
                                            top = 4.dp, bottom = 4.dp, start = 4.dp, end = 8.dp
                                        ),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {

                            parsedUserData.forEach { (key, value) ->
                                Spacer(modifier = Modifier.height(16.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                ) {
                                    Text(
                                        camelCaseToNormal(key),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (key == "medicationList" || key == "allergies") {
                                        val data =
                                            value.removePrefix("[").removeSuffix("]").split(",")
                                                .map { it.trim() }

                                        FlowRow(
                                            modifier = Modifier.padding(top = 0.dp),
                                        ) {
                                            data.forEach { data ->
                                                if (data.isNotEmpty()) {
                                                    AssistChip(
                                                        onClick = {

                                                        },
                                                        label = { Text(data.replace("\"", "")) },
                                                        modifier = Modifier
                                                            .padding(
                                                                start = 4.dp,
                                                                end = 4.dp
                                                            )
                                                            .border(0.dp, Color.Transparent),
                                                        shape = CircleShape,
                                                        colors = AssistChipDefaults.assistChipColors(
                                                            containerColor = if (key == "medicationList") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                                            labelColor = if (key == "medicationList") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
                                                        )
                                                    )
                                                }
                                            }
                                        }

                                    } else if (key == "emergencyContact" && value != "") {
                                        val gson = Gson()
                                        var dataType = object : TypeToken<List<ContactInfo>>() {}.type
                                        val contacts: List<ContactInfo> =
                                            gson.fromJson(value.trimIndent(), dataType)
                                        val context = LocalContext.current

                                        FlowRow(
                                            modifier = Modifier.padding(top = 0.dp),
                                        ) {
                                            contacts.forEach { contact ->
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxRowHeight()
                                                        .width(100.dp)
                                                        .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
                                                        .clip(RoundedCornerShape(12.dp))
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                                        .clickable {
                                                            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                                                                type = ContactsContract.RawContacts.CONTENT_TYPE
                                                                putExtra(ContactsContract.Intents.Insert.NAME, contact.name)
                                                                putExtra(ContactsContract.Intents.Insert.PHONE, contact.phoneNumber)
                                                            }
                                                            context.startActivity(intent)

                                                        },
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                ) {
                                                    Image(
                                                        painter = painterResource(R.drawable.princess),
                                                        contentDescription = "User Profile",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.
                                                             size(60.dp)
                                                            .aspectRatio(1f)
                                                            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                                                            .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                                                            .clip(CircleShape)
                                                    )
                                                    Text(
                                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                                                        text = contact.name,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.onPrimary,
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = "* In the event of a medical emergency, each of the following trusted contacts will be immediately informed to ensure swift support for the patient",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    } else {
                                        Text(
                                            value,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
                                if (parsedUserData.entries.last().key != key) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider()
                    Text(
                        "This SafeBand account hasn't been activated yet. Please activate the account to log in and manage your patient's physical wristband.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = WindowInsets.systemGestures.asPaddingValues()
                                    .calculateBottomPadding()
                            ),
                        onClick = {
                            setupVMService.updatePatientID(patientID.toString())
                            navHostController.navigate(SetupRoutes.RegisterGettingStarted.route)
                        },
                    ) {
                        Text("Activate")
                    }
                }
            }
        }
    }
}

fun camelCaseToNormal(camelCaseString: String): String {
    val result = camelCaseString.fold(StringBuilder()) { acc, c ->
        if (c.isUpperCase() && acc.isNotEmpty() && acc.last() != ' ') {
            acc.append(" ")
        }
        acc.append(c)
    }.toString()

    return result.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
    }
}

fun parseCustomUserData(
    jsonString: String,
    onDisplayDataParsed: (key: String, value: String) -> Unit
): Map<String, String> {
    val jsonObject = JSONObject(jsonString)
    val result = mutableMapOf<String, String>()

    jsonObject.keys().forEach { key ->
        when (key) {
            "fullName" -> {
                onDisplayDataParsed(key, jsonObject.getString(key))
                return@forEach
            }

            "doctorName" -> {
                onDisplayDataParsed(key, jsonObject.getString(key))
                return@forEach
            }

            "hospitalName" -> {
                onDisplayDataParsed(key, jsonObject.getString(key))
                return@forEach
            }

            "profileURL" -> {
                onDisplayDataParsed(key, jsonObject.getString(key))
                return@forEach
            }

            "dateOfBirth" -> {
                val timestamp =
                    Gson().fromJson(jsonObject.getString(key), FirestoreTimestamp::class.java)
                val date = Date(timestamp._seconds * 1000)
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

                result[key] = formatter.format(date)
            }

            "dateOfDiagnosis" -> {
                val timestamp =
                    Gson().fromJson(jsonObject.getString(key), FirestoreTimestamp::class.java)
                val date = Date(timestamp._seconds * 1000)
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

                result[key] = formatter.format(date)
            }

            "gender" -> {
                onDisplayDataParsed(key, jsonObject.getString(key))
                result[key] = jsonObject.getString(key)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }

            else -> result[key] = jsonObject.getString(key)
        }
    }

    return result
}


