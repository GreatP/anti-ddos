package com.cetc.security.ddos.persistence;

/**
 * Created by lb on 2016/5/30.
 */
public class AttackTypeCount {
    int attack_type;
    long count;

    public int getAttack_type() {
        return attack_type;
    }

    public void setAttack_type(int attack_type) {
        this.attack_type = attack_type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
