package fileIO;

import entity.Setting;

import java.io.*;

/**
 * Created by marco on 29/03/2017.
 */
public class MyFileReader {

    private BufferedReader bufferedReader;

    public MyFileReader() {
        String root = "";
        try {
            root = new File(".").getCanonicalPath() + "\\";
        } catch (Exception e) {
            System.out.println("Cannot get the root path!");
        }

        try {

            String path = "";
            if (Setting.system == 1)
                if (Setting.fileType == 1)
                    path = root + "src\\main\\java\\data\\processResultTraj.csv";
                else if (Setting.fileType == 2)
                    path = root + "\\data\\processed_data.txt";
                else if (Setting.system == 0)
                    path = "/mnt/tomcat-3/webapps/AdDeployment/resources/data/processResultTraj.csv";

            if (Setting.test)
                System.out.println(path);

            bufferedReader = new BufferedReader(new FileReader(path));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPath(String inputFilePath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getNextLine() {

        try {
            String line = bufferedReader.readLine();
            if (line != null) {
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {

        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
