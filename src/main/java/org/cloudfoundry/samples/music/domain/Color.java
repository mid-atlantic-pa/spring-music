package org.cloudfoundry.samples.music.domain;

public class Color {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Color(String value) {
        this.value = value;
    }
}
