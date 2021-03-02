package com.company.Objects;

import com.company.GameObject;
import com.company.Tool.Color;

public class Goal extends GameObject {

    public String toString() {
        return Color.YELLOW + "[goal]" + Color.RESET;
    }
}
