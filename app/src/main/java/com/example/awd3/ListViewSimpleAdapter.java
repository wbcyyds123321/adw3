package com.example.awd3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewSimpleAdapter extends AppCompatActivity {
    ListView listView;
    int[] imageArr={R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat,R.drawable.elephant};
    String[] titleArr={"Lion","Tiger","Monkey","Dog","Cat","Elephant"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_simple_adapter);
        //连接listView
        listView=(ListView)findViewById(R.id.ListViewsimple);
        //创建SimpleAdapter适配器
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,getData(),R.layout.layout_item,
                new String[]{"img","name"},new int[]{R.id.image2,R.id.textTitle});
       initNotificationChannel();
        setItemClick();
        //为listview添加SimpleAdapter适配器
        listView.setAdapter(simpleAdapter);
    }

    private List<? extends Map<String,?>> getData() {
        List<Map<String,Object>> list;
        Map<String,Object> map;
        list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<imageArr.length;i++){
            map=new HashMap<String,Object>();
            map.put("img",imageArr[i]);
            map.put("name",titleArr[i]);
            list.add(map);
        }

        return list;
    }
    private void setItemClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View toastView =getLayoutInflater().inflate(R.layout.toast_frame,null);


            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String animalName = titleArr[position];
                Toast toast =new Toast(getApplicationContext());
                TextView toastText = toastView.findViewById(R.id.toast_text);
                toastText.setText(animalName);
                toast.setView(toastView);
                sendNotification(view,animalName);
                toast.show();

            }
        });

    }
    private void initNotificationChannel() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel(
                    "animal_channel",
                    "Animal Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }
    }
    private void sendNotification(View itemView,String title){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ListViewSimpleAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, "animal_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知")
                .setContentText("您已选中「" + title + "」，这是自定义通知内容")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }
}