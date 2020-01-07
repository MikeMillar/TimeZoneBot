package DataModels;

import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class MyTimeZoneUtils {
    
    public static ZoneId getZone(String[] message) {
        try {
            ZoneId id = ZoneId.of(message[1]);
            return id;
        } catch (DateTimeException e) {
            System.out.println("Invalid ZoneId: " + e.getLocalizedMessage());
        }
        return null;
    }
    
    public static OffsetDateTime getLocalTime(ZoneId zone) {
        return OffsetDateTime.now(zone);
    }
    
}
