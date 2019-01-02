import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SafeKepper {
    private static String whatToDo;
    private static String filePath;
    private static String password1 = "";
    private static String password2 = "";
    private static String password3 = "";
    private static File file;
    private static Console console = System.console();
    private static boolean reEncrypt = true;
    private static Process executeShellCommand;



    public static void main(String[] args) {
       try {
           whatToDo = args[0];
           filePath = args[1];
           file = new File(filePath);

          if (whatToDo.equals("--encrypt"))
               encrypt();
           else if (whatToDo.equals("--decrypt")) {
               if(args[2].equals("--no"))
                   reEncrypt = false;
              decrypt();
          }

       } catch (Exception e) {
           System.out.println("Usage of SafeKepper --[encrypt/decrypt] filepath");
           System.out.println( " \t --encrypt: Asks for 3 passwords and encrypts given filepath with said passwords \n" +
                   "\t --decrypt: Asks for 3 passwords and decrypts the given filepath with said passwords , enter --yes or --no after file path if you want " +
                   "the file to be rencrypted after displaying it. \n" +
                   "\t filepath: is the location of the file which will be encrypted or dercrypted \n" +
                   "THIS PROGRAM HEAVILY RELIES ON GPG MV RM SO IT WILL ONLY WORK ON OS WITH THEM INSTALLED!!!!!");


        }
    }

    private static void decrypt() throws IOException, InterruptedException {
        executeShellCommand = Runtime.getRuntime().exec("clear");
        executeShellCommand.waitFor();
        char[] passwordarray = console.readPassword("Enter Level 1 Password: ");
        password1 = new String(passwordarray);
        passwordarray = console.readPassword("Enter Level 2 Password: ");
        password2 = new String(passwordarray);
        passwordarray = console.readPassword("Enter Level 3 Password: ");
        password3 = new String(passwordarray);

        try {
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("gpg --batch --passphrase " + password3 + " --decrypt-file " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("gpg --batch --passphrase " + password2 + " --decrypt-file " + file.getAbsolutePath()+ ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("gpg --batch --passphrase " + password1 + " --decrypt-file " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("rm " + file.getAbsolutePath() + ".gpg");
            executeShellCommand.waitFor();



        } catch (IOException e) {
            System.out.println("Error Moving file, Make sure you have permissions for the file and that MV is installed.");
        } catch (InterruptedException e) {
            System.out.println("Error Moving file, Make sure you have permissions for the file and that MV is installed.");

        }

        displayFile();

        if(reEncrypt) {
            System.out.println("For security " + file.getAbsolutePath() + " will be encrypted automatically with entered passwords...");
            encrypt();
        }

    }

    private static void displayFile() {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getAbsolutePath() + " not found");
        }
    }

    private static void encrypt() throws InterruptedException, IOException {
        if(password1.isEmpty() && password2.isEmpty() && password3.isEmpty()) {
            char[] passwordarray = console.readPassword("Enter Level 1 Password: ");
            password1 = new String(passwordarray);
            passwordarray = console.readPassword("Enter Level 2 Password: ");
            password2 = new String(passwordarray);
            passwordarray = console.readPassword("Enter Level 3 Password: ");
            password3 = new String(passwordarray);

            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password1 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password2 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();

            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password3 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();

            System.out.println("File Successfully Encrypted...");
        }
        else {

            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password1 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password2 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();

            executeShellCommand = Runtime.getRuntime().exec("gpg -c --batch --passphrase " + password3 + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();
            executeShellCommand = Runtime.getRuntime().exec("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());
            executeShellCommand.waitFor();

            System.out.println("File Successfully Encrypted...");

        }
    }
}
