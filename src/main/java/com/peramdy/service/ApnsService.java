package com.peramdy.service;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peramdy on 2017/7/6.
 */
public class ApnsService {


    public static void pushMessage() {


        String deviceToken = "765803bd54428cb6849d708afb325ee6cac278736d090763e952d674b24c0d0b";
        String deviceToken2 = "4f9e701ce3cb47173e3b3da5cdeb677297157b6be616689677e8e13c9b9ae652";

//      String alert = "this is a test message!";

        String alert = "这是一条花花写的测试消息测试消息！";

        Integer badge = 1;

        PushNotificationPayload payload = new PushNotificationPayload();
        /**
         * tip sound
         */
        String sound = "default";

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        tokens.add(deviceToken2);

        try {
            payload.addAlert(alert);
            payload.addBadge(badge);
            if (StringUtils.isNotBlank(sound))
                payload.addSound(sound);
            /**
             * certificate signed by apple
             */
            String certificatePath = "D:\\program\\apns\\certification\\prod.p12";
            /**
             * certificate password
             */
            String certificatePassword = "123456";

            PushNotificationManager pushManager = new PushNotificationManager();
            /**
             *
             * Communication settings for interacting with Apple's default production or sandbox notification server.
             * This constructor uses the recommended keystore type "PCKS12".
             *
             * @param keystore a keystore containing your private key and the certificate signed by Apple (File, InputStream, byte[], KeyStore or String for a file path)
             * @param password the keystore's password
             * @param production true to use Apple's production servers, false to use the sandbox
             * @throws KeystoreException thrown if an error occurs when loading the keystore
             */
            AppleNotificationServerBasicImpl appleNotificationServerBasic = new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, true);

            /**
             * Initialize a connection and create a SSLSocket
             * @param server The Apple server to connect to.
             * @throws CommunicationException thrown if a communication error occurs
             * @throws KeystoreException thrown if there is a problem with your keystore
             */
            pushManager.initializeConnection(appleNotificationServerBasic);

            List<PushedNotification> pushedNotificationList = null;
            if (tokens.size() > 1) {
                List<Device> devices = new ArrayList<Device>();
                for (String token : tokens) {
                    devices.add(new BasicDevice(token));
                }

                pushedNotificationList = pushManager.sendNotifications(payload, devices);

            } else if (tokens.size() == 1) {
                pushedNotificationList = new ArrayList<PushedNotification>();
                Device device = new BasicDevice(tokens.get(0));
                PushedNotification pushedNotification = pushManager.sendNotification(device, payload, true);
                pushedNotificationList.add(pushedNotification);
            }

            List<PushedNotification> failedNotifications = PushedNotification
                    .findFailedNotifications(pushedNotificationList);
            List<PushedNotification> successfulNotifications = PushedNotification
                    .findSuccessfulNotifications(pushedNotificationList);
            int failed = failedNotifications.size();
            int successful = successfulNotifications.size();

            System.out.println("==========failCount：" + failed);
            System.out.println("==========successfulCount：" + successful);

            pushManager.stopConnection();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
