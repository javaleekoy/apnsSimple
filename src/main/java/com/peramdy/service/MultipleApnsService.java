package com.peramdy.service;

import javapns.Push;
import javapns.notification.PushNotificationPayload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peramdy on 2017/7/6.
 */
public class MultipleApnsService {


    public static void sendMulitple(){
        String deviceToken = "765803bd54428cb6849d708afb325ee6cac278736d090763e952d674b24c0d0b";
        String alert = "this is a test message!";
        Integer badge = 8;
        String sound = "default";
        String certificatePath = "D:\\program\\apns\\certification\\prod.p12";
        String certificatePassword = "123456";


        PushNotificationPayload payload = new PushNotificationPayload();

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);

//        Push.payload(payload,certificatePath,certificatePassword);


    }




}
