package com.example.g_iwim_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.g_iwim_application.Manager.MessageDB.FIELD_STATUT_MESSAGE;
import static com.example.g_iwim_application.Manager.UserDB.FIELD_STATUT;

public class MenuChefActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = { "Matières", "Professeurs", "Etudiants" , "Messagerie" , "Logout"};
    int images[] = { R.drawable.ic_baseline_menu_book_24, R.drawable.ic_baseline_school_24, R.drawable.ic_baseline_school_24, R.drawable.ic_black_message_24, R.drawable.ic_black_logout_24 };
    private String statut_user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chef);
        listView = findViewById(R.id.listView);
        // now create an adapter class

        MenuChefActivity.MyAdapter adapter = new MenuChefActivity.MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);
        // there is my mistake...
        // now again check this..
        Bundle bundle = getIntent().getExtras();
        // statut_user =  bundle.getString(FIELD_STATUT);
        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position ==  0) {

                    Toast.makeText(MenuChefActivity.this, "matieres", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MenuChefActivity.this, MatiereListMainActivity.class));
                }
                if (position ==  1) {
                    Toast.makeText(MenuChefActivity.this, "profs", Toast.LENGTH_SHORT).show();
                    FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    String userID = fAuth.getCurrentUser().getUid();
                    Log.d("logged in user", "id user" + userID);
                    startActivity(new Intent(MenuChefActivity.this, ProfessorListActivity.class));
                }
                if (position ==  2) {
                    Toast.makeText(MenuChefActivity.this, "etudiants", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MenuChefActivity.this, StudentsListActivity.class));
                }

                if (position ==  3) {
                    Toast.makeText(MenuChefActivity.this, "messagerie", Toast.LENGTH_SHORT).show();
                    MessageType mt = new MessageType();
                    mt.show(getSupportFragmentManager(),"MyFragment");

                }
                if (position ==  4) {
                    Toast.makeText(MenuChefActivity.this, "logout", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

            }
        });
        // so item click is done now check list view
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        finish();
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[] , int imgs[]) {
            super(c, R.layout.activity_row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;

        }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.activity_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            return row;
        }
    }
}