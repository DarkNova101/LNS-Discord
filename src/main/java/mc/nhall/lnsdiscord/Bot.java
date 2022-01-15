package mc.nhall.lnsdiscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Bot {

    public Bot() {
        Map<String, Object> config = getConfig();
        JDA jda = null;

        try {
            jda = JDABuilder.createLight((String) config.get("token"))
                    .addEventListeners()
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if (jda == null) {
            System.out.println("JDA is not initialized.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Bot();
    }

    private Map<String, Object> getConfig() {
        File file = new File("config.yml");

        try {
            if (!file.createNewFile()) {
                InputStream is = getClass().getResourceAsStream("/config.yml");
                OutputStream os = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
                os.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        return yaml.load(is);
    }

    public void print(String text) {
        System.out.println("BOT ----" + text);
    }
}
