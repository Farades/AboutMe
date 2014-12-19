package ru.mtplab.aboutme;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ImageView avatarImage;
    private TextView nameText;
    private ListView skillsList;
    private ListView hobbyList;
    private TextView phoneText;
    private TextView emailText;
    private TextView socialText;
    private String name;
    private String phone;
    private String email;
    private String vk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView)findViewById(R.id.textView);
        name = getString(R.string.last_name) + " " + getString(R.string.first_name);
        nameText.setText(name);

        phoneText = (TextView)findViewById(R.id.phone);
        phone = getString(R.string.phone);
        phoneText.append(phone);
        Linkify.addLinks(phoneText, Linkify.ALL);

        emailText = (TextView)findViewById(R.id.email);
        email = getString(R.string.email);
        emailText.append(email);
        Linkify.addLinks(emailText, Linkify.ALL);

        socialText = (TextView)findViewById(R.id.social);
        vk = getString(R.string.vk_text);
        socialText.append(vk);
        socialText.setPaintFlags(socialText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        socialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.vk_link))));
            }
        });

        avatarImage = (ImageView)findViewById(R.id.imageView1);
        avatarImage.setImageResource(R.drawable.avatar);

        String[] skills;
        skills = getResources().getStringArray(R.array.professional_skills);
        skillsList = (ListView)findViewById(R.id.listView1);
        ArrayAdapter<String> skillsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, skills);
        skillsList.setAdapter(skillsAdapter);

        String[] hobby;
        hobby = getResources().getStringArray(R.array.hobby);
        hobbyList = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> hobbyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hobby);
        hobbyList.setAdapter(hobbyAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_copy) {
            String copyText = "ФИО: " + getString(R.string.last_name) + " " + getString(R.string.first_name) + "\n"
                    + "Email: " + getString(R.string.email) + "\n"
                    + "Телефон: " + getString(R.string.phone);
            saveToClipboard(copyText);
            showDialog("Копирование", "ФИО, email, телефон скопированы в буфер обмена.");
        } else if (id == R.id.menu_copy_name) {
            String copyText = "ФИО: " + getString(R.string.last_name) + " " + getString(R.string.first_name);
            saveToClipboard(copyText);
            showDialog("Копирование ФИО", "ФИО скопированы в буфер обмена.");
        } else if (id == R.id.menu_copy_email) {
            String copyText = "Email: " + getString(R.string.email);
            saveToClipboard(copyText);
            showDialog("Копирование email", "Email скопирован в буфер обмена.");
        } else if (id == R.id.menu_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.menu_sms) {
            String sendText = "ФИО: " + getString(R.string.last_name) + " " + getString(R.string.first_name) + "\n"
                    + "Email: " + getString(R.string.email) + "\n"
                    + "Телефон: " + getString(R.string.phone);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", sendText);
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(this);
        dlgBuilder.setTitle(title);
        dlgBuilder.setMessage(message);
        dlgBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog Dialog = dlgBuilder.create();
        Dialog.show();
    }

    private void saveToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("AboutMe", text);
        clipboard.setPrimaryClip(clip);
    }
}
