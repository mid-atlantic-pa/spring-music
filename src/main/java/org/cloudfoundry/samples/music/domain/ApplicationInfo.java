package org.cloudfoundry.samples.music.domain;

import java.util.Map;

public class ApplicationInfo {
    private String[] profiles;
    private String[] services;
    private Color color;
    private Map<String, Object> vcap;

    public ApplicationInfo(String[] profiles, String[] services, Color color, Map<String, Object> vcap) {
        this.profiles = profiles;
        this.services = services;
        this.color = color;
        this.vcap = vcap;
    }

    public Map<String, Object> getVcap() {
        return vcap;
    }

    public void setVcap(Map<String, Object> vcap) {
        this.vcap = vcap;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String[] getProfiles() {
        return profiles;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }
}
