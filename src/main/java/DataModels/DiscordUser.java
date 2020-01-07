package DataModels;

import java.io.*;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiscordUser implements Serializable {
    
    private static Map<String, Long> userIndex = new LinkedHashMap<>();
    private static Map<Long, DiscordUser> users = new LinkedHashMap<>();
    private static RandomAccessFile ra;
    
    private long id;
    private String name;
    private ZoneId timeZone;
    
    public DiscordUser(long id, String name, ZoneId timeZone) {
        this.id = id;
        this.name = name;
        this.timeZone = timeZone;
    }
    
    public static void saveUsers() {
        try (ObjectOutputStream userFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data\\Users.dat")))) {
            System.out.println("In User Save Block");
            for (DiscordUser user : users.values()) {
                userFile.writeObject(user);
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        try (DataOutputStream indexFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("Data\\UserIndex.dat")))) {
            System.out.println("In Index Save Block");
            for (DiscordUser user: users.values()) {
                indexFile.writeUTF(user.getName());
                indexFile.writeLong(user.getId());
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        System.out.println("Users saved!");
        printUsers();
        printUserIndex();
    }
    
    public static void loadUsers() {
        
        try (ObjectInputStream userFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Data\\Users.dat")))) {
            System.out.println("In User Load Block");
            boolean eof = false;
            while (!eof) {
                try {
                    DiscordUser user = (DiscordUser) userFile.readObject();
                    System.out.println("Read User: " + user);
                    users.put(user.getId(),user);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        try (DataInputStream indexFile = new DataInputStream(new BufferedInputStream(new FileInputStream("Data\\UserIndex.dat")))) {
            System.out.println("In Index Load Block");
            boolean eof = false;
            while (!eof) {
                try {
                    String name = indexFile.readUTF();
                    long userId = indexFile.readLong();
                    System.out.println("User Index-- Name: " + name + "; id: " + userId);
                    userIndex.put(name,userId);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        System.out.println("Users loaded!");
        printUsers();
        printUserIndex();
    }
    
    public static Map<Long, DiscordUser> getUsers() {
        return users;
    }
    
    public static DiscordUser contains(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        return null;
    }
    
    public static void addUserToIndex(DiscordUser user) {
        if (user != null) {
            userIndex.put(user.getName(), user.getId());
        }
    }
    
    public static void addUser(DiscordUser user) {
        if (user != null) {
            users.put(user.getId(), user);
            addUserToIndex(user);
            saveUsers();
        }
    }
    
    public static void deleteUserFromIndex(DiscordUser user) {
        try {
            userIndex.remove(user.getName());
        } catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public static void deleteUser(DiscordUser user) {
        try {
            users.remove(user);
            deleteUserFromIndex(user);
            saveUsers();
        } catch (NullPointerException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        saveUsers();
    }
    
    public ZoneId getTimeZone() {
        return timeZone;
    }
    
    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
        saveUsers();
    }
    
    public static void printUsers() {
        System.out.println("Printing Users: ");
        for (DiscordUser u: users.values()) {
            System.out.println(u);
        }
    }
    
    public static void printUserIndex() {
        System.out.println("Printing User Index:");
        for (String user: userIndex.keySet()) {
            System.out.println("Name: " + user + "; ID: " + userIndex.get(user));
        }
    }
    
    public void fillUserIndex() {
        for (DiscordUser user: users.values()) {
            userIndex.put(user.getName(),user.getId());
        }
    }
    
    public String toString() {
        return "User ID: " + id + "; Name: " + name + "; Time Zone: " + timeZone;
    }
}
