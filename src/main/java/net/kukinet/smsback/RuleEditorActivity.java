package net.kukinet.smsback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.kukinet.smsback.core.RulesService;
import net.kukinet.smsback.rules.Rule;

public class RuleEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_editor);

        // get the rule as JSON from MainActivity
        String jsonRule = getIntent().getExtras().getString("SELECTED_JSON_RULE");
        Rule selectedRule = new Rule(jsonRule);
        fillFields(selectedRule);

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer rulePriority = Integer.parseInt(((EditText) findViewById(R.id.etPriority)).getText().toString());
                Rule rule = RulesService.getInstance().getRules().get(rulePriority);
                rule.setMatchAddress(((EditText) findViewById(R.id.etSender)).getText().toString());
                rule.setMatchContent(((EditText) findViewById(R.id.etContent)).getText().toString());
                rule.setReplyTo(((EditText) findViewById(R.id.etReplyTo)).getText().toString());
                rule.setAddTimestamp(((EditText) findViewById(R.id.etAddTimestamp)).getText().toString());
                rule.setReplaceContent(((EditText) findViewById(R.id.etReplaceContent)).getText().toString());

                // deploy the updated rule and update display
                RulesService.getInstance().addRuleToSharedPrefs(rule);
                fillFields(rule);
            }
        });
        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Integer rulePriority = Integer.parseInt(((EditText) findViewById(R.id.etPriority)).getText().toString());
                                RulesService.getInstance().removeRuleFromSharedPrefs(rulePriority);

                                // back to main activity after deleting a rule
                                Intent mainActivityIntent = new Intent(RuleEditorActivity.this, MainActivity.class);
                                startActivity(mainActivityIntent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("rule will be removed permanently. Are you sure?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });











    }



    private void fillFields(Rule rule) {

        EditText etPriority = (EditText)findViewById(R.id.etPriority);
        EditText etSender = (EditText)findViewById(R.id.etSender);
        EditText etContent = (EditText)findViewById(R.id.etContent);
        EditText etReplyTo = (EditText)findViewById(R.id.etReplyTo);
        EditText etAddTimestamp = (EditText)findViewById(R.id.etAddTimestamp);
        EditText etReplaceContent = (EditText)findViewById(R.id.etReplaceContent);
        EditText etDescription = (EditText)findViewById(R.id.etDescription);

        etPriority.setText(String.valueOf(rule.getPriority()));
        etSender.setText(rule.getMatchAddress());
        etContent.setText(rule.getMatchContent());
        etReplyTo.setText(rule.getReplyTo());
        etAddTimestamp.setText(rule.getAddTimestamp());
        etReplaceContent.setText(rule.getReplaceContent());
        etDescription.setText(rule.getRuleDescription());

    }
}
