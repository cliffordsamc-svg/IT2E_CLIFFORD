package com.scc;

import com.scc.dao.DatabaseInitializer;
import com.scc.ui.Menu;

public class Main {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        new Menu().run();
    }
}