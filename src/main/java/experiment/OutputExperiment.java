package experiment;


import fileIO.MyFileWriter;

import java.util.ArrayList;

public class OutputExperiment {

    private MyFileWriter myFileWriter;

    public OutputExperiment(String fineName) {
        myFileWriter = new MyFileWriter(fineName);
    }

    public void writeFile(ArrayList<String> content) {
        for (String line : content) {
            myFileWriter.writeToFile(line);
        }

        myFileWriter.close();
    }


}
