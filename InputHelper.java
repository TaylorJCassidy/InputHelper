import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 *
 * @author Martin Gallacher, heavily modified by Taylor Cassidy
 */
public class InputHelper {
    private final Scanner reader;
    
    /**
     * Default Constructor
     */
    public InputHelper() {
        reader = new Scanner(System.in);
    }

    /**
     * Reads a character from the command line
     * @param prompt the prompt to be delivered to the user
     * @return typed char
     */
    public char readCharacter(String prompt) {        
        boolean empty;
        String inputString;
        do {
            System.out.println(prompt + ": ");
            inputString = reader.nextLine();
            empty = inputString.isEmpty();
            if (empty) {
                System.out.println("No character inputted. Please input a character.");
            }
        }
        while (empty);
        return inputString.charAt(0);
    }    
    
    /**
     * Reads a character from the command line, must be from validCharacters string
     * @param prompt the prompt to be delivered to the user
     * @param validCharacters string to validate char
     * @return typed char
     */
    public char readCharacter(String prompt, String validCharacters) {
        char inputText = 0;
        boolean inputError;        
        do {
            inputError = false;             
            System.out.println(prompt + ": ");
            String inputString = reader.nextLine();
            if (inputString.isEmpty()) {
                inputError = true;
                System.out.println("No character inputted. Please input a character.");
            }
            else {
                inputText = inputString.charAt(0);
                if (validCharacters.indexOf(inputText) == -1) {
                    inputError = true;
                    System.out.println("\nCharacter out of range. Please re-enter."); 
                }
            }
            
        } while (inputError);        
        return inputText;
    }     
    
    /**
     * Reads string from command line, can be blank
     * @param prompt the prompt to be delivered to the user
     * @return typed String
     */
    public String readString(String prompt) {
        System.out.println(prompt + ": ");
        return reader.nextLine();
    }
    
    /**
     * Reads string from command line, cannot be blank
     * @param prompt the prompt to be delivered to the user
     * @param what what the user is inputting, use null for generic error message
     * @return typed String
     */
    public String readStringRequired(String prompt, String what) {
        boolean empty;
        String inputText;
        do {
            System.out.println(prompt + ": ");
            inputText = reader.nextLine();
            empty = inputText.isEmpty();
            if (empty) {
                if (what == null) {
                    System.out.println("No text inputted. Please input text.");
                }
                else {
                    System.out.println("No " + what + " inputted. Please input a(n) " + what + ".");
                }
            }
        }
        while (empty);
        return inputText;
    }
    
    /**
     * Reads an int from the command line, must be between min and max values
     * @param prompt the prompt to be delivered to the user
     * @param max max int value
     * @param min min int value
     * @return typed int
     */
    public int readInt(String prompt, int max, int min) {
        int inputNumber = 0;
        boolean inputError;
        do {
            inputError = false;                
            System.out.println(prompt + ": ");

            try {
                inputNumber = Integer.parseInt(reader.nextLine());
                if (inputNumber < min || inputNumber > max) {
                    inputError = true;
                    System.out.println("\nNumber is out of range. Please re-enter.");                        
                }
            } catch (NumberFormatException e) {
                inputError = true;
                System.out.println("\nNot a valid number. Please re-enter.");
            }
        } while (inputError);
        return inputNumber;
    } 
    
    /**
     * Reads an int from the command line
     * @param prompt the prompt to be delivered to the user
     * @return typed int
     */
    public int readInt(String prompt) {
        int inputNumber = 0;
        boolean inputError;
        do {
            inputError = false;                
            System.out.println(prompt + ": ");
            try {
                inputNumber = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                inputError = true;
                System.out.println("\nNot a valid number or number is out of bounds. Please re-enter.");
            }
        } while (inputError);
        return inputNumber;
    }
    
    /**
     * Reads a boolean from the command line
     * @param prompt prompt to be delivered to user
     * @return typed boolean value
     */
    public boolean readBoolean(String prompt) {
        boolean inputError;
        boolean returnBoolean = false;
        do {
            inputError = false;
            System.out.println(prompt + ": ");
            String inputBoolean = reader.nextLine();
            if (inputBoolean.isEmpty()) {
                inputError = true;
                System.out.println("Cannot be empty. Please re-enter either true or false.");
            }
            else {
                switch(inputBoolean.toLowerCase()) {
                    case "true":
                        returnBoolean = true;
                        break;
                    case "false":
                        returnBoolean = false;
                        break;
                    default:
                        inputError = true;
                        System.out.println("\nNot a valid input. Please re-enter either true or false.");
                }
            }
        }
        while (inputError);
        return returnBoolean;
    }
    
    /**
     * Reads a boolean from the command line, must be either trueValue or falseValue
     * @param prompt prompt to be delivered to user
     * @param trueValue the String the user must type to indicate true
     * @param falseValue the String the user must type to indicate false
     * @return typed value converted to a boolean
     */
    public boolean readBoolean(String prompt, String trueValue, String falseValue) {
        boolean inputError;
        boolean returnBoolean = false;
        do {
            inputError = false;
            System.out.println(prompt + ": ");
            String inputBoolean = reader.nextLine();
            if (inputBoolean.isEmpty()) {
                inputError = true;
                System.out.println("Cannot be empty. Please re-enter either " + trueValue + " or " + falseValue + ".");
            }
            else {
                if (inputBoolean.equalsIgnoreCase(trueValue)) {
                    returnBoolean = true;
                }
                else if (inputBoolean.equalsIgnoreCase(falseValue)) {
                    returnBoolean = false;
                }
                else {
                    inputError = true;
                    System.out.println("\nNot a valid input. Please re-enter either " + trueValue + " or " + falseValue + ".");
                }
            }
        }
        while (inputError);
        return returnBoolean;
    }
    
    /**
     * Reads a LocalDate from the command line
     * @param prompt prompt to be delivered to user
     * @param dateFormat date format that the user must conform to, see DateTimeFormatter docs
     * @param userDateFormat date format to show to the user, e.g "Please input date (yyyy-mm-dd)"
     * @return typed date in the form of a LocalDate object
     * @see https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     */
    public LocalDate readDate(String prompt, String dateFormat, String userDateFormat) {
        boolean inputError = false;

        do {
            String dateToParse = this.readStringRequired(prompt + " (" + userDateFormat +")", "date");
            DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat).withResolverStyle(ResolverStyle.STRICT);
            try {
                return LocalDate.parse(dateToParse, format);
            }
            catch(DateTimeParseException e) {
                System.out.println("\nNot a valid date. Please re-enter to the format of " + userDateFormat);
                inputError = true;
            }
        }
        while (inputError);
        return null;
    }
}
