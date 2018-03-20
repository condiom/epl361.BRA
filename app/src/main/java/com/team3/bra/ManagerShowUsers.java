package com.team3.bra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagerShowUsers extends Activity {
    UserArrayAdapter adapter;
    ArrayList<String> listUsers =new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_users_show_layout);
       loadUsers();
    }

    public void loadUsers(){
        listUsers.clear();
        User.findUsers();
        ArrayList<User> users = User.getUsers();
        adapter=new UserArrayAdapter(this, users);

        ListView lv=(ListView) findViewById(R.id.listUsers);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String arr[] = getResources().getStringArray(R.array.users_array);
                Intent intent = new Intent(ManagerShowUsers.this, ManagerEditUser.class);
                Bundle b = new Bundle();
                b.putInt("key", (int)id);
                int i = (int)id;
                if(i>0){
                    i--;
                }
                b.putString("info", User.getUserById((int)i));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        loadUsers();

    }

    public void backClicked(View v){
        finish();
    }


}
