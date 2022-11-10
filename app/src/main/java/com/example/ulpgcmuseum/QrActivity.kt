package com.example.ulpgcmuseum

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*

private const val CAMERA_REQUEST_CODE = 101

class QrActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        setUpPermissions()
        codeScanner()
    }

    private fun codeScanner(){

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        val scannerText = findViewById<TextView>(R.id.scan_text)

        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS //para escanear codigo de barras tambien
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread{
                    scannerText.text = it.text
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("Qr", "Error al inicializar la camara: ${it.message}")
                }
            }
        }

        //si pones el scanCodeMode en continuos esto no es necesario.
        //para que cada vez que clickes se escanee el qr
        scannerView.setOnClickListener{
            codeScanner.startPreview()
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

    private fun setUpPermissions(){
        val permissions = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if(permissions != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Necesitas los permisos de la cámara para escaner un código qr", Toast.LENGTH_SHORT)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



}