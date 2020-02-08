package org.bzu.sf.myfragment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_bj, btn_sh, btn_gz, btn_bz;
    private ImageView iv_icon;
    private TextView tv_city, tv_weather, tv_temp, tv_wind, tv_pm;
    private List<WeatherInfo> listWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tv_city = findViewById(R.id.tv_city);
        iv_icon = findViewById(R.id.iv_icon);
        tv_weather = findViewById(R.id.tv_weather);
        tv_temp = findViewById(R.id.tv_temp);
        tv_wind = findViewById(R.id.tv_wind);
        tv_pm = findViewById(R.id.tv_pm);

        btn_bj = findViewById(R.id.btn_bj);
        btn_bj.setOnClickListener(this);

        btn_sh = findViewById(R.id.btn_sh);
        btn_sh.setOnClickListener(this);

        btn_gz = findViewById(R.id.btn_gz);
        btn_gz.setOnClickListener(this);

        btn_bz = findViewById(R.id.btn_bz);
        btn_bz.setOnClickListener(this);

        //btn_bj.callOnClick();

        InputStream is = getResources().openRawResource(R.raw.weather2);
        try {
            listWeather = WeatherService.getInfosFromJson(is);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        showCityWeather(1, R.drawable.sun);
    }

    /**
     * 显示城市天气
     * @param number 序号
     * @param iconNumber 图标序号
     */
    private void showCityWeather(int number, int iconNumber){
        WeatherInfo item = listWeather.get(number);
        iv_icon.setImageResource(iconNumber);
        tv_city.setText(item.getName());
        tv_weather.setText(item.getWeather());
        tv_temp.setText(item.getTemp());
        tv_wind.setText("风力："+item.getWind());
        tv_pm.setText("pm："+item.getPm());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_bj:
                showCityWeather(1, R.drawable.sun);
                break;
            case R.id.btn_sh:
                showCityWeather(0, R.drawable.cloud_sun);
                break;
            case R.id.btn_gz:
                showCityWeather(2, R.drawable.clouds);
                break;
            case R.id.btn_bz:
                showCityWeather(3, R.drawable.xiaoyu);
                break;
        }
    }
}
