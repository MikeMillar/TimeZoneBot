# TimeZoneBot
Simple Discord bot to store user time zones and allow users to call a function and see their local time.

DISCORD BOT PROJECT DETAILS:

This will be a simple bot that will retrieve a user's local time and display it chat.

Commands:
   - t!addTime = prompts user for timezone/ adds user timezone to bot application
   - t!addTime <OffsetZone> = sets users time zone
   - t!timenow @user_name#tag = displays that user's name in chat
   - t!help = lists bot commands

Potential Features:
   - Display local user's current weather as well

Code outline:
   - Wait for user event to add timezone
   - Extract/ Validate timezone from user message after command is called
   - Store timezone in dictionary (user+timezone)
   - Calculate and send user's local time when prompted
   - Send error message if no such user+timezone exists
   - ???
   - Profit

DEADLINE:
   - January 10, Friday (so on Saturday it will be deployed)

TOOLS:
   - Intellij + maven plugin
   - Github account
   - Git
