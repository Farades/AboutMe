package ru.mtplab.aboutme;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private ImageView avatarImage;
    private TextView nameText;
    private ListView skillsList;
    private ListView hobbyList;
    private TextView phoneText;
    private TextView emailText;
    private String name;
    private String phone;
    private String email;

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

        emailText = (TextView)findViewById(R.id.email);
        email = getString(R.string.email);
        emailText.append(email);

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
