package com.mphj.freelancer.sockets;

import com.mphj.freelancer.repository.DelivererDao;
import com.mphj.freelancer.repository.ShoppingCardDao;
import com.mphj.freelancer.repository.UserDao;
import com.mphj.freelancer.repository.models.Deliverer;
import com.mphj.freelancer.repository.models.ShoppingCard;
import com.mphj.freelancer.repository.models.User;
import com.mphj.freelancer.sockets.wrappers.DelivererSessionWrapper;
import com.mphj.freelancer.utils.HibernateUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebSocket
public class DelivererWebSocket {

    public static List<DelivererSessionWrapper> sessions = new ArrayList<>();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        sessions.add(DelivererSessionWrapper.from(session));
        log("NEW USER:" + session.getRemoteAddress().toString());
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        log("NEW MESSAGE FROM:" + session.getRemoteAddress().toString() + " --> " + message);

        DelivererSessionWrapper sessionWrapper = find(session);
        if (sessionWrapper == null)
            return;

        if (message.startsWith("/verify")) {

            if (sessionWrapper.getDeliverer() != null || sessionWrapper.getUser() != null) {

                try {
                    session.getRemote().sendString("501:Already authenticated");
                } catch (Exception e) {

                }

                return;
            }

            User user = authenticateUser(message);
            Deliverer deliverer = authenticateDeliverer(message);
            if (user != null) {
                sessionWrapper.setUser(user);
            } else if (deliverer != null) {
                sessionWrapper.setDeliverer(deliverer);
            } else {
                session.close(403, "Bad credentials!");
                return;
            }

            try {
                session.getRemote().sendString("200:Verified");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (message.startsWith("/set")) {

            if (sessionWrapper.getDeliverer() != null) {

                double[] latLng = parseLatLng(message);
                if (latLng == null) {
                    try {
                        session.getRemote().sendString("500:Bad Input");
                    } catch (Exception e) {

                    }
                    return;
                }

                sessionWrapper.getDeliverer().setLat(latLng[0]);
                sessionWrapper.getDeliverer().setLng(latLng[1]);

                ShoppingCardDao shoppingCardDao = new ShoppingCardDao(HibernateUtils.getSessionFactory());
                List<ShoppingCard> shoppingCards = shoppingCardDao.findNDByDelivererId(sessionWrapper.getDeliverer().getId());
                for (DelivererSessionWrapper delivererSessionWrapper : sessions) {
                    for (ShoppingCard shoppingCard : shoppingCards) {
                        if (delivererSessionWrapper.getUser() != null) {
                            if (delivererSessionWrapper.getUser().getId() == shoppingCard.getUserId()) {
                                try {
                                    delivererSessionWrapper
                                            .getSession()
                                            .getRemote()
                                            .sendString(latLng[0] + "," + latLng[1]);
                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                }

            } else {
                session.close(403, "First authenticate please!");
            }

        } else if (message.startsWith("/get")) {

            if (sessionWrapper.getUser() != null) {

                ShoppingCard shoppingCard = parseShoppingCard(message);

                if (shoppingCard == null || shoppingCard.getUserId() != sessionWrapper.getUser().getId()) {
                    try {
                        session.getRemote().sendString("400:Bad id");
                    } catch (Exception e) {
                    }
                    return;
                }

                Deliverer deliverer = findDeliverer(shoppingCard.getDelivererId());

                if (deliverer == null) {
                    try {
                        session.getRemote().sendString("500:Internal error");
                    } catch (Exception e) {
                    }
                    return;
                }

                try {
                    session.getRemote().sendString(deliverer.getLat() + "," + deliverer.getLng());
                } catch (Exception e) {

                }

            } else {
                session.close(403, "First authenticate please!");
            }

        }
    }


    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        session.close();
        log("ERROR FROM : " + session.getRemoteAddress().toString() + " ---> " + error.getMessage());
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
        log("USER LEFT: " + session.getRemoteAddress().toString() + " --- REASON : " + reason + " --- STATUS : " + statusCode);
    }


    private static void log(String msg) {
        System.out.println(msg);
    }


    public static DelivererSessionWrapper find(Session session) {
        for (DelivererSessionWrapper sessionWrapper : sessions) {
            if (sessionWrapper.getSession().equals(session)) {
                return sessionWrapper;
            }
        }
        return null;
    }


    public Deliverer authenticateDeliverer(String message) {
        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());
        Deliverer deliverer = null;
        String[] messageParts = message.split(" ");
        if (messageParts.length <= 1)
            return null;
        String identifyInfo = messageParts[1];
        String[] identifyInfoParts = identifyInfo.split("::");
        if (identifyInfoParts.length <= 1)
            return null;
        String phone = identifyInfoParts[0];
        String password = identifyInfoParts[1];
        try {
            deliverer = delivererDao.findByPhoneAndPassword(phone, password);
        } catch (Exception e) {

        }
        return deliverer;
    }


    public User authenticateUser(String message) {
        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = null;
        String[] messageParts = message.split(" ");
        if (messageParts.length <= 1)
            return null;
        String token = messageParts[1];
        try {
            user = userDao.findByToken(token);
        } catch (Exception e) {

        }
        return user;
    }

    public double[] parseLatLng(String message) {
        String[] messageParts = message.split(" ");
        if (messageParts.length <= 1)
            return null;
        String latLng = messageParts[1];
        String[] latLngParts = latLng.split(",");
        if (latLngParts.length <= 1)
            return null;
        double[] latLngArr = new double[2];
        try {
            latLngArr[0] = Double.parseDouble(latLngParts[0].trim());
            latLngArr[1] = Double.parseDouble(latLngParts[1].trim());
        } catch (Exception e) {
            return null;
        }
        return latLngArr;
    }


    public ShoppingCard parseShoppingCard(String message) {
        String[] messageParts = message.split(" ");
        if (messageParts.length <= 1)
            return null;

        int shoppingCardId = 0;

        try {
            shoppingCardId = Integer.parseInt(messageParts[1].trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (shoppingCardId == 0)
            return null;

        ShoppingCardDao shoppingCardDao = new ShoppingCardDao(HibernateUtils.getSessionFactory());

        try {
            return shoppingCardDao.findById(shoppingCardId);
        } catch (Exception e) {
            return null;
        }
    }


    public Deliverer findDeliverer(int delivererId) {
        Deliverer deliverer = null;
        for (DelivererSessionWrapper sessionWrapper : sessions) {
            if (sessionWrapper.getDeliverer() != null && sessionWrapper.getDeliverer().getId() == delivererId) {
                deliverer = sessionWrapper.getDeliverer();
            }
        }

        if (deliverer != null)
            return deliverer;

        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());
        try {
            return delivererDao.findById(delivererId);
        } catch (Exception e) {
            return null;
        }
    }


}
