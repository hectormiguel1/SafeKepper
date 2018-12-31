import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SafeKepper {
    private static String whatToDo;
    private static String filePath;
    private static String password1 = "";
    private static String password2 = "";
    private static String password3 = "";
    private static File file;
    private static Console console = System.console();
    private static ExecuteShellCommand shellCommand = new ExecuteShellCommand();


    public static void main(String[] args) {
       try {
           whatToDo = args[0];
           filePath = args[1];
           file = new File(filePath);

          if (whatToDo.equals("--encrypt"))
               encrypt();
           else if (whatToDo.equals("--decrypt"))
               decrypt();

       } catch (Exception e) {
           System.out.println("Usage of SafeKepper --[encrypt/decrypt] filepath");
           System.out.println( " \t --encrypt: Asks for 3 passwords and encrypts given filepath with said passwords \n" +
                   "\t --decrypt: Asks for 3 passwords and decrypts the given filepath with said passwords \n" +
                   "\t filepath: is the location of the file which will be encrypted or dercrypted \n" +
                   "THIS PROGRAM HEAVILY RELIES ON GPG MV RM SO IT WILL ONLY WORK ON OS WITH THEM INSTALLED!!!!!");


        }
    }

    private static void decrypt() {
        shellCommand.executeCommand("clear");
        char[] passwordarray = console.readPassword("Enter Level 1 Password: ");
        password1 = new String(passwordarray);
        passwordarray = console.readPassword("Enter Level 2 Password: ");
        password2 = new String(passwordarray);
        passwordarray = console.readPassword("Enter Level 3 Password: ");
        password3 = new String(passwordarray);

        shellCommand.executeCommand("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
        shellCommand.executeCommand("gpg --batch --passphrase " + password3 + " --decrypt-file " + file.getAbsolutePath() + ".gpg");

        shellCommand.executeCommand("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
        shellCommand.executeCommand("gpg --batch --passphrase " + password2 + " --decrypt-file " + file.getAbsolutePath() + ".gpg");

        shellCommand.executeCommand("mv " + file.getAbsolutePath() + " " + file.getAbsolutePath() + ".gpg");
        shellCommand.executeCommand("gpg --batch --passphrase " + password1 + " --decrypt-file " + file.getAbsolutePath() + ".gpg");

        shellCommand.executeCommand("rm " + file.getAbsolutePath() + ".gpg");
        shellCommand.executeCommand("cat " + filePath);

        displayFile();

        System.out.println("For security " + file.getAbsolutePath() + " will be encrypted automatically with entered passwords...");
        encrypt();

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

    private static void encrypt() {
        if(password1.isEmpty() && password2.isEmpty() && password3.isEmpty()) {
            char[] passwordarray = console.readPassword("Enter Level 1 Password: ");
            password1 = new String(passwordarray);
            passwordarray = console.readPassword("Enter Level 2 Password: ");
            password2 = new String(passwordarray);
            passwordarray = console.readPassword("Enter Level 3 Password: ");
            password3 = new String(passwordarray);

            shellCommand.executeCommand("gpg -c --batch --passphrase " + password1 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            shellCommand.executeCommand("gpg -c --batch --passphrase " + password2 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            shellCommand.executeCommand("gpg -c --batch --passphrase " + password3 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            System.out.println("File Successfully Encrypted...");
        }
        else {
            shellCommand.executeCommand("gpg -c --batch --passphrase " + password1 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            shellCommand.executeCommand("gpg -c --batch --passphrase " + password2 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            shellCommand.executeCommand("gpg -c --batch --passphrase " + password3 + " " + file.getAbsolutePath());
            shellCommand.executeCommand("mv " + file.getAbsolutePath() + ".gpg" + " " + file.getAbsolutePath());

            System.out.println("File Successfully Encrypted...");

        }
    }
}
