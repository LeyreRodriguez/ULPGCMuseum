package com.example.ulpgcmuseum

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.budiyev.android.codescanner.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.ktx.Firebase

private const val CAMERA_REQUEST_CODE = 101

class QrActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        setUpPermissions()
        codeScanner()
        dynamicLink()
    }


    //https://www.youtube.com/watch?v=H4ae9Jv5B3I
    //https://firebase.google.com/docs/dynamic-links/android/create#kotlin+ktx
    private fun dynamicLink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, OnSuccessListener {
            Log.i("Qr", "Tenemos un link dinamico")

            var deepLink : Uri? = null
            if(it != null){
                deepLink = it.link
            }

            if(deepLink != null){
                Log.i("Qr", "Deep link: " + deepLink.toString())
                val currentPage = deepLink.getQueryParameter("curPage")
            }



        }).addOnFailureListener(this, OnFailureListener{

        })
    }



    private fun dynamicLinkL() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

            }
            .addOnFailureListener(this) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }
    }

    private fun codeScanner() {

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        val scannerText = findViewById<TextView>(R.id.scan_text)
        val qrScanner = findViewById<Button>(R.id.link)
        pressButton(scannerText.text )
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS //para escanear codigo de barras tambien
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    scannerText.text = it.text
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Qr", "Error al inicializar la camara: ${it.message}")
                }
            }

        }

        //si pones el scanCodeMode en continuos esto no es necesario.
        //para que cada vez que clickes se escanee el qr
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        qrScanner.setOnClickListener {
            codeScanner.startPreview()
        }

        scannerText.setOnClickListener {
            goToLink(scannerText.text.toString())
        }

    }

    private fun numberCode() {
        val numberCode = findViewById<EditText>(R.id.qr_number_code)
        //get int from char code

        numberCode.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (event.getUnicodeChar()) {
                KeyEvent.KEYCODE_ENTER -> {
                    val numero =
                        numberCode.text.toString().substring(0, numberCode.text.length - 1)
                            .toInt()
                    numberCodeLink(numero)
                    true
                }
                else -> false
            }
        }
    }

    private fun numberCodeLink(numero: Int) {
        if (numero == 12) {
            goToLink("https://google.com")
        }
    }

    private fun goToLink(link: String) {
        val url = Uri.parse(link)
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }


    private fun pressButton(text: CharSequence?) {
        val link = findViewById<AppCompatButton>(R.id.link)
        link.setOnClickListener {
            val uri: Uri = Uri.parse(text as String?);
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    override fun onResume() {
        super.onResume()
        //para que ya puedas escanear segun entres a la activity
        codeScanner.startPreview()
    }

    override fun onPause() {
        //para que cuando cierres libere los recursos
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setUpPermissions() {
        val permissions = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permissions != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Necesitas los permisos de la cámara para escaner un código qr",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}




