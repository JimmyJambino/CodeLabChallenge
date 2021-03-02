package com.company.Objects;

import com.company.Tool.Color;
import com.company.GameObject;

public class Wall extends GameObject {

    public String toString() {
        return Color.BLACK + "[wall]" + Color.RESET;
    }
}
