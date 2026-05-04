import UpperCaseModule.*;

public class UpperCaseImpl extends UpperCasePOA{
    public String ConvertToUppercase(String input){
        return input.toUpperCase();
    }
}