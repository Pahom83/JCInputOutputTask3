import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static final String gamePath = System.getProperty("user.home") + "/Games/GunRunner";
    public static final String gameSavePath = gamePath + "/savegames";

    public static void main(String[] args) {
        unpackZip(gameSavePath + "/savegames.zip", gameSavePath);
        System.out.println(openProgress(gameSavePath + "/gamer1.save"));
        System.out.println(openProgress(gameSavePath + "/gamer2.save"));
        System.out.println(openProgress(gameSavePath + "/gamer3.save"));
    }

    private static GameProgress openProgress(String gameSavePath) {
        GameProgress gameProgress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gameSavePath))){
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }

    private static void unpackZip(String pathToZip, String unpackPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToZip))){
                ZipEntry entry;
                String name;
                while ((entry = zis.getNextEntry()) != null) {
                    name = entry.getName();
                    FileOutputStream fout = new FileOutputStream(unpackPath + "/" + name);
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zis.closeEntry();
                    fout.close();
                }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}