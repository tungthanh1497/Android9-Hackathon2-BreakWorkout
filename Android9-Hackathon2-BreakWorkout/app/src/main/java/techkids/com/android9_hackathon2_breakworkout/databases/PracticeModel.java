package techkids.com.android9_hackathon2_breakworkout.databases;

import java.io.Serializable;

/**
 * Created by tungthanh.1497 on 06/28/2017.
 */

public class PracticeModel implements Serializable {
    private int id;
    private String name;
    private String how;
    private boolean neck;
    private boolean eye;
    private boolean arm;
    private boolean leg;
    private boolean body;
    private boolean environment;
    private String image;

    public PracticeModel(int id, String name, String how, boolean neck, boolean eye, boolean arm, boolean leg, boolean body, boolean environment, String image) {
        this.id = id;
        this.name = name;
        this.how = how;
        this.neck = neck;
        this.eye = eye;
        this.arm = arm;
        this.leg = leg;
        this.body = body;
        this.environment = environment;
        this.image = image;
    }

    @Override
    public String toString() {
        return "PracticeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", neck=" + neck +
                ", eye=" + eye +
                ", arm=" + arm +
                ", leg=" + leg +
                ", body=" + body +
                ", environment=" + environment +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public boolean isNeck() {
        return neck;
    }

    public void setNeck(boolean neck) {
        this.neck = neck;
    }

    public boolean isEye() {
        return eye;
    }

    public void setEye(boolean eye) {
        this.eye = eye;
    }

    public boolean isArm() {
        return arm;
    }

    public void setArm(boolean arm) {
        this.arm = arm;
    }

    public boolean isLeg() {
        return leg;
    }

    public void setLeg(boolean leg) {
        this.leg = leg;
    }

    public boolean isBody() {
        return body;
    }

    public void setBody(boolean body) {
        this.body = body;
    }

    public boolean isEnvironment() {
        return environment;
    }

    public void setEnvironment(boolean environment) {
        this.environment = environment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
