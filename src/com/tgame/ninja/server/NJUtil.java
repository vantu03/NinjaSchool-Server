package com.tgame.ninja.server;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.DropRate;
import com.tgame.ninja.real.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class NJUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(NJUtil.class);

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Random random = new Random();

    public static String getDotNumber(long m) {
        String str = String.valueOf(m);
        int len = str.length() / 3;
        if (str.length() % 3 == 0) {
            --len;
        }
        for (int i = 0; i < len; ++i) {
            int index = str.length() - (i + 1) * 3 - i;
            str = str.substring(0, index) + "." + str.substring(index);
        }
        return str;
    }

    public static String getDayOpen() {
        return LocalDate.now().toString();
    }

    public static long getDaysBetween(LocalDate d1, LocalDate d2) {
        long between = ChronoUnit.DAYS.between(d1, d2);
        return Math.max(0, between);
    }

    public static boolean inBetweenDate(LocalDate start, LocalDate end) {
        LocalDate now = LocalDate.now();
        return now.compareTo(start) >= 0 && now.compareTo(end) <= 0;
    }

    public static boolean isNewWeek(LocalDate d1, LocalDate d2) {
        int week1 = d1.minusDays(1).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int week2 = d2.minusDays(1).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        return week1 != week2;
    }

    public static String getDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static String getDateTime(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(dateTimeFormatter);
    }

    public static int getHour() {
        return LocalTime.now().getHour();
    }

    public static int getMinute() {
        return LocalTime.now().getMinute();
    }

    public static int getSecond() {
        return LocalTime.now().getSecond();
    }

    public static long getMilisByMinute(int minute) {
        return minute * 60L * 1000L;
    }

    public static long getMilisByHour(int hour) {
        return hour * 60L * 60L * 1000L;
    }

    public static long getMilisByDay(int day) {
        return day * 24L * 60L * 60L * 1000L;
    }

    public static LocalDate getDateByMilis(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static String getTime(int timeRemainS) {
        int timeRemainM = 0;
        if (timeRemainS > 60) {
            timeRemainM = timeRemainS / 60;
            timeRemainS %= 60;
        }
        int timeRemainH = 0;
        if (timeRemainM > 60) {
            timeRemainH = timeRemainM / 60;
            timeRemainM %= 60;
        }
        int timeRemainD = 0;
        if (timeRemainH > 24) {
            timeRemainD = timeRemainH / 24;
            timeRemainH %= 24;
        }
        String s = "";
        if (timeRemainD > 0) {
            s = s + timeRemainD;
            s = s + "d";
            s = s + timeRemainH + "h";
        } else if (timeRemainH > 0) {
            s = s + timeRemainH;
            s = s + "h";
            s = s + timeRemainM + "'";
        } else {
            if (timeRemainM > 9) {
                s = s + timeRemainM;
            } else {
                s = s + "0" + timeRemainM;
            }
            s = s + ":";
            if (timeRemainS > 9) {
                s = s + timeRemainS;
            } else {
                s = s + "0" + timeRemainS;
            }
        }
        return s;
    }

    public static String getWeekString() {
        LocalDate dateNow = LocalDate.now();
        int w = dateNow.get(ChronoField.ALIGNED_WEEK_OF_MONTH);
        int m = dateNow.getMonthValue();
        int year = dateNow.getYear();
        return w + "_" + m + "_" + year;
    }

    public static String changeStr(String str) {
        StringBuilder de = new StringBuilder(Base64.getEncoder().encodeToString(str.getBytes()));
        byte[] data = ("K" + de).getBytes();
        de = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            de.append(data[i]);
            if (i < data.length - 1) {
                de.append("^");
            }
        }
        return de.toString();
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        int dX = Math.abs(x1 - x2);
        int dY = Math.abs(y1 - y2);
        return Math.max(dX, dY);
    }

    public static int distance(int point1, int point2) {
        return Math.abs(point1 - point2);
    }

    public static String formatVersion(int version) {
        String ver = String.valueOf(version);
        return version / 100 + "." + ver.charAt(ver.length() - 2) + "." + ver.charAt(ver.length() - 1);
    }

    public static boolean inArrayInt(int[] nums, int find) {
        for (int n : nums) {
            if (n == find) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOpenNinjaTaiNang() {
        return GameServer.openNinjaTaiNang && LocalDate.now().getDayOfMonth() <= 26;
    }

    public static boolean isOpenNinjaDeNhat() {
        return GameServer.openNinjaDeNhat && LocalDate.now().getDayOfMonth() <= 26;
    }

    public static boolean isBiNgo() {
        return false;
    }

    public static boolean isKyLan() {
        return false;
    }

    public static boolean isSonTinhThuyTinh() {
        return false;
    }

    public static boolean isTuongGiac() {
        return false;
    }

    public static boolean isMonkeyKing() {
        return false;
    }

    public static boolean isTuHaMaThan() {
        return false;
    }

    public static boolean isNgBang() {
        return false;
    }

    public static boolean isRong() {
        return false;
    }

    public static boolean isAlphaNumeric(String str) {
        if (!str.trim().isEmpty()) {
            return str.matches("^[A-Za-z0-9]+$");
        }
        return false;
    }

    public static boolean isValidUserName(String str) {
        if (!str.trim().isEmpty()) {
            return str.matches("^[A-Za-z0-9._-]+$");
        }
        return false;
    }

    public static boolean isEmail(String str) {
        return str.matches("^[a-z][a-z0-9_.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
    }

    public static boolean isPhone(String str) {
        return str.matches("^0+\\d{9}$");
    }

    public static Message messageNotLogin(byte command) throws IOException {
        if (GameServer.isServerLocal()) {
            LOGGER.info(">> Send message: {} > {}", Cmd.NOT_LOGIN, command);
        }
        Message m = new Message(Cmd.NOT_LOGIN);
        m.writeByte(command);
        return m;
    }

    public static Message messageNotMap(byte command) throws IOException {
        if (GameServer.isServerLocal() && command != -122 && command != -121 && command != -120 && command != -119) {
            LOGGER.info(">> Send message: {} > {}", Cmd.NOT_MAP, command);
        }
        Message m = new Message(Cmd.NOT_MAP);
        m.writeByte(command);
        return m;
    }

    public static Message messageSubCommand(byte command) throws IOException {
        if (GameServer.isServerLocal()) {
            LOGGER.info(">> Send message: {} > {}", Cmd.SUB_COMMAND, command);
        }
        Message m = new Message(Cmd.SUB_COMMAND);
        m.writeByte(command);
        return m;
    }

    public static String numberToString(String number) {
        StringBuilder value = new StringBuilder();
        String value2 = "";
        if (number.isEmpty()) {
            return value.toString();
        }
        if (number.charAt(0) == '-') {
            value2 = "-";
            number = number.substring(1);
        }
        for (int i = number.length() - 1; i >= 0; --i) {
            if ((number.length() - 1 - i) % 3 == 0 && number.length() - 1 - i > 0) {
                value.insert(0, number.charAt(i) + ".");
            } else {
                value.insert(0, number.charAt(i));
            }
        }
        return value2 + value;
    }

    public static int getPercent(int per, int... num) {
        try {
            int pc = random.nextInt(per);
            for (int i = 0; i < num.length; ++i) {
                if (pc < num[i]) {
                    return i;
                }
            }
        } catch (Exception e) {
        }
        return -1;
    }

    public static int probability(int... num) {
        try {
            int max = 0;
            for (int i : num) {
                max += i;
            }
            int random = randomNumber(max);
            int percentage = 0;
            for (int i = 0; i < num.length; i++) {
                percentage += num[i];
                if (percentage >= random) {
                    if (num[i] == 0) {
                        return probability(num);
                    }
                    return i;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return -1;
    }

    public static int dropItem(List<DropRate> drops) {
        int rarity = DropRate.getClosestRarity(randomNumber(DropRate.maxPercent));
        Vector<Integer> itemIds = new Vector<>();
        for (DropRate drop : drops) {
            if (drop.rarity == rarity) {
                itemIds.add(drop.itemId);
            }
        }
        if (itemIds.size() > 0) {
            return itemIds.get(random.nextInt(itemIds.size()));
        }
        return -1;
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static int randomMapBack() {
        int[] mapIds = { 1, 27, 72 };
        return mapIds[randomNumber(mapIds.length)];
    }

    public static int randomMinMax(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static int randomBetweenMinMax(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static int randomNumber(int max) {
        return random.nextInt(max);
    }

    public static byte[] readFileBytes(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return Files.readAllBytes(file.toPath());
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static String readFileString(String path) {
        try (FileInputStream file = new FileInputStream(path)) {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while (true) {
                byte[] b = { 0 };
                int x = file.read(b);
                if (x == -1) {
                    break;
                }
                ba.write(b);
            }
            return ba.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
        }
        return null;
    }

    public static String readFileString(String path, boolean isError) {
        try (FileInputStream f = new FileInputStream(path)) {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            while (true) {
                byte[] b = { 0 };
                int x = f.read(b);
                if (x == -1) {
                    break;
                }
                ba.write(b);
            }
            return ba.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            if (isError) {
                LOGGER.error("", e);
            }
        }
        return null;
    }

    public static String replace(String str, String replacement) {
        try {
            return str.replaceAll("#", replacement);
        } catch (Exception e) {
            LOGGER.error("Replace error. {} {} {}", str, replacement, e);
        }
        return "";
    }

    public static void saveFile(String path, String more) {
        try (FileWriter outFile = new FileWriter(path, false);
             PrintWriter out = new PrintWriter(outFile)
        ) {
            out.print(more);
            out.flush();
        } catch (IOException e) {
        }
    }

    public static void appendFile(String path, String more) {
        try (FileWriter outFile = new FileWriter(path, true);
             PrintWriter out = new PrintWriter(outFile)
        ) {
            out.print(more);
            out.flush();
        } catch (IOException e) {
        }
    }

    public static void sendAlertDialogInfo(Session conn, String title, String text) {
        try {
            Message message = new Message(Cmd.ALERT_MESSAGE);
            message.dos.writeUTF(title);
            message.dos.writeUTF(text);
            conn.sendMessage(message);
            message.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendAlertOpenWeb(Session conn, String strLeft, String strRight, String url, String text) {
        try {
            Message message = new Message(Cmd.ALERT_OPEN_WEB);
            message.dos.writeUTF(strLeft);
            message.dos.writeUTF(strRight);
            message.dos.writeUTF(url);
            message.dos.writeUTF(text);
            conn.sendMessage(message);
            message.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendAlertXoso(Session conn, String title, int time, String moneycuoc, int pc, String pcphanle, int currentPlayer, String infowin, int typeRoom, String moneyMe) {
        try {
            Message message = new Message(Cmd.ALERT_MESSAGE);
            message.dos.writeUTF("typemoi");
            message.dos.writeUTF(title);
            message.dos.writeShort(time);
            message.dos.writeUTF(moneycuoc);
            message.dos.writeShort(pc);
            message.dos.writeUTF(pcphanle);
            message.dos.writeShort(currentPlayer);
            message.dos.writeUTF(infowin);
            message.dos.writeByte(typeRoom);
            message.dos.writeUTF(moneyMe);
            conn.sendMessage(message);
            message.cleanup();
        } catch (Exception e) {
        }
    }

    public static void sendChienTruongAlert(String text) {
        Message message = null;
        try {
            message = new Message(Cmd.SERVER_ALERT);
            message.writeUTF(text);
            Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
            for (int userId : huPlayers.keySet()) {
                try {
                    Player player = ServerController.huPlayers.get(userId);
                    if (player == null || player.getSession() == null || !player.map.template.isMapChienTruong()) {
                        continue;
                    }
                    player.getSession().sendMessage(message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        } finally {
            if (message != null) {
                message.cleanup();
            }
        }
    }

    public static void sendDialog(Session conn, String text) {
        sendMessage(conn, text, Cmd.SERVER_DIALOG);
    }

    public static void sendMessage(Session conn, String text, byte command) {
        if (conn != null) {
            Message message = new Message(command);
            try {
                message.dos.writeUTF(text);
            } catch (IOException e) {
            }
            conn.sendMessage(message);
            message.cleanup();
        }
    }

    public static void sendMessage(Session conn, Message message) {
        if (conn != null) {
            conn.sendMessage(message);
            message.cleanup();
        }
    }

    public static void sendServer(Session conn, String text) {
        sendMessage(conn, text, Cmd.SERVER_MESSAGE);
    }

    public static void sendServerAlert(String text) {
        Message message = null;
        try {
            message = new Message(Cmd.SERVER_ALERT);
            message.writeUTF(text);
            Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
            for (int userId : huPlayers.keySet()) {
                try {
                    Player p = ServerController.huPlayers.get(userId);
                    if (p == null || p.getSession() == null) {
                        continue;
                    }
                    p.getSession().sendMessage(message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        } finally {
            if (message != null) {
                message.cleanup();
            }
        }
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
    }

    public static int typeSys(int sysA, int sysB) {
        int typeSys = 0;
        if ((sysA == 1 && sysB == 3) || (sysA == 2 && sysB == 1) || (sysA == 3 && sysB == 2)) {
            typeSys = 1;
        } else if ((sysA == 1 && sysB == 2) || (sysA == 2 && sysB == 3) || (sysA == 3 && sysB == 1)) {
            typeSys = 2;
        }
        return typeSys;
    }

    public static class sortDataTop implements Comparator<Player> {

        public int type;

        public sortDataTop(int type) {
            this.type = 0;
            this.type = type;
        }

        @Override
        public int compare(Player o1, Player o2) {
            return Integer.compare(o2.pointLoiDaiClass, o1.pointLoiDaiClass);
        }
    }
}
