package com.inclusitech.safeband.ui.views.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import android.os.Build
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.ui.core.MainRoutes
import okio.IOException

@Composable
fun ConnectDeviceNFC(navHostController: NavHostController, mainVMService: MainVMService, type: String, patientUID: String?) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nfc_scan))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(Unit) {
        mainVMService.startScan()
    }

    if(type == "READ"){
        NfcBroadcastReceiver { tag ->
            mainVMService.loadWristbandUID(tag.id.joinToString("") { "%02x".format(it) })
            navHostController.navigate(MainRoutes.AddDeviceCustomize.route)
            mainVMService.stopScan()
        }
    } else if(type == "WRITE" && patientUID != null){
        NfcBroadcastReceiver { tag ->
            if(mainVMService.currentRegisteredTag == tag.id.joinToString("") { "%02x".format(it) }.toString()){
//                val message = createUrlMessage("https://safeband.zaide.online/view/${patientUID}")
                val message = createUrlMessage("https://www.safeband.online/en/6137152")
                val success = writeNdefMessageToTag(message, tag)

                if (success) {
                    navHostController.navigate(MainRoutes.DeviceAddComplete.route)
                } else {
                    navHostController.navigate("${MainRoutes.GenericErrorView.route}/Error/Failed to write to Wristband NFC. Please try again.")
                }
            } else {
                navHostController.navigate("${MainRoutes.GenericErrorView.route}/Error/Failed to write to Wristband NFC. Please make sure that the Wristband you are trying to write is the same Wristband that you read earlier.")
            }
            mainVMService.stopScan()
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if(type == "READ") "Reading SafeBand Data..." else "Writing SafeBand Data...",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Tap Your Wristband behind your phone's NFC Antenna area to connect it.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

const val INTENT_ACTION_NFC_READ = "com.inclusitech.safeband.ui.views.main.INTENT_ACTION_NFC_READ"

@Composable
fun NfcBroadcastReceiver(
    onSuccess: (Tag) -> Unit
) {
    val context = LocalContext.current

    val currentOnSystemEvent by rememberUpdatedState(onSuccess)

    DisposableEffect(context) {
        val intentFilter = IntentFilter(INTENT_ACTION_NFC_READ)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {

                intent?.getParcelableCompatibility(
                    NfcAdapter.EXTRA_TAG,
                    Tag::class.java
                )?.let { tag ->
                    currentOnSystemEvent(tag)
                }
            }
        }
        ContextCompat.registerReceiver(
            context,
            broadcast,
            intentFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}

private fun createUrlMessage(url: String): NdefMessage {
    val uriField = url.toByteArray(Charsets.UTF_8)
    val payload = byteArrayOf(0x00) + uriField // 0x00 means no URI prefix shortcut
    val uriRecord = NdefRecord(
        NdefRecord.TNF_WELL_KNOWN,
        NdefRecord.RTD_URI,
        ByteArray(0),
        payload
    )
    return NdefMessage(arrayOf(uriRecord))
}

internal fun <T : Parcelable> Intent.getParcelableCompatibility(key: String, type: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key,type)
    } else {
        getParcelableExtra(key)
    }
}

fun writeNdefMessageToTag(message: NdefMessage, tag: Tag?): Boolean {
    try {
        val ndef = Ndef.get(tag)

        if (ndef != null) {
            ndef.connect()
            if (!ndef.isWritable) {
                Log.e("NFC", "Tag is read-only")
                return false
            }
            if (ndef.maxSize < message.toByteArray().size) {
                Log.e("NFC", "Not enough space on tag")
                return false
            }
            ndef.writeNdefMessage(message)
            ndef.close()
            return true
        } else {
            val format = NdefFormatable.get(tag)
            if (format != null) {
                format.connect()
                format.format(message)
                format.close()
                return true
            } else {
                Log.e("NFC", "Tag doesn't support NDEF")
                return false
            }
        }
    } catch (e: IOException) {
        Log.e("NFC", "Write error: ${e.toString()}")
        return false
    }
}
