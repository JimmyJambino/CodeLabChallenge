package com.company.Objects;

import com.company.Tool.Color;
import com.company.GameObject;

public class Road extends GameObject {
    public String toString() {
        //return "[road]";
        return Color.blackB + Color.WHITE +"[    ]" + Color.RESET;
    }
}
