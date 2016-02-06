package net.kukinet.smsback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.kukinet.smsback.core.RulesService;

public class StatusActivity extends AppCompatActivity {

    RulesService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        service = RulesService.getInstance();
        updateDisplay();

        ImageButton ibStatus = (ImageButton)findViewById(R.id.ibStatus);
        ibStatus.setOnClickListener(new View.OnClickListener() {
            @Override

            // flip status
            public void onClick(View v) {
                if(service.isEnabled()){
                    service.disableService();
                    updateDisplay();
                }else {
                    service.enableService();
                    updateDisplay();
                }
            }
        });

//        Button btn_start = (Button)findViewById(R.id.btnStart);
//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RulesService.getInstance().enableService();
//                updateDisplay();
//            }
//        });
//
//        Button btn_stop = (Button)findViewById(R.id.btnStop);
//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RulesService.getInstance().disableService();
//                updateDisplay();
//            }
//        });
    }

    private void updateDisplay() {
        TextView tvMatchedSms = (TextView) findViewById(R.id.tvMatchedSms);
        TextView tvUnMatchedSms = (TextView) findViewById(R.id.tvUnMatchedSms);
        TextView tvCmdSms = (TextView) findViewById(R.id.tvCmdSms);
        TextView tvBinarySms = (TextView) findViewById(R.id.tvBinarySms);
        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        ImageButton ibStatus = (ImageButton)findViewById(R.id.ibStatus);

        tvMatchedSms.setText(String.valueOf(service.getCounterMatchedSms()));
        tvUnMatchedSms.setText(String.valueOf(service.getCounterUnMatchedSms()));
        tvCmdSms.setText(String.valueOf(service.getCounterCmdSms()));
        tvBinarySms.setText(String.valueOf(service.getCounterBinarySms()));

        if (service.isEnabled()){
            ibStatus.setImageResource(R.drawable.blue_icon_service_up);
            tvStatus.setText("Service is running... (press to stop)");
        } else {
            ibStatus.setImageResource(R.drawable.red_icon_service_down);
            tvStatus.setText("Service stopped. (press to start)");

        }

    }
}
