package com.example.awd3;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionModeActivity extends AppCompatActivity {
    ListView listView;
    String[] titleArr={"One","Two","Three","Four","Five","Six"};
    List<String> deleteItem = new ArrayList<>();
    List<String> selectedItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitu_action_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ListView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView=(ListView)findViewById(R.id.ListView);
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,getData(),R.layout.layout_item2,
                new String[]{"name"},new int[]{R.id.textTitle2});
        listView.setAdapter(simpleAdapter);
        setItemClick();
    }
    private List<? extends Map<String,?>> getData() {
        List<Map<String,Object>> list;
        Map<String,Object> map;
        list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<titleArr.length;i++){
            map=new HashMap<String,Object>();
            if (!deleteItem.contains(titleArr[i])){
                map.put("name",titleArr[i]);
                list.add(map);
            }
        }
        return list;
    }
    private List<? extends Map<String,?>> getData2() {
        List<Map<String,Object>> list;
        Map<String,Object> map;
        list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<titleArr.length;i++){
            map=new HashMap<String,Object>();
            if (!selectedItem.contains(titleArr[i])){
                map.put("name",titleArr[i]);
                list.add(map);
            }
        }
        return list;
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(selectedItem.size()+" selected");
            mode.getMenuInflater().inflate(R.menu.menu_action_mode,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.delete){
                deleteItem.addAll(selectedItem);
                SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),getData(),R.layout.layout_item2,
                        new String[]{"name"},new int[]{R.id.textTitle2});
                listView.setAdapter(simpleAdapter);
                selectedItem.clear();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };
    private void setItemClick(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick (AdapterView parent, View view, int position, long id) {
                Map<String, Object> itemMap = (Map<String, Object>) parent.getItemAtPosition(position);
                String longClickedItem = (String) itemMap.get("name");
                if (selectedItem.contains(longClickedItem)){
                    view.setBackground(null);
                    selectedItem.remove(longClickedItem);

                }else {
                    view.setBackgroundColor(Color.RED);
                    selectedItem.add(longClickedItem);
                }

               startActionMode(modeCallBack);
                view.setSelected(true);
                return true;
            }

        });
    }

}