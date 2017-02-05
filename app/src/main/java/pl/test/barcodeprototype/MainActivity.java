package pl.test.barcodeprototype;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import devliving.online.mvbarcodereader.BarcodeCaptureFragment;
import devliving.online.mvbarcodereader.MVBarcodeScanner;

public class MainActivity extends AppCompatActivity implements BarcodeCaptureFragment.BarcodeScanningListener {

    List<String> mListOfBarcodes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MVBarcodeScanner.ScanningMode mode = null;
        @MVBarcodeScanner.BarCodeFormat int[] formats = null;
        BarcodeCaptureFragment fragment = BarcodeCaptureFragment.instantiate(mode, formats);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    private void showCollectedBarcodes(List<String> aListOfBarcodes) {
        Snackbar.make(findViewById(R.id.activity_main), aListOfBarcodes.toString(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBarcodeScanned(Barcode barcode) {
        if (!mListOfBarcodes.contains(barcode.displayValue)) {
            mListOfBarcodes.add(barcode.displayValue);
        }
        showCollectedBarcodes(mListOfBarcodes);
    }

    @Override
    public void onBarcodesScanned(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            if (!mListOfBarcodes.contains(barcode.displayValue)) {
                mListOfBarcodes.add(barcode.displayValue);
            }
        }

        showCollectedBarcodes(mListOfBarcodes);
    }

    @Override
    public void onBarcodeScanningFailed(String reason) {
        Snackbar.make(findViewById(R.id.activity_main), reason, Snackbar.LENGTH_SHORT).show();

    }
}
