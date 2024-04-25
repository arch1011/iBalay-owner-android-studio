package com.example.ibalay_owner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class JavaScriptInterface {
    private final Context mContext;

    public JavaScriptInterface(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void saveAsPDF(String base64Data, String mimeType) {
        try {
            // Convert base64 data to a file and store it
            convertBase64StringToFileAndStoreIt(base64Data, mimeType);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed to save PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertBase64StringToFileAndStoreIt(String base64Data, String mimeType) throws IOException {
        // Generate a unique file name based on current date and time
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
        String newTime = currentDateTime.replaceFirst(", ","_").replaceAll(" ","_").replaceAll(":","-");

        // Get file extension from mimeType
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension = mimeTypeMap.getExtensionFromMimeType(mimeType);

        // Create a file in the Downloads directory with the generated file name and appropriate extension
        File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/" + newTime + "_." + extension);

        // Decode base64 string to bytes
        byte[] pdfAsBytes = android.util.Base64.decode(base64Data, android.util.Base64.DEFAULT);

        // Write bytes to the file
        try (FileOutputStream os = new FileOutputStream(dwldsPath)) {
            os.write(pdfAsBytes);
        }

        // Notify the user that the file has been downloaded
        Toast.makeText(mContext, "File downloaded", Toast.LENGTH_SHORT).show();

        // Open the downloaded PDF file using a PDF viewer app
        openPDFFile(dwldsPath, extension);
    }

    private void openPDFFile(File file, String fileExtension) {
        // Create an intent to view the PDF file
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Set the data and type of the intent based on the file and its extension
        Uri apkURI = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
        intent.setDataAndType(apkURI, MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension));

        // Add flag to grant read permission to the file
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start activity to view the PDF file
        mContext.startActivity(intent);
    }
}
