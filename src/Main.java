import java.awt.*;
import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String imageFilePath = "C:/jev/image.jpg";
        String audioFilePath = "C:/jev/file.mp3";
        String videoFilePath = "C:/jev/video.mp4";

        Thread imageThread = new Thread(() -> saveFileFromURL(
                "https://avatars.mds.yandex.net/i?id=207777aadf6a147bdd6ec7e3cbca01ee_l-5233360-images-thumbs&n=13", imageFilePath));

        Thread audioThread = new Thread(() -> saveFileFromURL(
                "https://rus.hitmotop.com/get/music/20110213/Rammstein_-_Rammstein_420734.mp3", audioFilePath));

        Thread videoThread = new Thread(() -> saveFileFromURL(
                "https://videocdn.cdnpk.net/joy/content/video/free/2019-09/large_preview/190828_27_SuperTrees_HD_17.mp4?token=exp=1729354189~hmac=b9a4712bd4e6a58d97cf857c57025be328bed49d8ef6c0da5a8fc700e9df1a51", videoFilePath));

        imageThread.start();
        audioThread.start();
        videoThread.start();

        try {
            imageThread.join();
            audioThread.join();
            videoThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("File downloads completed.");

        openFileWithDefaultApp(imageFilePath);
        openFileWithDefaultApp(audioFilePath);
        openFileWithDefaultApp(videoFilePath);
    }

    private static void saveFileFromURL(String fileURL, String destinationPath) {
        try (InputStream inputStream = new URL(fileURL).openStream();
             OutputStream outputStream = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File " + destinationPath + " downloaded successfully.");
        } catch (IOException e) {
            System.err.println("Error downloading " + destinationPath + ": " + e.getMessage());
        }
    }

    private static void openFileWithDefaultApp(String filePath) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(filePath));
            } else {
                System.err.println("Desktop not supported.");
            }
        } catch (IOException e) {
            System.err.println("Error opening file " + filePath + ": " + e.getMessage());
        }
    }
}
