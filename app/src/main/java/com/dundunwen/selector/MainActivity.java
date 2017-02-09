package com.dundunwen.selector;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.DataProvider;
import chihane.jdaddressselector.ISelectAble;
import chihane.jdaddressselector.SelectedListener;
import chihane.jdaddressselector.Selector;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    int deep = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDeepDialog();
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


    }

    private void showSelectDeepDialog() {
        String[] arr = new String[]{"1","2","3","4","5","6"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择深度")
                .setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deep = which+1;
                        ((Button)findViewById(R.id.btn0)).setText(deep+"");
                    }
                }).show();
    }

    private ArrayList<ISelectAble> getDatas() {
        int count = new Random().nextInt(99) + 1;
        ArrayList<ISelectAble> data = new ArrayList<>(count);
        for (int j = 0; j < count; j++) {
            final int finalJ = j;
            data.add(new ISelectAble() {
                @Override
                public String getName() {
                    return "随机" + finalJ;
                }

                @Override
                public int getId() {
                    return finalJ;
                }

                @Override
                public Object getArg() {
                    return this;
                }
            });
        }
        return data;
    }

    List<List<ISelectAble>> datas = new ArrayList<>();

    private void showDialog() {
        Selector selector = new Selector(this, deep);

        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                //根据tab的深度和前一项选择的id，获取下一级菜单项
                Log.i(TAG, "provideData: currentDeep >>> "+currentDeep+" preId >>> "+preId);
                receiver.send(getDatas());
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                for (ISelectAble selectAble : selectAbles) {
                    result += selectAble.getName()+" ";
                }
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        });

        BottomDialog dialog = new BottomDialog(this);
        dialog.init(this,selector);
        dialog.show();
    }
}
