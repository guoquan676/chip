//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.template.chip.controller.test.test2;

import java.io.Serializable;
import java.util.Objects;

public class Industry implements Serializable, Comparable<Industry> {
    private static final long serialVersionUID = -1002562076609538761L;
    private int key;
    private String value;
    private int sort;

    public Industry() {
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int compareTo(Industry o) {
        return this.sort - o.sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Industry industry = (Industry)o;
            return this.key == industry.key && this.sort == industry.sort && Objects.equals(this.value, industry.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.key, this.value, this.sort});
    }

    public String toString() {
        return "Industry{key=" + this.key + ", value='" + this.value + '\'' + ", sort=" + this.sort + '}';
    }
}
