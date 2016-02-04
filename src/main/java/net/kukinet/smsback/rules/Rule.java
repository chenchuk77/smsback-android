package net.kukinet.smsback.rules;

import android.util.Log;

import com.google.gson.Gson;

import net.kukinet.smsback.core.SimpleSms;

/**
 * Created by chenchuk on 1/24/2016.
 */
public class Rule {

    private int priority;
    private String matchAddress;
    private String matchContent;
    private String replyTo;
    private String addTimestamp;
    private String replaceContent;

    public Rule(int priority, String matchAddress, String matchContent, String replyTo, String addTimestamp,
                  String replaceContent) {
        super();
        this.priority = priority;
        this.matchAddress = matchAddress;
        this.matchContent = matchContent;
        this.replyTo = replyTo;
        this.addTimestamp = addTimestamp;
        this.replaceContent = replaceContent;
    }

    public String getRuleDescription(){

        // TEMPLATE EXAMPLE :
        //"if incoming sms {from XXX} AND {contains ZZZ}, i will send {same content/"DDD"} {with timestamp} to CCCC"

        Boolean shouldMatchAddress = !matchAddress.equals("-") ;
        Boolean shouldMatchContent = !matchContent.equals("-") ;
        Boolean shouldAddTimestamp = addTimestamp.equals("+") ;
        Boolean shouldSendSameContent = replaceContent.equals("-") ;

        String desc = "If incoming sms ";

        if (shouldMatchAddress && shouldMatchContent) {
            desc += "from " + matchAddress + " AND contains " + matchContent + " ";
        } else if (shouldMatchAddress){
            desc += "from " + matchAddress + " ";
        } else if (shouldMatchContent){
            desc += "contains " + matchContent + " ";
        }
        desc += ", i will send ";
        String contentToSend = (shouldSendSameContent ? "the same content ": "'" +replaceContent +"' ") ;
        desc += contentToSend;
        desc += (shouldAddTimestamp) ? "with timestamp ": "";
        desc += "to " + replyTo + ".";
        return desc;
    }

    public Boolean isMatch(SimpleSms sms){
        // "-" is match any
        Boolean isSenderOk = matchAddress.equals("-") || matchAddress.equals(sms.getSenderAddress());
        Boolean isContentOk = matchContent.equals("-") || matchContent.equals(sms.getContent());

        // rule match if both true
        return isSenderOk && isContentOk;
    }

    public SimpleSms createSmsReply(SimpleSms sms){
        // "-" will send same message, any other value is a fixed message
        String content = replaceContent.equals("-") ? sms.getContent() : replaceContent;
        // append epoch timestamp
        if (addTimestamp.equals("+")) {
            content = content + ":" + System.currentTimeMillis()/1000;
        }
        SimpleSms smsReply = new SimpleSms()
                                .setRecipientAddress(replyTo)
                                .setContent(content);
        return smsReply;
    }




    // c'tor for json object
    public Rule (String jsonRule){
        Gson gson = new Gson();
        Log.e(getClass().getSimpleName(), "building Rule from json using gson : " + jsonRule);
        Rule rule = gson.fromJson(jsonRule, Rule.class);
        Log.e(getClass().getSimpleName(), "built Rule is " + (rule != null ? rule.toString() : "NOOL"));
        this.priority = rule.getPriority();
        this.matchAddress = rule.getMatchAddress();
        this.matchContent = rule.getMatchContent();
        this.replyTo = rule.getReplyTo();
        this.addTimestamp = rule.getAddTimestamp();
        this.replaceContent = rule.getReplaceContent();
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, Rule.class);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMatchAddress() {
        return matchAddress;
    }

    public void setMatchAddress(String matchAddress) {
        this.matchAddress = matchAddress;
    }

    public String getMatchContent() {
        return matchContent;
    }

    public void setMatchContent(String matchContent) {
        this.matchContent = matchContent;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getAddTimestamp() {
        return addTimestamp;
    }

    public void setAddTimestamp(String addTimestamp) {
        this.addTimestamp = addTimestamp;
    }

    public String getReplaceContent() {
        return replaceContent;
    }

    public void setReplaceContent(String replaceContent) {
        this.replaceContent = replaceContent;
    }
}
