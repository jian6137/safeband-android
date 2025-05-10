package com.inclusitech.safeband.core

import android.app.ComponentCaller
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.core.MainRouter
import com.inclusitech.safeband.ui.core.SetupRouter
import com.inclusitech.safeband.ui.theme.SafeBandTheme
import com.inclusitech.safeband.ui.views.main.INTENT_ACTION_NFC_READ
import com.inclusitech.safeband.ui.views.main.getParcelableCompatibility

class MainActivity : ComponentActivity() {

    private val startTime = System.currentTimeMillis()

    private var setupVMService: SetupVMService? = null
    private var mainVMService: MainVMService? = null

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var isLoggedIn by mutableStateOf(false)
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                System.currentTimeMillis() - startTime < 3000
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)


        val user = firebaseAuth.currentUser
        if (user != null) {
            mainVMService = viewModels<MainVMService>().value
            mainVMService?.setNfcAdapter(nfcAdapter)
            isLoggedIn = true
        } else {
            setupVMService = viewModels<SetupVMService>().value
            isLoggedIn = false
        }

        if (setupVMService != null) {
            authStateListener = FirebaseAuth.AuthStateListener { auth ->
                val currentUser = auth.currentUser
                isLoggedIn = currentUser != null

                if (currentUser != null) {
                    mainVMService = viewModels<MainVMService>().value
                    mainVMService?.setNfcAdapter(nfcAdapter)
                    setupVMService = null
                } else {
                    mainVMService = null
                    setupVMService = viewModels<SetupVMService>().value
                }
            }
        }

        setContent {
            SafeBandTheme {
                if (isLoggedIn) {
                    MainRouter(mainVMService!!)
                } else {
                    SetupRouter(setupVMService!!)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (setupVMService != null) {
            firebaseAuth.addAuthStateListener(authStateListener)
        }
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()

    }

    override fun onPause() {
        super.onPause()
        disableNfcForegroundDispatch()
    }

    override fun onStop() {
        super.onStop()
        if (setupVMService != null) {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        intent.let { nfcIntent ->
            sendBroadcast(Intent(INTENT_ACTION_NFC_READ).apply {
                Log.d("MAIN", "sendBroadcast")
                putExtra(
                    NfcAdapter.EXTRA_TAG,
                    nfcIntent.getParcelableCompatibility(NfcAdapter.EXTRA_TAG, Tag::class.java)
                )
                setPackage(packageName)
            })
        }
    }

    private fun enableNfcForegroundDispatch() {
        nfcAdapter?.let { adapter ->
            if (adapter.isEnabled) {
                val nfcIntentFilter = arrayOf(
                    IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
                )

                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_MUTABLE
                    )
                } else {
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
                adapter.enableForegroundDispatch(
                    this, pendingIntent, nfcIntentFilter, null
                )
            }
        }
    }

    private fun disableNfcForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(this)
    }

}

