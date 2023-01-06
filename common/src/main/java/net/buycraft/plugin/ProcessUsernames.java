package net.buycraft.plugin;

public class ProcessUsernames {
    public static String ProcessBedrockUsername(String username) {
        if(username.startsWith("BR_")) {
            username = username.replaceFirst("BR_", "BR-");
        }
        return username;
    }
}
