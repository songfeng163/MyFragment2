package org.bzu.sf.myfragment2;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> fragmentList;
    private Button btn_save_out, btn_read_out;    // 存入外存的按钮，读取外存的按钮
    private Button btn_weather;    // 天气预报
    private String fileName = "data.txt";     // 要保存或读取的文件名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());

        FragAdapater adapter = new FragAdapater(getSupportFragmentManager(), fragments);
        // 设定适配器
        ViewPager vp = findViewById(R.id.viewpager);
        vp.setAdapter(adapter);

        btn_save_out = findViewById(R.id.btn_save_out);
        btn_save_out.setOnClickListener(this);

        btn_read_out = findViewById(R.id.btn_read_out);
        btn_read_out.setOnClickListener(this);

        btn_weather = findViewById(R.id.btn_weaher);
        btn_weather.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_out:   // 存入外存
                saveOutFile();
                break;
            case R.id.btn_read_out:   // 读取外存
                readOutFile();
                break;
            case R.id.btn_weaher:    // 天气预报
                openWeatherActivity();
                break;
        }
    }

    /**
     * 保存外部文件
     */
    private void saveOutFile(){
        String state = Environment.getExternalStorageState();    // 获取外部设备
        if(state.equals(Environment.MEDIA_MOUNTED)) {    // 判断外部设备是否可用
            File SDPath = Environment.getExternalStorageDirectory();    // 获取SD目录
            File file = new File(SDPath, fileName);
            String data = "Hello World to out file";
            FileOutputStream fos;
            try{
                fos = new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 读取外部文件
     */
    private void readOutFile(){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File SDPath = Environment.getExternalStorageDirectory();
            File file = new File(SDPath, fileName);
            FileInputStream fis;
            try{
                fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String data = br.readLine();
                showDlgMsg(data);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 打开天气预报界面
     */
    private void openWeatherActivity(){
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    /**
     * 使用
     * @param msg
     */
    private void showToastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showDlgMsg(String msg){
        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(msg)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .show();
    }

    public class FragAdapater extends FragmentPagerAdapter{

        public FragAdapater(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
