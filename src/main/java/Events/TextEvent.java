package Events;

import DataModels.MyTimeZoneUtils;
import DataModels.DiscordUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TextEvent extends ListenerAdapter {
    
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - hh:mm:ss O");
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (messageSent[0].equalsIgnoreCase("t!addTime")) {
            if (!event.getMember().getUser().isBot()) {
                if (messageSent.length <= 1) {
                    event.getChannel().sendMessage("Please indicate your time zone in UTC or GMT (Ex: t!addTime GMT-7 or t!addTime UTC+2)").queue();
                } else {
                    ZoneId zone = MyTimeZoneUtils.getZone(messageSent);
                    if (zone != null) {
                        long userId = event.getMember().getUser().getIdLong();
                        String name = event.getMember().getUser().getName();
                        DiscordUser user = DiscordUser.contains(userId);
                        if (user != null) {
                            System.out.println("Found user!");
                            user.setTimeZone(zone);
                        } else {
                            System.out.println("No user found. New One created!");
                            DiscordUser newUser = new DiscordUser(userId, name, zone);
                            DiscordUser.addUser(newUser);
                        }
                        event.getChannel().sendMessage("Set " + name + "'s time zone to " + zone).queue();
                    } else {
                        event.getChannel().sendMessage("Unable to set time zone. Invalid input.").queue();
                    }
                }
            }
        } else if (messageSent[0].equalsIgnoreCase("t!timeNow")) {
            if (!event.getMember().getUser().isBot()) {
                int numMentioned = event.getMessage().getMentionedUsers().size();
                if (numMentioned < 1) {
                    event.getChannel().sendMessage("You need to mention a member. (Ex: t!timeNow @username)").queue();
                } else {
                    for (int i = 0; i < numMentioned; i++) {
                        User target = event.getMessage().getMentionedUsers().get(i);
                        DiscordUser user = DiscordUser.contains(target.getIdLong());
                        if (user != null) {
                            OffsetDateTime userTime = MyTimeZoneUtils.getLocalTime(user.getTimeZone());
                            event.getChannel().sendMessage("You mentioned " + user.getName() + ", their time is: " + userTime.format(formatter)).queue();
                        } else {
                            event.getChannel().sendMessage("User has not set up a time zone yet.").queue();
                        }
                    }
                }
            }
        } else if (messageSent[0].equalsIgnoreCase("t!help")) {
            event.getChannel().sendMessage("Type ```t!addTime <timezone>``` to set your local time zone. ```Ex: t!addTime GMT-5```\n" +
                    "Type ```t!timeNow <@user>``` to get the local time of that user. ```Ex: t!timeNow @bob```\n" +
                    "Type ```t!help``` to vew this menu.").queue();
        }
    }
    
    
    
}
