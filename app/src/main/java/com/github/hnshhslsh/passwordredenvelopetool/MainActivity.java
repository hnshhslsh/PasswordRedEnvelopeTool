package com.github.hnshhslsh.passwordredenvelopetool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.txt);
        editText = (EditText) findViewById(R.id.editText);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /*
    private void show(){
        if (clipboardManager.hasPrimaryClip()) {
            String out = new String();
            CharSequence s = clipboardManager.getPrimaryClip().getItemAt(0).getText();
            for (int i = 0; i < s.length(); ++i) {
                out += " " + Integer.toString((int) s.charAt(i), 8);
            }
            textView.setText(out);
        }
    }

    public void click(View view) {
        show();
    }
    */

    public void sendingPassword(View view) {
        String input = editText.getText().toString();
        if(input.length() > 0 && input.length() < 16) {
            input = "\24" + input + "\24\12\0\12";
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, input));
            editText.setText("");
            //show();
            Toast.makeText(MainActivity.this,R.string.sendOKTip, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,R.string.sendErrorTip, Toast.LENGTH_SHORT).show();
        }
    }

    public void gettingPassword(View view) {
        if (clipboardManager.hasPrimaryClip()) {
            String input = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            if(input.length() != 0){
                input = input.substring(0, input.length() - 1);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, input));
                //show();
                Toast.makeText(MainActivity.this, R.string.getOKTip, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, R.string.getErrorTip, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.getErrorTip, Toast.LENGTH_SHORT).show();
        }


    }
}
