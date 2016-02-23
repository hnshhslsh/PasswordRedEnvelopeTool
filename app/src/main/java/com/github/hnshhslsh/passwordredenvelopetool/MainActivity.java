package com.github.hnshhslsh.passwordredenvelopetool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private ClipboardManager clipboardManager;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.txt);
        editText = (EditText) findViewById(R.id.editText);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
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
        if(input.length() >= 0 && input.length() <= 14) {
            if(checkBox.isChecked()){
                Random random = new Random();
                int index = input.length() == 0 ? 0 : random.nextInt(input.length());
                input = input.substring(0,index) + "\24\12" + input.substring(index) + "\24\12";
            } else {
                input = input + "\24\12";
            }
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, input));
            editText.setText("");
            //show();
            Util.openPackage(this.getApplicationContext(), "com.tencent.mobileqq");
            Toast.makeText(MainActivity.this,R.string.sendOKTip, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,R.string.sendErrorTip, Toast.LENGTH_SHORT).show();
        }
    }

    public void gettingPassword(View view) {
         final int NORMAL = 0;
        final int ONLY_END = 1;
        final int OTHER = 2;
        if (clipboardManager.hasPrimaryClip()) {
            String input = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            if(input.length() != 0){
                int cnt = 0;
                int type = NORMAL;
                String out = new String();
                for(int i = 0;i < input.length() - 1;++i){
                    if(input.charAt(i) == '\372'){
                        out += "【换成“换行”】";
                        type = OTHER;
                    } else {
                        out += input.charAt(i);
                    }
                }
                if(input.charAt(input.length() - 1) == '\372'){
                    if(type == NORMAL){
                        type = ONLY_END;
                    } else {
                        out += "【换成“换行”】";
                    }
                }
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, out));
                //show();
                Util.openPackage(this.getApplicationContext(), "com.tencent.mobileqq");
                switch (type){
                    case NORMAL:
                        Toast.makeText(MainActivity.this, R.string.getNormalTip, Toast.LENGTH_LONG).show();
                        break;
                    case ONLY_END:
                        Toast.makeText(MainActivity.this, R.string.getOnlyEndTip, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, R.string.getOtherTip, Toast.LENGTH_LONG).show();
                        break;
                }
            } else {
                Toast.makeText(MainActivity.this, R.string.getErrorTip, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.getErrorTip, Toast.LENGTH_SHORT).show();
        }

    }



}
