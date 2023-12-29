package com.app.milkman.utils;

import com.app.milkman.model.ParentResponse;
import com.app.milkman.model.SMSRequest;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.twilio.rest.api.v2010.account.Message;

@Slf4j
@Component
public class SMSService {


    public ParentResponse sendSMS(SMSRequest smsRequest) {
        log.debug("SMS service invoked for!!! {}", smsRequest);
        Twilio.init(Constants.SMS_SID, Constants.SMS_TOKEN);
        Message.creator(new PhoneNumber(smsRequest.getToNumber()), new PhoneNumber(Constants.SMS_NUMBER), smsRequest.getTextMessage()).create();

        ParentResponse response = new ParentResponse();
        response.setStatus(Constants.SUCCESS);
        response.setStatusCode(Constants.SUCCESS_CODE);
        return response;
    }
}
