package resources.helperClasses;
import org.apache.commons.io.input.ReversedLinesFileReader;
import testAutomationListner.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleCSV {

    /*This method is to read single line from the start of csv file
     *@param file_path is the first parameter in fileOperation
     */
    public static String[] fileOperation(String file_path){
        try {
                File file=new File(file_path);
                BufferedReader br=new BufferedReader(new FileReader(file));
                String[] workerDetails=br.readLine().split(",");
                return workerDetails;
            }catch (FileNotFoundException fileNotFoundException) {
                Log.error("File not found! check the correct file path." + fileNotFoundException.getMessage());
            }catch (IOException ioException){
                Log.error("Can't read the file data." + ioException.getMessage());
            }
          return null;
    }

    /*This method is read last line from csv file
     *@param file_path is the first parameter in readFromLast
     */
    public static String readFromLast(String filePath) {
        String last="", line;
        try
        {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null)
            {
                    last = line;
            }
            return last;
        }catch (FileNotFoundException fileNotFoundException) {
            Log.error("File not found! check the correct file path." + fileNotFoundException.getMessage());
        }catch (IOException ioException){
            Log.error("Can't read the file data." + ioException.getMessage());
        }
        return "";

    }

    /*This method is to read all the lines from a csv file in form of list inside list
     *@param file_path is the first parameter in newFileOperation
     */
    public static List <List<String>> newFileOperation(String filepath) throws IOException {
        try {
            String line = "";
            List<List<String>> productDetails = new ArrayList<>();
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] products = line.split(",");
                productDetails.add(Arrays.asList(products));
            }
            return productDetails;

        } catch (FileNotFoundException e) {
            System.out.println("File not found! check the correct file path.");
        }
        return null;
    }

    /*This method is to write data to a csv file
     *@param file_path is the first parameter in writeData
     */
    public static void writeData(String file_path,String email,String password)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(file_path);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);

            StringBuilder sb = new StringBuilder();
            sb.append(email);
            sb.append(',');
            sb.append(password);
            sb.append('\n');
            outputfile.write(sb.toString());

            // closing writer connection
            outputfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*This method is to write posted job titles in a csv file
     *@param file_path is the first parameter in writeJobData
     */
    public static void writeJobData(String file_path,String jobTitle)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(file_path);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);

            StringBuilder sb = new StringBuilder();
            sb.append(jobTitle);
            sb.append('\n');
            outputfile.write(sb.toString());

            // closing writer connection
            outputfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*This method is to read last N lines from csv file
     *@param file_path is the first parameter in readLastLine
     */
    public static List<String> readLastLine(String file_path) {
        File file=new File(file_path);
        List<String> result = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file)) {
            String line = "";
            while ((line = reader.readLine()) != null && result.size() < 3) {
                result.add(line);
            }
            for(int i=0;i<result.size();i++)
                System.out.println(result.get(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
