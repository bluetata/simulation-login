package org.simulation;


import org.simulation.login.AbstractLogin;
import org.simulation.login.impl.GitHubLoginAdapter;

public class SimulationLogin {

    public static void main(String[] args) {
    AbstractLogin login = new GitHubLoginAdapter("", "");
        login.login();
    }
}
