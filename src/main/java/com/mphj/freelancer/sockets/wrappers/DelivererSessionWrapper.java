package com.mphj.freelancer.sockets.wrappers;

import com.mphj.freelancer.repository.models.Deliverer;
import com.mphj.freelancer.repository.models.User;
import org.eclipse.jetty.websocket.api.Session;

public class DelivererSessionWrapper {

    private Session session;
    private Deliverer deliverer;
    private User user;
    private long latestMessageTime;

    private DelivererSessionWrapper(Session session) {
        this.session = session;
    }

    public static DelivererSessionWrapper from(Session session) {
        DelivererSessionWrapper sessionWrapper = new DelivererSessionWrapper(session);
        return sessionWrapper;
    }


    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setLat(double lat) {
        deliverer.setLat(lat);
    }

    public double getLat() {
        return deliverer.getLat();
    }

    public void setLng(double lng) {
        deliverer.setLng(lng);
    }

    public double getLng() {
        return deliverer.getLng();
    }

    public long getLatestMessageTime() {
        return latestMessageTime;
    }

    public void setLatestMessageTime(long latestMessageTime) {
        this.latestMessageTime = latestMessageTime;
    }

    public Session getSession() {
        return session;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}