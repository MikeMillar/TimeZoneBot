import DataModels.DiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import Events.TextEvent;

public class MoBot {
    
    public static void main(String[] args) throws Exception {
        JDA jda = new JDABuilder("TEMP NO VALUE").build();
        DiscordUser.loadUsers();
        jda.addEventListener(new TextEvent());
    }
}
