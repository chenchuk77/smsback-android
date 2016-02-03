package net.kukinet.smsback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.kukinet.smsback.core.RulesService;
import net.kukinet.smsback.core.SimpleSms;
import net.kukinet.smsback.logger.Log;

public class CommandConsoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_console);


        Button btn_exec = (Button) findViewById(R.id.btnExec);
        btn_exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(this.getClass().getSimpleName(), "btnExec() pressed.");
                RulesService service = RulesService.getInstance();
                String command = ((EditText) findViewById(R.id.etCommand)).getText().toString();

                // faking an sms to pass for execution
                SimpleSms simulatedSms = new SimpleSms();
                simulatedSms.setContent(command);
                simulatedSms.setRecipientAddress("000000");
                service.execCommand(new SimpleSms().setContent(command));
                //refreshRulesListView();
                Toast.makeText(v.getContext(), "Done.", Toast.LENGTH_LONG).show();
                Log.e(this.getClass().getSimpleName(), "rules deployd: " + service.getRules().toString());

            }
        });


    }

}
